var laputajs = {};
laputajs.os = {};
laputajs.os.isIOS = /iOS|iPhone|iPad|iPod/i.test(navigator.userAgent);
laputajs.os.isAndroid = !laputajs.os.isIOS;
laputajs.callbacks = {}

laputajs.callback = function (callbackname, response) {
   var callbackobject = laputajs.callbacks[callbackname];
   console.log("xxxx"+callbackname);
   if (callbackobject !== undefined){
       if(callbackobject.callback != undefined){
          console.log("xxxxxx"+response);
            var ret = callbackobject.callback(response);
           if(ret === false){
               return
           }
           delete laputajs.callbacks[callbackname];
       }
   }
}

laputajs.takeNativeAction = function(commandname, parameters){
    console.log("laputajs takenativeaction")
    var request = {};
    request.name = commandname;
    request.param = parameters;
    if(window.laputajs.os.isAndroid){
        console.log("android take native action" + JSON.stringify(request));
        window.lauputawebview.takeNativeAction(JSON.stringify(request));
    } else {
        window.webkit.messageHandlers.lauputawebview.postMessage(JSON.stringify(request))
    }
}

laputajs.takeNativeActionWithCallback = function(commandname, parameters, callback) {
    var callbackname = "nativetojs_callback_" +  (new Date()).getTime() + "_" + Math.floor(Math.random() * 10000);
    laputajs.callbacks[callbackname] = {callback:callback};

    var request = {};
    request.name = commandname;
    request.param = parameters;
    request.param.callbackname = callbackname;
    if(window.laputajs.os.isAndroid){
        window.lauputawebview.takeNativeAction(JSON.stringify(request));
    } else {
        window.webkit.messageHandlers.lauputawebview.postMessage(JSON.stringify(request))
    }
}

window.laputajs = laputajs;
