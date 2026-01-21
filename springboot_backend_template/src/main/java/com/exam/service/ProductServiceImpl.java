package com.exam.service;

import java.sql.Timestamp;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.exam.custom_exceptions.*;
import com.exam.dto.ProductAvailabilityDTO;
import com.exam.dto.ProductDTO;
import com.exam.entities.Product;
import com.exam.repository.InventoryRepository;
import com.exam.repository.ProductRepository;

import lombok.AllArgsConstructor;

@Service
@Transactional
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {

	private final ProductRepository productRepository;
	private final InventoryRepository inventoryRepository;
	private final ModelMapper modelMapper;

	@Override
	public ProductDTO createProduct(ProductDTO dto) {

		if (productRepository.existsBySku(dto.getSku()))
			throw new DuplicateResourceException("Product already exists with SKU: " + dto.getSku());

		Product p = modelMapper.map(dto, Product.class);
		p.setId(null);
		p.setCreatedAt(new Timestamp(System.currentTimeMillis()));
		if (p.getIsActive() == null)
			p.setIsActive(true);

		return modelMapper.map(productRepository.save(p), ProductDTO.class);
	}

	@Override
	public Page<ProductDTO> getAllProducts(Pageable pageable, String search) {

		Page<Product> page;

		if (search == null || search.isBlank())
			page = productRepository.findAll(pageable);
		else
			page = productRepository.findByNameContainingIgnoreCase(search, pageable);

		return page.map(p -> modelMapper.map(p, ProductDTO.class));
	}

	@Override
	public ProductDTO getProductById(Long id) {
		Product p = productRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Product not found with ID: " + id));
		return modelMapper.map(p, ProductDTO.class);
	}

	@Override
	public ProductDTO getProductBySku(String sku) {
		Product p = productRepository.findBySku(sku)
				.orElseThrow(() -> new ResourceNotFoundException("Product not found with SKU: " + sku));
		return modelMapper.map(p, ProductDTO.class);
	}

	@Override
	public ProductDTO updateProduct(Long id, ProductDTO dto) {

		Product p = productRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Product not found with ID: " + id));

		if (dto.getSku() != null && !dto.getSku().equals(p.getSku())) {
			if (productRepository.existsBySku(dto.getSku()))
				throw new DuplicateResourceException("SKU already exists: " + dto.getSku());
		}

		// STRICT + NOT NULL -> only non-null fields update
		modelMapper.map(dto, p);

		return modelMapper.map(productRepository.save(p), ProductDTO.class);
	}

	@Override
	public String deleteProduct(Long id) {
		Product p = productRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Product not found with ID: " + id));
		productRepository.delete(p);
		return "Product deleted successfully";
	}

	@Override
	public ProductAvailabilityDTO getProductAvailability(Long productId) {

		// total stock across all warehouses for product
		Integer totalQty = inventoryRepository.sumQuantityByProductId(productId);
		Integer totalReserved = inventoryRepository.sumReservedByProductId(productId);

		if (totalQty == null) totalQty = 0;
		if (totalReserved == null) totalReserved = 0;

		return new ProductAvailabilityDTO(productId, totalQty, totalReserved, totalQty - totalReserved);
	}

	@Override
	public ProductDTO activateDeactivateProduct(Long id, boolean isActive) {
		Product p = productRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Product not found with ID: " + id));
		p.setIsActive(isActive);
		return modelMapper.map(productRepository.save(p), ProductDTO.class);
	}
}
