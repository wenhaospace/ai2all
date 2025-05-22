package com.wendell.ai2all.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rag")
public class RagController {


    private static final org.slf4j.Logger logger =
            org.slf4j.LoggerFactory.getLogger(RagController.class);

    private final ChatClient chatClient;

    private final ChatModel chatModel;

    private final VectorStore vectorStore;

    public RagController(ChatClient.Builder chatClientBuilder, ChatModel chatModel, VectorStore vectorStore) {
        this.chatClient = chatClientBuilder.build();
        this.chatModel = chatModel;
        this.vectorStore = vectorStore;
    }

    @GetMapping("/QuestionAnswerAdvisorDemo1")
    String generation(String userInput) {
        String userText = "Hello, who are you? ";

        ChatResponse response = ChatClient.builder(chatModel)
                .build().prompt()
                .advisors(new QuestionAnswerAdvisor(vectorStore))
                .user(userText)
                .call()
                .chatResponse();

        return response.getResult().getOutput().getText();
    }
}
