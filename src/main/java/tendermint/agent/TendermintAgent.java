package tendermint.agent;

import sima.core.agent.SimpleAgent;

import java.util.Map;

public class TendermintAgent extends SimpleAgent {
    
    // Constructors.
    
    public TendermintAgent(String agentName, int sequenceId, int uniqueId, Map<String, String> args) {
        super(agentName, sequenceId, uniqueId, args);
    }
}
