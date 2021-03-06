package org.csu.mypetstore.domain;

import java.io.Serializable;
import java.math.BigDecimal;


public class CartItem implements Serializable {
    private static final long serialVersionUID = 6620528781626504362L;
    private Item item;
    private int quantity;
    private boolean inStock;
    private BigDecimal total;
    private String itemId;
    private String cartId;
    private String categoryId;


    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getCartId() {
        return cartId;
    }

    public void setCartId(String cartId) {
        this.cartId = cartId;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public CartItem(){
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
        calculateTotal();
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
        calculateTotal();
    }

    public boolean isInStock() {
        return inStock;
    }

    public void setInStock(boolean inStock) {
        this.inStock = inStock;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }
    public void incrementQuantity() {
        quantity++;
        calculateTotal();
    }
    private void calculateTotal() {
        if (item != null && item.getListPrice() != null) {
            total = item.getListPrice().multiply(new BigDecimal(quantity));
        } else {
            total = null;
        }
    }
    public String getItemId() {
        return itemId;
    }
}
