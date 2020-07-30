package com.egorhristoforov.eventikrestapi.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping(value = "")
public class RootController {
    @GetMapping(value = "/swagger")
    public void swaggerDocumentation(HttpServletResponse httpResponse) throws Exception {
        httpResponse.sendRedirect("/swagger-ui.html");
    }
}
