package com.wendell.ai2all.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("chat")
public class ChatController {

    private static final org.slf4j.Logger logger =
            org.slf4j.LoggerFactory.getLogger(ChatController.class);
    private final ChatClient chatClient;

    public ChatController(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    @GetMapping("/ai")
    String generation(String userInput) {
        userInput = "Hello, who are you? ";
        var response = this.chatClient.prompt()
                .user(userInput)
                .call()
                .content();
        logger.info("Response: {}", response);

        return response;
    }
}
