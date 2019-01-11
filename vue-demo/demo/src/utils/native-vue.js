import NCore from './native-core.js'
import NUI from './native-ui.js'
import NNavigator from './native-navigator.js'

var jsBridge = {}
jsBridge.install = function (Vue, options) {
  var nCore = NCore()
  var nUi = NUI(nCore)
  var nNavigator = NNavigator(nCore)
  Vue.prototype.$nativeCore = nCore
  Vue.prototype.$nativeUi = nUi
  Vue.prototype.$nativeNavigator = nNavigator
}
export default jsBridge
