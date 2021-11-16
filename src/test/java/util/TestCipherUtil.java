package util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.security.Key;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.junit.Assert.assertThrows;

public class TestCipherUtil {

    // Constants.

    private static final int CORRECT_KEY_SIZE = 512;

    // Tests.

    @Nested
    @Tag("CipherUtil.Key")
    @DisplayName("CipherUtil Key Manipulation")
    class KeyManipulation {

        @Test
        @DisplayName("Test if extractKeyString returns a non null string with a correct key")
        void testExtractKeyStringWithCorrectKey() {
            KeyPair keyPair = CipherUtil.rsaGenerateKeyPair(CORRECT_KEY_SIZE);
            if (keyPair != null) {
                Key key = keyPair.getPublic();
                String stringKey = CipherUtil.extractKeyString(key);
                assertThat(stringKey).isNotNull();
            } else
                fail("keyPair null");
        }

        @Test
        @DisplayName("Test if extractKeyString throws NullPointerException with null key")
        void testExtractKeyStringWithNullKey() {
            assertThrows(NullPointerException.class, () -> CipherUtil.extractKeyString(null));
        }

    }

    @Nested
    @Tag("CipherUtil.RSA")
    @DisplayName("CipherUtil RSA")
    class RSA {

        @Nested
        @Tag("RSA.keyPairGeneration")
        @DisplayName("RSA KeyPair Generation")
        class KeyPairGeneration {

            @Test
            @DisplayName("Test if rsaGenerateKeyPair returns correctly KeyPair with correct key size")
            void testRsaGenerateKeyPairWithCorrectKeySize() {
                KeyPair keyPair = CipherUtil.rsaGenerateKeyPair(CORRECT_KEY_SIZE);
                assertThat(keyPair).isNotNull();
            }

            @Test
            @DisplayName("Test if rsaGenerateKeyPair returns null with non correct key size")
            void testRsaGenerateKeyPair() {
                KeyPair keyPair = CipherUtil.rsaGenerateKeyPair(0);
                assertThat(keyPair).isNull();

                keyPair = CipherUtil.rsaGenerateKeyPair(256);
                assertThat(keyPair).isNull();

                keyPair = CipherUtil.rsaGenerateKeyPair(5);
                assertThat(keyPair).isNull();
            }

        }

        @Nested
        @Tag("RSA.extractKey")
        @DisplayName("RSA extract key")
        class ExtractKey {

            @Nested
            @Tag("RSA.PublicKeyExtraction")
            @DisplayName("RSA public key extraction")
            class ExtractPublicKey {

                @Test
                @DisplayName("Test if rsaExtractPublicKey throws NullPointerException with null public key")
                void testRsaExtractPublicKeyWithNullPublicKey() {
                    assertThrows(NullPointerException.class, () -> CipherUtil.rsaExtractPublicKey(null));
                }

                @Test
                @DisplayName("Test if rsaExtractPublicKey returns null with empty string public key")
                void testRsaExtractPublicKeyWithEmptyPublicKey() {
                    PublicKey publicKey = CipherUtil.rsaExtractPublicKey("");
                    assertThat(publicKey).isNull();
                }

                @Test
                @DisplayName("Test if rsaExtractPublicKey returns correct PublicKey if the string is correct")
                void testRsaExtractPublicKeyWithCorrectString() {
                    PublicKey publicKey = Objects.requireNonNull(CipherUtil.rsaGenerateKeyPair(CORRECT_KEY_SIZE)).getPublic();
                    String pubKey = CipherUtil.extractKeyString(publicKey);
                    PublicKey pK = CipherUtil.rsaExtractPublicKey(pubKey);
                    assertThat(pK).isNotNull();
                }

            }

            @Nested
            @Tag("RSA.PrivateKeyExtraction")
            @DisplayName("RSA private key extraction")
            class ExtractPrivateKey {

                @Test
                @DisplayName("Test if rsaExtractPrivateKey throws NullPointerException with null public key")
                void testRsaExtractPrivateKeyWithNullPublicKey() {
                    assertThrows(NullPointerException.class, () -> CipherUtil.rsaExtractPrivateKey(null));
                }

                @Test
                @DisplayName("Test if rsaExtractPrivateKey returns null with empty string public key")
                void testRsaExtractPrivateKeyWithEmptyPublicKey() {
                    PrivateKey privateKey = CipherUtil.rsaExtractPrivateKey("");
                    assertThat(privateKey).isNull();
                }

                @Test
                @DisplayName("Test if rsaExtractPrivateKey returns correct PublicKey if the string is correct")
                void testRsaExtractPrivateKeyWithCorrectString() {
                    PrivateKey privateKey = Objects.requireNonNull(CipherUtil.rsaGenerateKeyPair(CORRECT_KEY_SIZE)).getPrivate();
                    String priKey = CipherUtil.extractKeyString(privateKey);
                    PrivateKey pK = CipherUtil.rsaExtractPrivateKey(priKey);
                    assertThat(pK).isNotNull();
                }

            }

            @Nested
            @Tag("RSA.Cipher")
            @DisplayName("RSA Cipher")
            class Cipher {

                @Test
                @DisplayName("Test if rsaCipher throws NullPointerException with null data")
                void testRsaCipherWithNullData() {
                    Key key = Objects.requireNonNull(CipherUtil.rsaGenerateKeyPair(CORRECT_KEY_SIZE)).getPublic();
                    assertThrows(NullPointerException.class, () -> CipherUtil.rsaCipher(key, null));
                }

                @Test
                @DisplayName("Test if rsaCipher returns null with null key")
                void testRsaCipherWithNullKey() {
                    String ciphered = CipherUtil.rsaCipher(null, "Hello");
                    assertThat(ciphered).isNull();
                }

                @Test
                @DisplayName("Test if rsaCipher returns correct ciphered string with correct key")
                void testRsaCipherWithCorrectKey() {
                    KeyPair keyPair = CipherUtil.rsaGenerateKeyPair(CORRECT_KEY_SIZE);
                    Key pubKey = Objects.requireNonNull(keyPair).getPublic();
                    Key priKey = Objects.requireNonNull(keyPair).getPrivate();

                    String data = "Hello";

                    String cipheredWithPub = CipherUtil.rsaCipher(pubKey, data);
                    String cipheredWithPri = CipherUtil.rsaCipher(priKey, data);

                    assertThat(cipheredWithPub).isNotNull();
                    assertThat(cipheredWithPri).isNotNull();
                }

            }

            @Nested
            @Tag("RSA.Decipher")
            @DisplayName("RSA Decipher")
            class Decipher {

                @Test
                @DisplayName("Test if rsaDecipher throws NullPointerException with null data")
                void testRsaDecipherWithNullData() {
                    Key key = Objects.requireNonNull(CipherUtil.rsaGenerateKeyPair(CORRECT_KEY_SIZE)).getPublic();
                    assertThrows(NullPointerException.class, () -> CipherUtil.rsaDecipher(key, null));
                }

                @Test
                @DisplayName("Test if rsaDecipher returns null with null key")
                void testRsaDecipherWithNullKey() {
                    String deciphered = CipherUtil.rsaDecipher(null, "Hello");
                    assertThat(deciphered).isNull();
                }

                @Test
                @DisplayName("Test if rsaDecipher correctly decipher data")
                void testRsaDecipherCorrectlyDecipher() {
                    String data = "Hello";

                    KeyPair keyPair = CipherUtil.rsaGenerateKeyPair(CORRECT_KEY_SIZE);
                    Key pubKey = Objects.requireNonNull(keyPair).getPublic();
                    Key priKey = Objects.requireNonNull(keyPair).getPrivate();

                    String cipheredWithPri = CipherUtil.rsaCipher(priKey, data);
                    String decipheredWithPub = CipherUtil.rsaDecipher(pubKey, cipheredWithPri);
                    assertThat(decipheredWithPub).isEqualTo(data);

                    String cipheredWithPub = CipherUtil.rsaCipher(pubKey, data);
                    String decipheredWithPri = CipherUtil.rsaDecipher(priKey, cipheredWithPub);
                    assertThat(decipheredWithPri).isEqualTo(data);
                }

                @Test
                @DisplayName("Test if rsaDecipher returns null if the decipher fail")
                void testRsaDecipherReturnsNullWhenDecipherFail() {
                    String data = "Hello";

                    KeyPair keyPair = CipherUtil.rsaGenerateKeyPair(CORRECT_KEY_SIZE);
                    Key priKey = Objects.requireNonNull(keyPair).getPrivate();

                    KeyPair otherKeyPair = CipherUtil.rsaGenerateKeyPair(CORRECT_KEY_SIZE);
                    Key otherPubKey = Objects.requireNonNull(otherKeyPair).getPublic();

                    // Decipher with the same key
                    String cipheredWithPri = CipherUtil.rsaCipher(priKey, data);
                    String decipheredWithPri = CipherUtil.rsaDecipher(priKey, cipheredWithPri);
                    assertThat(decipheredWithPri).isNull();

                    // Decipher with not associated key
                    cipheredWithPri = CipherUtil.rsaCipher(priKey, data);
                    String decipheredWithOtherPub = CipherUtil.rsaDecipher(otherPubKey, cipheredWithPri);
                    assertThat(decipheredWithOtherPub).isNull();
                }

            }

        }

    }

}
