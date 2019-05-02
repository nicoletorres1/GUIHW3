package hw3;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;

public class PriceFinder{

    protected double getRandomPrice(Item itemPrice){
        Random ran = new Random();
        return (new BigDecimal(ran.doubles(itemPrice.getMinPrice(), (itemPrice.getMaxPrice() + 1)).findFirst().getAsDouble())
                .setScale(2, RoundingMode.CEILING).doubleValue());
    }

}
