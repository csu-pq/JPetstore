<template>
  <div>
    <!-- 面包屑导航区 -->
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>商品管理</el-breadcrumb-item>
      <el-breadcrumb-item>商品分类</el-breadcrumb-item>
      <el-breadcrumb-item>{{this.$route.params.productId}}</el-breadcrumb-item>
    </el-breadcrumb>
    <!-- 卡片视图 -->
    <el-card>
      <el-row>
        <el-col>
          <el-button type="primary" @click="showAddItemDialog">上架商品</el-button>
        </el-col>
       </el-row>
      <el-table :data="itemList" stripe style="width: 75%">
        <el-table-column type="expand">
          <template slot-scope="scope">
            <el-form label-position="left" inline class="demo-table-expand">
              <el-form-item label="单价">
                <span>{{ scope.row.listPrice }}</span>
              </el-form-item>
              <el-form-item label="成本">
                <span>{{ scope.row.unitCost }}</span>
              </el-form-item>
              <el-form-item label="供应商">
                <span>{{ scope.row.supplierId }}</span>
              </el-form-item>
              <el-form-item label="库存">
                <span>{{scope.row.quantity}}</span>
              </el-form-item>
            </el-form>
          </template>
        </el-table-column>
        <el-table-column label="商品ID" prop="itemId">
<!--          <template slot-scope="scope">-->
<!--            <el-link style="color: #006fff" @click="viewItem(scope.row.itemId)">{{scope.row.itemId}}</el-link>-->
<!--          </template>-->
        </el-table-column>
        <el-table-column label="商品名称" prop="product.name"></el-table-column>
        <el-table-column label="商品描述" prop="attribute1"></el-table-column>
<!--        <el-table-column label="分级" >-->
<!--          <template slot>-->
<!--            <el-tag size="mini" type="warning" >三级</el-tag>-->
<!--          </template>-->
<!--        </el-table-column>-->
        <el-table-column label="操作">
          <template slot-scope="scope">
            <el-button type="primary" icon="el-icon-edit" size="mini" @click="showEditItemDialog(scope.$index,scope.row.supplierId)">编辑</el-button>
            <el-button type="danger" icon="el-icon-delete" size="mini" @click="removeItem(scope.row.itemId,scope.row.supplierId)">删除</el-button>
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
    <el-dialog title="Add Item" :visible.sync="addItemDialogVisible" width="50%" @close="itemDialogClosed">
      <el-form
        :model="itemForm"
        :rules="itemFormRules"
        ref="addItemFormRef"
        label-width="125px"
      >
        <el-form-item label="ItemId：" prop="itemId">
          <el-input v-model="itemForm.itemId"></el-input>
        </el-form-item>
        <el-form-item label="ProductId：" prop="productId" >
          <el-input v-model="itemForm.productId" :disabled=true></el-input>
        </el-form-item>
        <el-form-item label="单价：" prop="listPrice">
          <el-input v-model="itemForm.listPrice"></el-input>
        </el-form-item>
        <el-form-item label="成本：" prop="unitCost">
          <el-input v-model="itemForm.unitCost"></el-input>
        </el-form-item>
        <el-form-item label="供应商：" prop="supplierId">
          <el-input v-model="itemForm.supplierId" :disabled=true></el-input>
        </el-form-item>
        <el-form-item label="状态：" prop="status">
          <el-input v-model="itemForm.status"></el-input>
        </el-form-item>
        <el-form-item label="属性：" prop="attribute1">
          <el-input v-model="itemForm.attribute1"></el-input>
        </el-form-item>
        <el-form-item label="库存：" prop="quantity">
          <el-input v-model="itemForm.quantity"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="addItemDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="addItem">确 定</el-button>
      </span>
    </el-dialog>
    <!-- 编辑分类的对话框 -->
    <el-dialog title="Add Item" :visible.sync="editItemDialogVisible" width="50%" @close="itemDialogClosed">
      <el-form
        :model="itemForm"
        :rules="itemFormRules"
        ref="editItemFormRef"
        label-width="125px"
      >
        <el-form-item label="ItemId：" prop="itemId">
          <el-input v-model="itemForm.itemId" :disabled=true></el-input>
        </el-form-item>
        <el-form-item label="ProductId：" prop="productId" >
          <el-input v-model="itemForm.productId" :disabled=true></el-input>
        </el-form-item>
        <el-form-item label="单价：" prop="listPrice">
          <el-input v-model="itemForm.listPrice"></el-input>
        </el-form-item>
        <el-form-item label="成本：" prop="unitCost">
          <el-input v-model="itemForm.unitCost"></el-input>
        </el-form-item>
        <el-form-item label="供应商：" prop="supplierId">
          <el-input v-model="itemForm.supplierId" :disabled=true></el-input>
        </el-form-item>
        <el-form-item label="状态：" prop="status">
          <el-input v-model="itemForm.status"></el-input>
        </el-form-item>
        <el-form-item label="属性：" prop="attribute1">
          <el-input v-model="itemForm.attribute1"></el-input>
        </el-form-item>
        <el-form-item label="库存：" prop="quantity">
          <el-input v-model="itemForm.quantity"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="editItemDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="editItem">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
export default {
  data () {
    return {
      supplierId: -1,
      // 商品分类数据
      itemList: [],
      // 查询条件
      queryInfo: {
        productId: '',
        pagenum: 1,
        pagesize: 5
      },
      total: 0,
      // 添加分类
      addItemDialogVisible: false,
      // 添加分类对象
      itemForm: {
        itemId: '',
        productId: '',
        listPrice: '',
        unitCost: 0,
        supplierId: 0,
        status: 'P',
        attribute1: '',
        // attribute2,
        // attribute3,
        // attribute4,
        // attribute5,
        quantity: 0
      },
      // 添加分类表单的验证规则
      itemFormRules: {
        itemId: [
          { required: true, message: '请输入分类名称', trigger: 'blur' }
        ]
      },
      // 编辑对话框 控制
      editItemDialogVisible: false
      // 编辑分类表单验证
    }
  },
  created () {
    this.supplierId = JSON.parse(window.sessionStorage.getItem('user')).id
    this.queryInfo.productId = this.$route.params.productId
    console.log(this.supplierId)
    this.getItem()
  },
  methods: {
    async getItem () {
      const { data: res } = await this.$http.get('catalog/items', {
        params: this.queryInfo
      })
      console.log(res)
      if (res.status !== 200) {
        return this.$message.error('获取商品分类失败！')
      }
      // 给数据列表赋值
      this.itemList = res.data.list
      // 总数据条数
      this.total = res.data.total
    },
    handleSizeChange (newSize) {
      this.queryInfo.pagesize = newSize
      this.getItem()
    },
    // 监听 pagenum改变
    handleCurrentChange (newPage) {
      this.queryInfo.pagenum = newPage
      this.getItem()
    },
    // 添加操作
    showAddItemDialog () {
      this.itemForm.supplierId = this.supplierId
      this.itemForm.productId = this.$route.params.productId
      console.log(this.itemForm.productId)
      // 再打开对话框
      this.addItemDialogVisible = true
    },
    // 添加分类 选择项发生变化触发
    // 添加分类
    addItem () {
      this.$refs.addItemFormRef.validate(async valid => {
        if (!valid) return
        const { data: res } = await this.$http.post('catalog/items', this.itemForm)
        if (res.status !== 200) {
          return this.$message.error('添加分类失败！')
        }
        this.$message.success('添加分类成功！')
        this.getItem()
        this.addItemDialogVisible = false
      })
    },
    // 添加分类 重置表单
    itemDialogClosed () {
      this.itemForm = {}
    },
    // 删除分类
    async removeItem (itemId, supplierId) {
      console.log(itemId, supplierId)
      // if (supplierId !== JSON.parse(window.sessionStorage.getItem('user')).id) {
      //   return this.$message.error('你没有权限！')
      // }
      const confirmResult = await this.$confirm('此操作将永久删除该分类, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).catch(err => err)
      if (confirmResult !== 'confirm') {
        return this.$message.info('已取消删除！')
      }
      const { data: res } = await this.$http.delete('catalog/items?itemId=' + itemId)
      if (res.status !== 200) {
        return this.$message.error('删除商品分类失败！')
      }
      this.$message.success('删除商品分类成功！')
      this.getItem()
    },
    // 显示编辑对话框
    async showEditItemDialog (index, supplierId) {
      // if (supplierId !== JSON.parse(window.sessionStorage.getItem('user')).id) { return this.$message.error('你没有权限！') }
      this.itemForm = this.itemList[index]
      this.itemForm.productId = this.$route.params.productId
      this.editItemDialogVisible = true
    },
    // 编辑分类名
    editItem () {
      this.$refs.editItemFormRef.validate(async valid => {
        if (!valid) return
        const { data: res } = await this.$http.put('catalog/items', this.itemForm)
        if (res.status !== 200) return this.$message.error('更新分类名失败！')
        this.$message.success('更新分类名成功！')
        this.getItem()
        this.editItemDialogVisible = false
      })
    }
  }
}
</script>

<style lang = 'less' scoped>
  .treeTable {
    margin-top: 20px
  }
  /*.demo-table-expand .el-item-form {*/
  /*  margin-right: 0;*/
  /*  margin-bottom: 0;*/
  /*  width: 25%;*/
  /*}*/
  .demo-table-expand span {
    width: 90px;
    color: #99a9bf;
    margin-right: 100px;
  }
</style>
