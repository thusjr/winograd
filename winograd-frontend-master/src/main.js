// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'

import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'

import router from './router'

Vue.use(ElementUI)
Vue.config.productionTip = false

router.beforeEach((to, from, next) => {
  let flag = localStorage.getItem('logFlag') === 'true'
  if (flag) {
    if (to.meta.needLog === false) {
      // alert('请先退出登录!')
      next({ path: '/home' })
    } else {
      next()
    }
  } else if (to.meta.needLog === true) {
    if (to.meta.needLog) {
      // alert('请先登录!')
      next({ path: '/' })
    } else {
      next()
    }
  } else {
    next()
  }
})

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  components: { App },
  template: '<App/>'
})
