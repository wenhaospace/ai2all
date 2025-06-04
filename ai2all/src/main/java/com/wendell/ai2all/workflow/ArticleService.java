package com.wendell.ai2all.workflow;

import com.wendell.ai2all.controller.ChatController;
import jakarta.annotation.PostConstruct;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ArticleService {
    private static final org.slf4j.Logger logger =
            org.slf4j.LoggerFactory.getLogger(ArticleService.class);
    private final ChatModel chatModel;

    public ArticleService(ChatModel chatModel) {
        this.chatModel = chatModel;
    }

    @PostConstruct
    public void main_executor(){
        String article = generate_article();

        String vocabularies = extract_vocabulary(article);


    }

    public String generate_article() {
        String language = "English";
        String topic = "Daily";
        PromptTemplate promptTemplate = new PromptTemplate(" 生成一篇关于{topic}的英语短文，适合英语学习者阅读。\n" +
                "    要求：\n" +
                "    1. 长度约100词\n" +
                "    2. 包含5-8个重点词汇\n" +
                "    3. 语言生动有趣\n" +
                "    4. 结构清晰，有引言、主体和结论");

        Prompt prompt = promptTemplate.create(Map.of("topic", topic));

        var response = this.chatModel
                .call(prompt)
                .getResult();
        logger.info("Generate_article response: {}", response);

        return response.getOutput().getText();
    }

    public String extract_vocabulary(String text){
        PromptTemplate promptTemplate = new PromptTemplate(
                "从以下文本中提取5-8个重要词汇和短语，并提供简明释义和例句：\n" +
                        "    {text}\n" +
                        "    \n" +
                        "    格式要求：\n" +
                        "    - 每个词汇一行\n" +
                        "    - 格式：单词|词性|释义|例句"
        );

        Prompt prompt = promptTemplate.create(Map.of("text", text));

        var response = this.chatModel
                .call(prompt)
                .getResult();
        logger.info("Extract_vocabulary response: {}", response);

        return response.getOutput().getText();
    }

//    public String generate_humorous_dialogue(){
//
//    }
}
