package tendermint.message;

import sima.core.agent.AgentIdentifier;
import sima.core.environment.event.Transportable;
import sima.core.protocol.ProtocolIdentifier;

public class ProposalMessage extends TendermintMessage {
    
    // Constructors.
    
    public ProposalMessage(AgentIdentifier sender, AgentIdentifier receiver,
                           ProtocolIdentifier protocolTargeted, Transportable content) {
        super(sender, receiver, protocolTargeted, content);
    }
}
