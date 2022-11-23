<template>
    <el-menu :default-openeds="['1', '3']" style="min-height: 100%; overflow-x: hidden" 
        background-color="rgb(48,65,86)"
        text-color="#fff" 
        Active-text-color="ff04b" 
        :collapse-transition="false" 
        :collapse="isCollapse"
        router>
        <div style="height: 60px; line-height: 60px; text-align: center">
            <img src="../assets/logo.png" alt="" v-show="smallLogoShow" style="width: 20px;position: relative;top: 5px">
            <b style="color: azure" v-show="textShow">后台管理系统</b>
            <img src="../assets/logo.png" alt="" v-show="logoShow" style="width: 35px;position: relative;top: 10px">
        </div>

        <el-menu-item index="/">
            <i class="el-icon-s-home"></i>
            <span slot="title">首页</span>
        </el-menu-item>
        <el-submenu index="1">
            <template slot="title">
                <i class="el-icon-menu"></i>
                <span>系统管理</span>
            </template>
            <el-menu-item-group>
                <el-menu-item index="/user" class="el-icon-user">用户管理</el-menu-item>
            </el-menu-item-group>
        </el-submenu>
    </el-menu>
</template>

<script>
export default {
    props: {
        isCollapse: Boolean,
        smallLogoShow: Boolean,
        textShow: Boolean,
        logoShow: Boolean
    }
}
</script>