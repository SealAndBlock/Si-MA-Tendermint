package node.seednode.message;

import agent.TendermintAgentIdentifier;
import node.message.TendermintMessage;
import sima.core.protocol.ProtocolIdentifier;

import java.util.Objects;

public abstract class SeedNodeMessage extends TendermintMessage {

    // Variables.

    /**
     * The public key of the sender node.
     */
    private final String concernedNodePublicKey;

    // Constructors.

    protected SeedNodeMessage(TendermintAgentIdentifier sender, String concernedNodePublicKey, ProtocolIdentifier intendedProtocol) {
        super(sender, intendedProtocol);
        this.concernedNodePublicKey = concernedNodePublicKey;
    }

    // Methods.

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SeedNodeMessage that)) return false;
        if (!super.equals(o)) return false;
        return Objects.equals(concernedNodePublicKey, that.concernedNodePublicKey);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), concernedNodePublicKey);
    }

    // Getters.

    public String getConcernedNodePublicKey() {
        return concernedNodePublicKey;
    }
}
