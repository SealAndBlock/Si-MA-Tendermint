package node;

import sima.core.protocol.ProtocolIdentifier;
import sima.standard.environment.message.Message;

/**
 * Empty class just here in case we must add field common at all messages.
 */
public abstract class TendermintMessage extends Message {

    // Constructors.

    protected TendermintMessage(Message content, ProtocolIdentifier intendedProtocol) {
        super(content, intendedProtocol);
    }
}
