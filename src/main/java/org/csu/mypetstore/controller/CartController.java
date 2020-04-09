package org.csu.mypetstore.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.csu.mypetstore.domain.Account;
import org.csu.mypetstore.domain.Cart;
import org.csu.mypetstore.domain.CartItem;
import org.csu.mypetstore.domain.Item;
import org.csu.mypetstore.service.AccountService;
import org.csu.mypetstore.service.CartService;
import org.csu.mypetstore.service.CatalogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.annotation.SessionScope;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Controller
@SessionScope
@RequestMapping("/cart/")
public class CartController {
    @Autowired
    private AccountService accountService;
    @Autowired
    private CatalogService catalogService;
    @Autowired
    private CartService cartService;
    @Autowired
    private Cart cart;

//进入购物车界面（完）
    @GetMapping("viewCart")
    public String viewCart( Model model,HttpSession session){
        if(session.getAttribute("account")==null)
        {
           model.addAttribute("msg","请先登陆！");
           return "account/signOnForm";
        }
        else
        {
            Account account=(Account)session.getAttribute("account");
            cart=cartService.getCart(account.getUsername());
            String cartId=cart.getCartId();
            cart.setTotal(cartService.getCartTotalCost(cartId));

            List <CartItem> cartItemList=cartService.getCartItemList(cartId);

            model.addAttribute("account",account);
            model.addAttribute("cart",cart);
            model.addAttribute("cartItemList",cartItemList);
            return "cart/cart";
        }
    }

//添加商品跳往购物车界面(完)
    @GetMapping("addItemToCart")
    public String addItemToCart( String workingItemId, Model model,HttpSession session){
        if(session.getAttribute("account")==null)
        {
            model.addAttribute("msg","请先登陆！");
            return "account/signOnForm";
        }
        else
        {
            Account account=(Account)session.getAttribute("account");
            cart=cartService.getCart(account.getUsername());
            if(cart==null)//购物车为空则新建
            {
                cartService.creatCart(account.getUsername());
                cart=cartService.getCart(account.getUsername());
                String cartId=cart.getCartId();

                cartService.addItem(cartId,workingItemId);
                List<CartItem> cartItemList=cart.getCartItemList();

                model.addAttribute("cart",cart);
                model.addAttribute("account",account);
                model.addAttribute("cartItemList",cartItemList);
                return "cart/cart";
            }
            else//不为空直接添加
            {
                String cartId=cart.getCartId();
                cartService.addItem(cartId,workingItemId);
                cart.setTotal(cartService.getCartTotalCost(cartId));
                List <CartItem> cartItemList=cartService.getCartItemList(cartId);
                model.addAttribute("account",account);
                model.addAttribute("cart",cart);
                model.addAttribute("cartItemList",cartItemList);
                return "cart/cart";
            }
        }
    }
//移除商品(完)
    @GetMapping("/removeItemFromCart")
    public String removeItemFromCart(String itemId, Model model, HttpSession session) {
        Account account = (Account) session.getAttribute("account");
        Cart cart = cartService.getCart(account.getUsername());
        String cartId=cart.getCartId();

        cartService.removeCartItem(itemId, cartId);

        cart.setTotal(cartService.getCartTotalCost(cartId));
        List <CartItem> cartItemList=cartService.getCartItemList(cartId);
        model.addAttribute("account",account);
        model.addAttribute("cart",cart);
        model.addAttribute("cartItemList",cartItemList);
        return "cart/cart";
    }
    @GetMapping("/computeCartItem")
    @ResponseBody
    public String computeCartItem(String itemId, String quantity, HttpSession session) {
        Account account = (Account) session.getAttribute("account");
        Cart cart = cartService.getCartByUsername(account.getUsername());
        CartItem cartItem = cart.getCartItemById(itemId);
        cartItem.setQuantity(Integer.parseInt(quantity));
        cartService.updateCart(cartItem, account);
        double totalPrice = Integer.parseInt(quantity) * cartItem.getItem().getListPrice().doubleValue();

        Map<String, String> map = new HashMap<>();
        map.put("itemId", itemId);
        map.put("totalPrice", String.valueOf(totalPrice));
        map.put("subTotal", String.valueOf(cart.getSubTotal()));

        ObjectMapper objectMapper = new ObjectMapper();
        String json = null;
        try {
            json = objectMapper.writeValueAsString(map);
        } catch (JsonProcessingException jpe) {
            jpe.printStackTrace();
        }
        return json;
    }

    //更新购物车（完）
    @PostMapping("/updateCart")
    public String updateCart(Model model, HttpServletRequest request, HttpSession session) {
        Account account=(Account) session.getAttribute("account");
        Cart cart=cartService.getCart(account.getUsername());
        String[] quantities =request.getParameterValues("quantity");

        String cartId=cart.getCartId();

        List<CartItem> cartItemList=cartService.getCartItemList(cartId);
        int num=0;
        while (num<cartItemList.size()){
            int qua=Integer.parseInt(quantities[num]);
            String itemId=cartItemList.get(num).getItem().getItemId();

            cartService.updateCartItemQuantity(cartId,itemId,qua);
            num++;
        }
        cart.setTotal(cartService.getCartTotalCost(cartId));

        List <CartItem> cartItemList1=cartService.getCartItemList(cartId);
        model.addAttribute("account",account);
        model.addAttribute("cart",cart);
        model.addAttribute("cartItemList",cartItemList1);
        return "cart/cart";
    }
}
