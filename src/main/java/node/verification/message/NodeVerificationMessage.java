package node.verification.message;

import node.message.TendermintMessage;
import sima.core.protocol.ProtocolIdentifier;

public abstract class NodeVerificationMessage extends TendermintMessage {

    // Constructors.

    protected NodeVerificationMessage(ProtocolIdentifier intendedProtocol) {
        super(null, intendedProtocol);
    }
}
