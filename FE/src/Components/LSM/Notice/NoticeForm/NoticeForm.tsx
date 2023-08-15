import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import Button from './Button';
import ImageInput from './ImageInput';
import TitleInput from './TitleInput';
import Select from './Select';
import Textarea from './Textarea';
import api from 'api';

export default function NoticeForm() {
  const navigate = useNavigate();

  const [title, setTitle] = useState('');
  const [content, setContent] = useState('');
  const [category, setCategory] = useState('');

  const submitData = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();

    const formData = new FormData();

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
          navigate(`/admin/notice`);
        });
    } catch (error) {
      console.log(error);
    }
  };

  return (
    <form className="flex flex-col">
      <div className="flex items-center mb-4">
        <Select setCategory={setCategory} />
        <TitleInput setTitle={setTitle} />
      </div>
      <Textarea setContent={setContent} />
      <ImageInput />
      <Button submitData={submitData} />
    </form>
  );
}
