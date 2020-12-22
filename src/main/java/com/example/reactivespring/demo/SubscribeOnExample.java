package com.example.reactivespring.demo;

import reactor.core.publisher.Flux;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

public class SubscribeOnExample {

    private static final String[] NAMES = new String[] {
            "Anirudh", "Muthu", "Santosh", "Shakiya"
    };

    public static void main(String[] args) throws InterruptedException {
        Scheduler s = Schedulers.elastic();
        Flux<String> flux = Flux.range(0, 4)
                .map(SubscribeOnExample::indexToName);
        flux.subscribeOn(s)
                .subscribe(name -> System.out.println("Finally received " + name + " on thread " + Thread.currentThread().getName()));

        Thread.sleep(2000);
    }

    private static String indexToName(Integer index) {
        System.out.println("Finding a name for " + index + " from thread " + Thread.currentThread().getName());
        return NAMES[index];
    }
}
