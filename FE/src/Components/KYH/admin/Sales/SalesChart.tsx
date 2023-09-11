import { useState } from 'react';
import useFetchSales from 'hooks/admin/sales/useFetchSales';
import SalesChartHeader from '../AdminMain/SalesChartHeader';
import Chart from '../AdminMain/Chart';

export default function SalesChart() {
  const [salesStatus, setSalesStatus] = useState([false, true]);
  const sales = useFetchSales(salesStatus);
  return (
    <div className="px-6 py-4 mb-4 bg-white border border-gray-400 rounded-lg w-300">
      <SalesChartHeader
        salesStatus={salesStatus}
        setSalesStatus={setSalesStatus}
      />
      <Chart
        sales={sales}
        salesStatus={salesStatus}
        width={1100}
        height={350}
        margin={{ left: 20, top: 30, right: 20 }}
      />
    </div>
  );
}
