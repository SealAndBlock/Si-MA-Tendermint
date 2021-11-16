package util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.function.Supplier;

import static org.assertj.core.api.Assertions.assertThat;

public class TestUtil {

    @Nested
    @Tag("IllegalArgumentExceptionSupplier")
    @DisplayName("IllegalArgumentExceptionSupplier")
    class IllegalArgumentExceptionSupplierTest {

        @Test
        @DisplayName("Test if illegalArgumentExceptionSupplier never returns null")
        void testIllegalArgumentExceptionSupplier() {
            Supplier<IllegalArgumentException> supplier = Util.illegalArgumentExceptionSupplier("msg");
            assertThat(supplier).isNotNull();
        }

    }

}
