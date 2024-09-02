<template>
  <el-container class="container">
    <el-aside :width="leftPanelWidth + 'px'">
      <TagTree @change="onTagTreeSelected" />
    </el-aside>
    <el-aside v-if="knowledgeList.length > 1" width="200px">
      <el-menu :default-active="activeKnowledgeId" @select="refreshKnowledge">
        <el-menu-item v-for="knowledge in knowledgeList" :key="knowledge.knowledgeId" :index="knowledge.knowledgeId">
          {{ knowledge.title }}
        </el-menu-item>
      </el-menu>
    </el-aside>
    <!--Splitter @resize="(newWidth) => leftPanelWidth = newWidth" /-->
    <el-main>

      <div style="margin-bottom:5px" class="left-right">
        <div style="margin-left:10px;"><el-button type="success" plain icon="el-icon-plus" circle
            @click="onAddKnowledge"></el-button></div>
        <div><el-button type="primary" @click="saveKnowledge" icon="el-icon-check" circle></el-button></div>
      </div>
      <el-form :model="currentKnowledge" label-width="50px" v-if="currentKnowledge">
        <el-form-item label="标题">
          <el-input class="simple-input" v-model="currentKnowledge.title"></el-input>
        </el-form-item>
        <el-form-item label="标签" v-if="currentKnowledge?.knowledgeId">
          <el-tooltip v-for="tag in currentKnowledge.tags" :key="tag.tagId" effect="dark" :content="tag.tag.fullPath"
            placement="top-start">
            <el-tag :key="tag.tagId" style="margin-right:5px;">
              {{ tag.tag.fullPath }}
              <el-button type="text" size="mini" icon="el-icon-close" @click="removeTag(tag.tagId)"></el-button>
            </el-tag>
          </el-tooltip>
          <el-button v-if="!showTagSelection" size="mini" icon="el-icon-plus" type="success" plain circle
            @click="showTagSelection = true;" style="margin-left: 5px;"></el-button>
          <el-input v-if="showTagSelection" placeholder="新标签" v-model="newTag" style="margin-left:10px;">
            <el-select v-model="parentTag" slot="prepend" placeholder="选择父标签" filterable remote
              :remote-method="fetchTags" style="width:200px;">
              <el-option v-for="tag in tagOptions" :key="tag.tagId" :label="tag.tagName" :value="tag"></el-option>
            </el-select>
            <el-button slot="append" icon="el-icon-check" @click="onTagCreate"></el-button>
            <el-button slot="append" icon="el-icon-close" @click="showTagSelection = false; newTag = null;"></el-button>
          </el-input>
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
import TagTree from './tag_tree.vue';

export default {
  components: {
    Splitter, TagTree
  },
  data() {
    return {
      leftPanelWidth: 200,
      knowledgeList: [],
      currentKnowledge: null,
      tagOptions: [],
      activeKnowledgeId: '',
      showTagSelection: false,
      newTag: '',
      parentTag: null
    };
  },
  methods: {
    onTagTreeSelected(tag) {
      this.fetchKnowledge(tag?.tagId);
    },
    async fetchKnowledge(tagId) {
      this.knowledgeList = await knowledgeApi.queryKnowledge(tagId);
      if (this.knowledgeList.length > 0) {
        this.refreshKnowledge(this.knowledgeList[0].knowledgeId);
      }
      else {
        this.activeKnowledgeId = '';
        this.currentKnowledge = null;
      }

    },
    async fetchTags(query, cb) {
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
    onTagSelect(newValue) {
      console.log(newValue);
      this.newTag = newValue.value;
      this.$refs.newTag.focus();
    },
    async onTagCreate() {
      await knowledgeApi.addTagToKnowledge(this.currentKnowledge.knowledgeId, this.newTag ? { parentTagId: this.parentTag?.tagId, tagName: this.newTag, parent: { fullPath: this.parentTag?.fullPath } } : { tagId: this.parentTag?.tagId });
      this.showTagSelection = false;
      this.refreshKnowledge(this.currentKnowledge.knowledgeId);
    },
    async removeTag(tagId) {
      await knowledgeApi.removeTagFromKnowledge(this.currentKnowledge.knowledgeId, tagId);
      this.refreshKnowledge(this.currentKnowledge.knowledgeId);
    },
    async saveKnowledge() {
      this.currentKnowledge = await knowledgeApi.saveKnowledge(this.currentKnowledge);
      this.$message.success('知识项保存成功');
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
  border: 1px solid #dcdcdc;
}

.el-main {
  padding: 7px;
  border: 1px solid #dcdcdc;
}

.el-form-item {
  margin-bottom: 5px;
}

.simple-input ::v-deep .el-input__inner {
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
