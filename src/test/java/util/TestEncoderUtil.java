package util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestEncoderUtil {

    @Nested
    @Tag("EncoderUtil.base64")
    @DisplayName("EncoderUtil Base64")
    class Base64 {

        @Nested
        @Tag("Base64.encode")
        @DisplayName("Base64 encode")
        class Encode {

            @Test
            @DisplayName("Test encodeBase64 returns empty string with empty array")
            void testEncodeBase64WithEmptyArray() {
                byte[] emptyArray = new byte[0];
                String encoded = EncoderUtil.encodeBase64(emptyArray);
                assertThat(encoded).isEmpty();
            }

            @Test
            @DisplayName("Test encodeBase64 throws NullPointerException with null array")
            void testEncodeBase64WithNullArray() {
                assertThrows(NullPointerException.class, () -> EncoderUtil.encodeBase64(null));
            }

            @Test
            @DisplayName("Test encodeBase64 does not returns the same string")
            void testEncodeBase64DoesNotReturnsSameString() {
                String msg = "hello";

                String encoded = EncoderUtil.encodeBase64(msg.getBytes());
                assertThat(encoded).isNotEqualTo(msg);

                String doubleEncoded = EncoderUtil.encodeBase64(encoded.getBytes());
                assertThat(doubleEncoded).isNotEqualTo(encoded);
            }
        }

        @Nested
        @Tag("Base64.decode")
        @DisplayName("Base64 decode")
        class Decode {

            @Test
            @DisplayName("Test if decodeBase64 returns empty arrays with empty string")
            void testDecodeBase64WithEmptyString() {
                byte[] decoded = EncoderUtil.decodeBase64("");
                assertThat(decoded).isEmpty();
            }

            @Test
            @DisplayName("Test if decodeBase64 throws NullPointerException with null string")
            void testDecodeBase64WithNullString() {
                assertThrows(NullPointerException.class, () -> EncoderUtil.decodeBase64(null));
            }

            @Test
            @DisplayName("Test if decodeBase64 correctly decode an encoded string")
            void testDecodeBase64DecodeCorrectly() {
                String msg = "Hello";
                String encoded = EncoderUtil.encodeBase64(msg.getBytes());
                String decoded = new String(EncoderUtil.decodeBase64(encoded));
                assertThat(decoded).isEqualTo(msg);
            }

        }

    }

}
