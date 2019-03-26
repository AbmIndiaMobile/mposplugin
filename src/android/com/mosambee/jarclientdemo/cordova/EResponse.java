/********************************************************
 * Copyright (C) 2012 Ezetap Mobile Solutions Pvt. Ltd.
 * 
 * This software is distributed for illustration purpose on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND.
 *
 *******************************************************/
package com.mosambee.jarclientdemo.cordova;

import org.json.JSONObject;


/**
 * Sample class for Ezetap server response. Ezetap application sends json formatted response as part of transaction result.
 * 
 */
public  class EResponse {

	public boolean success =false;
	public String errorCode;
	public String errorMessage;
	public String authCode;
	
	private Long transactionId;
	
	private String reverseReferenceNumber;
	private String displayPAN;
	private String orderNumber;
	private String nameOnCard;
	private String acquisitionKey;
	private double amount;
	private double totalAmount;
	private double additionalAmount;
	private double mobileNo;
	private String customerReceiptUrl;
	private String merchantReceiptUrl;
	private String merchantName;
	private String cardType;
	private String userAgreement;
	private String currencyCode;
	
	private String jsonRepresentation;
	
	public EResponse(String strJson) throws Exception {
		this(new JSONObject(strJson));
	}
	
	public EResponse(JSONObject json) throws Exception {
		success 	= json.getBoolean("success");
		jsonRepresentation = json.toString();
		if(success == false )  {
			errorCode 	= json.getString("errorCode");
			errorMessage = json.getString("errorMessage");
		} else {
			if(json.has("authCode")) {
				authCode 	= json.getString("authCode");
			}
		}
		
		
		if(success) {
			if(json.has("transactionId"))transactionId = json.getLong("transactionId");
			if(json.has("amount"))amount = json.getDouble("amount");
			if(json.has("totalAmount"))totalAmount = json.getDouble("totalAmount");
			if(json.has("additionalAmount"))additionalAmount = json.getDouble("additionalAmount");
			if(json.has("displayPAN"))displayPAN = json.getString("displayPAN");
			if(json.has("orderNumber"))orderNumber = json.getString("orderNumber");
			if(json.has("customerReceiptUrl"))customerReceiptUrl = json.getString("customerReceiptUrl");
			if(json.has("amomerchantReceiptUrlunt"))merchantReceiptUrl = json.getString("merchantReceiptUrl");
			if(json.has("nameOnCard"))nameOnCard = json.getString("nameOnCard");
			if(json.has("acquisitionKey"))acquisitionKey = json.getString("acquisitionKey");
			if(json.has("reverseReferenceNumber"))reverseReferenceNumber = json.getString("reverseReferenceNumber");
			if(json.has("userAgreement"))userAgreement = json.getString("userAgreement");
			if(json.has("cardType")) {
				cardType = json.getString("cardType");
			}
			if(json.has("merchantName"))merchantName = json.getString("merchantName");
			if(json.has("currencyCode"))currencyCode = json.getString("currencyCode");
		}
		
		
	}
	
	
	
	
	
	public String getJsonRepresentation() {
		return jsonRepresentation;
	}
	
	/**
	 * Authentication code returned by the payment processor for a successful transaction. This needs to be persisted
	 * with your application. 
	 */
	public String getAuthCode() {
		return authCode;
	}
	/**
	 * Indicates that the request was successful.
	 */
	public boolean isSuccess() {
		return success;
	}
	
	/**
	 * If the transaction was not successful, this returns the error code for the failure.
	 */
	public String getErrorCode() {
		return errorCode;
	}

	/**
	 * If the transaction was not successful, this returns the error message for the failure.
	 */
	public String getErrorMessage() {
		return errorMessage;
	}

	/**
	 * @param transactionId the transactionId to set
	 */
	public void setTransactionId(Long transactionId) {
		this.transactionId = transactionId;
	}

	/**
	 * @return the transactionId
	 */
	public Long getTransactionId() {
		return transactionId;
	}

	/**
	 * @param reverseReferenceNumber the reverseReferenceNumber to set
	 */
	public void setReverseReferenceNumber(String reverseReferenceNumber) {
		this.reverseReferenceNumber = reverseReferenceNumber;
	}

	/**
	 * @return the reverseReferenceNumber
	 */
	public String getReverseReferenceNumber() {
		return reverseReferenceNumber;
	}

	/**
	 * @param displayPAN the displayPAN to set
	 */
	public void setDisplayPAN(String displayPAN) {
		this.displayPAN = displayPAN;
	}

	/**
	 * @return the displayPAN
	 */
	public String getDisplayPAN() {
		return displayPAN;
	}

	/**
	 * @param nameOnCard the nameOnCard to set
	 */
	public void setNameOnCard(String nameOnCard) {
		this.nameOnCard = nameOnCard;
	}

	/**
	 * @return the nameOnCard
	 */
	public String getNameOnCard() {
		return nameOnCard;
	}

	/**
	 * @param orderNumber the orderNumber to set
	 */
	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	/**
	 * @return the orderNumber
	 */
	public String getOrderNumber() {
		return orderNumber;
	}

	/**
	 * @param acquisitionKey the acquisitionKey to set
	 */
	public void setAcquisitionKey(String acquisitionKey) {
		this.acquisitionKey = acquisitionKey;
	}

	/**
	 * @return the acquisitionKey
	 */
	public String getAcquisitionKey() {
		return acquisitionKey;
	}

	/**
	 * @param amount the amount to set
	 */
	public void setAmount(double amount) {
		this.amount = amount;
	}

	/**
	 * @return the amount
	 */
	public double getAmount() {
		return amount;
	}

	/**
	 * @param totalAmount the totalAmount to set
	 */
	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}

	/**
	 * @return the totalAmount
	 */
	public double getTotalAmount() {
		return totalAmount;
	}

	/**
	 * @param additionalAmount the additionalAmount to set
	 */
	public void setAdditionalAmount(double additionalAmount) {
		this.additionalAmount = additionalAmount;
	}

	/**
	 * @return the additionalAmount
	 */
	public double getAdditionalAmount() {
		return additionalAmount;
	}

	/**
	 * @param mobileNo the mobileNo to set
	 */
	public void setMobileNo(double mobileNo) {
		this.mobileNo = mobileNo;
	}

	/**
	 * @return the mobileNo
	 */
	public double getMobileNo() {
		return mobileNo;
	}

	/**
	 * @param customerReceiptUrl the customerReceiptUrl to set
	 */
	public void setCustomerReceiptUrl(String customerReceiptUrl) {
		this.customerReceiptUrl = customerReceiptUrl;
	}

	/**
	 * @return the customerReceiptUrl
	 */
	public String getCustomerReceiptUrl() {
		return customerReceiptUrl;
	}

	/**
	 * @param merchantReceiptUrl the merchantReceiptUrl to set
	 */
	public void setMerchantReceiptUrl(String merchantReceiptUrl) {
		this.merchantReceiptUrl = merchantReceiptUrl;
	}

	/**
	 * @return the merchantReceiptUrl
	 */
	public String getMerchantReceiptUrl() {
		return merchantReceiptUrl;
	}

	/**
	 * @param merchantName the merchantName to set
	 */
	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	/**
	 * @return the merchantName
	 */
	public String getMerchantName() {
		return merchantName;
	}

	/**
	 * @param cardType the cardType to set
	 */
	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	/**
	 * @return the cardType
	 */
	public String getCardType() {
		return cardType;
	}

	/**
	 * @param userAgreement the userAgreement to set
	 */
	public void setUserAgreement(String userAgreement) {
		this.userAgreement = userAgreement;
	}

	/**
	 * @return the userAgreement
	 */
	public String getUserAgreement() {
		return userAgreement;
	}

	/**
	 * @param currencyCode the currencyCode to set
	 */
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	/**
	 * @return the currencyCode
	 */
	public String getCurrencyCode() {
		return currencyCode;
	}
	
	
}
