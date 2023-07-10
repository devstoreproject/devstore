export default function Discount() {
  return (
    <section className="mx-5 py-7 border-t border-gray-300">
      <div>
        <div className="flex items-center">
          <h2 className="w-36">할인</h2>
          <p className="w-32">쿠폰 적용</p>
          <ul className="h-14 border border-gray-300 rounded-full px-10 bg-light-gray mr-2 flex items-center">
            <li>
              18,000 할인 / 50,000 이상 구매 시 5% 할인 (신규혜택) ~2023.06.16
            </li>
          </ul>
        </div>
        <div className="flex items-center ml-36">
          <p className="w-32">적립금 적용</p>
          <div className="flex mt-3">
            <input
              type="textarea"
              className="h-14 border border-gray-300 rounded-full px-10 bg-light-gray mr-2 flex items-center"
            ></input>
            <input
              type="textarea"
              className="h-14 border border-gray-300 rounded-full px-10 bg-light-gray mr-2 flex items-center"
            ></input>
          </div>
        </div>
        <p className="h-14 flex items-center ml-36">700 할인 적용</p>
      </div>
    </section>
  );
}
