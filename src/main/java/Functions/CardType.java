package Functions;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public enum CardType {
    INFANTRY, CAVALRY, ARTILLERY;
    private static final List<CardType> VALUES = Collections.unmodifiableList(Arrays.asList(values()));
    private static final int SIZE = VALUES.size();
    private static final Random RANDOM = new Random();
    /**
     * get random card
     * @return random card type
     */

    public static CardType randomCard(){
        return VALUES.get(RANDOM.nextInt(SIZE));

    }

}
