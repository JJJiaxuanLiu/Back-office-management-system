<template>
    <div style=" font-size: 12px; line-height: 60px; display: flex">
        <div style="flex: 1; font-size: 20px">
            <span :class="collapaseBtnClass" style="cursor: pointer" @click="collapse"></span>


            <el-breadcrumb separator="/" style="display:inline-block; margin-left: 10px;font-size: medium;">
                <el-breadcrumb-item :to="{ path: '/' }" >首页</el-breadcrumb-item>
                <el-breadcrumb-item>{{currentPathName}}</el-breadcrumb-item>
            </el-breadcrumb>

        </div>
        <el-dropdown style="width: 70px; cursor: pointer">
            <span>王小虎</span><i class="el-icon-arrow-down" style="margin-left: 5px"></i>
            <el-dropdown-menu slot="dropdown" style="width:100px; text-align:center">
                <el-dropdown-item>个人中心</el-dropdown-item>
                <router-link to="/login" style="text-decoration:none">
                    <el-dropdown-item>退出</el-dropdown-item>
                </router-link>
            </el-dropdown-menu>
        </el-dropdown>

    </div>
</template>

<script>
import { RouterLink } from 'vue-router';

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
        // currentPathName:''
        };
    },
    components: { RouterLink }
}

</script>