import Vue from 'vue'
import Router from 'vue-router'
import HelloWorld from '@/components/HelloWorld'
import UserRegister from '@/components/UserRegister'
import UserLogin from '@/components/UserLogin'
import RecoNewsPage from '@/components/classifiednewspage/RecoNewsPage'
import DomesticPage from '@/components/classifiednewspage/DomesticPage'
import GlobalPage from '@/components/classifiednewspage/GlobalPage'
import AntiepPage from '@/components/classifiednewspage/AntiepPage'
import SportsPage from '@/components/classifiednewspage/SportsPage'
import TechPage from '@/components/classifiednewspage/TechPage'
import SearchBackPage from '@/components/SearchBackPage'
// import UserLogPopover from '@/components/userservice/Popover'
import HomePage from '@/components/userservice/HomePage'
import ModifyInfoPage from '@/components/userservice/ModifyInfoPage'

Vue.use(Router)

export default new Router({
  mode: 'history',
  routes: [
    {
      path: '/helloworld',
      name: 'HelloWorld',
      component: HelloWorld
    },
    {
      path: '/',
      name: 'MainPage',
      component: RecoNewsPage,
      meta: {
        needLog: false
      }
    },
    {
      path: '/regis',
      component: UserRegister,
      meta: {
        needLog: false
      }
    },
    {
      path: '/login',
      component: UserLogin,
      meta: {
        needLog: false
      }
    },
    {
      path: '/search',
      component: SearchBackPage
    },
    {
      path: '/recommendation',
      component: RecoNewsPage
    },
    {
      path: '/domestic',
      component: DomesticPage
    },
    {
      path: '/global',
      component: GlobalPage
    },
    {
      path: '/antiepidemic',
      component: AntiepPage
    },
    {
      path: '/sports',
      component: SportsPage
    },
    {
      path: '/tech',
      component: TechPage
    },
    {
      path: '/home',
      component: HomePage,
      meta: {
        needLog: true
      }
    },
    {
      path: '/edit',
      component: ModifyInfoPage,
      meta: {
        needLog: true
      }
    }
  ]
})
