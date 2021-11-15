package node.data;

import node.NodeType;

import java.util.Arrays;

/**
 * Class which contains information about sub node of a Node.
 * <p>
 * This class contains two String arrays. One which contains all public keys of each sub node which are {@link NodeType#VALIDATOR} and one which
 * contains all public keys of each sub node which are {@link NodeType#FULL_NODE}.
 */
public record SubNodeInformation(String[] validators, String[] fullNodes) {

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
}
