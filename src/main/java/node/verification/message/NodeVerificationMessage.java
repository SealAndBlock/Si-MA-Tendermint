package node.verification.message;

import agent.TendermintAgentIdentifier;
import node.message.TendermintMessage;
import sima.core.protocol.ProtocolIdentifier;

public abstract class NodeVerificationMessage extends TendermintMessage {

    // Constructors.

    protected NodeVerificationMessage(TendermintAgentIdentifier sender, ProtocolIdentifier intendedProtocol, ProtocolIdentifier replyProtocol) {
        super(sender, intendedProtocol, replyProtocol);
    }
}
