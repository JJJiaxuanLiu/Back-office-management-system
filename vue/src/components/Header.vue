<template>
    <div style=" font-size: 12px; line-height: 60px; display: flex">
        <div style="flex: 1; font-size: 20px">
            <span :class="collapaseBtnClass" style="cursor: pointer" @click="collapse"></span>


            <el-breadcrumb separator="/" style="display:inline-block; margin-left: 10px;font-size: medium;">
                <el-breadcrumb-item :to="{ path: '/' }" >首页</el-breadcrumb-item>
                <el-breadcrumb-item>{{currentPathName}}</el-breadcrumb-item>
            </el-breadcrumb>

        </div>
        <el-dropdown style="width: 150px; cursor: pointer; text-align: right;">
            <div style="display: inline-block">
                <img :src="user.avatarUrl" alt=""
                        style="width: 30px; height: 30px; border-radius: 30%; position:relative; top:8px; right:5px">
                <span>{{user.nickname}}</span><i class="el-icon-arrow-down" style="margin-left: 5px"></i>
            </div>
            <el-dropdown-menu slot="dropdown" style="width:100px; text-align:center">
                <el-dropdown-item>
                    <router-link to="/person">个人中心</router-link>
                </el-dropdown-item>
                <el-dropdown-item>
                    <span style="text-decoration:none" @click="logout">退出</span>
                </el-dropdown-item>
                    
            </el-dropdown-menu>
        </el-dropdown>

    </div>
</template>

<script>
import { RouterLink, RouterView } from 'vue-router';

export default {
    name: "Header",
    props: {
        collapaseBtnClass: String,
        collapse: Function,
    },
    computed: {
        currentPathName() {
            return this.$store.state.currentPathName; //需要监听的数据
        }
    },
    watch: {
        currentPathName(newVal, oldVal) {
            console.log(newVal);
        }
    },
    data() {
        return {
        user: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {},
        };
    },
    methods:{
        logout(){
            this.$router.push("/login")
            localStorage.removeItem("user");
            this.$message.success("退出成功！");
        }
    },
    components: { RouterLink, RouterView }
}

</script>