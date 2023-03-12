package connUtil.bean;

public class ProductBean {//資料處理物件
	private String date;
	private String id;
	private String stockname; 
	private String vol1;
	private String open; 
	private String high;
	private String low;
	private String close;
	

	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getStockname() {
		return stockname;
	}
	public void setStockname(String stockname) {
		this.stockname = stockname;
	}
	public String getVol1() {
		return vol1;
	}
	public void setVol1(String vol1) {
		this.vol1 = vol1;
	}
	
	public String getOpen() {
		return open;
	}
	public void setOpen(String open) {
		this.open = open;
	}
	public String getHigh() {
		return high;
	}
	public void setHigh(String high) {
		this.high = high;
	}
	public String getLow() {
		return low;
	}
	public void setLow(String low) {
		this.low = low;
	}
	public String getClose() {
		return close;
	}
	public void setClose(String close) {
		this.close = close;
	}
	
		
	
//    public void show() { 
//   	String.format("%s%s%s%s%s%s%s%s%s",date,id,stockname,vol1,open,high,low,close );
//        return	this.date+"\t"+this.id +"\t"+this.stockname+"\t"+this.vol1+"\t"+
//    			this.open+"\t"+this.high+"\t"+this.low+"\t"+this.close; 
//    } 
    public void show() {
    	System.out.format("%-15s%-15s%-15s%-15s\n", date,id,stockname,vol1,open,high,low,close);
        //System.out.printf(this.date+"\t"+this.id +"\t"+this.stockname+"\t"+this.vol1+"\t"+this.open+"\t"+this.high+"\t"+this.low+"\t"+this.close);
    } 
	
}
