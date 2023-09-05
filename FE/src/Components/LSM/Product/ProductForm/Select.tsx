// import { useEffect } from 'react';
interface CategoryInputProp {
  // datas: any;
  // path: string;
  category: string;
  setCategory: React.Dispatch<React.SetStateAction<string>>;
  // setEditCategory: React.Dispatch<React.SetStateAction<string>>;
  // editCategory: string;
  // id: string;
}

export default function Select({ setCategory }: CategoryInputProp) {
  const selectHandler = (e: any) => {
    setCategory(e.target.value);
  };
  // const selectHandler = (e: any) => {
  //   if (path === 'post') {
  //     setCategory(e.target.value);
  //   } else {
  //     setEditCategory(e.target.value);
  //   }
  // };

  // useEffect(() => {
  //   setEditCategory(datas?.category);
  // }, [datas?.category]);

  return (
    <>
      <div className="w-full">
        <select
          name="notice"
          className="w-full px-4 py-3 border rounded-3xl border-gray"
          defaultValue={'none'}
          onChange={selectHandler}
          required={true}
        >
          <option value="none">카테고리를 선택해주세요</option>
          <option value="COMPUTER">컴퓨터</option>
          <option value="MONITOR">모니터</option>
          <option value="MOUSE">마우스</option>
          <option value="HEADSET">헤드셋</option>
          <option value="CHAIR">의자</option>
          <option value="DESK">책상</option>
        </select>
      </div>
      {/* {path === 'post' ? (
        <div className="">
          <select
            name="notice"
            className="px-2 py-1.5 border rounded-lg border-gray"
            defaultValue={'none'}
            onChange={selectHandler}
            required={true}
          >
            <option value="none" disabled>
              카테고리 선택
            </option>
            <option value="OPERATING">운영정책</option>
            <option value="UPDATE">업데이트</option>
            <option value="EVENT">이벤트</option>
          </select>
        </div>
      ) : (
        <div className="">
          <select
            name="notice"
            className="px-2 py-1.5 border rounded-lg border-gray"
            value={editCategory}
            onChange={selectHandler}
            required={true}
          >
            <option value="none" disabled>
              카테고리 선택
            </option>
            <option value="OPERATING">운영정책</option>
            <option value="UPDATE">업데이트</option>
            <option value="EVENT">이벤트</option>
          </select>
        </div>
      )} */}
    </>
  );
}
