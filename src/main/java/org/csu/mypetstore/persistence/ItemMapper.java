package org.csu.mypetstore.persistence;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.csu.mypetstore.domain.Item;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface ItemMapper {
    void updateInventoryQuantity(Map<String, Object> param);

    int getInventoryQuantity(String itemId);

    List<Item> getItemListByProduct(String productId);

    Item getItem(String itemId);
}
