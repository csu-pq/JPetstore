package org.csu.mypetstore;

import org.csu.mypetstore.domain.Category;
import org.csu.mypetstore.domain.Item;
import org.csu.mypetstore.domain.Product;
import org.csu.mypetstore.persistence.service.CatalogService;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
@MapperScan("org.csu.mypetstore.persistence")
class MypetstoreApplicationTests {
    @Autowired
    private CatalogService catalogService;

    @Test
    void contextLoads() {
    }



    @Test
    void testCategory(){
        Category c =  catalogService.getCategory("BIRDS");
        System.out.println(c.getCategoryId()+","+c.getName()+","+c.getDescription());

    }
    private void printInfo(Category category){
        System.out.println(category.getCategoryId()+","+category.getName()+","+category.getDescription());
    }
    @Test
    void testProduct(){
        List<Product> productlist = catalogService.getProductListByCategory("Birds");
        System.out.println(productlist.size());
        System.out.println("--------------------------------------------------");
        List<Product> searchProductList = catalogService.searchProductList("dog");
        for(Product temp : searchProductList){
            printInfo(temp);

        }
        System.out.println("--------------------------------------------------");
    }
    private void printInfo(Product product){
        System.out.println(product.getCategoryId()+","+product.getProductId()+","+product.getName()+","+product.getDescription());
    }
    @Test
    void testItem(){
        Item item = catalogService.getItem("EST-18");
        printInfo(item);
        System.out.println("--------------------------------------------------");
        printInfo(item.getProduct());
        System.out.println("--------------------------------------------------");

        List<Item> itemList = catalogService.getItemListByProduct("K9-RT-02");
        for(Item temp : itemList){
            printInfo(temp);
        }
        System.out.println("--------------------------------------------------");

        boolean isItemInStock = catalogService.isItemInStock("EST-26");
        System.out.println(isItemInStock);
        System.out.println("--------------------------------------------------");
    }

    private void printInfo(Item item){
        System.out.println(item.getProductId()+","+item.getItemId()+","+item.getListPrice()+","+
                item.getUnitCost()+","+item.getSupplierId()+","+item.getStatus()+","+item.getQuantity());
    }



}
