package org.csu.mypetstore;

import org.csu.mypetstore.domain.Category;
import org.csu.mypetstore.persistence.service.CatalogService;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@MapperScan("org.csu.mypetstore.persistence")
class MypetstoreApplicationTests {
    @Autowired
    CatalogService catalogService;

    @Test
    void contextLoads() {
    }

    @Test
    void testCategory(){
        Category c =  catalogService.getCategory("BIRDS");
        System.out.println(c.getCategoryId()+","+c.getName()+","+c.getDescription());

    }

}
