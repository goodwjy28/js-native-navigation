<template>
  <div class="home">
    <h1>js-native-navigation</h1>
    <h2>路由导航</h2>
    <p>about 页回传的值：{{backInfo}}</p>
    <button @click="nativeNavigation()">原生导航到 H5 about</button>
    <button @click="nativeNavigationUrl()">原生导航到 外连接 H5 Bing</button>
    <button @click="nativeNavigationNative()">原生导航到 原生页 detail</button>
    <h2>设置原生导航条</h2>
    <button @click="setNativeBarTitle()">设置导航条 title</button>
    <button @click="setNativeBarRight()">设置导航条一个 right button</button>
    <button @click="setNativeBarDoubleRight()">设置导航条两个 right button</button>
    <button @click="setNativeBarLeft()">设置导航条一个 left button</button>
    <button @click="setNativeBarDoubleLeft()">设置导航条两个 left button</button>
  </div>
</template>

<script>
// @ is an alias to /src
import HelloWorld from '@/components/HelloWorld.vue'

export default {
  name: 'home',
  components: {
    HelloWorld
  },
  created() {
    this.$nativeNavigator.receiveGoBack(param => {
      this.backInfo = JSON.stringify(param)
    })
    this.$nativeNavigator.setBarTitle('首页')
  },
  methods: {
    nativeNavigation: function () {
      this.$nativeNavigator.push('/about', 'path',{'foo': 'bar'})
    },
    nativeNavigationUrl: function () {
      this.$nativeNavigator.push('https://cn.bing.com', 'url')
    },
    nativeNavigationNative: function () {
      this.$nativeNavigator.push('detail', 'native')
    },
    setNativeBarTitle: function () {
      this.$nativeNavigator.setBarTitle('哇哈哈哈')
    },
    setNativeBarRight: function () {
      var itemInfo = {
        'image': 'https://static.pptstore.net/icons/00/17/c2c0ea0e090a7d715514_s.png',
        'title': '签到'
      }
      this.$nativeNavigator.setBarRightButton(itemInfo, ()=> {
        console.log('点击了签到')
        this.$nativeUi.promptAlert('点击了签到')
      })
    },
    setNativeBarDoubleRight: function () {
      var itemInfo1 = {
        'image': '',
        'title': '签到'
      }
      var itemInfo2 = {
        'image': 'https://static.pptstore.net/icons/00/38/d12e521f14ac86e8bee9_s.png',
        'title': '问题'
      }
      this.$nativeNavigator.setBarDoubleRightButton(itemInfo1, () => {
        console.log('点了签到')
        this.$nativeUi.promptAlert('点了签到')
      }, itemInfo2, () => {
        console.log('点了问题')
        this.$nativeUi.promptAlert('点了问题')
      })
    },
    setNativeBarLeft: function () {
      var itemInfo = {
        'image': '',
        'title': '返回'
      }
      this.$nativeNavigator.setBarLeftButton(itemInfo, () => {
        console.log('点击了返回')
        this.$nativeUi.promptAlert('点击了返回')
      })
    },
    setNativeBarDoubleLeft: function () {
      var itemInfo1 = {
        'image': 'https://static.pptstore.net/icons/00/17/c2c0ea0e090a7d715514_s.png',
        'title': '返回'
      }
      var itemInfo2 = {
        'image': 'https://static.pptstore.net/icons/00/38/d12e521f14ac86e8bee9_s.png',
        'title': '进度'
      }
      this.$nativeNavigator.setBarDoubleLeftButton(itemInfo1, () => {
        console.log('点了返回')
        this.$nativeNavigator.push('/about', 'path',{'foo': 'bar'})
      }, itemInfo2, () => {
        console.log('点了进度')
        this.$nativeUi.promptAlert('点了进度')
      })
    }
  },
  data() {
    return {
      backInfo: '无'
    }
  }
}
</script>
