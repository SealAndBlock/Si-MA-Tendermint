package node.seednode.message;

import node.message.TendermintMessage;
import sima.core.protocol.ProtocolIdentifier;

public abstract class SeedNodeMessage extends TendermintMessage {

    // Variables.

    /**
     * The public key of the sender node.
     */
    private final String concernedNodePublicKey;

    // Constructors.

    protected SeedNodeMessage(String concernedNodePublicKey, ProtocolIdentifier intendedProtocol) {
        super(null, intendedProtocol);
        this.concernedNodePublicKey = concernedNodePublicKey;
    }

    // Getters.

    public String getConcernedNodePublicKey() {
        return concernedNodePublicKey;
    }
}
