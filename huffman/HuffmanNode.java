package huffman;
import java.io.Serializable;

public class HuffmanNode implements Comparable<HuffmanNode>, Serializable {
    char character;
    int frequency;
    HuffmanNode left;
    HuffmanNode right;

    public HuffmanNode(char character, int frequency) {
        this.character = character;
        this.frequency = frequency;
    }

    public HuffmanNode(int frequency, HuffmanNode left, HuffmanNode right) {
        this.frequency = frequency;
        this.left = left;
        this.right = right;
    }

    public boolean isLeaf() {
        return left == null && right == null;
    }

    @Override
    public int compareTo(HuffmanNode other) {
        return this.frequency - other.frequency;
    }

    @Override
    public String toString() {
        if (isLeaf()) {
            return "Char: '" + character + "', Freq: " + frequency;
        } else {
            return "Freq: " + frequency;
        }
    }
}
