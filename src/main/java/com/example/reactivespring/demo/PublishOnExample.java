package com.example.reactivespring.demo;

import reactor.core.publisher.Flux;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

public class PublishOnExample {

    private static final String[] NAMES = new String[] {
            "Anirudh", "Muthu", "Santosh", "Shakiya"
    };

    public static void main(String[] args) {
        Scheduler s = Schedulers.elastic();
        Flux<String> flux = Flux.range(0, 4)
                .map(PublishOnExample::indexToName);
        flux.publishOn(s).subscribe(name -> System.out.println("Finally received " + name + " on thread " + Thread.currentThread().getName()));
    }

    private static String indexToName(Integer index) {
        System.out.println("Finding a name for " + index + " from thread " + Thread.currentThread().getName());
        return NAMES[index];
    }
}
