package node.message;

import agent.TendermintAgentIdentifier;
import sima.core.protocol.ProtocolIdentifier;
import sima.standard.environment.message.Message;

import java.util.Objects;
import java.util.Optional;

import static util.Util.illegalArgumentExceptionSupplier;

/**
 * Empty class just here in case we must add field common at all messages.
 */
public abstract class TendermintMessage extends Message {

    // Variables.

    private final TendermintAgentIdentifier sender;

    private final ProtocolIdentifier replyProtocol;

    // Constructors.

    /**
     * @param sender           the message sender
     * @param intendedProtocol the intended protocol
     * @param replyProtocol    the protocol where the response must be sent
     */
    protected TendermintMessage(TendermintAgentIdentifier sender, ProtocolIdentifier intendedProtocol, ProtocolIdentifier replyProtocol) {
        super(null, intendedProtocol);
        this.sender = Optional.ofNullable(sender).orElseThrow(illegalArgumentExceptionSupplier("Sender cannot be null"));
        this.replyProtocol = replyProtocol;
    }

    // Methods.

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TendermintMessage that)) return false;
        if (!super.equals(o)) return false;
        return sender.equals(that.sender) && Objects.equals(replyProtocol, that.replyProtocol);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), sender, replyProtocol);
    }

    // Getters.

    public TendermintAgentIdentifier getSender() {
        return sender;
    }

    public ProtocolIdentifier getReplyProtocol() {
        return replyProtocol;
    }
}
