package hw4;

import hw3.Item;
import hw3.PriceFinder;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.zip.GZIPInputStream;

public class WebPriceFinder extends PriceFinder {
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

            }
        } catch (IOException e) { e.printStackTrace();
        } finally {
            if (con != null) {  con.disconnect(); }
        }

        return super.getRandomPrice(itemPrice);
    }
}
