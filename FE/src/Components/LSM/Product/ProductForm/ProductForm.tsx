import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import Button from './Button';
import ContentsInput from './ContentsInput';
import ImageInput from './ImageInput';
import OptionInput from './OptionInput';
import OrderInput from './OrderInput';
import TitleInput from './TitleInput';
import PriceInput from './PriceInput';
import CategoryInput from './CategoryInput';
import api from 'api';

export default function ProductForm() {
  const navigate = useNavigate();

  const [title, setTitle] = useState('');
  const [category, setCategory] = useState('');
  const [price, setPrice] = useState<any>('');
  const [imagePreview, setImagePreview] = useState<any>([]);
  const [images, setImages] = useState([]);
  const [deliveryPrice, setDeliveryPrice] = useState<any>(0);
  const [options, setOptions] = useState([
    { optionName: '', itemCount: 0, optionDetail: '', additionalPrice: 0 },
  ]);
  const [defaultCount, setDefaultCount] = useState(0);
  const [contents, setContents] = useState('');

  const postData = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();

    if (category === 'none') {
      alert('카테고리를 선택해주세요!🤔');
      return;
    }

    const formData = new FormData();

    formData.append('imageList', imagePreview);

    const postDatas = {
      name: title,
      category,
      itemPrice: price,
      infoList: images,
      deliveryPrice,
      optionList: options,
      defaultCount,
      description: contents,
    };

    formData.append(
      'post',
      new Blob([JSON.stringify(postDatas)], { type: 'application/json' })
    );

    try {
      await api
        .post('/api/items', formData, {
          headers: {
            'Content-Type': 'multipart/form-data',
            Authorization: localStorage.getItem('authorization'),
          },
        })
        .then((res) => {
          navigate('/admin/productlist');
          console.log(res);
        });
    } catch (error) {
      console.log(error);
    }
    console.log(imagePreview, postDatas);
  };

  return (
    <form className="w-full">
      <div className="w-175">
        <TitleInput setTitle={setTitle} />
        <CategoryInput category={category} setCategory={setCategory} />
        <PriceInput setPrice={setPrice} />
      </div>
      <div className="w-200">
        <ImageInput
          imagePreview={imagePreview}
          setImagePreview={setImagePreview}
          images={images}
          setImages={setImages}
        />
        <OrderInput
          deliveryPrice={deliveryPrice}
          setDeliveryPrice={setDeliveryPrice}
        />
        <OptionInput
          options={options}
          setOptions={setOptions}
          setDefaultCount={setDefaultCount}
        />
      </div>
      <ContentsInput contents={contents} setContents={setContents} />
      {/* <Button postData={postData} patchData={patchData} /> */}
      <Button postData={postData} />
    </form>
  );
}
