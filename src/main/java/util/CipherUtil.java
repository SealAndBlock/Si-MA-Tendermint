package util;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Optional;

import static util.EncoderUtil.decodeBase64;
import static util.EncoderUtil.encodeBase64;

public class CipherUtil {

    // Static.

    public static final String RSA_ALGORITHM = "RSA";
    private static final String RSA_ECB_OAEP_ALGORITHM = "RSA";
    public static final String SHA1PRNG_ALGORITHM = "SHA1PRNG";

    public static final String SUN_PROVIDER = "SUN";

    // Constructor.

    private CipherUtil() {
    }

    // Methods.

    /**
     * @param key the key to base64 encoded to string
     *
     * @return a base64 encoded string which is the value of the {@link Key}.
     */
    public static String extractKeyString(Key key) {
        return encodeBase64(Optional.of(key).get().getEncoded());
    }

    /**
     * Extract a {@link PublicKey} from the pubKey string for the RSA algorithm.
     * <p>
     * <strong>IMPORTANT!</strong> The pubKey string must be base64 encoded.
     *
     * @param pubKey the public key base64 encoded in string
     *
     * @return a {@link PublicKey} extract from the pubKey string. If the extraction failed, returns null.
     */
    public static PublicKey rsaExtractPublicKey(String pubKey) {
        try {
            return KeyFactory.getInstance(RSA_ALGORITHM).generatePublic(new X509EncodedKeySpec(decodeBase64(pubKey)));
        } catch (InvalidKeySpecException | NoSuchAlgorithmException e) {
            return null;
        }
    }

    /**
     * Extract {@link PrivateKey} from the priKey string for the RSA algorithm.
     * <p>
     * <strong>IMPORTANT!</strong> The pubKey string must be base64 encoded.
     *
     * @param priKey the private key base64 encoded in string
     *
     * @return a {@link PrivateKey} extract from the priKey string. If the extraction failed, returns null.
     */
    public static PrivateKey rsaExtractPrivateKey(String priKey) {
        try {
            return KeyFactory.getInstance(RSA_ALGORITHM).generatePrivate(new PKCS8EncodedKeySpec(decodeBase64(priKey)));
        } catch (InvalidKeySpecException | NoSuchAlgorithmException e) {
            return null;
        }
    }

    /**
     * Cipher data with the key pass in parameter by using the RSA algorithm.
     *
     * @param key  the key used to cipher data
     * @param data the data to cipher
     *
     * @return a string which is the result of the encryption of the data.
     */
    public static String rsaCipher(Key key, String data) {
        try {
            Cipher cipher = Cipher.getInstance(RSA_ECB_OAEP_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, key);

            return encodeBase64(cipher.doFinal(Optional.of(data).get().getBytes()));
        } catch (NoSuchPaddingException | IllegalBlockSizeException | NoSuchAlgorithmException | BadPaddingException | InvalidKeyException e) {
            return null;
        }
    }

    /**
     * Decipher data with the key pass in parameter by using the RSA algorithm.
     *
     * @param key  the key used to decipher data
     * @param data the data to decipher
     *
     * @return a string which is the result of the decryption of the data.
     */
    public static String rsaDecipher(Key key, String data) {
        try {
            Cipher cipher = Cipher.getInstance(RSA_ECB_OAEP_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, key);

            return new String(cipher.doFinal(decodeBase64(data)));

        } catch (NoSuchPaddingException | IllegalBlockSizeException | NoSuchAlgorithmException | BadPaddingException
                | InvalidKeyException e) {
            return null;
        }
    }

    /**
     * Generate a {@link KeyPair} for the RSA algorithm.
     *
     * @param keySize the size of a key
     *
     * @return a {@link KeyPair} for the RSA algorithm. If the generation failed, returns null.
     */
    public static KeyPair rsaGenerateKeyPair(int keySize) {

        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(RSA_ALGORITHM);
            keyPairGenerator.initialize(keySize, SecureRandom.getInstance(SHA1PRNG_ALGORITHM, SUN_PROVIDER));
            return keyPairGenerator.generateKeyPair();
        } catch (Exception e) {
            return null;
        }
    }

}
