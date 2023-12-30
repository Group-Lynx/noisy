<template>
  <div class="flex h-screen w-screen">
    <div
      class="z-50 flex w-24 min-w-[96px] flex-col items-center bg-gray-900 pt-2 shadow-xl shadow-blue-700"
    >
      <div class="m-2">
        <NuxtLink to="/me">
          <Avatar
            icon="pi pi-user"
            size="xlarge"
            class="rounded-[32px] bg-blue-400 text-gray-950 transition-all duration-100 hover:rounded-xl"
            :class="tabPosition === 'ME' && '!rounded-xl'"
          />
        </NuxtLink>
      </div>

      <div class="m-2">
        <NuxtLink to="/discover">
          <Avatar
            icon="pi pi-search"
            size="xlarge"
            class="rounded-[32px] bg-blue-400 text-gray-950 transition-all duration-100 hover:rounded-xl"
            :class="tabPosition === 'DISCOVER' && '!rounded-xl'"
          />
        </NuxtLink>
      </div>

      <Divider />

      <LynChatsList />
    </div>
    <div class="flex flex-grow">
      <slot />
    </div>
  </div>
</template>

<script lang="ts" setup>
import Discover from "~/pages/discover.vue";

const route = useRoute();

// Styling support
const tabPosition: ComputedRef<"ME" | "DISCOVER" | "NEW" | "CHAT"> = computed(
  () => {
    let position = route.path.split("/")[1];
    switch (position) {
      case "me":
        return "ME";
      case "discover":
        return "DISCOVER";
      case "new":
        return "NEW";
      default:
        return "CHAT";
    }
  },
);

// User chats
const { data, error, status } = await useApiServerData("/chat");
if (status.value !== "success") {
  await navigateTo("/welcome");
}

// Chatroom management
const chatrooms = useState<
  {
    chat: Chat;
    unread: number;
  }[]
>("chatrooms", () => data.value.chatrooms);
</script>

<style></style>
