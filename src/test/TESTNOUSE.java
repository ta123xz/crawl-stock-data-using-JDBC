package test;

import javax.swing.JOptionPane;

import connUtil.bean.ProductBean;
import productSystem.impl.ProductDAOImpl;

public class TESTNOUSE {

	public static void main(String[] args) {
		
		ProductDAOImpl s=new ProductDAOImpl();
		ProductBean pb=new ProductBean();
		//s.crawlToCSVAndDatabase();
		
		//String input= JOptionPane.showInputDialog("請輸入日期:格式為yyyy-mm-dd");
		//String inputdate=JOptionPane.showInputDialog("請輸入日期");
		//String inputid=JOptionPane.showInputDialog("請輸入代號");
		//s.getStockPriceByDateId(inputdate,inputid);
		//s.getStockPriceByDate(input);
		//s.sortYearMonthPrice();
		//s.bulkInsertcsv();
		//s.deleteSomeData();
		//s.unionPriceInnerjoinOperating();
		//s.updatePrice(pb);
		//s.updateP(pb);
		
	}
	
	
	
	

}
