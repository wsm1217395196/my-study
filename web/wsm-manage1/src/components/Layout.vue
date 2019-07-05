<template>
  <div class="wrrp">
    <!--头部-->
    <div class="top">
      <div><img src="../assets/img/qq_photo.jpg" style="width: 218px;height: 60px;float: left"/></div>
      <div style="color: white;float: left;font-size: 18px;margin-top: 20px;padding-left: 10px;">
        导航栏。。。。
      </div>
      <div style="color: white;float: left;font-size: 18px;margin-top: 20px;padding-left: 400px;">
        wsm后台管理系统
      </div>
      <div style="color: white;float: right;font-size: 18px;margin-top: 20px;padding-right: 10px">
        个人资料
      </div>
    </div>

    <!--底部-->
    <div class="bottom">

      <!--左边侧栏菜单-->
      <div class="b-l">
        <el-menu
          :default-active="defaultActiveIndex"
          @select="handleSelect"
          class="el-menu-vertical-demo"
          background-color="#545c64"
          router
          text-color="#fff"
          active-text-color="#ffd04b">
          <template v-for="(item,index) in $router.options.routes">

            <el-menu-item :index="item.children[0].path" v-if="item.leaf">
              <i :class="item.icon"></i>
              <span slot="title">{{item.children[0].name}}</span>
            </el-menu-item>

            <el-submenu v-if="!item.leaf" :index="index+''">
              <template slot="title">
                <i :class="item.icon"></i>
                <span>{{item.name}}</span>
              </template>
              <template v-for="(sonItem,sonIndex) in item.children">
                <el-menu-item :index="sonItem.path">{{sonItem.name}}</el-menu-item>
              </template>
            </el-submenu>
          </template>
        </el-menu>
      </div>

      <!--右边内容-->
      <div class="b-r">
        <router-view></router-view>
      </div>
    </div>
  </div>
</template>

<script>
  export default {
    name: 'Layout',
    data() {
      return {
        defaultActiveIndex: "0"
      }
    },
    methods: {
      handleSelect(index) {
        debugger;
        this.defaultActiveIndex = index;
      }
    }
  }
</script>

<style scoped>
  .wrrp {
    width: 100%;
    height: 100%;
  }

  .top {
    height: 60px;
    background: #373d41;
  }

  .bottom {
    height: calc(100% - 60px);
    overflow: hidden;
  }

  .b-l {
    float: left;
    width: 220px;
    height: 100%;
  }

  .b-r {
    float: left;
    width: calc(100% - 220px);
    height: 100%;
    background-color: #E9EEF3;
  }

  .el-menu{
    overflow:auto;
    height: 100%;
  }
</style>
