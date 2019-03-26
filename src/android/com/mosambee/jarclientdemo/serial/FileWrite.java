package com.mosambee.jarclientdemo.serial;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileWrite {
	private long  nDataLength = 0;
    private static class SerialPortFileWriteHolder {  
    private static final FileWrite INSTANCE = new FileWrite();  
    }  
    private FileWrite(){}
    public static final FileWrite GetInstance() {  
    return SerialPortFileWriteHolder.INSTANCE;  
    }  
	
	FileOutputStream moutStream = null;
	private String mstrFileName ="";
	
	public void Create(String strFileName) {
		Close();
		
		mstrFileName = strFileName;
		File newFile = new File(strFileName);
		try {
			nDataLength = 0;
			moutStream = new FileOutputStream(newFile);
		} catch (FileNotFoundException e) {
		}
	}
	
	public void Close() {
		if (moutStream != null) {
			try {
				nDataLength = 0;
				moutStream.close();
			} catch (IOException e) {
			}
			moutStream = null;
		}
	}
	
	public boolean IsOpen() {
		return (moutStream != null);
	}
	
	public void Write(byte[] bytes, int nLength) {
		if (!IsOpen()) {
			if (!mstrFileName.isEmpty()) {
				File newFile = new File(mstrFileName);
				if (newFile.exists()) {
					try {
						nDataLength = 0;
						moutStream = new FileOutputStream(newFile,true);
					} catch (FileNotFoundException e) {
					}
				}else {
					Create(mstrFileName);
				}
			}else {
				return;
			}
		}else {
			try {
				moutStream.write(bytes, 0, nLength);
				nDataLength = nDataLength + nLength;
				if (nDataLength > 10485760) {
					Close();
				}
			} catch (IOException e) {
				Close();
			}
		}
	}
	
}
