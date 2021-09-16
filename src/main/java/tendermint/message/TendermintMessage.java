package tendermint.message;

import sima.basic.environment.event.Message;
import sima.core.agent.AgentIdentifier;
import sima.core.environment.event.Transportable;
import sima.core.protocol.ProtocolIdentifier;

public abstract class TendermintMessage extends Message {
    
    // Constructors.
    
    protected TendermintMessage(AgentIdentifier sender, AgentIdentifier receiver,
                                ProtocolIdentifier protocolTargeted, Transportable content) {
        super(sender, receiver, protocolTargeted, content);
    }
}
