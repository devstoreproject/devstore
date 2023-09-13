interface DataProps {
  datas: any;
}

export default function Date({ datas }: DataProps) {
  const modifyDate = datas?.modifiedAt?.slice();
  const date = modifyDate?.slice(0, 10);
  return <p className="text-sm font-semibold text-subtitle-gray">{date}</p>;
}
