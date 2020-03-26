package org.csu.mypetstore.persistence.service;

import org.csu.mypetstore.domain.Category;
import org.csu.mypetstore.persistence.CategoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CatalogService {
    @Autowired
    private CategoryMapper categoryMapper;

    public Category getCategory(String categoryId){
        return categoryMapper.getCategory(categoryId);
    }
}
