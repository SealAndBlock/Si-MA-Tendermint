package tendermint.network;

import sima.core.agent.AgentIdentifier;

public interface ProposerVerifier {
    
    AgentIdentifier getProposer(long height, long round);
    
    default boolean amIProposer(AgentIdentifier agent, long height, long round) {
        return getProposer(height, round).equals(agent);
    }
    
}
