<template>
  <a-card :body-style="{padding: '24px 32px'}" :bordered="false">
    <a-form v-model="form">
      <a-form-item
          label="权限名"
          :labelCol="{span: 7}"
          :wrapperCol="{span: 10}"
      >
        <a-input placeholder="权限名" v-model="form.permissionName" />
      </a-form-item>
      <a-form-item
          label="url地址"
          :labelCol="{span: 7}"
          :wrapperCol="{span: 10}"
      >
        <a-input v-model="form.uri" />
      </a-form-item>
      <a-form-item style="margin-top: 24px" :wrapperCol="{span: 10, offset: 7}">
        <a-button type="primary" @click="submit">提交</a-button>
        <a-button style="margin-left: 8px" @click="resetForm">重置</a-button>
      </a-form-item>
    </a-form>
  </a-card>
</template>

<script>
import * as _ from "lodash";
import {serviceRequest} from "@/utils/service-request";
import paths from "@/utils/paths";

export default {
  name: "PermissionForm",
  mounted() {
    this.getData();
  },
  data() {
    return {
      desc: '编辑权限信息',
      originalForm: {},
      form: {},
    }
  },
  methods: {
    getData() {
      const id = this.$route.query.id
      if (id) {
        serviceRequest('/permission/detail', 'get', { id: id }).then(res => {
          this.originalForm = _.cloneDeep(res.data.data)
          this.form = res.data.data
        })
      }
    },
    resetForm() {
      this.form = _.cloneDeep(this.originalForm);
    },
    submit() {
      const params = this.form
      serviceRequest('/permission/update', 'post', params).then(res => {
        const data = res.data;
        if (data.code === 0) {
          this.$router.push(paths.fe.permission.list);
          this.$message.success(data.message, 3);
        } else {
          this.$error({ content: data.message })
        }
      })
    }
  }
}
</script>

<style scoped>

</style>