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

    void setItemStatusP(String itemId);

    void setItemStatusN(String itemId);

    void updateInventoryQuantity(String itemId, int quantity);

    void addItem(Item item);

    void addItemInventory(Item item);

    void updateItem(Item item);

    void updateItemInventory(Item item);

    void deleteItem(String itemId);

    void deleteItemInventory(String itemId);

    List<Item> getAllItem();
}
