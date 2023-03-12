package productSystem;

import java.util.List;

import connUtil.bean.ProductBean;

public interface ProductDAO {
	
	
	void crawlToCSVAndDatabase(String otc);
	
	void crawlToCSVAndDatabase();
	
	
	public ProductBean getStockPriceByDate(String input);
	
	void sortYearMonthPrice();
	void bulkInsertcsv();
	void deleteSomeData();
	void unionPriceInnerjoinOperating();
	
	
}
