package com.ardademirtas.service.impl;

import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ardademirtas.Utils.CheckAuthorization;
import com.ardademirtas.dto.DtoCard;
import com.ardademirtas.dto.DtoCardIU;
import com.ardademirtas.entity.Account;
import com.ardademirtas.entity.Card;
import com.ardademirtas.enums.CardType;
import com.ardademirtas.enums.Status;
import com.ardademirtas.exception.BaseException;
import com.ardademirtas.exception.ErrorMessage;
import com.ardademirtas.exception.MessageType;
import com.ardademirtas.repository.AccountRepository;
import com.ardademirtas.repository.CardRepository;
import com.ardademirtas.service.ICardService;

@Service
public class CardServiceImpl extends CheckAuthorization  implements ICardService {

	
	@Autowired
	private CardRepository cardRepository;
	
	@Autowired
	private AccountRepository accountRepository;
	

	@Override
	public DtoCard save(DtoCardIU dtoCardIU) {
		
		        
		            
     Account dbAccount =accountRepository.findById(dtoCardIU.getAccount_id()).orElseThrow(
	 ()-> new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST, dtoCardIU.getAccount_id().toString())));
     
                
     
     
		
	checkAuthorization(dbAccount.getCustomer().getUser().getUsername());
	
	long cardNumber = (long)(Math.random() * 9000000000000000L)
            + 1000000000000000L;
	
	  String cardN = String.valueOf(cardNumber);
	
	
	int cvv = (int)(Math.random() * 900) + 100;
	
	 String c_v_v= String.valueOf(cvv);
	 
	 
	 Calendar calendar = Calendar.getInstance();

	 calendar.setTime(new Date());   // Şu anki tarih

	 calendar.add(Calendar.YEAR, 4); // 4 yıl ekle
		
		
	Card card= new Card();
	card.setAccount(dbAccount);
	card.setCardNumber(cardN);
	card.setCvv(c_v_v);
	card.setCardType(CardType.DEBIT);
	card.setExpiredDate(calendar.getTime());
	card.setStatus(Status.ACTIVE);
	
	
	   Card save = cardRepository.save(card);
		 
		
	   DtoCard dtoCard= new DtoCard();
	   
	   BeanUtils.copyProperties(save, dtoCard);
		
		return dtoCard;
	}


       }
