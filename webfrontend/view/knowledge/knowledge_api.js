import { request } from '@/utils/utils';

export default class KnowledgeApi {
    constructor() {
        this.baseUrl = `/api/knowledge`;
    }

    async saveKnowledge(knowledge, currentTagId) {
        return await request({
            url: `${this.baseUrl}`,
            method: 'post',
            data: knowledge,
            params: { currentTagId }
        });
    }

    async queryKnowledge(tagId) {
        return await request({
            url: `${this.baseUrl}`,
            method: 'get',
            params: { tagId }
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

    async getTagTree() {
        return await request({
            url: `${this.baseUrl}/tags/tree`,
            method: 'get'
        });
    }

    async updateTag(tagId, tag) {
        return await request({
            url: `${this.baseUrl}/tags/${tagId}`,
            method: 'put',
            data: tag
        });
    }

    async deleteTag(tagId) {
        return await request({
            url: `${this.baseUrl}/tags/${tagId}`,
            method: 'delete'
        });
    }

    async moveTag(tagId, parentTagId) {
        return await request({
            url: `${this.baseUrl}/tags/${tagId}/move`,
            method: 'put',
            data: { parentTagId }
        });
    }

    async deleteKnowledge(knowledgeId) {
        return await request({
            url: `${this.baseUrl}/${knowledgeId}`,
            method: 'delete'
        });
    }
};

export const knowledgeApi = new KnowledgeApi();
