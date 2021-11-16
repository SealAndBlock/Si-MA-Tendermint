package environment;

import agent.TendermintAgentIdentifier;
import sima.core.agent.AgentIdentifier;
import sima.core.environment.Environment;
import sima.core.environment.event.Event;
import sima.core.simulation.SimaSimulation;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class TendermintEnvironment extends Environment {

    // Variables.

    /**
     * Map String public key with the Tendermint Agent associate to this public key.
     */
    private final Map<String, TendermintAgentIdentifier> mapPublicKeyAgent;

    // Constructors.

    public TendermintEnvironment(String environmentName, Map<String, String> args) {
        super(environmentName, args);
        this.mapPublicKeyAgent = new HashMap<>();
    }

    // Methods.

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TendermintEnvironment that)) return false;
        if (!super.equals(o)) return false;
        return mapPublicKeyAgent.equals(that.mapPublicKeyAgent);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), mapPublicKeyAgent);
    }

    @Override
    public synchronized boolean acceptAgent(AgentIdentifier agentIdentifier) {
        if (super.acceptAgent(agentIdentifier)) {
            TendermintAgentIdentifier tendermintAgentIdentifier = (TendermintAgentIdentifier) agentIdentifier;
            mapPublicKeyAgent.put(tendermintAgentIdentifier.getPublicKey(), tendermintAgentIdentifier);
            return true;
        } else
            return false;
    }

    /**
     * @param publicKey the public key of the wanted agent
     *
     * @return the {@link TendermintAgentIdentifier} of the agent associated to the public key. If there is no agent, returns null.
     */
    public synchronized TendermintAgentIdentifier getTendermintAgentIdentifier(String publicKey) {
        return mapPublicKeyAgent.get(publicKey);
    }

    /**
     * Only accept {@link agent.TendermintAgent}
     *
     * @param agentIdentifier the agent identifier
     *
     * @return true if the agentIdentifier is an instance of {@link TendermintAgentIdentifier}, else false.
     */
    @Override
    protected boolean agentCanBeAccepted(AgentIdentifier agentIdentifier) {
        return agentIdentifier instanceof TendermintAgentIdentifier;
    }

    @Override
    protected void agentIsLeaving(AgentIdentifier leavingAgentIdentifier) {
        if (leavingAgentIdentifier instanceof TendermintAgentIdentifier tendermintAgentIdentifier)
            mapPublicKeyAgent.remove(tendermintAgentIdentifier.getPublicKey());
    }

    @Override
    protected void scheduleEventProcess(AgentIdentifier target, Event event, long delay) {
        SimaSimulation.getScheduler().scheduleEvent(target, event, delay);
    }
}
