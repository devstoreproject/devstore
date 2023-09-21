interface NoticeProp {
  datas: any;
  path: string;
  setContent: React.Dispatch<React.SetStateAction<string>>;
  setEditContent: React.Dispatch<React.SetStateAction<string>>;
}

export default function Textarea({
  datas,
  setContent,
  setEditContent,
  path,
}: NoticeProp) {
  const textHandler = (e: any) => {
    if (path === 'post') {
      setContent(e.target.value);
    } else {
      setEditContent(e.target.value);
    }
  };
  return (
    <>
      {path === 'post' ? (
        <textarea
          placeholder="내용을 입력해 주세요"
          className="w-full px-2 pt-2 border rounded-lg pb-80 border-gray"
          onChange={textHandler}
        />
      ) : (
        <textarea
          placeholder="내용을 입력해 주세요"
          className="w-full px-2 pt-2 border rounded-lg pb-80 border-gray"
          onChange={textHandler}
          defaultValue={datas.content}
        />
      )}
    </>
  );
}
