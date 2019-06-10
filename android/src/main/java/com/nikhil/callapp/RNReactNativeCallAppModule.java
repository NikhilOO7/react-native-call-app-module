
package com.nikhil.callapp;
import android.os.Build;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Callback;

public class RNReactNativeCallAppModule extends ReactContextBaseJavaModule {

  private final ReactApplicationContext reactContext;

  public RNReactNativeCallAppModule(ReactApplicationContext reactContext) {
    super(reactContext);
    this.reactContext = reactContext;
  }

  @Override
  public String getName() {
    return "RNReactNativeCallApp";
  }

  @ReactMethod
  public void callPerson(String phone_no) {
    if(checkSelfPermission(Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED) {
      requestPermission(new String[](Manifest.permission.CALL_PHONE), pid);
    } else {
      Intent call = new Intent(Intent.ACTION_CALL);
      call.setData(Uri.parse("tel:"+ phone_no));
      startActivity(call);
    }
  }

  @Override
  public void onRequestPermissionResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResult) {
    super.onRequestPermissionResult(requestCode, permissions, grantResult);
    if(requestCode == pid) {
      if(grantResult[0] == PackageManager.PERMISSION_GRANTED) {
        callatruntimepermission();
      }
    }
  }
}