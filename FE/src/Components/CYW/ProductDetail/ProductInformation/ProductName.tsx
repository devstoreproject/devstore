interface ProductNameProps {
  name: string;
}

export default function ProductName({ name }: ProductNameProps) {
  return (
    <div>
      <h2 className="font-bold">{name}</h2>
    </div>
  );
}
