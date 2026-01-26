////package com.exam.controller;
////import java.util.List;
////
////import org.springframework.http.HttpStatus;
////import org.springframework.http.ResponseEntity;
////import org.springframework.security.access.prepost.PreAuthorize;
////import org.springframework.web.bind.annotation.DeleteMapping;
////import org.springframework.web.bind.annotation.GetMapping;
////import org.springframework.web.bind.annotation.PathVariable;
////import org.springframework.web.bind.annotation.PostMapping;
////import org.springframework.web.bind.annotation.PutMapping;
////import org.springframework.web.bind.annotation.RequestBody;
////import org.springframework.web.bind.annotation.RequestMapping;
////import org.springframework.web.bind.annotation.RestController;
////
////import com.exam.dto.OrderDTO;
////import com.exam.entities.Order;
////import com.exam.service.IOrderService;
////
////import jakarta.validation.Valid;
////import lombok.RequiredArgsConstructor;
////@RestController
////@RequestMapping("/orders")
////@RequiredArgsConstructor
////public class OrderController {
////
////    private final IOrderService service;
////
////    @GetMapping
////    public ResponseEntity<List<OrderDTO>> getAll() {
////        return ResponseEntity.ok(service.getAll());
////    }
////
////    @GetMapping("/{id}")
////    public ResponseEntity<OrderDTO> getById(@PathVariable Long id) {
////        return ResponseEntity.ok(service.getById(id));
////    }
////
////    @GetMapping("/customer/{customerId}")
////    public ResponseEntity<List<OrderDTO>> getByCustomer(@PathVariable Long customerId) {
////        return ResponseEntity.ok(service.getByCustomerId(customerId));
////    }
////
////    @PostMapping
////    public ResponseEntity<OrderDTO> create(@Valid @RequestBody Order order) {
////        OrderDTO created = service.save(order);
////        return ResponseEntity.status(HttpStatus.CREATED).body(created);
////    }
////
////    @PutMapping("/{id}")
////    public ResponseEntity<OrderDTO> update(
////            @PathVariable Long id,
////            @Valid @RequestBody Order order) {
////
////        order.setId(id);
////        return ResponseEntity.ok(service.save(order));
////    }
////
////    @DeleteMapping("/{id}")
////    public ResponseEntity<Void> delete(@PathVariable Long id) {
////        service.delete(id);
////        return ResponseEntity.noContent().build();
////    }
////}
//package com.exam.controller;
//
//import java.util.List;
//
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.web.bind.annotation.*;
//
//import com.exam.dto.OrderDTO;
//import com.exam.entities.Order;
//import com.exam.service.IOrderService;
//
//import jakarta.validation.Valid;
//import lombok.RequiredArgsConstructor;
//
//@RestController
//@RequestMapping("/orders")
//@RequiredArgsConstructor
//public class OrderController {
//
//    private final IOrderService service;
//
//    /*
//     * GET /orders
//     * Authorization: ADMIN, CUSTOMER_SUPPORT
//     */
//    @PreAuthorize("hasAnyRole('ADMIN','CUSTOMER_SUPPORT')")
//    @GetMapping
//    public ResponseEntity<List<OrderDTO>> getAll() {
//        return ResponseEntity.ok(service.getAll());
//    }
//
//    /*
//     * GET /orders/{id}
//     * Authorization: Any authenticated user
//     */
//    @PreAuthorize("isAuthenticated()")
//    @GetMapping("/{id}")
//    public ResponseEntity<OrderDTO> getById(@PathVariable Long id) {
//        return ResponseEntity.ok(service.getById(id));
//    }
//
//    /*
//     * GET /orders/customer/{customerId}
//     * Authorization: ADMIN, CUSTOMER_SUPPORT
//     * (Owner-based access can be added later)
//     */
//    @PreAuthorize("hasAnyRole('ADMIN','CUSTOMER_SUPPORT')")
//    @GetMapping("/customer/{customerId}")
//    public ResponseEntity<List<OrderDTO>> getByCustomer(@PathVariable Long customerId) {
//        return ResponseEntity.ok(service.getByCustomerId(customerId));
//    }
//
//    /*
//     * POST /orders
//     * Authorization: Any authenticated user
//     */
//    @PreAuthorize("isAuthenticated()")
//    @PostMapping
//    public ResponseEntity<OrderDTO> create(@Valid @RequestBody Order order) {
//        OrderDTO created = service.save(order);
//        return ResponseEntity.status(HttpStatus.CREATED).body(created);
//    }
//
//    /*
//     * PUT /orders/{id}
//     * Authorization: ADMIN
//     * (Owner-based update can be added later)
//     */
//    @PreAuthorize("hasRole('ADMIN')")
//    @PutMapping("/{id}")
//    public ResponseEntity<OrderDTO> update(
//            @PathVariable Long id,
//            @Valid @RequestBody Order order) {
//
//        order.setId(id);
//        return ResponseEntity.ok(service.save(order));
//    }
//
//    /*
//     * DELETE /orders/{id}
//     * Authorization: ADMIN
//     */
//    @PreAuthorize("hasRole('ADMIN')")
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> delete(@PathVariable Long id) {
//        service.delete(id);
//        return ResponseEntity.noContent().build();
//    }
//}
package com.exam.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.exam.dto.OrderDTO;
import com.exam.dto.OrderRequestDTO;
import com.exam.entities.Order;
import com.exam.service.IOrderService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final IOrderService service;

    /*
     * GET /orders
     * Authorization: Any authenticated user (ADMIN / WAREHOUSE_MANAGER / CUSTOMER_SUPPORT / CUSTOMER)
     */
    @PreAuthorize("isAuthenticated()")
    @GetMapping
    public ResponseEntity<List<OrderDTO>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    /*
     * GET /orders/{id}
     * Authorization: Any authenticated user
     */
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{id}")
    public ResponseEntity<OrderDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    /*
     * GET /orders/customer/{customerId}
     * Authorization: Any authenticated user
     */
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<OrderDTO>> getByCustomer(@PathVariable Long customerId) {
        return ResponseEntity.ok(service.getByCustomerId(customerId));
    }

    /*
     * POST /orders
     * Authorization: Only CUSTOMER can place order
     */
    @PreAuthorize("hasRole('CUSTOMER')")
    @PostMapping
    public ResponseEntity<OrderDTO> create(@Valid @RequestBody OrderRequestDTO order) {
        OrderDTO created = service.save(order);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /*
     * PUT /orders/{id}
     * Authorization: ADMIN, WAREHOUSE_MANAGER
     */
    @PreAuthorize("hasAnyRole('ADMIN','WAREHOUSE_MANAGER')")
    @PutMapping("/{id}")
    public ResponseEntity<OrderDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody OrderRequestDTO order) {

        //order.setId(id);
        return ResponseEntity.ok(service.save(order));
    }

    /*
     * DELETE /orders/{id}
     * Authorization: ADMIN, WAREHOUSE_MANAGER
     */
    @PreAuthorize("hasAnyRole('ADMIN','WAREHOUSE_MANAGER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
