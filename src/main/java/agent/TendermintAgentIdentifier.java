package agent;

import sima.core.agent.AgentIdentifier;

import java.util.Objects;
import java.util.Optional;

import static util.Util.illegalArgumentExceptionSupplier;

public class TendermintAgentIdentifier extends AgentIdentifier {

    // Variables.

    private final String publicKey;

    // Constructors.

    /**
     * @param publicKey       the public key of the agent
     * @param agentName       the agent name
     * @param agentSequenceId the agent sequence id
     * @param agentUniqueId   the agent number id
     *
     * @throws NullPointerException     if agentName is null
     * @throws IllegalArgumentException if agentSequenceId or agentNumberId or less than 0 or if the agentName is empty. Also, if the public key is
     *                                  null or empty.
     */
    public TendermintAgentIdentifier(String publicKey, String agentName, int agentSequenceId, int agentUniqueId) {
        super(agentName, agentSequenceId, agentUniqueId);
        this.publicKey = Optional.ofNullable(publicKey).orElseThrow(illegalArgumentExceptionSupplier("PublicKey cannot be null"));
        if (this.publicKey.isEmpty())
            throw new IllegalArgumentException("PublicKey cannot be null");
    }

    // Methods.

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TendermintAgentIdentifier that)) return false;
        if (!super.equals(o)) return false;
        return publicKey.equals(that.publicKey);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), publicKey);
    }

    // Getters.

    public String getPublicKey() {
        return publicKey;
    }
}
