package node.data;

import node.NodeType;
import sima.core.utils.Utils;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;

/**
 * Class which contains all information about a node.
 * <p>
 * Here the description of all fields:
 * <ul>
 *     <li><strong>nodeTypes</strong>: all node types that the node is.</li>
 *     <li><strong>nodePublicKey</strong>: the public key of the node.</li>
 *     <li><strong>ip</strong>: the ip of the node.</li>
 *     <li><strong>subNodesInformation</strong>: sub nodes information (only for node which are {@link NodeType#SENTRY_NODE})</li>
 * </ul>
 */
public record NodeInformation(NodeType[] nodeTypes, String nodePublicKey, String ip, SubNodeInformation subNodesInformation) implements Serializable {

    // Constructors.

    public NodeInformation {
        Utils.notNullOrThrows(nodeTypes, new IllegalArgumentException("NodeType cannot be null"));
        Utils.notNullOrThrows(nodePublicKey, new IllegalArgumentException("NodePublicKey cannot be null"));
        Utils.notNullOrThrows(ip, new IllegalArgumentException("Ip cannot be null"));
    }

    public NodeInformation(NodeType[] nodeType, String nodePublicKey, String ip) {
        this(nodeType, nodePublicKey, ip, null);
    }

    // Methods.

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof NodeInformation that)) return false;
        return Arrays.equals(nodeTypes, that.nodeTypes) && nodePublicKey.equals(that.nodePublicKey) && ip.equals(that.ip) &&
                Objects.equals(subNodesInformation, that.subNodesInformation);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(nodePublicKey, ip, subNodesInformation);
        result = 31 * result + Arrays.hashCode(nodeTypes);
        return result;
    }

    @Override
    public String toString() {
        return "NodeInformation{" +
                "nodeTypes=" + Arrays.toString(nodeTypes) +
                ", nodePublicKey='" + nodePublicKey + '\'' +
                ", ip='" + ip + '\'' +
                ", subNodesInformation=" + subNodesInformation +
                '}';
    }
}
