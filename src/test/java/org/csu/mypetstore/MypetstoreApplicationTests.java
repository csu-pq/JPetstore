package org.csu.mypetstore;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.csu.mypetstore.domain.*;
import org.csu.mypetstore.service.AccountService;
import org.csu.mypetstore.service.CartService;
import org.csu.mypetstore.service.CatalogService;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
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
//    @Test
//    void testItem(){
//        Item item = catalogService.getItem("EST-18");
//        printInfo(item);
//        System.out.println("--------------------------------------------------");
//        printInfo(item.getProduct());
//        System.out.println("--------------------------------------------------");
//
//        List<Item> itemList = catalogService.getItemListByProduct("K9-RT-02");
//        for(Item temp : itemList){
//            printInfo(temp);
//        }
//        System.out.println("--------------------------------------------------");
//
//        boolean isItemInStock = catalogService.isItemInStock("EST-26");
//        System.out.println(isItemInStock);
//        System.out.println("--------------------------------------------------");
//    }

    private void printInfo(Item item){
        System.out.println(item.getProductId()+","+item.getItemId()+","+item.getListPrice()+","+
                item.getUnitCost()+","+item.getSupplierId()+","+item.getStatus()+","+item.getQuantity());
    }

    @Test
    void testAccount(){
        //查询账户
        Account account = accountService.getAccount("a");
        System.out.println(account.getEmail()+","+account.getPassword()+","+account.getAddress1());
        //修改账户
        account.setUsername("abc1");
        account.setPassword("zyx1");
        accountService.updateAccount(account);
        //新建用户
        Account new_account=new Account();
        new_account.setUsername("new1");
        new_account.setPassword("1231");
        new_account.setFirstName("f1");
        new_account.setLastName("l1");
        new_account.setAddress1("ad11");
        new_account.setAddress2("ad21");
        new_account.setCity("changsha1");
        new_account.setCountry("China1");
        new_account.setEmail("new@csu.edu.cn1");
        new_account.setState("state1");
        new_account.setPhone("phone1");
        new_account.setZip("zip1");
        new_account.setLanguagePreference("language1");
        new_account.setFavouriteCategoryId("favorite1");
        accountService.insertAccount(new_account);
    }
    @Test
    void testCartItem()
    {
        cartService.addItem("1000","EST-1");
        CartItem cartItem = cartService.getCartItem("1000","EST-1");
        System.out.println(cartItem.getCartId()+", "+cartItem.getCategoryId()+","+cartItem.getItem().getProductId()+","+cartItem.getItemId()+", "+cartItem.getQuantity());
    }

    @Test
    void testPageInfo(){
        PageHelper.startPage(1, 3);
        List<Category> categoryList= catalogService.getCategoryList();
        PageInfo<Category> pageInfo = new PageInfo<>(categoryList);
        System.out.print(pageInfo);
    }
    @Test
    void testaddCategory(){
        Category category = new Category();
        category.setCategoryId("TIGERS");
        category.setName("Tigers");
        category.setDescription("6666666");
        catalogService.addCategory(category);
    }

    @Test
    void testUpdateCategory(){
        Category category = new Category();
        category.setCategoryId("TIGERS");
        category.setName("Tigers");
        category.setDescription("11111111");
        catalogService.updateCategory(category);
    }

    @Test
    void testDeleteCategory(){
        String categoryId = "TIGERS";
        catalogService.deleteCategory(categoryId);
    }
    @Test
    void testAddItem(){
        Item item = new Item();
        item.setQuantity(1);
        item.setAttribute1("222");
        item.setItemId("BBB");
        item.setProductId("AV-CB-01");
        item.setListPrice(new BigDecimal(1));
        item.setSupplierId(1);
        item.setStatus("P");
        catalogService.addItem(item);
    }
    @Test
    void addSupplier(){
        Supplier supplier = new Supplier();
        supplier.setSuppid("3");
        supplier.setUsername("1");
        supplier.setPassword("000");
        supplier.setStatus("CA");
        supplier.setEmail("1111");
        supplier.setPhone("11111");
        accountService.insertSupplier(supplier);
    }



}
