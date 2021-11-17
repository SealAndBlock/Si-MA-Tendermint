package node.seednode.message.inscription;

import agent.TendermintAgentIdentifier;
import sima.core.protocol.ProtocolIdentifier;

public class NodeInscriptionFailMessage extends SeedNodeInscriptionMessage {

    // Constructors.

    public NodeInscriptionFailMessage(TendermintAgentIdentifier sender, String concernedNodePublicKey, ProtocolIdentifier intendedProtocol) {
        super(sender, concernedNodePublicKey, intendedProtocol);
    }
}
