package util;

import java.util.function.Supplier;

public class Utils {

    // Constructors.

    private Utils() {
    }

    // Methods.

    public static Supplier<IllegalArgumentException> illegalArgumentExceptionSupplier(String msg) {
        return () -> new IllegalArgumentException(msg);
    }

}
