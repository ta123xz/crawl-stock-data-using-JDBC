package test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

public class twsecrawler {

	public static void main(String[] args) {
		ArrayList<String> tradedate = new ArrayList<>();
		try {
			URL url = new URL(
					"https://query1.finance.yahoo.com/v7/finance/download/2330.TW?period1=1668816000&period2=1669420800&interval=1d&events=history&includeAdjustedClose=true");
			InputStream openStream = url.openStream();
			InputStreamReader irs = new InputStreamReader(openStream);
			BufferedReader br = null;
			br = new BufferedReader(irs);
			String data = "";

			//int i=0;

			
			while ((data = br.readLine()) != null) {
				String split[] = data.split(",");
				tradedate.add(split[0]);
			}
			
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		//爬yahoo finance網站的台積電歷史股價作為twse中url的查詢參數,以ArrayList儲存日期
		
		
		//利用ArrayList日期串接查詢參數
		for(int s=1;s<tradedate.size();s++) {
			String day=tradedate.get(s).replace("-", "");
			String day1=tradedate.get(s);
			try {
				
				URL url = new URL(
						"https://www.twse.com.tw/zh/exchangeReport/MI_INDEX?response=csv&date="+day+"&type=ALLBUT0999");
				InputStream openStream = url.openStream();
				InputStreamReader irs = new InputStreamReader(openStream);
				BufferedReader br = null;
				br = new BufferedReader(irs);
				String data = "";
				String key = "\"(元";
	
				while ((data = br.readLine()) != null) {
					String[] split = data.split(",");
					if (split[0].equals(key))
						break;
	
				} // 將字元串流讀到證券代號前一行第0個index的值為"(元
	
				BufferedWriter bw = new BufferedWriter(new FileWriter("C://temp/twse"+day+".csv"));
				int n=0;
				while ((data = br.readLine()) != null) {
					if(n==0) {
						bw.write("日期,");
						String split[] = data.split(",");
						bw.write(data);
						bw.newLine();
						System.out.println(Arrays.toString(split));
					}
					else
					{
						bw.write(day1+",");
						String split[] = data.replace(",","").replace("\"\"","\",\"").split(",");
						System.out.println(Arrays.toString(split));
						for(int a=0;a<split.length;a++)
							bw.write(split[a]+","); 
						bw.newLine();
						bw.flush();
					}
					n++;
				} // 繼續將其讀完並寫出csv
				br.close();
			} 
			
			catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
