package com.exam.service;

import org.springframework.data.domain.*;

import com.exam.dto.ProductAvailabilityDTO;
import com.exam.dto.ProductDTO;

public interface ProductService {

	ProductDTO createProduct(ProductDTO dto);

	Page<ProductDTO> getAllProducts(Pageable pageable, String search);

	ProductDTO getProductById(Long id);

	ProductDTO getProductBySku(String sku);

	ProductDTO updateProduct(Long id, ProductDTO dto);

	String deleteProduct(Long id);

	ProductAvailabilityDTO getProductAvailability(Long productId);

	ProductDTO activateDeactivateProduct(Long id, boolean isActive);
}
