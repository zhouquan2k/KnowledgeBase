<template>
  <el-container class="container">
    <el-aside :width="leftPanelWidth + 'px'">
      <el-button type="success" plain icon="el-icon-plus" @click="onAddKnowledge"></el-button>
      <el-input placeholder="搜索知识项" v-model="searchText" @input="fetchKnowledge"
        style="width:120px;margin-left:5px"></el-input>
      <el-menu :default-active="activeKnowledgeId" @select="refreshKnowledge">
        <el-menu-item v-for="knowledge in knowledgeList" :key="knowledge.knowledgeId" :index="knowledge.knowledgeId">
          {{ knowledge.title }}
        </el-menu-item>
      </el-menu>
    </el-aside>
    <Splitter @resize="(newWidth) => leftPanelWidth = newWidth" />
    <el-main>
      <el-form :model="currentKnowledge" label-width="50px">
        <div style="text-align: right;margin-bottom:5px">
          <el-button type="primary" @click="saveKnowledge" icon="el-icon-check" circle></el-button>
        </div>
        <el-form-item label="标题">
          <el-input v-model="currentKnowledge.title"></el-input>
        </el-form-item>
        <el-form-item label="标签">
          <el-tag v-for="tag in currentKnowledge.tags" :key="tag.tagId" style="margin-right:5px;">
            {{ tag.tag.tagName }}
            <el-button type="text" size="mini" icon="el-icon-close" @click="removeTag(tag.tagId)"></el-button>
          </el-tag>
          <el-button v-if="!showTagSelection" size="mini" icon="el-icon-plus" type="success" plain circle
            @click="showTagSelection = true;" style="margin-left: 5px;"></el-button>
          <el-select ref="tagSelect" v-if="showTagSelection" v-model="newTag" filterable remote allow-create
            default-first-option :remote-method="fetchTags" placeholder=" " @change="onSelectTag"
            @blur="showTagSelection = false">
            <el-option v-for="tag in tagOptions" :key="tag.tagId" :label="tag.tagName" :value="tag"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="内容">
          <el-input type="textarea" v-model="currentKnowledge.content" :rows="25"></el-input>
        </el-form-item>
      </el-form>
    </el-main>
  </el-container>
</template>

<script>
import { knowledgeApi } from './knowledge_api';
import Splitter from '@/components/splitter.vue';

export default {
  components: {
    Splitter
  },
  data() {
    return {
      leftPanelWidth: 200,
      knowledgeList: [],
      currentKnowledge: {},
      tagOptions: [],
      searchText: '',
      activeKnowledgeId: '',
      showTagSelection: false,
      newTag: ''
    };
  },
  watch: {
    showTagSelection(newVal) {
      if (newVal) {
        this.$nextTick(() => {
          this.$refs.tagSelect.focus();
        });
      }
    },
  },
  methods: {
    async fetchKnowledge() {
      this.knowledgeList = await knowledgeApi.queryKnowledge(this.searchText);
    },
    async fetchTags(query) {
      if (query !== '') {
        this.tagOptions = await knowledgeApi.getMatchedTags(query);
      } else {
        this.tagOptions = [];
      }
    },
    onAddKnowledge() {
      this.currentKnowledge = { tags: [] };
      this.activeKnowledgeId = null;
    },
    async refreshKnowledge(knowledgeId) {
      this.currentKnowledge = await knowledgeApi.getKnowledge(knowledgeId);
      this.activeKnowledgeId = knowledgeId;
    },
    async onSelectTag(selectedValue) {
      await knowledgeApi.addTagToKnowledge(this.currentKnowledge.knowledgeId, (selectedValue.tagId) ? selectedValue : { tagName: selectedValue });
      this.showTagSelection = false;
      this.refreshKnowledge(this.currentKnowledge.knowledgeId);
    },
    async removeTag(tagId) {
      await knowledgeApi.removeTagFromKnowledge(this.currentKnowledge.knowledgeId, tagId);
      this.refreshKnowledge(this.currentKnowledge.knowledgeId);
    },
    async saveKnowledge() {
      await knowledgeApi.saveKnowledge(this.currentKnowledge);
      this.$message.success('知识项保存成功');
      this.fetchKnowledge();
    }
  },
  created() {
    this.fetchKnowledge();
  }
};
</script>

<style scoped>
.container {
  display: flex;
  height: 100vh;
  width: 100%;
}

.el-aside {
  background-color: #ffffff;
  padding: 0px 0px;
}

.el-main {
  padding: 7px;
}

.el-form-item {
  margin-bottom: 5px;
}

::v-deep .el-input__inner {
  border-left: none !important;
  border-right: none !important;
  border-top: none !important;
}

.el-menu-item {
  line-height: 40px;
  height: 40px;
}

.el-button--mini.is-circle {
  padding: 3px;
}

.el-tag .el-button--mini {
  padding: 0px;
}
</style>
