<script lang="ts">
    import AiIcon from 'virtual:icons/mingcute/ai-fill';
    import PhoneIcon from 'virtual:icons/mingcute/phone-fill';
    import ChatIcon from 'virtual:icons/mingcute/chat-1-fill';
    import ContactsIcon from 'virtual:icons/mingcute/contacts-4-fill';
    import NewsIcon from 'virtual:icons/mingcute/news-2-fill';
    import PostsIcon from 'virtual:icons/mingcute/section-fill';
    import SettingsIcon from 'virtual:icons/mingcute/settings-4-fill';
    import UserIcon from 'virtual:icons/mingcute/user-3-fill';

    import SidebarIcon from '$lib/components/SidebarIcon.svelte';

    let authenticated = true;
    let user = {
        name: "Nolan Freeman"
    };
</script>

<div class="fixed top-0 left-0 h-screen w-16 m-0
            bg-gray-900 text-white shadow-lg
            flex flex-col justify-between">

    <div class="flex flex-col">
        <SidebarIcon title="Ask">
            <AiIcon />
        </SidebarIcon>
        <SidebarIcon title="Calls">
            <PhoneIcon />
        </SidebarIcon>
        <SidebarIcon title="Messages">
            <ChatIcon />
        </SidebarIcon>
        <SidebarIcon title="Contacts">
            <ContactsIcon />
        </SidebarIcon>
        <SidebarIcon title="News">
            <NewsIcon />
        </SidebarIcon>
        <SidebarIcon title="Posts">
            <PostsIcon />
        </SidebarIcon>
    </div>
    <div class="mb-2">
        <SidebarIcon title="Preferences">
            <SettingsIcon />
        </SidebarIcon>
        <SidebarIcon title={user.name}>
            {#if authenticated}
                <img src="/profile.png" alt="User" class="w-12 h-12 rounded-3xl hover:rounded-xl transition-all duration-300 ease-linear " /> 
            {:else}
                <UserIcon />
            {/if}
        </SidebarIcon>
    </div>
</div>