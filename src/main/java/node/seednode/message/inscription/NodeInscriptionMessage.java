package node.seednode.message.inscription;

import node.NodeType;
import sima.core.protocol.ProtocolIdentifier;

import java.util.Optional;

import static util.Utils.illegalArgumentExceptionSupplier;

public class NodeInscriptionMessage extends SeedNodeInscriptionMessage {

    // Variables.

    /**
     * All node types that the node is.
     */
    private final NodeType[] nodeTypes;

    // Constructors.

    public NodeInscriptionMessage(String concernedNodePublicKey, NodeType[] nodeTypes, ProtocolIdentifier intendedProtocol) {
        super(concernedNodePublicKey, intendedProtocol);
        this.nodeTypes = Optional.ofNullable(nodeTypes).orElseThrow(illegalArgumentExceptionSupplier("NodeTypes cannot be null."));
        if (this.nodeTypes.length <= 0)
            throw new IllegalArgumentException("NodeTypes must contains at least one node type");
    }

    // Getters.

    public NodeType[] getNodeTypes() {
        return nodeTypes;
    }
}
