import { useRef, useState } from 'react';
import type { ChangeEvent } from 'react';
import { AiOutlinePlus } from 'react-icons/ai';
import { IoMdClose } from 'react-icons/io';

interface NoticeProp {
  setImage: any;
}

export default function ImageInput({ setImage }: NoticeProp) {
  const [imagePreview, setImagePreview] = useState<string>('');

  const imageRef = useRef<HTMLInputElement>(null);
  const imageInputClickHandler = () => {
    imageRef.current?.click();
  };

  const onChangeImage = (e: ChangeEvent<HTMLInputElement>) => {
    e.preventDefault();

    const file = e.target.files?.[0];

    setImage(() => file);

    if (file != null) {
      const blobUrl = URL.createObjectURL(file);
      setImagePreview(blobUrl);
    }
  };

  const deleteImage = () => {
    URL.revokeObjectURL(imagePreview);
    setImagePreview('');
  };

  return (
    <>
      <label htmlFor="noticeImage" />
      <input
        type="file"
        accept="image/*"
        id="noticeImage"
        ref={imageRef}
        onChange={onChangeImage}
        className="hidden"
      />
      <div className="flex items-center w-full mt-10">
        {imagePreview === '' ? null : (
          <div className="relative w-40 h-40 mr-2 bg-white border border-transparent rounded-lg">
            <button
              type="button"
              onClick={deleteImage}
              className="absolute left-0 text-xl -top-6 text-subtitle-gray"
            >
              <IoMdClose />
            </button>
            <img
              src={imagePreview}
              alt="공지사항 이미지"
              className="object-cover w-40 h-40 rounded-lg"
            />
          </div>
        )}
        <button
          type="button"
          onClick={imageInputClickHandler}
          className="flex items-center justify-center w-40 h-40 text-4xl border rounded-lg border-gray"
        >
          <AiOutlinePlus />
        </button>
      </div>
    </>
  );
}
