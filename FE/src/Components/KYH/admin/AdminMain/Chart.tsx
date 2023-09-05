import type { Sales } from 'model/sales';
import {
  LineChart,
  Line,
  XAxis,
  YAxis,
  CartesianGrid,
  Tooltip,
  Legend,
} from 'recharts';
import salseToChartData from 'utils/sales/salesToChartData';

interface OwnProps {
  sales: Sales[];
  salesStatus: boolean[];
  width: number;
  height: number;
  margin: {
    left: number;
    top: number;
    right: number;
  };
}

export default function Chart({
  sales,
  salesStatus,
  width,
  height,
  margin,
}: OwnProps) {
  const salesData = salseToChartData(sales, salesStatus);
  return (
    <LineChart width={width} height={height} data={salesData} margin={margin}>
      <CartesianGrid strokeDasharray="3 3" />
      <XAxis dataKey="name" />
      <YAxis />
      <Tooltip />
      <Legend />
      <Line type="monotone" dataKey="매출금액" stroke="#00B700" />
    </LineChart>
  );
}
