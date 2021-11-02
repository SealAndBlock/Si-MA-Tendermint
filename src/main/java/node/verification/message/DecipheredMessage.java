package node.verification.message;

import sima.core.protocol.ProtocolIdentifier;

import java.util.Optional;

public class DecipheredMessage extends IdentityProofMessage {

    // Variables.

    private final String decipheredMsg;
    private final String cipheredMsg;

    // Constructors.

    /**
     * @param nodePublicKey    the public key of the node to verify
     * @param decipheredMsg    the message decipher from ciphered message
     * @param cipheredMsg      the ciphered message
     * @param intendedProtocol the intended protocol
     *
     * @throws IllegalArgumentException If decipheredMsg or cipheredMsg is null
     */
    public DecipheredMessage(String nodePublicKey, String decipheredMsg, String cipheredMsg, ProtocolIdentifier intendedProtocol) {
        super(nodePublicKey, intendedProtocol);

        this.decipheredMsg = Optional.ofNullable(decipheredMsg).orElseThrow(() -> new IllegalArgumentException("Cannot pass null decipheredMsg"));
        this.cipheredMsg = Optional.ofNullable(cipheredMsg).orElseThrow(() -> new IllegalArgumentException("Cannot pass null cipheredMsg"));
    }

    // Getters.

    public String getDecipheredMsg() {
        return decipheredMsg;
    }

    public String getCipheredMsg() {
        return cipheredMsg;
    }
}
