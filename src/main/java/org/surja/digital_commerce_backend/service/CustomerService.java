package org.surja.digital_commerce_backend.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.surja.digital_commerce_backend.dto.*;
import org.surja.digital_commerce_backend.entity.*;
import org.surja.digital_commerce_backend.exception.NotFoundException;
import org.surja.digital_commerce_backend.exception.OutOfStocksException;
import org.surja.digital_commerce_backend.repo.OrderRepo;
import org.surja.digital_commerce_backend.repo.ProductRepo;
import org.surja.digital_commerce_backend.repo.UserRepo;


import java.util.ArrayList;
import java.util.List;


@Service
public class CustomerService {

    @Autowired
    private ProductRepo productRepo;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private OrderRepo orderRepo;

    public List<ProductDTO> getProdcutsByKeyword(String keyword, Pageable pageable) throws NotFoundException {
        List<Product> productList = productRepo.findByNameContaining(keyword,pageable);
        if (productList.isEmpty()) {
            throw new NotFoundException("No products found for keyword: " + keyword);
        }
        List<ProductDTO> result = new ArrayList<>();
        for(Product product: productList){
            ProductDTO productDTO = ProductDTO.buildProductDTOFromProduct(product);
            result.add(productDTO);
        }

        return result;
    }

    public ProductDTO getById(Long id) throws NotFoundException {
        ProductDTO productDTO = ProductDTO.buildProductDTOFromProduct(productRepo
                .findById(id)
                .orElseThrow(()-> new NotFoundException("No proudct found with id : "+id)));

        return productDTO;
    }

    @Transactional
    public OrderDetailDto addToOrder(AddToOrderDto addToOrderDto) throws NotFoundException {

        // fetching the details of product from db
        Product product = productRepo.findById(addToOrderDto
                        .getProductId())
                .orElseThrow(() -> new NotFoundException("No product found with id : " + addToOrderDto.getProductId()));

        // fetching the details of user session
        User user = (User) SecurityContextHolder.getContext().getAuthentication();

        //getting the list of draft orders specific to the user
        List<Order> orderList = orderRepo.findByOrderStatusAndUser(OrderStatus.DRAFT, user);

        Order existingOrder;

        //if there is an order in draft state we will get the order
        //else  we will create new order,
        if (!orderList.isEmpty()) {
            existingOrder = orderList.get(0);
        } else {
            existingOrder = Order.builder()
                    .orderStatus(OrderStatus.DRAFT)
                    .totalAmount(0.0)
                    .user(user)
                    .orderItems(new ArrayList<>())
                    .build();
        }


        //checking if the requested number of product is available
        if (product.getStocks() < addToOrderDto.getQuantity()) {
            throw new OutOfStocksException("Available stocks is : " + product.getStocks() + " for the Product with id : " + addToOrderDto.getProductId() + " ");
        }

        //creating new order item which is requested by the user by  addToOrderDto

        OrderItem orderItem = null;
        double totalItemsPrice = 0;
//         if the product is already present
        for (OrderItem item : existingOrder.getOrderItems()) {
            if (item.getProduct().getId().equals(addToOrderDto.getProductId())) {
                item.setQuantity(item.getQuantity() + addToOrderDto.getQuantity());
                totalItemsPrice = item.getPrice() * addToOrderDto.getQuantity();
                orderItem = item;

            }
        }
        if (orderItem == null) {
            orderItem = OrderItem.builder()
                    .order(existingOrder)
                    .price(product.getPrice())
                    .quantity(addToOrderDto.getQuantity())
                    .product(product)
                    .build();

            //adding the orderItem in existing order
            existingOrder.getOrderItems().add(orderItem);
            //price of this orderItem
            totalItemsPrice = product.getPrice() * orderItem.getQuantity();
        }

        //setting the new order price in order
        existingOrder.setTotalAmount(existingOrder.getTotalAmount() + totalItemsPrice);

        // saving the existing order
        orderRepo.save(existingOrder);

        //returning OrderDetailDto
        OrderDetailDto orderDetailDto = new OrderDetailDto();
        orderDetailDto.setOrderId(existingOrder.getId());
        orderDetailDto.setOrderTotalPrice(existingOrder.getTotalAmount());

        List<OrderItemDto> orderItemDtoList = new ArrayList<>();
        for (OrderItem orderItem1 : existingOrder.getOrderItems()) {
            orderItemDtoList.add(OrderItemDto.mapOrderItemToDto(orderItem1));
        }
        orderDetailDto.setOrderItems(orderItemDtoList);
        return orderDetailDto;
    }


    @Transactional
    public ResponseDTO submitOrder(Long id) throws NotFoundException {

        Order order = orderRepo.findById(id)
                .orElseThrow(()-> new NotFoundException("No order found with id : "+id));

        ResponseDTO response = new ResponseDTO();
        if (order.getOrderStatus().equals(OrderStatus.DRAFT)) {

            for(OrderItem item : order.getOrderItems()){
                Product product = productRepo.findById(item.getProduct().getId())
                        .orElseThrow(()->new NotFoundException("No product found with id : "+id));

                int stocks = product.getStocks() - item.getQuantity();
                if(stocks>=0){
                    product.setStocks(stocks);
                }
                else {
                    throw new OutOfStocksException("Out of stock ! Current product Stock : "+product.getStocks() + " for the product : "+product.getId());
                }

            }
            order.setOrderStatus(OrderStatus.PLACED);
            orderRepo.save(order);
            response.setId(order.getId());
            response.setMessage("order success fully placed ");
            response.setCode("1234-S");
            return response;

        }
        else {
            response.setId(order.getId());
            response.setMessage("faild to place the order , no draft order ");
            response.setCode("1234-F");
            return response;
        }

    }
}
