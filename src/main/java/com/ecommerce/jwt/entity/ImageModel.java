package com.ecommerce.jwt.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "image_model")
public class ImageModel {

	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "image_model_seq")
    @SequenceGenerator(name = "image_model_seq", sequenceName = "image_model_seq", allocationSize = 1)
	private Long id;
	private String name;
	private String type;
	@Lob
	//@Column(length = 50000000)
	@Column(name = "pic_byte", columnDefinition = "bytea")
	private byte[] picByte;
}
