import api from 'api';
import { useState } from 'react';
import type { ShippingList } from '../Type/ShippingType';
import DaumPostcodeEmbed from 'react-daum-postcode';

interface ShipProps {
  isShippingFormWrite: ShippingList;
  setIsShippingFormWrite: React.Dispatch<React.SetStateAction<ShippingList>>;
  isMe: boolean;
  setIsMe: React.Dispatch<React.SetStateAction<boolean>>;
}
function DeliverForm({
  isShippingFormWrite,
  setIsShippingFormWrite,
  isMe,
  setIsMe,
}: ShipProps) {
  // const [mobileOpen, setMobileOpen] = useState(false);
  // const [requestOpen, setRequestOpen] = useState(false);
  const [addressFinder, setAddressFinder] = useState<boolean>(false);
  const [addressFinderOn, setAddressFinderOn] = useState<boolean>(false);

  // input 요소의 change를 받아 state를 변경하는 함수
  const handleShippingChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setIsShippingFormWrite({
      ...isShippingFormWrite,
      [e.target.name]: e.target.value,
    });
  };
  // 드롭다운
  // const handleMobileOpen = () => {
  //   setMobileOpen(!mobileOpen);
  // };
  // const handleRequestOpen = () => {
  //   setRequestOpen(!requestOpen);
  //   setAddressFinder(false);
  // };

  // 폰번호 뒤에만 가져오기
  // const mobileCheck = () => {
  //   return isShippingForm.mobileNumber.slice(-9);
  // };
  // const phoneFrontIndex = isShippingForm.mobileNumber.indexOf('-');
  // const phoneFront = isShippingForm.mobileNumber.slice(0, phoneFrontIndex);
  // const NumberChange = mobileCheck();

  // 배송지 가져오는 get요청
  const fetchGetShipping = () => {
    const userId = Number(localStorage.getItem('userId'));
    api
      .get(`api/address/users/${userId}`)
      .then((res) => {
        if (res.data.data.length === 0) {
          alert('기본 배송지가 등록되어있지 않습니다.');
        }
        const [data] = res.data.data;
        setIsShippingFormWrite(data);
      })
      .catch(() => {
        alert('기본 배송지가 등록되어있지 않습니다.');
      });
  };

  const onCompletePost = (data: any) => {
    setIsShippingFormWrite({
      ...isShippingFormWrite,
      zipCode: data.zonecode,
      addressSimple: data.roadAddress,
    });
    setAddressFinder(false);
    setAddressFinderOn(true);
  };

  return (
    <div>
      {isMe ? (
        <>
          <div className="flex items-center">
            <p className="flex items-center w-40 px-10 border border-gray-300 rounded-full h-14">
              {isShippingFormWrite.recipient}
            </p>
            <input
              type="checkbox"
              id="orderer"
              className="w-5 h-5 mx-3 border-gray-300"
              onClick={() => {
                setIsMe(!isMe);
                setIsShippingFormWrite({
                  recipient: '',
                  mobileNumber: '',
                  zipCode: '',
                  addressSimple: '',
                  addressDetail: '',
                });
              }}
            ></input>
            <label htmlFor="orderer" className="cursor-default">
              주문자와 동일
            </label>
          </div>
          <div className="flex items-center mt-3">
            {/* <div className="relative w-40 mr-2 h-14">
              <ul
                className={`top-0 left-0 border border-gray-300 rounded-full px-10 bg-light-gray w-full absolute z-20 ${
                  mobileOpen
                    ? 'rounded-xl'
                    : 'overflow-hidden rounded-full h-14'
                }`}
                onClick={handleMobileOpen}
              >
                <li className="flex items-center h-14">{phoneFront}</li>
              </ul>
            </div>
            - */}
            <p className="flex items-center w-64 px-10 border border-gray-300 rounded-full h-14">
              {isShippingFormWrite.mobileNumber}
            </p>
          </div>
          <div className="mt-3">
            <p className="flex items-center w-40 px-10 border border-gray-300 rounded-full h-14">
              {isShippingFormWrite.zipCode}
            </p>
            <div className="flex gap-2 mt-3">
              <p className="flex items-center w-64 px-10 border border-gray-300 rounded-full h-14">
                {isShippingFormWrite.addressSimple}
              </p>
              <p className="flex items-center w-64 px-10 border border-gray-300 rounded-full h-14">
                {isShippingFormWrite.addressDetail}
              </p>
            </div>
          </div>
        </>
      ) : (
        <>
          <div className="flex items-center">
            <input
              type="textarea"
              name="recipient"
              className="w-40 px-10 bg-gray-100 border border-gray-300 rounded-full h-14"
              onChange={handleShippingChange}
            ></input>
            <input
              type="checkbox"
              id="orderer"
              className="w-5 h-5 mx-3 border-gray-300"
              onClick={() => {
                fetchGetShipping();
                setIsMe(!isMe);
              }}
            ></input>
            <label htmlFor="orderer" className="cursor-default">
              주문자와 동일
            </label>
          </div>
          <div className="flex items-center mt-3">
            {/* <div className="relative w-40 mr-2 h-14">
              <ul
                className={`top-0 left-0 border border-gray-300 rounded-full px-10 bg-light-gray w-full absolute z-20 ${
                  mobileOpen
                    ? 'rounded-xl'
                    : 'overflow-hidden rounded-full h-14'
                }`}
                onClick={handleMobileOpen}
              >
                <li className="flex items-center h-14">010</li>
                <li className="flex items-center h-14">010</li>
              </ul>
            </div>
            - */}
            <input
              type="textarea"
              name="mobileNumber"
              placeholder="숫자만 입력해주세요"
              className="w-64 px-10 ml-2 bg-gray-100 border border-gray-300 rounded-full h-14"
              onChange={handleShippingChange}
            ></input>
          </div>
          <div className="mt-3">
            {addressFinderOn ? (
              <p className="flex items-center float-left w-40 px-10 border border-gray-300 rounded-full h-14">
                {isShippingFormWrite.zipCode}
              </p>
            ) : (
              <input
                type="textarea"
                name="zipCode"
                className="w-40 px-10 bg-gray-100 border border-gray-300 rounded-full h-14"
                onChange={handleShippingChange}
              ></input>
            )}
            <button
              className="w-40 px-10 ml-5 text-white border rounded-full h-14 border-light-black bg-light-black"
              onClick={(e) => {
                e.preventDefault();
                setAddressFinder(!addressFinder);
              }}
            >
              주소찾기
            </button>
            <div className="mt-3">
              {addressFinderOn ? (
                <p className="flex items-center float-left px-10 border border-gray-300 rounded-full h-14">
                  {isShippingFormWrite.addressSimple}
                </p>
              ) : (
                <input
                  type="textarea"
                  name="addressSimple"
                  className="w-64 px-10 bg-gray-100 border border-gray-300 rounded-full h-14"
                  onChange={handleShippingChange}
                ></input>
              )}
              <input
                type="textarea"
                name="addressDetail"
                placeholder="상세주소를 입력하세요"
                className="w-64 px-10 ml-2 bg-gray-100 border border-gray-300 rounded-full h-14"
                onChange={handleShippingChange}
              ></input>
            </div>
            {addressFinder && (
              <>
                <div className="fixed z-50 w-1/2 -translate-x-1/2 -translate-y-1/2 top-1/2 left-1/2">
                  <DaumPostcodeEmbed
                    onComplete={onCompletePost}
                    autoClose={true}
                  />
                </div>
                <div className="fixed top-0 left-0 z-40 w-full h-full bg-black opacity-30"></div>
              </>
            )}
          </div>
        </>
      )}
      {/* <div className="relative z-10 mt-3 mr-2 h-14 w-128">
        <ul
          className={`border border-gray-300 px-10 bg-light-gray ${
            requestOpen ? 'rounded-xl' : 'overflow-hidden rounded-full h-14'
          }`}
          onClick={handleRequestOpen}
        >
          <li className="flex items-center h-14">
            배송 시 요청사항을 선택해주세요.
          </li>
          <li className="flex items-center h-14">빠른 배송 부탁드립니다.</li>
        </ul>
      </div> */}
    </div>
  );
}

export default DeliverForm;
