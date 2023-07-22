package com.submarine.submarineWolf.submarineWolf.service;

import com.submarine.submarineWolf.submarineWolf.config.Config;
import com.submarine.submarineWolf.submarineWolf.core.WolfInfo;
import com.submarine.submarineWolf.submarineWolf.service.dto.MessageDTO;
import lombok.extern.java.Log;
import org.apache.catalina.core.ApplicationContext;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.time.LocalTime;
import java.util.Scanner;

@Component
@Log
public class Messaging implements InitializingBean {

    @Autowired
    @Qualifier("WolfInfo")
    private WolfInfo wolfInfo;

    public void subscribe() {
        WebClient client = WebClient.create("http://localhost:8080/api/v1");
        ParameterizedTypeReference<ServerSentEvent<String>> type
                = new ParameterizedTypeReference<ServerSentEvent<String>>() {};

        Flux<ServerSentEvent<String>> eventStream = client.get()
                .uri("/messaging")
                .retrieve()
                .bodyToFlux(type);

        eventStream.subscribe(
                content -> System.out.println(LocalTime.now() + " " + content.event() + " " + content.data()),
                error -> log.info("Error receiving " + error),
                () -> log.info("THIS IS THE END... BEAUTIFUL FRIEND THE END..."));
        System.out.println("ENTER YOUR MESSAGE");
    }

    public void send(String message) {
        WebClient client = WebClient.create(wolfInfo.getPath() +"/api/v1");
        client.post()
                .uri("/messaging")
                .body(BodyInserters.fromValue(MessageDTO.builder()
                        .message(message)
                        .author(wolfInfo.getWolfName())
                        .build()))
                .retrieve()
                .bodyToMono(Void.class)
                .subscribe();
    }

    public void scanner() {
        Scanner in = new Scanner(System.in);
        while (true) {
            String s = in.nextLine();
            send(s);
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        subscribe();
        scanner();
    }
}
