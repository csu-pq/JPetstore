package org.csu.mypetstore.service;

import org.csu.mypetstore.domain.Category;
import org.csu.mypetstore.domain.Product;
import org.csu.mypetstore.domain.Item;
import org.csu.mypetstore.persistence.CategoryMapper;
import org.csu.mypetstore.persistence.ItemMapper;
import org.csu.mypetstore.persistence.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

import java.util.List;

@Service
public class CatalogService {
    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private ItemMapper itemMapper;



    public Category getCategory(String categoryId){
        return categoryMapper.getCategory(categoryId);
    }
    public Product getProduct(String productId) {
        return this.productMapper.getProduct(productId);
    }
    public List<Product> getProductListByCategory(String categoryId) {
        return this.productMapper.getProductListByCategory(categoryId);
    }
    public List<Product> searchProductList(String keyword) {
        return productMapper.searchProductList("%" + keyword.toLowerCase() + "%");
    }

    public List<Item> getItemListByProduct(String productId){
        return itemMapper.getItemListByProduct(productId);
    }

    public Item getItem(String itemId){
        return itemMapper.getItem(itemId);
    }

    public boolean isItemInStock(String itemId){
        return itemMapper.getInventoryQuantity(itemId) > 0;
    }

    public int getItemInventoryQuantity(String itemId) {
        return itemMapper.getInventoryQuantity(itemId);
    }

    public List<Category> getCategoryList() {
        return categoryMapper.getCategoryList();
    }
    /**************************分割线***********************************/

    public void addCategory(Category category){ categoryMapper.addCategory(category); }

    public void updateCategory(Category category){
        categoryMapper.updateCategory(category);
    }

    public void deleteCategory(String categoryId){ categoryMapper.deleteCategory(categoryId); }

    public void addProduct(Product product){ productMapper.addProduct(product);}

    public void updateProduct(Product product){ productMapper.updateProduct(product);}

    public void deleteProduct(String productId){ productMapper.deleteProduct(productId);}

    public void addItem(Item item){
        itemMapper.addItem(item);
        itemMapper.addItemInventory(item);
    }

    public void updateItem(Item item){
        itemMapper.updateItem(item);
        itemMapper.updateItemInventory(item);
    }

    public void deleteItem(String itemId){
        itemMapper.deleteItem(itemId);
        itemMapper.deleteItemInventory(itemId);
    }

    public List<Item> getAllItem(){
        return itemMapper.getAllItem();
    }
}
