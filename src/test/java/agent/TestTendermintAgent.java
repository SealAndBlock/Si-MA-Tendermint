package agent;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.security.KeyPair;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class TestTendermintAgent {

    @Nested
    @Tag("TendermintAgent.constructor")
    @DisplayName("TendermintAgent constructor")
    class Constructor {

        @Test
        @DisplayName("Test constructor does not throw exception with correct args")
        void testConstructorWithCorrectArgs() {
            assertDoesNotThrow(() -> new TendermintAgent("Agent", 0, 0, null));
        }

    }

    @Nested
    @Tag("TendermintAgent.equals")
    @DisplayName("TendermintAgent equals")
    class Equals {

        @Test
        @DisplayName("Test if equals always returns false even if two agents have been constructed in the same way")
        void testEquals() {
            TendermintAgent a0 = new TendermintAgent("Env", 0, 0, null);
            TendermintAgent a1 = new TendermintAgent("Env", 0, 0, null);

            assertThat(a0).isNotEqualTo(a1);
        }

    }

    @Nested
    @Tag("TendermintAgent.hashCode")
    @DisplayName("TendermintAgent hashCode")
    class HashCode {

        @Test
        @DisplayName("Test if hashCode does not throw exception")
        void testHashCodeForEqualEnvironments() {
            TendermintAgent a0 = new TendermintAgent("Env", 0, 0, null);

            assertDoesNotThrow(a0::hashCode);
        }

    }

    @Nested
    @Tag("TendermintAgent.getAgentIdentifier")
    @DisplayName("TendermintAgent getAgentIdentifier")
    class GetAgentIdentifier {

        @Test
        @DisplayName("Test if getAgentIdentifier returns correct agent identifier")
        void testGetAgentIdentifier() {
            TendermintAgent t0 = new TendermintAgent("Agent", 0, 0, null);
            TendermintAgentIdentifier identifier = t0.getAgentIdentifier();

            assertThat(identifier).isNotNull();
            assertThat(identifier.getPublicKey()).isEqualTo(t0.getPublicKey());
            assertThat(identifier.getAgentName()).isEqualTo(t0.getAgentName());
            assertThat(identifier.getAgentSequenceId()).isEqualTo(t0.getSequenceId());
            assertThat(identifier.getAgentUniqueId()).isEqualTo(t0.getUniqueId());
        }

    }

    @Nested
    @Tag("TendermintAgent.getKeyPair")
    @DisplayName("TendermintAgent getKeyPair")
    class GetKeyPair {

        @Test
        @DisplayName("Test getKeyPair never returns null")
        void testGetKeyPair() {
            TendermintAgent t0 = new TendermintAgent("Agent", 0, 0, null);
            KeyPair keyPair = t0.getKeyPair();

            assertThat(keyPair).isNotNull();
        }
    }

    @Nested
    @Tag("TendermintAgent.getPublicKey")
    @DisplayName("TendermintAgent getPublicKey")
    class getPublicKey {

        @Test
        @DisplayName("Test getPublicKey never returns null")
        void testGetKeyPair() {
            TendermintAgent t0 = new TendermintAgent("Agent", 0, 0, null);
            String publicKey = t0.getPublicKey();

            assertThat(publicKey).isNotNull();
        }
    }

    @Nested
    @Tag("TendermintAgent.getPrivateKey")
    @DisplayName("TendermintAgent getPrivateKey")
    class GetPrivateKey {

        @Test
        @DisplayName("Test getPrivateKey never returns null")
        void testGetKeyPair() {
            TendermintAgent t0 = new TendermintAgent("Agent", 0, 0, null);
            String privateKey = t0.getPrivateKey();

            assertThat(privateKey).isNotNull();
        }
    }

}
