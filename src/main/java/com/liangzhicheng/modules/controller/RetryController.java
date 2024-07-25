package com.liangzhicheng.modules.controller;

import com.liangzhicheng.modules.service.IRetryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/retry")
public class RetryController {

    private final IRetryService retryService;

    @GetMapping(value = "/test")
    public String test() {
        retryService.test(0);
        return "success";
    }

}