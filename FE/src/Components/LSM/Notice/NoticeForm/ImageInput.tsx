import { useRef, useState, useEffect } from 'react';
import { AiOutlinePlus } from 'react-icons/ai';
import { IoMdClose } from 'react-icons/io';

interface NoticeProp {
  datas: any;
  path: string;
  setImage: any;
  setEditImage: any;
}

export default function ImageInput({
  setEditImage,
  setImage,
  path,
  datas,
}: NoticeProp) {
  const [imagePreview, setImagePreview] = useState<string>('');
  const [imageEditPreview, setImageEditPreview] = useState<string>('');

  const imageRef = useRef<HTMLInputElement>(null);
  const imageInputClickHandler = () => {
    imageRef.current?.click();
  };

  const onChangeImage = (e: React.ChangeEvent<HTMLInputElement>) => {
    e.preventDefault();

    const file = e.target.files?.[0];

    if (path === 'post') {
      setImage(file);
    } else {
      setEditImage(file);
    }

    if (file != null) {
      const blobUrl = URL.createObjectURL(file);
      if (path === 'post') {
        setImagePreview(blobUrl);
      } else {
        setImageEditPreview(blobUrl);
      }
    }
  };

  const deleteImage = () => {
    if (path === 'post') {
      URL.revokeObjectURL(imagePreview);
      setImagePreview('');
    } else {
      URL.revokeObjectURL(imageEditPreview);
      setImageEditPreview('');
    }
  };

  useEffect(() => {
    setImageEditPreview(datas?.image?.thumbnailPath);
  }, [datas?.image?.thumbnailPath]);

  return (
    <>
      {path === 'post' ? (
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
      ) : (
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
            {imageEditPreview === undefined ||
            imageEditPreview === '' ? null : (imageEditPreview === undefined ||
                imageEditPreview === '') &&
              datas?.image !== null ? (
              <div className="relative w-40 h-40 mr-2 bg-white border border-transparent rounded-lg">
                <button
                  type="button"
                  onClick={deleteImage}
                  className="absolute left-0 text-xl -top-6 text-subtitle-gray"
                >
                  <IoMdClose />
                </button>
                <img
                  src={imageEditPreview}
                  alt="공지사항 이미지"
                  className="object-cover w-40 h-40 rounded-lg"
                />
              </div>
            ) : (
              <div className="relative w-40 h-40 mr-2 bg-white border border-transparent rounded-lg">
                <button
                  type="button"
                  onClick={deleteImage}
                  className="absolute left-0 text-xl -top-6 text-subtitle-gray"
                >
                  <IoMdClose />
                </button>
                <img
                  src={imageEditPreview}
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
      )}
    </>
  );
}
