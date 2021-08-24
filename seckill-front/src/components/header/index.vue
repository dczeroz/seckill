<template>
  <div class="header">
		<icon></icon>
		<routers-history></routers-history>
  </div>
</template>

<script>
	import Icon from './Icon'
	import routersHistory from './routers-history'
	export default {
		components: {
			Icon,
			routersHistory,
		}
	}
</script>

<style scoped>
	.header {
		display: flex;
		height: 3rem;
		background-color: #f9f9f9;
	}
</style>
