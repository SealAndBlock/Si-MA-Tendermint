package node.verification.message;

import agent.TendermintAgentIdentifier;
import sima.core.protocol.ProtocolIdentifier;

import java.util.Objects;
import java.util.Optional;

public class CipheredMessage extends IdentityProofMessage {

    // Variables.

    private final String cipheredMsg;

    // Constructors.

    /**
     * @param sender           the sender
     * @param toVerifyAgent    the identifier of agent to verify
     * @param cipheredMsg      the cipheredMsg to send
     * @param intendedProtocol the intended protocol
     *
     * @throws IllegalArgumentException if the cipheredMsg is null
     */
    public CipheredMessage(TendermintAgentIdentifier sender, TendermintAgentIdentifier toVerifyAgent, String cipheredMsg,
                           ProtocolIdentifier intendedProtocol) {
        super(sender, toVerifyAgent, intendedProtocol, null);

        this.cipheredMsg = Optional.ofNullable(cipheredMsg).orElseThrow(() -> new IllegalArgumentException("Cannot pass null cipheredMsg"));
    }

    // Methods.

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CipheredMessage that)) return false;
        if (!super.equals(o)) return false;
        return cipheredMsg.equals(that.cipheredMsg);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), cipheredMsg);
    }

    // Getters.

    public String getCipheredMsg() {
        return cipheredMsg;
    }
}
