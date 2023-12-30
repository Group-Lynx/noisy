// types/index.d.ts
declare global {
  type ErrorResp = {
    err: string;
    msg: string;
  };

  type UserResp = User;
  type ChatResp = Chat;

  type UserChatsResp = {
    chatrooms: {
      chat: Chat;
      unread: number;
    }[];
  };

  type ChatInfoResp = {
    name: string;
    owner_name: string;
    members: User[];
  };

  type ChatMsgsResp = {
    messages: Message[];
  };

  type SearchUserResp = {
    users: User[];
  };
  type SearchChatResp = {
    chatrooms: Chat[];
  };
}

export {};
