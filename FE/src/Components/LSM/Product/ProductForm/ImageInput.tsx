import { useRef } from 'react';
import { AiOutlinePlus } from 'react-icons/ai';

export default function ImageInput() {
  const imageRef = useRef<HTMLInputElement>(null);
  const imageInputClickHandler = () => {
    imageRef.current?.click();
  };
  return (
    <div className="my-20">
      <p className="mb-6 text-subtitle-gray">대표이미지 등록</p>
      <label htmlFor="productImage" className="text-label-gray" />
      <input
        type="file"
        accept="image/*"
        id="productImage"
        ref={imageRef}
        className="hidden"
      />
      <div className="flex items-center w-full mt-4">
        <button
          type="button"
          onClick={imageInputClickHandler}
          className="flex items-center justify-center text-2xl border rounded-lg border-label-gray w-28 h-28"
        >
          <AiOutlinePlus />
        </button>
      </div>
    </div>
  );
}
