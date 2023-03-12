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

public class tpexcrawler {

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

			int i = 0;

			while ((data = br.readLine()) != null) {
				String split[] = data.split(",");
				tradedate.add(split[0]);
			}

			/*
			 * for (int s = 1; s < tradedate.size(); s++)
			 * System.out.println(tradedate.get(s) .replace(tradedate.get(s).substring(0,
			 * 4), Integer.toString(Integer.parseInt(tradedate.get(s).substring(0, 4)) -
			 * 1911)) .replace("-", "/"));
			 * 
			 * /* for(int s=1;s<tradedate.size();s++)
			 * System.out.println(tradedate.get(s).replace("-", "")); //給twse的index
			 */
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		for (int s = 1; s < tradedate.size(); s++) {
			String day1 = tradedate.get(s);
			String day = tradedate.get(s)
					.replace(tradedate.get(s).substring(0, 4),
							Integer.toString(Integer.parseInt(tradedate.get(s).substring(0, 4)) - 1911))
					.replace("-", "/");
			try {

				URL url = new URL(
						"https://www.tpex.org.tw/web/stock/aftertrading/otc_quotes_no1430/stk_wn1430_result.php?l=zh-tw&o=csv&d="
								+ day + "&se=EW&s=0,asc,0");
				InputStream openStream = url.openStream();
				InputStreamReader irs = new InputStreamReader(openStream);
				BufferedReader br = null;
				br = new BufferedReader(irs);
				String data = "";
				String key = "資料日期"; 

				while ((data = br.readLine()) != null) {
					String[] split = data.split(",");
					if (split[0].contains(key))
						break;

				} // 將字元串流讀到證券代號前一行第0個index的值為"(元

				BufferedWriter bw = new BufferedWriter(new FileWriter("C://temp/otc" + day1 + ".csv"));
				int n = 0;
				while ((data = br.readLine()) != null) {
					if (n == 0) { 
						bw.write("日期,");
						String split[] = data.split(",");
						bw.write(data);
						bw.newLine();
						System.out.println(Arrays.toString(split));
					} else {
						bw.write(day1 + ",");
						String split[] = data.replace(",", "").replace("\"\"", "\",\"").split(",");

						System.out.println(Arrays.toString(split));
						for (int a = 0; a < split.length; a++)
							bw.write(split[a] + ",");
						bw.newLine();
						bw.flush(); // 將緩衝區資料寫入檔案

					}
					n++;
				} // 繼續將其讀完並寫出csv
				br.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
