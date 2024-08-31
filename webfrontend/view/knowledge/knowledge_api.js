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

    async createTag(tag) {
        return await request({
            url: `${this.baseUrl}/tags`,
            method: 'post',
            data: tag
        });
    }
};

export const knowledgeApi = new KnowledgeApi();
