package com.ardademirtas.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ardademirtas.Utils.CheckAuthorization;
import com.ardademirtas.Utils.DateUtils;
import com.ardademirtas.dto.CurrencyRatesResponse;
import com.ardademirtas.dto.DtoTransaction;
import com.ardademirtas.dto.DtoTransactionIU;
import com.ardademirtas.dto.DtoTransactionSpecialIU;
import com.ardademirtas.entity.Account;
import com.ardademirtas.entity.Card;
import com.ardademirtas.entity.Transaction;
import com.ardademirtas.enums.CurrencyType;
import com.ardademirtas.enums.Status;
import com.ardademirtas.enums.TransactionType;
import com.ardademirtas.exception.BaseException;
import com.ardademirtas.exception.ErrorMessage;
import com.ardademirtas.exception.MessageType;
import com.ardademirtas.repository.AccountRepository;
import com.ardademirtas.repository.CardRepository;
import com.ardademirtas.repository.TransactionRepository;
import com.ardademirtas.service.ICurrencyRatesService;
import com.ardademirtas.service.ITransactionService;

@Service
public class TransactionServiceImpl extends CheckAuthorization implements ITransactionService {

	

	@Autowired
	private TransactionRepository transactionRepository;

	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private CardRepository cardRepository;
	
	@Autowired
	private ICurrencyRatesService currencyRatesService;

	

	@Override
	@Transactional
	public DtoTransaction saveTransaction(DtoTransactionIU dtoTransactionIU) {

		Long sender_account_id = dtoTransactionIU.getSender_account_id();
		Long receiver_account_id = dtoTransactionIU.getReceiver_account_id();
		
		
		if (dtoTransactionIU.getTransactionType()==TransactionType.DEPOSIT || dtoTransactionIU.getTransactionType() ==TransactionType.WITHDRAW) {
			
			throw new BaseException(new ErrorMessage(MessageType.INVALID_TYPE,dtoTransactionIU.getTransactionType().toString()));
		}
		
		

		if (sender_account_id.equals(receiver_account_id)) {

			throw new BaseException(new ErrorMessage(MessageType.GENERAL_EXCEPTİON,
					sender_account_id.toString() + "--?" + receiver_account_id.toString()));
		}
		
		

		Account senderOptional = accountRepository.findById(sender_account_id).orElseThrow(
				() -> new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST, sender_account_id.toString())));

		Account receiverOptional = accountRepository.findById(receiver_account_id).orElseThrow(
				() -> new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST, receiver_account_id.toString())));

	
		
		checkAuthorization(senderOptional.getCustomer().getUser().getUsername());

		
		
		if (senderOptional.getStatus() == Status.BLOCKED || receiverOptional.getStatus() == Status.BLOCKED) {

			throw new BaseException(new ErrorMessage(MessageType.ACCOUNT_BLOKE, ""));
		}
		

		Card card_Sender = cardRepository.findByAccount(senderOptional)
				.orElseThrow(() -> new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST, "")));

		Card card_receiver = cardRepository.findByAccount(receiverOptional)
				.orElseThrow(() -> new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST, "")));

		
		
		if (card_receiver.getStatus() == Status.BLOCKED || card_Sender.getStatus() == Status.BLOCKED) {

			throw new BaseException(new ErrorMessage(MessageType.CARD_BLOKE, ""));
		}
		

		if (!expireControlCard(card_Sender.getExpiredDate()) || !expireControlCard(card_receiver.getExpiredDate())) {

			throw new BaseException(new ErrorMessage(MessageType.CARD_EXPIRE, ""));
		}
		
			
		return    currencyConversion(dtoTransactionIU, senderOptional, receiverOptional);
		

	}
	
	

	@Override
	@Transactional
	public DtoTransaction saveSpecialTransaction(DtoTransactionSpecialIU dtoTransactionSpecialIU) {
	
		
		
		if (dtoTransactionSpecialIU.getTransactionType()==TransactionType.TRANSFER) {
			
			throw  new BaseException(new ErrorMessage(MessageType.INVALID_TYPE,dtoTransactionSpecialIU
			.getTransactionType().toString()));
		}
		
		
		
	     Long account_id = dtoTransactionSpecialIU.getAccount_id();
		
         Account account = accountRepository.findById(account_id).orElseThrow(()->
	     new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST, account_id.toString())));
         
         checkAuthorization(account.getCustomer().getUser().getUsername());
		
             
         Card card = cardRepository.findByAccount(account).orElseThrow(()->
         new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST, "")));
         
         
      
         if (card.getStatus()==Status.BLOCKED) {
			 
        	 throw new BaseException(new ErrorMessage(MessageType.CARD_BLOKE, ""));
		} 
         
         if (account.getStatus()==Status.BLOCKED) {
			
        	 throw new BaseException(new ErrorMessage(MessageType.ACCOUNT_BLOKE,""));
		}
         
         
         if (!expireControlCard(card.getExpiredDate())) {
			
        	 throw new BaseException(new ErrorMessage(MessageType.CARD_EXPIRE, ""));
		}
         

         
         if (dtoTransactionSpecialIU.getTransactionType()==TransactionType.DEPOSIT) {
			
        	   
             Transaction depositTransaction = depositTransaction(dtoTransactionSpecialIU, account);  
             
             
             DtoTransaction dtoTransaction= new DtoTransaction();
             
             BeanUtils.copyProperties(depositTransaction, dtoTransaction);
             
             return dtoTransaction;   
      	 
		}
                   
         
         if (dtoTransactionSpecialIU.getTransactionType()==TransactionType.WITHDRAW) {
        	 
        	 Transaction wıthDrawTransactıon = wıthDrawTransactıon(dtoTransactionSpecialIU, account);
        	    
        	    
                DtoTransaction dtoTransaction= new DtoTransaction();
                
                BeanUtils.copyProperties(wıthDrawTransactıon, dtoTransaction);
                
                return dtoTransaction; 	          
			
		} 
		return null;
	}
	
	
	

	private Boolean expireControlCard(Date date) {

		return new Date().before(date);
	}

	
	
	private DtoTransaction createTransaction(DtoTransactionIU dtoTransactionIU, Account senderOptional,
			Account receiverOptionel, CurrencyRatesResponse currencyRatesResponse) {


		BigDecimal amount = dtoTransactionIU.getAmount();

		if (amount.compareTo(BigDecimal.ZERO) <= 0) {

			throw new BaseException(new ErrorMessage(MessageType.INVALID_AMOUNT, amount.toString()));
		}

		if (senderOptional.getBalance().compareTo(BigDecimal.ZERO) < 0) {

			throw new BaseException(
					new ErrorMessage(MessageType.INVALID_BALANCE, senderOptional.getBalance().toString()));
		}

		if (senderOptional.getBalance().compareTo(amount) < 0) {

			throw new BaseException(
					new ErrorMessage(MessageType.INSUFFICIENT_BALANCE, senderOptional.getBalance().toString()));
		}

		Transaction transaction = new Transaction();

		BeanUtils.copyProperties(dtoTransactionIU, transaction);

		transaction.setTransactionDate(new Date());
		transaction.setSender_account_id(senderOptional);
		transaction.setReceiver_account_id(receiverOptionel);
		
		
		if (senderOptional.getCurrencyType()==CurrencyType.TRY && receiverOptionel.getCurrencyType()==CurrencyType.TRY
			||  senderOptional.getCurrencyType()==CurrencyType.USD && receiverOptionel.getCurrencyType()==CurrencyType.USD) {
			
			transaction.setExchangeRate(null);
			transaction.setConvertedAmount(null);
			
		}
		
		
		 
	 
		
	     if ( senderOptional.getCurrencyType()==CurrencyType.TRY && receiverOptionel.getCurrencyType()==CurrencyType.USD ) {
	    	
	    	 BigDecimal  usdRate= new BigDecimal(currencyRatesResponse.getItems().get(0).getUsd());
	    	 
		       BigDecimal usdSave = dtoTransactionIU.getAmount().divide(usdRate, 2,RoundingMode.HALF_UP);
		       
		       transaction.setExchangeRate(usdRate);
		       transaction.setConvertedAmount(usdSave);	
		       
		}else if (senderOptional.getCurrencyType()==CurrencyType.USD && receiverOptionel.getCurrencyType()==CurrencyType.TRY) {
		      
			BigDecimal  usdRate= new BigDecimal(currencyRatesResponse.getItems().get(0).getUsd());
			 BigDecimal  usdSave =dtoTransactionIU.getAmount().multiply(usdRate);
			   transaction.setExchangeRate(usdRate);
			   transaction.setConvertedAmount(usdSave);
			   
		}
    
	
		Transaction save = transactionRepository.save(transaction);

		DtoTransaction dtoTransaction = new DtoTransaction();
		BeanUtils.copyProperties(save, dtoTransaction);

		return dtoTransaction;
	}
	
	
	
	private void   sameTransaction(DtoTransactionIU dtoTransactionIU, Account senderOptional,
			Account receiverOptional) {
		
			
		BigDecimal amount = dtoTransactionIU.getAmount();
		BigDecimal subtractBalance = senderOptional.getBalance().subtract(amount);

		senderOptional.setBalance(subtractBalance);
		accountRepository.save(senderOptional);

		BigDecimal addBalance = receiverOptional.getBalance().add(amount);

		receiverOptional.setBalance(addBalance);

		accountRepository.save(receiverOptional);
		
	} 
	
	
	
	private Transaction  depositTransaction(DtoTransactionSpecialIU dtoTransactionSpecialIU ,Account account) {
		
	
         BigDecimal amount = dtoTransactionSpecialIU.getAmount();
         

 		if (amount.compareTo(BigDecimal.ZERO) <= 0) {

 			throw new BaseException(new ErrorMessage(MessageType.INVALID_AMOUNT, amount.toString()));
 		}

        
         BigDecimal addBalance = account.getBalance().add(amount);
         account.setBalance(addBalance);
        
         accountRepository.save(account);
         
         Transaction transaction = new Transaction();
         
         BeanUtils.copyProperties(dtoTransactionSpecialIU, transaction);
         transaction.setTransactionDate(new Date());
         transaction.setSender_account_id(account);
 

         return   transactionRepository.save(transaction);
         	
	}
	
	private Transaction   wıthDrawTransactıon(DtoTransactionSpecialIU dtoTransactionSpecialIU,Account account) {
		
		
	 BigDecimal amount = dtoTransactionSpecialIU.getAmount();
	 

		if (amount.compareTo(BigDecimal.ZERO) <= 0) {

			throw new BaseException(new ErrorMessage(MessageType.INVALID_AMOUNT, amount.toString()));
		}

	 
	 if (account.getBalance().compareTo(amount) < 0 ) {
		
		 throw new BaseException(new ErrorMessage(MessageType.INSUFFICIENT_BALANCE,account.getBalance().toString()));
	}
	 
	 
	
	 
	 BigDecimal subtract = account.getBalance().subtract(amount);
	 account.setBalance(subtract);
	 accountRepository.save(account);
	
	   Transaction transaction= new Transaction();
	   
	   BeanUtils.copyProperties(dtoTransactionSpecialIU, transaction);
	   transaction.setTransactionDate(new Date());
	   transaction.setSender_account_id(account);
	  
	   
	   
	         return  transactionRepository.save(transaction);
		
	
	}
	
	
	
	
	private DtoTransaction currencyConversion(DtoTransactionIU dtoTransactionIU, Account senderOptional,
			Account receiverOptional) {
		
		
		CurrencyRatesResponse currencyRatesResponse = currencyRatesService.getCurrencyRatesResponse(DateUtils.getCurrentDate(new Date()), DateUtils.getCurrentDate(new Date()));
		
		
		
		if (senderOptional.getCurrencyType() == CurrencyType.TRY
				&& receiverOptional.getCurrencyType() == CurrencyType.TRY) {

			DtoTransaction dtoTransaction = createTransaction(dtoTransactionIU, senderOptional, receiverOptional,currencyRatesResponse);
			sameTransaction(dtoTransactionIU, senderOptional, receiverOptional);
	

			return dtoTransaction;
   	}
		
		if (senderOptional.getCurrencyType()==CurrencyType.USD
				&& receiverOptional.getCurrencyType()==CurrencyType.USD) {
		
			
             DtoTransaction dtoTransaction = createTransaction(dtoTransactionIU, senderOptional, receiverOptional,currencyRatesResponse);
			 sameTransaction(dtoTransactionIU, senderOptional, receiverOptional);
			
			return  dtoTransaction;
					
	}else {
		
		 
		return     diffTypeCurrencyConversation(dtoTransactionIU, senderOptional, receiverOptional,currencyRatesResponse);
	}	
		
	}
		

	
	private DtoTransaction  diffTypeCurrencyConversation(DtoTransactionIU dtoTransactionIU, Account senderOptional,
			Account receiverOptional, CurrencyRatesResponse  currencyRatesResponse) {
		
		
	      BigDecimal  usdRates= new BigDecimal(currencyRatesResponse.getItems().get(0).getUsd());
		
		
		 if (senderOptional.getCurrencyType()==CurrencyType.TRY
				&& receiverOptional.getCurrencyType()==CurrencyType.USD) {
			
			
			 DtoTransaction dtoTransaction = createTransaction(dtoTransactionIU, senderOptional, receiverOptional,currencyRatesResponse);
			
			
			   BigDecimal usdSave = dtoTransactionIU.getAmount().divide(usdRates, 2,RoundingMode.HALF_UP);
		       
		       receiverOptional.setBalance(receiverOptional.getBalance().add(usdSave));
		       
		       accountRepository.save(receiverOptional);
		       
		       
		       BigDecimal subtract = senderOptional.getBalance().subtract(dtoTransactionIU.getAmount());
		       senderOptional.setBalance(subtract);
		       
		       accountRepository.save(senderOptional);
		       
				return dtoTransaction;	
				
		}else {
			
   DtoTransaction dtoTransaction = createTransaction(dtoTransactionIU, senderOptional, receiverOptional,currencyRatesResponse);
			
   
   BigDecimal saveUsd = dtoTransactionIU.getAmount().multiply(usdRates);
   
   receiverOptional.setBalance(receiverOptional.getBalance().add(saveUsd));
   accountRepository.save(receiverOptional);
   
   BigDecimal subtract = senderOptional.getBalance().subtract(dtoTransactionIU.getAmount());
   
   senderOptional.setBalance(subtract);
   
   accountRepository.save(senderOptional);
				
			return dtoTransaction;
			
			
	               	}
		
		
	}


	@Override
	public List<DtoTransaction> getTransaction(Long id) {
	
          Account optional = accountRepository.findById(id).orElseThrow(
		  ()->new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST, id.toString())));
		
	  	checkAuthorization(optional.getCustomer().getUser().getUsername());
		
		
     List<Transaction> bySenderAccountIdOrReceiverAccountId = transactionRepository.findAccountTransactions(id, id);
		
     List<DtoTransaction>dtoTransactions= new ArrayList<>();
     
     for (Transaction Transaction : bySenderAccountIdOrReceiverAccountId) {
    	 
    	 DtoTransaction dtoTransaction= new DtoTransaction();
    	 
    	 BeanUtils.copyProperties(Transaction, dtoTransaction);
    	 
    	 
    	    dtoTransactions.add(dtoTransaction);

	}
		
		return dtoTransactions;
	}
	

}
