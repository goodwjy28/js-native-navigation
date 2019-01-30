# js-native-navigation
使用原生导航替代浏览器导航 Web 页

> 就目前来看，基于 H5 的客户端混合开发还是主流方案，如果 H5 页面间的跳转可以像原生页面间的跳转一样，那么体验会好很多，招商银行的手机银行和掌上生活客户端，大部分页面都是 H5，而他们就采用这种结合原生导航的方式做的跳转，体验相比其他手机银行客户端要好很多，省去了处理 H5 返回刷新页面的问题。

js-native-navigation 的实现是基于 js 和 native 之间的交互，使用的是工具库是 [js-native-bridge](https://github.com/al-liu/js-native-bridge)。

使用终端 cd 到 vue demo 根目录，运行命令 `npm run serve`，运行成功后可看到访问地址，如：`http:192.168.1.122:8080`，iOS 和 Android demo 中的 WebView 加载该地址，便可看到 demo 效果。

以下介绍 js 中如何使用原生导航，iOS 和 Android 原生客户端的实现参考客户端 demo 代码。

## 导航
### 基本导航方法
#### 跳转到下一页

```js
// Home.vue 
this.$nativeNavigator.push('/about', 'path',{'foo': 'bar'})
```
第一个参数是跳转的路径，第二个参数是路径的类型，第三个参数就是传递到下一页的值。

#### 接收前一页传来的值

```js
// About.vue
this.$nativeNavigator.getRouteContext(param => {
  this.content = JSON.stringify(param)
})
```
上一页传递过来的值，会被保存在原生页面对象中，需要时直接获取。

#### 返回上一页

```js
// About.vue
this.$nativeNavigator.goBack({'baz': '哈'})
```

#### 接收返回页回传的值

```js
this.$nativeNavigator.receiveGoBack(param => {
  this.backInfo = JSON.stringify(param)
})
```

#### 返回根页面

```js
this.$nativeNavigator.goBackRoot()
```

### 跳转类型
`this.$nativeNavigator` 的跳转方法支持三种，第一种是 H5 页间的跳转，第二种是打开外部链接，第三种是打开原生页。

#### 打开外部链接：

```js
this.$nativeNavigator.push('https://cn.bing.com', 'url')
```

#### 打开原生页面：

```js
this.$nativeNavigator.push('detail', 'native')
```
第一个参数 `detail` 是原生页的路由名，要导航的原生页面需要在原生代码中注册路由。


## 设置导航条
使用原生的导航条，体验要比 H5 的更好，但是需要提供给 H5 一些设置导航条的方法，如设置标题和左右两边的按钮。
以下只是提供部分参考案例，项目中要根据实际情况做定制。

### 设置标题

```js
this.$nativeNavigator.setBarTitle('首页')
```

### 设置导航条右边一个按钮

```js
var itemInfo = {
    'image': 'https://static.pptstore.net/icons/00/17/c2c0ea0e090a7d715514_s.png',
    'title': '签到'
}
this.$nativeNavigator.setBarRightButton(itemInfo, function () {
    console.log('点击了签到')
})
```

### 设置导航条右边两个按钮

```js
var itemInfo1 = {
    'image': '',
    'title': '签到'
}
var itemInfo2 = {
    'image': 'https://static.pptstore.net/icons/00/38/d12e521f14ac86e8bee9_s.png',
    'title': '问题'
}
this.$nativeNavigator.setBarDoubleRightButton(itemInfo1, function () {
    console.log('点了签到')
}, itemInfo2, function () {
    console.log('点了问题')
})
```

### 设置导航条左边一个按钮

```js
  var itemInfo = {
    'image': '',
    'title': '返回'
  }
  this.$nativeNavigator.setBarLeftButton(itemInfo, function () {
    console.log('点击了返回')
  })
```

### 设置导航条左边两个按钮

```js
  var itemInfo1 = {
    'image': 'https://static.pptstore.net/icons/00/17/c2c0ea0e090a7d715514_s.png',
    'title': '返回'
  }
  var itemInfo2 = {
    'image': 'https://static.pptstore.net/icons/00/38/d12e521f14ac86e8bee9_s.png',
    'title': '进度'
  }
  this.$nativeNavigator.setBarDoubleLeftButton(itemInfo1, function () {
    console.log('点了返回')
  }, itemInfo2, function () {
    console.log('点了进度')
  })
```
