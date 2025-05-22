package com.wendell.ai2all.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.Generation;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.chat.prompt.SystemPromptTemplate;
import org.springframework.ai.converter.StructuredOutputConverter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("chat")
public class ChatController {

    private static final org.slf4j.Logger logger =
            org.slf4j.LoggerFactory.getLogger(ChatController.class);
    private final ChatClient chatClient;

    private final ChatModel chatModel;

    public ChatController(ChatClient.Builder chatClientBuilder, ChatModel chatModel) {
        this.chatClient = chatClientBuilder.build();
        this.chatModel = chatModel;
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
    @GetMapping("/promptTemplate1")
    String generationByPromptTemplateDemo1(String userInput) {
        String language = "English";
        String topic = "Business English";
        PromptTemplate promptTemplate = new PromptTemplate("Tell me a  {language} word about {topic}");

        Prompt prompt = promptTemplate.create(Map.of("language", language, "topic", topic));

        var response =this.chatModel
                .call(prompt)
                .getResult();
        logger.info("Response: {}", response);

        return response.getOutput().getText();
    }

    @GetMapping("/promptTemplate2")
    String generationByPromptTemplateDemo2(String userInput) {
        String userText = """
        Tell me about three famous pirates from the Golden Age of Piracy and why they did.
        Write at least a sentence for each pirate.
        """;

        Message userMessage = new UserMessage(userText);

        String systemText = """
          You are a helpful AI assistant that helps people find information.
          Your name is {name}
          You should reply to the user's request with your name and also in the style of a {voice}.
          """;

        String name = "AI Assistant";
        String voice = "friendly";
        SystemPromptTemplate systemPromptTemplate = new SystemPromptTemplate(systemText);
        Message systemMessage = systemPromptTemplate.createMessage(Map.of("name", name, "voice", voice));

        Prompt prompt = new Prompt(List.of(userMessage, systemMessage));

        List<Generation> response = chatModel.call(prompt).getResults();
        logger.info("Response: {}", response);
        StringBuilder result = new StringBuilder();
        for (Generation generation : response) {
            result.append(generation.getOutput().getText()).append("\n");
        }
        return result.toString();
    }
}
