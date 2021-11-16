package node.seednode.message.inscription;

import node.seednode.message.SeedNodeMessage;
import sima.core.protocol.ProtocolIdentifier;

import java.util.Optional;

import static util.Util.illegalArgumentExceptionSupplier;

public abstract class SeedNodeInscriptionMessage extends SeedNodeMessage {

    // Constructors.

    protected SeedNodeInscriptionMessage(String concernedNodePublicKey, ProtocolIdentifier intendedProtocol) {
        super(Optional.ofNullable(concernedNodePublicKey).orElseThrow(illegalArgumentExceptionSupplier("ConcernedNodePublicKey cannot be null.")),
              intendedProtocol);
    }
}
