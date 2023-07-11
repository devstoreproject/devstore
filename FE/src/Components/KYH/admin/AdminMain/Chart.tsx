import {
  LineChart,
  Line,
  XAxis,
  YAxis,
  CartesianGrid,
  Tooltip,
  Legend,
} from 'recharts';

export default function Chart() {
  const data = [
    {
      name: '5월',
      매출금액: 1200000,
    },
    {
      name: '6월',
      매출금액: 890000,
    },
    {
      name: '7월',
      매출금액: 800000,
    },
    {
      name: '8월',
      매출금액: 1000000,
    },
    {
      name: '9월',
      매출금액: 1200000,
    },
    {
      name: '10월',
      매출금액: 1800000,
    },
    {
      name: '11월',
      매출금액: 2400000,
    },
    {
      name: '12월',
      매출금액: 3000000,
    },
  ];

  return (
    <LineChart
      width={550}
      height={250}
      data={data}
      margin={{ left: 16, top: 30, right: 16 }}
    >
      <CartesianGrid strokeDasharray="3 3" />
      <XAxis dataKey="name" />
      <YAxis />
      <Tooltip />
      <Legend />
      <Line type="monotone" dataKey="매출금액" stroke="#00B700" />
    </LineChart>
  );
}
