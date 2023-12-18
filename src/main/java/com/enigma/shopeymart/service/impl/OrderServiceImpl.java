package com.enigma.shopeymart.service.impl;

import com.enigma.shopeymart.dto.request.OrderRequest;
import com.enigma.shopeymart.dto.response.*;
import com.enigma.shopeymart.entity.Customer;
import com.enigma.shopeymart.entity.Order;
import com.enigma.shopeymart.entity.OrderDetail;
import com.enigma.shopeymart.entity.ProductPrice;
import com.enigma.shopeymart.repository.OrderRepository;
import com.enigma.shopeymart.service.CustomerService;
import com.enigma.shopeymart.service.OrderService;
import com.enigma.shopeymart.service.ProductPriceService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(rollbackOn = Exception.class)
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository; //inject
    private final CustomerService customerService;
    private final ProductPriceService productPriceService;


    @Override
    public OrderResponse createNewOrder(OrderRequest orderRequest) {
        //TODO 1 : validate customer
        CustomerResponse customerResponse = customerService.getById(orderRequest.getCustomerId());

        //TODO 2 : Convert oerderDetailRequest to OrderDetail
        List<OrderDetail> orderDetails = orderRequest.getOrderDetailRequestList().stream().map(orderDetailRequest -> {
            //TODO 3 :Validate Product Price
            ProductPrice productPrice = productPriceService.getById(orderDetailRequest.getProductPriceId());
            return OrderDetail.builder()
                    .productPrice(productPrice)
                    .quantity(orderDetailRequest.getQuantity())
                    .build();
        }).toList();

        //TODO 4 : Create New Order
        Order order = Order.builder()
                .customer(Customer.builder()
                        .id(customerResponse.getId())
                        .build())
                .transDate(LocalDateTime.now())
                .orderDetails(orderDetails)
                .build();
        orderRepository.saveAndFlush(order);

        List<OrderDetailResponse> orderDetailResponses = order.getOrderDetails().stream().map(orderDetail -> {
            //TODO 5 : Set Order from orderDeataili after creating new order
            orderDetail.setOrder(order);
//            System.out.println(order);
            //TODO 6 : change the stock from purchase quantity
            ProductPrice currentProductPrice = orderDetail.getProductPrice();
            currentProductPrice.setStock(currentProductPrice.getStock() - orderDetail.getQuantity());
            return OrderDetailResponse.builder()
                    .orderDetailId(orderDetail.getId())
                    .quantity(orderDetail.getQuantity())
                    //TODO 7 : Convert Product to productResponse(productPrice)
                    .product(ProductResponse.builder()
                            .id(currentProductPrice.getProduct().getId())
                            .nameProduct(currentProductPrice.getProduct().getName())
                            .description(currentProductPrice.getProduct().getDescription())
                            .stock(currentProductPrice.getStock())
                            .price(currentProductPrice.getPrice())
                            //TODO 8 :convert store to storeResponse(productPrice)
                            .store(StoreResponse.builder()
                                    .id(currentProductPrice.getStore().getId())
                                    .storeName(currentProductPrice.getStore().getName())
                                    .noSiup(currentProductPrice.getStore().getNoSiup())
                                    .address(currentProductPrice.getStore().getAddress())
                                    .phone(currentProductPrice.getStore().getMobilePhone())
                                    .build())
                            .build())
                    .build();

        }).toList();

        // TODO 9 : customer to custoer resposne
//        CustomerResponse customerResponse1 = CustomerResponse.builder()
//                .id(order.getCustomer().getId())
//                .name(order.getCustomer().getName())
//                .email(order.getCustomer().getEmail())
//                .phone(order.getCustomer().getPhone())
//                .build();

        //TODO 10 : return OrderResponse
        return OrderResponse.builder()
                .orderId(order.getId())
                .transDate(order.getTransDate())
                .customer(customerResponse)
                .orderDetail(orderDetailResponses)
                .build();
    }

    @Override
    public OrderResponse getOrderById(String id) {
        return null;
    }

    @Override
    public List<OrderResponse> getAllOrder() {
        return null;
    }
}
