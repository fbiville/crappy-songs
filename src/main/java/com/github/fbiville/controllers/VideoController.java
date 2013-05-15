package com.github.fbiville.controllers;

import com.github.fbiville.domain.Video;
import com.github.fbiville.exceptions.ResourceNotFoundException;
import com.github.fbiville.repositories.VideosRepository;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.github.fbiville.validation.YoutubeUrlValidator.YOUTUBE_REGEX;
import static com.google.common.collect.Iterables.get;
import static com.google.common.collect.Iterables.size;
import static java.util.regex.Pattern.compile;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;


@Controller
public class VideoController {

    private final VideosRepository videosRepository;

    @Inject
    public VideoController(VideosRepository videosRepository) {
        this.videosRepository = videosRepository;
    }

    @RequestMapping(value = "/", method = GET)
    public ModelAndView index() {
        return new ModelAndView("index");
    }

    @RequestMapping(value = "/video/{id}", method = GET)
    public ModelAndView watch(@PathVariable long id) {
        Video video = videosRepository.findOne(id);
        if (video == null) {
            throw new ResourceNotFoundException(id + " not found");
        }

        ModelAndView form = new ModelAndView("watch");
        form.addObject("video", getContents(video));
        return form;
    }

    @RequestMapping(value = "/video/next", method = GET)
    public void watchRandom(HttpServletResponse response) throws IOException {
        Iterable<Video> all = videosRepository.findAll();
        int count = size(all);
        if (count == 0) {
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
        }
        else {
            Video video = get(all, new Random().nextInt(count));
            response.setStatus(HttpServletResponse.SC_TEMPORARY_REDIRECT);
            response.addHeader("Location", "/video/" + video.getId().longValue());
        }

    }

    @RequestMapping(value = "/video", method = GET)
    public ModelAndView form(@ModelAttribute("videoCommand") Video video) {
        return new ModelAndView("form");
    }

    @RequestMapping(value = "/video", method = POST)
    public ModelAndView submit(@ModelAttribute("videoCommand") @Valid Video video, BindingResult result) {
        ModelAndView view;
        if (result.hasErrors()) {
            view = new ModelAndView("form");
        }
        else {
            videosRepository.save(video);
            view = new ModelAndView("redirect:/");
        }
        return view;
    }

    private String getContents(Video video) {
        Pattern compile = compile(YOUTUBE_REGEX);
        Matcher matcher = compile.matcher(video.getUrl());
        if (matcher.matches()) {
            String id = matcher.group(1);
            String embedded = "<iframe width='425' height='349' " +
                "src='https://www.youtube.com/embed/{ID}?autoplay=1' " +
                "frameborder='0' allowfullscreen='true'></iframe>";
            return embedded.replace("{ID}", id);
        }
        return "";
    }
}
