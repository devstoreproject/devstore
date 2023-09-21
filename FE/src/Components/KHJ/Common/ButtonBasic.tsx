interface OwnProps {
  buttonName: string;
  buttonBlack: boolean;
}

function ButtonBasic({
  buttonName,
  buttonBlack,
}: OwnProps): React.ReactElement {
  return (
    <button
      className={`h-14 w-48 rounded-full border
        ${buttonBlack ? 'bg-light-black text-white' : 'border-light-black'}
      `}
    >
      {buttonName}
    </button>
  );
}

export default ButtonBasic;
