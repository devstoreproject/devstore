import DOMPurify from 'isomorphic-dompurify';

export default function ProductTab({
  tab,
  description,
}: {
  tab: number;
  description: string;
}) {
  return tab === 0 ? (
    <div className="flex justify-center">
      <div
        className="ql-editor"
        dangerouslySetInnerHTML={{
          __html: DOMPurify.sanitize(description),
        }}
      />
    </div>
  ) : null;
}
