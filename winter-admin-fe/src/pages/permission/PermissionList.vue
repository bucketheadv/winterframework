<template>
  <a-card>
    <a-space class="operator">
      <a-button @click="addNew" type="primary">新建</a-button>
      <a-dropdown>
        <a-menu @click="handleMenuClick" slot="overlay">
          <a-menu-item key="delete">批量删除</a-menu-item>
        </a-menu>
        <a-button>
          更多操作 <a-icon type="down" />
        </a-button>
      </a-dropdown>
    </a-space>
    <standard-table
        :columns="columns"
        :dataSource="dataSource"
        :selectedRows.sync="selectedRows"
        @clear="onClear"
        @change="onChange"
        :pagination="{...pagination, onChange: onPageChange}"
        @selectedRowChange="onSelectChange"
    >
      <div slot="action" slot-scope="{text, record}">
        <a style="margin-right: 8px">
          <a-icon type="edit"/>
          <router-link :to="getEditorPath(record.id)" >编辑</router-link>
        </a>
        <router-link :to="getDetailPath(record.id)" >详情</router-link>
      </div>
    </standard-table>
  </a-card>
</template>

<script>
import StandardTable from "@/components/table/StandardTable";
import {serviceRequest} from "@/utils/service-request";
import paths from "@/utils/paths";
import {formatDateTime} from "@/utils/dateUtil";

const columns = [
  {
    title: 'id',
    dataIndex: 'id',
  },
  {
    title: '权限名',
    dataIndex: 'permissionName',
  },
  {
    title: 'url地址',
    dataIndex: 'uri'
  },
  {
    title: '创建时间',
    dataIndex: 'createTime',
    customRender: (text) => formatDateTime(text)
  },
  {
    title: '更新时间',
    dataIndex: 'updateTime',
    customRender: (text) => formatDateTime(text)
  },
  {
    title: '操作',
    scopedSlots: { customRender: 'action' }
  }
]

export default {
  name: "PermissionList",
  components: {StandardTable},
  mounted() {
    this.getData();
  },
  data() {
    return {
      desc: '权限信息列表',
      columns: columns,
      dataSource: [],
      selectedRows: [],
      pagination: {
        current: 1,
        pageSize: 10,
        total: 0
      }
    }
  },
  methods: {
    getData() {
      serviceRequest("/permission/list", 'GET', {
        current: this.pagination.current,
        size: this.pagination.pageSize
      }).then(res => {
        const data = res.data.data;
        data.forEach(d => d.key = d.id)
        this.dataSource = data;
      });
    },
    onPageChange() {
    },
    onSelectChange() {
    },
    onClear() {
    },
    onChange() {
    },
    addNew () {
      this.$router.push(paths.fe.permission.editor)
    },
    getEditorPath(id) {
      return paths.fe.permission.editor + '?id=' + id
    },
    getDetailPath(id) {
      return paths.fe.permission.detail + '?id=' + id
    },
    handleMenuClick (e) {
      if (e.key === 'delete') {
        this.remove()
      }
    },
    remove() {
      const ids = this.selectedRows.map(i => i.id)
      if (ids && ids.length > 0) {
        this.$confirm({
          title: '您确认要删除吗?',
          okText: '确认',
          cancelText: '取消',
          onOk: () => {
            serviceRequest('/permission/delete', 'post', { ids: ids }).then(res => {
              const data = res.data
              if (data.code === 0) {
                this.getData()
                this.$message.success(data.message, 3)
              } else {
                this.$message.error(data.message, 3)
              }
            })
          }
        })
      }
    },
  }
}
</script>

<style lang="less" scoped>
.operator{
  margin-bottom: 18px;
}
</style>