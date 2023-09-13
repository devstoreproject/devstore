import { useMemo, useRef, useEffect } from 'react';
import ReactQuill from 'react-quill';
import 'react-quill/dist/quill.snow.css';
import AWS from 'aws-sdk';

interface ProductProp {
  datas: any;
  pathName: string;
  contents: string;
  editContents: string;
  setContents: React.Dispatch<React.SetStateAction<any>>;
  setEditContents: React.Dispatch<React.SetStateAction<any>>;
}

const REGION = process.env.REACT_APP_AWS_S3_BUCKET_REGION;
const ACCESS_KEY = process.env.REACT_APP_AWS_S3_BUCKET_ACCESS_KEY_ID;
const SECRET_ACCESS_KEY = process.env.REACT_APP_AWS_S3_BUCKET_SECRET_ACCESS_KEY;

export default function ContentsInput({
  datas,
  pathName,
  contents,
  editContents,
  setContents,
  setEditContents,
}: ProductProp) {
  const quillRef = useRef<any>(null);

  const imageHandler = async () => {
    const input = document.createElement('input');
    input.setAttribute('type', 'file');
    input.setAttribute('accept', 'image/*');
    input.click();

    input.onchange = async () => {
      const file = input.files?.[0];
      try {
        const name = Date.now();

        AWS.config.update({
          region: REGION,
          accessKeyId: ACCESS_KEY,
          secretAccessKey: SECRET_ACCESS_KEY,
        });

        const upload = new AWS.S3.ManagedUpload({
          params: {
            ACL: 'public-read',
            Bucket: 'devstore-editor-image',
            Key: `upload/${name}`,
            Body: file,
          },
        });

        const editor = quillRef.current.getEditor();
        const range = editor.getSelection();

        const imgUrl = upload.promise().then((res) => {
          if (pathName === 'post') {
            setContents(
              (prev: string) => `${prev}<img src ="${res.Location}"/>`
            );
          } else {
            setEditContents(
              (prev: string) => `${prev}<img src ="${res.Location}"/>`
            );
          }
        });

        editor.insertEmbed(range.index, 'image', imgUrl);
        editor.setSelection(parseInt(range.index) + 1);
      } catch (error) {
        console.log(error);
      }
    };
  };

  const modules = useMemo(() => {
    return {
      toolbar: {
        container: [
          ['image'],
          [{ header: [1, 2, 3, false] }],
          ['bold', 'underline', 'strike', 'blockquote'],
          [{ color: [] }, { background: [] }, { align: [] }],
        ],
        handlers: {
          image: imageHandler,
        },
      },
    };
  }, []);

  const formats = [
    'header',
    'bold',
    'underline',
    'strike',
    'blockquote',
    'image',
    'color',
    'background',
    'align',
  ];

  useEffect(() => {
    if (datas?.description !== null) {
      setEditContents(datas?.description);
    }
  }, [datas]);

  return (
    <div className="">
      <p className="mb-6 text-subtitle-gray">상품 상세 내용</p>
      <>
        {pathName === 'post' ? (
          <ReactQuill
            ref={quillRef}
            modules={modules}
            formats={formats}
            theme="snow"
            placeholder="내용을 입력해주세요..."
            value={contents}
            onChange={setContents}
            style={{ width: '1000px', paddingRight: '60px' }}
            className="ql-toolbar ql-container"
          />
        ) : (
          <ReactQuill
            ref={quillRef}
            modules={modules}
            formats={formats}
            theme="snow"
            placeholder="내용을 입력해주세요..."
            value={editContents}
            onChange={setEditContents}
            style={{ width: '1000px', paddingRight: '60px' }}
            className="ql-toolbar ql-container"
          />
        )}
      </>
    </div>
  );
}
