package node.seednode.message.suppression;

import sima.core.protocol.ProtocolIdentifier;

public class SuppressionSuccessMessage extends SeedNodeSuppressionMessage {

    // Variables.

    private final String[] concernedNodeSubNodePublicKey;

    // Constructors.

    /**
     * @param concernedNodePublicKey         the public key of the concerned node which has been removed
     * @param concernedNodeSubNodePublicKeys the public keys of each sub node of the concerned node which has been removed
     * @param intendedProtocol               the intended protocol
     */
    public SuppressionSuccessMessage(String concernedNodePublicKey, String[] concernedNodeSubNodePublicKeys, ProtocolIdentifier intendedProtocol) {
        super(concernedNodePublicKey, intendedProtocol);
        this.concernedNodeSubNodePublicKey = concernedNodeSubNodePublicKeys;
    }

    // Getters.

    public String[] getConcernedNodeSubNodePublicKey() {
        return concernedNodeSubNodePublicKey;
    }
}
