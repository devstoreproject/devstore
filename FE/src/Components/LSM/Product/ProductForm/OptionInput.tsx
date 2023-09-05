import { AiOutlineMinus } from 'react-icons/ai';

interface ProductProp {
  options: any;
  setOptions: React.Dispatch<React.SetStateAction<any>>;
  setDefaultCount: React.Dispatch<React.SetStateAction<any>>;
}

export default function OptionInput({
  options,
  setOptions,
  setDefaultCount,
}: ProductProp) {
  const addInputHandler = () => {
    setOptions([
      ...options,
      { optionName: '', itemCount: 0, optionDetail: '', additionalPrice: 0 },
    ]);
  };

  const removeInputHandler = (idx: number) => {
    const filteredInputs = [...options];
    filteredInputs.splice(idx, 1);
    setOptions(filteredInputs);
  };

  const onChangeHandler = (
    idx: number,
    e: React.ChangeEvent<HTMLInputElement>
  ) => {
    const list = [...options] as any;
    list[idx][e.target.id] = e.target.value;
    setOptions(list);

    const itemCounts = list.map((item: any) => parseInt(item.itemCount));
    const totalItemCount = itemCounts.reduce(
      (acc: number, cur: number) => acc + cur,
      0
    );

    setDefaultCount(totalItemCount);
  };

  return (
    <div className="w-full my-20">
      <p className="mb-6 text-subtitle-gray">옵션 등록</p>
      {options.map((item: any, idx: number) => {
        return (
          <div key={idx} className="flex flex-col items-start justify-between">
            <div className="flex items-center w-full mb-8">
              <label htmlFor="optionName" className="w-24 mr-4 text-label-gray">
                옵션 {idx + 1}
              </label>
              <input
                id="optionName"
                name="optionName"
                type="text"
                placeholder="옵션을 입력해 주세요"
                onChange={(e) => {
                  onChangeHandler(idx, e);
                }}
                className="w-full px-5 py-3 border rounded-3xl border-gray"
              />
              <label htmlFor="itemCount">{}</label>
              <input
                id="itemCount"
                name="itemCount"
                type="text"
                placeholder="옵션 수량 (숫자만 입력)"
                onChange={(e) => {
                  onChangeHandler(idx, e);
                }}
                className="px-5 py-3 ml-4 border rounded-3xl border-gray"
              />
              {options.length > 1 && (
                <button
                  type="button"
                  onClick={() => {
                    removeInputHandler(idx);
                  }}
                  className="flex items-center justify-center p-4 ml-4 border rounded-full"
                >
                  <AiOutlineMinus />
                </button>
              )}
            </div>
            {options.length - 1 === idx && options.length < 10 && (
              <button
                type="button"
                onClick={addInputHandler}
                className="px-10 py-3 text-sm border rounded-3xl"
              >
                옵션추가하기 +
              </button>
            )}
          </div>
        );
      })}
    </div>
  );
}
