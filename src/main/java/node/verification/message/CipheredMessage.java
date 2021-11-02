package node.verification.message;

import sima.core.protocol.ProtocolIdentifier;

import java.util.Optional;

public class CipheredMessage extends IdentityProofMessage {

    // Variables.

    private final String cipheredMsg;

    // Constructors.

    /**
     * @param nodePublicKey    the public key of the node to verify
     * @param cipheredMsg      the cipheredMsg to send
     * @param intendedProtocol the intended protocol
     *
     * @throws IllegalArgumentException if the cipheredMsg is null
     */
    public CipheredMessage(String nodePublicKey, String cipheredMsg, ProtocolIdentifier intendedProtocol) {
        super(nodePublicKey, intendedProtocol);

        this.cipheredMsg = Optional.ofNullable(cipheredMsg).orElseThrow(() -> new IllegalArgumentException("Cannot pass null cipheredMsg"));
    }

    // Getters.

    public String getCipheredMsg() {
        return cipheredMsg;
    }
}
