// types/index.d.ts
declare global {
  type AuthReq = {
    name: string;
    pswd: string;
  };

  type UserPatchReq = {
    new_name: string | null;
    new_pswd: string | null;
    auth_pswd: string;
  };

  type UserDelReq = {
    auth_pswd: string;
  };

  type ChatPatchReq = {
    new_name: string | null;
  };
}

export {};
