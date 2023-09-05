interface OwnProps {
  productName: string;
}

export default function ProductNameContainer({ productName }: OwnProps) {
  return (
    <div className="flex flex-col ml-8">
      <span className="mb-2 font-bold">상품명</span>
      <p className="flex items-center h-10 pl-4 text-sm border border-gray-500 rounded-xl w-128">
        {productName}
      </p>
    </div>
  );
}
