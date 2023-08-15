import { BsImage } from 'react-icons/bs';

interface DataProps {
  datas: any;
}

export default function ImageBox({ datas }: DataProps) {
  return (
    <div className="py-20 my-10 bg-white">
      {datas.image != null ? (
        <img
          src={datas?.image?.originalPath}
          alt="공지사항 이미지"
          className="object-cover m-auto w-250 h-120"
        />
      ) : (
        <div className="w-full h-40">
          <BsImage className="w-full my-2 text-8xl text-tab-gray" />
          <span className="text-2xl font-bold text-tab-gray">No Image</span>
        </div>
      )}
    </div>
  );
}
