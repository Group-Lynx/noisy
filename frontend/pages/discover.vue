<template>
  <LynMessageCombo :msg-to-display="msgToDisplay" />

  <Dialog v-model:visible="dialogVisible" modal header="创建聊天室">
    <div class="flex flex-col items-center justify-center">
      <span class="m-4">
        <InputText v-model:modelValue="chatToCreate" />
      </span>
      <Button @click="createChat()">创建</Button>
    </div>
  </Dialog>
  <div class="flex flex-grow flex-col">
    <div
      class="m-5 flex h-24 min-h-[6rem] items-center justify-center rounded-xl bg-gray-800"
    >
      <span class="mx-2">
        <Dropdown
          :options="actions"
          v-model="activeAction"
          optionLabel="name"
        />
      </span>

      <span class="mx-2">
        <InputText v-model="searchPattern" />
      </span>

      <span class="mx-2">
        <Button class="pi pi-search" @click="search()"> 搜索 </Button>
      </span>

      <span class="mx-2">
        <Button
          class="pi pi-plus"
          @click="dialogVisible = true"
          v-if="activeAction.code === 'chat'"
        >
          创建
        </Button>
      </span>
    </div>

    <div class="flex h-full flex-col items-center overflow-y-auto">
      <div v-for="result in searchResult" class="m-4 w-3/4">
        <Card>
          <template #content>
            <div class="flex items-center">
              <span class="mx-8">
                <Avatar size="xlarge" :label="nameInitial(result.name)" />
              </span>
              <p class="text-2xl">{{ result.name }}</p>
              <span class="mr-16 flex flex-grow justify-end">
                <Button
                  v-if="activeAction.code === 'chat'"
                  @click="joinChat(result.name)"
                  :disabled="
                    chatrooms
                      .map((chat) => chat.chat.name)
                      .includes(result.name)
                  "
                >
                  {{
                    chatrooms
                      .map((chat) => chat.chat.name)
                      .includes(result.name)
                      ? "已加入"
                      : "加入"
                  }}
                </Button>
              </span>
            </div>
          </template>
        </Card>
      </div>
    </div>
  </div>
</template>

<script lang="ts" setup>
const emit = defineEmits<{
  joinChat: [name: string];
}>();

// Messages
const msgToDisplay = ref<
  {
    msg: string;
    lvl: "error" | "warn" | "info" | "success";
  }[]
>([]);

// Chatrooms
const chatrooms = useState<
  {
    chat: Chat;
    unread: number;
  }[]
>("chatrooms");

// Search pattern
const searchPattern = ref("");

// Search domain
const activeAction = ref({ name: "聊天室", code: "chat" });
const actions = ref([
  { name: "聊天室", code: "chat" },
  { name: "用户", code: "user" },
]);
watch(activeAction, () => {
  searchResult.value = null;
});

// Search Result
const searchResult = ref();
async function search() {
  const { data, error, status } = await useApiServerData(
    "/search/" + activeAction.value.code + "?name=" + searchPattern.value,
  );

  if (status.value !== "success") {
    msgToDisplay.value.push({
      msg: error.value?.data.err + ": " + error.value?.data.msg,
      lvl: "error",
    });
  }

  switch (activeAction.value.code) {
    case "user":
      searchResult.value = data.value.users;
      break;
    case "chat":
      searchResult.value = data.value.chatrooms;
      break;
  }
}

// Join Chat
async function joinChat(name: string) {
  const { data, error, status } = await useApiServerData(
    "/chat/" + name + "/join",
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
      msg: "加入成功",
      lvl: "success",
    });
    emit("joinChat", name);
  }
}

// Create Chat
const chatToCreate = ref("");
const dialogVisible = ref(false);
async function createChat() {
  console.log(chatToCreate.value);
  const { data, error, status } = await useApiServerData("/chat", {
    method: "POST",
    body: {
      name: chatToCreate.value,
    },
  });
  if (status.value !== "success") {
    msgToDisplay.value.push({
      msg: error.value?.data.err + ": " + error.value?.data.msg,
      lvl: "error",
    });
  } else {
    msgToDisplay.value.push({
      msg: "创建成功",
      lvl: "success",
    });
    emit("joinChat", chatToCreate.value);
    dialogVisible.value = false;
  }
}

// Styling support: Avatar display
function nameInitial(chatName: string): string {
  let nameParts = chatName.split(/\s+/).filter(Boolean);

  if (nameParts.length == 1) {
    return nameParts[0][0].toUpperCase();
  } else {
    return nameParts[0][0].toUpperCase().concat(nameParts[1][0].toUpperCase());
  }
}
</script>

<style></style>
