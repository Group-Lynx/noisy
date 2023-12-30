<template>
  <LynMessageCombo :msg-to-display="msgToDisplay" />

  <Sidebar v-model:visible="detailVisible">
    <template #header>
      <Avatar :label="nameInitial(chatName)" size="large" />
      <p>{{ chatName }}</p>
    </template>

    <template #default>
      <div class="max-h-96 overflow-y-auto">
        <div v-for="user in chatMembers" class="mx-2 my-4">
          <Avatar :label="nameInitial(user.name)" />
          <span class="ml-4">{{ user.name }}</span>
        </div>
      </div>

      <div class="m-4 flex justify-center">
        <Button @click="leaveChat(chatName)">离开</Button>
      </div>
    </template>
  </Sidebar>
  <div class="flex flex-grow flex-col">
    <div class="z-40 flex-grow overflow-y-auto bg-gray-900">
      <div v-for="msg in messages">
        <LynChatBubble :message="msg" />
      </div>
      <span ref="scrollAnchor"></span>
    </div>

    <div
      class="z-50 flex h-32 min-h-[128px] items-center justify-center bg-gray-800"
    >
      <Button icon="pi pi-bars" class="mx-2" @click="showDetail()"></Button>
      <InputText class="mx-2 w-3/4" v-model="msgToSend" />
      <Button
        class="mx-2"
        @click="sendMsg()"
        :disabled="msgToSend === '' && client.connected"
        >{{ (() => client.connected) ? "发送" : "等待连接" }}</Button
      >
    </div>
  </div>
</template>

<script lang="ts" setup>
import { Client } from "@stomp/stompjs";

const runtimeConfig = useRuntimeConfig();
const route = useRoute();
let routeParts = route.path.split("/");
let chatName = routeParts[routeParts.length - 1];
const chatrooms = useState<
  {
    chat: Chat;
    unread: number;
  }[]
>("chatrooms");

// Messages
const msgToDisplay = ref<
  {
    msg: string;
    lvl: "error" | "warn" | "info" | "success";
  }[]
>([]);

const { data: warppedUser, status: authed } = await useApiServerData("/auth");
if (authed.value !== "success") {
  await navigateTo("/welcome");
}
const user_name = warppedUser.value.name;

// Chat messages
const { data, status } = await useApiServerData<ChatMsgsResp | ErrorResp>(
  "/chat/" + chatName + "/msg",
);
if (status.value !== "success") {
  await navigateTo("/welcome");
}
const messages = ref((data.value as ChatMsgsResp).messages);

// Styling support: Avatar display
function nameInitial(chatName: string): string {
  let nameParts = chatName.split(/\s+/).filter(Boolean);
  if (nameParts.length == 1) {
    return nameParts[0][0].toUpperCase();
  } else {
    return nameParts[0][0].toUpperCase().concat(nameParts[1][0].toUpperCase());
  }
}

// Display Chat Detail
const detailVisible = ref(false);
const chatMembers = ref();
async function showDetail() {
  detailVisible.value = true;
  const { data } = await useApiServerData("/chat/" + chatName);
  chatMembers.value = data.value.user;
}

// Leave Chat
async function leaveChat(name: string) {
  const { data, error, status } = await useApiServerData(
    "/chat/" + name + "/leave",
    {
      method: "POST",
    },
  );
  if (status.value !== "success") {
    msgToDisplay.value.push({
      msg: error.value?.data.err + ": " + error.value?.data.msg,
      lvl: "error",
    });
  } else {
    msgToDisplay.value.push({
      msg: "离开成功",
      lvl: "success",
    });
    chatrooms.value = chatrooms.value.filter(
      (warpped) => warpped.chat.name !== name,
    );
    await navigateTo("/me");
  }
}

// Send message: Socket client
const client = new Client({
  brokerURL: runtimeConfig.public.socketEndpoint,
  onConnect(frame) {
    client.subscribe("/broadcast/" + chatName, (msg) => {
      messages.value.push(JSON.parse(msg.body));
      setTimeout(() => {
        scrollAnchor.value.scrollIntoView();
      }, 10);
    });
    scrollAnchor.value.scrollIntoView();
  },
});
onMounted(() => client.activate());
onUnmounted(() => client.deactivate());

// Send message: action
const msgToSend = ref("");
function sendMsg() {
  client.publish({
    destination: "/listen/".concat(chatName),
    body: JSON.stringify({
      sender: user_name,
      content: msgToSend.value,
    }),
  });
  msgToSend.value = "";
}

// Auto Scroll
const scrollAnchor = ref();
</script>

<style></style>
