<template>
  <div class="no-scrollbar w-full overflow-x-auto" ref="scrollRef">
    <div
      :class="
        !arrivedState.top &&
        'fixed h-4 w-24 overflow-hidden rounded-b-3xl bg-gradient-to-b from-blue-900 to-transparent '
      "
    ></div>
    <div
      :class="
        !arrivedState.bottom &&
        'fixed bottom-0 h-4 w-24 overflow-hidden rounded-t-3xl bg-gradient-to-t from-blue-900 to-transparent '
      "
    ></div>
    <div class="flex w-full flex-col items-center">
      <div v-for="warpped in chatrooms">
        <div class="z-40 m-2">
          <NuxtLink :to="'/chat/'.concat(warpped.chat.name)">
            <Avatar
              :label="nameInitial(warpped.chat.name)"
              size="xlarge"
              class="overflow-clip rounded-[32px] bg-blue-400 text-gray-950 transition-all duration-100 hover:rounded-xl"
              :class="isCurrentChat(warpped.chat.name) && '!rounded-xl'"
            />
          </NuxtLink>
        </div>
      </div>
    </div>
  </div>
</template>

<script lang="ts" setup>
const route = useRoute();
const chatrooms = useState<
  {
    chat: Chat;
    unread: number;
  }[]
>("chatrooms");

// Styling support: Avatar display
function isCurrentChat(chatName: string): boolean {
  return (
    route.path.split("/")[1] == "chat" && route.path.split("/")[2] == chatName
  );
}
function nameInitial(chatName: string): string {
  let nameParts = chatName.split(/\s+/).filter(Boolean);

  if (nameParts.length == 1) {
    return nameParts[0][0].toUpperCase();
  } else {
    return nameParts[0][0].toUpperCase().concat(nameParts[1][0].toUpperCase());
  }
}

// Styling support: Scroll hint
const scrollRef = ref();
const { arrivedState } = useScroll(scrollRef);
</script>

<style scoped>
.no-scrollbar::-webkit-scrollbar {
  display: none;
}
.no-scrollbar {
  -ms-overflow-style: none; /* IE and Edge */
  scrollbar-width: none; /* Firefox */
}
</style>
