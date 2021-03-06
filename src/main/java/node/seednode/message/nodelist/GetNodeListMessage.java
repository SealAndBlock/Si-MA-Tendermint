package node.seednode.message.nodelist;

import agent.TendermintAgentIdentifier;
import node.NodeType;
import sima.core.protocol.ProtocolIdentifier;

import java.util.Arrays;
import java.util.Optional;

import static util.Util.illegalArgumentExceptionSupplier;

public class GetNodeListMessage extends SeedNodeListNodeMessage {

    // Variables.

    private final NodeType[] nodeTypes;

    // Constructors.

    public GetNodeListMessage(TendermintAgentIdentifier sender, NodeType[] nodeTypes, ProtocolIdentifier intendedProtocol, ProtocolIdentifier replyProtocol) {
        super(sender, intendedProtocol, replyProtocol);
        this.nodeTypes = Optional.ofNullable(nodeTypes).orElseThrow(illegalArgumentExceptionSupplier("NodeTypes cannot be null"));
    }

    // Methods.

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GetNodeListMessage that)) return false;
        if (!super.equals(o)) return false;
        return Arrays.equals(nodeTypes, that.nodeTypes);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + Arrays.hashCode(nodeTypes);
        return result;
    }

    // Getters.

    public NodeType[] getNodeTypes() {
        return nodeTypes;
    }
}
