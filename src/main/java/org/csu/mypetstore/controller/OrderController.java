package org.csu.mypetstore.controller;

import org.csu.mypetstore.domain.Account;
import org.csu.mypetstore.domain.Cart;
import org.csu.mypetstore.domain.CartItem;
import org.csu.mypetstore.domain.Order;
import org.csu.mypetstore.service.CartService;
import org.csu.mypetstore.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import javax.servlet.http.HttpSession;
import java.util.Iterator;
import java.util.List;

@Controller
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private CartService cartService;


    @GetMapping("/newOrderForm")
    public String newOrderForm(Model model, HttpSession session) {
        Account account = (Account) session.getAttribute("account");

        if (account == null) {
            model.addAttribute("accountMsg", "You must sign on before attempting to check out.  Please sign on and try checking out again.");
            return "account/signOnForm";
        } else {
            Cart cart = cartService.getCartByUsername(account.getUsername());
            Order order = new Order();
            order.initOrder(account, cart);
            session.setAttribute("order", order);
            return "order/newOrder";
        }
    }

    @PostMapping("/newOrder")
    public String newOrder(Order newOrder, String shippingAddressRequired, HttpSession session) {
        Order order = (Order) session.getAttribute("order");
        order.setCardType(newOrder.getCardType());
        order.setCreditCard(newOrder.getCreditCard());
        order.setExpiryDate(newOrder.getExpiryDate());
        order.setBillToFirstName(newOrder.getBillToFirstName());
        order.setBillToLastName(newOrder.getBillToLastName());
        order.setBillAddress1(newOrder.getBillAddress1());
        order.setBillAddress2(newOrder.getBillAddress2());
        order.setBillCity(newOrder.getBillCity());
        order.setBillState(newOrder.getBillState());
        order.setBillZip(newOrder.getBillZip());
        order.setBillCountry(newOrder.getBillCountry());

        if(shippingAddressRequired!=null && shippingAddressRequired.equals("1")) {
            return "order/shipping";
        } else {
            return "order/confirmOrder";
        }
    }

    @GetMapping("/confirmOrder")
    public String confirmOrder(Model model, HttpSession session) {
        Account account = (Account) session.getAttribute("account");
        Order order = (Order) session.getAttribute("order");
        orderService.insertOrder(order);

        Cart cart = cartService.getCartByUsername(account.getUsername());
        Iterator<CartItem> cartItems = cart.getAllCartItems();
        while(cartItems.hasNext()) {
            CartItem cartItem = cartItems.next();
            cartService.removeCartItem(cartItem, account);
        }
        return "order/viewOrder";
    }

    @GetMapping("/viewListOrders")
    public String viewListOrders(HttpSession session) {
        Account account = (Account) session.getAttribute("account");
        List<Order> orderList = orderService.getOrdersByUsername(account.getUsername());
        session.setAttribute("orderList", orderList);
        return "order/listOrder";
    }

    @GetMapping("/viewOder")
    public String view(@SessionAttribute("account")Account account, Model model)
    {
        List<Order> orderList=orderService.getOrdersByUsername(account.getUsername());
        model.addAttribute("account",account);
        model.addAttribute("orderList",orderList);
        return "order/listOrders";
    }

    @GetMapping("/getOrderByOrderId")
    public String viewOrder(@SessionAttribute("account")Account account, String orderId,Model model)
    {
        System.out.println(orderId);
        int intOrderId= Integer.parseInt(orderId);
        Order order=orderService.getOrder(intOrderId);
        model.addAttribute("order",order);
        model.addAttribute("account",account);
        return "order/viewOrderByOrderId";
    }
    @PostMapping("/shipping")
    public String shipping(Order newOrder, HttpSession session) {
        Order order = (Order) session.getAttribute("order");
        order.setShipToFirstName(newOrder.getShipToFirstName());
        order.setShipToLastName(newOrder.getShipToLastName());
        order.setShipAddress1(newOrder.getShipAddress1());
        order.setShipAddress2(newOrder.getShipAddress2());
        order.setShipCity(newOrder.getShipCity());
        order.setShipState(newOrder.getShipState());
        order.setShipZip(newOrder.getShipZip());
        order.setShipCountry(newOrder.getShipCountry());

        session.setAttribute("order", order);
        return "order/confirmOrder";
    }


}
