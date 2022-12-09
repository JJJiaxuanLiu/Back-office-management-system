<template>
  <div>
    <el-container style="height: 100vh">
      <el-aside :width="asideWidth + 'px'" style="background-color: rgb(238, 241, 246) ">
        <Aside :isCollapse="isCollapse" :smallLogoShow="smallLogoShow" :textShow="textShow" :logoShow="logoShow" ></Aside>
      </el-aside>

      <el-container>
        <el-header style=" border-bottom: 1px solid #ccc; ">
          <Header :collapaseBtnClass="collapaseBtnClass" :collapse="collapse" :user="user"></Header>
        </el-header>

        <el-main>
          <!-- 表示当前页面的子路由会在<router-view/>中展示 -->
          <router-view @refreshUser="getUser"/>

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
      user: localStorage.getItem('user') ? JSON.parse(localStorage.getItem('user')) : {},
      
    }
  },

  components:{
    Aside,
    Header
  },
  created() {
    //从后台获取最新数据
    this.getUser();
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

    getUser() {
      //从后台获取user
      this.request.get("/user/username/"+ this.user.username).then(res => {
        //给user重新赋值
      this.user = res.data;
      });
    },



  }
}
</script>
