package com.wendell.ai2all.pipeline;


import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.markdown.MarkdownDocumentReader;
import org.springframework.ai.reader.markdown.config.MarkdownDocumentReaderConfig;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
class IngestionPipeline {

    private static final Logger logger = LoggerFactory.getLogger(IngestionPipeline.class);

    private final VectorStore vectorStore;

    @Value("classpath:documents/story1.md")
    Resource file1;

    @Value("classpath:documents/story2.md")
    Resource file2;

    @Value("classpath:documents/deepseek_finacial_note.md")
    Resource file3;

    @Value("classpath:documents/reclaim_mock_data/Template_RAG.md")
    Resource reclaimFile;

    IngestionPipeline(VectorStore vectorStore) {
        this.vectorStore = vectorStore;
    }

//    @PostConstruct
    void run() {
        List<Document> documents = new ArrayList<>();

        logger.info("Loading .md files as Documents");
//        var markdownReader1 = new MarkdownDocumentReader(file1, MarkdownDocumentReaderConfig.builder()
//                .withAdditionalMetadata("location", "North Pole")
//                .build());
//        documents.addAll(markdownReader1.get());
//        var markdownReader2 = new MarkdownDocumentReader(file2, MarkdownDocumentReaderConfig.builder()
//                .withAdditionalMetadata("location", "Italy")
//                .build());
//        documents.addAll(markdownReader2.get());

//        var markdownReader3 = new MarkdownDocumentReader(file3, MarkdownDocumentReaderConfig.builder()
//                .withAdditionalMetadata("Type", "Financial Note")
//                .build());
//        documents.addAll(markdownReader3.get());
//
//        logger.info("Creating and storing Embeddings from Documents");
//        vectorStore.add(new TokenTextSplitter().split(documents));

        var markdownReader4 = new MarkdownDocumentReader(reclaimFile, MarkdownDocumentReaderConfig.builder()
                .withAdditionalMetadata("Type", "Financial Note")
                .build());
        documents.addAll(markdownReader4.get());

        logger.info("Creating and storing Embeddings from Documents");
        vectorStore.add(new TokenTextSplitter().split(documents));

//        List<Document> documents = List.of(
//                new Document("Spring AI rocks!! Spring AI rocks!! Spring AI rocks!! Spring AI rocks!! Spring AI rocks!!", Map.of("meta1", "meta1")),
//                new Document("The World is Big and Salvation Lurks Around the Corner"),
//                new Document("You walk forward facing the past and you turn back toward the future.", Map.of("meta2", "meta2")));
//
//        // Add the documents to PGVector
//        vectorStore.add(documents);
//
//        // Retrieve documents similar to a query
//        List<Document> results = this.vectorStore.similaritySearch(SearchRequest.builder().query("Spring").topK(5).build());
//
//        logger.info("Found {} results", results.size());
//        for (Document result : results) {
//            logger.info("Result: {}", result.getFormattedContent());
//        }
    }

}
