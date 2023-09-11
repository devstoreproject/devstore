interface OwnProps {
  name: string;
}

export default function ProductName({ name }: OwnProps) {
  return (
    <div>
      <h2 className="font-bold">{name}</h2>
    </div>
  );
}
