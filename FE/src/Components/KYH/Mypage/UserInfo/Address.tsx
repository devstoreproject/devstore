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
    <div className="flex mb-4">
      <span className="w-32 mt-2 text-gray-500">주소</span>
      <div className="flex flex-col">
        <input
          type="text"
          className="h-10 pl-4 mb-4 text-sm border border-gray-300 w-96 rounded-3xl"
          placeholder="우편번호"
          defaultValue={zipCode}
          readOnly
        ></input>
        <input
          type="text"
          className="h-10 pl-4 mb-4 text-sm border border-gray-300 w-96 rounded-3xl"
          placeholder="주소"
          defaultValue={address}
          readOnly
        ></input>
        <input
          type="text"
          className="h-10 pl-4 text-sm border border-gray-300 w-96 rounded-3xl"
          placeholder="상세주소"
          onChange={addressDetailInputHandler}
          value={addressDetail}
        ></input>
      </div>
      <button
        className="h-10 px-4 ml-3 text-sm bg-gray-300 rounded-xl shadow-btn active:shadow-none active:ml-3.5 active:mt-6.5 duration-100"
        onClick={addressBtnHandler}
      >
        우편번호찾기
      </button>

      <span className="mt-1 ml-2 text-xs font-bold text-red-500"></span>
    </div>
  );
}
