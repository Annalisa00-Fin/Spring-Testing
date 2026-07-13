package com.fincons.Team3;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class LibraryTests {
    @Mock
    private OrderRepository orderRepository;

    @Mock
    private NotificationService notificationService;

    @InjectMocks
    private OrderService orderService;

    @Test
    public void notificationServiceTest(){
        Order order = new Order(1L, "iphone", 4);
        orderService.createOder(order);

        //controls if the number of orders matches the list size
        assertEquals(1, orderService.listOrders.size());

        //controls if the notification is sent after the order is created
        verify(notificationService).send("Order was created");
    }

    @Test
    void orderServiceTest() {
        Order order1 = new Order(1L, "iphone", 4);
        Order order2 = new Order(2L, "samsung", 7);

        orderService.createOrder(order1);
        orderService.createOrder(order2);

        List<Order> orders = orderService.listOrders();
        //controls if the list size is matching
        assertEquals(2, orders.size());

        //controls if the orders are matching its positions
        assertEquals(order1, orders.get(0));
        assertEquals(order2, orders.get(1));

        //controls if two notifications are sent
        verify(notificationService, times(2)).send("Order was created");
    }

}
