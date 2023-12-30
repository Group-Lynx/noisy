<template>
  <div class="mx-8 my-4 flex w-fit max-w-7xl">
    <Card>
      <template #title>
        <Avatar
          shape="circle"
          size="large"
          :label="nameInitial(message.sender)"
          :style="'background-color:' + mapToColor(message.sender)"
        />
        <span class="ml-4">{{ message.sender }}</span>
      </template>
      <template #subtitle>
        {{ formatDate(message.sent_date) }}
      </template>
      <template #content>{{ message.content }}</template>
    </Card>
  </div>
</template>

<script lang="ts" setup>
defineProps<{
  message: {
    sender: string;
    content: string;
    sent_date: {
      year: number;
      month: number;
      day: number;
      hour: number;
      minute: number;
    };
  };
}>();

// Styling support: Avatar display
function nameInitial(userName: string): string {
  let nameParts = userName.split(/\s+/).filter(Boolean);
  if (nameParts.length == 1) {
    return nameParts[0][0].toUpperCase();
  } else {
    return nameParts[0][0].toUpperCase().concat(nameParts[1][0].toUpperCase());
  }
}

// Styling support: Date formating
function formatDate(date: DateTime): string {
  return (
    date.year +
    " 年 " +
    date.month +
    " 月 " +
    date.day +
    " 日 " +
    date.hour +
    ":" +
    (date.minute < 10 ? "0" + date.minute : date.minute)
  );
}
</script>

<style></style>
