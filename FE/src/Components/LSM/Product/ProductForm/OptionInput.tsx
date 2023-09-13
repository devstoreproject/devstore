import { useEffect } from 'react';
import { AiOutlineMinus } from 'react-icons/ai';
import { useSelector } from 'react-redux';
import type { StoreType } from 'model/redux';

interface ProductProp {
  datas: any;
  pathName: string;
  options: any;
  setOptions: React.Dispatch<React.SetStateAction<any>>;
  setDefaultCount: React.Dispatch<React.SetStateAction<any>>;
  editOptions: any;
  setEditOptions: React.Dispatch<React.SetStateAction<any>>;
  deleteOptions: number[];
  setDeleteOptions: React.Dispatch<React.SetStateAction<any>>;
}

export default function OptionInput({
  datas,
  pathName,
  options,
  setOptions,
  setDefaultCount,
  editOptions,
  setEditOptions,
  deleteOptions,
  setDeleteOptions,
}: ProductProp) {
  const getOptionId = useSelector((e: StoreType) => e.currentOptionId);

  const addInputHandler = () => {
    if (pathName === 'post') {
      setOptions([
        ...options,
        {
          itemCount: 0,
          optionDetail: '',
          additionalPrice: 0,
        },
      ]);
    } else {
      setEditOptions([
        ...editOptions,
        {
          optionId: null,
          itemCount: 0,
          optionDetail: '',
          additionalPrice: 0,
        },
      ]);
    }
  };

  const removeInputHandler = (idx: number, getOptionId: any) => {
    const filteredOption = getOptionId.filter(
      (option: number, index: number) => {
        return idx === index;
      }
    );
    if (pathName === 'edit') {
      const filteredEditInputs = [...editOptions];
      filteredEditInputs.splice(idx, 1);
      const filteredOptionId: number = filteredOption[0]?.optionId;
      if (filteredOptionId !== undefined) {
        deleteOptions.push(filteredOptionId);
        setDeleteOptions(deleteOptions);
      } else {
        setDeleteOptions(null);
      }
      if (deleteOptions === null || deleteOptions.length === 0) {
        setDeleteOptions(null);
      }
      setEditOptions(filteredEditInputs);
    } else {
      const filteredInputs = [...options];
      filteredInputs.splice(idx, 1);
      setOptions(filteredInputs);
    }
  };

  const onChangeHandler = (
    idx: number,
    e: React.ChangeEvent<HTMLInputElement>
  ) => {
    if (pathName === 'post') {
      const list = [...options] as any;
      list[idx][e.target.id] = e.target.value;

      const itemCounts = list.map((item: any) => parseInt(item.itemCount));
      const totalItemCount = itemCounts.reduce(
        (acc: number, cur: number) => acc + cur,
        0
      );
      setOptions(list);
      setDefaultCount(totalItemCount);
    } else {
      const editList = [...editOptions] as any;
      editList[idx][e.target.id] = e.target.value;

      const editItemCounts = editList.map((item: any) =>
        parseInt(item.itemCount)
      );
      const totalEditItemCount = editItemCounts.reduce(
        (acc: number, cur: number) => acc + cur,
        0
      );
      setEditOptions(editList);
      setDefaultCount(totalEditItemCount);
      console.log(editList);
    }
  };

  useEffect(() => {
    if (datas?.optionList !== null) {
      setEditOptions(datas?.optionList);
    }
  }, [datas]);

  return (
    <div className="w-full my-20">
      <p className="mb-6 text-subtitle-gray">옵션 등록</p>
      {pathName === 'post' &&
        options?.map((option: any, idx: number) => {
          return (
            <div
              key={idx}
              className="flex flex-col items-start justify-between"
            >
              <div className="flex items-center w-full mb-8">
                <div className="flex flex-col w-full">
                  <p className="mb-2 text-label-gray">옵션 {idx + 1}</p>
                  <label htmlFor="optionDetail" />
                  <input
                    id="optionDetail"
                    name="optionDetail"
                    type="text"
                    placeholder="옵션 내용 (ex. 색상, 사이즈)"
                    onChange={(e) => {
                      onChangeHandler(idx, e);
                    }}
                    className="w-full px-5 py-3 mb-2 border rounded-3xl border-gray"
                  />
                  <label htmlFor="additionalPrice" />
                  <input
                    id="additionalPrice"
                    name="additionalPrice"
                    type="text"
                    placeholder="추가 금액 (숫자만 입력)"
                    onChange={(e) => {
                      onChangeHandler(idx, e);
                    }}
                    className="w-full px-5 py-3 mb-2 border rounded-3xl border-gray"
                  />
                  <label htmlFor="itemCount" />
                  <input
                    id="itemCount"
                    name="itemCount"
                    type="text"
                    placeholder="옵션 수량 (숫자만 입력)"
                    onChange={(e) => {
                      onChangeHandler(idx, e);
                    }}
                    className="px-5 py-3 mb-2 border rounded-3xl border-gray"
                  />
                </div>
                {options?.length > 1 && (
                  <button
                    type="button"
                    onClick={() => {
                      removeInputHandler(idx, getOptionId);
                    }}
                    className="flex items-center justify-center p-4 ml-4 border rounded-full"
                  >
                    <AiOutlineMinus />
                  </button>
                )}
              </div>
              {options?.length - 1 === idx && options?.length < 10 && (
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
      {pathName === 'edit' &&
        editOptions?.map((option: any, idx: number) => {
          return (
            <div
              key={idx}
              className="flex flex-col items-start justify-between"
            >
              <div className="flex items-center w-full mb-8">
                <div className="flex flex-col w-full">
                  <p id="optionName" className="mb-2 text-label-gray">
                    옵션 {idx + 1}
                  </p>
                  <label htmlFor="optionDetail" />
                  <input
                    id="optionDetail"
                    name="optionDetail"
                    type="text"
                    placeholder="옵션 내용 (ex. 블랙, XL)"
                    onChange={(e) => {
                      onChangeHandler(idx, e);
                    }}
                    value={
                      option?.optionDetail === null ? '' : option.optionDetail
                    }
                    className="w-full px-5 py-3 mb-2 border rounded-3xl border-gray"
                  />
                  <label htmlFor="additionalPrice" />
                  <input
                    id="additionalPrice"
                    name="additionalPrice"
                    type="text"
                    placeholder="추가 금액 (숫자만 입력)"
                    onChange={(e) => {
                      onChangeHandler(idx, e);
                    }}
                    value={
                      option?.additionalPrice === null
                        ? ''
                        : option.additionalPrice
                    }
                    className="w-full px-5 py-3 mb-2 border rounded-3xl border-gray"
                  />
                  <label htmlFor="itemCount" />
                  <input
                    id="itemCount"
                    name="itemCount"
                    type="text"
                    placeholder="옵션 수량 (숫자만 입력)"
                    onChange={(e) => {
                      onChangeHandler(idx, e);
                    }}
                    value={option?.itemCount}
                    className="px-5 py-3 border rounded-3xl border-gray"
                  />
                </div>

                {editOptions?.length > 1 && (
                  <button
                    type="button"
                    onClick={() => {
                      removeInputHandler(idx, getOptionId);
                    }}
                    className="flex items-center justify-center p-4 ml-4 border rounded-full"
                  >
                    <AiOutlineMinus />
                  </button>
                )}
              </div>
              {editOptions?.length - 1 === idx && editOptions?.length < 10 && (
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
