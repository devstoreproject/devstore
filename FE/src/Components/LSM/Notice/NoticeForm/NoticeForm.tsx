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
}

export default function NoticeForm({ datas }: NoticeEditProp) {
  const navigate = useNavigate();

  const { id } = useParams() as { id: string };

  const [title, setTitle] = useState('');
  const [content, setContent] = useState('');
  const [category, setCategory] = useState('');
  const [image, setImage] = useState<string | Blob>('');

  const [editTitle, setEditTitle] = useState('');
  const [editContent, setEditContent] = useState('');
  const [editCategory, setEditCategory] = useState('');

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

    if (category === '') {
      alert('카테고리를 선택해주세요!🤔');
      return;
    }

    const contents = {
      userId: '2',
      editTitle,
      editContent,
      editCategory,
    };

    try {
      await api.patch(`/api/notices/${id}`, contents).then((res) => {
        console.log(res);
      });
    } catch (error) {
      console.log(error);
    }
  };

  return (
    <form className="flex flex-col">
      <div className="flex items-center mb-4">
        <Select
          category={category}
          setCategory={setCategory}
          setEditCategory={setEditCategory}
        />
        <TitleInput setTitle={setTitle} setEditTitle={setEditTitle} />
      </div>
      <Textarea setContent={setContent} setEditContent={setEditContent} />
      <ImageInput setImage={setImage} />
      <Button postData={postData} patchData={patchData} />
    </form>
  );
}
