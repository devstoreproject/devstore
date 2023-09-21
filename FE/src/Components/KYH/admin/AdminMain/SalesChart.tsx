import { useState } from 'react';
import Chart from './Chart';
import SalesChartHeader from './SalesChartHeader';
import useFetchSales from 'hooks/admin/sales/useFetchSales';

export default function SalesChart() {
  const [salesStatus, setSalesStatus] = useState([false, true]);
  const sales = useFetchSales(salesStatus);

  return (
    <div className="px-6 py-4 bg-white rounded-lg w-152 shadow-signBox h-80">
      <SalesChartHeader
        salesStatus={salesStatus}
        setSalesStatus={setSalesStatus}
      />
      <Chart
        sales={sales}
        salesStatus={salesStatus}
        width={550}
        height={230}
        margin={{ left: 16, top: 10, right: 16 }}
      />
    </div>
  );
}
