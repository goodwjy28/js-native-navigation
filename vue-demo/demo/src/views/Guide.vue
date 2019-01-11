<template>
  <div class="guide">
    <h1>This is an guide page</h1>
    <p>home 页传来的值：{{content}}</p>
    <p>guide 页传回来的值：{{backInfo}}</p>
    <button @click="nativeNavigation()">native 完成 返回上一页</button>
    <button @click="nativeNavigationRoot()">native 完成 首页</button>
  </div>
</template>
<script>
export default {
  created() {
    var self = this
    this.$nativeNavigator.receiveGoto(function (param) {
      self.content = JSON.stringify(param)
    })
  },
  methods: {
    nativeNavigation: function () {
      this.$nativeNavigator.goBack({'baz': '哼'})
    },
    nativeNavigationRoot: function () {
      this.$nativeNavigator.goBackRoot()
    }
  },
  data() {
    return {
      content: 'hello world'
    }
  }
}
</script>
