package com.example.reactivespring;

import reactor.core.publisher.ConnectableFlux;
import reactor.core.publisher.Flux;

import java.io.InputStreamReader;
import java.util.concurrent.atomic.AtomicInteger;

public class Demo {

    private static final String[] NAMES = new String[] {
      "Anirudh", "Muthu", "Santosh", "Shakiya"
    };

    public static void main(String[] args) {
        // Nothing happens until you subscribe
        Flux<String> flux = Flux.range(0, 4)
        .map(Demo::indexToName);

        // Now subscribe to flux. Until we subscribe to a flux/mono, we are just creating an asynchronous pipeline
        flux.subscribe();

        // if we want to do something with data
        flux.subscribe(item -> System.out.println("Received " + item), Throwable::printStackTrace);

        System.out.println("==================================================================================");

        // cold and hot observable
        flux.subscribe(item -> System.out.println("Received first " + item), Throwable::printStackTrace);
        flux.subscribe(item -> System.out.println("Received second " + item), Throwable::printStackTrace);

        AtomicInteger count = new AtomicInteger();
        final Flux<Integer> generate = Flux.generate(sink -> sink.next(count.incrementAndGet()));

        generate.take(4).subscribe(item -> System.out.println("first received " + item));
        generate.take(4).subscribe(item -> System.out.println("second received " + item));

        // convert cold flux to hot
        final ConnectableFlux<String> cflux = flux.publish();
        cflux.subscribe(item -> System.out.println("Received first " + item), Throwable::printStackTrace);
        cflux.connect();
        cflux.subscribe(item -> System.out.println("Received second " + item), Throwable::printStackTrace);


    }

    private static String indexToName(Integer index) {
//        System.out.println("Finding a name for " + index + " from thread " + Thread.currentThread().getName());
        return NAMES[index];
    }
}
