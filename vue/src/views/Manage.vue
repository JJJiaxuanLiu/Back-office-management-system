<template>
  <div>
    <el-container style="height: 100vh">
      <el-aside :width="asideWidth + 'px'" style="background-color: rgb(238, 241, 246) ">
        <Aside :isCollapse="isCollapse" :smallLogoShow="smallLogoShow" :textShow="textShow" :logoShow="logoShow"></Aside>
      </el-aside>

      <el-container>
        <el-header style=" border-bottom: 1px solid #ccc; ">
          <Header :collapaseBtnClass="collapaseBtnClass" :collapse="collapse"></Header>
        </el-header>

        <el-main>
          <!-- 表示当前页面的子路由会在<router-view/>中展示 -->
          <router-view/>

        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script>

import  Aside  from '@/components/Aside.vue';
import Header from '@/components/Header.vue';
export default {
  data() {
    return {
     
      collapaseBtnClass: 'el-icon-s-fold',
      isCollapse: false,
      asideWidth: 200,
      textShow: true,
      logoShow: false,
      smallLogoShow: true,
      
    }
  },

  components:{
    Aside,
    Header
  },

  methods: {
    // 点击收缩整体菜单按钮
    collapse() {
      this.isCollapse = !this.isCollapse
      if (this.isCollapse) {
        this.asideWidth = 64;
        this.collapaseBtnClass = 'el-icon-s-unfold';
        this.textShow = false;
        this.logoShow = true;
        this.smallLogoShow = false;
      } else {
        this.asideWidth = 200;
        this.collapaseBtnClass = 'el-icon-s-fold';
        this.textShow = true;
        this.logoShow = false;
        this.smallLogoShow = true;
      }
    },



  }
}
</script>
