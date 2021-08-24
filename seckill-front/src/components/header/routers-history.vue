<template>
	<!-- 头部功能键 -->
  <div class="routers-hisory" >
		<!-- 路由后退 -->
		<div class="back" @click="back">
			<i class="iconfont icon-seckillback"></i>
		</div>
		<!-- 路由前进 -->
		<div class="forward" @click="forward">
			<i class="iconfont icon-seckillforward"></i>
		</div>
  </div>
</template>

<script>
	export default {
		methods: {
			back() {
					this.$router.back()
			},
			forward() {
					this.$router.forward()
			}
		}
	}
</script>

<style scoped>
	.routers-hisory {
		display: flex;
		margin-left: 4rem;
    margin-top: 0.8rem;
	}
	.back,
	.forward {
		width: 1.4rem;
		height: 1.4rem;
		border-radius: 50%;
		margin-left: 2rem;
	}
	.icon-seckillback,
	.icon-seckillforward {
		font-size: 1.2rem;
	}
	.back:hover ,
	.forward:hover {
		background-color: rgb(214, 211, 211);
	}
</style>