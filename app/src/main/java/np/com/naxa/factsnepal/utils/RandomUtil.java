package np.com.naxa.factsnepal.utils;

import java.util.Random;

public class RandomUtil {

    public static int getRandomNumber(){
        Random r = new Random();
        int low = 10;
        int high = 100;
        int result = r.nextInt(high-low) + low;
        return result;
    }
}
