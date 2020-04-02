package org.csu.mypetstore.controller;

import org.csu.mypetstore.domain.Category;
import org.csu.mypetstore.domain.Item;
import org.csu.mypetstore.domain.Product;
import org.csu.mypetstore.service.CatalogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.annotation.SessionScope;

import java.util.List;

@Controller
@SessionScope
@RequestMapping("/catalog")
public class CatalogController {
    @Autowired
    private CatalogService catalogService;

    @GetMapping("/index")
    public String view(){
        return "catalog/main";
    }

    @GetMapping("/viewCategory")
    public String viewCategory(String categoryId, Model model){
        if (categoryId != null){
            Category category = catalogService.getCategory(categoryId);
            List<Product> productList = catalogService.getProductListByCategory(categoryId);
            model.addAttribute("category",category);
            model.addAttribute("productList",productList);

        }
        return "catalog/category";
    }
    @GetMapping("viewProduct")
    public String viewProduct(String productId,Model model){
        if (productId != null) {
            List<Item> itemList = catalogService.getItemListByProduct(productId);
            Product product = catalogService.getProduct(productId);
            model.addAttribute("product", product);
            model.addAttribute("itemList", itemList);
        }
        return "catalog/product";
    }
    @GetMapping("viewItem")
    public String viewItem(String itemId,Model model){
        Item item = catalogService.getItem(itemId);
        Product product = item.getProduct();
        processProductDescription(product);
        model.addAttribute("item",item);
        model.addAttribute("product",product);
        return "catalog/item";
    }
    @PostMapping("searchProducts")
    public String searchProducts(String keyword, Model model){
        if(keyword == null || keyword.length() < 1){
            String msg = "Please enter a keyword to search for, then press the search button.";
            model.addAttribute("msg",msg);
            return "common/error";
        }else {
            List<Product> productList = catalogService.searchProductList(keyword.toLowerCase());
            processProductDescription(productList);
            model.addAttribute("productList",productList);
            return "catalog/search_products";
        }

    }


    /*
        解决Thymeleaf将数据库中的Product的描述(description属性)中的<image>标签解析成普通文本的问题。
        本方法在Product中添加了imageURL属性，相当于将product的描述信息分成两部分处理了。
        同样，界面上也是用了两个标签了，一个img标签和一个lable标签。
        此方法是快速解决上述问题的临时方案，更好的方法应是更改数据库结构，将图片信息和普通文字描述信息分为两个字段存储。
     */
    private void processProductDescription(Product product){
        String [] temp = product.getDescription().split("\"");
        product.setDescriptionImage(temp[1]);
        product.setDescriptionText(temp[2].substring(1));
    }
    private void processProductDescription(List<Product> productList){
        for(Product product : productList) {
            processProductDescription(product);
        }
    }
}
