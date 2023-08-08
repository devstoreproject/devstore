const DeliveryReadonly: React.FC = (props) => {
  return (
    <section className="mx-5 py-7 flex border-b border-gray-300">
      <div className="w-36">
        <h2>배송지 정보</h2>
      </div>
      <form className="flex">
        <div className="w-32">
          <p className="h-14 flex items-center">받는 이</p>
          <p className="h-14 flex items-center mt-3">이메일</p>
          <p className="h-14 flex items-center mt-3">휴대전화</p>
          <p className="h-14 flex items-center mt-3">일반전화</p>
          <p className="h-14 flex items-center mt-3">주소</p>
          <p className="h-14 flex items-center mt-3"></p>
          <p className="h-14 flex items-center mt-3">배송요청사항</p>
        </div>
        <div>
          <div className="flex items-center">
            <input
              type="textarea"
              placeholder="텍스트를 입력하세요"
              className="h-14 border border-gray-300 bg-light-gray rounded-full px-10"
              readOnly
            ></input>
          </div>
          <div className="mt-3">
            <input
              type="textarea"
              placeholder="텍스트를 입력하세요"
              className="h-14 border border-gray-300 bg-light-gray rounded-full px-10"
              readOnly
            ></input>
          </div>
          <div className="flex items-center mt-3">
            <div className="relative h-14 w-40 mr-2">
              <ul
                className={`top-0 left-0 border border-gray-300 rounded-full px-10 bg-light-gray w-full absolute z-20 h-14`}
              >
                <li className="h-14 flex items-center">010</li>
              </ul>
            </div>
            -
            <input
              type="textarea"
              placeholder="텍스트를 입력하세요"
              className="h-14 border border-gray-300 bg-light-gray rounded-full px-10 ml-2"
              readOnly
            ></input>
          </div>
          <div className="flex items-center mt-3">
            <div className="relative mr-2 w-40 h-14 select-none">
              <ul
                className={`border border-gray-300 px-10 bg-light-gray tems-center absolute w-full left-0 top-0 z-10 rounded-full h-14`}
              >
                <li className="h-14 flex items-center">02</li>
              </ul>
            </div>
            -
            <input
              type="textarea"
              placeholder="텍스트를 입력하세요"
              className="h-14 border border-gray-300 bg-light-gray rounded-full px-10 ml-2"
              readOnly
            ></input>
          </div>
          <div className="mt-3">
            <input
              type="textarea"
              placeholder="우편번호를 입력하세요"
              className="h-14 border border-gray-300 bg-light-gray rounded-full px-10"
              readOnly
            ></input>
            <button></button>
            <div className="mt-3">
              <input
                type="textarea"
                placeholder="텍스트를 입력하세요"
                className="h-14 border border-gray-300 bg-light-gray rounded-full px-10"
                readOnly
              ></input>
              <input
                type="textarea"
                placeholder="상세주소를 입력하세요"
                className="h-14 border border-gray-300 bg-light-gray rounded-full px-10 ml-2"
                readOnly
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
};

export default DeliveryReadonly;
