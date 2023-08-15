interface NoticeProp {
  setContent: any;
}

export default function Textarea({ setContent }: NoticeProp) {
  return (
    <textarea
      placeholder="내용을 입력해 주세요"
      className="w-full px-2 pt-2 border rounded-lg pb-80 border-gray"
      onChange={(e) => setContent(e.target.value)}
    />
  );
}
