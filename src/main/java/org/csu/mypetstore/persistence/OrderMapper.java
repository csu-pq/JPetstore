package org.csu.mypetstore.persistence;

import org.csu.mypetstore.domain.Order;
import org.springframework.stereotype.Repository;
import org.csu.mypetstore.domain.LineItem;

import java.util.List;

@Repository
public interface OrderMapper {


    List<Order> getOrdersByUsername(String username);

    List<Order> getAllOrder();

    Order getOrder(int orderId);

    void insertOrder(Order order);

    void insertOrderStatus(Order order);

    List<LineItem> getOrderInfo(int orderId);

    void updateOrderAddr(Order order);

    void sendstatus(Order order);
}
