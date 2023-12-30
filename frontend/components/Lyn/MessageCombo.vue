<template>
  <div class="absolute z-50 flex w-full flex-col items-center justify-center">
    <Message :severity="msg.lvl" v-for="msg in msgs">{{ msg.msg }}</Message>
  </div>
</template>

<script lang="ts" setup>
const props = defineProps<{
  msgToDisplay: {
    msg: string;
    lvl: "error" | "warn" | "info" | "success";
  }[];
}>();

const msgs = ref(props.msgToDisplay);
</script>

<style></style>
