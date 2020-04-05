package org.csu.mypetstore.controller;

import org.csu.mypetstore.domain.Account;
import org.csu.mypetstore.domain.Order;
import org.csu.mypetstore.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.util.List;

@Controller
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;
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
}
