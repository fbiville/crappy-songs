package com.github.fbiville.repositories;

import com.github.fbiville.domain.Video;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.repository.annotation.RestResource;

import java.util.List;

@RestResource(path = "/videos")
public interface VideosRepository extends CrudRepository<Video, Long> {

    List<Video> findByUrlStartsWith(@Param("url") String url);
}
