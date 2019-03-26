package com.mosambee.jarclientdemo.serial;

import android.app.Service;
import android.content.Intent;
import android.os.Environment;
import android.os.IBinder;
import android.util.Log;

public  class SerialPortService extends Service {
 
    @Override
    public void onCreate() { 
    	SerialPortIOManage.getInstance().setonSerialPortIOListener(new SerialPortIOManage.onSerialPortIOListener() {
			
			@Override
			public void OnIOCallBack(byte[] data, int length) {
				
			}
			
			@Override
			public void OnConnectStatusCallBack(boolean statue) {
				if (statue) {
		    		String fileString = Environment.getExternalStorageDirectory().getPath() +
						"/serial.txt";
		    		FileWrite.GetInstance().Create(fileString);
				}
			}
		});
    }

    @Override
    public void onDestroy() {
    	SerialPortIOManage.getInstance().disConnect();
        super.onDestroy();
    }

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		
		String serial=intent.getStringExtra("serial");
		Log.i("xxx", "onStartCommand  ....>"+serial);
		if("dev/ttyMT0".equals(serial)){
			Log.i("xxx", "1111111");
			SerialPortIOManage.getInstance().Connect(serial, 115200);
		}else {
			Log.i("xxx", "222222222222");
			SerialPortIOManage.getInstance().Connect(serial, 9600);
		}
		
		Log.i("xxx", "onStartCommand eeeeeeeeeeee.");
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
}
