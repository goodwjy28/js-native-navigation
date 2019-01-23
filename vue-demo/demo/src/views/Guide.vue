<template>
  <div class="guide">
    <h1>This is an guide page</h1>
    <p>About 页传来的值：{{content}}</p>
    <button @click="nativeNavigation()">native 完成 返回上一页</button>
    <button @click="nativeNavigationRoot()">native 完成 首页</button>
  </div>
</template>
<script>
export default {
  created () {
    this.$nativeNavigator.getRouteContext((param) => {
      this.content = JSON.stringify(param)
    })
    this.$nativeNavigator.setBarTitle('指南')
  },
  methods: {
    nativeNavigation: function () {
      this.$nativeNavigator.goBack({ 'baz': '哼' })
    },
    nativeNavigationRoot: function () {
      this.$nativeNavigator.goBackRoot()
    }
  },
  data () {
    return {
      content: '无'
    }
  }
}
</script>
