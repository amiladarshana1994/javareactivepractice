package org.reactive;

import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;

public class ContinuesStream {
    public static void main(String[] args) throws InterruptedException {
        // continues stream
        Flux fooStream = Flux
                .interval(Duration.ofSeconds(1))
                .map(i -> "Foo "+i);

        // take 10 elements from continues stream
        Flux fooStreamOf10 = Flux
                .interval(Duration.ofSeconds(1))
                .map(i -> "Foo "+i)
                .take(10);

        // continues stream
        Flux helloStream = Flux
                .interval(Duration.ofSeconds(1))
                .map(i -> "Hello "+i);

        // continues stream on seperate thread
        Flux helloStreamPara = Flux
                .interval(Duration.ofSeconds(1))
                .publishOn(Schedulers.parallel())
                .map(i -> "Hello "+i+" on "+ Thread.currentThread().getName());

        // merge and stream through 2 streams
        //fooStream.mergeWith(helloStream).doOnNext(System.out::println).subscribe();

        // merge and stream through 2 streams
        //fooStreamOf10.mergeWith(helloStream).doOnNext(System.out::println).subscribe();

        // use seperate thread to publish data
        fooStreamOf10.mergeWith(helloStreamPara).doOnNext(System.out::println).subscribe();
        Thread.sleep(100000);
    }
}
