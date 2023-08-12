export default function ProductTab({
  tab,
  description,
}: {
  tab: number;
  description: string;
}) {
  return tab === 0 ? (
    <div className="flex justify-center">
      <div>{description}</div>
    </div>
  ) : null;
}
