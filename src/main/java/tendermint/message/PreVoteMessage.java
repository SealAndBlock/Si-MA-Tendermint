package tendermint.message;

import sima.core.agent.AgentIdentifier;
import sima.core.environment.event.Transportable;
import sima.core.protocol.ProtocolIdentifier;

public class PreVoteMessage extends IDValueMessage {
    
    // Constructors.
    
    protected PreVoteMessage(AgentIdentifier sender, AgentIdentifier receiver,
                             ProtocolIdentifier protocolTargeted, Transportable content) {
        super(sender, receiver, protocolTargeted, content);
    }
}
