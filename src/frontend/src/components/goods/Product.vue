<template>
  <div>
    <!-- 面包屑导航区 -->
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>商品管理</el-breadcrumb-item>
      <el-breadcrumb-item>商品分类</el-breadcrumb-item>
      <el-breadcrumb-item>{{this.$route.params.categoryId}}</el-breadcrumb-item>
    </el-breadcrumb>
    <!-- 卡片视图 -->
    <el-card>
      <el-row>
        <el-col>
          <el-button type="primary" @click="showAddProductDialog">Add Product</el-button>
        </el-col>
      </el-row>
      <el-table :data="productList" stripe style="width: 75%">
        <el-table-column type="index" label="#"></el-table-column>
        <el-table-column label="商品ID" prop="productId">
          <template slot-scope="scope">
            <el-link style="color: #006fff" @mouseover.native="showImage(scope.$index)" @mouseleave.native="closeImage" @click="viewItem(scope.row.productId)">{{scope.row.productId}}</el-link>
          </template>
        </el-table-column>
        <el-table-column label="商品名称" prop="name"></el-table-column>
        <el-table-column label="分级" >
          <template slot>
            <el-tag size="mini" type="warning" >二级</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作">
          <template slot-scope="scope">
            <el-button type="primary" icon="el-icon-edit" size="mini" @click="showEditProductDialog(scope.$index)">编辑</el-button>
            <el-button type="danger" icon="el-icon-delete" size="mini" @click="removeProduct(scope.row.productId)">删除</el-button>
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
    <el-dialog title="Add Product" :visible.sync="addProductDialogVisible" width="50%" @close="productDialogClosed">
      <el-form
        :model="productForm"
        :rules="productFormRules"
        ref="addProductFormRef"
        label-width="125px"
      >
        <el-form-item label="ProductId：" prop="productId">
          <el-input v-model="productForm.productId"></el-input>
        </el-form-item>
        <el-form-item label="CategoryId：" prop="categoryId" >
          <el-input v-model="productForm.categoryId" :disabled=true></el-input>
        </el-form-item>
        <el-form-item label="Name：" prop="name">
          <el-input v-model="productForm.name"></el-input>
        </el-form-item>
        <el-form-item label="Description：" prop="description">
          <el-input v-model="productForm.description"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="addProductDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="addProduct">确 定</el-button>
      </span>
    </el-dialog>
    <!-- 编辑分类的对话框 -->
    <el-dialog title="Add Product" :visible.sync="editProductDialogVisible" width="50%" @close="productDialogClosed">
      <el-form
        :model="productForm"
        :rules="productForm"
        ref="editProductFormRef"
        label-width="125px"
      >
        <el-form-item label="ProductId：" prop="productId">
          <el-input v-model="productForm.productId" :disabled=true></el-input>
        </el-form-item>
        <el-form-item label="CategoryId：" prop="categoryId" >
          <el-input v-model="productForm.categoryId" :disabled=true ></el-input>
        </el-form-item>
        <el-form-item label="Name：" prop="name">
          <el-input v-model="productForm.name"></el-input>
        </el-form-item>
        <el-form-item label="Description：" prop="description">
          <el-input v-model="productForm.description"></el-input>
        </el-form-item>
        <el-form-item label="Image：">
          <span v-html="productForm.descriptionImage"></span>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="editProductDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="editProduct">确 定</el-button>
      </span>
    </el-dialog>
    <div class="productImage" v-html="productImage" v-if="productImage">
    </div>
  </div>
</template>

<script>
export default {
  data () {
    return {
      // 商品分类数据
      productList: [],
      // product的图片与信息
      productImage: '',
      // 查询条件
      queryInfo: {
        categoryId: '',
        pagenum: 1,
        pagesize: 5
      },
      total: 0,
      // 添加分类
      addProductDialogVisible: false,
      // 添加分类对象
      productForm: {
        productId: '',
        categoryId: '',
        name: '',
        description: '',
        descriptionImage: ''
      },
      // 添加分类表单的验证规则
      productFormRules: {
        cat_name: [
          { required: true, message: '请输入分类名称', trigger: 'blur' }
        ]
      },
      // 编辑对话框 控制
      editProductDialogVisible: false
      // 编辑分类表单验证
    }
  },
  created () {
    this.queryInfo.categoryId = this.$route.params.categoryId
    console.log(this.queryInfo.categoryId)
    this.getProduct()
  },
  methods: {
    async getProduct () {
      const { data: res } = await this.$http.get('catalog/products', {
        params: this.queryInfo
      })
      console.log(res)
      if (res.status !== 200) {
        return this.$message.error('获取商品分类失败！')
      }
      // 给数据列表赋值
      this.productList = res.data.list
      this.productList.forEach(item => {
        item.descriptionImage = item.description.split('>')[0] + '>'
        item.descriptionText = item.description.split('>')[1]
        // item.descriptionImage = item.description.split('"')[1]
        // item.descriptionImage = require(item.description.split('"')[1])
      })
      // 总数据条数
      this.total = res.data.total
    },
    showImage (index) {
      console.log(index)
      this.productImage = this.productList[index].descriptionImage
    },
    closeImage () {
      this.productImage = ''
    },
    viewItem (productId) {
      this.$router.push({
        path: `/item/${productId}`
      })
    },
    handleSizeChange (newSize) {
      this.queryInfo.pagesize = newSize
      this.getProduct()
    },
    // 监听 pagenum改变
    handleCurrentChange (newPage) {
      this.queryInfo.pagenum = newPage
      this.getProduct()
    },
    // 添加操作
    showAddProductDialog () {
      this.productForm.categoryId = this.$route.params.categoryId
      console.log(this.productForm.categoryId)
      // 再打开对话框
      this.addProductDialogVisible = true
    },
    // 添加分类 选择项发生变化触发
    addProduct () {
      this.$refs.addProductFormRef.validate(async valid => {
        if (!valid) return
        const { data: res } = await this.$http.post('catalog/products', this.productForm)
        if (res.status !== 200) {
          return this.$message.error('添加分类失败！')
        }
        this.$message.success('添加分类成功！')
        this.getProduct()
        this.addProductDialogVisible = false
      })
    },
    // 添加分类 重置表单
    productDialogClosed () {
      this.productForm = {}
    },
    // 删除分类
    async removeProduct (productId) {
      console.log(productId)
      const confirmResult = await this.$confirm('此操作将永久删除该分类, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).catch(err => err)
      if (confirmResult !== 'confirm') {
        return this.$message.info('已取消删除！')
      }
      const { data: res } = await this.$http.delete('catalog/products?productId=' + productId)
      if (res.status !== 200) {
        return this.$message.error('删除商品分类失败！')
      }
      this.$message.success('删除商品分类成功！')
      this.getProduct()
    },
    // 显示编辑对话框
    async showEditProductDialog (index) {
      this.productForm = this.productList[index]
      this.editProductDialogVisible = true
    },
    // 编辑分类名
    editProduct () {
      this.$refs.editProductFormRef.validate(async valid => {
        if (!valid) return
        const { data: res } = await this.$http.put('catalog/products', this.productForm)
        if (res.status !== 200) return this.$message.error('更新分类名失败！')
        this.$message.success('更新分类名成功！')
        this.getProduct()
        this.editProductDialogVisible = false
      })
    }
  }
}
</script>

<style lang = 'less' scoped>
.treeTable {
  margin-top: 20px
}

.el-carousel__item img {
  /*color: #475669;*/
  /*font-size: 14px;*/
  /*opacity: 0.75;*/
  /*line-height: 200px;*/
  /*margin: 0;*/
  width: 100%;
}

.el-carousel__item:nth-child(2n) {
  background-color: #99a9bf;
}

.el-carousel__item:nth-child(2n+1) {
  background-color: #d3dce6;
}
</style>
