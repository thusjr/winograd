<template>
  <el-row :gutter="20">
    <el-col :span="10" :offset="0">
      <ul>
        <li
          v-for="content in news"
          :key="content.title"
          style="margin: 10px 0px 20px 0;"
        >
          <el-link style="font-size: 20px" :underline="false" :href=content.url target="_blank">
            {{ content.title }}
          </el-link>
          <!-- <router-link  underline="false" to="/news">
            {{ content }} -->
          <!-- </router-link> -->
        </li>
      </ul>
    </el-col>
    <el-col :span="6" :offset="0">
      <div>test</div>
    </el-col>
    <el-col :span="8" :offset="0">
      <!-- <el-carousel height="400px">
        <el-carousel-item v-for="item in 5" :key="item">
          <el-image
            style="width: 100%; height: 400px"
            :src="require('@/assets/winograd-hd.png')"
            :fit="fit"
          />
        </el-carousel-item>
      </el-carousel> -->
    </el-col>
  </el-row>
</template>

<script>
export default {
  name: 'NewsCollection',
  data () {
    return {
      fit: 'fit',

      news: [
        {title: 'Trump.', url: 'https://www.donaldjtrump.com/'},
        {title: 'Covid.', url: 'https://zh.wikipedia.org/wiki/2019%E5%86%A0%E7%8A%B6%E7%97%85%E6%AF%92%E7%97%85'},
        {title: 'Nobody konws covid better than me!', url: 'https://edition.cnn.com/2020/10/09/media/trump-coughs-on-fox-news/index.html'}
      ]

    }
  }
}
</script>

<style>
.el-carousel__item h3 {
  color: #475669;
  font-size: 14px;
  opacity: 0.75;
  line-height: 150px;
  margin: 0;
}
.el-carousel__item:nth-child(2n) {
  background-color: #b1c3db;
}
.el-carousel__item:nth-child(2n + 1) {
  background-color: #d3dce6;
}
a {
  text-decoration: none;
}
.router-link-active {
  text-decoration: none;
}
</style>
