package node.seednode.message.suppression;

import node.seednode.message.SeedNodeMessage;
import sima.core.protocol.ProtocolIdentifier;

import java.util.Optional;

import static util.Utils.illegalArgumentExceptionSupplier;

public abstract class SeedNodeSuppressionMessage extends SeedNodeMessage {

    // Constructors.

    protected SeedNodeSuppressionMessage(String concernedNodePublicKey, ProtocolIdentifier intendedProtocol) {
        super(Optional.ofNullable(concernedNodePublicKey).orElseThrow(illegalArgumentExceptionSupplier("ConcernedNodePublicKey cannot be null.")),
              intendedProtocol);
    }
}
