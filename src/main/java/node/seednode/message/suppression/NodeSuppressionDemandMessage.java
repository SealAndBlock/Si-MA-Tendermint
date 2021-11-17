package node.seednode.message.suppression;

import agent.TendermintAgentIdentifier;
import sima.core.protocol.ProtocolIdentifier;

public class NodeSuppressionDemandMessage extends SeedNodeSuppressionMessage {

    // Constructors.

    public NodeSuppressionDemandMessage(TendermintAgentIdentifier sender, String concernedNodePublicKey, ProtocolIdentifier intendedProtocol) {
        super(sender, concernedNodePublicKey, intendedProtocol);
    }
}
