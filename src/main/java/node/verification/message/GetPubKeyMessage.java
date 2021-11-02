package node.verification.message;

import sima.core.protocol.ProtocolIdentifier;

public class GetPubKeyMessage extends NodeVerificationMessage {

    // Constructors.

    public GetPubKeyMessage(ProtocolIdentifier intendedProtocol) {
        super(intendedProtocol);
    }
}
