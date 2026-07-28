package com.ardademirtas.entity;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import com.ardademirtas.enums.CardType;
import com.ardademirtas.enums.Status;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "card")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Card {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "card_number")
	@NotBlank
	@Size(min = 16,max = 16,message = "The card number must be exactly 16 digits long.")
	private String cardNumber;
	
	@Column(name = "cvv")
	@NotBlank
	@Size(min = 3,max = 3)
	private String  cvv;
	
	
	@Column(name = "expired_date")
	@DateTimeFormat(iso = ISO.DATE)
	private Date expiredDate;
	
	
	@Column(name = "card_type")
	@Enumerated(EnumType.STRING)
	private CardType  cardType;
	
	
	@Column(name = "status")
	@Enumerated(EnumType.STRING)
	private Status status;
	
	
	@ManyToOne
	private Account account;
	

}
