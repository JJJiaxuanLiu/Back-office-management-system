<template>
    <div>

        <div class="ma-10">
            <el-input style="width: 200px" placeholder="请输入文件名" suffix-icon="el-icon-search" v-model="name">
            </el-input>
            <el-button class="ml-5" type="primary" @click="load">搜索</el-button>
            <el-button class="ml-5" type="warning" @click="reset">重置</el-button>
        </div>

        <div class="ma-10">
            <el-upload action="http://localhost:9090/file/upload" :show-file-list="false"
                :on-success="handleFileUploadSuccess" style="display: inline-block">
                <el-button type="primary" icon="el-icon-upload2" class="ml-5">上传文件</el-button>
            </el-upload>
            <el-button type="danger" icon="el-icon-delete" @click="delBatch" style="margin-left: 5px">批量删除</el-button>
            <!-- <el-button type="primary" icon="el-icon-upload2" @click="exportFile" class="ml-5">导出</el-button> -->

        </div>

        <el-table :data="tableData" stripe @selection-change="handleSelectionChange">

            <el-table-column type="selection" width="55"></el-table-column>
            <el-table-column prop="id" label="ID" width="80"></el-table-column>
            <el-table-column prop="name" label="文件名" ></el-table-column>
            <el-table-column prop="type" label="文件类型" ></el-table-column>
            <el-table-column prop="size" label="文件大小"></el-table-column>
            <el-table-column  label="下载">
                <template slot-scope="scope">
                    <el-button type="primary" @click="donwload(scope.row.url)">下载</el-button>
                </template>
            </el-table-column>
            <el-table-column label="是否禁用链接">
                <template slot-scope="scope">
                    <el-switch v-model="scope.row.enable" active-color="#13ce66" inactive-color="#ccc" @change="changeEnable(scope.row)">
                    </el-switch>
                </template>
            </el-table-column>
            <el-table-column label="操作">
                <template slot-scope="scope">
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

    </div>
</template>


<script>
export default{
    data() {
        return{
            tableData :[],
            total: 0,
            pageNum: 1,
            pageSize: 5,
            multipleSelection: [],
            name: '',
        }
    },
    created() {
        this.load();
    },
    methods:{
        load() {
            // 请求分页查询数据
            this.request.get("/file/page", {
                params: {
                    pageNum: this.pageNum,
                    pageSize: this.pageSize,
                    name: this.name,
                      
                }
            }
            ).then(res => {

                this.tableData = res.records;
                this.total = res.total;
            })
        },
        handleSizeChange(pageSize) {
            this.pageSize = pageSize;
            this.load();
        },
        handleCurrentChange(pageNum) {
            this.pageNum = pageNum;
            this.load();
        },
        handleSelectionChange(val) {
            this.multipleSelection = val;
            console.log("val="+val);
        },

        reset() {
            this.name = '';
            this.load();
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
                this.request.delete("/file/del/batch", {
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

        handleDelete(id) {
            this.$confirm('此操作将永久删除该文件, 是否继续?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                this.request.delete("/file/" + id).then(res => {
                    if(res.code === '200'){
                        this.$message({
                        type: 'success',
                        message: '删除成功!'
                        });
                        this.load();
                    }
                });
               
            }).catch(() => {
                this.$message({
                    type: 'info',
                    message: '已取消删除'
                });
            });
        },

        handleFileUploadSuccess(){
            this.$message.success("文件上传成功!");
            this.load();
        },

        donwload(url){
            window.open(url);
        },

        changeEnable(row){
            this.request.put("/file", row).then(res => {
                    if (res.code ==='200') {
                        this.$message.success(res.msg);
                        this.load();
                    } else {
                        this.$message.error("修改失败");
                        this.load();
                    }
                })
        }
    }

}
</script>