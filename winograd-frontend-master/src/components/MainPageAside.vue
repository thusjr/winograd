<template>
  <el-menu
    default-active="1"
    class="main-aside"
  >
    <el-menu-item index="1" class="aside-menu" @click="onClickReco">
      <i class="el-icon-s-home"></i>
      <span slot="title">推荐</span>
    </el-menu-item>
    <el-menu-item index="2" class="aside-menu">
      <i class="el-icon-discover"></i>
      <span slot="title">国内</span>
    </el-menu-item>
    <el-menu-item index="3" class="aside-menu">
      <i class="el-icon-position"></i>
      <span slot="title">全球</span>
    </el-menu-item>
    <el-menu-item index="4" class="aside-menu">
      <i class="el-icon-data-line"></i>
      <span slot="title">疫情</span>
    </el-menu-item>
    <el-menu-item index="5" class="aside-menu">
      <i class="el-icon-basketball"></i>
      <span slot="title">体育</span>
    </el-menu-item>
    <el-menu-item index="6" class="aside-menu">
      <i class="el-icon-mobile"></i>
      <span slot="title">科技</span>
    </el-menu-item>
  </el-menu>
</template>

<script>
// import axios from 'axios'
export default {
  name: 'MainPageAside',
  data () {
    return {}
  },
  methods: {
    onClickReco: function () {
      this.$emit('clickMenu', '24hours')
    }
  }
}
</script>

<style>
.main-aside{
    margin-left: 60px;
}
.aside-menu{
    font-size: 16px;
}
</style>
