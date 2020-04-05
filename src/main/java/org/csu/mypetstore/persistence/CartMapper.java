package org.csu.mypetstore.persistence;

import org.csu.mypetstore.domain.Cart;
import org.csu.mypetstore.domain.CartItem;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface CartMapper {

    Cart getCartByUsername(String username);

    void addCart(int cartid,String username);

    void deleteCart(String username);

    void updateCart(Map<String, Object> map);

    void removeCartItem(Map<String, Object> map);

    List<CartItem> getCartItemListByUsername(String username);
}
