<template>
  <a-card :bordered="false">
    <detail-list title="管理员详情">
      <detail-list-item term="id">{{form.id}}</detail-list-item>
      <detail-list-item term="Email">{{form.email}}</detail-list-item>
      <detail-list-item term="创建时间">{{formatDate(form.createTime)}}</detail-list-item>
      <detail-list-item term="更新时间">{{formatDate(form.updateTime)}}</detail-list-item>
      <a-divider style="margin-bottom: 32px"/>
    </detail-list>
    <detail-list title="角色">
      <a-checkbox-group v-model="roleIds" disabled="true">
        <a-row>
          <a-col v-for="role in roles" :span="30" :key="role.roleId">
            <a-checkbox v-model="role.hasRole" :checked="role.hasRole" :value="role.roleId">{{role.roleName}}</a-checkbox>
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
  name: "AdminUserDetail",
  components: {DetailListItem, DetailList},
  mounted() {
    this.getData()
  },
  data() {
    return {
      form: {},
      roles: [],
      roleIds: [],
    }
  },
  methods: {
    getData() {
      const id = this.$route.query.id
      if (id) {
        this.$serviceRequest('/admin_user/detail', 'get', { id: id }).then((res) => {
          const data = res.data;
          if (data.code === 0) {
            this.form = data.data;
          } else {
            this.$message.error(data.message)
          }
        })
      }
      this.$serviceRequest("/role/listAdminUserRoles", 'get', { adminUserId: id }).then(res => {
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
    formatDate(ms) {
      return formatDateTime(ms)
    }
  }
}
</script>

<style scoped>

</style>