export default core => {
  return {
    /**
     * 使用原生导航方式，导航 web 页面
     * @param {string} name 路径名，远程 url 地址或原生页面路由 key
     * @param {string} type path（web 页面间的导航）, url（native 打开一个 url 链接）, native（打开一个原生页面）
     * @param {object} param 传递的上下文参数
     */
    push (name, type, param) {
      if (type === undefined || type === null) {
        type = 'path'
      }
      if (param === undefined || param === null) {
        param = {}
      }
      core.evaluateNative('NativeNavigator', 'push', name, type, param)
    },

    /**
     * 返回上一页
     * @param {object} param 回传的上下文参数
     */
    goBack (param) {
      if (param === undefined || param === null) {
        param = {}
      }
      core.evaluateNative('NativeNavigator', 'goBack', param)
    },

    /**
     * 返回根页面
     */
    goBackRoot () {
      core.evaluateNative('NativeNavigator', 'goBackRoot')
    },

    /**
     * 注册通知，接受后一页面回传的值
     * @param {funtion} callback
     */
    receiveGoBack (callback) {
      core.registerJs('noticeGoBack', callback)
    },

    /**
     * 获取上一页传来的上下文信息
     * @param {function} callback 回调函数，获取上下文信息
     */
    getRouteContext (callback) {
      this.getRouteContext.callback = callback
      core.loadWidget('navigator', this)
      core.evaluateNative('NativeNavigator', 'getRouteContext', function (info) {
        return window.$nativeBridgeWidget.navigator.getRouteContext.callback(info)
      })
    },

    /**
     * 设置 native 导航条的 title
     * @param {string} title
     */
    setBarTitle (title) {
      core.evaluateNative('NativeNavigator', 'setBarTitle', title)
    },

    /**
     * 设置 native 导航条的 rightButtonItem
     * @param {object} itemInfo {image, title}, 原生优先使用 image，如没有 image 则使用 title
     * @param {function} callback 点击按钮回调该函数
     */
    setBarRightButton (itemInfo, callback) {
      core.evaluateNative('NativeNavigator', 'setBarRightButton', itemInfo, callback)
    },

    /**
     * 设置 native 导航条的 rightButtonItem 两个
     * @param {object} itemInfo1 第一个 button 的信息，{image, title}, 原生优先使用 image，如没有 image 则使用 title
     * @param {function} callback1 第一个回调函数，点击按钮回调该函数
     * @param {object} itemInfo2 第二个 button 的信息，{image, title}, 原生优先使用 image，如没有 image 则使用 title
     * @param {function} callback2 第二个回调函数，点击按钮回调该函数
     */
    setBarDoubleRightButton (itemInfo1, callback1, itemInfo2, callback2) {
      core.evaluateNative('NativeNavigator', 'setBarDoubleRightButton', itemInfo1, callback1, itemInfo2, callback2)
    },

    /**
     * 设置 native 导航条的 leftButtonItem
     * @param {object} itemInfo {image, title}, 原生优先使用 image，如没有 image 则使用 title
     * @param {function} callback 点击按钮回调该函数
     */
    setBarLeftButton (itemInfo, callback) {
      core.evaluateNative('NativeNavigator', 'setBarLeftButton', itemInfo, callback)
    },

    /**
     * 设置 native 导航条的 leftButtonItem 两个
     * @param {object} itemInfo1 第一个 button 的信息，{image, title}, 原生优先使用 image，如没有 image 则使用 title
     * @param {function} callback1 第一个回调函数，点击按钮回调该函数
     * @param {object} itemInfo2 第二个 button 的信息，{image, title}, 原生优先使用 image，如没有 image 则使用 title
     * @param {function} callback2 第二个回调函数，点击按钮回调该函数
     */
    setBarDoubleLeftButton (itemInfo1, callback1, itemInfo2, callback2) {
      core.evaluateNative('NativeNavigator', 'setBarDoubleLeftButton', itemInfo1, callback1, itemInfo2, callback2)
    }
  }
}
