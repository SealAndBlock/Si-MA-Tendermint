package tendermint.protocol;

import sima.core.agent.SimpleAgent;
import sima.core.environment.event.Event;
import sima.core.protocol.Protocol;
import sima.core.protocol.ProtocolManipulator;

import java.util.Map;

public class TendermintConsensusProtocol extends Protocol {
    
    // Constructors.
    
    public TendermintConsensusProtocol(String protocolTag, SimpleAgent agentOwner, Map<String, String> args) {
        super(protocolTag, agentOwner, args);
    }
    
    // Methods.
    
    protected ProtocolManipulator createDefaultProtocolManipulator() {
        return null;
    }
    
    public void processEvent(Event event) {
        // Nothing
    }
}
