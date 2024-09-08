package com.progartisan.module.knowledgebase.knowledge.api;

import com.progartisan.module.knowledgebase.knowledge.model.Knowledge;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/knowledge")
public interface KnowledgeQueryService {

    @GetMapping("/query/tag")
    List<Knowledge> queryKnowledgeByTag(@RequestParam String tagName, @RequestParam(required = false, defaultValue = "false") boolean needParent);

    @GetMapping(value = "/query/tag/{id}.html", produces = MediaType.TEXT_HTML_VALUE)
    String getTagHtml(@PathVariable String id);
}
