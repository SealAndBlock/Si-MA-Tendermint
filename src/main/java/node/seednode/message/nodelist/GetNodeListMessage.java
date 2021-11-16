package node.seednode.message.nodelist;

import node.NodeType;
import sima.core.protocol.ProtocolIdentifier;

import java.util.Arrays;
import java.util.Optional;

import static util.Util.illegalArgumentExceptionSupplier;

public class GetNodeListMessage extends SeedNodeListNodeMessage {

    // Variables.

    private final NodeType[] nodeTypes;

    // Constructors.

    public GetNodeListMessage(NodeType[] nodeTypes, ProtocolIdentifier intendedProtocol) {
        super(intendedProtocol);
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
