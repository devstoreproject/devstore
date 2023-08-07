import { useState } from 'react';

function DeliverForm() {
  const [mobileOpen, setMobileOpen] = useState(false);
  const [phoneOpen, setPhoneOpen] = useState(false);
  const [requestOpen, setRequestOpen] = useState(false);
  const handleMobileOpen = () => {
    setMobileOpen(!mobileOpen);
  };
  const handlePhoneOpen = () => {
    setPhoneOpen(!phoneOpen);
  };
  const handleRequestOpen = () => {
    setRequestOpen(!requestOpen);
  };
  return (
    <div>
      <div className="flex items-center">
        <input
          type="textarea"
          placeholder="텍스트를 입력하세요"
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
          placeholder="텍스트를 입력하세요"
          className="h-14 border border-gray-300 bg-gray-100 rounded-full px-10"
        ></input>
      </div>
      <div className="flex items-center mt-3">
        <div className="relative h-14 w-40 mr-2">
          <ul
            className={`top-0 left-0 border border-gray-300 rounded-full px-10 bg-light-gray w-full absolute z-20 ${
              mobileOpen ? 'rounded-xl' : 'overflow-hidden rounded-full h-14'
            }`}
            onClick={handleMobileOpen}
          >
            <li className="h-14 flex items-center">010</li>
            <li className="h-14 flex items-center">010</li>
          </ul>
        </div>
        -
        <input
          type="textarea"
          placeholder="텍스트를 입력하세요"
          className="h-14 border border-gray-300 bg-gray-100 rounded-full px-10 ml-2"
        ></input>
      </div>
      <div className="flex items-center mt-3">
        <div className="relative mr-2 w-40 h-14 select-none">
          <ul
            className={`border border-gray-300 px-10 bg-light-gray tems-center absolute w-full left-0 top-0 z-10 ${
              phoneOpen ? 'rounded-xl' : 'overflow-hidden rounded-full h-14'
            }`}
            onClick={handlePhoneOpen}
          >
            <li className="h-14 flex items-center">02</li>
            <li className="h-14 flex items-center">02</li>
          </ul>
        </div>
        -
        <input
          type="textarea"
          placeholder="텍스트를 입력하세요"
          className="h-14 border border-gray-300 bg-gray-100 rounded-full px-10 ml-2"
        ></input>
      </div>
      <div className="mt-3">
        <input
          type="textarea"
          placeholder="우편번호를 입력하세요"
          className="h-14 border border-gray-300 bg-gray-100 rounded-full px-10"
        ></input>
        <button></button>
        <div className="mt-3">
          <input
            type="textarea"
            placeholder="텍스트를 입력하세요"
            className="h-14 border border-gray-300 bg-gray-100 rounded-full px-10"
          ></input>
          <input
            type="textarea"
            placeholder="상세주소를 입력하세요"
            className="h-14 border border-gray-300 bg-gray-100 rounded-full px-10 ml-2"
          ></input>
        </div>
      </div>
      <div className="relative z-10 mr-2 mt-3 h-14 w-128">
        <ul
          className={`border border-gray-300 px-10 bg-light-gray ${
            requestOpen ? 'rounded-xl' : 'overflow-hidden rounded-full h-14'
          }`}
          onClick={handleRequestOpen}
        >
          <li className="h-14 flex items-center">빠른 배송 부탁드립니다.</li>
          <li className="h-14 flex items-center">빠른 배송 부탁드립니다.</li>
        </ul>
      </div>
    </div>
  );
}

export default DeliverForm;
