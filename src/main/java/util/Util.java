package util;

import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class Util {

    // Constructors.

    private Util() {
    }

    // Methods.

    public static @NotNull Supplier<IllegalArgumentException> illegalArgumentExceptionSupplier(String msg) {
        return () -> new IllegalArgumentException(msg);
    }

}
