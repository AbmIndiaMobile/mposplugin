package com.mosambee.jarclientdemo;


import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;
import android.widget.Toast;

import com.mosambee.jarclientdemo.serial.SerialPortIOManage;
import com.mosambee.jarclientdemo.serial.SerialPortService;
import com.mosambee.lib.TRACE;
import com.printer.sdk.PrinterConstants;
import com.printer.sdk.PrinterInstance;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaWebView;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.abmindia.department.R;

public class CustomPlugin extends CordovaPlugin {
    private Intent intent;


    public PrinterInstance myPrinter;
    private boolean isConnected;
    public static TextView tvScannarData;
    private String devicesName;
    private String devicesAddress;
    private int baudrate;
    private MyBroadcastReceiver myReceiver;
    String responseTextY;
    String propertyNo,receiptNo,receiptDate,ownerName,mobileNo,totalamt,paidamt,outstandAmt,advanceAmt,time,orgName,payMode,
      chequeNo,chequeDate,bankName,cardNumber,name,wardNo,mobileTax,propertyAddress,uuid,copy,zone,ward;
  String parsedStr;
    public void initialize(CordovaInterface cordova, CordovaWebView webView) {
        super.initialize(cordova, webView);
        myReceiver = new MyBroadcastReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("MY_ACTION");
        cordova.getActivity().registerReceiver(myReceiver, intentFilter);
    }


    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
      //Toast.makeText(cordova.getActivity(),args.getString(0),Toast.LENGTH_LONG).show();
        if (action.equals("closePort")){
            try {

                Toast.makeText(cordova.getActivity(),"closePort",Toast.LENGTH_LONG).show();

                intent = new Intent();
                intent.putExtra("openPort",false);
                intent.putExtra("deviceType","Both");
                intent.setClassName("com.mosambee.printService", "com.mosambee.printService.PrinterService");
                cordova.getActivity().startService(intent);

                String responseText = "Hello world, " + args.getString(0);
                callbackContext.success(responseText);
            } catch (JSONException e){
                callbackContext.error("Failed to parse parameters");
            }
            return true;
        }else if (action.equals("printer")){
            try {
                //Toast.makeText(cordova.getActivity(),"printer",Toast.LENGTH_LONG).show();

                intent = new Intent();
                intent.putExtra("openPort",true);
                intent.putExtra("deviceType","Printer");
                intent.setClassName("com.mosambee.printService", "com.mosambee.printService.PrinterService");
                cordova.getActivity().startService(intent);

                propertyNo = args.getString(0);
                receiptNo = args.getString(1);
                receiptDate = args.getString(2);
                //ownerName = "Sacheen ramesh  tendulkar";
                ownerName = args.getString(3);
                mobileNo = args.getString(4);
                totalamt = args.getString(5);
                paidamt = args.getString(6);
                outstandAmt = args.getString(7);
                advanceAmt = args.getString(8);
                time = args.getString(9);
                orgName = args.getString(10);
                responseTextY = args.getString(0);
                payMode = args.getString(11);
                chequeNo = args.getString(12);
                chequeDate = args.getString(13);
                bankName = args.getString(14);
                cardNumber = args.getString(15);
                //name = "laxmipathy krishna balaji";
                name = args.getString(16);
                wardNo = args.getString(17);
                mobileTax = args.getString(18);
                //propertyAddress = "Plot No. 268, Linking Rd, Bandra";
                propertyAddress = args.getString(19);
                uuid = args.getString(20);
                copy = args.getString(21);
                zone = args.getString(22);
                ward = args.getString(23);




//                parsedStr = ward.replaceAll("(.{8})", "               $1\n");

                String responseText = "Hello world, " + args.getString(0);
            } catch (JSONException e){
                callbackContext.error("Failed to parse parameters");
            }
            return true;
        }else if (action.equals("startScanner")){
            try {

                Toast.makeText(cordova.getActivity(),"startScanner",Toast.LENGTH_LONG).show();
                intent = new Intent();
                intent.putExtra("openPort",true);
                intent.putExtra("deviceType","Scanner");
                intent.setClassName("com.mosambee.printService", "com.mosambee.printService.PrinterService");
                cordova.getActivity().startService(intent);

            } catch (NoSuchMethodError er) {
                Toast.makeText(cordova.getActivity().getApplicationContext(), "Connection to scanner failed." + er.getMessage(), Toast.LENGTH_SHORT).show();
            }

            return true;
        }else if (action.equals("stopScanner")){
            try {

                Toast.makeText(cordova.getActivity(),"stopScanner",Toast.LENGTH_LONG).show();


                System.out.println("-----------in closeTheSerialPort");
                handler.removeMessages(1000);
                handler.removeMessages(1001);
                handler.removeMessages(999);
                SerialPortIOManage.getInstance().disConnect();
                System.out.println("-----------in closeTheSerialPort");

            } catch (NoSuchMethodError er) {
                Toast.makeText(cordova.getActivity().getApplicationContext(), "Connection to scanner failed." + er.getMessage(), Toast.LENGTH_SHORT).show();
            }
            return true;
        }


        return true;
    }

    private Handler mHandler = new Handler() {
        @SuppressLint("ShowToast")
        @Override
        public void handleMessage(Message msg) {

            System.out.println("@@@@@@@@@@@@" + msg.what);
            switch (msg.what) {
                case PrinterConstants.Connect.SUCCESS:
                    isConnected = true;
                    System.out.println("isConnected status:::;" + isConnected);
                    break;
                case PrinterConstants.Connect.FAILED:
                    isConnected = false;

                    break;
                case PrinterConstants.Connect.CLOSED:
                    isConnected = false;
                    Toast.makeText(cordova.getActivity().getApplicationContext(), "Connection closed", Toast.LENGTH_SHORT).show();
                    break;
                case PrinterConstants.Connect.NODEVICE:
                    isConnected = false;
                    Toast.makeText(cordova.getActivity().getApplicationContext(), "No connection", Toast.LENGTH_SHORT).show();
                    break;
                case 0:
                    Toast.makeText(cordova.getActivity().getApplicationContext(), "0", Toast.LENGTH_SHORT).show();
                    break;
                case -1:
                    Toast.makeText(cordova.getActivity().getApplicationContext(), "-1", Toast.LENGTH_SHORT).show();
                    break;
                case -2:
                    Toast.makeText(cordova.getActivity().getApplicationContext(), "-2", Toast.LENGTH_SHORT).show();
                    break;
                case -3:
                    Toast.makeText(cordova.getActivity().getApplicationContext(), "-3", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        }
    };


    public static Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 888:
                    SerialPortIOManage.getInstance().sendDataToDevice("00");
                    sendEmptyMessageDelayed(1000, 20);
                    break;
                case 999:
                    SerialPortIOManage.getInstance().sendDataToDevice("00");
                    sendEmptyMessageDelayed(1001, 20);
                    break;
                case 1000:
                    SerialPortIOManage.getInstance().sendDataToDevice("04 E4 04 00 FF 14");
                    break;
                case 1001:
                    SerialPortIOManage.getInstance().sendDataToDevice("07 C6 04 00 FF 8A 08 FD 9E");
                    //SerialPortIOManage.getInstance().sendDataToDevice("07 C6 04 80 14 8A 01 FE 10");
                    break;
            }
        }

        ;
    };



    public class MyBroadcastReceiver extends BroadcastReceiver {

        public MyBroadcastReceiver() {
            super();
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            TRACE.i("===== onReceive");


            Bundle bundle = intent.getExtras();
            String deviceType = bundle.getString("deviceType");
            int deviceState = bundle.getInt("deviceState");
            int deviceOpen1 = bundle.getInt("deviceOpen1");
            int deviceOpen2 = bundle.getInt("deviceOpen2");

            assert deviceType != null;
//          if (deviceType.equals("Printer")) {
            if (deviceState == 0 && deviceOpen1 == 0 && deviceOpen2 == 0) {
//                        buttonPrinter.setEnabled(true);
//                        buttonPrinter.setBackgroundColor(getResources().getColor(R.color.enable));
              Toast.makeText(cordova.getActivity().getApplicationContext(), "" + deviceType + "\ndeviceState: " + deviceState + "\ndeviceOpen1: " + deviceOpen1 + "\ndeviceOpen2: " + deviceOpen2, Toast.LENGTH_LONG).show();
              try {
                goForPrint();
              } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
              }


            } else {
              Toast.makeText(cordova.getActivity().getApplicationContext(), "" + deviceType + "\n else", Toast.LENGTH_LONG).show();
            }

//          } else if (deviceType.equals("Scanner")) {
//            if (deviceState == 0 && deviceOpen1 == 0) {
////                        buttonScanner.setEnabled(true);
////                        buttonScanner.setBackgroundColor(getResources().getColor(R.color.enable));
////                        buttonStopScan.setEnabled(true);
////                        buttonStopScan.setBackgroundColor(getResources().getColor(R.color.enable));
//              goForScanner();
//              Toast.makeText(cordova.getActivity().getApplicationContext(), "" + deviceType + "\ndeviceState: " + deviceState + "\ndeviceOpen1: " + deviceOpen1, Toast.LENGTH_LONG).show();
//
//            } else {
//              Toast.makeText(cordova.getActivity().getApplicationContext(), "" + deviceType + "\n else", Toast.LENGTH_LONG).show();
//            }
//
//            if (deviceState == 0 && deviceOpen1 == 0 && deviceOpen2 == 0) {
////                        buttonPrinter.setEnabled(true);
////                        buttonPrinter.setBackgroundColor(getResources().getColor(R.color.enable));
////                        buttonScanner.setEnabled(true);
////                        buttonScanner.setBackgroundColor(getResources().getColor(R.color.enable));
//              Toast.makeText(cordova.getActivity().getApplicationContext(), "" + deviceType + "\ndeviceState: " + deviceState + "\ndeviceOpen1: " + deviceOpen1 + "\ndeviceOpen2: " + deviceOpen2, Toast.LENGTH_LONG).show();
//
//            } else {
//              Toast.makeText(cordova.getActivity().getApplicationContext(), "" + deviceType + "\n else", Toast.LENGTH_LONG).show();
//            }
//
//          } else if (deviceType.equals("Both")) {
//            if (deviceState == 0 && deviceOpen1 == 0 && deviceOpen2 == 0) {
////                        buttonPrinter.setEnabled(true);
////                        buttonPrinter.setBackgroundColor(getResources().getColor(R.color.enable));
////                        buttonScanner.setEnabled(true);
////                        buttonScanner.setBackgroundColor(getResources().getColor(R.color.enable));
//              Toast.makeText(cordova.getActivity().getApplicationContext(), "" + deviceType + "\ndeviceState: " + deviceState + "\ndeviceOpen1: " + deviceOpen1 + "\ndeviceOpen2: " + deviceOpen2, Toast.LENGTH_LONG).show();
//
//            } else {
//              Toast.makeText(cordova.getActivity().getApplicationContext(), "" + deviceType + "\n else", Toast.LENGTH_LONG).show();
//            }
//
//          }
        }

        //Monochromatic image pass to printImage()

        public void goForPrint() throws UnsupportedEncodingException {
            System.out.println("----------buttonPrint--------");
            devicesName = "Serial device";
            devicesAddress = "/dev/ttyMT0";
            String com_baudrate = "115200";
            baudrate = Integer.parseInt(com_baudrate);
            myPrinter = PrinterInstance.getPrinterInstance(new File(devicesAddress), baudrate, 0, mHandler);
            System.out.println("myPrinter.getCurrentStatus()-" + myPrinter.getCurrentStatus());
            boolean b = myPrinter.openConnection();
            System.out.println("-----------" + b);
            String stringy = "₹";
            DecimalFormat format = new DecimalFormat("0.00");
           // System.out.println(format.format(myNumber));
           // myPrinter.printText(responseTextY.toString());

          myPrinter.setPrinter(PrinterConstants.Command.ALIGN, 1);
          myPrinter.setFont(0, 0, 0, 1, 0);
          myPrinter.printText(orgName+"\n");

          myPrinter.setPrinter(PrinterConstants.Command.ALIGN, 1);
          myPrinter.setFont(0, 0, 0, 1, 0);
          myPrinter.printText("Rule 20 ( 2 )"+"\n");

          myPrinter.setPrinter(PrinterConstants.Command.ALIGN, 1);
          myPrinter.setFont(0, 0, 0, 1, 0);
          myPrinter.printText("Property Tax Receipt\n");

          myPrinter.setPrinter(PrinterConstants.Command.ALIGN, 1);
          myPrinter.setFont(0, 0, 0, 1, 0);
          myPrinter.printText(copy+" copy"+"\n");

          myPrinter.setPrinter(PrinterConstants.Command.ALIGN, 1);
          myPrinter.setFont(0, 0, 0, 0, 0);
          myPrinter.printText("Date:"+receiptDate+"\t Time:"+time+"\n");
          myPrinter.setPrinter(PrinterConstants.Command.ALIGN, 1);

          myPrinter.setPrinter(PrinterConstants.Command.ALIGN, 1);
          myPrinter.setFont(0, 0, 0, 1, 0);
          myPrinter.printText("----------------\n");

          myPrinter.setPrinter(PrinterConstants.Command.ALIGN, 0);
          myPrinter.setFont(0, -4, -4, 1, 0);
          myPrinter.printText("Receipt No.   : "+receiptNo+"\n");
         // myPrinter.setPrinter(PrinterConstants.Command.PRINT_AND_WAKE_PAPER_BY_LINE, 1);

          myPrinter.setPrinter(PrinterConstants.Command.ALIGN, 0);
          myPrinter.setFont(0, 0, 0, 1, 0);
          myPrinter.printText("Property No.  : "+propertyNo+"\n");
          //myPrinter.setPrinter(PrinterConstants.Command.PRINT_AND_WAKE_PAPER_BY_LINE, 1);

            if (ward.length() > 16) {
              myPrinter.setPrinter(PrinterConstants.Command.ALIGN, 4);
              myPrinter.setFont(0, 0, 0, 1, 0);
              myPrinter.printText("Zone & ward   : " + zone + ",\n                " + padded(ward, 16, "                ") + "\n");
            } else {
              myPrinter.setPrinter(PrinterConstants.Command.ALIGN, 4);
              myPrinter.setFont(0, 0, 0, 1, 0);
              myPrinter.printText("Zone & ward   : " + zone + ",\n                " + ward + "\n");
            }


          if(propertyAddress.length()>16){
            myPrinter.setPrinter(PrinterConstants.Command.ALIGN, 4);
            myPrinter.setFont(0, 0, 0, 1, 0);
            myPrinter.printText("Address       : "+padded(propertyAddress,16,"                ")+"\n");
          }else {
            myPrinter.setPrinter(PrinterConstants.Command.ALIGN, 0);
            myPrinter.setFont(0, 0, 0, 1, 0);
            myPrinter.printText("Address       : "+propertyAddress+"\n");
          }

          //myPrinter.setPrinter(PrinterConstants.Command.PRINT_AND_WAKE_PAPER_BY_LINE, 1);
          if(ownerName.length()>16) {
            myPrinter.setPrinter(PrinterConstants.Command.ALIGN, 0);
            myPrinter.setFont(0, 0, 0, 1, 0);
            myPrinter.printText("Owner name    : " + padded(ownerName,16,"                ") + "\n");
          }else {
            myPrinter.setPrinter(PrinterConstants.Command.ALIGN, 0);
            myPrinter.setFont(0, 0, 0, 1, 0);
            myPrinter.printText("Owner name    : " + ownerName + "\n");
          }
         // myPrinter.setPrinter(PrinterConstants.Command.PRINT_AND_WAKE_PAPER_BY_LINE, 1);

          myPrinter.setPrinter(PrinterConstants.Command.ALIGN, 0);
          myPrinter.setFont(0, 0, 0, 1, 0);
          myPrinter.printText("Mobile No.    : "+mobileNo+"\n");
          //myPrinter.setPrinter(PrinterConstants.Command.PRINT_AND_WAKE_PAPER_BY_LINE, 1);

          myPrinter.setPrinter(PrinterConstants.Command.ALIGN, 0);
          myPrinter.setFont(0, 0, 0, 1, 0);
          myPrinter.printText("Total Amount  : Rs. "+Float.parseFloat(totalamt)+"\n");
          //myPrinter.setPrinter(PrinterConstants.Command.PRINT_AND_WAKE_PAPER_BY_LINE, 1);

          myPrinter.setPrinter(PrinterConstants.Command.ALIGN, 0);
          myPrinter.setFont(0, 0, 0, 1, 0);
          myPrinter.printText("----------------\n");

          myPrinter.setPrinter(PrinterConstants.Command.ALIGN, 0);
          myPrinter.setFont(0, 0, 0, 1, 0);
          myPrinter.printText("Paid Amount   : Rs. "+Float.parseFloat(paidamt)+"\n");
          //myPrinter.setPrinter(PrinterConstants.Command.PRINT_AND_WAKE_PAPER_BY_LINE, 1);

          myPrinter.setPrinter(PrinterConstants.Command.ALIGN, 0);
          myPrinter.setFont(0, 0, 0, 0, 0);
          myPrinter.printText("\n");

          myPrinter.setPrinter(PrinterConstants.Command.ALIGN, 0);
          myPrinter.setFont(0, 0, 0, 1, 0);
          myPrinter.printText("Outstanding \nAmount        : Rs. "+Float.parseFloat(outstandAmt)+"\n");
         // myPrinter.setPrinter(PrinterConstants.Command.PRINT_AND_WAKE_PAPER_BY_LINE, 1);

          myPrinter.setPrinter(PrinterConstants.Command.ALIGN, 0);
          myPrinter.setFont(0, 0, 0, 1, 0);
          myPrinter.printText("Advance Amount: Rs. "+Float.parseFloat(advanceAmt)+"\n");

          myPrinter.setPrinter(PrinterConstants.Command.ALIGN, 0);
          myPrinter.setFont(0, 0, 0, 1, 0);
          myPrinter.printText("Pos ID        : "+uuid +"\n");
          //myPrinter.setPrinter(PrinterConstants.Command.PRINT_AND_WAKE_PAPER_BY_LINE, 1);
        if(payMode.equals("WEB")){
          myPrinter.setPrinter(PrinterConstants.Command.ALIGN, 0);
          myPrinter.setFont(0, 0, 0, 1, 0);
          myPrinter.printText("Pay Mode      : "+ "Credit/Debit\n                 Card"+"\n");
         //myPrinter.setPrinter(PrinterConstants.Command.PRINT_AND_WAKE_PAPER_BY_LINE, 1);
        }else {
          myPrinter.setPrinter(PrinterConstants.Command.ALIGN, 0);
          myPrinter.setFont(0, 0, 0, 1, 0);
          myPrinter.printText("Pay Mode      : " + payMode+"\n");
         /// myPrinter.setPrinter(PrinterConstants.Command.PRINT_AND_WAKE_PAPER_BY_LINE, 1);
        }

          if(payMode.equals("Cheque")||payMode.equals("Demand Draft")||payMode.equals("WEB")){
            if(payMode.equals("Cheque")) {
              myPrinter.setPrinter(PrinterConstants.Command.ALIGN, 0);
              myPrinter.setFont(0, 0, 0, 1, 0);
              myPrinter.printText("Cheque no.    : " + chequeNo+"\n");
             // myPrinter.setPrinter(PrinterConstants.Command.PRINT_AND_WAKE_PAPER_BY_LINE, 1);

              myPrinter.setPrinter(PrinterConstants.Command.ALIGN, 0);
              myPrinter.setFont(0, 0, 0, 1, 0);
              myPrinter.printText("Cheque date   : " + chequeDate+"\n");
             // myPrinter.setPrinter(PrinterConstants.Command.PRINT_AND_WAKE_PAPER_BY_LINE, 1);

              myPrinter.setPrinter(PrinterConstants.Command.ALIGN, 0);
              myPrinter.setFont(0, 0, 0, 1, 0);
              myPrinter.printText("Bank :" + bankName+"\n");
             // myPrinter.setPrinter(PrinterConstants.Command.PRINT_AND_WAKE_PAPER_BY_LINE, 1);

              myPrinter.setPrinter(PrinterConstants.Command.ALIGN, 1);
              myPrinter.setFont(0, 0, 0, 1, 0);
              myPrinter.printText("\n");
              //myPrinter.setPrinter(PrinterConstants.Command.PRINT_AND_WAKE_PAPER_BY_LINE, 1);

              myPrinter.setPrinter(PrinterConstants.Command.ALIGN, 1);
              myPrinter.setFont(0, 0, 0, 1, 2);
              myPrinter.printText("Receipt subject to realisation  of cheque \n");
             // myPrinter.setPrinter(PrinterConstants.Command.PRINT_AND_WAKE_PAPER_BY_LINE, 1);
            }else if(payMode.equals("Demand Draft")){
              myPrinter.setPrinter(PrinterConstants.Command.ALIGN, 0);
              myPrinter.setFont(0, 0, 0, 1, 0);
              myPrinter.printText("Demand Draft \nno            : " + chequeNo+"\n");
             // myPrinter.setPrinter(PrinterConstants.Command.PRINT_AND_WAKE_PAPER_BY_LINE, 1);

              myPrinter.setPrinter(PrinterConstants.Command.ALIGN, 0);
              myPrinter.setFont(0, 0, 0, 1, 0);
              myPrinter.printText("Demand Draft \ndate          : " + chequeDate+"\n");
              //myPrinter.setPrinter(PrinterConstants.Command.PRINT_AND_WAKE_PAPER_BY_LINE, 1);

              myPrinter.setPrinter(PrinterConstants.Command.ALIGN, 0);
              myPrinter.setFont(0, 0, 0, 1, 0);
              myPrinter.printText("Bank - " + bankName+"\n");
             // myPrinter.setPrinter(PrinterConstants.Command.PRINT_AND_WAKE_PAPER_BY_LINE, 1);

              myPrinter.setPrinter(PrinterConstants.Command.ALIGN, 1);
              myPrinter.setFont(0, 0, 0, 1, 0);
              myPrinter.printText("\n");
              //myPrinter.setPrinter(PrinterConstants.Command.PRINT_AND_WAKE_PAPER_BY_LINE, 1);

              myPrinter.setPrinter(PrinterConstants.Command.ALIGN, 1);
              myPrinter.setFont(0, 0, 0, 1, 2);
              myPrinter.printText("Receipt subject to realisation  of Demand draft \n");
              //myPrinter.setPrinter(PrinterConstants.Command.PRINT_AND_WAKE_PAPER_BY_LINE, 1);
            }else{
              myPrinter.setPrinter(PrinterConstants.Command.ALIGN, 0);
              myPrinter.setFont(0, 0, 0, 1, 0);
              myPrinter.printText("Card Number   : " + cardNumber+"\n");
              //myPrinter.setPrinter(PrinterConstants.Command.PRINT_AND_WAKE_PAPER_BY_LINE, 1);
            }
          }

          myPrinter.setPrinter(PrinterConstants.Command.ALIGN, 0);
          myPrinter.setFont(0, 0, 0, 0, 0);
          myPrinter.printText("\n");

          if(name.length()>16) {
            myPrinter.setPrinter(PrinterConstants.Command.ALIGN, 0);
            myPrinter.setFont(0, 0, 0, 1, 0);
            myPrinter.printText("Tax Collector : " + padded(name,16,"                ") + "\n");
          }else {
            myPrinter.setPrinter(PrinterConstants.Command.ALIGN, 0);
            myPrinter.setFont(0, 0, 0, 1, 0);
            myPrinter.printText("Tax Collector : " + name + "\n");
          }

          myPrinter.setPrinter(PrinterConstants.Command.ALIGN, 0);
          myPrinter.setFont(0, 0, 0, 1, 0);
          myPrinter.printText("Mobile No     : " +mobileTax+"\n");

          myPrinter.setPrinter(PrinterConstants.Command.ALIGN, 1);
          myPrinter.setFont(0, 0, 0, 1, 0);
          myPrinter.printText("----------Thank you---------\n");

          myPrinter.setPrinter(PrinterConstants.Command.ALIGN, 1);
          myPrinter.setFont(0, 0, 0, 0, 0);
          myPrinter.printText("Note:Please keep this receipt \n for future reference." +
            " For \n detailed bill please visit \n"+orgName+"\n or https://urbanecg.gov.in\n\n");

          myPrinter.setPrinter(PrinterConstants.Command.ALIGN, 1);
          myPrinter.setFont(0, 0, 0, 0, 0);
          myPrinter.printText("This is electronically generated receipt and does not require   any  signature\n");

          myPrinter.setFont(0, 0, 0, 0, 0);
          myPrinter.setPrinter(PrinterConstants.Command.ALIGN, 1);
          myPrinter.setPrinter(PrinterConstants.Command.PRINT_AND_WAKE_PAPER_BY_LINE, 3);


          myPrinter.cutPaper(1,0);
            if (b && myPrinter != null && myPrinter.getCurrentStatus() == 0) {
                //printData();
                // Bitmap icon = BitmapFactory.decodeResource(cordova.getActivity().getResources(), R.drawable.download);
                // Drawable myDrawable = cordova.getActivity().getResources().getDrawable(R.drawable.download);
                // Bitmap anImage      = ((BitmapDrawable) myDrawable).getBitmap();
                //myPrinter.printImage(anImage, PrinterConstants.PAlign.CENTER,1,false);
                //myPrinter.printText(responseTextY.toString());
                //    myPrinter.printText("Synergistic Financial Network");
                //myPrinter.drawQrCode(2, 2, "123456", PrinterConstants.PRotate.Rotate_0, 2, 2);
            }else
                Toast.makeText(cordova.getActivity().getApplicationContext(), "Connection to printer failed.", Toast.LENGTH_SHORT).show();

        }

        public void goForScanner(){
            try {
                System.out.println("-----------goForScanner");

                System.out.println("-----------in openTheSerialPort");
                Intent startIntent2 = new Intent(cordova.getActivity().getApplicationContext(), SerialPortService.class);
                startIntent2.putExtra("serial", "dev/ttyMT1");
                cordova.getActivity().startService(startIntent2);
                handler.removeMessages(1000);
                handler.removeMessages(1001);
                handler.removeMessages(999);
                handler.sendEmptyMessageDelayed(999, 10);

                System.out.println("-----------in openTheScanHead");
                SerialPortIOManage.getInstance().resetBuffer();
                handler.removeMessages(888);
                handler.removeMessages(999);
                handler.removeMessages(1000);
                handler.removeMessages(1001);
                handler.sendEmptyMessageDelayed(888, 1000);


            } catch (NoSuchMethodError  er) {
                Toast.makeText(cordova.getActivity().getApplicationContext(), "Connection to scanner failed." + er.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }


    }

//  public static StringBuilder splitString(String msg, int lineSize) {
//    List<String> res = new ArrayList<String>();
//
//    Pattern p = Pattern.compile("\\b.{1," + (lineSize-1) + "}\\b\\W?");
//    Matcher m = p.matcher(msg);
//
//    while(m.find()) {
//      System.out.println(m.group().trim());   // Debug
//      res.add(m.group());
//    }
//    StringBuilder out = new StringBuilder();
//    for (Object o : res)
//    {
//      out.append(o.toString());
//      out.append("                     ");
//    }
//
//    return out;
//  }

//  public static String printLine(String str, int offset) {
//    String spaceOffset = new String(new char[offset]).replace('\0', ' ');
//    StringBuilder sb = new StringBuilder();
//    String[] starr = str.split("(?<=\\G.{" + offset + "})");
//    for (int i = 0; i <starr.length; i++) {
//      sb.append("\n" + spaceOffset).append(starr[i]);
//    }
//    return sb.toString();
//  }

  public String padded(String original, int interval, String separator) {

      String formatted = "";

      for (int i = 0; i < original.length(); i++) {
        if (i % interval == 0 && i > 0) {
          formatted += separator;
        }
        formatted += original.substring(i, i + 1);
      }

      return formatted;

  }

}
