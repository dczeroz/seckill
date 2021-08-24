<template>
	<!-- 导航栏 -->
	<el-row >
		<el-col>
			<el-menu
				:default-active="this.$route.path"
				class="el-menu-vertical-demo"
				background-color="#ededed"
				text-color="#4a4a4a"
				active-text-color="#d33a31"
				:router="true">
				<el-menu-item index="/goodsLists">
					<!-- 父子传值 传入link值 -->
					<nav-menu-img link='/goodsLists'>
						<!-- 导航栏图标 icon为未选中状态 active-icon为选中状态变为红色 -->
						<i class="iconfont icon-seckillshangpin" slot="icon"></i>
						<i class="iconfont icon-seckillshangpin active" slot="active-icon"></i>
					</nav-menu-img>
					<span slot="title" class="item-title">商品列表</span>
				</el-menu-item>
				<el-menu-item index="/myOrder">
					<nav-menu-img link='/myOrder'>
						<i class="iconfont icon-seckillshangpinkuozhan" slot="icon"></i>
						<i class="iconfont icon-seckillshangpinkuozhan active" slot="active-icon"></i>
					</nav-menu-img>
					<span slot="title" class="item-title">我的订单</span>
				</el-menu-item>
			</el-menu>
		</el-col>
	</el-row>                                           
</template>

<script>
	import NavMenuImg from "./nav-menu-img"

	export default {
		components: {
				NavMenuImg
		}
	}

</script>

<style scoped>
	.el-menu-item {
		padding-left: 2rem !important; 
		font-size: 0.8rem !important;
	}
	.item-title {
		margin-left: 1rem;
	}
	.active {
		color: #4a4a4a;
	}
</style>