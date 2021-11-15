package node.seednode.message.nodelist;

import node.seednode.message.SeedNodeMessage;
import sima.core.protocol.ProtocolIdentifier;

public abstract class SeedNodeListNodeMessage extends SeedNodeMessage {

    // Constructors.

    protected SeedNodeListNodeMessage(ProtocolIdentifier intendedProtocol) {
        super(null, intendedProtocol);
    }
}
