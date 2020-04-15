package org.csu.mypetstore.persistence;

import org.apache.ibatis.annotations.Mapper;
import org.csu.mypetstore.domain.Category;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface CategoryMapper {

    Category getCategory(String categoryId);

    void addCategory(Category category);

    void updateCategory(Category category);

    void deleteCategory(String categoryId);

    List<Category> getCategoryList();
}
