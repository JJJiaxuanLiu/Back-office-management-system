<template>
    <el-card style="width:700px; padding:20px">
        <el-form :model="form" label-width="55px" size="small">
            <el-upload class="avatar-uploader" action="http://localhost:9090/file/upload"
                :show-file-list="false" :on-success="handleAvatarSuccess" >
                <img v-if="form.avatarUrl" :src="form.avatarUrl" class="avatar">
                <i v-else class="el-icon-plus avatar-uploader-icon"></i>
            </el-upload>
            
                <el-form-item label="用户名">
                    <el-input v-model="form.username" autocomplete="off" :disabled="true"></el-input>
                </el-form-item>
            
            
            <el-form-item label="昵称">
                <el-input v-model="form.nickname" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="邮箱">
                <el-input v-model="form.email" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="电话">
                <el-input v-model="form.phone" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="地址">
                <el-input v-model="form.address" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" style="width:500px" @click="save">确 定</el-button>
            </el-form-item>
        </el-form>
    </el-card>
</template>

<script>
export default {
    data() {
        return {
            form: {},
            user: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {},
        }
    },
    created() {
       this.getUser().then(res =>{
        this.form = res
       });
    },
    methods: {
        async getUser() {
            return (await this.request.get("/user/username/" + this.user.username)).data
            
        },

        save() {
            this.request.put("/user", this.form).then(res => {
                if (res.code === '200') {
                    this.$message.success("保存成功");
                    //触发父级manage更新user方法
                    this.$emit('refreshUser');

                    // this.getUser().then(res => {
                    //     res.token = JSON.parse(localStorage.getItem(res)).token;
                    //     localStorage.setItem('user', JSON.stringify(res));
                    // })
                   
                } else {
                    this.$message.error("保存失败");
                    this.getUser();
                }
            })

        },

        handleAvatarSuccess(res){
            this.form.avatarUrl = res;
            
            let url = localStorage.getItem('user.avatarUrl')
            console.log(url+'...................');
        }



    }
}
</script>

<style>
    .avatar-uploader{
        text-align: center;
    }
  .avatar-uploader .el-upload {
    border: 1px dashed #d9d9d9;
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
  }
  .avatar-uploader .el-upload:hover {
    border-color: #409EFF;
    padding-bottom: 10px;
  }
  .avatar-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    width: 168px;
    height: 168px;
    line-height: 168px;
    text-align: center;
  }
  .avatar {
    width: 168px;
    height: 168px;
    display: block;
  }
</style>