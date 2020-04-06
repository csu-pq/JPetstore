package org.csu.mypetstore.domain;

import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.*;

@Component("cart")
public class Cart implements Serializable {
    private static final long serialVersionUID = 8329559983943337176L;
    private final Map<String, CartItem> itemMap = Collections.synchronizedMap(new HashMap());
    private final List<CartItem> itemList;
    private String cartId;
    private BigDecimal total;

    public void setTotal(BigDecimal number)
    {
        this.total=number;
    }
    public String getCartId() {
        return cartId;
    }

    public BigDecimal getTotal()
    {
        return total;
    }

    public void setCartId(String cartId) {
        this.cartId = cartId;
    }

    {
        itemList = new ArrayList();
    }

    public Cart() {
    }

    public Iterator<CartItem> getCartItems() {
        return this.itemList.iterator();
    }

    public List<CartItem> getCartItemList() {
        return this.itemList;
    }

    public int getNumberOfItems() {
        return this.itemList.size();
    }

    public Iterator<CartItem> getAllCartItems() {
        return this.itemList.iterator();
    }

    public boolean containsItemId(String itemId) {
        return this.itemMap.containsKey(itemId);
    }
    public CartItem getCartItemById(String itemId) {
        CartItem cartItem = (CartItem) itemMap.get(itemId);
        return cartItem;
    }

    public void addItem(Item item, boolean isInStock) {
        CartItem cartItem = (CartItem)this.itemMap.get(item.getItemId());
        if (cartItem == null) {
            cartItem = new CartItem();
            cartItem.setItem(item);
            cartItem.setQuantity(0);
            cartItem.setInStock(isInStock);
            this.itemMap.put(item.getItemId(), cartItem);
            this.itemList.add(cartItem);
        }

        cartItem.incrementQuantity();
    }
    public void addCartItem(CartItem cartItem) {
        CartItem cartItem1 = (CartItem) itemMap.get(cartItem.getItem().getItemId());
        if(cartItem1 == null) {
            itemMap.put(cartItem.getItem().getItemId(), cartItem);
            itemList.add(cartItem);
        }
    }

    public Item removeItemById(String itemId) {
        CartItem cartItem = (CartItem)this.itemMap.remove(itemId);
        if (cartItem == null) {
            return null;
        } else {
            this.itemList.remove(cartItem);
            return cartItem.getItem();
        }
    }

    public void incrementQuantityByItemId(String itemId) {
        CartItem cartItem = (CartItem)this.itemMap.get(itemId);
        cartItem.incrementQuantity();
    }

    public void setQuantityByItemId(String itemId, int quantity) {
        CartItem cartItem = (CartItem)this.itemMap.get(itemId);
        cartItem.setQuantity(quantity);
    }

    public BigDecimal getSubTotal() {
        BigDecimal subTotal = new BigDecimal("0");

        BigDecimal listPrice;
        BigDecimal quantity;
        for(Iterator items = this.getAllCartItems(); items.hasNext(); subTotal = subTotal.add(listPrice.multiply(quantity))) {
            CartItem cartItem = (CartItem)items.next();
            Item item = cartItem.getItem();
            listPrice = item.getListPrice();
            quantity = new BigDecimal(String.valueOf(cartItem.getQuantity()));
        }

        return subTotal;
    }
}
