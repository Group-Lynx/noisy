<template>
  <div class="h-screen w-screen">
    <Skeleton width="100%" height="100%"></Skeleton>
  </div>
</template>

<script lang="ts" setup>
definePageMeta({
  layout: "empty",
});
const runtimeConfig = useRuntimeConfig();

const { status } = await useApiServerData("/auth");

switch (status.value) {
  case "success":
    await navigateTo("/me");
    break;
  case "error":
    await navigateTo("/welcome");
    break;
  default:
    break;
}
</script>

<style></style>
