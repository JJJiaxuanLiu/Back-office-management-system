<template>
    <div>
        

        <div class="ma-10">
            <el-input style="width: 200px" placeholder="请输入菜单名称" suffix-icon="el-icon-user" v-model="name">
            </el-input>
            <!-- <el-input style="width: 200px" placeholder="请输入描述" suffix-icon="el-icon-tickets" v-model="description"
                class="ml-5"></el-input>
             -->
            <el-button class="ml-5" type="primary" @click="load">搜索</el-button>
            <el-button class="ml-5" type="warning" @click="reset">重置</el-button>
        </div>

        <div class="ma-10">
            <el-button type="primary" icon="el-icon-circle-plus-outline" @click="handleAdd" class="ml-5">新增</el-button>
            <el-button type="danger" icon="el-icon-delete" @click="delBatch" style="margin-left: 5px">批量删除</el-button>
            <!-- <el-upload action="http://localhost:9090/role/import" :show-file-list="false" accept="xlsx" 
            :on-success="handleExcelImportSuccess" style="display: inline-block" >
                <el-button type="primary" icon="el-icon-download" class="ml-5">导入</el-button>
            </el-upload>
            <el-button type="primary" icon="el-icon-upload2"  @click="exportFile" class="ml-5">导出</el-button> -->

        </div>

        <el-table :data="tableData" stripe @selection-change="handleSelectionChange" 
                    row-key="id" default-expand-all>

            <el-table-column type="selection" width="55"></el-table-column>
            <el-table-column prop="id" label="id" width="180"></el-table-column>
            <el-table-column prop="name" label="名称" ></el-table-column>
            <el-table-column prop="path" label="路径" ></el-table-column>
            <el-table-column prop="pagePath" label="页面路径" ></el-table-column>
            <el-table-column label="图标" class-name="fontSize18" align="center" label-class-name="fontSize12">
                <template slot-scope="scope">
                    <i :class="scope.row.icon"></i>
                </template>
            </el-table-column>
            <el-table-column prop="description" label="描述" ></el-table-column>
            <el-table-column label="操作" width="300"> 
                <template slot-scope="scope">
                    <el-button type="primary" icon="el-icon-plus" @click="handleChildren(scope.row.id)" v-if="(!scope.row.pid && !scope.row.path)">新增子菜单</el-button>
                    <el-button type="success" icon="el-icon-edit" @click="handleEdit(scope.row)">编辑</el-button>
                    <el-button type="danger" icon="el-icon-delete" @click="handleDelete(scope.row.id)">删除</el-button>
                </template>

            </el-table-column>
        </el-table>
        <!-- <div class="pd-10">
            <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="pageNum"
                :page-sizes="[2, 5, 10, 20]" :page-size="pageSize" layout="total, sizes, prev, pager, next, jumper"
                :total="total">
            </el-pagination>
        </div> -->

        <el-dialog title="菜单信息" :visible.sync="dialogFormVisible" width="50%">
            <el-form :model="form" label-width="120px" size="small">
                <el-form-item label="名称">
                    <el-input v-model="form.name" autocomplete="off" ></el-input>
                </el-form-item>
                <el-form-item label="路径">
                    <el-input v-model="form.path" autocomplete="off" ></el-input>
                </el-form-item>
                <el-form-item label="页面路径">
                    <el-input v-model="form.pagePath" autocomplete="off" ></el-input>
                </el-form-item>
                <el-form-item label="图标">
                    <template slot-scope="scope">
                        <el-select clearable v-model="form.icon" placehoder="请选择" style="width:100%">
                            <el-option v-for="item in options" :key="item.name" :label="item.name" :value="item.value">
                                <i :class="item.value" />{{item.name}}
                            </el-option>
                        </el-select>
                    </template>
                    <!-- <el-input v-model="form.icon" autocomplete="off" ></el-input> -->
                </el-form-item>
                <el-form-item label="描述">
                    <el-input v-model="form.description" autocomplete="off"></el-input>
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


export default {
    data() {
        return {
            tableData: [],
            name: '',
            dialogFormVisible: false,
            form: {},
            multipleSelection: [],
            newUserFlag: false,
            options: [],
        }
    },

    created() {
        this.load();
    },
    
    methods: {
        load() {
            // 请求分页查询数据
            this.request.get("/menu", {
                params: {
                    name: this.name,
                }
            }
            ).then(res => {
                this.tableData = res.data;
                console.log(res.data);
            })
            
           
        },

        reset() {
            this.name = '';
            this.description = '';
            this.load();
        },

        handleAdd() {
            this.dialogFormVisible = true;
            this.form = {};
            this.newUserFlag = true;
        },

        save() {
            if (!this.newUserFlag) {
                this.request.put("/menu", this.form).then(res => {
                    if (res.code ==='200') {
                        this.$message.success('更新菜单成功');
                        this.dialogFormVisible = false;
                        this.load();
                    } else {
                        this.$message.error(res.msg);
                        this.load();
                    }
                })
                
            } else {
                this.request.post("/menu", this.form).then(res => {
                    this.newUserFlag = false;
                    if (res.code === '200') {
                        this.$message.success('新增菜单成功！');
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
            this.request.get('/dict/icons').then(res => {
                this.options = res.data
            })
        },

        //传过来的菜单id，成为子菜单的pid
        handleChildren(pid){
            this.form = {};
            this.dialogFormVisible = true;
            if(pid){
                this.form.pid = pid;
            };
            this.newUserFlag = true;
        },

        handleDelete(id) {
            this.$confirm('此操作将永久删除该文件, 是否继续?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                this.request.delete("/menu/" + id).then(res => {
                    if(res.code === '200'){
                        this.$message({
                        type: 'success',
                        message: '删除成功!'
                        });
                        this.load();
                    }
                })
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
                this.request.delete("/menu/del/batch", {
                    params: {
                        stringids: batchid
                    }
                }).then(res => {
                    if(res.code === '600'){
                        this.$message.error(res.msg);
                    }else if(res.code == '200'){
                        this.$message.success("批量删除成功！");
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
             window.open("http://localhost:9090/menu/export")
           
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
            this.request.post("/menu/importcheck").then(res =>{
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

<style>
.fontSize18{
    font-size: 18px;
}

.fontSize12{
    font-size: 12px;
}
</style>