import ReactQuill from 'react-quill';
import 'react-quill/dist/quill.snow.css';

export default function ContentsInput() {
  return (
    <div>
      <p className="mb-6 text-subtitle-gray">상품 상세 등록</p>
      <ReactQuill />
    </div>
  );
}