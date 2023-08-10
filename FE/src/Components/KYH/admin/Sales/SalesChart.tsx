import Chart from './Chart';
import SalesChartHeader from './SalesChartHeader';

export default function SalesChart() {
  return (
    <div className="px-6 py-4 mb-4 bg-white border border-gray-400 rounded-lg w-300">
      <SalesChartHeader />
      <Chart />
    </div>
  );
}
