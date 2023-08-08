interface OwnProps {
  pageName?: string;
  pageHistory: string;
}

export default function PageHistory({
  pageName,
  pageHistory,
}: OwnProps): React.ReactElement {
  return (
    <>
      <p className="pl-5 text-sm mt-8 mb-6 text-subtitle-gray">{pageHistory}</p>
      {pageName !== undefined ? (
        <h1 className="text-center mb-10 text-2xl font-bold">{pageName}</h1>
      ) : null}
    </>
  );
}
