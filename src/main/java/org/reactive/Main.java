package org.reactive;

import reactor.core.publisher.Mono;

import java.time.Duration;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Mono<String> req0 = Mono.delay(Duration.ofSeconds(5)).flatMap(i -> Mono.just("hello0"));
        Mono<String> req1 = Mono.just("hello1");
        Mono<String> req2 = Mono.just("hello2");
        //Mono<String> req3 = handleWithBug("hello3");
        Mono<String> req3 = Mono.defer(() -> handleWithBug("hello3"))
                .doOnError(err -> err.printStackTrace())
                .retry(3);

        req1.flux().mergeWith(req0).mergeWith(req1).mergeWith(req2).mergeWith(req3)
                .collectList()
                .doOnNext(System.out::println)
                .doOnError(err -> err.printStackTrace())
                .subscribe();

        Thread.sleep(10000);
    }

    static int i = 0;
    static Mono<String> handleWithBug(String val){
        if(i == 0){
            i++;
            return Mono.error(new RuntimeException("Failed"));
        }
        else return Mono.just(val);
    }
}