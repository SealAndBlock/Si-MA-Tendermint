package node.seednode.message.inscription;

import sima.core.protocol.ProtocolIdentifier;

public class NodeInscriptionSuccessMessage extends SeedNodeInscriptionMessage {

    // Constructors.

    public NodeInscriptionSuccessMessage(String concernedNodePublicKey, ProtocolIdentifier intendedProtocol) {
        super(concernedNodePublicKey, intendedProtocol);
    }
}
