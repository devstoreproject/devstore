interface addressContainerProps {
  setIsOpenPost: React.Dispatch<React.SetStateAction<boolean>>;
  addressDetail: string;
  setAddressDetail: React.Dispatch<React.SetStateAction<string>>;
  address: string;
  zipCode: string;
}

export default function Address({
  setIsOpenPost,
  addressDetail,
  setAddressDetail,
  address,
  zipCode,
}: addressContainerProps) {
  const addressBtnHandler = (e: React.MouseEvent) => {
    e.preventDefault();
    setIsOpenPost(true);
  };

  const addressDetailInputHandler = (
    e: React.ChangeEvent<HTMLInputElement>
  ) => {
    setAddressDetail(e.target.value);
  };
  return (
    <div className="flex mt-2">
      <div className="flex flex-col w-full mr-2">
        <span className="ml-2">주소</span>
        <div className="flex items-center">
          <input
            type="text"
            className="h-10 pl-2 mr-2 border border-black"
            placeholder="우편번호"
            defaultValue={zipCode}
            readOnly
          ></input>
          <button
            className="w-32 ml-auto h-10 px-3 mb-1 text-sm bg-gray-300 rounded-xl shadow-btn active:shadow-none active:ml-3.5 active:mt-6.5 duration-100"
            onClick={addressBtnHandler}
          >
            우편번호찾기
          </button>
        </div>
        <input
          type="text"
          className="h-10 pl-2 mt-4 text-sm border border-black"
          placeholder="주소"
          defaultValue={address}
          readOnly
        ></input>
        <input
          type="text"
          className="h-10 pl-2 mt-4 text-sm border border-black"
          placeholder="상세주소"
          onChange={addressDetailInputHandler}
          value={addressDetail}
        ></input>
        <span className="mt-1 ml-2 text-xs font-bold text-red-500"></span>
      </div>
    </div>
  );
}
