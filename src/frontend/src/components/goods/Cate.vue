<template>
  <div>
    <!-- 面包屑导航区 -->
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>商品管理</el-breadcrumb-item>
      <el-breadcrumb-item>商品分类</el-breadcrumb-item>
    </el-breadcrumb>
    <!-- 卡片视图 -->
    <el-card>
      <el-row>
        <el-col>
          <el-button type="primary" @click="showAddCateDialog">Add Category</el-button>
        </el-col>
      </el-row>
      <el-table :data="cateList" stripe style="width: 75%" current-row-key="index">
        <el-table-column type="index" label="#"></el-table-column>
        <el-table-column label="商品ID" prop="categoryId">
          <template slot-scope="scope">
           <el-link style="color: #006fff" @click="viewProduct(scope.row.categoryId)">{{scope.row.categoryId}}</el-link>
          </template>
        </el-table-column>
        <el-table-column label="商品名称" prop="name"></el-table-column>
        <el-table-column label="分级" >
          <template slot>
            <el-tag size="mini" type="warning" >一级</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作">
          <template slot-scope="scope">
            <el-button type="primary" icon="el-icon-edit" size="mini" @click="showEditCateDialog(scope.$index)">编辑</el-button>
            <el-button type="danger" icon="el-icon-delete" size="mini" @click="removeCate(scope.row.categoryId)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
      <!-- 分页 -->
      <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="queryInfo.pagenum"
        :page-sizes="[3, 5, 10, 15]"
        :page-size="queryInfo.pagesize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total"
      ></el-pagination>
    </el-card>
    <!-- 添加分类的对话框 -->
    <el-dialog title="Add Category" :visible.sync="addCateDialogVisible" width="50%" @close="addCateDialogClosed">
      <el-form
        :model="addCateForm"
        :rules="addCateFormRules"
        ref="addCateFormRef"
        label-width="125px"
      >
        <el-form-item label="CategoryId：" prop="categoryId">
          <el-input v-model="addCateForm.categoryId"></el-input>
        </el-form-item>
        <el-form-item label="Name：" prop="name">
          <el-input v-model="addCateForm.name"></el-input>
        </el-form-item>
        <el-form-item label="Description：" prop="description">
          <el-input v-model="addCateForm.description"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="addCateDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="addCate">确 定</el-button>
      </span>
    </el-dialog>
    <!-- 编辑分类的对话框 -->
    <el-dialog title="Add Category" :visible.sync="editCateDialogVisible" width="50%" @close="addCateDialogClosed">
      <el-form
        :model="addCateForm"
        :rules="addCateFormRules"
        ref="editCateFormRef"
        label-width="125px"
      >
        <el-form-item label="CategoryId：" prop="categoryId">
          <el-input :disabled="true" v-model="addCateForm.categoryId"></el-input>
        </el-form-item>
        <el-form-item label="Name：" prop="name">
          <el-input v-model="addCateForm.name"></el-input>
        </el-form-item>
        <el-form-item label="Description：" prop="description">
          <el-input v-model="addCateForm.description"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="editCateDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="editCate">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
export default {
  data () {
    return {
      // 商品分类数据
      cateList: [],
      // 查询条件
      queryInfo: {
        // type: 3,
        pagenum: 1,
        pagesize: 5
      },
      total: 0,
      // 添加分类
      addCateDialogVisible: false,
      // 添加分类对象
      addCateForm: {
        // 将要添加分类名称
        name: '',
        // 分类id
        categoryId: '',
        // 描述
        description: ''
      },
      // 添加分类表单的验证规则
      addCateFormRules: {
        name: [
          { required: true, message: '请输入分类名称', trigger: 'blur' }
        ],
        categoryId: [
          { required: true, message: '请输入分类id', trigger: 'blur' }
        ]
      },
      // 编辑对话框 控制
      editCateDialogVisible: false,
      // 编辑表单 绑定对象
      editCateForm: ''
    }
  },
  created () {
    this.getCategory()
  },
  methods: {
    async getCategory () {
      // `params` 是即将与请求一起发送的 URL 参数
      const { data: res } = await this.$http.get('catalog/categories', {
        params: this.queryInfo
      })
      console.log(res)
      if (res.status !== 200) {
        return this.$message.error('获取商品分类失败！')
      }
      // 给数据列表赋值
      this.cateList = res.data.list
      // 总数据条数
      this.total = res.data.total
    },
    viewProduct (categoryId) {
      this.$router.push({
        path: `/product/${categoryId}`
      })
    },
    // 监听 pageSizeChange
    handleSizeChange (newSize) {
      this.queryInfo.pagesize = newSize
      this.getCategory()
    },
    // 监听 pagenum改变
    handleCurrentChange (newPage) {
      this.queryInfo.pagenum = newPage
      this.getCategory()
    },
    // 添加操作
    showAddCateDialog () {
      // 再打开对话框
      this.addCateDialogVisible = true
    },
    // 添加分类
    addCate () {
      this.$refs.addCateFormRef.validate(async valid => {
        if (!valid) return
        const { data: res } = await this.$http.post('catalog/categories', this.addCateForm)
        console.log(res)
        if (res.status !== 200) {
          return this.$message.error('添加分类失败！')
        }
        this.$message.success('添加分类成功！')
        this.getCategory()
        this.addCateDialogVisible = false
      })
    },
    // 添加分类 重置表单
    addCateDialogClosed () {
      this.addCateForm = {}
    },
    // 删除分类
    async removeCate (categoryId) {
      console.log(categoryId)
      const confirmResult = await this.$confirm('此操作将永久删除该分类, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).catch(err => err)
      if (confirmResult !== 'confirm') { return this.$message.info('已取消删除！') }
      const { data: res } = await this.$http.delete('catalog/categories?categoryId=' + categoryId)
      if (res.status !== 200) { return this.$message.error('删除商品分类失败！') }
      this.$message.success('删除商品分类成功！')
      this.getCategory()
    },
    // 显示编辑对话框
    async showEditCateDialog (index) {
      this.addCateForm = this.cateList[index]
      this.editCateDialogVisible = true
    },
    // 编辑分类名
    editCate () {
      this.$refs.editCateFormRef.validate(async valid => {
        if (!valid) return
        const { data: res } = await this.$http.put('catalog/categories', this.addCateForm)
        if (res.status !== 200) return this.$message.error('更新分类名失败！')
        this.$message.success('更新分类名成功！')
        this.getCategory()
        this.editCateDialogVisible = false
      })
    }
  }
}
</script>

<style lang='less' scoped>
.treeTable {
  margin-top: 20px;
}

</style>
