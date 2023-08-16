interface NoticeProp {
  category: string;
  setCategory: any;
  setEditCategory: any;
}

export default function Select({
  category,
  setCategory,
  setEditCategory,
}: NoticeProp) {
  return (
    <div className="">
      <select
        name="notice"
        className="px-2 py-1.5 border rounded-lg border-gray"
        defaultValue={'none'}
        onChange={(e) => setCategory(e.target.value)}
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
  );
}
