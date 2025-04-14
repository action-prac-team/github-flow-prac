package org.example.tamaapi.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.tamaapi.domain.order.Order;
import org.example.tamaapi.dto.requestDto.CustomPageRequest;
import org.example.tamaapi.dto.responseDto.CustomPage;
import org.example.tamaapi.dto.responseDto.order.AdminOrderResponse;
import org.example.tamaapi.dto.responseDto.order.OrderItemResponse;
import org.example.tamaapi.repository.order.OrderItemRepository;
import org.example.tamaapi.repository.order.OrderRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;



@RestController
@RequiredArgsConstructor
@Slf4j
public class OrderApiController {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;

    //모든 주문 조회
    @GetMapping("/api/orders")
    public CustomPage<AdminOrderResponse> orders(@Valid @ModelAttribute CustomPageRequest customPageRequest) {
        PageRequest pageRequest = PageRequest.of(customPageRequest.getPage() - 1, customPageRequest.getSize());
        Page<Order> orders = orderRepository.findAllWithMemberAndDelivery(pageRequest);
        List<AdminOrderResponse> orderResponses = orders.stream().map(AdminOrderResponse::new).toList();
        List<Long> orderIds = orders.stream().map(Order::getId).toList();
        Map<Long, List<OrderItemResponse>> orderItemsMap = orderItemRepository.findAllWithByOrderIdIn(orderIds).stream().map(OrderItemResponse::new).collect(Collectors.groupingBy(OrderItemResponse::getOrderId));
        orderResponses.forEach(o -> o.setOrderItems(orderItemsMap.get(o.getId())));

        return new CustomPage<>(orderResponses, orders.getPageable(), orders.getTotalPages(), orders.getTotalElements());
    }

}
