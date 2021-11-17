package node.seednode.message.inscription;

import agent.TendermintAgentIdentifier;
import node.data.NodeInformation;
import sima.core.protocol.ProtocolIdentifier;

import java.util.Objects;
import java.util.Optional;

import static util.Util.illegalArgumentExceptionSupplier;

public class NodeInscriptionMessage extends SeedNodeInscriptionMessage {

    // Variables.

    /**
     * All node types that the node is.
     */
    private final NodeInformation nodeInformation;

    // Constructors.

    public NodeInscriptionMessage(TendermintAgentIdentifier sender, NodeInformation nodeInformation, ProtocolIdentifier intendedProtocol,
                                  ProtocolIdentifier replyProtocol) {
        super(sender,
              Optional.ofNullable(nodeInformation).orElseThrow(illegalArgumentExceptionSupplier("NodeTypes cannot be null.")).nodePublicKey(),
              intendedProtocol, replyProtocol);
        this.nodeInformation = nodeInformation;
    }

    // Methods.

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof NodeInscriptionMessage that)) return false;
        if (!super.equals(o)) return false;
        return nodeInformation.equals(that.nodeInformation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), nodeInformation);
    }

    // Getters.

    public NodeInformation getNodeInformation() {
        return nodeInformation;
    }

}
