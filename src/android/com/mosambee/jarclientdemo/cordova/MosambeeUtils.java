package com.mosambee.jarclientdemo.cordova;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.List;

public class MosambeeUtils{
	private String DEBUG_TAG="Mosambee Api";
	public static final String APK_URL = "http://14.140.169.74/mosambee_api.apk";
	private String BASE_PACKAGE="com.mosambee.activity.api";
	private String API_PACKAGE="com.mosambee.api";
	private String JSON_REQUEST_PARAM="jsonRequestParam";
	public static String APP_KEY="skdjJgGVvcCjh4b5J2551EW7i8l6I9k6Z2SD0f1ds5z7sDZ6Xq7a5315266pj5h";
	private  String[] REQUEST_PARAM = { "requestType","userId","enableLogin","enableTip","amount",  "invoiceNumber","requestCode","customerMobile","customerEmail","merchantRef1","mechantRef2","password","invoiceDate","communicationMode","currentActivity"};


		public void startPayment(Activity currentActivity, String requestType, int amount,
                                 boolean enableTip, String customerMobile,
                                 Context activity,
                                 String appKey,
                                 String userId,
                                 String invoiceNumber,
                                 int requestCode, boolean enableLogin, String customerEmail, String merchantRef1, String merchantRef2, String password, String invoiceDate, String communicationMode) {
			Log.v(DEBUG_TAG, "Amount"+amount);
			try {
				System.out.println("mount"+amount);
				Intent intent = new Intent();
				intent.setAction(API_PACKAGE);
				intent.addCategory(Intent.CATEGORY_DEFAULT);
				//intent.setType("text/plain");
				String targetAppPackage = findApplicationPackage(intent, activity);
				if (targetAppPackage == null) {
					Log.v(DEBUG_TAG, "Mosambee app not found."+targetAppPackage);
					showDialog(activity);
					return;
				}
				intent.setPackage(targetAppPackage);
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
				JSONObject requestParam = new JSONObject();
				requestParam.put(REQUEST_PARAM[0], requestType);
				requestParam.put(REQUEST_PARAM[1], userId);
				requestParam.put(REQUEST_PARAM[2],enableLogin);
				requestParam.put(REQUEST_PARAM[3], enableTip);
				requestParam.put(REQUEST_PARAM[4], amount);
				requestParam.put(REQUEST_PARAM[5], invoiceNumber);
				requestParam.put(REQUEST_PARAM[6], requestCode);
				requestParam.put(REQUEST_PARAM[7], customerMobile);
				requestParam.put(REQUEST_PARAM[8], customerEmail);
				requestParam.put(REQUEST_PARAM[9], merchantRef1);
				requestParam.put(REQUEST_PARAM[10], merchantRef2);
				requestParam.put(REQUEST_PARAM[11], password);
				requestParam.put(REQUEST_PARAM[12], invoiceDate);
				requestParam.put(REQUEST_PARAM[13],communicationMode);
				//requestParam.put(REQUEST_PARAM[14],currentActivity);
				
				Log.v(DEBUG_TAG, ">>"+requestParam.toString(0));
				intent.putExtra(JSON_REQUEST_PARAM, requestParam.toString());

				currentActivity.startActivityForResult(intent, requestCode);

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		private String findApplicationPackage(Intent intent, Context activity) {
			PackageManager packageManager = activity.getPackageManager();
			List<ResolveInfo> availableApps = packageManager.queryIntentActivities(intent,
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
		private AlertDialog showDialog(final Context context) {
			AlertDialog.Builder downloadDialog = new AlertDialog.Builder(context);
			downloadDialog.setTitle("Mosambee");
			downloadDialog
					.setMessage("This application requires Mosambee Application. Please contact Mosambee");
			downloadDialog.setPositiveButton("Yes",
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialogInterface, int i) {
							try {
								DownloadUtils utils = new DownloadUtils(APK_URL, context);
								utils.start();
							} catch (ActivityNotFoundException anfe) {
								Log.v(DEBUG_TAG,
										"Mosambee Application download failed...");
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
}
