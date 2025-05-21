package com.wendell.ai2all.repository;

import com.wendell.ai2all.model.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> {

    @Query(value = """
        SELECT * FROM documents 
        ORDER BY embedding <-> CAST(:vector AS vector)
        LIMIT 10
        """, nativeQuery = true)
    List<Document> findSimilarDocuments(@Param("vector") String vector);
}
