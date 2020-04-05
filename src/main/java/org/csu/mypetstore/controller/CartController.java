package org.csu.mypetstore.controller;

import org.csu.mypetstore.domain.Account;
import org.csu.mypetstore.domain.Cart;
import org.csu.mypetstore.domain.CartItem;
import org.csu.mypetstore.domain.Item;
import org.csu.mypetstore.service.CartService;
import org.csu.mypetstore.service.CatalogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.context.annotation.SessionScope;

import javax.servlet.http.HttpServletRequest;
import java.util.Iterator;
import java.util.List;

@Controller
@SessionScope
@RequestMapping("/cart/")
public class CartController {
    @Autowired
    private CatalogService catalogService;
    @Autowired
    private CartService cartService;
    @Autowired
    private Cart cart;
//进入购物车界面
    @GetMapping("viewCart")
    public String viewCart(@SessionAttribute("account")Account account, Model model){
        cart=cartService.getCart(account.getUsername());
        String cartId=cart.getCartId();
        List <CartItem> cartItemList=cartService.getCartItemList(cartId);
        System.out.println(cartItemList.size());
        model.addAttribute("account",account);
        model.addAttribute("cart",cart);
        model.addAttribute("cartItemList",cartItemList);
        return "cart/cart";
    }
//添加商品跳往购物车界面
    @GetMapping("addItemToCart")
    public String addItemToCart(@SessionAttribute("account") Account account, String workingItemId, Model model){
        cart=cartService.getCart(account.getUsername());
        if(cart==null)//购物车为空则新建
        {
            cartService.creatCart(account.getUsername());
            cart=cartService.getCart(account.getUsername());
            String cartId=cart.getCartId();
            //是否有库存
            boolean inStock=catalogService.isItemInStock(workingItemId);
            cartService.addItem(cartId,workingItemId);
            List<CartItem> cartItemList=cart.getCartItemList();
            model.addAttribute("cart",cart);
            model.addAttribute("account",account);
            model.addAttribute("cartItemList",cartItemList);
            return "cart/cart";
        }
        else//不为空直接添加
        {
            boolean inStock=catalogService.isItemInStock(workingItemId);
            cart.addItem(catalogService.getItem(workingItemId),inStock);
            model.addAttribute("cart",cart);
            model.addAttribute("account",account);
            return "cart/cart";
        }
    }
}
