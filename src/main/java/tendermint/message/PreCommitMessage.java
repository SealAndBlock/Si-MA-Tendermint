package tendermint.message;

import sima.core.agent.AgentIdentifier;
import sima.core.environment.event.Transportable;
import sima.core.protocol.ProtocolIdentifier;

public class PreCommitMessage extends IDValueMessage{
    
    // Constructors.
    
    protected PreCommitMessage(AgentIdentifier sender, AgentIdentifier receiver,
                               ProtocolIdentifier protocolTargeted, Transportable content) {
        super(sender, receiver, protocolTargeted, content);
    }
}
