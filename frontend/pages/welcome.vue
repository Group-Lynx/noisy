<template>
  <LynMessageCombo :msg-to-display="msgToDisplay" />

  <div class="flex h-screen w-screen flex-col items-center justify-center">
    <Card class="w-1/5 min-w-min">
      <template #header>
        <div class="under-fix flex justify-center">
          <TabMenu :model="actionList" v-model:activeIndex="activeIndex" />
        </div>
      </template>

      <template #content>
        <LynLoginPanel
          v-if="activeIndex === 0"
          v-model:name="name"
          v-model:pswd="pswd"
          @button-click="submitRequest()"
        />
        <LynSignupPanel
          v-else-if="activeIndex === 1"
          v-model:name="name"
          v-model:pswd="pswd"
          v-model:pswdConfirm="pswdConfirm"
          @button-click="submitRequest()"
        />
      </template>
    </Card>
  </div>
</template>

<script lang="ts" setup>
definePageMeta({
  layout: "welcome",
});

// Tab menu
const actionList = ref([
  { label: "登录", icon: "pi pi-sign-in", code: "login" },
  { label: "注册", icon: "pi pi-user-plus", code: "signup" },
]);
const activeIndex = ref(0);

// User input
const name = ref("");
const pswd = ref("");
const pswdConfirm = ref("");

// Messages
const msgToDisplay = ref<
  {
    msg: string;
    lvl: "error" | "warn" | "info" | "success";
  }[]
>([]);

// Button pressed
onKeyStroke("Enter", (e) => {
  if (
    (actionList.value[activeIndex.value].code === "login" &&
      name.value !== "" &&
      pswd.value !== "") ||
    (actionList.value[activeIndex.value].code === "signup" &&
      name.value !== "" &&
      pswd.value !== "" &&
      pswdConfirm.value === pswd.value)
  ) {
    submitRequest();
  }
});
async function submitRequest() {
  const { data, error, status } = await useApiServerData(
    "/auth/" + actionList.value[activeIndex.value].code,
    {
      method: "POST",
      body: {
        name: name.value,
        pswd: pswd.value,
      },
    },
  );

  if (status.value !== "success") {
    msgToDisplay.value.push({
      msg: error.value?.data.err + ": " + error.value?.data.msg,
      lvl: "error",
    });
  } else if (actionList.value[activeIndex.value].code === "signup") {
    msgToDisplay.value.push({
      msg: "注册成功, 请登录",
      lvl: "info",
    });
    activeIndex.value = 0;
    pswd.value = "";
  } else {
    // const userCookie = useCookie("user");
    // userCookie.value = data.value.user;
    // const tokenCookie = useCookie("token");
    // tokenCookie.value = data.value.token;
    await navigateTo("/me");
  }
}
</script>

<style scoped>
.under-fix {
  box-shadow: inset 0em -2px #424b57;
}
</style>
