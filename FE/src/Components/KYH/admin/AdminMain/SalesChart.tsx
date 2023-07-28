import Chart from './Chart';
import SalesChartHeader from './SalesChartHeader';

export default function SalesChart() {
  return (
    <div className="px-6 py-4 bg-white rounded-lg w-152 shadow-signBox">
      <SalesChartHeader />
      <Chart />
    </div>
  );
}
