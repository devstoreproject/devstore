export default function Delivery() {
  return (
    <section className="mx-5 py-7 flex">
      <div className="w-36">
        <h2>배송지 정보 입력</h2>
        <p>*은 필수 입력입니다.</p>
      </div>
      <form className="flex">
        <div className="w-32">
          <p className="h-14 flex items-center">받는 이*</p>
          <p className="h-14 flex items-center mt-3">이메일*</p>
          <p className="h-14 flex items-center mt-3">휴대전화*</p>
          <p className="h-14 flex items-center mt-3">일반전화</p>
          <p className="h-14 flex items-center mt-3">주소</p>
          <p className="h-14 flex items-center mt-3"></p>
          <p className="h-14 flex items-center mt-3">배송요청사항</p>
        </div>
        <div>
          <div className="flex items-center">
            <input
              type="textarea"
              defaultValue="텍스트를 입력하세요"
              className="h-14 border border-gray-300 bg-gray-100 rounded-full px-10"
            ></input>
            <input
              type="checkbox"
              id="orderer"
              className="w-5 h-5 mx-3 border-gray-300"
            ></input>
            <label htmlFor="orderer" className="cursor-default">
              주문자와 동일
            </label>
          </div>
          <div className="mt-3">
            <input
              type="textarea"
              defaultValue="텍스트를 입력하세요"
              className="h-14 border border-gray-300 bg-gray-100 rounded-full px-10"
            ></input>
          </div>
          <div className="flex items-center mt-3">
            <ul className="h-14 border border-gray-300 rounded-full px-10 bg-light-gray mr-2 w-40 flex items-center">
              <li>010</li>
            </ul>
            -
            <input
              type="textarea"
              defaultValue="텍스트를 입력하세요"
              className="h-14 border border-gray-300 bg-gray-100 rounded-full px-10 ml-2"
            ></input>
          </div>
          <div className="flex items-center mt-3">
            <ul className="h-14 border border-gray-300 rounded-full px-10 bg-light-gray mr-2 w-40 flex items-center">
              <li>02</li>
            </ul>
            -
            <input
              type="textarea"
              defaultValue="텍스트를 입력하세요"
              className="h-14 border border-gray-300 bg-gray-100 rounded-full px-10 ml-2"
            ></input>
          </div>
          <div className="mt-3">
            <input
              type="textarea"
              defaultValue="우편번호를 입력하세요"
              className="h-14 border border-gray-300 bg-gray-100 rounded-full px-10"
            ></input>
            <button></button>
            <div className="mt-3">
              <input
                type="textarea"
                defaultValue="텍스트를 입력하세요"
                className="h-14 border border-gray-300 bg-gray-100 rounded-full px-10"
              ></input>
              <input
                type="textarea"
                defaultValue="상세주소를 입력하세요"
                className="h-14 border border-gray-300 bg-gray-100 rounded-full px-10 ml-2"
              ></input>
            </div>
          </div>
          <ul className="h-14 border border-gray-300 rounded-full px-10 bg-light-gray mr-2 flex items-center mt-3">
            <li>빠른 배송 부탁드립니다.</li>
          </ul>
        </div>
      </form>
    </section>
  );
}
