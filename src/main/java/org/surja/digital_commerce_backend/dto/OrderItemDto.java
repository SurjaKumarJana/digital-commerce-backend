package org.surja.digital_commerce_backend.dto;

import lombok.*;
import org.surja.digital_commerce_backend.entity.OrderItem;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemDto {
    private Long id;
    private Integer quantity;
    private Double price;
    private String productName;
    private Double totalPrice;


    public static OrderItemDto mapOrderItemToDto(OrderItem orderItem){
        OrderItemDto orderItemDto = OrderItemDto.builder()
                .id(orderItem.getId())
                .price(orderItem.getPrice())
                .quantity(orderItem.getQuantity())
                .totalPrice(orderItem.getPrice()*orderItem.getQuantity())
                .productName(orderItem.getProduct().getName())
                .build();
        return orderItemDto;
    }

}
