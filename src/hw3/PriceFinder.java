package hw3;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;

public class PriceFinder{
    private static double maxPrice = 369.99;
    private static double minPrice = 61.67;
    public static double getRandomPrice(){
        Random ran = new Random();
        return (new BigDecimal(ran.doubles(minPrice, (maxPrice + 1)).findFirst().getAsDouble())
                .setScale(2, RoundingMode.CEILING).doubleValue());
    }
}
