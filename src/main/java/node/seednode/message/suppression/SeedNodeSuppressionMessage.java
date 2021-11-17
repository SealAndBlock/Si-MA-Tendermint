package node.seednode.message.suppression;

import agent.TendermintAgentIdentifier;
import node.seednode.message.SeedNodeMessage;
import sima.core.protocol.ProtocolIdentifier;

import java.util.Optional;

import static util.Util.illegalArgumentExceptionSupplier;

public abstract class SeedNodeSuppressionMessage extends SeedNodeMessage {

    // Constructors.

    protected SeedNodeSuppressionMessage(TendermintAgentIdentifier sender, String concernedNodePublicKey, ProtocolIdentifier intendedProtocol) {
        super(sender,
              Optional.ofNullable(concernedNodePublicKey).orElseThrow(illegalArgumentExceptionSupplier("ConcernedNodePublicKey cannot be null.")),
              intendedProtocol);
    }
}
