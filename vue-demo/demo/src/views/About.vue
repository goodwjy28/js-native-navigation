<template>
  <div class="about">
    <h1>This is an about page</h1>
    <p>上一页传来的值：{{content}}</p>
    <button @click="nativeNavigation()">native 完成 返回上一页</button>
    <button @click="nativeNavigationGoto()">去 guide 页</button>
  </div>
</template>
<script>
export default {
  created() {
    var self = this
    this.$nativeNavigator.receiveGoto(function (param) {
      self.content = JSON.stringify(param)
    })
    this.$nativeNavigator.receiveGoBack(function (param) {
      self.backInfo = JSON.stringify(param)
    })
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
