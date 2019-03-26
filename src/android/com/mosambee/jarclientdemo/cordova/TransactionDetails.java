package com.mosambee.jarclientdemo.cordova;

import org.json.JSONObject;

/**
 * @author JAYESH
 *
 */
public class TransactionDetails {

	private final long transactionId;
	private String authCode;
	private boolean signatureAttached;
	private boolean isVoidable;
	private double amount;
	private double totalAmount;
	private String merchantName;
	
	
	private String customerMobile;
	private String timeStamp;// formatted timestamp
	private String lastFourDigits;// if this transaction is a card transaction
	private String nameOnCard;// if this transaction is a card transaction
	private String paymentMode;// card / cash / cheque
	
	public TransactionDetails(long transactionId) {
		this.transactionId = transactionId;
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
	 * @param customerMobile the customerMobile to set
	 */
	public void setCustomerMobile(String customerMobile) {
		this.customerMobile = customerMobile;
	}

	/**
	 * @return the customerMobile
	 */
	public String getCustomerMobile() {
		return customerMobile;
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
	 * @param isVoidable the isVoidable to set
	 */
	public void setVoidable(boolean isVoidable) {
		this.isVoidable = isVoidable;
	}

	/**
	 * @return the isVoidable
	 */
	public boolean isVoidable() {
		return isVoidable;
	}

	/**
	 * @param paymentMode the paymentMode to set
	 */
	public void setPaymentMode(String paymentMode) {
		this.paymentMode = paymentMode;
	}

	/**
	 * @return the paymentMode
	 */
	public String getPaymentMode() {
		return paymentMode;
	}

	/**
	 * @param lastFourDigits the lastFourDigits to set
	 */
	public void setLastFourDigits(String lastFourDigits) {
		this.lastFourDigits = lastFourDigits;
	}

	/**
	 * @return the lastFourDigits
	 */
	public String getLastFourDigits() {
		return lastFourDigits;
	}

	/**
	 * @param timeStamp the timeStamp to set
	 */
	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}

	/**
	 * @return the timeStamp
	 */
	public String getTimeStamp() {
		return timeStamp;
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
	 * @param authCode the authCode to set
	 */
	public void setAuthCode(String authCode) {
		this.authCode = authCode;
	}

	/**
	 * @return the authCode
	 */
	public String getAuthCode() {
		return authCode;
	}
	
	
	
	public static TransactionDetails getTransactionDetails(JSONObject jsonTxn) {

		try {

			TransactionDetails aTxn = new TransactionDetails(jsonTxn
					.getLong(EConstants.KEY_TRANSACTION_ID));

			aTxn.setAuthCode(jsonTxn.getString(EConstants.KEY_AUTH_CODE));

			if(jsonTxn.has(EConstants.KEY_AMOUNT)) {
				aTxn.setAmount(jsonTxn.getDouble(EConstants.KEY_AMOUNT));
			
			}

			
			if(jsonTxn.has(EConstants.KEY_TOTAL_AMOUNT)) {
				aTxn.setTotalAmount(jsonTxn.getDouble(EConstants.KEY_TOTAL_AMOUNT));
			}
			
			if(jsonTxn.has(EConstants.KEY_PAYMENT_MODE)) {
				aTxn.setPaymentMode(jsonTxn.getString(EConstants.KEY_PAYMENT_MODE));
			}

			
			if(jsonTxn.has(EConstants.KEY_CUSTOMER_MOBILE)) {
				aTxn.setCustomerMobile(jsonTxn.getString(EConstants.KEY_CUSTOMER_MOBILE));
			}
			
			if(jsonTxn.has(EConstants.KEY_TXN_TIME)) {
				aTxn.setTimeStamp(jsonTxn.getString(EConstants.KEY_TXN_TIME));
			}
			
			if (jsonTxn.has(EConstants.KEY_CARD_LAST_4_DIGITS)) {
				aTxn.setLastFourDigits(jsonTxn
						.getString(EConstants.KEY_CARD_LAST_4_DIGITS));
			}
			
			
			if (jsonTxn.has(EConstants.KEY_NAME_ON_CARD)) {
				aTxn.setNameOnCard(jsonTxn
						.getString(EConstants.KEY_NAME_ON_CARD));
			}
			
			if (jsonTxn.has(EConstants.KEY_MERCHANT_NAME)) {
				aTxn.setMerchantName(jsonTxn
						.getString(EConstants.KEY_MERCHANT_NAME));
			}
			
			
			//TODO: jayesh need to remove KEY_SIGNATURE_ATTACHED as we are going to use KEY_IS_SIGNATURE_ATTACHED
			if (jsonTxn.has(EConstants.KEY_SIGNATURE_ATTACHED)) {
				aTxn.setSignatureAttached(jsonTxn
						.getBoolean(EConstants.KEY_SIGNATURE_ATTACHED));
			}
			
			if (jsonTxn.has(EConstants.KEY_IS_SIGNATURE_ATTACHED)) {
				aTxn.setSignatureAttached(jsonTxn
						.getBoolean(EConstants.KEY_IS_SIGNATURE_ATTACHED));
			}
			
			if (jsonTxn.has(EConstants.KEY_IS_VOIDABLE)) {
				aTxn.setVoidable(jsonTxn
						.getBoolean(EConstants.KEY_IS_VOIDABLE));
			}

			return aTxn;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * @param signatureAttached the signatureAttached to set
	 */
	public void setSignatureAttached(boolean signatureAttached) {
		this.signatureAttached = signatureAttached;
	}

	/**
	 * @return the signatureAttached
	 */
	public boolean isSignatureAttached() {
		return signatureAttached;
	}

	/**
	 * @return the transactionId
	 */
	public long getTransactionId() {
		return transactionId;
	}
	
}
