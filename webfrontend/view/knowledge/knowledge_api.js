import { request } from '@/utils/utils';

export default class KnowledgeApi {
    constructor() {
        this.baseUrl = `/api/knowledge`;
    }

    async saveKnowledge(knowledge) {
        return await request({
            url: `${this.baseUrl}`,
            method: 'post',
            data: knowledge
        });
    }

    async queryKnowledge(tag) {
        return await request({
            url: `${this.baseUrl}`,
            method: 'get',
            params: { tag }
        });
    }

    async getMatchedTags(text) {
        return await request({
            url: `${this.baseUrl}/tags`,
            method: 'get',
            params: { text }
        });
    }

    async addTagToKnowledge(knowledgeId, tag) {
        return await request({
            url: `${this.baseUrl}/${knowledgeId}/tags`,
            method: 'post',
            data: tag
        });
    }

    async removeTagFromKnowledge(knowledgeId, tagId) {
        return await request({
            url: `${this.baseUrl}/${knowledgeId}/tags/${tagId}`,
            method: 'delete'
        });
    }

    async getKnowledge(knowledgeId) {
        return await request({
            url: `${this.baseUrl}/${knowledgeId}`,
            method: 'get'
        });
    }
};

export const knowledgeApi = new KnowledgeApi();
