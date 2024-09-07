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

    async getTagTree(project) {
        return await request({
            url: `${this.baseUrl}/tags/${project}/tree`,
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

    async moveTag(tagId, moveType, refTagId) {
        return await request({
            url: `${this.baseUrl}/tags/${tagId}/move/${moveType[0].toUpperCase() + moveType.slice(1)}`,
            method: 'put',
            data: { tagId: refTagId }
        });
    }

    async deleteKnowledge(knowledgeId) {
        return await request({
            url: `${this.baseUrl}/${knowledgeId}`,
            method: 'delete'
        });
    }

    async importDocuments(projectName) {
        return await request({
            url: `${this.baseUrl}/documents/${projectName}/import`,
            method: 'post',
        });
    }

    async getDocuments(projectName, tagName) {
        return await request({
            url: `${this.baseUrl}/documents/${projectName}`,
            method: 'get',
            params: { tagName }
        });
    }

    async getDocument(documentId) {
        return await request({
            url: `${this.baseUrl}/documents/id/${documentId}`,
            method: 'get'
        });
    }

    async getProjects() {
        return await request({
            url: `${this.baseUrl}/projects`,
            method: 'get'
        });
    }

    async getProject(projectName) {
        return await request({
            url: `${this.baseUrl}/projects/${projectName}`,
            method: 'get'
        });
    }
};

export const knowledgeApi = new KnowledgeApi();
