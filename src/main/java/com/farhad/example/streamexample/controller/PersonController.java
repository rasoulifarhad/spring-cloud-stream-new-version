package com.farhad.example.streamexample.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.farhad.example.streamexample.domain.model.Person;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/persons")
public class PersonController {

    private final StreamBridge streamBridge;
    private final ObjectMapper objectMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createPerson() throws JsonProcessingException {

        final Person person = Person
                                .builder()
                                .name("Farhad")
                                .build();

        final String json = objectMapper.writeValueAsString(person);

        final Message<String> msg = MessageBuilder
                                            .withPayload(json)
                                            .build();

        streamBridge.send("personSource-out-0", msg);
    }
}
