package huffman;
import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class HuffmanEncoder {

    private String inputString;
    private HuffmanNode root;
    private Map<Character, String> huffmanCodes;

    public HuffmanEncoder(String inputString) {
        this.inputString = inputString;
        this.huffmanCodes = new HashMap<>();
    }

    public void encodeAndSave(String outputFilePath) throws IOException {
        if (inputString == null || inputString.isEmpty()) {
            System.out.println("String de entrada vazia. Nada para codificar.");
            return;
        }

        // Calcula a frequência de cada caractere
        Map<Character, Integer> frequencies = calculateFrequencies(inputString);

        // Constroe a árvore de Huffman
        this.root = buildHuffmanTree(frequencies);

        // Gera o dicionário de códigos binários
        generateHuffmanCodes(root, "", huffmanCodes);

        // Codifica a string original
        String encodedString = encodeString(inputString, huffmanCodes);

        // Salvar em um arquivo
        saveEncodedData(outputFilePath, root, encodedString);

        System.out.println("String original: " + inputString);
        System.out.println("Frequências: " + frequencies);
        System.out.println("Códigos de Huffman: " + huffmanCodes);
        System.out.println("String codificada (binário): " + encodedString);
        System.out.println("Dados codificados salvos em: " + outputFilePath);
    }

    private Map<Character, Integer> calculateFrequencies(String text) {
        Map<Character, Integer> frequencies = new HashMap<>();
        for (char c : text.toCharArray()) {
            frequencies.put(c, frequencies.getOrDefault(c, 0) + 1);
        }
        return frequencies;
    }

    private HuffmanNode buildHuffmanTree(Map<Character, Integer> frequencies) {
        PriorityQueue<HuffmanNode> pq = new PriorityQueue<>();
        for (Map.Entry<Character, Integer> entry : frequencies.entrySet()) {
            pq.add(new HuffmanNode(entry.getKey(), entry.getValue()));
        }

        while (pq.size() > 1) {
            HuffmanNode left = pq.poll();
            HuffmanNode right = pq.poll();
            HuffmanNode parent = new HuffmanNode(left.frequency + right.frequency, left, right);
            pq.add(parent);
        }
        return pq.poll();
    }

    private void generateHuffmanCodes(HuffmanNode node, String code, Map<Character, String> codes) {
        if (node == null) {
            return;
        }
        if (node.isLeaf()) {
            codes.put(node.character, code.isEmpty() ? "0" : code); // Caso de string com apenas um caractere
            return;
        }
        generateHuffmanCodes(node.left, code + "0", codes);
        generateHuffmanCodes(node.right, code + "1", codes);
    }

    private String encodeString(String text, Map<Character, String> codes) {
        StringBuilder sb = new StringBuilder();
        for (char c : text.toCharArray()) {
            sb.append(codes.get(c));
        }
        return sb.toString();
    }

    private void saveEncodedData(String filePath, HuffmanNode root, String encodedString) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            // Salva a árvore de Huffman
            oos.writeObject(root);

            // Salva a string codificada
            // Para economizar espaço, podemos salvar os bits diretamente.
            // Para simplicidade, vamos salvar a string de 0s e 1s, mas em uma aplicação real
            // você converteria para bytes.
            oos.writeUTF(encodedString);
        }
    }
}
