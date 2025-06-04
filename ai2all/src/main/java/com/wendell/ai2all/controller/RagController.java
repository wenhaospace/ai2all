package com.wendell.ai2all.controller;


import com.wendell.ai2all.model.UserInputRequest;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.web.bind.annotation.*;

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
    String generation1(@RequestParam("userInput") String userInput) {
        String userText = userInput;

        ChatResponse response = ChatClient.builder(chatModel)
                .build().prompt()
                .advisors(new QuestionAnswerAdvisor(vectorStore))
                .user(userText)
                .call()
                .chatResponse();

        return response.getResult().getOutput().getText();
    }

    @PostMapping("/QuestionAnswerAdvisorDemo2")
    public String generation2(@RequestBody UserInputRequest request) {
        String userText = request.getUserInput();

        ChatResponse response = ChatClient.builder(chatModel)
                .build().prompt()
                .advisors(new QuestionAnswerAdvisor(vectorStore))
                .user(userText)
                .call()
                .chatResponse();

        return response.getResult().getOutput().getText();
    }
}
