package node.seednode.message.nodelist;

import node.data.NodeInformation;
import sima.core.protocol.ProtocolIdentifier;

public class NodeListMessage extends SeedNodeListNodeMessage {

    // Variables.

    private final NodeInformation[] allNodeInformation;

    // Constructors.

    public NodeListMessage(NodeInformation[] allNodeInformation, ProtocolIdentifier intendedProtocol) {
        super(intendedProtocol);
        this.allNodeInformation = allNodeInformation;
    }

    // Getters.

    public NodeInformation[] getAllNodeInformation() {
        return allNodeInformation;
    }
}
