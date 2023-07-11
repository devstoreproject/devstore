import Chart from './Chart';
import SalesChartHeader from './SalesChartHeader';

export default function SalesChart() {
  return (
    <div className="w-1/2 py-4 pl-6 bg-white rounded-lg shadow-signBox">
      <SalesChartHeader />
      <Chart />
    </div>
  );
}
