package node.verification;

import agent.TendermintAgentIdentifier;
import node.TendermintProtocol;
import node.verification.message.CipheredMessage;
import node.verification.message.DecipheredMessage;
import org.jetbrains.annotations.NotNull;
import sima.core.agent.SimaAgent;
import sima.core.environment.event.Event;
import sima.core.exception.ForcedWakeUpException;
import sima.core.protocol.ProtocolManipulator;
import sima.core.scheduler.Scheduler;
import sima.core.simulation.SimaSimulationUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static sima.core.simulation.SimaSimulation.SimaLog;
import static sima.core.simulation.SimaSimulation.getScheduler;
import static util.CipherUtil.*;

public class NodeVerificationProtocol extends TendermintProtocol {

    // Constants.

    private static final int RANDOM_TEXT_SIZE = 25;

    private static final long WAITING_TIMEOUT = 100;

    // Variables.

    /**
     * Map public key with the generated random text to try to verify the node.
     */
    private final Map<TendermintAgentIdentifier, String> mapRandomMsg;

    private final Map<TendermintAgentIdentifier, Scheduler.Condition> mapWaitingCondition;

    private final Map<TendermintAgentIdentifier, DecipheredMessage> mapDecipherReceive;

    // Constructors.

    public NodeVerificationProtocol(String protocolTag, SimaAgent agentOwner, Map<String, String> args) {
        super(protocolTag, agentOwner, args);
        mapRandomMsg = new HashMap<>();
        mapWaitingCondition = new HashMap<>();
        mapDecipherReceive = new HashMap<>();
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
        TendermintAgentIdentifier sender = decipheredMessage.getSender();
        TendermintAgentIdentifier concernedAgent = decipheredMessage.getConcernedAgentIdentifier();
        if (sender.equals(concernedAgent)) {
            Scheduler.Condition c = mapWaitingCondition.get(sender);
            mapDecipherReceive.put(sender, decipheredMessage);
            if (c != null)
                c.wakeup();
        } else
            SimaLog.info("Receive a decipher message with sender which differs from the concerned agent.");
    }

    /**
     * This method start the procedure to try to verify the identity of a node.
     *
     * @param toVerify the identifier to verify
     *
     * @return true if the identity of the node has been verified, else false.
     */
    public boolean verifyNodeIdentity(TendermintAgentIdentifier toVerify) {
        String randomMsg = generateRandomMsgFor(Optional.of(toVerify).get());
        String ciphered = cipherMsg(randomMsg, toVerify);
        if (ciphered != null) {
            if (getTendermintEnvironment().isEvolving(toVerify)) {
                sendCipheredMessageTo(toVerify,
                                      new CipheredMessage(getAgentOwner().getAgentIdentifier(), toVerify, ciphered, getIdentifier()));
                DecipheredMessage decipheredMessage = waitDecipheredMsgFrom(toVerify);
                if (decipheredMessage == null)
                    return false;
                else {
                    String msgSent = mapRandomMsg.get(toVerify);
                    String deciphered = decipheredMessage.getDecipheredMsg();
                    return msgSent.equals(deciphered);
                }
            } else {// Agent not in the environment
                SimaLog.info("Agent with the pubKey " + toVerify + " is not in the TendermintEnvironment.");
                return false;
            }
        } else // Cipher fail
            return false;
    }

    private void sendCipheredMessageTo(TendermintAgentIdentifier target, CipheredMessage cipheredMessage) {
        getMessageTransport().send(target, cipheredMessage);
    }

    /**
     * Wait until a {@link DecipheredMessage} is received and is for the specified public key
     *
     * @param agentToVerify the agent to verify
     *
     * @return the deciphered message received. If no message is received after a certain timer, returns null.
     */
    private DecipheredMessage waitDecipheredMsgFrom(TendermintAgentIdentifier agentToVerify) {
        Scheduler.Condition c = new Scheduler.Condition();
        mapWaitingCondition.put(agentToVerify, c);
        try {
            getScheduler().scheduleAwait(c, WAITING_TIMEOUT);
            return mapDecipherReceive.get(agentToVerify);
        } catch (ForcedWakeUpException | InterruptedException e) {
            Thread.currentThread().interrupt();
            SimaLog.error("Exception wake the wait of DecipheredMsg", e);
            return null;
        }
    }

    /**
     * Generate a random msg for the public key and map them in {@link #mapRandomMsg}.
     *
     * @param toVerify the identifier to verify
     *
     * @return random msg, never returns null.
     */
    private @NotNull String generateRandomMsgFor(TendermintAgentIdentifier toVerify) {
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        String generated = generateRandomText(leftLimit, rightLimit);
        mapRandomMsg.put(toVerify, generated);
        return generated;
    }

    private @NotNull String generateRandomText(int leftLimit, int rightLimit) {
        StringBuilder buffer = new StringBuilder(RANDOM_TEXT_SIZE);
        for (int i = 0; i < RANDOM_TEXT_SIZE; i++) {
            int randomLimitedInt = SimaSimulationUtils.randomInt(leftLimit, rightLimit);
            buffer.append((char) randomLimitedInt);
        }
        return buffer.toString();
    }

    /**
     * Cipher the message with the pub key
     *
     * @param msg      the message to cipher
     * @param toVerify the identifier for  which the message will be ciphered
     *
     * @return the ciphered message with the key. If the cipher fail, return null
     */
    private String cipherMsg(String msg, TendermintAgentIdentifier toVerify) {
        return rsaCipher(rsaExtractPublicKey(toVerify.getPublicKey()), msg);
    }

    /**
     * Deciphered the {@link CipheredMessage} received and send the {@link DecipheredMessage} to the node which has sent the message.
     *
     * @param cipheredMessage the ciphered message which must be deciphered
     */
    private void proveIdentity(CipheredMessage cipheredMessage) {
        String ciphered = cipheredMessage.getCipheredMsg();
        TendermintAgentIdentifier sender = cipheredMessage.getSender();
        if (cipheredMessage.getConcernedAgentIdentifier().equals(getAgentOwner().getAgentIdentifier())) {
            String deciphered = decipherMessage(ciphered, getNodePrivateKey());
            if (deciphered != null) {
                sendDecipheredMessageTo(sender,
                                        new DecipheredMessage(getAgentOwner().getAgentIdentifier(), getAgentOwner().getAgentIdentifier(), deciphered,
                                                              ciphered,
                                                              getIdentifier()));
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
        getMessageTransport().send(target, decipheredMessage);
    }
}
