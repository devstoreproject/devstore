import SleeperDateContainer from './SleeperDateContainer';

export default function SleeperAccountForm() {
  return (
    <form className="flex flex-col p-5 mt-40 bg-white rounded-xl w-96 h-128 shadow-signBox">
      <span className="mt-4 text-xl font-bold text-center">휴면 계정 안내</span>
      <p className="mt-8 text-center">
        회원님이 1년 이상 로그인 하지 않아
        <br />
        계정을 안전하게 보호하기 위해
        <br />
        휴먼 상태로 전환 되었습니다.
      </p>
      <SleeperDateContainer />
      <p className="pb-5 mt-10 text-sm text-center border-b border-black">
        서비스를 계속 이용 하시려면 [ 해제하기 ] 버튼을 눌러주세요
      </p>
      <div className="flex justify-around w-full mt-4">
        <button className="mt-6 w-2/5 text-black border bg-white h-11 rounded-xl shadow-btn active:shadow-none active:ml-0.5 active:mt-6.5 duration-100">
          취소
        </button>
        <button className="mt-6 w-2/5 text-black bg-neon-green h-11 rounded-xl shadow-btn active:shadow-none active:ml-0.5 active:mt-6.5 duration-100">
          해제하기
        </button>
      </div>
    </form>
  );
}
