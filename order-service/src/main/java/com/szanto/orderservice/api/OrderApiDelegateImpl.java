package com.szanto.orderservice.api;

import com.szanto.orderservice.model.OrderRequest;
import com.szanto.orderservice.model.OrderResponse;
import com.szanto.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class OrderApiDelegateImpl implements OrderApiDelegate {

    private final OrderService orderService;

    /**
     * POST /order : Place Order
     */
    @Override
    public ResponseEntity<OrderResponse> orderPost(OrderRequest orderRequest) {
        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userId = jwt.getClaimAsString("sub");
        return orderService.placeOrder(userId, orderRequest) ?
                new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
    }

    /**
     * GET /order/{orderId} : Get a product by product id
     */
    @Override
    public ResponseEntity<OrderResponse> orderOrderIdGet(Long orderId) {

        OrderResponse response = orderService.getOrderById(orderId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * GET /order/{userId} : Get all orders of a user
     */
    @Override
    @PreAuthorize("hasAuthority('USER_ID_' + #userId) or hasRole('ROLE_admin')")
    public ResponseEntity<List<OrderResponse>> orderUserIdGet(String userId) {
        List<OrderResponse> response = orderService.getOrdersOfUser(userId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
