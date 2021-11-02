package node.verification.message;

import sima.core.protocol.ProtocolIdentifier;

import java.util.Optional;

public abstract class IdentityProofMessage extends NodeVerificationMessage {

    // Variables.

    private final String toVerifiedNodePublicKey;

    // Constructors.

    /**
     * @param nodePublicKey    the public key of the node
     * @param intendedProtocol the intended protocol
     *
     * @throws IllegalArgumentException if nodePublicKey is null
     */
    protected IdentityProofMessage(String nodePublicKey, ProtocolIdentifier intendedProtocol) {
        super(intendedProtocol);

        this.toVerifiedNodePublicKey =
                Optional.ofNullable(nodePublicKey).orElseThrow(() -> new IllegalArgumentException("Cannot pass a null publicKey"));
    }

    // Getters.

    public String getToVerifiedNodePublicKey() {
        return toVerifiedNodePublicKey;
    }
}
