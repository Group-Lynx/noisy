export function isErrorResp(obj: any): boolean {
  return obj && typeof obj.err === "string" && typeof obj.msg === "string";
}

export default {};
