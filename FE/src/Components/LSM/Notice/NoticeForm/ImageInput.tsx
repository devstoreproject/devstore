import { useRef } from 'react';

export default function ImageInput() {
  const imageRef = useRef<HTMLInputElement>(null);
  const imageInputClickHandler = () => {
    imageRef.current?.click();
  };
  return (
    <>
      <label htmlFor="noticeImage" />
      <input
        type="file"
        accept="image/*"
        id="noticeImage"
        ref={imageRef}
        className="hidden"
      />
      <div className="flex items-center w-full mt-4">
        <div className="w-full px-2 py-1 mr-4 border rounded-lg border-gray ">
          사진을 첨부해 주세요
        </div>
        <button
          type="button"
          onClick={imageInputClickHandler}
          className="w-20 py-1.5 px-1 text-sm font-semibold bg-gray-400 border rounded-lg border-gray"
        >
          사진첨부
        </button>
      </div>
    </>
  );
}
