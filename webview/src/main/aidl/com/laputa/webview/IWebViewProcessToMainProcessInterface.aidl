// IWebViewProcessToMainProcessInterface.aidl
package com.laputa.webview;

// Declare any non-default types here with import statements
import com.laputa.webview.IWebViewProcessToMainProcessCallback;

// WebView process to Main  process 接口
interface IWebViewProcessToMainProcessInterface {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void handleCommand(String commandName,String param,in IWebViewProcessToMainProcessCallback callback);
}
