package node.verification.message;

import sima.core.protocol.ProtocolIdentifier;

import java.util.Objects;
import java.util.Optional;

public abstract class IdentityProofMessage extends NodeVerificationMessage {

    // Variables.

    private final String concernedNodePublicKey;

    // Constructors.

    /**
     * @param concernedNodePublicKey the public key of the node
     * @param intendedProtocol       the intended protocol
     *
     * @throws IllegalArgumentException if concernedNodePublicKey is null
     */
    protected IdentityProofMessage(String concernedNodePublicKey, ProtocolIdentifier intendedProtocol) {
        super(intendedProtocol);

        this.concernedNodePublicKey =
                Optional.ofNullable(concernedNodePublicKey).orElseThrow(() -> new IllegalArgumentException("Cannot pass a null publicKey"));
    }

    // Methods.

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof IdentityProofMessage that)) return false;
        if (!super.equals(o)) return false;
        return concernedNodePublicKey.equals(that.concernedNodePublicKey);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), concernedNodePublicKey);
    }

    // Getters.

    public String getConcernedNodePublicKey() {
        return concernedNodePublicKey;
    }
}
