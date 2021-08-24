<template>
<!-- 导航栏图标 -->
  <span class="item-img" v-if="!isActive">
    <slot name="icon"></slot>
  </span>
  <span class="item-active-img" v-else>
    <slot name="active-icon"></slot>
  </span>
</template>

<script>
	export default {
		name: "NavMenuImg",
		props: {
			// 传入link值
			link: {
				type: String,
				required: true
			}
		},
		computed: {
			// 判断图标是否变色
			isActive() {
				var test = /\d+/;
				if(this.link == "/goodsLists"){
					return this.$route.path == this.link
				}
				else {
					if(!test.test(this.$route.path)) {
						return this.$route.path.lastIndexOf(this.link) !== -1
					}else {
						return this.$route.path == this.link
					}  
				}
			}  
		}
	}
</script>

<style scoped>

</style>