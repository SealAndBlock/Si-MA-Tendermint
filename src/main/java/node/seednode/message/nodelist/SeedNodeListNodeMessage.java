package node.seednode.message.nodelist;

import agent.TendermintAgentIdentifier;
import node.seednode.message.SeedNodeMessage;
import sima.core.protocol.ProtocolIdentifier;

public abstract class SeedNodeListNodeMessage extends SeedNodeMessage {

    // Constructors.

    protected SeedNodeListNodeMessage(TendermintAgentIdentifier sender, ProtocolIdentifier intendedProtocol, ProtocolIdentifier replyProtocol) {
        super(sender, null, intendedProtocol, replyProtocol);
    }
}
