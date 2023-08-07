export default function InquiryRegister() {
  return (
    <form className="flex justify-center pb-4">
      <input
        id="inquiryRegister"
        type="text"
        placeholder="문의 사항을 입력해주세요."
        className="px-5 py-3 border lg:w-full rounded-3xl border-gray w-250"
      />
      <button className="px-24 py-3 ml-2 border border-gray bg-slate-800 text-slate-50 rounded-3xl">
        문의하기
      </button>
    </form>
  );
}
