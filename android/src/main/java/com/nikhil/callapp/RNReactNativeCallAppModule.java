
package com.nikhil.callapp;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.database.Cursor;
import android.provider.CallLog;
import android.provider.CallLog.Calls;
import android.support.annotation.NonNull;
import android.util.Log;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableMap;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RNReactNativeCallAppModule extends ReactContextBaseJavaModule {

  private static ReactApplicationContext reactContext;
  private String mobile_no;
  private static final int PERMISSIONS_REQUEST_ACCESS_CALL = 101;
  public RNReactNativeCallAppModule(ReactApplicationContext reactContext) {
    super(reactContext);
    RNReactNativeCallAppModule.reactContext = reactContext;
  }

  @Override
  public String getName() {
    return "RNReactNativeCallApp";
  }

  @ReactMethod
  public void callPerson(String phone_no) {
    this.mobile_no = phone_no;
    if(ContextCompat.checkSelfPermission(reactContext.getApplicationContext(), Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED) {
      ActivityCompat.requestPermissions(getCurrentActivity(), new String[]{android.Manifest.permission.CALL_PHONE}, PERMISSIONS_REQUEST_ACCESS_CALL);
    } else {
    Log.d("CALL LOG", "Here inside callPerson func" + phone_no);
      Intent call = new Intent(Intent.ACTION_CALL);
      call.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
      call.setData(Uri.parse("tel:"+ phone_no));
      RNReactNativeCallAppModule.reactContext.startActivity(call);
    }
  }

  @ReactMethod
  public String getCallLogs(int limit, Promise promise) {
    StringBuffer sb = new StringBuffer();
    Cursor managedCursor = this.getReactApplicationContext().getContentResolver().query(CallLog.Calls.CONTENT_URI, null,
            null, null, null);
    int number = managedCursor.getColumnIndex(CallLog.Calls.NUMBER);
    int type = managedCursor.getColumnIndex(CallLog.Calls.TYPE);
    int date = managedCursor.getColumnIndex(CallLog.Calls.DATE);
    int duration = managedCursor.getColumnIndex(CallLog.Calls.DURATION);
    sb.append("Call Details :");

    while (managedCursor.moveToNext()) {
      String phNumber = managedCursor.getString(number);
      String callType = managedCursor.getString(type);
      String callDate = managedCursor.getString(date);
      Date callDayTime = new Date(Long.valueOf(callDate));
      String callDuration = managedCursor.getString(duration);
      String dir = null;
      int dircode = Integer.parseInt(callType);
      switch (dircode) {
        case CallLog.Calls.OUTGOING_TYPE:
          dir = "OUTGOING";
          break;

        case CallLog.Calls.INCOMING_TYPE:
          dir = "INCOMING";
          break;

        case CallLog.Calls.MISSED_TYPE:
          dir = "MISSED";
          break;
      }
      sb.append("\nPhone Number:--- " + phNumber + " \nCall Type:--- "
              + dir + " \nCall Date:--- " + callDayTime
              + " \nCall duration in sec :--- " + callDuration);
      sb.append("\n----------------------------------");
    }
    Log.d("CALL LOG", " here is the call logs " + sb);
    managedCursor.close();
    return sb.toString();
  }

  private String resolveCallType(int callTypeCode) {
    switch (callTypeCode) {
      case Calls.OUTGOING_TYPE:
        return "OUTGOING";
      case Calls.INCOMING_TYPE:
        return "INCOMING";
      case Calls.MISSED_TYPE:
        return "MISSED";
      default:
        return "UNKNOWN";
    }
  }

  private boolean shouldContinue(int limit, int count) {
    return limit < 0 || count < limit;
  }
//  @Override
  public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResult) {
    if(requestCode == PERMISSIONS_REQUEST_ACCESS_CALL) {
      if(grantResult[0] == PackageManager.PERMISSION_GRANTED) {
        callPerson(this.mobile_no);
      }
    }
  }
}