/**
 * 
 */
package com.mosambee.jarclientdemo.cordova;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;

/**
 * @author JAYESH
 * 
 */
public class EUtils {

	// for dev server
//	public static final String BASE_PACKAGE = "com.ezetap.service.dev";
//	public static final String APK_URL 	   = "http://d.eze.cc/portal/app/android/serviceApp";
	
	//For DEMO Server
	public static final String BASE_PACKAGE = "com.ezetap.service.demo";
	public static final String APK_URL 	   = "http://d.eze.cc/demo/app/android/serviceApp";
	
	public static final String EZETAP_PACKAGE_ACTION = ".EZESERV";
	private static final String DEBUG_TAG = "EzeUtils";

	/**
	 * To initiate a card payment transaction, application should call this method 
	 * with following parameters:
	 * 
	 * @param amount - Transaction amount
	 * @param tip - Tip for this transaction (can be zero)
	 * @param mobile - Mobile number of customer (optional)
	 * @param context - Calling activity's context 
	 * @param appKey - Ezetap Licence key for your organization
	 * @param username - Ezetap username
	 * @param reqCode - Request code for handling response 
	 */
	public void startCardPayment(double amount, double tip, String mobile,
                                 Activity context,
                                 String appKey,
                                 String username,
                                 String orderNumber,
                                 int reqCode, Hashtable<String, Object> appData, boolean enableCustomLogin) {
		Log.v(DEBUG_TAG, "amount=" + amount + " tip=" + tip + " mobile="
				+ mobile);
		enableCustomLogin =true;
		try {
			Intent intent = new Intent();

			intent.setAction(BASE_PACKAGE + EZETAP_PACKAGE_ACTION);
			intent.addCategory(Intent.CATEGORY_DEFAULT);

			String targetAppPackage = findTargetAppPackage(intent, context);
			if (targetAppPackage == null) {
				Log.v(DEBUG_TAG, "Ezetap app not found.");
				showDownloadDialog(context);
				return;
			}
			intent.setPackage(targetAppPackage);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
			
			JSONObject reqData = new JSONObject();
			
			reqData.put(EConstants.KEY_ACTION, EConstants.ACTION_PAYCARD);

			reqData.put(EConstants.KEY_ENABLE_CUSTOM_LOGIN,enableCustomLogin);
			reqData.put(EConstants.KEY_AMOUNT, new Double(amount));
			if (tip > 0.0) {
				reqData.put(EConstants.KEY_TIP_AMOUNT, new Double(tip));
			}
			reqData.put(EConstants.KEY_USERNAME, username);
//			reqData.put(EConstants.KEY_APPKEY, appKey);
			reqData.put(EConstants.KEY_ORDERID, orderNumber);
			reqData.put(EConstants.KEY_CUSTOMER_MOBILE, mobile);
			
			if(appData != null) {
				Enumeration<String> keys = appData.keys();
				while (keys.hasMoreElements()) {
					String aKey = (String) keys.nextElement();
					Object aVal = appData.get(aKey);
					if(aVal instanceof String[]) {
						JSONArray jsonArr = new JSONArray();
						String[] arr = (String[])aVal;

						for (int index = 0; index < arr.length; index++) {
							jsonArr.put(arr[index]);
						}
						
						reqData.put(aKey, jsonArr);
					} else if(aVal instanceof String) {
						reqData.put(aKey, aVal);
					}
				}
				
			}
			Log.v(DEBUG_TAG, "MAP>>"+appData);
			Log.v(DEBUG_TAG, ">>"+reqData.toString(0));
			intent.putExtra(EConstants.KEY_JSON_REQ_DATA, reqData.toString());
			context.startActivityForResult(intent, reqCode);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	
	/**
	 * To initiate a cash payment transaction, application should call this method 
	 * with following parameters:
	 * 
	 * @param amount - Transaction amount
	 * @param tip - Tip for this transaction (can be zero)
	 * @param mobile - Mobile number of customer (optional)
	 * @param context - Calling activity's context 
	 * @param appKey - Ezetap Licence key for your organization
	 * @param username - Ezetap username
	 * @param reqCode - Request code for handling response 
	 */
	/*public void startCashPayment(double amount, double tip, String mobile,
			Activity context, String appKey, 
			String username, 
			String orderNumber,
			String customerName,
			int reqCode, 
			boolean enableCustomLogin) {
		Log.v(DEBUG_TAG, "amount=" + amount + " tip=" + tip + " mobile="
				+ mobile);

		try {
			Intent intent = new Intent();

			intent.setAction(BASE_PACKAGE + EZETAP_PACKAGE_ACTION);
			intent.addCategory(Intent.CATEGORY_DEFAULT);

			String targetAppPackage = findTargetAppPackage(intent, context);
			if (targetAppPackage == null) {
				Log.v(DEBUG_TAG, "Ezetap app not found.");
				showDownloadDialog(context);
				return;
			}
			intent.setPackage(targetAppPackage);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
			
			JSONObject reqData = new JSONObject();
			
			reqData.put(EConstants.KEY_ACTION,
					EConstants.ACTION_PAY_CASH);

			reqData.put(EConstants.KEY_ENABLE_CUSTOM_LOGIN,enableCustomLogin);
			reqData.put(EConstants.KEY_AMOUNT, new Double(amount));
			if (tip > 0.0) {
				reqData.put(EConstants.KEY_TIP_AMOUNT, new Double(tip));
			}
			reqData.put(EConstants.KEY_USERNAME, username);
//			reqData.put(EConstants.KEY_APPKEY, appKey);
			reqData.put(EConstants.KEY_ORDERID, orderNumber);
			reqData.put(EConstants.KEY_CUSTOMER_NAME, customerName);
			reqData.put(EConstants.KEY_CUSTOMER_MOBILE, mobile);
			
			intent.putExtra(EConstants.KEY_JSON_REQ_DATA, reqData.toString());
			
			context.startActivityForResult(intent, reqCode);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	*/
	/**
	 * To initiate a void transaction request, application should call this method 
	 * with following parameters:
	 * 
	 * @param txnID - Transaction ID to void
	 * @param context - Calling activity's context 
	 * @param appKey - Ezetap Licence key for your organization
	 * @param username - Ezetap username
	 * @param reqCode - Request code for handling response 
	 */
	public void startVoidTransaction(Long txnID, Activity context,
                                     String appKey, String username, int reqCode, boolean enableCustomLogin) {
		Log.v(DEBUG_TAG, "txnID=" + txnID);

		try {
			Intent intent = new Intent();
			intent.setAction(BASE_PACKAGE + EZETAP_PACKAGE_ACTION);
			intent.addCategory(Intent.CATEGORY_DEFAULT);
			String targetAppPackage = findTargetAppPackage(intent, context);
			if (targetAppPackage == null) {
				Log.v(DEBUG_TAG, "Ezetap app not found.");
				showDownloadDialog(context);
				return;
			}
			intent.setPackage(targetAppPackage);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
			
			JSONObject reqData = new JSONObject();
			
			reqData.put(EConstants.KEY_ACTION, EConstants.ACTION_VOID);

			reqData.put(EConstants.KEY_TRANSACTION_ID, txnID);
			reqData.put(EConstants.KEY_USERNAME, username);
//			reqData.put(EConstants.KEY_APPKEY, appKey);
			reqData.put(EConstants.KEY_ENABLE_CUSTOM_LOGIN, enableCustomLogin);
			
			intent.putExtra(EConstants.KEY_JSON_REQ_DATA, reqData.toString());
			context.startActivityForResult(intent, reqCode);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	
	
	/**
	 * To logout current sesssion associated with AppKey , application should call this method 
	 * with following parameters:
	 * 
	 * @param context - Calling activity's context 
	 * @param appKey - Ezetap Licence key for your organization
	 * @param reqCode - Request code for handling response 
	 */
	public void logout(Activity context,
                       String appKey, String username , int reqCode) {

		try {
			Intent intent = new Intent();

			intent.setAction(BASE_PACKAGE + EZETAP_PACKAGE_ACTION);
			intent.addCategory(Intent.CATEGORY_DEFAULT);

			String targetAppPackage = findTargetAppPackage(intent, context);
			if (targetAppPackage == null) {
				Log.v(DEBUG_TAG, "Ezetap app not found.");
				showDownloadDialog(context);
				return;
			}
			intent.setPackage(targetAppPackage);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
			
			
			JSONObject reqData = new JSONObject();
			
			reqData.put(EConstants.KEY_ACTION, EConstants.ACTION_LOGOUT);
			reqData.put(EConstants.KEY_USERNAME, username);
			
//			reqData.put(EConstants.KEY_APPKEY, appKey);

			intent.putExtra(EConstants.KEY_JSON_REQ_DATA, reqData.toString());
			context.startActivityForResult(intent, reqCode);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Attach signature to existing transaction, application should call this method 
	 * with following parameters:
	 * 
	 * @param txnID - Transaction ID to attach signature
	 * @param context - Calling activity's context 
	 * @param appKey - Ezetap Licence key for your organization
	 * @param username - Ezetap username
	 * @param reqCode - Request code for handling response 
	 */
	public void attachSignature(Long txnID, Activity context,
                                String appKey, String username,
                                int reqCode, boolean enableCustomLogin) {
		Log.v(DEBUG_TAG, "Attach Signature txnID=" + txnID);

		try {
			Intent intent = new Intent();

			intent.setAction(BASE_PACKAGE + EZETAP_PACKAGE_ACTION);
			intent.addCategory(Intent.CATEGORY_DEFAULT);

			String targetAppPackage = findTargetAppPackage(intent, context);
			if (targetAppPackage == null) {
				Log.v(DEBUG_TAG, "Ezetap app not found.");
				showDownloadDialog(context);
				return;
			}
			intent.setPackage(targetAppPackage);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
			
			
			JSONObject reqData = new JSONObject();
			reqData.put(EConstants.KEY_ACTION, EConstants.ACTION_ATTACH_SIGNATURE);

			reqData.put(EConstants.KEY_TRANSACTION_ID, txnID);
			reqData.put(EConstants.KEY_USERNAME, username);
//			reqData.put(EConstants.KEY_APPKEY, appKey);
			reqData.put(EConstants.KEY_ENABLE_CUSTOM_LOGIN, enableCustomLogin);
			
			intent.putExtra(EConstants.KEY_JSON_REQ_DATA, reqData.toString());
			
			context.startActivityForResult(intent, reqCode);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	private String findTargetAppPackage(Intent intent, Activity context) {
		PackageManager pm = context.getPackageManager();
		List<ResolveInfo> availableApps = pm.queryIntentActivities(intent,
				PackageManager.MATCH_DEFAULT_ONLY);
		if (availableApps != null) {
			for (ResolveInfo availableApp : availableApps) {
				String packageName = availableApp.activityInfo.packageName;
				if (BASE_PACKAGE.equals(packageName)) {
					return packageName;
				}
			}
		}
		return null;
	}

	private AlertDialog showDownloadDialog(final Activity context) {
		AlertDialog.Builder downloadDialog = new AlertDialog.Builder(context);
		downloadDialog.setTitle("Install Ezetap");
		downloadDialog
				.setMessage("This application requires Ezetap. Would you like to install it?");
		downloadDialog.setPositiveButton("Yes",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialogInterface, int i) {
						//TODO: Jayesh : need to define multiple links
//						Uri uri = Uri
//								.parse(APK_URL);
//						Intent intent = new Intent(Intent.ACTION_VIEW, uri);
						try {
//							context.startActivity(intent);
							DownloadUtils utils = new DownloadUtils(APK_URL, context);
							utils.start();
							
						} catch (ActivityNotFoundException anfe) {
							Log.v(DEBUG_TAG,
									"Could not install Ezetap application.");
						}
					}
				});
		downloadDialog.setNegativeButton("No",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialogInterface, int i) {
						Toast toast = Toast.makeText(context,
								"Operation aborted.", Toast.LENGTH_LONG);
						toast.show();
					}
				});
		return downloadDialog.show();
	}

	/**
	 * @param username
	 * @param password
	 * @param Activity
	 * @param ezetapLicenceKey
	 * @param reqCode
	 */
	public void startLoginRequest(String username, String password,
                                  Activity context, String ezetapLicenceKey,
                                  int reqCode) {

		try {
			Intent intent = new Intent();

			intent.setAction(BASE_PACKAGE + EZETAP_PACKAGE_ACTION);
			intent.addCategory(Intent.CATEGORY_DEFAULT);

			String targetAppPackage = findTargetAppPackage(intent, context);
			if (targetAppPackage == null) {
				Log.v(DEBUG_TAG, "Ezetap app not found.");
				showDownloadDialog(context);
				return;
			}
			intent.setPackage(targetAppPackage);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
			JSONObject reqData = new JSONObject();
			reqData.put(EConstants.KEY_ACTION, EConstants.ACTION_LOGIN);
			reqData.put(EConstants.KEY_PASSWORD, password);
			reqData.put(EConstants.KEY_USERNAME, username);
			intent.putExtra(EConstants.KEY_JSON_REQ_DATA, reqData.toString());
			
			//intent.putExtra("DBG_HOST", "10.0.1.118:8080");
			
			context.startActivityForResult(intent, reqCode);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	
	
	
	/**
	 * @param username
	 * @param password
	 * @param Activity
	 * @param ezetapLicenceKey
	 * @param reqCode
	 */
	public void startChangePasswordRequest(String username,
                                           String oldPassword,
                                           String newPassword,
                                           Activity context, String ezetapLicenceKey,
                                           int reqCode) {

		try {
			Intent intent = new Intent();

			intent.setAction(BASE_PACKAGE + EZETAP_PACKAGE_ACTION);
			intent.addCategory(Intent.CATEGORY_DEFAULT);

			String targetAppPackage = findTargetAppPackage(intent, context);
			if (targetAppPackage == null) {
				Log.v(DEBUG_TAG, "Ezetap app not found.");
				showDownloadDialog(context);
				return;
			}
			intent.setPackage(targetAppPackage);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
			
			intent.putExtra(EConstants.KEY_ACTION, EConstants.ACTION_CHANGE_PWD);

			intent.putExtra(EConstants.KEY_PASSWORD, oldPassword);
			intent.putExtra(EConstants.KEY_NEW_PASSWORD, newPassword);
			
			intent.putExtra(EConstants.KEY_USERNAME, username);
//			intent.putExtra(EConstants.KEY_APPKEY, ezetapLicenceKey);

			context.startActivityForResult(intent, reqCode);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	
	/**
	 * registerDongle : To register or change the ownership of Ezetap Device
	 * @param username
	 * @param Activity
	 * @param ezetapLicenceKey
	 * @param reqCode
	 * @param enableCustomLogin
	 */
	public void registerDongle(String username,
			Activity context,
			String ezetapLicenceKey,
			int reqCode, 
			boolean enableCustomLogin) {

		try {
			Intent intent = new Intent();

			intent.setAction(BASE_PACKAGE + EZETAP_PACKAGE_ACTION);
			intent.addCategory(Intent.CATEGORY_DEFAULT);

			String targetAppPackage = findTargetAppPackage(intent, context);
			if (targetAppPackage == null) {
				Log.v(DEBUG_TAG, "Ezetap app not found.");
				showDownloadDialog(context);
				return;
			}
			intent.setPackage(targetAppPackage);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
			
			// put request parameters
			intent.putExtra(EConstants.KEY_ACTION, EConstants.ACTION_REGISTER_DONGLE);
			intent.putExtra(EConstants.KEY_USERNAME, username);
//			intent.putExtra(EConstants.KEY_APPKEY, ezetapLicenceKey);
			intent.putExtra(EConstants.KEY_ENABLE_CUSTOM_LOGIN,enableCustomLogin);
			
			context.startActivityForResult(intent, reqCode);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * @param username
	 * @param Activity
	 * @param ezetapLicenceKey
	 * @param reqCode
	 * @param enableCustomLogin
	 */
	public void fetchTransactionHistory(String username,
                                        Activity context, String ezetapLicenceKey,
                                        int reqCode, boolean enableCustomLogin) {

		try {
			Intent intent = new Intent();

			intent.setAction(BASE_PACKAGE + EZETAP_PACKAGE_ACTION);
			intent.addCategory(Intent.CATEGORY_DEFAULT);

			String targetAppPackage = findTargetAppPackage(intent, context);
			if (targetAppPackage == null) {
				Log.v(DEBUG_TAG, "Ezetap app not found.");
				showDownloadDialog(context);
				return;
			}
			intent.setPackage(targetAppPackage);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
			
			// put request parameters
			intent.putExtra(EConstants.KEY_ACTION, EConstants.ACTION_TXN_HISTORY);
			intent.putExtra(EConstants.KEY_USERNAME, username);
//			intent.putExtra(EConstants.KEY_APPKEY, ezetapLicenceKey);
			intent.putExtra(EConstants.KEY_ENABLE_CUSTOM_LOGIN,enableCustomLogin);
			
			context.startActivityForResult(intent, reqCode);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
}
