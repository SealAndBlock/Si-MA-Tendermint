package node.verification.message;

import sima.core.protocol.ProtocolIdentifier;

import java.util.Objects;
import java.util.Optional;

public class CipheredMessage extends IdentityProofMessage {

    // Variables.

    private final String cipheredMsg;

    // Constructors.

    /**
     * @param concernedNodePublicKey the public key of the node to verify
     * @param cipheredMsg            the cipheredMsg to send
     * @param intendedProtocol       the intended protocol
     *
     * @throws IllegalArgumentException if the cipheredMsg is null
     */
    public CipheredMessage(String concernedNodePublicKey, String cipheredMsg, ProtocolIdentifier intendedProtocol) {
        super(concernedNodePublicKey, intendedProtocol);

        this.cipheredMsg = Optional.ofNullable(cipheredMsg).orElseThrow(() -> new IllegalArgumentException("Cannot pass null cipheredMsg"));
    }

    // Methods.

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CipheredMessage that)) return false;
        if (!super.equals(o)) return false;
        return cipheredMsg.equals(that.cipheredMsg);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), cipheredMsg);
    }

    // Getters.

    public String getCipheredMsg() {
        return cipheredMsg;
    }
}
