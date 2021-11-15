package node.seednode.message.inscription;

import sima.core.protocol.ProtocolIdentifier;

public class NodeInscriptionFailMessage extends SeedNodeInscriptionMessage {

    // Constructors.

    public NodeInscriptionFailMessage(String concernedNodePublicKey, ProtocolIdentifier intendedProtocol) {
        super(concernedNodePublicKey, intendedProtocol);
    }
}
