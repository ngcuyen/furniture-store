package com.hutech.furniturestore.constants;

import com.hutech.furniturestore.dtos.request.OrderDetailDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponse {
    private Long id;
    private double totalPrice;
    private String status;
    private String fullName;
    private String phone;
    private String address;
    private String note;
    private LocalDateTime orderDate;
    private Date shippingDate;
    private String paymentMethod;
    private boolean isRemoved;
    private List<OrderDetailDto> orderDetails;
}
