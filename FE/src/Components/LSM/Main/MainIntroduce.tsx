import Member from './Introduce/Member';

export default function MainIntroduce() {
  return (
    <div className="w-full opacity-90 bg-gradient-to-r from-zinc-900 to-gray-600">
      <div className="px-10 m-auto py-28 max-w-365">
        <h2 className="text-3xl font-semibold text-white">개발자를 표현하다</h2>
        <p className="mt-10 mb-20 text-lg text-white">
          데브스토어의 팀원들을 소개합니다 :)
        </p>
        <ul className="flex items-center sm:w-full sm:flex-col sm:m-auto md:w-full">
          {members.map((member) => (
            <Member key={member.id} name={member.name} link={member.link} />
          ))}
        </ul>
      </div>
    </div>
  );
}

const members = [
  {
    id: 1,
    name: '안병욱',
    link: 'https://github.com/boahn',
  },
  {
    id: 2,
    name: '이기호',
    link: 'https://github.com/LEEGIHO94',
  },
  {
    id: 3,
    name: '이혜인',
    link: 'https://github.com/zoeee6',
  },
  {
    id: 4,

    name: '김용희',
    link: 'https://github.com/kyhui1115',
  },
  {
    id: 5,
    name: '김현지',
    link: 'https://github.com/ASOpaper',
  },
  {
    id: 6,
    name: '이승미',
    link: 'https://github.com/mya413',
  },
  {
    id: 7,
    name: '최영웅',
    link: 'https://github.com/memolovel',
  },
];
