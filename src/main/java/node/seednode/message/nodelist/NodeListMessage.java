package node.seednode.message.nodelist;

import agent.TendermintAgentIdentifier;
import node.data.NodeInformation;
import sima.core.protocol.ProtocolIdentifier;

import java.util.Arrays;

public class NodeListMessage extends SeedNodeListNodeMessage {

    // Variables.

    private final NodeInformation[] allNodeInformation;

    // Constructors.

    public NodeListMessage(TendermintAgentIdentifier sender, NodeInformation[] allNodeInformation, ProtocolIdentifier intendedProtocol) {
        super(sender, intendedProtocol);
        this.allNodeInformation = allNodeInformation;
    }

    // Methods.

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof NodeListMessage that)) return false;
        if (!super.equals(o)) return false;
        return Arrays.equals(allNodeInformation, that.allNodeInformation);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + Arrays.hashCode(allNodeInformation);
        return result;
    }

    // Getters.

    public NodeInformation[] getAllNodeInformation() {
        return allNodeInformation;
    }
}
