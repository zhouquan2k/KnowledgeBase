package com.progartisan.module.knowledgebase.knowledge.api;

import com.progartisan.module.knowledgebase.knowledge.model.Knowledge;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/knowledge")
public interface KnowledgeQueryService {

    @GetMapping("/query/tag")
    List<Knowledge> queryKnowledgeByTag(@RequestParam String tagName, @RequestParam(required = false, defaultValue = "false") boolean needParent);
}
