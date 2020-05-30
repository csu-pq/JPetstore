package org.csu.mypetstore.BMSController;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.csu.mypetstore.domain.LineItem;
import org.csu.mypetstore.domain.Order;
import org.csu.mypetstore.service.OrderService;
import org.csu.mypetstore.utils.ResultFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bms/order")
public class BMSOrderController {
    @Autowired
    OrderService orderService;
    @GetMapping("/orders")
    public ResultFactory viewOrders(@RequestParam(value = "pagenum")int pagenum, @RequestParam(value = "pagesize")int pageSize) {
        PageHelper.startPage(pagenum, pageSize);
        List<Order> orderList= orderService.getAllOrder();//返回所有的订单
        PageInfo<Order> pageInfo = new PageInfo<>(orderList);
        return ResultFactory.successResult(pageInfo,"查询成功");
    }
    @GetMapping("/orderInfo")
    public ResultFactory getOrderInfo(@RequestParam("orderId") int orderId){
        List<LineItem> lineItem=orderService.getOrderInfo(orderId);
        return ResultFactory.successResult(lineItem,"查询成功");
    }
    @PostMapping("/changeAddr")
    public ResultFactory changeAddr(@RequestParam("address1") String address1,@RequestParam("address2")String address2,@RequestParam("id") int id){
        Order order=orderService.getOrder(id);
        order.setShipAddress1(address1);
        order.setShipAddress2(address2);
        orderService.updateOrderAddr(order);
        return ResultFactory.successResult(null,"修改成功");
    }
    //发货
    @PutMapping("/sendstatus")
    public ResultFactory sendstatus(@RequestParam("orderId")int orderId,@RequestParam("sendstatus")int sendstatus){
        orderService.changeSendStatus(orderId,sendstatus);
        return ResultFactory.successResult(null,"修改成功");
    }
}