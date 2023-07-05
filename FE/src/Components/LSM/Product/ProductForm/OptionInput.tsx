import { AiOutlinePlus } from 'react-icons/ai';

export default function OptionInput() {
  const optionList = [
    { id: 1, name: '옵션 1' },
    { id: 2, name: '옵션 2' },
  ];

  return (
    <div className="my-20">
      <p className="mb-6 text-subtitle-gray">옵션 등록</p>
      {optionList.map((option) => {
        return (
          <div key={option.id} className="flex items-center w-full mb-11">
            <label htmlFor="productOrder" className="w-20 text-label-gray">
              {option.name}
            </label>
            <input
              id="productOrder"
              type="text"
              placeholder="텍스트를 입력해 주세요"
              disabled
              className="w-full px-5 py-3 border rounded-3xl border-gray"
            />
            <button
              type="button"
              className="flex items-center justify-center p-4 ml-4 border rounded-full"
            >
              <AiOutlinePlus />
            </button>
          </div>
        );
      })}
      <button type="button" className="px-10 py-3 text-sm border rounded-3xl">
        옵션추가하기 +
      </button>
    </div>
  );
}
