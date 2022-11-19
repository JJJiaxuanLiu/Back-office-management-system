<template>
  <div>
    <el-container style="height: 100vh">
      <el-aside :width="asideWidth + 'px'" style="background-color: rgb(238, 241, 246) ">
        <el-menu :default-openeds="['1', '3']" style="min-height: 100%; overflow-x: hidden"
          background-color="rgb(48,65,86)" text-color="#fff" Active-text-color="ff04b" :collapse-transition="false"
          :collapse="isCollapse">
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
            <el-button type="primary" icon="el-icon-circle-plus-outline" @click="handleAdd">新增</el-button>
            <el-button type="danger" icon="el-icon-delete" @click="delBatch">删除</el-button>
            <el-button type="primary" icon="el-icon-download">导入</el-button>
            <el-button type="primary" icon="el-icon-upload2">导出</el-button>

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

        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script>
// @ is an alias to /src
import HelloWorld from '@/components/HelloWorld.vue'

export default {
  name: 'HomeView',
  data() {

    return {
      tableData: [],
      total: 0,
      pageNum: 1,
      pageSize: 5,
      username: '',
      email: '',
      address: '',
      dialogFormVisible: false,
      form: {},
      multipleSelection: [],
      collapaseBtnClass: 'el-icon-s-fold',
      isCollapse: false,
      asideWidth: 200,
      textShow: true,
      logoShow: false,
      smallLogoShow: true,
      editSave: false,
    }
  },
  created() {
    this.load();
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
    },

    save() {
      if (this.editSave) {
        this.request.put("/user", this.form).then(res => {
          if (res) {
            this.$message.success("保存成功");
            this.dialogFormVisible = false;

          } else {
            this.$message.error("保存失败");
          }
        })
        this.editSave = false;
      } else {
        this.request.post("/user", this.form).then(res => {
          if (res) {
            this.$message.success("保存成功");
            this.dialogFormVisible = false;
            this.load();
          } else {
            this.$message.error("保存失败");
          }
        })
      }


    },

    handleEdit(row) {
      this.form = row;
      this.dialogFormVisible = true;
      this.editSave = true;
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
      console.log(val);
    },

    delBatch() {
      this.$confirm('此操作将永久删除该文件, 是否继续?', '提示', {
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
        });
        this.load();
        this.$message({
          type: 'success',
          message: '删除成功!'
        });
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        });
      });


    },

    handleSizeChange(pageSize) {
      this.pageSize = pageSize;
      this.load();
    },
    handleCurrentChange(pageNum) {
      this.pageNum = pageNum;
      this.load();
    },


  }
}
</script>
