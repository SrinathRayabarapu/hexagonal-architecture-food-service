package com.javatechie.adapter.input.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.javatechie.domain.dto.FoodOrder;
import com.javatechie.domain.port.input.PlaceOrderUsecasePort;
import com.javatechie.domain.port.input.TrackOrderUsecasePort;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OrderController.class)
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private PlaceOrderUsecasePort placeOrderUsecasePort;

    @MockitoBean
    private TrackOrderUsecasePort trackOrderUsecasePort;

    @Test
    void placeOrder_returnsOk() throws Exception {
        FoodOrder order = new FoodOrder();
        order.setOrderId("order-1");
        order.setCustomerName("Alex");
        order.setRestaurantName("Pizza Place");
        order.setItem("Margherita");

        doNothing().when(placeOrderUsecasePort).placeOrder(any(FoodOrder.class));

        mockMvc.perform(post("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(order)))
                .andExpect(status().isOk())
                .andExpect(content().string("Order placed"));
    }

    @Test
    void trackOrder_returnsStatus() throws Exception {
        when(trackOrderUsecasePort.trackOrder("order-1")).thenReturn("ORDER PLACED");

        mockMvc.perform(get("/orders/track/order-1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Status: ORDER PLACED"));
    }
}
