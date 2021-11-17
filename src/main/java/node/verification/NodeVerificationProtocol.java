package node.verification;

import agent.TendermintAgent;
import agent.TendermintAgentIdentifier;
import environment.TendermintEnvironment;
import node.exception.NoTendermintAgentException;
import node.verification.message.CipheredMessage;
import node.verification.message.DecipheredMessage;
import org.jetbrains.annotations.NotNull;
import sima.core.agent.SimaAgent;
import sima.core.environment.event.Event;
import sima.core.protocol.Protocol;
import sima.core.protocol.ProtocolManipulator;
import sima.core.simulation.SimaSimulationUtils;
import sima.standard.transport.MessageTransportProtocol;

import java.util.HashMap;
import java.util.Map;

import static sima.core.simulation.SimaSimulation.SimaLog;
import static util.CipherUtil.*;

public class NodeVerificationProtocol extends Protocol {

    // Constants.

    private static final int RANDOM_TEXT_SIZE = 25;

    // Variables.

    /**
     * Map public key with the generated random text to try to verify the node.
     */
    private final Map<String, String> mapPubKeyRandomMsg;

    private MessageTransportProtocol messageTransport;

    private TendermintEnvironment tendermintEnvironment;

    // Constructors.

    public NodeVerificationProtocol(String protocolTag, SimaAgent agentOwner, Map<String, String> args) {
        super(protocolTag, agentOwner, args);

        if (!(agentOwner instanceof TendermintAgent)) {
            throw new NoTendermintAgentException("AgentOwner must be a TendermintAgent");
        }

        mapPubKeyRandomMsg = new HashMap<>();
    }

    // Methods.

    @Override
    public void onOwnerStart() {
        // Nothing.
    }

    @Override
    public void onOwnerKill() {
        // Nothing.
    }

    @Override
    protected ProtocolManipulator createDefaultProtocolManipulator() {
        return new ProtocolManipulator.DefaultProtocolManipulator(this);
    }

    @Override
    public void processEvent(Event event) {
        if (event instanceof CipheredMessage cipheredMessage) {
            proveIdentity(cipheredMessage);
        } else if (event instanceof DecipheredMessage decipheredMessage) {
            receiveDecipheredMessage(decipheredMessage);
        } else {
            throw new UnsupportedOperationException(event.getClass() + " -> Unsupported event for " + NodeVerificationProtocol.class);
        }
    }

    private void receiveDecipheredMessage(DecipheredMessage decipheredMessage) {
        // TODO unlock thread waiting until receive a DecipheredMessage
    }

    /**
     * This method start the procedure to try to verify the identity of a node.
     *
     * @param nodePubKey the public key of the node to verify
     *
     * @return true if the identity of the node has been verified, else false.
     */
    public boolean verifyNodeIdentity(String nodePubKey) {
        String randomMsg = generateRandomMsgFor(nodePubKey);
        String ciphered = cipherMsg(randomMsg, nodePubKey);
        if (ciphered != null) {
            TendermintAgentIdentifier agent;
            if ((agent = getAgent(nodePubKey)) != null) {
                sendCipheredMessageTo(agent,
                                      new CipheredMessage(getAgentOwner().getAgentIdentifier(), nodePubKey, ciphered, getIdentifier()));
                DecipheredMessage decipheredMessage = waitDecipheredMsgFrom(nodePubKey);
                if (decipheredMessage == null)
                    return false;
                else {
                    String msgSent = mapPubKeyRandomMsg.get(nodePubKey);
                    String deciphered = decipheredMessage.getDecipheredMsg();
                    return msgSent.equals(deciphered);
                }
            } else {// Agent not in the environment
                SimaLog.info("Agent with the pubKey " + nodePubKey + " is not in the TendermintEnvironment.");
                return false;
            }
        } else // Cipher fail
            return false;
    }

    private TendermintAgentIdentifier getAgent(String publicKey) {
        return tendermintEnvironment.getTendermintAgentIdentifier(publicKey);
    }

    private void sendCipheredMessageTo(TendermintAgentIdentifier target, CipheredMessage cipheredMessage) {
        messageTransport.send(target, cipheredMessage);
    }

    /**
     * Wait until a {@link DecipheredMessage} is received and is for the specified public key
     *
     * @param publicKey the public key of the sender
     *
     * @return the deciphered message received. If no message is received after a certain timer, returns null.
     */
    private DecipheredMessage waitDecipheredMsgFrom(String publicKey) {
        // TODO implement the wait in SiMA-core
        return null;
    }

    /**
     * Generate a random msg for the public key and map them in {@link #mapPubKeyRandomMsg}.
     *
     * @param pubKey the public key for which the random message is generated
     *
     * @return random msg, never returns null.
     */
    private @NotNull String generateRandomMsgFor(String pubKey) {
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = RANDOM_TEXT_SIZE;
        StringBuilder buffer = new StringBuilder(targetStringLength);
        for (int i = 0; i < targetStringLength; i++) {
            int randomLimitedInt = SimaSimulationUtils.randomInt(leftLimit, rightLimit);
            buffer.append((char) randomLimitedInt);
        }
        return buffer.toString();
    }

    /**
     * Cipher the message with the pub key
     *
     * @param msg    the message to cipher
     * @param pubKey the public key with which the message will be ciphered
     *
     * @return the ciphered message with the key. If the cipher fail, return null
     */
    private String cipherMsg(String msg, String pubKey) {
        return rsaCipher(rsaExtractPublicKey(pubKey), msg);
    }

    /**
     * Deciphered the {@link CipheredMessage} received and send the {@link DecipheredMessage} to the node which has sent the message.
     *
     * @param cipheredMessage the ciphered message which must be deciphered
     */
    private void proveIdentity(CipheredMessage cipheredMessage) {
        String ciphered = cipheredMessage.getCipheredMsg();
        if (cipheredMessage.getConcernedNodePublicKey().equals(getNodePublicKey())) {
            String deciphered = decipherMessage(ciphered, getNodePrivateKey());

            if (deciphered != null) {
                TendermintAgentIdentifier agent;
                if ((agent = getAgent(cipheredMessage.getConcernedNodePublicKey())) != null)
                    sendDecipheredMessageTo(agent,
                                            new DecipheredMessage(getAgentOwner().getAgentIdentifier(), getNodePublicKey(), deciphered, ciphered,
                                                                  getIdentifier()));
                else
                    SimaLog.info("Agent with the public key " + cipheredMessage.getConcernedNodePublicKey() + " is not in the TendermintEnvironment");
            } else // decipher fail -> cannot prove the identity (NEVER HAPPEN?)
                SimaLog.error("Fail to decipher the message with correct public key. Concerned agent : " + getAgentOwner().getAgentIdentifier());

        } // else not concerned by the message.
    }

    private @NotNull String getNodePublicKey() {
        return getAgentOwner().getPublicKey();
    }

    private @NotNull String getNodePrivateKey() {
        return getAgentOwner().getPrivateKey();
    }

    private String decipherMessage(String ciphered, String privateKey) {
        return rsaDecipher(rsaExtractPrivateKey(privateKey), ciphered);
    }

    private void sendDecipheredMessageTo(TendermintAgentIdentifier target, DecipheredMessage decipheredMessage) {
        messageTransport.send(target, decipheredMessage);
    }

    @Override
    public TendermintAgent getAgentOwner() {
        return (TendermintAgent) super.getAgentOwner();
    }

    // Getters and Setters.

    public MessageTransportProtocol getMessageTransport() {
        return messageTransport;
    }

    public void setMessageTransport(MessageTransportProtocol messageTransport) {
        this.messageTransport = messageTransport;
    }

    public TendermintEnvironment getTendermintEnvironment() {
        return tendermintEnvironment;
    }

    public void setTendermintEnvironment(TendermintEnvironment tendermintEnvironment) {
        this.tendermintEnvironment = tendermintEnvironment;
    }
}
