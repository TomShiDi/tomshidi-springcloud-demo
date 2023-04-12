package com.tomshidi.demo.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author tangshili
 * @since 2023/4/12 16:23
 */
@RestController
@RequestMapping("/security")
public class SecurityController {

    @PreAuthorize("hasPermission('','tomshidi:view')")
    @RequestMapping("/request")
    public String request() {
        return "success";
    }
}
