package huffman;
import java.io.*;

public class HuffmanDecoder {

    private String inputFilePath;
    private HuffmanNode root;
    private String encodedString;

    public HuffmanDecoder(String inputFilePath) {
        this.inputFilePath = inputFilePath;
    }

    public void decodeAndDisplay() throws IOException, ClassNotFoundException {
        // Ler e reconstruir a árvore de Huffman e a string codificada
        readEncodedData(inputFilePath);

        if (root == null || encodedString == null || encodedString.isEmpty()) {
            System.out.println("Não foi possível carregar os dados codificados ou a string está vazia.");
            return;
        }

        // Decodificar a string binária
        String decodedString = decodeString(root, encodedString);

        //  Exibir a string original
        System.out.println("\n--- Decodificador ---");
        System.out.println("Arquivo de entrada: " + inputFilePath);
        System.out.println("String codificada (binário lida): " + encodedString);
        System.out.println("String decodificada: " + decodedString);
    }

    private void readEncodedData(String filePath) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            this.root = (HuffmanNode) ois.readObject();
            this.encodedString = ois.readUTF();
        }
    }

    private String decodeString(HuffmanNode root, String encodedString) {
        StringBuilder decoded = new StringBuilder();
        HuffmanNode current = root;

        for (char bit : encodedString.toCharArray()) {
            if (bit == '0') {
                current = current.left;
            } else { // bit == '1'
                current = current.right;
            }

            if (current.isLeaf()) {
                decoded.append(current.character);
                current = root; // Reinicia do nó raiz
            }
        }
        return decoded.toString();
    }
}
