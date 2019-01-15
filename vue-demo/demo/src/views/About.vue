<template>
  <div class="about">
    <h1>This is an about page</h1>
    <p>Home 传来的值：{{content}}</p>
    <p>Guide 回传的值：{{backInfo}}</p>
    <button @click="nativeNavigation()">原生导航返回上一页，回传值给 Home</button>
    <button @click="nativeNavigationGoto()">原生导航到 H5 Guide</button>
  </div>
</template>
<script>
export default {
  created() {
    var self = this
    this.$nativeNavigator.getRouteContext(function (param) {
      self.content = JSON.stringify(param)
    })
    this.$nativeNavigator.receiveGoBack(function (param) {
      self.backInfo = JSON.stringify(param)
    })
    this.$nativeNavigator.setBarTitle('关于')
  },
  methods: {
    nativeNavigation: function () {
      this.$nativeNavigator.goBack({'baz': '哈'})
    },
    nativeNavigationGoto: function () {
      this.$nativeNavigator.push('/haha/guide', 'path', {'foo': [1,2,3]})
    }
  },
  data() {
    return {
      content: '无',
      backInfo: '无'
    }
  }
}
</script>
