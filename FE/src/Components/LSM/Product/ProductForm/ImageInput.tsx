import { useRef, useState } from 'react';
import { AiOutlinePlus, AiOutlineStar, AiFillStar } from 'react-icons/ai';
import { IoMdClose } from 'react-icons/io';

interface ImageInputProp {
  imagePreview: any;
  setImagePreview: React.Dispatch<React.SetStateAction<any>>;
  images: any;
  setImages: React.Dispatch<React.SetStateAction<any>>;
  editImages: any;
  setEditImages: React.Dispatch<React.SetStateAction<any>>;
}

export default function ImageInput({
  imagePreview,
  setImagePreview,
  images,
  setImages,
  editImages,
  setEditImages,
}: ImageInputProp) {
  const [isRepresentative, setIsRepresentative] = useState<boolean[]>([
    false,
    false,
    false,
    false,
    false,
  ]);

  const repToggleHandler = (idx: number) => {
    const newIsRepresentative: any = [...isRepresentative];
    newIsRepresentative[idx] = !isRepresentative[idx];
    setIsRepresentative(newIsRepresentative);

    const updatedImages = images.map((image: any, imageIdx: number) => {
      if (imageIdx === idx) {
        return {
          ...image,
          representative: true,
        };
      } else {
        return {
          ...image,
          representative: false,
        };
      }
    });

    setImages(updatedImages);
  };

  const imageRef = useRef<HTMLInputElement>(null);
  const imageInputClickHandler = () => {
    imageRef.current?.click();
  };

  const addImageHandler = (e: React.ChangeEvent<HTMLInputElement>) => {
    const imageList: any = e.target.files;
    let imageUrlList: any = [...imagePreview];

    for (let i = 0; i < imageList.length; i++) {
      const currentImageUrl = URL.createObjectURL(imageList[i]);
      imageUrlList.push(currentImageUrl);
    }

    if (imageUrlList.length > 5) {
      imageUrlList = imageUrlList.slice(0, 5);
    }

    const newImages = [...images];

    for (let i = 0; i < imageList.length; i++) {
      newImages.push({
        orderNumber: newImages?.length + 1,
        representative: false,
      });
    }

    setImagePreview(imageUrlList);

    setImages(newImages);
  };

  const removeImageHandler = (idx: number) => {
    const filteredInputs = [...imagePreview];
    filteredInputs.splice(idx, 1);
    setImagePreview(filteredInputs);

    const filteredImages = [...images];
    filteredImages.splice(idx, 1);
    setImages(filteredImages);
  };

  return (
    <div className="my-20">
      <p className="mb-6 text-subtitle-gray">대표이미지 등록</p>
      <div className="flex">
        {imagePreview.map((item: any, idx: any) => {
          return (
            <div
              key={idx}
              className="flex flex-col items-start justify-start mr-4"
            >
              <div className="right-0 flex items-end justify-end w-full">
                <button
                  type="button"
                  onClick={() => {
                    repToggleHandler(idx);
                  }}
                >
                  {images[idx].representative === true ? (
                    <AiFillStar />
                  ) : (
                    <AiOutlineStar />
                  )}
                </button>

                <button
                  type="button"
                  onClick={() => {
                    removeImageHandler(idx);
                  }}
                >
                  <IoMdClose />
                </button>
              </div>
              <img
                src={item}
                alt="상품 이미지"
                className="object-cover text-2xl rounded-lg w-28 h-28"
              />
            </div>
          );
        })}
        <label htmlFor="productImage" className="text-label-gray" />
        <input
          type="file"
          multiple
          name="orderNumber"
          accept="image/*"
          id="productImage"
          ref={imageRef}
          onChange={addImageHandler}
          className="hidden"
        />
        <div className="flex items-center mt-4">
          {imagePreview.length < 5 && (
            <button
              type="button"
              onClick={imageInputClickHandler}
              className="flex items-center justify-center text-2xl border rounded-lg w-28 h-28 border-label-gray"
            >
              <AiOutlinePlus />
            </button>
          )}
        </div>
      </div>
    </div>
  );
}
