package barbatos_rex1.auth;

public class LongHasher {
    public static long hash(Object... objs) {
        long result = 17;
        for (Object o : objs) {
            result *= o.hashCode();
        }
        return result*37;
    }
}
