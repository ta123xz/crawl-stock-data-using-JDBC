package test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

public class tradedatescawlnouse {
	
	public static void main(String[] args) {
		
		try {
			
			URL url=new URL("https://query1.finance.yahoo.com/v7/finance/download/2330.TW?period1=1611619200&period2=1669420800&interval=1d&events=history&includeAdjustedClose=true");
			InputStream openStream = url.openStream();
			InputStreamReader irs=new InputStreamReader(openStream);
			BufferedReader br = null;
			br=new BufferedReader(irs);
			String data="";
			
		
			
			int i=0;
			
			ArrayList<String> tradedate = new ArrayList<>(); 
			while ((data = br.readLine()) !=null) {				
			    	String split[] = data.split(",");
			    	tradedate.add(split[0]);
			}
			
			
			
			for(int s=1;s<tradedate.size();s++)		
				System.out.println(tradedate.get(s).replace(tradedate.get(s).substring(0,4),Integer.toString(Integer.parseInt(tradedate.get(s).substring(0,4))-1911)).replace("-", "/"));
			
			
			
			/*
			for(int s=1;s<tradedate.size();s++)		
				System.out.println(tradedate.get(s).replace("-", ""));		//給twse的index
			*/
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
