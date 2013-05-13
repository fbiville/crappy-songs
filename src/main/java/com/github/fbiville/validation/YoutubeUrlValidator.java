package com.github.fbiville.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.regex.Pattern.compile;

public class YoutubeUrlValidator implements ConstraintValidator<YoutubeUrl, String> {

    public static final String YOUTUBE_REGEX = "^(?:(?:https?)://)?(?:www.)?youtube.com/watch?(?:.*)v=([A-Za-z0-9._%-]{11}).*";

    @Override
    public void initialize(YoutubeUrl constraintAnnotation) {

    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        Pattern pattern = compile(YOUTUBE_REGEX);
        Matcher matcher = pattern.matcher(value);
        return matcher.matches();
    }
}
