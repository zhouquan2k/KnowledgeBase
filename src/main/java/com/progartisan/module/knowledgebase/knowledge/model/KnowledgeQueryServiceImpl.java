package com.progartisan.module.knowledgebase.knowledge.model;

import com.progartisan.component.common.Util;
import com.progartisan.component.framework.Query;
import com.progartisan.component.framework.Service;
import com.progartisan.module.knowledgebase.knowledge.api.KnowledgeQueryService;
import com.progartisan.module.knowledgebase.knowledge.infra.KnowledgeMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;

@Service(value = "知识查询", name = "knowledgeQuery")
@Named
@Slf4j
@RequiredArgsConstructor
public class KnowledgeQueryServiceImpl implements KnowledgeQueryService {

    private final KnowledgeMapper knowledgeMapper;

    @Override
    @Query
    public List<Knowledge> queryKnowledgeByTag(String tagName, boolean needParent) {
        List<Tag> tags = knowledgeMapper.getTagsByName(tagName);
        Util.check(tags.size() == 1, "tag not found or more than one found: %s (%d)", tagName, tags.size());
        List<String> tagIds = new ArrayList<>();
        if (needParent) {
            getParentIds(tags.get(0), tagIds);
        }
        tagIds.add(tags.get(0).getTagId());
        List<Knowledge> result = new ArrayList<>();
        tagIds.forEach(tagId -> result.addAll(knowledgeMapper.queryKnowledgeByTag(tagId)));
        return result;
    }

    private void getParentIds(Tag currentTag, List<String> tagIds) {
        if (Util.isEmpty(currentTag.getParentTagId())) {
            return;
        }
        tagIds.add(currentTag.getParentTagId());
        Tag parentTag = knowledgeMapper.getTag(currentTag.getParentTagId());
        getParentIds(parentTag, tagIds);
    }

    @Override
    @Query
    public String getTagHtml(String id) {
        // var tag = knowledgeMapper.getTag(id);
        // Util.check(tag != null, "tag not found: %s", id);
        StringBuilder htmlContent = new StringBuilder();
        // 获取所有子标签
        List<Tag> childrenTags = knowledgeMapper.getChildrenTags(id);
        var knowledge = knowledgeMapper.getKnowledgeByTitleTagId(id);
        htmlContent.append("<html><body>");
        // 获取所有这些子标签的 知识点
        if (!childrenTags.isEmpty()) {
            htmlContent.append("<ul>");
            childrenTags.forEach(child -> {
                htmlContent.append("<li><a href=\"/api/knowledge/query/tag/")
                        .append(child.getTagId())
                        .append(".html\">")
                        .append(child.getTagName())
                        .append("</a></li>");
            });
            htmlContent.append("</ul>");
        }
        if (knowledge != null) {
            htmlContent.append("<pre>");
            htmlContent.append(knowledge.getContent());
            htmlContent.append("</pre>");
        }
        htmlContent.append("</body></html>");
        return htmlContent.toString();
    }
}
