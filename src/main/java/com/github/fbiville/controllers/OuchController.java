package com.github.fbiville.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/errors/")
public class OuchController {

    @RequestMapping("not-found")
    public String notFound() {
        return "404";
    }
}
