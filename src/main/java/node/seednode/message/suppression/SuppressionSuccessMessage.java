package node.seednode.message.suppression;

import agent.TendermintAgentIdentifier;
import sima.core.protocol.ProtocolIdentifier;

import java.util.Arrays;

public class SuppressionSuccessMessage extends SeedNodeSuppressionMessage {

    // Variables.

    private final String[] concernedNodeSubNodePublicKey;

    // Constructors.

    /**
     * @param sender                         the sender
     * @param concernedNodePublicKey         the public key of the concerned node which has been removed
     * @param concernedNodeSubNodePublicKeys the public keys of each sub node of the concerned node which has been removed
     * @param intendedProtocol               the intended protocol
     */
    public SuppressionSuccessMessage(TendermintAgentIdentifier sender, String concernedNodePublicKey, String[] concernedNodeSubNodePublicKeys,
                                     ProtocolIdentifier intendedProtocol) {
        super(sender, concernedNodePublicKey, intendedProtocol, null);
        this.concernedNodeSubNodePublicKey = concernedNodeSubNodePublicKeys;
    }

    // Methods.

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SuppressionSuccessMessage that)) return false;
        if (!super.equals(o)) return false;
        return Arrays.equals(concernedNodeSubNodePublicKey, that.concernedNodeSubNodePublicKey);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + Arrays.hashCode(concernedNodeSubNodePublicKey);
        return result;
    }

    // Getters.

    public String[] getConcernedNodeSubNodePublicKey() {
        return concernedNodeSubNodePublicKey;
    }
}
