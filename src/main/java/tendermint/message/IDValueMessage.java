package tendermint.message;

import sima.core.agent.AgentIdentifier;
import sima.core.environment.event.Transportable;
import sima.core.protocol.ProtocolIdentifier;

public abstract class IDValueMessage extends TendermintMessage {
    
    // Constructors.
    
    protected IDValueMessage(AgentIdentifier sender, AgentIdentifier receiver,
                          ProtocolIdentifier protocolTargeted, Transportable content) {
        super(sender, receiver, protocolTargeted, content);
    }
}
