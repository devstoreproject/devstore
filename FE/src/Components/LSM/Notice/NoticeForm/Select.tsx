import { useEffect } from 'react';
interface NoticeProp {
  datas: any;
  path: string;
  setCategory: React.Dispatch<React.SetStateAction<string>>;
  setEditCategory: React.Dispatch<React.SetStateAction<string>>;
  editCategory: string;
  id: string;
}

export default function Select({
  datas,
  path,
  setCategory,
  setEditCategory,
  editCategory,
  id,
}: NoticeProp) {
  const selectHandler = (e: any) => {
    if (path === 'post') {
      setCategory(e.target.value);
    } else {
      setEditCategory(e.target.value);
      console.log(e.target.value);
    }
  };

  useEffect(() => {
    setEditCategory(datas?.category);
  }, [datas?.category]);

  console.log(editCategory);

  return (
    <>
      {path === 'post' ? (
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
      )}
    </>
  );
}
