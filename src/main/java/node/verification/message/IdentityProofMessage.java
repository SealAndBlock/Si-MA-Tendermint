package node.verification.message;

import agent.TendermintAgentIdentifier;
import sima.core.protocol.ProtocolIdentifier;

import java.util.Objects;
import java.util.Optional;

public abstract class IdentityProofMessage extends NodeVerificationMessage {

    // Variables.

    private final TendermintAgentIdentifier concernedAgentIdentifier;

    // Constructors.

    /**
     * @param sender                   the sender
     * @param concernedAgentIdentifier the public key of the node
     * @param intendedProtocol         the intended protocol
     *
     * @throws IllegalArgumentException if concernedNodePublicKey is null
     */
    protected IdentityProofMessage(TendermintAgentIdentifier sender, TendermintAgentIdentifier concernedAgentIdentifier,
                                   ProtocolIdentifier intendedProtocol,
                                   ProtocolIdentifier replyProtocol) {
        super(sender, intendedProtocol, replyProtocol);

        this.concernedAgentIdentifier =
                Optional.ofNullable(concernedAgentIdentifier).orElseThrow(() -> new IllegalArgumentException("Cannot pass a null publicKey"));
    }

    // Methods.

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof IdentityProofMessage that)) return false;
        if (!super.equals(o)) return false;
        return concernedAgentIdentifier.equals(that.concernedAgentIdentifier);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), concernedAgentIdentifier);
    }

    // Getters.

    public TendermintAgentIdentifier getConcernedAgentIdentifier() {
        return concernedAgentIdentifier;
    }
}
