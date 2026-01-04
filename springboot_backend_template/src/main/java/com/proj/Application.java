package com.proj;

import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	// configure ModelMapper class as a spring bean
	@Bean
	ModelMapper modelMapper() {
		ModelMapper mapper = new ModelMapper();
		mapper.getConfiguration() //get default config
		.setPropertyCondition(Conditions.isNotNull()) //transfer only not null props from src-> dest
		.setMatchingStrategy(MatchingStrategies.STRICT);//transfer the props form src -> dest which match by name & data type
	
		return mapper;
	}

}

//
//| Controller     | Endpoint                                         | Description              |
//| -------------- | ------------------------------------------------ | ------------------------ |
//| Carrier        | `GET /api/carriers`                              | Get all carriers         |
//| Carrier        | `GET /api/carriers/{id}`                         | Get carrier by ID        |
//| Product        | `GET /api/products`                              | Get all products         |
//| Warehouse      | `GET /api/warehouses`                            | Get all warehouses       |
//| Inventory      | `GET /api/inventory`                             | Get inventory records    |
//| Inventory Txn  | `GET /api/inventory-transactions/inventory/{id}` | Inventory history        |
//| Shipment       | `GET /api/shipments`                             | Get all shipments        |
//| Shipment Event | `GET /api/shipment-events/shipment/{id}`         | Shipment tracking events |
