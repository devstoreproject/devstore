interface DataProps {
  datas: any;
}

export default function TabTitle({ datas }: DataProps) {
  return (
    <h3 className="px-4 py-1 text-sm font-semibold bg-tab-gray rounded-2xl">
      {datas?.category === 'OPERATING'
        ? '운영정책'
        : datas?.category === 'EVENT'
        ? '이벤트'
        : '업데이트'}
    </h3>
  );
}
