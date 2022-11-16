<template>
  <div >
    <el-container style="height: 100vh">
      <el-aside :width="asideWidth+'px'" style="background-color: rgb(238, 241, 246) ">
        <el-menu :default-openeds="['1', '3']" style="min-height: 100%; overflow-x: hidden"
                  background-color="rgb(48,65,86)"
                  text-color="#fff"
                  Active-text-color="ff04b"
                  :collapse-transition="false"
                  :collapse="isCollapse"
        >
          <div style="height: 60px; line-height: 60px; text-align: center">
            <img src="../assets/logo.png" alt="" v-show="smallLogoShow" style="width: 20px;position: relative;top: 5px">
            <b style="color: azure" v-show="textShow">后台管理系统</b>
            <img src="../assets/logo.png" alt="" v-show="logoShow" style="width: 35px;position: relative;top: 10px">
          </div>
          <el-submenu index="1">
            <template slot="title">
              <i class="el-icon-message"></i>
              <span slot="tilte">导航一</span>
            </template>
            <el-menu-item-group>
              <template slot="title">分组一</template>
              <el-menu-item index="1-1">选项1</el-menu-item>
              <el-menu-item index="1-2">选项2</el-menu-item>
            </el-menu-item-group>
            <el-menu-item-group title="分组2">
              <el-menu-item index="1-3">选项3</el-menu-item>
            </el-menu-item-group>
            <el-submenu index="1-4">
              <template slot="title">选项4</template>
              <el-menu-item index="1-4-1">选项4-1</el-menu-item>
            </el-submenu>
          </el-submenu>
          <el-submenu index="2">
            <template slot="title">
              <i class="el-icon-menu"></i>
              <span slot="tilte">导航二</span>
            </template>
            <el-menu-item-group>
              <template slot="title">分组一</template>
              <el-menu-item index="2-1">选项1</el-menu-item>
              <el-menu-item index="2-2">选项2</el-menu-item>
            </el-menu-item-group>
            <el-menu-item-group title="分组2">
              <el-menu-item index="2-3">选项3</el-menu-item>
            </el-menu-item-group>
            <el-submenu index="2-4">
              <template slot="title">选项4</template>
              <el-menu-item index="2-4-1">选项4-1</el-menu-item>
            </el-submenu>
          </el-submenu>
          <el-submenu index="3">
            <template slot="title">
              <i class="el-icon-setting"></i>
              <span slot="tilte">导航三</span>
            </template>
            <el-menu-item-group>
              <template slot="title">分组一</template>
              <el-menu-item index="3-1">选项1</el-menu-item>
              <el-menu-item index="3-2">选项2</el-menu-item>
            </el-menu-item-group>
            <el-menu-item-group title="分组2">
              <el-menu-item index="3-3">选项3</el-menu-item>
            </el-menu-item-group>
            <el-submenu index="3-4">
              <template slot="title">选项4</template>
              <el-menu-item index="3-4-1">选项4-1</el-menu-item>
            </el-submenu>
          </el-submenu>
        </el-menu>
      </el-aside>

      <el-container>
        <el-header style=" font-size: 12px; border-bottom: 1px solid #ccc; line-height: 60px; display: flex">
          <div style="flex: 1; font-size: 25px">
            <span :class="collapaseBtnClass" style="cursor: pointer" @click="collapse"></span>
          </div>
          <el-dropdown style="width: 70px; cursor: pointer">
            <span>王小虎</span><i class="el-icon-arrow-down" style="margin-left: 5px"></i>
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item>个人中心</el-dropdown-item>
              <el-dropdown-item>退出</el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>

        </el-header>

        <el-main>
          <div style="margin-bottom: 30px">
            <el-breadcrumb separator="/">
              <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
              <el-breadcrumb-item>用户管理</el-breadcrumb-item>
            </el-breadcrumb>
          </div>

          <div class="ma-10" >
            <el-input style="width: 200px" placeholder="请输入姓名" suffix-icon="el-icon-search"></el-input>
            <el-input style="width: 200px" placeholder="请输入邮箱" suffix-icon="el-icon-message" class="ml-5"></el-input>
            <el-input style="width: 200px" placeholder="请输入地址" suffix-icon="el-icon-location-information" class="ml-5"></el-input>
            <el-button class="ml-5" type="primary">搜索</el-button>
          </div>

          <div class="ma-10">
            <el-button type="primary" icon="el-icon-circle-plus-outline">新增</el-button>
            <el-button type="danger" icon="el-icon-delete">删除</el-button>
            <el-button type="primary" icon="el-icon-download">导入</el-button>
            <el-button type="primary" icon="el-icon-upload2">导出</el-button>

          </div>

          <el-table :data="tableData" stripe>
            <el-table-column prop="date" label="日期" width="140">
            </el-table-column>
            <el-table-column prop="name" label="姓名" width="120">
            </el-table-column>
            <el-table-column prop="address" label="地址">
            </el-table-column>
            <el-table-column label="操作">
              <el-button type="success" icon="el-icon-edit">编辑</el-button>
              <el-button type="danger" icon="el-icon-delete">删除</el-button>
            </el-table-column>
          </el-table>
          <div class="pd-10">
            <el-pagination
                :page-sizes="[5, 10, 15, 20]"
                :page-size="10"
                layout="total, sizes, prev, pager, next, jumper"
                :total="400">
            </el-pagination>
          </div>
        </el-main>
      </el-container>
    </el-container>
  </div >
</template>

<script>
// @ is an alias to /src
import HelloWorld from '@/components/HelloWorld.vue'

export default {
  name: 'HomeView',
  data() {
    const item = {
      date: '2016-05-02',
      name: '王小虎',
      address: '上海市普陀区金沙江路 1518 弄'
    };
    return {
      tableData: Array(20).fill(item),
      collapaseBtnClass: 'el-icon-s-fold',
      isCollapse: false,
      asideWidth: 200,
      textShow: true,
      logoShow: false,
      smallLogoShow :true,
    }
  },
  methods: {
    // 点击收缩整体菜单按钮
    collapse(){
      this.isCollapse = !this.isCollapse
      if(this.isCollapse){
        this.asideWidth = 64;
        this.collapaseBtnClass = 'el-icon-s-unfold';
        this.textShow = false;
        this.logoShow = true;
        this.smallLogoShow = false;
      }else{
        this.asideWidth = 200;
        this.collapaseBtnClass = 'el-icon-s-fold';
        this.textShow = true;
        this.logoShow = false;
        this.smallLogoShow = true;
      }
    }
  }
}
</script>
