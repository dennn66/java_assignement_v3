package com.dennn66.tasktracker.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class MainController {
    // GET http://localhost:8189/app/
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String showHomePage() {
        return "index";
    }

    @GetMapping("/abc404")
    public String page404() {
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found");
    }

    @GetMapping("/resp")
    public String respIndex(HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.NOT_FOUND.value());
        return "index";
    }

}
