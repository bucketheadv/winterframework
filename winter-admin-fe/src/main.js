import Vue from 'vue'
import App from './App.vue'
import {initRouter} from './router'
import './theme/index.less'
import Antd from 'ant-design-vue'
import Viser from 'viser-vue'
// import '@/mock'
import store from './store'
import 'animate.css/source/animate.css'
import Plugins from '@/plugins'
import {initI18n} from '@/utils/i18n'
import bootstrap from '@/bootstrap'
import 'moment/locale/zh-cn'
import {serviceRequest} from "@/utils/service-request";

const router = initRouter(store.state.setting.asyncRoutes)
const i18n = initI18n('CN', 'US')

Vue.use(Antd)
Vue.config.productionTip = false
Vue.use(Viser)
Vue.use(Plugins)

Vue.prototype.$serviceRequest = async function (path, method, params, config) {
  config = config || {}
  config.headers = config.headers || {}
  config.headers['Token'] = store.getters['account/token']
  const result = await serviceRequest(path, method, params, config)
  const data = result.data
  if (data.code === 200001) {
    this.$message.error('请登录')
    this.$router.push('/login')
  } else if (data.code === 300001) {
    this.$router.push('/403')
  }
  return result;
}

bootstrap({router, store, i18n, message: Vue.prototype.$message})

new Vue({
  router,
  store,
  i18n,
  render: h => h(App),
}).$mount('#app')
