package environment;

import agent.TendermintAgentIdentifier;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import sima.core.agent.AgentIdentifier;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class TestTendermintEnvironment {

    @Nested
    @Tag("TendermintEnvironment.constructor")
    @DisplayName("TendermintEnvironment constructor")
    class Constructor {

        @Test
        @DisplayName("Test if constructor does not throws exception with correct args")
        void testConstructorWithCorrectArgs() {
            Map<String, String> args = new HashMap<>();
            assertDoesNotThrow(() -> new TendermintEnvironment("TendermintEnv", args));
        }

    }

    @Nested
    @Tag("TendermintEnvironment.equals")
    @DisplayName("TendermintEnvironment Equals")
    class Equals {

        @Test
        @DisplayName("Test if equals returns false with two empty Environments with different name")
        void testEqualsWithTwoEnvironmentsWithDifferentName() {
            TendermintEnvironment env0 = new TendermintEnvironment("Env0", null);
            TendermintEnvironment env1 = new TendermintEnvironment("Env1", null);

            assertThat(env0).isNotEqualTo(env1);
        }

        @Test
        @DisplayName("Test if equals returns true with two empty Environment with same name")
        void testEqualsWithTwoEmptyEnvironmentsWithSameName() {
            TendermintEnvironment env0 = new TendermintEnvironment("Env", null);
            TendermintEnvironment env1 = new TendermintEnvironment("Env", null);

            assertThat(env0).isEqualTo(env1);
        }

        @Test
        @DisplayName("Test if equals returns false with two Environments with same name but does not contains same agent")
        void testEqualsWithTwoEnvironmentsWithDifferentContain() {
            TendermintEnvironment env0 = new TendermintEnvironment("Env", null);
            TendermintEnvironment env1 = new TendermintEnvironment("Env", null);

            TendermintAgentIdentifier t0 = new TendermintAgentIdentifier("FALSE KEY", "T0", 0, 0);
            TendermintAgentIdentifier t1 = new TendermintAgentIdentifier("FALSE KEY", "T1", 1, 1);

            boolean t0Accepted = env0.acceptAgent(t0);
            assertThat(t0Accepted).isTrue();

            boolean t1Accepted = env1.acceptAgent(t1);
            assertThat(t1Accepted).isTrue();

            assertThat(env0).isNotEqualTo(env1);
        }

        @Test
        @DisplayName("Test if equals returns true with two Environments with same contain and same name")
        void testEqualsWithTwoEnvironmentsWithSameNameAndSameContain() {
            TendermintEnvironment env0 = new TendermintEnvironment("Env", null);
            TendermintEnvironment env1 = new TendermintEnvironment("Env", null);

            TendermintAgentIdentifier t0 = new TendermintAgentIdentifier("FALSE KEY", "T0", 0, 0);
            TendermintAgentIdentifier t1 = new TendermintAgentIdentifier("FALSE KEY", "T0", 0, 0);

            boolean t0Accepted = env0.acceptAgent(t0);
            assertThat(t0Accepted).isTrue();

            boolean t1Accepted = env1.acceptAgent(t1);
            assertThat(t1Accepted).isTrue();

            assertThat(env0).isEqualTo(env1);
        }

    }

    @Nested
    @Tag("TendermintEnvironment.hashCode")
    @DisplayName("TendermintEnvironment hashCode")
    class HashCode {

        @Test
        @DisplayName("Test if two equal Environments have the same hash code")
        void testHashCodeForEqualEnvironments() {
            TendermintEnvironment env0 = new TendermintEnvironment("Env", null);
            TendermintEnvironment env1 = new TendermintEnvironment("Env", null);

            int h0 = env0.hashCode();
            int h1 = env1.hashCode();

            assertThat(h0).isEqualByComparingTo(h1);
        }

    }

    @Nested
    @Tag("TendermintEnvironment.acceptAgent")
    @DisplayName("TendermintEnvironment acceptAgent")
    class AcceptAgent {

        @Test
        @DisplayName("Test if acceptAgent returns false if the agent identifier is not a TendermintAgentIdentifier")
        void testAcceptAgentWithNotTendermintAgentIdentifier() {
            TendermintEnvironment env0 = new TendermintEnvironment("Env", null);

            AgentIdentifier agentIdentifier = new AgentIdentifier("Agent", 0, 0);
            boolean accepted = env0.acceptAgent(agentIdentifier);

            assertThat(accepted).isFalse();
        }

        @Test
        @DisplayName("Test if acceptAgent returns true if the agent identifier is a TendermintAgentIdentifier")
        void testAcceptAgentWithTendermintAgentIdentifier() {
            TendermintEnvironment env0 = new TendermintEnvironment("Env", null);

            TendermintAgentIdentifier tendermintAgentIdentifier = new TendermintAgentIdentifier("FALSE KEY", "Agent", 0, 0);
            boolean accepted = env0.acceptAgent(tendermintAgentIdentifier);

            assertThat(accepted).isTrue();
        }

    }

    @Nested
    @Tag("TendermintEnvironment.getTendermintAgentIdentifier")
    @DisplayName("TendermintEnvironment getTendermintAgentIdentifier")
    class GetTendermintAgentIdentifier {

        @Test
        @DisplayName("Test if getTendermintAgentIdentifier returns null if there is no agent add to the environment with the specified public key")
        void testGetTendermintAgentIdentifierWithNoAddedAgent() {
            TendermintEnvironment env0 = new TendermintEnvironment("Env", null);

            TendermintAgentIdentifier agent = env0.getTendermintAgentIdentifier("FALSE KEY");
            assertThat(agent).isNull();
        }

        @Test
        @DisplayName("Test if getTendermintAgentIdentifier returns not null identifier if there is an added agent with the specified key")
        void testGetTendermintAgentIdentifierWithAddedAgent() {
            TendermintEnvironment env0 = new TendermintEnvironment("Env", null);

            String pubKey = "FALSE KEY";
            TendermintAgentIdentifier t0 = new TendermintAgentIdentifier(pubKey, "T0", 0, 0);

            boolean accepted = env0.acceptAgent(t0);
            assertThat(accepted).isTrue();

            TendermintAgentIdentifier agent = env0.getTendermintAgentIdentifier(pubKey);
            assertThat(agent).isNotNull().isEqualTo(t0);
        }

    }

    @Nested
    @Tag("TendermintEnvironment.agentIsLeaving")
    @DisplayName("TendermintEnvironment agentIsLeaving")
    class AgentIsLeaving {

        @Test
        @DisplayName("Test if agentIsLeaving remove the public key of the agent")
        void testAgentIsLeaving() {
            TendermintEnvironment env0 = new TendermintEnvironment("Env", null);

            String pubKey = "FALSE KEY";
            TendermintAgentIdentifier t0 = new TendermintAgentIdentifier(pubKey, "T0", 0, 0);

            boolean accepted = env0.acceptAgent(t0);
            assertThat(accepted).isTrue();

            env0.agentIsLeaving(t0);

            TendermintAgentIdentifier identifier = env0.getTendermintAgentIdentifier(pubKey);
            assertThat(identifier).isNull();
        }

    }

}
