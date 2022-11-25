<template>
    <div>
        

        <div class="ma-10">
            <el-input style="width: 200px" placeholder="请输入姓名" suffix-icon="el-icon-search" v-model="username">
            </el-input>
            <el-input style="width: 200px" placeholder="请输入邮箱" suffix-icon="el-icon-message" v-model="email"
                class="ml-5"></el-input>
            <el-input style="width: 200px" placeholder="请输入地址" suffix-icon="el-icon-location-information"
                v-model="address" class="ml-5"></el-input>
            <el-button class="ml-5" type="primary" @click="load">搜索</el-button>
            <el-button class="ml-5" type="warning" @click="reset">重置</el-button>
        </div>

        <div class="ma-10">
            <el-button type="primary" icon="el-icon-circle-plus-outline" @click="handleAdd" class="ml-5">新增</el-button>
            <el-button type="danger" icon="el-icon-delete" @click="delBatch" style="margin-left: 5px">批量删除</el-button>
            <el-upload action="http://localhost:9090/user/import" :show-file-list="false" accept="xlsx" 
            :on-success="handleExcelImportSuccess" style="display: inline-block" >
                <el-button type="primary" icon="el-icon-download" class="ml-5">导入</el-button>
            </el-upload>
            <el-button type="primary" icon="el-icon-upload2"  @click="exportFile" class="ml-5">导出</el-button>

        </div>

        <el-table :data="tableData" stripe @selection-change="handleSelectionChange">

            <el-table-column type="selection" width="55"></el-table-column>
            <el-table-column prop="id" label="id" width="80"></el-table-column>
            <el-table-column prop="username" label="用户名" width="140"></el-table-column>
            <el-table-column prop="nickname" label="昵称" width="120"></el-table-column>
            <el-table-column prop="email" label="邮箱"></el-table-column>
            <el-table-column prop="phone" label="电话"></el-table-column>
            <el-table-column prop="address" label="地址"></el-table-column>
            <el-table-column label="操作">
                <template slot-scope="scope">
                    <el-button type="success" icon="el-icon-edit" @click="handleEdit(scope.row)">编辑</el-button>
                    <el-button type="danger" icon="el-icon-delete" @click="handleDelete(scope.row.id)">删除</el-button>
                </template>

            </el-table-column>
        </el-table>
        <div class="pd-10">
            <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="pageNum"
                :page-sizes="[2, 5, 10, 20]" :page-size="pageSize" layout="total, sizes, prev, pager, next, jumper"
                :total="total">
            </el-pagination>
        </div>

        <el-dialog title="用户信息" :visible.sync="dialogFormVisible" width="50%">
            <el-form :model="form" label-width="120px" size="small">
                <el-form-item label="用户名">
                    <el-input v-model="form.username" autocomplete="off"></el-input>
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
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="dialogFormVisible = false">取 消</el-button>
                <el-button type="primary" @click="save">确 定</el-button>
            </div>
        </el-dialog>
    </div>
</template>

<script>
import axios from 'axios';

export default {
    data() {
        return {
            tableData: [],
            total: 0,
            pageNum: 1,
            pageSize: 10,
            username: '',
            email: '',
            address: '',
            dialogFormVisible: false,
            form: {},
            multipleSelection: [],
            newUserFlag: false,
        }
    },

    created() {
        this.load();
    },
    
    methods: {
        load() {
            // 请求分页查询数据
            this.request.get("/user/page", {
                params: {
                    pageNum: this.pageNum,
                    pageSize: this.pageSize,
                    username: this.username,
                    email: this.email,
                    address: this.address,
                }
            }
            ).then(res => {

                this.tableData = res.records;
                this.total = res.total;
            })
        },

        reset() {
            this.username = '';
            this.email = '';
            this.address = '';
            this.load();
        },

        handleAdd() {
            this.dialogFormVisible = true;
            this.form = {};
            this.newUserFlag = true;
        },

        save() {
            if (!this.newUserFlag) {
                this.request.put("/user", this.form).then(res => {
                    if (res.code ==='200') {
                        this.$message.success(res.msg);
                        this.dialogFormVisible = false;
                        this.load();
                    } else {
                        this.$message.error(res.msg);
                        this.load();
                    }
                })
                
            } else {
                this.request.post("/user", this.form).then(res => {
                    this.newUserFlag = false;
                    if (res.code === '200') {
                        this.$message.success(res.msg);
                        this.dialogFormVisible = false;
                        this.load();
                    } else {
                        this.$message.error(res.msg);
                        this.load();
                    }
                })
            }


        },

        handleEdit(row) {
            this.form = row;
            this.dialogFormVisible = true;
        },

        handleDelete(id) {
            this.$confirm('此操作将永久删除该文件, 是否继续?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                this.request.delete("/user/" + id);
                this.$message({
                    type: 'success',
                    message: '删除成功!'
                });
                this.load();
            }).catch(() => {
                this.$message({
                    type: 'info',
                    message: '已取消删除'
                });
            });
        },

        handleSelectionChange(val) {
            this.multipleSelection = val;
            console.log("val="+val);
        },

        delBatch() {
            this.$confirm('此操作将永久删除, 是否继续?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                let ids = this.multipleSelection.map(v => v.id);  //将拿到的对象[{},{}] -> [id,id]数组
                console.log("id=" + ids);
                let batchid = '';
                for (const id of ids) {     //将ids拼接为像1,2,3,4的字符串，拼接在url参数中，以RequestParam形式发送
                    batchid === '' ? (batchid = id) : (batchid = batchid + ',' + id);
                }
                console.log(batchid);
                this.request.delete("/user/del/batch", {
                    params: {
                        stringids: batchid
                    }
                }).then(res => {
                    if(res.code === '600'){
                        this.$message.error(res.msg);
                    }else if(res.code == '200'){
                        this.$message.success("删除成功！");
                        this.load();
                    }
                });
                // this.$message({
                //     type: 'success',
                //     message: '删除成功!'
                // });
                // this.load();
            }).catch(() => {
                this.$message({
                    type: 'info',
                    message: '已取消删除'
                });
            }); 
        },

        exportFile(){
             window.open("http://localhost:9090/user/export")
           
        },

        handleSizeChange(pageSize) {
            this.pageSize = pageSize;
            this.load();
        },
        handleCurrentChange(pageNum) {
            this.pageNum = pageNum;
            this.load();
        },
        handleExcelImportSuccess(){
            this.$message.success("文件上传成功，校验中...");
            this.checkFile();
        },
        checkFile(){
            this.request.post("/user/importcheck").then(res =>{
                if(res.code === '200'){
                    this.$message.success("文件校验成功，已上传！");
                    this.load();
                }else{
                    this.$message.error(res.msg);
                }
            })
        },
    }
}
</script>