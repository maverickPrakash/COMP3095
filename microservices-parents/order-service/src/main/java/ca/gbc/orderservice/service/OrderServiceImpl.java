package ca.gbc.orderservice.service;

import ca.gbc.orderservice.dto.InventoryRequest;
import ca.gbc.orderservice.dto.InventoryResponse;
import ca.gbc.orderservice.dto.OrderLineItemDto;
import ca.gbc.orderservice.dto.OrderRequest;
import ca.gbc.orderservice.model.Order;
import ca.gbc.orderservice.model.OrderLineItem;
import ca.gbc.orderservice.repository.OrderRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class OrderServiceImpl implements OrderService{
    private final OrderRepository orderRepository;
    private final WebClient.Builder webClient;
    @Value("${inventory-service.uri}")
    private String inventoryApiUri;
    public void placeOrder(OrderRequest orderRequest){
        Order order = new Order();

        order.setOrderNumber(UUID.randomUUID().toString());
        List<OrderLineItem> orderLineItems = orderRequest
                .getOrderLineItemDtoList()
                .stream()
                .map(this::mapToDto)
                .toList();
        order.setOrderLineItemList(orderLineItems);

        List<InventoryRequest> inventoryRequests = order.getOrderLineItemList()
                        .stream().map(orderLineItem -> InventoryRequest
                        .builder()
                        .skuCode(orderLineItem.getSkuCode())
                        .quantity(orderLineItem.getQuantity())
                        .build()).toList();
        List<InventoryResponse> inventoryResponseList = webClient.build()
                        .post()
                        .uri(inventoryApiUri)
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(inventoryRequests)
                        .retrieve()
                        .bodyToFlux(InventoryResponse.class)
                        .collectList()
                        .block();

        assert inventoryResponseList!=null;

        boolean allProductsInStock = inventoryResponseList.stream().allMatch(InventoryResponse::isSufficientStock);
        if(Boolean.TRUE.equals(allProductsInStock)){
            orderRepository.save(order);
        }else {
            throw new RuntimeException("Not all the product are in stock "+inventoryResponseList);
        }



    }

    private OrderLineItem mapToDto(OrderLineItemDto orderLineItemDto){
        OrderLineItem orderLineItem = new OrderLineItem();
        orderLineItem.setPrice(orderLineItemDto.getPrice());
        orderLineItem.setQuantity(orderLineItemDto.getQuanity());
        orderLineItem.setSkuCode(orderLineItemDto.getSkuCode());
        return orderLineItem;
    }
}