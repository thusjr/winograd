<template>
    <el-menu :default-active="activeItem" router>
        <el-menu-item index="recommendation">
            <div class="el-icon-s-home" />
            <span>推荐</span>
        </el-menu-item>
        <el-menu-item index="domestic">
            <div class="el-icon-discover" />
            <span>国内</span>
        </el-menu-item>
        <el-menu-item index="global">
            <div class="el-icon-position" />
            <span>全球</span>
        </el-menu-item>
        <el-menu-item index="antiepidemic">
            <div class="el-icon-data-line"/>
            <span>疫情</span>
        </el-menu-item>
        <el-menu-item index="sports">
            <div class="el-icon-basketball"/>
            <span>体育</span>
        </el-menu-item>
        <el-menu-item index="tech">
            <div class="el-icon-mobile" />
            <span>科技</span>
        </el-menu-item>
        </el-menu>
</template>

<script>
export default {
  name: 'Menu',
  computed: {
    activeItem: function () {
      return location.pathname.slice(1)
    }
  }
}
</script>

<style>

.el-menu-item {
    font-size: 18px;
}

</style>
