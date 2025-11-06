import java.util.*;

public class Utils {
    private static Random R = new Random();
    public static int rnd(int a, int b) {
        return a + R.nextInt(b - a + 1);
    }
}

