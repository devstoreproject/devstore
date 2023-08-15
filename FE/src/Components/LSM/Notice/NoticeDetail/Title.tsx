interface DataProps {
  datas: any;
}

export default function Title({ datas }: DataProps) {
  return (
    <h2 className="my-10 text-2xl font-semibold leading-10 w-250 line-clamp-2">
      {datas.title}
    </h2>
  );
}
