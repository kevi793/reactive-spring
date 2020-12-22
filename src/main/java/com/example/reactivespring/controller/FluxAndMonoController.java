package com.example.reactivespring.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.Date;
import java.util.Random;

@RestController
public class FluxAndMonoController {

    @GetMapping("/flux")
    public Flux<Integer> flux() {
        return Flux.just(1,2,3,4)
                .delayElements(Duration.ofSeconds(1)).log();
    }

    @GetMapping(value = "/flux-stream", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public Flux<Integer> fluxWithStream() {
        return Flux.range(1,1000).log();
        //        Flux<Integer> flux1 = Flux.range(1,1000).log();
//        Flux<Integer> flux2 = Flux.range(1,1000).log();
//        return Flux.zip(flux1, flux2, Integer::sum);
    }

    @GetMapping(value = "/flux-stream-2", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public Flux<Long> fluxWithStream2() {
        return Flux.interval(Duration.ofMillis(2000))
                .delayElements(Duration.ofSeconds(2)).log();
    }

    @GetMapping(value = "/hello/{timeout}")
    public String hello(@PathVariable(name = "timeout") int timeout) throws InterruptedException {
        Thread.sleep(timeout);
        return new Date().toString();
    }

    @GetMapping(value = "/hello-reactive/{timeout}")
    public Flux<String> helloReactive(@PathVariable(name = "timeout") int timeout) throws InterruptedException {
        return Flux.just(new Date().toString())
                .delayElements(Duration.ofMillis(timeout));
    }

    @GetMapping(value = "/hello-reactive-sleep/{timeout}")
    public Flux<String> helloReactiveSleep(@PathVariable(name = "timeout") int timeout) throws InterruptedException {
        Thread.sleep(timeout);
        return Flux.just(new Date().toString());
    }

}
