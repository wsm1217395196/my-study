<template>
  <div id="app">
    <!--<img src="./assets/logo.png">-->
    <!--<div style="color: red">头部</div>-->
    <router-view/>
    <!--<div style="color: red">尾部</div>-->
  </div>
</template>

<script>
  export default {
    name: 'App'
  }
</script>

<style>
  * {
    margin: 0;
    padding: 0;
  }

  #app {
    font-family: 'Avenir', Helvetica, Arial, sans-serif;
    -webkit-font-smoothing: antialiased;
    -moz-osx-font-smoothing: grayscale;
    text-align: center;
    color: #2c3e50;
    /*margin-top: 60px;*/
  }
</style>
