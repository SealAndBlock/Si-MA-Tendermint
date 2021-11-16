package agent;

import org.jetbrains.annotations.NotNull;
import sima.core.agent.SimaAgent;
import util.CipherUtil;

import java.security.KeyPair;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import static util.CipherUtil.extractKeyString;

public class TendermintAgent extends SimaAgent {

    // Constants.

    /**
     * Key size used to generate RSA KeyPair
     */
    public static final int KEY_SIZE = 4096;

    // Variables.

    private final KeyPair keyPair;

    // Constructors.

    /**
     * @param agentName  the agent name
     * @param sequenceId the agent sequence id
     * @param uniqueId   the agent unique id
     * @param args       the argument of the agent
     *
     * @throws RuntimeException if the RSA KeyPair generation failed
     * @see CipherUtil#rsaGenerateKeyPair(int)
     */
    public TendermintAgent(String agentName, int sequenceId, int uniqueId, Map<String, String> args) {
        super(agentName, sequenceId, uniqueId, args);
        this.keyPair = Optional.ofNullable(CipherUtil.rsaGenerateKeyPair(KEY_SIZE))
                .orElseThrow(() -> new RuntimeException("Cannot generate RSA KeyPair"));
    }

    // Methods.

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TendermintAgent that)) return false;
        if (!super.equals(o)) return false;
        return keyPair.equals(that.keyPair);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), keyPair);
    }

    // Getters.

    @Override
    public @NotNull TendermintAgentIdentifier getAgentIdentifier() {
        return new TendermintAgentIdentifier(getPublicKey(), getAgentName(), getSequenceId(), getUniqueId());
    }

    public @NotNull KeyPair getKeyPair() {
        return keyPair;
    }

    public @NotNull String getPublicKey() {
        return extractKeyString(keyPair.getPublic());
    }

    public @NotNull String getPrivateKey() {
        return extractKeyString(keyPair.getPrivate());
    }
}
