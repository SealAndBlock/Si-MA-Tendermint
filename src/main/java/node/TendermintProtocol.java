package node;

import agent.TendermintAgent;
import agent.TendermintAgentIdentifier;
import environment.TendermintEnvironment;
import node.exception.NoTendermintAgentException;
import sima.core.agent.SimaAgent;
import sima.core.protocol.Protocol;
import sima.standard.transport.MessageTransportProtocol;

import java.util.Map;

public abstract class TendermintProtocol extends Protocol {

    // Variables.

    private TendermintEnvironment tendermintEnvironment;

    private MessageTransportProtocol messageTransport;

    // Constructors.

    protected TendermintProtocol(String protocolTag, SimaAgent agentOwner, Map<String, String> args) {
        super(protocolTag, agentOwner, args);

        if (!(agentOwner instanceof TendermintAgent)) {
            throw new NoTendermintAgentException("AgentOwner must be a TendermintAgent");
        }
    }

    // Methods.

    protected TendermintAgentIdentifier getAgent(String publicKey) {
        return tendermintEnvironment.getTendermintAgentIdentifier(publicKey);
    }

    @Override
    public TendermintAgent getAgentOwner() {
        return (TendermintAgent) super.getAgentOwner();
    }

    // Getters and Setters.

    public TendermintEnvironment getTendermintEnvironment() {
        return tendermintEnvironment;
    }

    public void setTendermintEnvironment(TendermintEnvironment tendermintEnvironment) {
        this.tendermintEnvironment = tendermintEnvironment;
    }

    public MessageTransportProtocol getMessageTransport() {
        return messageTransport;
    }

    public void setMessageTransport(MessageTransportProtocol messageTransport) {
        this.messageTransport = messageTransport;
    }
}
