//package com.exam.controller;
//import com.exam.service.*;
//import java.util.List;
//
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.DeleteMapping;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.PutMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.exam.dto.ReturnDTO;
//import com.exam.entities.Return;
//
//
//import jakarta.validation.Valid;
//import lombok.RequiredArgsConstructor;
//@RestController
//@RequestMapping("/returns")
//@RequiredArgsConstructor
//public class ReturnController {
//
//    private final IReturnService service;
//
//    @GetMapping
//    public ResponseEntity<List<ReturnDTO>> getAll() {
//        return ResponseEntity.ok(service.getAll());
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<ReturnDTO> getById(@PathVariable Long id) {
//        return ResponseEntity.ok(service.getById(id));
//    }
//
//    @PostMapping
//    public ResponseEntity<ReturnDTO> create(@Valid @RequestBody Return returnEntity) {
//        ReturnDTO created = service.save(returnEntity);
//        return ResponseEntity.status(HttpStatus.CREATED).body(created);
//    }
//
//    @PutMapping("/{id}")
//    public ResponseEntity<ReturnDTO> update(
//            @PathVariable Long id,
//            @Valid @RequestBody Return returnEntity) {
//
//        returnEntity.setId(id);
//        return ResponseEntity.ok(service.save(returnEntity));
//    }
//
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

import com.exam.dto.ReturnDTO;
import com.exam.entities.Return;
import com.exam.service.IReturnService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/returns")
@RequiredArgsConstructor
public class ReturnController {

    private final IReturnService service;

    /*
     * GET /returns
     * Authorization: Any authenticated user (ALL ROLES)
     */
    @PreAuthorize("isAuthenticated()")
    @GetMapping
    public ResponseEntity<List<ReturnDTO>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    /*
     * GET /returns/{id}
     * Authorization: Any authenticated user (ALL ROLES)
     */
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{id}")
    public ResponseEntity<ReturnDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    /*
     * POST /returns
     * Authorization: Only CUSTOMER
     */
    @PreAuthorize("hasRole('CUSTOMER')")
    @PostMapping
    public ResponseEntity<ReturnDTO> create(@Valid @RequestBody Return returnEntity) {
        ReturnDTO created = service.save(returnEntity);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /*
     * PUT /returns/{id}
     * Authorization: ADMIN, WAREHOUSE_MANAGER
     */
    @PreAuthorize("hasAnyRole('ADMIN','WAREHOUSE_MANAGER')")
    @PutMapping("/{id}")
    public ResponseEntity<ReturnDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody Return returnEntity) {

        returnEntity.setId(id);
        return ResponseEntity.ok(service.save(returnEntity));
    }

    /*
     * DELETE /returns/{id}
     * Authorization: ADMIN, WAREHOUSE_MANAGER
     */
    @PreAuthorize("hasAnyRole('ADMIN','WAREHOUSE_MANAGER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
