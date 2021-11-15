package node.seednode.message.suppression;

import sima.core.protocol.ProtocolIdentifier;

public class NodeSuppressionDemandMessage extends SeedNodeSuppressionMessage {

    // Constructors.

    public NodeSuppressionDemandMessage(String concernedNodePublicKey, ProtocolIdentifier intendedProtocol) {
        super(concernedNodePublicKey, intendedProtocol);
    }
}
