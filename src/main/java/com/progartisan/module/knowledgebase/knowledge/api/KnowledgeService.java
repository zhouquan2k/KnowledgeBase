package com.progartisan.module.knowledgebase.knowledge.api;

import com.progartisan.module.knowledgebase.knowledge.model.Knowledge;
import com.progartisan.module.knowledgebase.knowledge.model.Tag;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RequestMapping("/api/knowledge")
public interface KnowledgeService {

    @PostMapping
    Knowledge saveKnowledge(@RequestBody Knowledge knowledge);

    @GetMapping("/{knowledgeId}")
    Knowledge getKnowledge(@PathVariable String knowledgeId);

    @GetMapping
    List<Knowledge> queryKnowledge(@RequestParam(required = false) String tag);

    @GetMapping("/tags")
    List<Tag> getMatchedTags(@RequestParam String text);

    @PostMapping("/tags")
    Tag createTag(@RequestBody Tag tag);

    @PostMapping("/{knowledgeId}/tags")
    Knowledge addTagToKnowledge(@PathVariable String knowledgeId, @RequestBody Tag tag);

    @DeleteMapping("/{knowledgeId}/tags/{tagId}")
    Knowledge removeTagFromKnowledge(@PathVariable String knowledgeId, @PathVariable String tagId);
}
