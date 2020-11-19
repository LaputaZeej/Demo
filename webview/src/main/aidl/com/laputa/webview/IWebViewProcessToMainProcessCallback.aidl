// IWebViewProcessToMainProcessCallback.aidl
package com.laputa.webview;

// Declare any non-default types here with import statements

// WebView process to Main  process 回调
interface IWebViewProcessToMainProcessCallback {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void onResult(String callbackName,String response);
}
