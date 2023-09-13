interface CategoryInputProp {
  pathName: string;
  setCategory: React.Dispatch<React.SetStateAction<string>>;
  setEditCategory: React.Dispatch<React.SetStateAction<string>>;
  editCategory: string;
}

export default function Select({
  pathName,
  setCategory,
  setEditCategory,
  editCategory,
}: CategoryInputProp) {
  const selectHandler = (e: any) => {
    if (pathName === 'post') {
      setCategory(e.target.value);
    } else {
      setEditCategory(e.target.value);
    }
  };

  return (
    <>
      {pathName === 'post' ? (
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
      ) : (
        <div className="w-full">
          <select
            name="notice"
            className="w-full px-4 py-3 border rounded-3xl border-gray"
            value={editCategory}
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
      )}
    </>
  );
}
