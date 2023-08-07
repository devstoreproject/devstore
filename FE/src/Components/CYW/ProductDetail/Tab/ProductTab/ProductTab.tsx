export default function ProductTab({
  tab,
  description,
}: {
  tab: number;
  description: string;
}) {
  return tab === 1 ? (
    <div className="flex justify-center">
      <div>{description}</div>
    </div>
  ) : null;
}
