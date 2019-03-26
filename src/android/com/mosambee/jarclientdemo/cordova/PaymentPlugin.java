package com.mosambee.jarclientdemo.cordova;

import android.content.Intent;
import android.widget.Toast;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class PaymentPlugin extends CordovaPlugin {
    private CallbackContext callbackContext = null;
    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext)
            throws JSONException {
        this.callbackContext=callbackContext;
//        Toast.makeText(cordova.getActivity(),"mosambee",Toast.LENGTH_LONG).show();

        MosambeeUtils mosutils;
        mosutils = new MosambeeUtils();
        cordova.setActivityResultCallback(PaymentPlugin.this);

        String name = args.getString(0);
        String mobile = args.getString(1);
        int amount = Integer.parseInt(args.getString(2));
        String invoice = args.getString(3);
        //Toast.makeText(cordova.getActivity(),"amount"+amount,Toast.LENGTH_SHORT).show();
        mosutils.startPayment(cordova.getActivity(), "Sale", amount, false,
          mobile, cordova.getActivity(), "", name,
                invoice, 200, false, "",
          "", "", "9409", "", "BT");

        return true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
       // Toast.makeText(cordova.getActivity(),"result", Toast.LENGTH_LONG).show();
        System.out.println("ResultCode=" + resultCode
                + "\nrequestCode " + requestCode);
        System.out.println("----------"+data);

        if(data==null){

            callbackContext.error("Fail.");
           // return;
        }
      String status = "";
      String cardNumber = "";
      String response = data.getStringExtra("response");
      System.out.println("Y----------"+response);
      try {
        JSONObject jsonObject =new JSONObject(response);
        status = String.valueOf(jsonObject.get("transactionStatus"));
        cardNumber = String.valueOf(jsonObject.get("cardNumber"));
      } catch (JSONException e) {
        e.printStackTrace();
      }

        System.out.println("response" + status);

        if(status.equals("Approved or completed successfully"))
        {
          callbackContext.success(cardNumber);

        }else {
          callbackContext.error("Fail.");
        }
        try {
            JSONObject jsonObject =new JSONObject(response);
            JSONArray jsonArray = new JSONArray(jsonObject.getString("history"));
            for (int i = 0, n = jsonArray.length(); i < n; i++) {
                JSONArray historyItem = new JSONArray(jsonArray.get(i).toString());
                System.out.println("History single item array"+ historyItem);
                for (int j = 0; j < historyItem.length(); j++) {
                    //System.out.println("history item : "+historyItem.get(j));
                }
            }
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


    }
}
