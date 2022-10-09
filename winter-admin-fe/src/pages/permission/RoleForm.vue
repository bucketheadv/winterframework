<template>
  <a-card :body-style="{padding: '24px 32px'}" :bordered="false">
    <a-form refs="form" @onsubmit="submit">
      <a-form-item
          label="角色名"
          :labelCol="{span: 7}"
          :wrapperCol="{span: 10}"
      >
        <a-input placeholder="角色名" v-model="form.roleName" />
      </a-form-item>
      <a-form-item
        label="是否管理员"
        :labelCol="{span: 7}"
        :wrapperCol="{span: 10}"
      >
        <a-checkbox value="true" v-model="form.isSuperAdmin">是</a-checkbox>
      </a-form-item>
      <a-form-item
        label="权限列表"
        :labelCol="{span: 7}"
        :wrapperCol="{span: 10}"
      >
        <a-checkbox-group v-model="permissionIds">
          <a-row>
            <a-col v-for="permission in permissions" :span="30" :key="permission.permissionId">
              <a-checkbox v-model="permission.hasPerm" :checked="permission.hasPerm" :value="permission.permissionId">{{permission.permissionName}}</a-checkbox>
            </a-col>
          </a-row>
        </a-checkbox-group>
      </a-form-item>
      <a-form-item style="margin-top: 24px" :wrapperCol="{span: 10, offset: 7}">
        <a-button type="primary" @click="submit">提交</a-button>
        <a-button style="margin-left: 8px" @click="resetForm">重置</a-button>
      </a-form-item>
    </a-form>
  </a-card>
</template>

<script>
import * as _ from 'lodash'
import {serviceRequest} from "@/utils/service-request";
import paths from "@/utils/paths";
export default {
  name: "RoleForm",
  mounted() {
    this.getData()
  },
  data() {
    return {
      desc: '编辑角色信息',
      originalForm: {},
      form: {},
      permissionIds: [],
      permissions: [],
    }
  },
  methods: {
    async getData() {
      const id = this.$route.query.id
      if (id) {
        await serviceRequest('/role/detail', 'get', { id: id }).then(res => {
          this.originalForm = _.cloneDeep(res.data.data)
          this.form = res.data.data
        })
      }
      await serviceRequest("/permission/listRolePermissions", 'get', { roleId: id }).then(res => {
        this.permissions = res.data.data
        const permissionIds = []
        this.permissions.forEach(p => {
          if (p.hasPerm) {
            permissionIds.push(p.permissionId)
          }
        })
        this.permissionIds = permissionIds;
      })
    },
    resetForm() {
      this.form = _.cloneDeep(this.originalForm);
    },
    submit(e) {
      e.preventDefault()
      const params = this.form
      params.permissionIds = this.permissionIds
      serviceRequest('/role/update', 'post', params).then((res) => {
        if (res.data.code !== 0) {
          this.$error({content: res.data.message})
          return
        }
        this.$router.push(paths.fe.role.list);
        this.$message.success(res.data.message, 3);
      })
    }
  }
}
</script>

<style scoped>

</style>