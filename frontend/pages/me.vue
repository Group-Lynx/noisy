<template>
  <LynMessageCombo :msg-to-display="msgToDisplay" />

  <div class="flex h-full w-full flex-col items-center overflow-auto">
    <span class="mt-10 w-5/6">
      <Card>
        <template #content>
          <div class="flex">
            <Avatar class="relative mx-10 h-32 w-32 flex-shrink-0" />
            <p
              class="flex flex-shrink-0 items-center justify-center text-6xl font-semibold"
            >
              {{ user.name }}
            </p>
            <span class="mr-10 flex w-full flex-grow items-center justify-end">
              <Button @click="logout()">登出</Button>
            </span>
          </div>
        </template>
      </Card>
    </span>

    <span class="mt-10 w-5/6">
      <Card>
        <template #content>
          <div class="m-8 ml-8 flex items-center">
            <div class="mx-8 w-16 text-xl">用户名</div>
            <InputText :placeholder="user.name" disabled />
          </div>

          <div class="m-8 ml-8 flex items-center">
            <div class="mx-8 w-16 text-xl">密码</div>
            <Password v-model="new_pswd" :feedback="false" />
          </div>
        </template>
        <template #footer>
          <div class="mb-4 mr-8 flex justify-end">
            <Button
              @click="patchDialogVisible = true"
              :disabled="new_pswd === ''"
              >提交变更</Button
            >
          </div>
        </template>
      </Card>

      <Dialog v-model:visible="patchDialogVisible" modal header="认证密码">
        <span class="m-2">
          <Password v-model="auth_pswd" :feedback="false" />
        </span>
        <span class="m-2">
          <Button @click="sendPatchRequest()">发送</Button>
        </span>
      </Dialog>
    </span>
  </div>
</template>

<script lang="ts" setup>
const { data, error, status } = await useApiServerData("/auth");
if (status.value !== "success") {
  await navigateTo("/welcome");
}
const user = ref<User>(data.value);

const new_pswd = ref("");
const auth_pswd = ref("");

// Messages
const msgToDisplay = ref<
  {
    msg: string;
    lvl: "error" | "warn" | "info" | "success";
  }[]
>([]);

// Update User Info
const patchDialogVisible = ref(false);
async function sendPatchRequest() {
  console.log({
    new_name: null,
    new_pswd: new_pswd,
    auth_pswd: auth_pswd,
  });

  const { data, status } = await useApiServerData("/user/" + user.value.name, {
    method: "PATCH",
    body: {
      new_name: user.value.name,
      new_pswd: new_pswd,
      auth_pswd: auth_pswd,
    },
  });
  console.log("data", data.value);

  if (status.value !== "success") {
    msgToDisplay.value.push({
      msg: error.value?.data.err + ": " + error.value?.data.msg,
      lvl: "error",
    });
  } else {
    msgToDisplay.value.push({
      msg: "修改成功",
      lvl: "success",
    });
  }
  patchDialogVisible.value = false;
  auth_pswd.value = "";
}

// Logout
async function logout() {
  const userCookie = useCookie("user");
  userCookie.value = null;
  const tokenCookie = useCookie("token");
  tokenCookie.value = null;
  await navigateTo("/welcome");
}
</script>

<style></style>
