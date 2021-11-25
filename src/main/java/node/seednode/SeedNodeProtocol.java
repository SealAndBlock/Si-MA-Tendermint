package node.seednode;

import agent.TendermintAgentIdentifier;
import node.NodeType;
import node.TendermintProtocol;
import node.data.NodeInformation;
import node.data.SubNodeInformation;
import node.seednode.message.inscription.NodeInscriptionFailMessage;
import node.seednode.message.inscription.NodeInscriptionMessage;
import node.seednode.message.inscription.NodeInscriptionSuccessMessage;
import node.seednode.message.nodelist.GetNodeListMessage;
import node.seednode.message.nodelist.NodeListMessage;
import node.seednode.message.suppression.NodeSuppressionDemandMessage;
import node.seednode.message.suppression.SuppressionSuccessMessage;
import node.verification.NodeVerificationProtocol;
import org.jetbrains.annotations.NotNull;
import sima.core.agent.SimaAgent;
import sima.core.environment.event.Event;
import sima.core.protocol.ProtocolIdentifier;
import sima.core.protocol.ProtocolManipulator;

import java.util.*;

public class SeedNodeProtocol extends TendermintProtocol {

    // Variables.

    private NodeVerificationProtocol nodeVerificationProtocol;

    /**
     * Map each node type with a map which map public key with associated node information.
     */
    private final Map<NodeType, Map<String, NodeInformation>> mapByNodeType;

    // Constructors.

    public SeedNodeProtocol(String protocolTag, SimaAgent agentOwner, Map<String, String> args) {
        super(protocolTag, agentOwner, args);
        mapByNodeType = initNodeMap();
    }

    private Map<NodeType, Map<String, NodeInformation>> initNodeMap() {
        final Map<NodeType, Map<String, NodeInformation>> map = new EnumMap<>(NodeType.class);
        map.put(NodeType.SEED_NODE, new HashMap<>());
        map.put(NodeType.FULL_NODE, new HashMap<>());
        map.put(NodeType.SENTRY_NODE, new HashMap<>());
        map.put(NodeType.VALIDATOR, new HashMap<>());
        return map;
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
        if (event instanceof NodeInscriptionMessage nodeInscriptionMessage) {
            receiveNodeInscriptionMessage(nodeInscriptionMessage);
        } else if (event instanceof GetNodeListMessage getNodeListMessage) {
            receiveGetNodeListMessage(getNodeListMessage);
        } else if (event instanceof NodeSuppressionDemandMessage nodeSuppressionDemandMessage) {
            receiveNodeSuppressionMessage(nodeSuppressionDemandMessage);
        } else {
            throw new UnsupportedOperationException(event.getClass() + " -> SeedNodeProtocol does not support this type of event");
        }
    }

    private void receiveNodeInscriptionMessage(NodeInscriptionMessage nodeInscriptionMessage) {
        final NodeInformation nodeInformation = nodeInscriptionMessage.getNodeInformation();
        if (verifyNode(nodeInformation) && verifyAllSubNodes(nodeInformation)) {
            addNode(nodeInformation);
            sendInscriptionSuccess(nodeInscriptionMessage.getSender(), nodeInformation, nodeInscriptionMessage.getReplyProtocol());
        } else {
            sendInscriptionFail(nodeInscriptionMessage.getSender(), nodeInformation, nodeInscriptionMessage.getReplyProtocol());
        }
    }

    private void sendInscriptionSuccess(TendermintAgentIdentifier target, NodeInformation nodeInformation, ProtocolIdentifier replyProtocol) {
        getMessageTransport().send(target, new NodeInscriptionSuccessMessage(getAgentOwner().getAgentIdentifier(), nodeInformation.nodePublicKey(),
                                                                             replyProtocol));
    }

    private void sendInscriptionFail(TendermintAgentIdentifier target, NodeInformation nodeInformation, ProtocolIdentifier replyProtocol) {
        getMessageTransport().send(target, new NodeInscriptionFailMessage(getAgentOwner().getAgentIdentifier(), nodeInformation.nodePublicKey(),
                                                                          replyProtocol));
    }

    private void receiveGetNodeListMessage(GetNodeListMessage getNodeListMessage) {
        Set<NodeInformation> nodeInformationSet = getNodeInformation(getNodeListMessage.getNodeTypes());

        NodeInformation[] nodeInformation = new NodeInformation[0];
        nodeInformation = nodeInformationSet.toArray(nodeInformation);

        sendNodeListMessageTo(getNodeListMessage.getSender(), nodeInformation, getNodeListMessage.getReplyProtocol());
    }

    /**
     * Returns all {@link NodeInformation} about Node which are store with one are several {@link NodeType} in the {@link NodeType} array.
     * <p>
     * Returns a Set to avoid duplicate {@link NodeInformation} because a Node can be different {@link NodeType} in the same type, therefore it will
     * be stored in each map of each {@link NodeType} that it is.
     *
     * @param nodeTypes array of {@link NodeType} for which we want {@link NodeInformation}
     *
     * @return a set of {@link NodeInformation}.
     */
    @NotNull
    private Set<NodeInformation> getNodeInformation(NodeType[] nodeTypes) {
        Set<NodeInformation> nodeInformationSet = new HashSet<>();
        for (NodeType nodeType : nodeTypes) {
            Map<String, NodeInformation> mapNodeInformation = mapByNodeType.get(nodeType);
            nodeInformationSet.addAll(mapNodeInformation.values());
        }
        return nodeInformationSet;
    }

    private void sendNodeListMessageTo(TendermintAgentIdentifier target, NodeInformation[] nodeInformation, ProtocolIdentifier replyProtocol) {
        getMessageTransport().send(target, new NodeListMessage(getAgentOwner().getAgentIdentifier(), nodeInformation, replyProtocol));
    }

    private void receiveNodeSuppressionMessage(NodeSuppressionDemandMessage nodeSuppressionDemandMessage) {
        removeNode(nodeSuppressionDemandMessage.getSender(), nodeSuppressionDemandMessage.getConcernedNodePublicKey(),
                   nodeSuppressionDemandMessage.getReplyProtocol());
    }

    /**
     * Remove the node of the seed node. If the node is a {@link NodeType#SENTRY_NODE} and contains sub node, also remove sub nodes.
     * <p>
     * A node and a sub node is removed only if the node and all sub nodes has been verified.
     *
     * @param sender     the sender of the suppression request
     * @param nodePubKey the public key of the node
     */
    private void removeNode(TendermintAgentIdentifier sender, String nodePubKey, ProtocolIdentifier replyProtocol) {
        boolean alreadyVerified = false;
        List<String> removedSubNodes = null;

        for (NodeType nodeType : NodeType.values()) {
            Map<String, NodeInformation> mapNode = mapByNodeType.get(nodeType);
            NodeInformation nodeInformation;
            if ((nodeInformation = mapNode.get(nodePubKey)) != null) {
                if (!alreadyVerified) {
                    if (!verifyNode(nodeInformation) || !verifyAllSubNodes(nodeInformation))
                        return;
                    alreadyVerified = true;
                }

                // Remove node from the current map for the current node type.
                mapNode.remove(nodePubKey);

                // Removes sub node from validators and fullNodes map if it is not already done.
                if (nodeType.equals(NodeType.SENTRY_NODE)) {
                    removedSubNodes = removeAllSubNode(nodeInformation);
                }
            }
        }

        sendSuppressionSuccess(sender, new SuppressionSuccessMessage(getAgentOwner().getAgentIdentifier(),
                                                                     nodePubKey,
                                                                     removedSubNodes == null ? null : removedSubNodes.toArray(new String[0]),
                                                                     replyProtocol));
    }

    private List<String> removeAllSubNode(NodeInformation nodeInformation) {
        List<String> removedNode = new ArrayList<>();
        if (nodeInformation.subNodesInformation() != null && nodeInformation.subNodesInformation().containsInfo()) {
            Map<String, NodeInformation> mapValidators = mapByNodeType.get(NodeType.VALIDATOR);
            Map<String, NodeInformation> mapFullNodes = mapByNodeType.get(NodeType.FULL_NODE);

            for (String validatorPubKey : nodeInformation.subNodesInformation().validators()) {
                NodeInformation removed = mapValidators.remove(validatorPubKey);
                if (removed != null)
                    removedNode.add(validatorPubKey);
            }

            for (String fullNodePubKey : nodeInformation.subNodesInformation().fullNodes()) {
                NodeInformation removed = mapFullNodes.remove(fullNodePubKey);
                if (removed != null)
                    removedNode.add(fullNodePubKey);
            }
        }

        return removedNode.isEmpty() ? null : removedNode;
    }

    private void sendSuppressionSuccess(TendermintAgentIdentifier target, SuppressionSuccessMessage suppressionSuccessMessage) {
        getMessageTransport().send(target, suppressionSuccessMessage);
    }

    /**
     * Verifies if the node which sends messages is really the node which is pretended to be.
     *
     * @param nodeToVerify node to verify
     *
     * @return true if the node has been verified else false.
     */
    private boolean verifyNode(NodeInformation nodeToVerify) {
        return verifyNode(getAgent(nodeToVerify.nodePublicKey()));
    }

    /**
     * Verifies if the node which sends messages is really the node which is pretended to be.
     *
     * @param nodeToVerify the node to verify
     *
     * @return true if the node has been verified else false.
     */
    private boolean verifyNode(TendermintAgentIdentifier nodeToVerify) {
        return nodeVerificationProtocol.verifyNodeIdentity(nodeToVerify);
    }

    /**
     * Verifies all sub node of a nodes. If only one sub node is not verified, returns false.
     * <p>
     * Only verify if the node is a {@link NodeType#SENTRY_NODE} and has sub node. In all others case, returns always true. If the node is a {@link
     * NodeType#SENTRY_NODE} and contains sub node, returns true only if all sub nodes are verified, else false.
     *
     * @param nodeInformation node information of the node for which we must verify sub node
     *
     * @return true if all sub nodes has been verified, else false.
     */
    private boolean verifyAllSubNodes(NodeInformation nodeInformation) {

        // If sentry node with sub nodes
        SubNodeInformation subNodeInformation = nodeInformation.subNodesInformation();
        if (Arrays.asList(nodeInformation.nodeTypes()).contains(NodeType.SENTRY_NODE) && subNodeInformation != null &&
                subNodeInformation.containsInfo()) {
            for (String validatorToVerifyPubKey : subNodeInformation.validators()) {
                boolean verified = verifyNode(getAgent(validatorToVerifyPubKey));
                if (!verified)
                    return false;
            }

            for (String fullNodeToVerifyPubKey : subNodeInformation.fullNodes()) {
                boolean verified = verifyNode(getAgent(fullNodeToVerifyPubKey));
                if (!verified)
                    return false;
            }

        } // else no sentry node

        return true;
    }

    /**
     * Add the node in {@link #mapByNodeType}.
     * <p>
     * The node is added in each map which correspond to node types that it is.
     * <p>
     * If the node has sub node, each sub node is added. A NodeInformation is created with the same ip as the parent node.
     *
     * @param nodeInformation the node information
     */
    private void addNode(NodeInformation nodeInformation) {
        for (NodeType nodeType : nodeInformation.nodeTypes()) {
            Map<String, NodeInformation> mapNodeType = mapByNodeType.get(nodeType);
            mapNodeType.put(nodeInformation.nodePublicKey(), nodeInformation);
            if (nodeType.equals(NodeType.SENTRY_NODE)) {
                addSubNode(nodeInformation);
            }
        }
    }

    private void addSubNode(NodeInformation parentNodeInformation) {
        if (parentNodeInformation.subNodesInformation() != null) {
            // Validators.
            for (NodeInformation validator : parentNodeInformation.subNodesInformation().extractValidators(parentNodeInformation.nodePublicKey())) {
                mapByNodeType.get(NodeType.VALIDATOR).put(parentNodeInformation.nodePublicKey(), validator);
            }

            // FullNodes.
            for (NodeInformation fullNode : parentNodeInformation.subNodesInformation().extractFullNodes(parentNodeInformation.nodePublicKey())) {
                mapByNodeType.get(NodeType.FULL_NODE).put(parentNodeInformation.nodePublicKey(), fullNode);
            }
        }
    }


    // Getters and Setters.

    public NodeVerificationProtocol getNodeVerificationProtocol() {
        return nodeVerificationProtocol;
    }

    public void setNodeVerificationProtocol(NodeVerificationProtocol nodeVerificationProtocol) {
        this.nodeVerificationProtocol = nodeVerificationProtocol;
    }
}
