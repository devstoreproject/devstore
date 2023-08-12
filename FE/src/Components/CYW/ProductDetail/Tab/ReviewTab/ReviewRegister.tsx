export default function ReviewRegister() {
  return (
    <form className="flex justify-center pb-4">
      <input
        id="inquiryRegister"
        type="text"
        placeholder="상품에 대한 리뷰를 입력해주세요."
        className="px-5 py-3 border lg:w-full rounded-3xl border-gray w-250"
      />
      <button className="px-24 py-3 ml-2 border border-gray bg-slate-800 text-slate-50 rounded-3xl">
        리뷰 남기기
      </button>
    </form>
  );
}
