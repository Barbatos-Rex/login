package barbatos_rex1.auth.util;

public class LongHasher {
    private LongHasher() {
        //Util Class
    }

    public static long hash(Object... objs) {
        long result = 17;
        for (Object o : objs) {
            result *= o.hashCode();
        }
        return result*37;
    }
}
