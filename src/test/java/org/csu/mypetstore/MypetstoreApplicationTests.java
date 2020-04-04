package org.csu.mypetstore;

import org.csu.mypetstore.domain.*;
import org.csu.mypetstore.service.AccountService;
import org.csu.mypetstore.service.CartService;
import org.csu.mypetstore.service.CatalogService;
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
    @Autowired
    private AccountService accountService;
    @Autowired
    private CartService cartService;

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

    @Test
    void testAccount(){
        //查询账户
        Account account = accountService.getAccount("j2ee");
        System.out.println(account.getEmail()+","+account.getPassword()+","+account.getAddress1());
        //修改账户
        account.setUsername("abc");
        account.setPassword("zyx");
        accountService.updateAccount(account);
        //新建用户
        Account new_account=new Account();
        new_account.setUsername("new");
        new_account.setPassword("123");
        new_account.setFirstName("f");
        new_account.setLastName("l");
        new_account.setAddress1("ad1");
        new_account.setAddress2("ad2");
        new_account.setCity("changsha");
        new_account.setCountry("China");
        new_account.setEmail("new@csu.edu.cn");
        new_account.setState("state");
        new_account.setPhone("phone");
        new_account.setZip("zip");
        new_account.setLanguagePreference("language");
        new_account.setFavouriteCategoryId("favorite");
        accountService.insertAccount(new_account);
    }
    @Test
    void testCartItem()
    {
        cartService.addItem("1000","EST-1");
        CartItem cartItem = cartService.getCartItem("1000","EST-1");
        System.out.println(cartItem.getCartId()+", "+cartItem.getCategoryId()+","+cartItem.getItem().getProductId()+","+cartItem.getItemId()+", "+cartItem.getQuantity());
    }



}
