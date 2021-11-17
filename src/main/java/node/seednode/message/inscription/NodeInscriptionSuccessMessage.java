package node.seednode.message.inscription;

import agent.TendermintAgentIdentifier;
import sima.core.protocol.ProtocolIdentifier;

public class NodeInscriptionSuccessMessage extends SeedNodeInscriptionMessage {

    // Constructors.

    public NodeInscriptionSuccessMessage(TendermintAgentIdentifier sender, String concernedNodePublicKey, ProtocolIdentifier intendedProtocol) {
        super(sender, concernedNodePublicKey, intendedProtocol, null);
    }
}
