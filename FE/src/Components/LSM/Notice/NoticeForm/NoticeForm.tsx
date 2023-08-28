import { useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import Button from './Button';
import ImageInput from './ImageInput';
import TitleInput from './TitleInput';
import Select from './Select';
import Textarea from './Textarea';
import api from 'api';

interface NoticeEditProp {
  datas: any;
  path: string;
}

export default function NoticeForm({ datas, path }: NoticeEditProp) {
  const navigate = useNavigate();

  const { id } = useParams() as { id: string };

  const [title, setTitle] = useState('');
  const [content, setContent] = useState('');
  const [category, setCategory] = useState('');
  const [image, setImage] = useState<string | Blob>('');

  const [editTitle, setEditTitle] = useState<string>(datas?.title);
  const [editContent, setEditContent] = useState<string>(datas?.content);
  const [editCategory, setEditCategory] = useState<string>(datas?.category);
  const [editImage, setEditImage] = useState<string | Blob>(
    datas?.image?.thumbnailPath
  );

  const postData = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();

    if (category === '') {
      alert('카테고리를 선택해주세요!🤔');
      return;
    }

    const formData = new FormData();

    formData.append('image', image);

    const contents = {
      userId: '2',
      title,
      content,
      category,
    };

    formData.append(
      'post',
      new Blob([JSON.stringify(contents)], { type: 'application/json' })
    );

    try {
      await api
        .post('/api/notices', formData, {
          headers: {
            'Content-Type': 'multipart/form-data',
          },
        })
        .then((res) => {
          navigate('/admin/notice');
        });
    } catch (error) {
      console.log(error);
    }
  };

  const patchData = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();

    const formData = new FormData();

    formData.append('image', editImage);

    const contents = {
      userId: '2',
      title: editTitle,
      content: editContent,
      category: editCategory,
    };

    formData.append(
      'patch',
      new Blob([JSON.stringify(contents)], { type: 'application/json' })
    );

    try {
      await api
        .patch(`/api/notices/${id}`, formData, {
          headers: {
            'Content-Type': 'multipart/form-data',
          },
        })
        .then((res) => {
          navigate(`/notice/${id}`);
        });
    } catch (error) {
      console.log(error);
    }
  };

  return (
    <form className="flex flex-col">
      <div className="flex items-center mb-4">
        <Select
          datas={datas}
          path={path}
          setCategory={setCategory}
          setEditCategory={setEditCategory}
          editCategory={editCategory}
          id={id}
        />
        <TitleInput
          datas={datas}
          path={path}
          setTitle={setTitle}
          setEditTitle={setEditTitle}
        />
      </div>
      <Textarea
        datas={datas}
        path={path}
        setContent={setContent}
        setEditContent={setEditContent}
      />
      <ImageInput
        datas={datas}
        path={path}
        setImage={setImage}
        setEditImage={setEditImage}
      />
      <Button postData={postData} patchData={patchData} />
    </form>
  );
}
