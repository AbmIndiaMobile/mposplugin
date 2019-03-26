/********************************************************
 * Copyright (C) 2012 Ezetap Mobile Solutions Pvt. Ltd.
 * 
 * This software is distributed for illustration purpose on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND.
 *
 *******************************************************/
package com.mosambee.jarclientdemo.cordova;


public class EConstants {

	
	
	
	/**
	 * Keys for application request.
	 */
	public static final String KEY_ACTION 					= "action";
	public static final String KEY_JSON_REQ_DATA 			= "jsonReqData";
	public static final String KEY_USERNAME 				= "username";
	public static final String KEY_TIP_ENABLED 				= "tipEnabled";
	
	public static final String KEY_PASSWORD 				= "password";
	public static final String KEY_NEW_PASSWORD				= "newPassword";
	
	public static final String KEY_AUTH_TOKEN 				= "authToken";
	public static final String KEY_AUTH_CODE				= "authCode";
	public static final String KEY_AMOUNT 					= "amount";
	public static final String KEY_TIP_AMOUNT				= "additionalAmount";
	public static final String KEY_TOTAL_AMOUNT				= "totalAmount";
	public static final String KEY_TRANSACTION_LIST 			= "txns";
	public static final String KEY_CARD_LAST_4_DIGITS = "cardLastFourDigit";
	
	public static final String KEY_ORDERID 					= "orderNumber";
	public static final String KEY_CUSTOMER_MOBILE 			= "customerMobileNumber";
	public static final String KEY_CUSTOMER_NAME			= "customerName";
	public static final String KEY_CAPTURE_SIGNATURE		= "captureSignature";
	public static final String KEY_TRANSACTION_ID			= "transactionId";
	public static final String KEY_ERROR_CODE					= "errorCode";
	public static final String KEY_ERROR_MESSAGE				= "errorMessage";
	public static final String KEY_ENABLE_CUSTOM_LOGIN			= "enableCustomLogin";
	public static final String KEY_PAYMENT_MODE 				= "paymentType";
	public static final String KEY_TXN_TIME 					= "transactionTime";

	/**
	 * Keys through which application will receive response from Ezetap Application.
	 */
	
	
	/**
	 *  JSON formated transaction response for Ezetap application.
	 */
	public static final String KEY_RESPONSE_DATA 				= "responseData";
	
	/**
	 * Key contains transaction status.
	 */
	public static final String KEY_IS_SUCCESS 					= "isSuccess";
	
	
	public static final String KEY_NAME_ON_CARD				= "nameOnCard";

	/**
	 * Key contains signature status.
	 */
	public static final String KEY_IS_SIGNATURE_ATTACHED 		= "isSignatueAttached";

	
	
	public static final String KEY_MERCHANT_NAME			= "merchantName";


	/**
	 * Action code to logout the session from Ezetap Application.
	 */
	public static final String ACTION_LOGOUT				= "logout";
	
	/**
	 * Action code to start a card transaction.
	 */
	public static final String ACTION_PAYCARD				= "paycard";
	
	public static final String ACTION_PAY_CASH				= "paycash";
	
	/**
	 * Action code to register Ezetap Device.
	 */
	public static final String ACTION_REGISTER_DONGLE		= "registerDongle";
	
	/**
	 * Action code to initiate a void transaction.
	 */
	public static final String ACTION_VOID					= "void";
	
	
	/**
	 * Action code to request for login.
	 */
	public static final String ACTION_LOGIN					= "login";
	
	
	/**
	 * Action code to change user password.
	 */
	public static final String ACTION_CHANGE_PWD			= "change-password";
	
	/**
	 * Action code to attach the signature with an existing transaction.
	 */
	public static final String ACTION_ATTACH_SIGNATURE		= "attachSignature";


	/**
	 * Action code to get the transaction history.
	 */
	public static final String ACTION_TXN_HISTORY			= "txnhistory";

	
	
	// result from Ezetap service application 
	public static final int RESULT_SUCCESS				= 2001;
	public static final int RESULT_FAILED				= 3001;
	
	
	
	/**
	 * Ezetap result codes
	 */
//	public static final String ERROR_CODE_SESSION_TIMEDOUT 	= "SESSION_TIMEDOUT";

	public static final String ERROR_CODE_SESSION_TIMED_OUT 	= "SESSION_TIMED_OUT";
	public static final String ERROR_CODE_ACCESS_DENIED			 = "ACCESS_DENIED";

	public static final String KEY_SIGNATURE_ATTACHED = "signatureAttached";

	public static final String KEY_IS_VOIDABLE = "voidable";
	
	

	
}
