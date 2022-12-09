<template>
    <div class="wrapper">
        <div
            style="margin: 200px auto; background-color: #fff;width: 400px;height:300px;padding:20px;border-radius:10px">
            <div style="margin:20px 0;text-align:center;font-size:24px"><b>登 录</b></div>
            <el-form :model="user" :rules="rules" ref="userForm">
                <el-form-item prop="username">
                    <el-input placeholder="请输入用户名" size="medium" style="margin:10px 0" prefix-icon="el-icon-user" v-model="user.username">
                    </el-input>
                </el-form-item>
                <el-form-item prop="password">
                    <el-input placeholder="请输入密码" size="medium" style="margin:10px 0" prefix-icon="el-icon-lock" show-password
                        v-model="user.password"></el-input>
                </el-form-item>
                <div style="margin:10px 0; text-align: right">
                    <el-button type="primary" size="small" autocomplete="off" @click="login()">登录</el-button>
                    <el-button type="warning" size="small" autocomplete="off" @click="$router.push('/register')">注册</el-button>
                </div>
            </el-form>
        </div>
    </div>
</template>

<script>
export default {
    name: "Login",
    data() {
        return {
            user: {
                username:'',
                password:'',
            },
            rules: {
                username: [
                    { required: true, message: '请输入用户名', trigger: 'blur' },
                    { min: 1, max: 15, message: '长度在 1 到 15 个字符', trigger: 'blur' }
                ],
               
                password: [
                    { required: true, message: '请输入密码', trigger: 'blur' },
                    { min: 1, max: 15, message: '长度在 1 到 15 个字符', trigger: 'blur' }
                ],
            }
        }
    },
    methods: {
        login() {
            this.$refs.userForm.validate((valid) => {
                if (valid) {
                    this.request.post("/user/login", this.user).then(res => {
                        if (res.code === '200') {
                            localStorage.setItem("user", JSON.stringify(res.data))  //将用户信息存入浏览器
                            localStorage.setItem("menus", JSON.stringify(res.data.menus))  //将用户信息存入浏览器
                            this.$router.push("/");
                            this.$message.success(res.msg);
                        } else {
                            this.$message.error(res.msg);
                        }
                    })
                } else {
                    console.log('error submit!!');
                    return false;
                }
            });
        },

    }
}
</script>

<style>
.wrapper {
    height: 100vh;
    background-image: linear-gradient(to bottom right, #FC466B, #3F5EFB);
    overflow: hidden;
}
</style>