package com.ardademirtas.entity;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "customer")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Customer {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "first_name")
	@NotBlank
	private String firstname;
	
	@Column(name = "last_name")
	@NotBlank
	private String lastname;
	
	@Column(name = "tc_no",unique = true)
	@Size(min = 11, max = 11,message = "TC No must be exactly 11 digits")
	@NotBlank
	private String tcno;
	
	@Column(name = "email",unique = true)
	@Email(message = "It must comply with the email format.")
	@NotBlank
	private String email;
	
	@Column(name = "phone",unique = true)
	@Pattern(regexp = "\\d{11}", message = "Phone number must contain exactly 11 digits.")
	@NotBlank
	private String phone;
	
	@Column(name = "created_time")
	@DateTimeFormat(iso = ISO.DATE)
	private Date  createdTime;
	
	
    @OneToOne
	@JoinColumn(name = "user_id", referencedColumnName = "id", unique = true, nullable = false)
	private User user;
	

}
