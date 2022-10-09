<template>
  <a-card :bordered="false">
    <detail-list title="权限详情">
      <detail-list-item term="id">{{form.id}}</detail-list-item>
      <detail-list-item term="名称">{{form.permissionName}}</detail-list-item>
      <detail-list-item term="uri">{{form.uri}}</detail-list-item>
      <detail-list-item term="创建时间">{{formatDate(form.createTime)}}</detail-list-item>
      <detail-list-item term="更新时间">{{formatDate(form.updateTime)}}</detail-list-item>
    </detail-list>
    <a-divider style="margin-bottom: 32px"/>
  </a-card>
</template>

<script>
import DetailList from '../../components/tool/DetailList'
import {serviceRequest} from "@/utils/service-request";
import {formatDateTime} from "@/utils/dateUtil";

const DetailListItem = DetailList.Item
export default {
  name: "PermissionDetail",
  components: {DetailListItem, DetailList},
  mounted() {
    this.getData()
  },
  data() {
    return {
      form: {}
    }
  },
  methods: {
    getData() {
      serviceRequest('/permission/detail', 'get', { id: this.$route.query.id }).then((res) => {
        const data = res.data
        if (data.code === 0) {
          this.form = data.data
        } else {
          this.$message.error(data.message)
        }
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