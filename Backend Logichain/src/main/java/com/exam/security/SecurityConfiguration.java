////package com.exam.security;
////
////import org.springframework.context.annotation.Bean;
////import org.springframework.context.annotation.Configuration;
////import org.springframework.http.HttpMethod;
////import org.springframework.security.authentication.AuthenticationManager;
////import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
////import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
////import org.springframework.security.config.annotation.web.builders.HttpSecurity;
////import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
////import org.springframework.security.config.http.SessionCreationPolicy;
////import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
////import org.springframework.security.crypto.password.PasswordEncoder;
////import org.springframework.security.web.SecurityFilterChain;
////import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
////
////import lombok.RequiredArgsConstructor;
////
////@Configuration
////@EnableWebSecurity
////@EnableMethodSecurity
////@RequiredArgsConstructor
////public class SecurityConfiguration {
////
////    private final CustomJwtVerificationFilter jwtFilter;
////
////    @Bean
////    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
////
////        return http
////                .csrf(csrf -> csrf.disable())
////                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
////
////                .authorizeHttpRequests(request -> request
////
////                        // ✅ Swagger Public
////                        .requestMatchers(
////                                "/v3/api-docs/**",
////                                "/swagger-ui/**",
////                                "/swagger-ui.html"
////                        ).permitAll()
////
////                        // ✅ Public Auth APIs
////                        .requestMatchers(HttpMethod.POST, "/users").permitAll()
////                        .requestMatchers(HttpMethod.POST, "/users/signin").permitAll()
////
////                        // OPTIONS allow
////                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
////
////                        // Everything else requires token
////                        .anyRequest().authenticated()
////                )
////
////                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
////
////                .build();
////    }
////
////    @Bean
////    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
////        return config.getAuthenticationManager();
////    }
////
////    @Bean
////    public PasswordEncoder passwordEncoder() {
////        return new BCryptPasswordEncoder();
////    }
////}
//package com.exam.security;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.HttpMethod;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
//import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//
//@Configuration
//@EnableWebSecurity
//@EnableMethodSecurity
//@RequiredArgsConstructor
//@Slf4j
//public class SecurityConfiguration {
//
//    private final CustomJwtVerificationFilter jwtFilter;
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//
//        log.info("******** configuring spring security filter chain ********");
//
//        return http
//                .csrf(csrf -> csrf.disable())
//                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//
//                .authorizeHttpRequests(req -> req
//
//                        // ✅ Swagger Public
//                        .requestMatchers(
//                                "/v3/api-docs/**",
//                                "/swagger-ui/**",
//                                "/swagger-ui.html"
//                        ).permitAll()
//
//                        // ✅ User Public APIs (signup + signin)
//                        .requestMatchers(HttpMethod.POST, "/users").permitAll()
//                        .requestMatchers(HttpMethod.POST, "/users/signin").permitAll()
//
//                        // ✅ Preflight requests
//                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
//
//                        // =====================================================
//                        // ✅ ProductController : /api/v1/products
//                        // =====================================================
//                        // GET → all authenticated users
//                        .requestMatchers(HttpMethod.GET, "/api/v1/products/**").authenticated()
//
//                        // POST, PUT, DELETE, PATCH → ADMIN, WAREHOUSE_MANAGER
//                        .requestMatchers(HttpMethod.POST, "/api/v1/products/**").hasAnyRole("ADMIN", "WAREHOUSE_MANAGER")
//                        .requestMatchers(HttpMethod.PUT, "/api/v1/products/**").hasAnyRole("ADMIN", "WAREHOUSE_MANAGER")
//                        .requestMatchers(HttpMethod.DELETE, "/api/v1/products/**").hasAnyRole("ADMIN", "WAREHOUSE_MANAGER")
//                        .requestMatchers(HttpMethod.PATCH, "/api/v1/products/**").hasAnyRole("ADMIN", "WAREHOUSE_MANAGER")
//
//                        // =====================================================
//                        // ✅ WarehouseController : /api/v1/warehouses
//                        // =====================================================
//                        // GET → ADMIN, WAREHOUSE_MANAGER, CUSTOMER_SUPPORT
//                        .requestMatchers(HttpMethod.GET, "/api/v1/warehouses/**")
//                        .hasAnyRole("ADMIN", "WAREHOUSE_MANAGER", "CUSTOMER_SUPPORT")
//
//                        // POST, PUT, DELETE → ADMIN
//                        .requestMatchers(HttpMethod.POST, "/api/v1/warehouses/**").hasRole("ADMIN")
//                        .requestMatchers(HttpMethod.PUT, "/api/v1/warehouses/**").hasRole("ADMIN")
//                        .requestMatchers(HttpMethod.DELETE, "/api/v1/warehouses/**").hasRole("ADMIN")
//
//                        // =====================================================
//                        // ✅ InventoryController : /api/v1/inventory
//                        // =====================================================
//                        // GET → All authenticated users
//                        .requestMatchers(HttpMethod.GET, "/api/v1/inventory/**").authenticated()
//
//                        // POST, PUT → ADMIN, WAREHOUSE_MANAGER
//                        .requestMatchers(HttpMethod.POST, "/api/v1/inventory/**").hasAnyRole("ADMIN", "WAREHOUSE_MANAGER")
//                        .requestMatchers(HttpMethod.PUT, "/api/v1/inventory/**").hasAnyRole("ADMIN", "WAREHOUSE_MANAGER")
//
//                        // reserve, release → ADMIN (you said internal service calls OR ADMIN)
//                        .requestMatchers(HttpMethod.POST, "/api/v1/inventory/*/reserve").hasRole("ADMIN")
//                        .requestMatchers(HttpMethod.POST, "/api/v1/inventory/*/release").hasRole("ADMIN")
//
//                        // transfer → ADMIN, WAREHOUSE_MANAGER
//                        .requestMatchers(HttpMethod.POST, "/api/v1/inventory/transfer").hasAnyRole("ADMIN", "WAREHOUSE_MANAGER")
//
//                        // =====================================================
//                        // ✅ InventoryTransactionController : /api/v1/inventory-transactions
//                        // =====================================================
//                        .requestMatchers("/api/v1/inventory-transactions/**")
//                        .hasAnyRole("ADMIN", "WAREHOUSE_MANAGER")
//
//                        // =====================================================
//                        // ✅ ShipmentController : /api/v1/shipments
//                        // =====================================================
//                        // GET tracking public OR customer support -> we'll allow public tracking endpoint
//                        .requestMatchers(HttpMethod.GET, "/api/v1/shipments/track/**").permitAll()
//
//                        // GET other shipment APIs → ADMIN, WAREHOUSE_MANAGER, CUSTOMER_SUPPORT
//                        .requestMatchers(HttpMethod.GET, "/api/v1/shipments/**")
//                        .hasAnyRole("ADMIN", "WAREHOUSE_MANAGER", "CUSTOMER_SUPPORT")
//
//                        // POST create shipment → ADMIN, WAREHOUSE_MANAGER
//                        .requestMatchers(HttpMethod.POST, "/api/v1/shipments").hasAnyRole("ADMIN", "WAREHOUSE_MANAGER")
//
//                        // PUT/POST updates → ADMIN, WAREHOUSE_MANAGER
//                        .requestMatchers(HttpMethod.PUT, "/api/v1/shipments/**").hasAnyRole("ADMIN", "WAREHOUSE_MANAGER")
//                        .requestMatchers(HttpMethod.POST, "/api/v1/shipments/**").hasAnyRole("ADMIN", "WAREHOUSE_MANAGER")
//
//                        // =====================================================
//                        // ✅ CarrierController : /api/v1/carriers
//                        // =====================================================
//                        // GET → All authenticated users
//                        .requestMatchers(HttpMethod.GET, "/api/v1/carriers/**").authenticated()
//
//                        // POST, PUT, DELETE, PATCH → ADMIN
//                        .requestMatchers(HttpMethod.POST, "/api/v1/carriers/**").hasRole("ADMIN")
//                        .requestMatchers(HttpMethod.PUT, "/api/v1/carriers/**").hasRole("ADMIN")
//                        .requestMatchers(HttpMethod.DELETE, "/api/v1/carriers/**").hasRole("ADMIN")
//                        .requestMatchers(HttpMethod.PATCH, "/api/v1/carriers/**").hasRole("ADMIN")
//
//                        // =====================================================
//                        // ✅ ShipmentEventController : /api/v1/shipments/{shipmentId}/events
//                        // =====================================================
//                        // GET → Shipment owner OR ADMIN OR CUSTOMER_SUPPORT
//                        // (Owner logic needs method-level check, so we allow ADMIN + CUSTOMER_SUPPORT here)
//                        .requestMatchers(HttpMethod.GET, "/api/v1/shipments/*/events/**")
//                        .hasAnyRole("ADMIN", "CUSTOMER_SUPPORT")
//
//                        // POST → ADMIN, WAREHOUSE_MANAGER
//                        .requestMatchers(HttpMethod.POST, "/api/v1/shipments/*/events/**")
//                        .hasAnyRole("ADMIN", "WAREHOUSE_MANAGER")
//
//                        .requestMatchers(HttpMethod.POST,"/orders")
//                        .hasRole("CUSTOMER")
//                        // =====================================================
//                        // Default rule
//                        // =====================================================
//                        .anyRequest().authenticated()
//                )
//
//                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
//
//                .build();
//    }
//
//    @Bean
//    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
//        return config.getAuthenticationManager();
//    }
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//}
//
package com.exam.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
@Slf4j
public class SecurityConfiguration {

    private final CustomJwtVerificationFilter jwtFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        log.info("******** configuring spring security filter chain ********");

        return http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                .authorizeHttpRequests(req -> req

                        // ✅ Swagger Public
                        .requestMatchers(
                                "/v3/api-docs/**",
                                "/swagger-ui/**",
                                "/swagger-ui.html"
                        ).permitAll()

                        // ✅ Public Auth APIs
                        .requestMatchers(HttpMethod.POST, "/users").permitAll()
                        .requestMatchers(HttpMethod.POST, "/users/signin").permitAll()

                        // ✅ Preflight requests
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                        // =====================================================
                        // ✅ ProductController : /api/v1/products
                        // =====================================================
                        .requestMatchers(HttpMethod.GET, "/api/v1/products/**").authenticated()
                        .requestMatchers(HttpMethod.POST, "/api/v1/products/**").hasAnyRole("ADMIN", "WAREHOUSE_MANAGER")
                        .requestMatchers(HttpMethod.PUT, "/api/v1/products/**").hasAnyRole("ADMIN", "WAREHOUSE_MANAGER")
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/products/**").hasAnyRole("ADMIN", "WAREHOUSE_MANAGER")
                        .requestMatchers(HttpMethod.PATCH, "/api/v1/products/**").hasAnyRole("ADMIN", "WAREHOUSE_MANAGER")

                        // =====================================================
                        // ✅ WarehouseController : /api/v1/warehouses
                        // =====================================================
                        .requestMatchers(HttpMethod.GET, "/api/v1/warehouses/**")
                        .hasAnyRole("ADMIN", "WAREHOUSE_MANAGER", "CUSTOMER_SUPPORT")

                        .requestMatchers(HttpMethod.POST, "/api/v1/warehouses/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/v1/warehouses/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/warehouses/**").hasRole("ADMIN")

                        // =====================================================
                        // ✅ InventoryController : /api/v1/inventory
                        // =====================================================
                        .requestMatchers(HttpMethod.GET, "/api/v1/inventory/**").authenticated()

                        .requestMatchers(HttpMethod.POST, "/api/v1/inventory/**").hasAnyRole("ADMIN", "WAREHOUSE_MANAGER")
                        .requestMatchers(HttpMethod.PUT, "/api/v1/inventory/**").hasAnyRole("ADMIN", "WAREHOUSE_MANAGER")

                        .requestMatchers(HttpMethod.POST, "/api/v1/inventory/*/reserve").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/v1/inventory/*/release").hasRole("ADMIN")

                        .requestMatchers(HttpMethod.POST, "/api/v1/inventory/transfer")
                        .hasAnyRole("ADMIN", "WAREHOUSE_MANAGER")

                        // =====================================================
                        // ✅ InventoryTransactionController : /api/v1/inventory-transactions
                        // =====================================================
                        .requestMatchers("/api/v1/inventory-transactions/**")
                        .hasAnyRole("ADMIN", "WAREHOUSE_MANAGER")

                        // =====================================================
                        // ✅ ShipmentController : /api/v1/shipments
                        // =====================================================
                        .requestMatchers(HttpMethod.GET, "/api/v1/shipments/track/**").permitAll()

                        .requestMatchers(HttpMethod.GET, "/api/v1/shipments/**")
                        .hasAnyRole("ADMIN", "WAREHOUSE_MANAGER", "CUSTOMER_SUPPORT")

                        .requestMatchers(HttpMethod.POST, "/api/v1/shipments")
                        .hasAnyRole("ADMIN", "WAREHOUSE_MANAGER")

                        .requestMatchers(HttpMethod.PUT, "/api/v1/shipments/**")
                        .hasAnyRole("ADMIN", "WAREHOUSE_MANAGER")

                        .requestMatchers(HttpMethod.POST, "/api/v1/shipments/**")
                        .hasAnyRole("ADMIN", "WAREHOUSE_MANAGER")

                        // =====================================================
                        // ✅ CarrierController : /api/v1/carriers
                        // =====================================================
                        .requestMatchers(HttpMethod.GET, "/api/v1/carriers/**").authenticated()

                        .requestMatchers(HttpMethod.POST, "/api/v1/carriers/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/v1/carriers/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/carriers/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PATCH, "/api/v1/carriers/**").hasRole("ADMIN")

                        // =====================================================
                        // ✅ ShipmentEventController : /api/v1/shipments/{shipmentId}/events
                        // =====================================================
                        .requestMatchers(HttpMethod.GET, "/api/v1/shipments/*/events/**")
                        .hasAnyRole("ADMIN", "CUSTOMER_SUPPORT")

                        .requestMatchers(HttpMethod.POST, "/api/v1/shipments/*/events/**")
                        .hasAnyRole("ADMIN", "WAREHOUSE_MANAGER")

                        // =====================================================
                        // ✅ OrdersController : /orders
                        // =====================================================
                        .requestMatchers(HttpMethod.POST, "/orders").hasRole("CUSTOMER")
                        .requestMatchers(HttpMethod.PUT, "/orders/**").hasAnyRole("ADMIN", "WAREHOUSE_MANAGER")
                        .requestMatchers(HttpMethod.GET, "/orders/**").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/orders/**").hasAnyRole("ADMIN", "WAREHOUSE_MANAGER")

                        // =====================================================
                        // ✅ ReturnController : /returns
                        // =====================================================
                        // GET -> Any authenticated user
                        .requestMatchers(HttpMethod.GET, "/returns/**").authenticated()

                        // POST -> Only CUSTOMER
                        .requestMatchers(HttpMethod.POST, "/returns/**").hasRole("CUSTOMER")

                        // PUT + DELETE -> ADMIN, WAREHOUSE_MANAGER
                        .requestMatchers(HttpMethod.PUT, "/returns/**").hasAnyRole("ADMIN", "WAREHOUSE_MANAGER")
                        .requestMatchers(HttpMethod.DELETE, "/returns/**").hasAnyRole("ADMIN", "WAREHOUSE_MANAGER")

                        // =====================================================
                        // Default rule
                        // =====================================================
                        .anyRequest().authenticated()
                )

                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)

                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
