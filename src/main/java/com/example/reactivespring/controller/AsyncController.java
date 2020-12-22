package com.example.reactivespring.controller;

import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.CompletableFuture;

@RestController
public class AsyncController {
    @GetMapping("/async_result")
    @Async
    public CompletableFuture getResultAsyc(HttpServletRequest request) throws InterruptedException {
        // sleep for 500 ms
        Thread.sleep(5000);
        return CompletableFuture.completedFuture("Result is ready!");
    }
}