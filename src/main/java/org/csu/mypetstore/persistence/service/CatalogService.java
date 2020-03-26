package org.csu.mypetstore.persistence.service;

import org.csu.mypetstore.domain.Category;
import org.csu.mypetstore.domain.Product;
import org.csu.mypetstore.persistence.CategoryMapper;
import org.csu.mypetstore.persistence.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Service
public class CatalogService {
    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private ProductMapper productMapper;



    public Category getCategory(String categoryId){
        return categoryMapper.getCategory(categoryId);
    }
    public Product getProduct(String productId) {
        return this.productMapper.getProduct(productId);
    }
    public List<Product> getProductListByCategory(String categoryId) {
        return this.productMapper.getProductListByCategory(categoryId);
    }

}
