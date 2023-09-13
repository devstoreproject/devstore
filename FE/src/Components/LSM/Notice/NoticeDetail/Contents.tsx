interface DataProps {
  datas: any;
}

export default function Contents({ datas }: DataProps) {
  return <p className="text-lg text-left">{datas.content}</p>;
}
