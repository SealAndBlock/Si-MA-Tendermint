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

    // Constructors.

    protected TendermintMessage(TendermintAgentIdentifier sender, ProtocolIdentifier intendedProtocol) {
        super(null, intendedProtocol);
        this.sender = Optional.ofNullable(sender).orElseThrow(illegalArgumentExceptionSupplier("Sender cannot be null"));
    }

    // Methods.

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TendermintMessage that)) return false;
        if (!super.equals(o)) return false;
        return sender.equals(that.sender);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), sender);
    }

    // Getters.

    public TendermintAgentIdentifier getSender() {
        return sender;
    }
}
