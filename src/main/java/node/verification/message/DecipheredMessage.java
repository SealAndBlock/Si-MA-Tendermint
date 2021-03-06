package node.verification.message;

import agent.TendermintAgentIdentifier;
import sima.core.protocol.ProtocolIdentifier;

import java.util.Objects;
import java.util.Optional;

public class DecipheredMessage extends IdentityProofMessage {

    // Variables.

    private final String decipheredMsg;
    private final String cipheredMsg;

    // Constructors.

    /**
     * @param sender                   the sender
     * @param concernedAgentIdentifier the identifier of agent to verify
     * @param decipheredMsg            the message decipher from ciphered message
     * @param cipheredMsg              the ciphered message
     * @param intendedProtocol         the intended protocol
     *
     * @throws IllegalArgumentException If decipheredMsg or cipheredMsg is null
     */
    public DecipheredMessage(TendermintAgentIdentifier sender, TendermintAgentIdentifier concernedAgentIdentifier, String decipheredMsg,
                             String cipheredMsg,
                             ProtocolIdentifier intendedProtocol) {
        super(sender, concernedAgentIdentifier, intendedProtocol, null);

        this.decipheredMsg = Optional.ofNullable(decipheredMsg).orElseThrow(() -> new IllegalArgumentException("Cannot pass null decipheredMsg"));
        this.cipheredMsg = Optional.ofNullable(cipheredMsg).orElseThrow(() -> new IllegalArgumentException("Cannot pass null cipheredMsg"));
    }

    // Methods.

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DecipheredMessage that)) return false;
        if (!super.equals(o)) return false;
        return decipheredMsg.equals(that.decipheredMsg) && cipheredMsg.equals(that.cipheredMsg);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), decipheredMsg, cipheredMsg);
    }

    // Getters.

    public String getDecipheredMsg() {
        return decipheredMsg;
    }

    public String getCipheredMsg() {
        return cipheredMsg;
    }
}
