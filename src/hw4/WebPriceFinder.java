package hw4;

import hw3.Item;
import hw3.PriceFinder;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.GZIPInputStream;

public class WebPriceFinder extends PriceFinder {
    double actPrice;
    @Override
    protected double getRandomPrice(Item itemPrice) {
        HttpURLConnection con = null;
        try {
            URL url = new URL(itemPrice.getURL());
            con = (HttpURLConnection) url.openConnection();
            // con.setRequestProperty("User-Agent", "...");
            String encoding = con.getContentEncoding();
            if (encoding == null) { encoding = "utf-8"; }
            InputStreamReader reader = null;
            if ("gzip".equals(encoding)) { // gzipped document?
                reader = new InputStreamReader(new GZIPInputStream(con.getInputStream()));
            } else {
                reader = new InputStreamReader(con.getInputStream(), encoding);
            }
            BufferedReader in = new BufferedReader(reader);
            String line;
            while ((line = in.readLine()) != null) {
                //System.out.println(line);
                if (line.contains("attach-baseProductBuyingPrice")) {
                    Pattern pattern = Pattern.compile("\\$(\\d+\\.\\d{2})");
                    Matcher matcher = pattern.matcher(line);
                    while (matcher.find()) {
                        String price = matcher.group(1);
                        actPrice = Double.parseDouble(price);
                    }
                }
                if (line.contains("aria-label")) {
                    actPrice = getWalmartPrice(line);
                    if(actPrice > 0.0) {
                        return actPrice;
                    }
                }
            }
        } catch (IOException e) { e.printStackTrace();
        } finally {
            if (con != null) {  con.disconnect(); }
        }

        return actPrice;
    }

    public double getWalmartPrice(String line){
        Pattern pattern = Pattern.compile("\\$(\\d+\\.\\d{2})");
        Matcher matcher = pattern.matcher(line);
        while (matcher.find()) {
            String price = matcher.group(1);
            double tempPrice = Double.parseDouble(price);
            if(tempPrice > 0.0) {
                return tempPrice;
            }
        }
        return 0;
    }
}
