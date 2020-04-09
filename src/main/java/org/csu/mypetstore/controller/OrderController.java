package org.csu.mypetstore.controller;

import org.csu.mypetstore.domain.*;
import org.csu.mypetstore.persistence.LineItemMapper;
import org.csu.mypetstore.service.CartService;
import org.csu.mypetstore.service.CatalogService;
import org.csu.mypetstore.service.OrderService;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.model.IModel;

import javax.jws.WebParam;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

@Controller
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private CartService cartService;
    @Autowired
    private CatalogService catalogService;

//结账1(完)
    @GetMapping("/newOrderForm")
    public String newOrderForm(@SessionAttribute("account")Account account, Model model,HttpSession session) {
        Cart cart=cartService.getCart(account.getUsername());
        String cartId=cart.getCartId();
        List<CartItem> cartItemList=cartService.getCartItemList(cartId);
        cart.setTotal(cartService.getCartTotalCost(cartId));

        List<String> outOfStockItems = new ArrayList<>();
        for (CartItem cartItem : cartItemList) {
            if (!cartItem.isInStock())
                outOfStockItems.add(cartItem.getItem().getItemId());
        }
        if (outOfStockItems.size() > 0) {
                model.addAttribute("outOfStockMsg", "The item(s) " + outOfStockItems.toString() + " is(are) out of stock.");
                model.addAttribute("cart", cart);
                model.addAttribute("account",account);
                model.addAttribute("cartItemList",cartItemList);
                return "cart/cart";
        }
        Order order= new Order();
        Order newOrder=new Order();
        newOrder.initOrder(account, cart);
        //发货日期
        Calendar calendar = Calendar.getInstance();
        int month=calendar.get(Calendar.MONTH)+1;
        int date=calendar.get(Calendar.DATE);
        order.setExpiryDate(month+"/"+date);
        newOrder.setExpiryDate(month+"/"+date);
        //默认设置
        order.setBillToFirstName(account.getFirstName());
        order.setBillToLastName(account.getLastName());
        order.setBillAddress1(account.getAddress1());
        order.setBillAddress2(account.getAddress2());
        order.setBillCity(account.getCity());
        order.setBillCountry(account.getCountry());
        order.setBillState(account.getState());
        order.setBillZip(account.getZip());
        order.setCreditCard("999 9999 9999 9999");

        model.addAttribute("order",order);
        model.addAttribute("account",account);
        session.setAttribute("newOrder",newOrder);
        return "order/newOderForm";
    }
//结账2
    @PostMapping("/newOrder")
    public String newOrder(@SessionAttribute("account")Account account,@SessionAttribute("newOrder")Order newOrder, Order order, String shippingAddressRequired, Model model,HttpSession session) {
        if(shippingAddressRequired!=null && shippingAddressRequired.equals("false")) {
            //复制
            newOrder.setBillToFirstName(order.getBillToFirstName());
            newOrder.setBillToLastName(order.getBillToLastName());
            newOrder.setBillAddress1(order.getBillAddress1());
            newOrder.setBillAddress2(order.getBillAddress2());
            newOrder.setBillCity(order.getBillCity());
            newOrder.setBillCountry(order.getBillCountry());
            newOrder.setBillState(order.getBillState());
            newOrder.setBillZip(order.getBillZip());

            //默认设置
            order.setShipAddress1(account.getAddress1());
            order.setShipAddress2(account.getAddress2());
            order.setShipCity(account.getCity());
            order.setShipCountry(account.getCountry());
            order.setShipToFirstName(account.getFirstName());
            order.setShipToLastName(account.getLastName());
            order.setShipState(account.getState());
            order.setShipZip(account.getZip());

            session.setAttribute("account",account);
            model.addAttribute("order",order);
            session.setAttribute("newOrder",newOrder);

            return "order/shipping";
        } else {
            //复制
            newOrder.setBillToFirstName(order.getBillToFirstName());
            newOrder.setBillToLastName(order.getBillToLastName());
            newOrder.setBillAddress1(order.getBillAddress1());
            newOrder.setBillAddress2(order.getBillAddress2());
            newOrder.setBillCity(order.getBillCity());
            newOrder.setBillCountry(order.getBillCountry());
            newOrder.setBillState(order.getBillState());
            newOrder.setBillZip(order.getBillZip());
            //复制
            newOrder.setShipAddress1(account.getAddress1());
            newOrder.setShipAddress2(account.getAddress2());
            newOrder.setShipCity(account.getCity());
            newOrder.setShipCountry(account.getCountry());
            newOrder.setShipToFirstName(account.getFirstName());
            newOrder.setShipToLastName(account.getLastName());
            newOrder.setShipState(account.getState());
            newOrder.setShipZip(account.getZip());

            //订单号
            Calendar now1 = Calendar.getInstance();
            int day=now1.get(Calendar.DAY_OF_MONTH);
            int hour=now1.get(Calendar.HOUR_OF_DAY);
            int min=now1.get(Calendar.MINUTE);
            int sec=now1.get(Calendar.SECOND);
            int orderid= (int) (sec+min*Math.pow(10,2)+hour*Math.pow(10,4)+day*Math.pow(10,6));
            newOrder.setOrderId(orderid);
            //放入商品
            Cart cart=cartService.getCart(account.getUsername());
            String cartId=cart.getCartId();
            List<CartItem>cartItemList=cartService.getCartItemList(cartId);
            for(int i=0;i<cartItemList.size();i++)
            {
                newOrder.addLineItem(cartItemList.get(i));
            }
            model.addAttribute("newOrder",newOrder);
            session.setAttribute("account",account);
            session.setAttribute("newOrder",newOrder);
            return "order/confirmOrder";
        }
    }
    //结账3
    @PostMapping("/shipping")
    public String shipping(@SessionAttribute("account")Account account,@SessionAttribute("newOrder")Order newOrder, Order order,Model model,HttpSession session) {
        //复制
        newOrder.setShipAddress1(order.getShipAddress1());
        newOrder.setShipAddress2(order.getShipAddress2());
        newOrder.setShipCity(order.getShipCity());
        newOrder.setShipCountry(order.getShipCountry());
        newOrder.setShipToFirstName(order.getShipToFirstName());
        newOrder.setShipToLastName(order.getShipToLastName());
        newOrder.setShipState(order.getShipState());
        newOrder.setShipZip(order.getShipZip());
        //订单号
        Calendar now1 = Calendar.getInstance();
        int day=now1.get(Calendar.DAY_OF_MONTH);
        int hour=now1.get(Calendar.HOUR_OF_DAY);
        int min=now1.get(Calendar.MINUTE);
        int sec=now1.get(Calendar.SECOND);
        int orderid= (int) (sec+min*Math.pow(10,2)+hour*Math.pow(10,4)+day*Math.pow(10,6));
        newOrder.setOrderId(orderid);
        //放入商品
        Cart cart=cartService.getCart(account.getUsername());
        String cartId=cart.getCartId();
        List<CartItem>cartItemList=cartService.getCartItemList(cartId);
        for(int i=0;i<cartItemList.size();i++)
        {
            newOrder.addLineItem(cartItemList.get(i));
        }
        model.addAttribute("newOrder",newOrder);
        session.setAttribute("newOrder",newOrder);
        model.addAttribute("account",account);
        return "order/confirmOrder";
    }
//结账4
    @GetMapping("/viewOrder")
    public String confirmOrder(@SessionAttribute("account")Account account,@SessionAttribute("newOrder")Order newOrder,Model model, HttpSession session) {
        //插入订单
        Cart cart=cartService.getCart(account.getUsername());
        String cartId=cart.getCartId();
        cart.setTotal(cartService.getCartTotalCost(cartId));
        newOrder.setTotalPrice(cartService.getCartTotalCost(cartId));
        orderService.insertOrder(newOrder);
        //删除购物车商品
        List <CartItem>cartItemList=cartService.getCartItemList(cartId);
        for (CartItem item : cartItemList) {
            cartService.removeCartItem(item.getItem().getItemId(), cartId);
        }

        model.addAttribute("cartItemList",cartItemList);
        model.addAttribute("newOrder",newOrder);
        model.addAttribute("account",account);
        session.setAttribute("newOrder",newOrder);
        session.setAttribute("account",account);
        return "order/viewOrder";
    }
//查看历史订单（完）
    @GetMapping("/viewListOrders")
    public String viewListOrders(@SessionAttribute("account")Account account, Model model) {
        List<Order> orderList = orderService.getOrdersByUsername(account.getUsername());
        model.addAttribute("account",account);
        model.addAttribute("orderList", orderList);
        return "order/listOrders";
    }
//查看某一历史订单（完）
    @GetMapping("/getOrderByOrderId")
    public String viewOrder(@SessionAttribute("account")Account account, String orderId,Model model)
    {
        int intOrderId= Integer.parseInt(orderId);
        Order order=orderService.getOrder(intOrderId);
        System.out.println(order.getUsername());

        model.addAttribute("order",order);
        model.addAttribute("account",account);
        return "order/viewOrderByOrderId";
    }
}
