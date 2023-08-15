interface NoticeProp {
  setTitle: any;
}

export default function TitleInput({ setTitle }: NoticeProp) {
  return (
    <input
      type="text"
      placeholder="제목을 입력해 주세요"
      className="w-full px-2 py-1 ml-4 border rounded-lg border-gray"
      onChange={(e) => setTitle(e.target.value)}
    />
  );
}
