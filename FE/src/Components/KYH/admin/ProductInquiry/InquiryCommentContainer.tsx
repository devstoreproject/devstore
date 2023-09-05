interface OwnProps {
  comment: string;
}

export default function InquiryCommentContainer({ comment }: OwnProps) {
  return (
    <div className="flex flex-col mt-4 ml-8">
      <span className="mb-2 font-bold">문의내용</span>
      <p className="flex h-32 pt-4 pl-4 overflow-y-scroll text-sm border border-gray-500 rounded-xl w-128">
        {comment}
      </p>
    </div>
  );
}
