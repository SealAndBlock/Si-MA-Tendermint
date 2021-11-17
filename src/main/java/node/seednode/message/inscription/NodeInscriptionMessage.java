package node.seednode.message.inscription;

import agent.TendermintAgentIdentifier;
import node.NodeType;
import sima.core.protocol.ProtocolIdentifier;

import java.util.Arrays;
import java.util.Optional;

import static util.Util.illegalArgumentExceptionSupplier;

public class NodeInscriptionMessage extends SeedNodeInscriptionMessage {

    // Variables.

    /**
     * All node types that the node is.
     */
    private final NodeType[] nodeTypes;

    // Constructors.

    public NodeInscriptionMessage(TendermintAgentIdentifier sender, String concernedNodePublicKey, NodeType[] nodeTypes, ProtocolIdentifier intendedProtocol) {
        super(sender, concernedNodePublicKey, intendedProtocol);
        this.nodeTypes = Optional.ofNullable(nodeTypes).orElseThrow(illegalArgumentExceptionSupplier("NodeTypes cannot be null."));
        if (this.nodeTypes.length <= 0)
            throw new IllegalArgumentException("NodeTypes must contains at least one node type");
    }

    // Methods.

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof NodeInscriptionMessage that)) return false;
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
