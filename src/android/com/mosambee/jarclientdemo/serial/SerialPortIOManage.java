package com.mosambee.jarclientdemo.serial;

import android.os.Handler;
import android.util.Log;


import com.mosambee.jarclientdemo.CustomPlugin;
import com.mosambee.jarclientdemo.android_serialport_api.SerialPortDevice;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;



public class SerialPortIOManage {
	SerialPortDevice temPortDevice = null;
	private InputStream mInputStream = null;
	private OutputStream mOutputStream = null;

	private String mstrComPath = "";
	private int mnBaudrate = 57600;

	ReadThread mReadThread = null;

	onSerialPortIOListener mListener = null;

	private static class SingletonHolder {
		private static final SerialPortIOManage INSTANCE = new SerialPortIOManage();
	}

	private SerialPortIOManage() {
	}

	public static final SerialPortIOManage getInstance() {
		return SingletonHolder.INSTANCE;
	}

	public void setonSerialPortIOListener(onSerialPortIOListener listener) {
		mListener = listener;
	}

	public interface onSerialPortIOListener {
		abstract void OnConnectStatusCallBack(boolean statue);

		abstract void OnIOCallBack(byte[] data, int length);
	}

	public void Connect(String path, int baudrate) {
		mstrComPath = path;
		mnBaudrate = baudrate;

		temPortDevice = new SerialPortDevice(path, baudrate, 0);
		if (temPortDevice.connect()) {
			mInputStream = temPortDevice.getInputStream();
			mOutputStream = temPortDevice.getOutputStream();
			mReadThread = new ReadThread();
			mReadThread.start();

			if (null != mListener)
				mListener.OnConnectStatusCallBack(true);
		} else {
			if (null != mListener)
				mListener.OnConnectStatusCallBack(false);
		}
	}

	public void disConnect() {
		if (mReadThread != null) {
			mReadThread.interrupt();
			mReadThread = null;
		}

		try {
			if (mInputStream != null) {
				mInputStream.close();
				mInputStream = null;
			}
			if (mOutputStream != null) {
				mOutputStream.close();
				mOutputStream = null;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (temPortDevice != null) {
			temPortDevice.close();
		}

	}

	public String test(String a) {
		String[] strs = a.split("\\s+");
		// Log.i("xxx", "strs.lll==>" + strs.length);
		StringBuffer buffer = new StringBuffer();
		for (String string : strs) {
			// Log.i("xxx", "mm==>" + string);
			buffer.append(string);

		}

		return String.valueOf(buffer);
	}

	public void sendDataToDevice(String data) {
		try {
			currCom = data;
			data = test(data);
			if (mOutputStream != null && data != null) {
				Log.i("xxx", "ssdata==>" + data);
				mOutputStream.write(conver16HexToByte(data));
				// mOutputStream.write('\n');
				mOutputStream.flush();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static byte[] conver16HexToByte(String hex16Str) {
		char[] c = hex16Str.toCharArray();
		byte[] b = new byte[c.length / 2];
		for (int i = 0; i < b.length; i++) {
			int pos = i * 2;
			b[i] = (byte) ("0123456789ABCDEF".indexOf(c[pos]) << 4 | "0123456789ABCDEF"
					.indexOf(c[pos + 1]));
		}
		return b;
	}

	public static final String byte2hex(byte b[], int lenght) {
		if (b == null) {
			throw new IllegalArgumentException(
					"Argument b ( byte array ) is null! ");
		}
		String hs = "";
		String stmp = "";
		Log.i("xxx", "lenght==>" + b.length);
		for (int n = 0; n < lenght; n++) {
			stmp = Integer.toHexString(b[n] & 0xff);
			if (stmp.length() == 1) {
				hs = hs + "0" + stmp;
			} else {
				hs = hs + stmp;
			}
		}
		return hs.toUpperCase();
	}

	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			super.handleMessage(msg);
			switch (msg.what){
				case 3000:
					SerialPortIOManage.getInstance().resetBuffer();
					CustomPlugin.handler.removeMessages(888);
					CustomPlugin.handler.removeMessages(999);
					CustomPlugin.handler.removeMessages(1000);
					CustomPlugin.handler.removeMessages(1001);
					CustomPlugin.handler.sendEmptyMessageDelayed(888, 1000);
					break;
			}
		};
	};
	int byteRead = -1;

	public void resetBuffer() {
	}

	public String fingerCom = "02 61 00 05 01 00 BF 41 DD 1B 03";
	public String scanningCom = "04 E4 04 00 FF 14";
	public String postCom = "4D 00 06 21 10 00 0A 00 00 70";
	public String currCom = null;
	 byte[] temp;

	class ReadThread extends Thread {
		public void run() {
			int nMaxBufLength = 1024;
			byte[] buffer = new byte[nMaxBufLength];

			while (true) {
				try {
					Log.i("xxx", "bbbbbbbbbbbbbbbbb==>");
					Thread.sleep(200);
					if (mInputStream != null) {
						byteRead = mInputStream.read(buffer);

						 temp = new byte[byteRead];
						for (int i = 0; i < byteRead; i++) {
							temp[i] = buffer[i];
							Log.i("xxx", "temp[i]==>" + temp[i]);
						}
						Log.i("xxx", "byteRead==>" + byteRead);
						Log.i("xxx", "currCom==>" + currCom);
						if (byteRead > 0
								&& (fingerCom.equals(currCom)
										|| scanningCom.equals(currCom) || postCom
											.equals(currCom))) {
							handler.removeMessages(3000);
							handler.sendEmptyMessage(3000);
							handler.post(new Runnable() {

								@Override
								public void run() {
									if ("scanning".equals(CustomPlugin.tvScannarData.getTag())) {
										Log.i("xxx", "buffer==>" + new String(temp));
										CustomPlugin.tvScannarData.setText(new String(temp));
									} else {
										String old = String.valueOf(CustomPlugin.tvScannarData.getText()) + "\n";
										String str = byte2hex(temp, temp.length);
										CustomPlugin.tvScannarData.setText(old + str);
									}
								}
							});
							if (mListener != null) {
								mListener.OnIOCallBack(buffer, buffer.length);
								FileWrite.GetInstance().Write(buffer, buffer.length);
							}

						} else {

						}
					} else {
						break;
					}
				} catch (Exception e) {
					e.printStackTrace();
					continue;
				}
			}
		}
	}

}
