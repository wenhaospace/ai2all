package com.wendell.ai2all.controller;


import com.wendell.ai2all.model.ActorsFilms;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//https://docs.spring.io/spring-ai/reference/api/structured-output-converter.html
@RestController
@RequestMapping("/structured-output")
public class StructuredOutputController {
    private static final org.slf4j.Logger logger =
            org.slf4j.LoggerFactory.getLogger(StructuredOutputController.class);

    private final ChatClient chatClient;

    private final ChatModel chatModel;

    public StructuredOutputController(ChatClient.Builder chatClientBuilder, ChatModel chatModel) {
        this.chatClient = chatClientBuilder.build();
        this.chatModel = chatModel;
    }
    @RequestMapping("/beanOutputConverterDemo")
    ActorsFilms generation(String userInput) {
        ActorsFilms actorsFilms = ChatClient.create(chatModel).prompt()
                .user(u -> u.text("Generate the filmography of 5 movies for {actor}.")
                        .param("actor", "Jakcie Chan"))
                .call()
                .entity(ActorsFilms.class);

        return actorsFilms;
    }


}
