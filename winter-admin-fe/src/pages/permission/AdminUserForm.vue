<template>
  <a-card :body-style="{padding: '24px 32px'}" :bordered="false">
    <a-form refs="form" @onsubmit="submit">
      <a-form-item
          label="Email"
          :labelCol="{span: 7}"
          :wrapperCol="{span: 10}"
      >
        <a-input placeholder="邮箱地址" v-model="form.email" />
      </a-form-item>
      <a-form-item
          label="密码"
          :labelCol="{span: 7}"
          :wrapperCol="{span: 10}"
      >
        <a-input type="password" placeholder="密码" v-model="form.password" />
      </a-form-item>
      <a-form-item
          label="角色列表"
          :labelCol="{span: 7}"
          :wrapperCol="{span: 10}"
      >
        <a-checkbox-group v-model="roleIds">
          <a-row>
            <a-col v-for="role in roles" :span="30" :key="role.roleId">
              <a-checkbox v-model="role.hasRole" :checked="role.hasRole" :value="role.roleId">{{role.roleName}}</a-checkbox>
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
import * as _ from "lodash";
import paths from "@/utils/paths";

export default {
  name: "AdminUserForm",
  mounted() {
    this.getData()
  },
  data() {
    return {
      desc: '编辑角色信息',
      originalForm: {},
      form: {},
      roleIds: [],
      roles: [],
    }
  },
  methods: {
    async getData() {
      const id = this.$route.query.id
      if (id) {
        await this.$serviceRequest('/admin_user/detail', 'get', { id: id }).then(res => {
          this.originalForm = _.cloneDeep(res.data.data)
          this.form = res.data.data
        })
      }
      await this.$serviceRequest("/role/listAdminUserRoles", 'get', { adminUserId: id }).then(res => {
        this.roles = res.data.data
        const roleIds = []
        this.roles.forEach(p => {
          if (p.hasRole) {
            roleIds.push(p.roleId)
          }
        })
        this.roleIds = roleIds;
      })
    },
    resetForm() {
      this.form = _.cloneDeep(this.originalForm);
    },
    submit(e) {
      e.preventDefault()
      const params = this.form
      params.roleIds = this.roleIds
      this.$serviceRequest('/admin_user/update', 'post', params).then((res) => {
        if (res.data.code !== 0) {
          this.$error({content: res.data.message})
          return
        }
        this.$router.push(paths.fe.adminUser.list);
        this.$message.success(res.data.message, 3);
      })
    }
  }
}
</script>

<style scoped>

</style>