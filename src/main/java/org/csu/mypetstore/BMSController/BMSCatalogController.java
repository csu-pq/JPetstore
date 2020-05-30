package org.csu.mypetstore.BMSController;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.csu.mypetstore.domain.Category;
import org.csu.mypetstore.domain.Item;
import org.csu.mypetstore.domain.Product;
import org.csu.mypetstore.service.CatalogService;
import org.csu.mypetstore.utils.ResultFactory;
import org.csu.mypetstore.domain.Item;
import org.csu.mypetstore.service.CatalogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bms/catalog")
public class BMSCatalogController {
    @Autowired
    private CatalogService catalogService;

    @GetMapping("/categories")
    public ResultFactory viewCategory(@RequestParam(value = "pagenum")int pagenum,@RequestParam(value = "pagesize")int pageSize) {
        PageHelper.startPage(pagenum, pageSize);
        List<Category> categoryList= catalogService.getCategoryList();
        PageInfo<Category> pageInfo = new PageInfo<>(categoryList);
        return ResultFactory.successResult(pageInfo,"查询成功");
    }
    @GetMapping("/products")
    public ResultFactory viewProduct(@RequestParam(value = "pagenum")int pagenum,@RequestParam(value = "pagesize")int pageSize,@RequestParam(value = "categoryId")String categoryId) {
        PageHelper.startPage(pagenum, pageSize);
        List<Product> productsList= catalogService.getProductListByCategory(categoryId);
        PageInfo<Product> pageInfo = new PageInfo<>(productsList);
        return ResultFactory.successResult(pageInfo,"查询成功");
    }
    @GetMapping("/items")
    public ResultFactory viewItem(@RequestParam(value = "pagenum")int pagenum,@RequestParam(value = "pagesize")int pageSize,@RequestParam(value = "productId")String productId) {
        PageHelper.startPage(pagenum, pageSize);
        List<Item> itemList= catalogService.getItemListByProduct(productId);
        for(int i=0;i<itemList.size();i++)
        {
            String itemid=itemList.get(i).getItemId();
            int qty=catalogService.getItemInventoryQuantity(itemid);
            itemList.get(i).setQuantity(qty);
        }
        PageInfo<Item> pageInfo = new PageInfo<>(itemList);
        return ResultFactory.successResult(pageInfo,"查询成功");
    }
    @PostMapping("/categories")
    public ResultFactory addCategory(@RequestBody Category addCateForm) {
        catalogService.addCategory(addCateForm);
        return ResultFactory.successResult(null,"成功");
    }
    @PutMapping("/categories")
    public ResultFactory updateCategory(@RequestBody Category addCateForm) {
        catalogService.updateCategory(addCateForm);
        return ResultFactory.successResult(null,"成功");
    }
    @DeleteMapping("/categories")
    public ResultFactory deleteCategory(@RequestParam String categoryId) {
        catalogService.deleteCategory(categoryId);
        return ResultFactory.successResult(null,"成功");
    }

    @PostMapping("/products")
    public ResultFactory addProduct(@RequestBody Product addCateForm) {
        catalogService.addProduct(addCateForm);
        return ResultFactory.successResult(null,"成功");
    }
    @PutMapping("/products")
    public ResultFactory updateProduct(@RequestBody Product addCateForm) {
        catalogService.updateProduct(addCateForm);
        return ResultFactory.successResult(null,"成功");
    }
    @DeleteMapping("/products")
    public ResultFactory deleteProduct(@RequestParam String productId) {
        catalogService.deleteProduct(productId);
        return ResultFactory.successResult(null,"成功");
    }


    @PostMapping("/items")
    public ResultFactory addItem(@RequestBody Item addCateForm) {
        catalogService.addItem(addCateForm);
        return ResultFactory.successResult(null,"成功");
    }
    @PutMapping("/items")
    public ResultFactory updateItem(@RequestBody Item addCateForm) {
        catalogService.updateItem(addCateForm);
        return ResultFactory.successResult(null,"成功");
    }
    @DeleteMapping("/items")
    public ResultFactory deleteItem(@RequestParam String itemId) {
        catalogService.deleteItem(itemId);
        return ResultFactory.successResult(null,"成功");
    }

    @GetMapping("/allItems")
    public ResultFactory getAllItem(@RequestParam(value = "pagenum")int pagenum,@RequestParam(value = "pagesize")int pageSize){
        PageHelper.startPage(pagenum, pageSize);
        List<Item> itemList = catalogService.getAllItem();
        System.out.println(itemList.get(0).getQuantity());
        System.out.println(itemList.get(1).getQuantity());
        PageInfo<Item> pageInfo = new PageInfo<>(itemList);
        return ResultFactory.successResult(pageInfo,"成功");
    }


}
