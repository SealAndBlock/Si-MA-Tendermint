package node.data;

import node.NodeType;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Class which contains information about sub node of a Node.
 * <p>
 * This class contains two String arrays. One which contains all public keys of each sub node which are {@link NodeType#VALIDATOR} and one which
 * contains all public keys of each sub node which are {@link NodeType#FULL_NODE}.
 */
public record SubNodeInformation(String[] validators, String[] fullNodes) implements Serializable {

    // Methods.

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SubNodeInformation that)) return false;
        return Arrays.equals(validators, that.validators) && Arrays.equals(fullNodes, that.fullNodes);
    }

    @Override
    public int hashCode() {
        int result = Arrays.hashCode(validators);
        result = 31 * result + Arrays.hashCode(fullNodes);
        return result;
    }

    @Override
    public String toString() {
        return "SubNodesInformation{" +
                "validators=" + Arrays.toString(validators) +
                ", fullNodes=" + Arrays.toString(fullNodes) +
                '}';
    }

    /**
     * @param sentryNodePubKey the public key of the parent sentry node.
     *
     * @return the list of {@link NodeInformation} for each validators. Never returns null, at least empty list.
     */
    public @NotNull List<NodeInformation> extractValidators(String sentryNodePubKey) {
        List<NodeInformation> informationList = new ArrayList<>();
        if (validators != null)
            for (String pubKeyValidator : validators) {
                informationList.add(new NodeInformation(new NodeType[]{NodeType.VALIDATOR}, pubKeyValidator, sentryNodePubKey, null));
            }
        return informationList;
    }

    /**
     * @param sentryNodePubKey the public key of the parent sentry node.
     *
     * @return the list of {@link NodeInformation} for each full node. Never returns null, at least empty list.
     */
    public @NotNull List<NodeInformation> extractFullNodes(String sentryNodePubKey) {
        List<NodeInformation> informationList = new ArrayList<>();
        if (fullNodes != null)
            for (String pubKeyFullNode : fullNodes) {
                informationList.add(new NodeInformation(new NodeType[]{NodeType.FULL_NODE}, pubKeyFullNode, sentryNodePubKey, null));
            }
        return informationList;
    }

    /**
     * @return true if the {@link SubNodeInformation} contains information are not.
     */
    public boolean containsInfo() {
        return (validators != null && validators.length > 0) || (fullNodes != null && fullNodes.length > 0);
    }
}
