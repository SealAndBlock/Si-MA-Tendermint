package node.verification.message;

import sima.core.protocol.ProtocolIdentifier;

public class PubKeyMessage extends IdentityProofMessage {

    // Constructors.

    /**
     * @param nodePublicKey    the public key of the node which send us the message
     * @param intendedProtocol the intended protocol
     */
    public PubKeyMessage(String nodePublicKey, ProtocolIdentifier intendedProtocol) {
        super(nodePublicKey, intendedProtocol);
    }
}
