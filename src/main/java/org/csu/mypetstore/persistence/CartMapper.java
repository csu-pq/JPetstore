package org.csu.mypetstore.persistence;

import org.csu.mypetstore.domain.Cart;
import org.springframework.stereotype.Repository;

@Repository
public interface CartMapper {

    Cart getCartByUsername(String username);

    void addCart(String username);

    void deleteCart(String username);

}
