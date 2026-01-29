<script lang="ts">
	import '../app.css';

	import Sidebar from "$lib/components/Sidebar.svelte";

    let { children } = $props();

</script>

<div class="h-screen w-screen">
	<!-- Actually rendered in opposite order -->
	<!-- Done so hover elements don't get hidden -->
	{@render children()}
	<Sidebar /> 
</div>