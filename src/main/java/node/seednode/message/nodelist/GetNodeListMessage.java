package node.seednode.message.nodelist;

import node.NodeType;
import sima.core.protocol.ProtocolIdentifier;

import java.util.Optional;

import static util.Utils.illegalArgumentExceptionSupplier;

public class GetNodeListMessage extends SeedNodeListNodeMessage {

    // Variables.

    private final NodeType[] nodeTypes;

    // Constructors.

    public GetNodeListMessage(NodeType[] nodeTypes, ProtocolIdentifier intendedProtocol) {
        super(intendedProtocol);
        this.nodeTypes = Optional.ofNullable(nodeTypes).orElseThrow(illegalArgumentExceptionSupplier("NodeTypes cannot be null"));
    }

    // Getters.

    public NodeType[] getNodeTypes() {
        return nodeTypes;
    }
}
