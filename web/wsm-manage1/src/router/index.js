// 组件
import Vue from 'vue'
import Router from 'vue-router'
import ElementUI from 'element-ui';

//组件样式
import 'element-ui/lib/theme-chalk/index.css';

// 页面组件
import Layout from '@/components/Layout'//布局页面
import Home from '@/components/Home'//首页
import User from '@/components/User'//用户管理
import RecruitPlatform from '@/components/RecruitPlatform'//招聘平台
import Job from '@/components/Job'//用户管理


Vue.use(Router)
Vue.use(ElementUI)

export default new Router({
  mode: 'history',
  routes: [
    {
      path: '/',
      name: 'Layout',
      component: Layout,
      redirect: '/home',
      leaf: true, // true只有一个节点
      menuShow: true,
      icon: 'el-icon-s-home', // 图标样式class
      children: [
        {path: '/home', component: Home, name: '首页', menuShow: true},
      ]
    },
    {
      path: '/',
      name: '用户管理',
      component: Layout,
      leaf: true, // true只有一个节点
      menuShow: true,
      icon: 'el-icon-user', // 图标样式class
      children: [
        {path: '/user', component: User, name: '用户管理', menuShow: true},
      ]
    },
    {
      path: '/',
      name: '职业信息',
      component: Layout,
      leaf: false, // true只有一个节点
      menuShow: true,
      icon: 'el-icon-menu', // 图标样式class
      children: [
        {path: '/recruitPlatform', component: RecruitPlatform, name: '招聘平台', menuShow: true},
        {path: '/job', component: Job, name: '职位管理', menuShow: true},
      ]
    },
  ]
})
