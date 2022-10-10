<template>
  <a-card :bordered="false">
    <detail-list title="角色详情">
      <detail-list-item term="id">{{form.id}}</detail-list-item>
      <detail-list-item term="名称">{{form.roleName}}</detail-list-item>
      <detail-list-item term="是否超管">{{form.isSuperAdmin ? '是' : '否'}}</detail-list-item>
      <detail-list-item term="创建时间">{{formatDate(form.createTime)}}</detail-list-item>
      <detail-list-item term="更新时间">{{formatDate(form.updateTime)}}</detail-list-item>
      <a-divider style="margin-bottom: 32px"/>
    </detail-list>
    <detail-list title="权限">
      <a-checkbox-group v-model="permissionIds" disabled="true">
        <a-row>
          <a-col v-for="permission in permissions" :span="30" :key="permission.permissionId">
            <a-checkbox v-model="permission.hasPerm" :checked="permission.hasPerm" :value="permission.permissionId">{{permission.permissionName}}</a-checkbox>
          </a-col>
        </a-row>
      </a-checkbox-group>
    </detail-list>
  </a-card>
</template>

<script>
import DetailList from "@/components/tool/DetailList";
import {formatDateTime} from "@/utils/dateUtil";
const DetailListItem = DetailList.Item

export default {
  name: "RoleDetail",
  components: {DetailListItem, DetailList},
  mounted() {
    this.getData()
  },
  data() {
    return {
      form: {},
      permissions: [],
      permissionIds: [],
    }
  },
  methods: {
    getData() {
      const id = this.$route.query.id
      this.serviceRequest('/role/detail', 'get', { id: id }).then((res) => {
        const data = res.data;
        if (data.code === 0) {
          this.form = data.data;
        } else {
          this.$message.error(data.message)
        }
      })
      this.serviceRequest("/permission/listRolePermissions", 'get', { roleId: id }).then(res => {
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
    formatDate(ms) {
      return formatDateTime(ms)
    }
  }
}
</script>

<style scoped>

</style>