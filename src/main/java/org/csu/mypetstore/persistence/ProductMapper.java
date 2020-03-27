package org.csu.mypetstore.persistence;

import org.apache.ibatis.annotations.Mapper;
import org.csu.mypetstore.domain.Product;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface ProductMapper {
    //查商品
    List<Product> getProductListByCategory(String categoryId);
    //获取商品信息
    Product getProduct(String ProductId);
    //查询功能
    List<Product> searchProductList(String keywords);


}
