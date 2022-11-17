package barbatos_rex1.auth.util;

public class Validations {
    private Validations() {
        //Util Class
    }

    public static boolean assertAnyNull(Object... objs) {
        for (Object o :
                objs) {
            if (o == null) {
                return false;
            }
        }
        return true;
    }


}
