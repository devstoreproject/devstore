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
import { useSelector } from 'react-redux';
import type { StoreType } from 'model/redux';

interface ProductEditProp {
  datas: any;
  pathName: string;
}

export default function ProductForm({ datas, pathName }: ProductEditProp) {
  const navigate = useNavigate();
  const getItemId = useSelector((e: StoreType) => e.currentItemId);

  const [title, setTitle] = useState('');
  const [category, setCategory] = useState('');
  const [price, setPrice] = useState(0);
  const [images, setImages] = useState([]);
  const [imagePreview, setImagePreview] = useState<any>([]);
  const [deliveryPrice, setDeliveryPrice] = useState(0);
  const [options, setOptions] = useState([
    { optionName: '', itemCount: 0, optionDetail: '', additionalPrice: 0 },
  ]);
  const [defaultCount, setDefaultCount] = useState(0);
  const [contents, setContents] = useState('');

  const [editTitle, setEditTitle] = useState(datas?.name);
  const [editCategory, setEditCategory] = useState(datas?.category);
  const [editPrice, setEditPrice] = useState(datas?.itemPrice);
  const [editImages, setEditImages] = useState(datas?.infoList);
  const [editDeliveryPrice, setEditDeliveryPrice] = useState(
    datas?.deliveryPrice
  );
  const [editOptions, setEditOptions] = useState(datas?.optionList);
  const [deleteOptions, setDeleteOptions] = useState<any>([]);
  const [editContents, setEditContents] = useState(datas?.description);

  console.log(datas);

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
          },
        })
        .then((res) => {
          navigate('/admin/productlist');
          console.log(res);
        });
    } catch (error) {
      console.log(error);
    }
    console.log(postDatas);
  };

  const patchData = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();

    try {
      const formData = new FormData();

      formData.append('imageList', imagePreview);

      const patchDatas = {
        name: editTitle,
        category: editCategory,
        itemPrice: editPrice,
        deliveryPrice: editDeliveryPrice,
        description: editContents,
        defaultCount,
        discountRate: 0,
        deleteImageId: null,
        deleteOptionId: deleteOptions,
        updateOptionList: [...editOptions],
      };

      formData.append(
        'patch',
        new Blob([JSON.stringify(patchDatas)], { type: 'application/json' })
      );
      console.log(patchDatas);
      const res = await api.patch(`/api/items/${getItemId}`, formData, {
        headers: {
          'Content-Type': 'multipart/form-data',
        },
      });
      console.log(res);
      navigate('/admin/productlist');
    } catch (error) {
      console.log(error);
    }
  };

  return (
    <form className="w-full">
      <div className="w-175">
        <TitleInput
          datas={datas}
          pathName={pathName}
          setTitle={setTitle}
          setEditTitle={setEditTitle}
        />
        <CategoryInput
          datas={datas}
          pathName={pathName}
          setCategory={setCategory}
          editCategory={editCategory}
          setEditCategory={setEditCategory}
        />
        <PriceInput
          datas={datas}
          pathName={pathName}
          setPrice={setPrice}
          setEditPrice={setEditPrice}
        />
      </div>
      <div className="w-200">
        <ImageInput
          imagePreview={imagePreview}
          setImagePreview={setImagePreview}
          images={images}
          setImages={setImages}
          editImages={editImages}
          setEditImages={setEditImages}
        />
        <OrderInput
          datas={datas}
          pathName={pathName}
          deliveryPrice={deliveryPrice}
          setDeliveryPrice={setDeliveryPrice}
          setEditDeliveryPrice={setEditDeliveryPrice}
        />
        <OptionInput
          datas={datas}
          pathName={pathName}
          options={options}
          setOptions={setOptions}
          setDefaultCount={setDefaultCount}
          editOptions={editOptions}
          setEditOptions={setEditOptions}
          deleteOptions={deleteOptions}
          setDeleteOptions={setDeleteOptions}
        />
      </div>
      <ContentsInput
        datas={datas}
        pathName={pathName}
        contents={contents}
        setContents={setContents}
        editContents={editContents}
        setEditContents={setEditContents}
      />
      <Button postData={postData} patchData={patchData} />
    </form>
  );
}
