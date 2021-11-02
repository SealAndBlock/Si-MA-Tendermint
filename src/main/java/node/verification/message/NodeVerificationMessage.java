package node.verification.message;

import node.TendermintMessage;
import sima.core.protocol.ProtocolIdentifier;
import sima.standard.environment.message.Message;

public abstract class NodeVerificationMessage extends TendermintMessage {

    // Constructors.

    public NodeVerificationMessage(ProtocolIdentifier intendedProtocol) {
        super(null, intendedProtocol);
    }
}
