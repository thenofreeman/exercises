<script lang="ts">
    let { children, title } = $props();
</script>

<div class="relative flex items-center justify-center
            h-12 w-12 mt-2 bt-2 mx-auto
            rounded-3xl
            bg-gray-800 text-white
            hover:bg-violet-700 hover:text-white hover:rounded-xl
            transition-all duration-300 ease-linear 
            cursor-pointer
            group">
    {@render children()}

    <span class="absolute w-auto p-2 m-2 min-2-max left-14
                rounded-md shadow-md
                text-white bg-gray-800
                text-xs font-bold
                transition-all duration-100 scale-0 origin-left
                group-hover:scale-100">
        {title}
    </span>
</div>