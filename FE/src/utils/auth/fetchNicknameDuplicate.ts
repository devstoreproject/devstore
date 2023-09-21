import api from 'api';

const fetchNicknameDuplicate = (
  nickname: string,
  setFn: React.Dispatch<React.SetStateAction<boolean>>
) => {
  api
    .get(`/api/users/valid-nick?nickName=${nickname}`)
    .then(() => {
      setFn(true);
      window.alert('사용 가능한 닉네임 입니다.');
    })
    .catch((err) => {
      console.log(err);
      if (err.response.status === 409) {
        setFn(false);
        window.alert('중복된 닉네임 입니다.');
      }
    });
};

export default fetchNicknameDuplicate;
