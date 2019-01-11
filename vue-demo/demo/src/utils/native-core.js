export default _ => {
  var userAgent = navigator.userAgent
  var os = {
    iOS: !!userAgent.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/),
    Android: userAgent.indexOf('Android') > -1 || userAgent.indexOf('Linux') > -1,
    WeChat: !!userAgent.match(/MicroMessenger/i),
    WeiBo: !!userAgent.match(/WeiBo/i),
    QQ: !!userAgent.match(/QQ/i)
  }

  var nativeBridge = window.$nativeBridge || {}
  window.$nativeBridge = nativeBridge

  var nativeBridgeWidget = window.$nativeBridgeWidget || {}
  window.$nativeBridgeWidget = nativeBridgeWidget

  var jsBridge = window.$jsBridge || {}
  window.$jsBridge = jsBridge

  return {
    /**
     * js 调用 native 方法，可传参，支持函数回调
     * @param {string} pluginName 插件名，如 UI 组件，Store 存储组件，Kit 相机，蓝牙等组件
     * @param {string} pluginFunc 函数名，如 UI.alert, alert 为 UI 组件的函数名
     * @param  {...any} theArgs 函数参数，回调函数
     * 使用 eval 函数，拼接出符合 ios，android 系统对应的 js 代码，并执行
     */
    evaluateNative (pluginName, pluginFunc, ...theArgs) {
      var nativeFuncName = pluginName + '.' + pluginFunc
      nativeFuncName += '('
      var argsCount = theArgs.length
      var i
      for (i = 0; i < argsCount; i++) {
        var argValue = theArgs[i]
        if (typeof (argValue) === 'function') {
          // ios 支持直接传函数作为参数
          if (os.iOS == true) {
            nativeFuncName += argValue
          } else if (os.Android == true) {
            // android 不支持直接传函数，只支持传 string
            var callbackFuncName = pluginName + '$' + pluginFunc + '$' + i
            // 维护 native 调用 js 的这些回调函数
            var spacename = 'AndroidCallbacks'
            this.putGlobalValue(spacename, callbackFuncName, argValue)
            nativeFuncName += "'"
            nativeFuncName += '$nativeBridge.' + spacename + '.' + callbackFuncName
            nativeFuncName += "'"
          }
        } else if (typeof (argValue) === 'object') {
          var argJsonValue = JSON.stringify(argValue)
          if (os.iOS == true) {
            nativeFuncName += argJsonValue
          } else if (os.Android == true) {
            nativeFuncName += "'"
            nativeFuncName += argJsonValue
            nativeFuncName += "'"
          }
        } else {
          nativeFuncName += "'"
          nativeFuncName += argValue
          nativeFuncName += "'"
        }
        if (i != argsCount - 1) {
          nativeFuncName += ','
        }
      }
      nativeFuncName += ')'
      console.log('ecal 函数：' + nativeFuncName)
      eval(nativeFuncName)
    },
    /**
     * 注册供 native 端调用的 js 方法，如 $jsBridge[funcName](arg0, arg1 ...)
     * @param {string} funcName
     * @param {function} func
     */
    registerJs (funcName, func) {
      jsBridge[funcName] = func
    },
    /**
     * 注册组件对象到全局对象 $nativeBridgeWidget 中
     * @param {string} key
     * @param {object} value
     */
    loadWidget (key, value) {
      var widget = nativeBridgeWidget[key] || value
      nativeBridgeWidget[key] = widget
    },
    /**
     * 设置全局变量到 $nativeBridge 中
     * @param {*} namespace 命名空间，避免重名
     * @param {*} key
     * @param {*} value
     */
    putGlobalValue (namespace, key, value) {
      var space = nativeBridge[namespace] || {}
      space[key] = value
      nativeBridge[namespace] = space
    }
  }
}
