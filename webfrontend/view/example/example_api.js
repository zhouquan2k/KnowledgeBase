import { request } from '@/utils/utils';

export default class ExampleApi {
    constructor() {
        this.baseUrl = `/examples`;
    }

    async getExamples(params) {
        return await request({
            url: `${this.baseUrl}`,
            method: 'get',
            params
        });
    }

    async getExample(exampleId) {
        return await request({
            url: `${this.baseUrl}/${exampleId}`,
            method: 'get'
        });
    }

    async saveExample(example) {
        return await request({
            url: `${this.baseUrl}`,
            method: 'post',
            data: example
        });
    }

    async deleteExample(exampleId) {
        return await request({
            url: `${this.baseUrl}/${exampleId}`,
            method: 'delete'
        });
    }

    async getAssistants() {
        return await request({
            url: `${this.baseUrl}/assistants`,
            method: 'get'
        });
    }
};

export const exampleApi = new ExampleApi();
