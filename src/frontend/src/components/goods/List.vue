<template>
  <div>
    <!-- 面包屑导航区 -->
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>商品管理</el-breadcrumb-item>
      <el-breadcrumb-item>商品列表</el-breadcrumb-item>
    </el-breadcrumb>
    <!-- 卡片视图 -->
    <el-card>
      <el-row :gutter="20">
        <el-col :span="6">
          <el-input placeholder="请输入内容" v-model="keywords" clearable @clear="getGoodsList">
            <el-button slot="append" icon="el-icon-search" @click="searchItem(keywords)"></el-button>
          </el-input>
        </el-col>
      </el-row>
      <!-- 表格数据 -->
      <el-table :data="goodsList" border stripe>
        <el-table-column type="index"></el-table-column>
        <el-table-column label="item-id" prop="itemId" width="70px">
          <template slot-scope="scope">
            <el-link style="color: #006fff" @click="viewItem(scope.row.product.productId)">{{scope.row.itemId}}</el-link>
          </template>
        </el-table-column>
        <el-table-column label="商品名称" prop="product.name" width="150px"></el-table-column>
        <el-table-column label="商品描述" prop="attribute1" width="200px"></el-table-column>
        <el-table-column label="商品价格(元)" prop="listPrice" width="100px"></el-table-column>
        <el-table-column label="成本(元)" prop="unitCost" width="100px"></el-table-column>
        <el-table-column label="商品数量" prop="quantity" width="70px"></el-table-column>
        <el-table-column label="供应商" prop="supplierId" width="70px"></el-table-column>
      </el-table>
      <!-- 分页区域 -->
      <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="queryInfo.pagenum"
        :page-sizes="[5, 10, 15, 20]"
        :page-size="queryInfo.pagesize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total"
        background
      ></el-pagination>
    </el-card>
  </div>
</template>

<script>
export default {
  data () {
    return {
      keywords: '',
      queryInfo: {
        // query: '',
        pagenum: 1,
        pagesize: 10
      },
      // 商品列表
      goodsList: [],
      // 搜索列表
      searchList: [],
      // 商品总数
      total: 0
    }
  },
  created () {
    this.getGoodsList()
  },
  methods: {
    // 根据分页获取对应的商品列表
    async getGoodsList () {
      const { data: res } = await this.$http.get('catalog/allItems', {
        params: this.queryInfo
      })
      if (res.status !== 200) {
        return this.$message.error('获取商品列表失败！')
      }
      console.log(res)
      this.goodsList = res.data.list
      this.total = res.data.total
    },
    handleSizeChange (newSize) {
      this.queryInfo.pagesize = newSize
      this.getGoodsList()
    },
    handleCurrentChange (newSize) {
      this.queryInfo.pagenum = newSize
      this.getGoodsList()
    },
    viewItem (productId) {
      console.log(productId)
      this.$router.push({
        path: `/item/${productId}`
      })
    },
    async searchItem (keywords) {
      await this.getGoodsList()
      let newList = []
      let total = 0
      let lowKeywords = keywords.toLowerCase()
      this.goodsList.forEach(item => {
        let productName = item.product.name.toLowerCase()
        if (productName.indexOf(lowKeywords) !== -1) {
          newList.push(item)
          total++
        }
      })
      this.goodsList = newList
      this.total = total
    }
  }
}
</script>

<style lang="less" scoped>
</style>
