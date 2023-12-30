<template>
  <div class="flex flex-col items-center">
    <div class="m-5">
      <span class="p-float-label">
        <InputText
          :model-value="name"
          @update:model-value="$emit('update:name', $event)"
          id="username"
          type="text"
        />
        <label for="username"> 用户名 </label>
      </span>
    </div>

    <div class="m-5">
      <span class="p-float-label">
        <Password
          :model-value="pswd"
          @update:model-value="$emit('update:pswd', $event)"
          :feedback="false"
          inputId="password"
        />
        <label for="password"> 用户密码 </label>
      </span>
    </div>

    <div class="mt-5">
      <Button :disabled="someNotFilled()" @click="$emit('button-click')">
        登录
      </Button>
    </div>
  </div>
</template>

<script lang="ts" setup>
const props = defineProps({
  name: String,
  pswd: String,
});
defineEmits(["update:name", "update:pswd", "button-click"]);

// Validating Input
function someNotFilled(): boolean {
  return props.name === "" || props.pswd === "";
}
</script>

<style></style>
