package node.verification.message;

import sima.core.protocol.ProtocolIdentifier;

import java.util.Objects;
import java.util.Optional;

public class DecipheredMessage extends IdentityProofMessage {

    // Variables.

    private final String decipheredMsg;
    private final String cipheredMsg;

    // Constructors.

    /**
     * @param concernedNodePublicKey the public key of the node to verify
     * @param decipheredMsg          the message decipher from ciphered message
     * @param cipheredMsg            the ciphered message
     * @param intendedProtocol       the intended protocol
     *
     * @throws IllegalArgumentException If decipheredMsg or cipheredMsg is null
     */
    public DecipheredMessage(String concernedNodePublicKey, String decipheredMsg, String cipheredMsg, ProtocolIdentifier intendedProtocol) {
        super(concernedNodePublicKey, intendedProtocol);

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
