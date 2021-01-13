<template>
  <div>
    <el-container style="width: 1000px; margin-left: auto; margin-right: auto">
      <el-header class="newsHeader">newtitle</el-header>
      <el-container>
        <el-main class="newsBody">newsbody</el-main>
        <el-footer class="newsFooter">winograd</el-footer>
      </el-container>
    </el-container>
  </div>
</template>

<script>
export default {
  name: 'NewsContent'
}
</script>

<style>
.newsHeader {
  background-color: #93adce;
  color: #333;
  text-align: center;
  line-height: 60px;
}
.newsBody {
  background-color: #aac4df;
  color: #333;
  text-align: center;
  line-height: 160px;
}
.newsFooter {
  background-color: #dfedff;
  color: #333;
  text-align: center;
  line-height: 60px;
}
</style>
