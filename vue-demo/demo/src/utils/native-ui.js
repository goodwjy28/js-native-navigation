export default core => {
  return {
    sendMessage (name, param, headler) {
      this.sendMessage.headler = headler
      core.loadWidget('ui', this)
      core.evaluateNative('NativeUI', 'sendMessage', name, param, function headler (result) {
        return window.$nativeBridgeWidget.ui.sendMessage.headler(result)
      })
    },
    alert (title, msg, affirm, cancel) {
      this.alert.affirm = affirm
      this.alert.cancel = cancel
      core.loadWidget('ui', this)
      core.evaluateNative('NativeUI', 'alert', title, msg, function affirm () {
        return window.$nativeBridgeWidget.ui.alert.affirm()
      }, function cancel () {
        return window.$nativeBridgeWidget.ui.alert.cancel()
      })
    },
    promptAlert (msg) {
      this.alert('提示', msg, () => {}, () => {})
    }
  }
}
