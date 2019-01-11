import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import nativeVue from './utils/native-vue.js'
Vue.use(nativeVue)
Vue.config.productionTip = false

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
