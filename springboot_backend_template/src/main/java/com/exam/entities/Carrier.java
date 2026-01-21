package com.exam.entities;

import java.sql.Timestamp;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "carriers", uniqueConstraints = {
		@UniqueConstraint(columnNames = "carrier_code")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Carrier {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "carrier_code", nullable = false, unique = true)
	private String carrierCode;

	@Column(name = "carrier_name", nullable = false)
	private String carrierName;

	@Column(name = "contact_email")
	private String contactEmail;

	@Column(name = "created_at")
	private Timestamp createdAt;

	@Column(nullable = false)
	private Boolean isActive = true;
}
