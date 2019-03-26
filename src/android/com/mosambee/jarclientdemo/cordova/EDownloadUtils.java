package com.mosambee.jarclientdemo.cordova;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

public class EDownloadUtils {

	
	private static final int DOWNLOAD_START 	= 1;
	private static final int DOWNLOAD_CANCEL 	= 2;
	private static final int DOWNLOAD_UPDATE 	= 3;
	private static final int DOWNLOAD_COMPLETED	= 4;
	ProgressDialog dialog;
	boolean bCancelled = false;
	
	String downloadedFilePath = null;
	final String url;
	 Activity currentActivity;
	public EDownloadUtils(String url, Activity context) {
		this.url = url;
		this.currentActivity = context;
	}
	public void start() {
		Message msg = eventHandler.obtainMessage();
		msg.what = DOWNLOAD_START;
		eventHandler.sendMessage(msg);
	}
	
	private void startDownload() {
		Thread aTh = new Thread() {
			public void run() {
				String dirPath 	= Environment.getExternalStorageDirectory()
						+ "/mosambee-download/";
	            InputStream is = null;
	            OutputStream os = null;
	            try {
	                URL fileUrl;
	                byte[] buf;
	                int ByteRead = 0;
	                int ByteWritten = 0;
	                fileUrl = new URL(url);
	                URLConnection URLConn = fileUrl.openConnection();
	                URLConn.setUseCaches(false);
	                int totalFileSize = URLConn.getContentLength();
	                is = URLConn.getInputStream();
	                String fileName = "BJP.apk";//url.substring(url.lastIndexOf("/") + 1);
	                File f = new File(dirPath);
	                f.mkdirs();
	                String abs = dirPath + fileName;
	                int tmpIdx = 0;
	                f = new File(abs);
	                while(f.exists()) {
	                	tmpIdx++;
	                	abs = dirPath + "BJP-"+tmpIdx +".apk";
	                	 f = new File(abs);
	                }
	                downloadedFilePath = abs;
	                Log.v("DEBUG", "Downloaded fle path ="+downloadedFilePath);
	                os = new BufferedOutputStream(new FileOutputStream(abs));
	                buf = new byte[1024];
	                /*
	                 * This loop reads the bytes and updates a progressdialog
	                 */
	                while ((ByteRead = is.read(buf)) != -1 && !bCancelled) {

	                    os.write(buf, 0, ByteRead);
	                    ByteWritten += ByteRead;

	                    final int tmpWritten = ByteWritten;

	                    Message msg = eventHandler.obtainMessage();
	            		msg.what = DOWNLOAD_UPDATE;
	            		if(totalFileSize < tmpWritten) {
	            			totalFileSize = tmpWritten;
	            		}
	            		msg.obj = "Downloading Ezetap application.\n Please wait..."+"("+tmpWritten+"/"+totalFileSize+")";
	            		eventHandler.sendMessage(msg);
	                }
	                is.close();
	                os.flush();
	                os.close();
	                Thread.sleep(200);
	                if(!bCancelled) {
		                Message msg = eventHandler.obtainMessage();
	            		msg.what = DOWNLOAD_COMPLETED;
	            		msg.obj = "Downloading completed";
	            		eventHandler.sendMessage(msg);
	                }
	                

	            } catch (Exception e) {
	            	//add toast
	            	dialog.dismiss();
	                e.printStackTrace();

	            }
			}
		};
		
		aTh.start();
	}
	
	
	public Handler eventHandler = new Handler() {
		@SuppressWarnings("deprecation")
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case DOWNLOAD_START:
			{
				try {
					Thread.sleep(1000);
				} catch (Exception e) {
				}
				dialog = new ProgressDialog(currentActivity);
			    dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			    dialog.setTitle("Downloading");
			    dialog.setMessage("Downloading Ezetap application.\n Please wait...");
			    
			    dialog.setButton("Cancel",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialogInterface, int i) {
							
								try {
									bCancelled = true;
									dialog.dismiss();
								} catch (Exception e) {
								}
							}
						});
			    
			    dialog.show();
			    startDownload();			    
			}break;

			case DOWNLOAD_UPDATE:
				{
					 dialog.setMessage(""+msg.obj);
				}
				break;
			
			case DOWNLOAD_COMPLETED:
				{
					dialog.dismiss();
				
					try {
					
						String fileName = downloadedFilePath;
						Uri aUri ;//= Uri.parse(fileName);
						aUri = Uri.fromFile(new File(fileName));
						Intent intent = new Intent(Intent.ACTION_VIEW);
						intent.setDataAndType(aUri,
								"application/vnd.android.package-archive");
						currentActivity.startActivity(intent);
						currentActivity = null;
					} catch (Exception e) {
					}
				}	
				break;
				
			default:
				break;
			}
		}
	};
	
	
	
	
	
	
	
}
