package org.csu.mypetstore.persistence;

import org.apache.ibatis.annotations.Param;
import org.csu.mypetstore.domain.Cart;
import org.csu.mypetstore.domain.CartItem;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartItemMapper {

    List<CartItem> getCartItemList (String cartId);

    CartItem getCartItem(String cartId, String itemId);

    void insertCartItem(@Param("cartId") String cartId, @Param("itemId") String itemId, @Param("quantity") int quantity);

    void deleteCartItem(@Param("cartId") String cartId, @Param("itemId") String itemId);

    void deleteCartItemByCart(@Param("cartId") String cartId);

    void updateQuantity(@Param("cartId") String cartId,@Param("itemId") String itemId, @Param("quantity") int quantity);

}
