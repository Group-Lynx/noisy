// types/index.d.ts
declare global {
  type User = {
    name: string;
  };
  type Chat = {
    name: string;
  };
  type DateTime = {
    year: number;
    month: number;
    day: number;
    hour: number;
    minute: number;
  };
  type Message = {
    sender: string;
    content: string;
    sent_date: DateTime;
  };
}

export {};
