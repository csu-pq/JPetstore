package org.csu.mypetstore.persistence;

import org.apache.ibatis.annotations.Mapper;
import org.csu.mypetstore.domain.Category;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface CategoryMapper {

    Category getCategory(String categoryId);
}
