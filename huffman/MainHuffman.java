package huffman;
import java.io.IOException;
import java.util.Scanner;

public class MainHuffman {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("--- Implementação do Algoritmo de Huffman ---");

        System.out.print("Digite a string a ser codificada: ");
        String inputString = scanner.nextLine();

        String outputFileName = "saida.huff";

        // --- Codificador ---
        try {
            HuffmanEncoder encoder = new HuffmanEncoder(inputString);
            encoder.encodeAndSave(outputFileName);
        } catch (IOException e) {
            System.err.println("Erro ao codificar e salvar: " + e.getMessage());
            e.printStackTrace();
        }

        // --- Decodificador ---
        try {
            HuffmanDecoder decoder = new HuffmanDecoder(outputFileName);
            decoder.decodeAndDisplay();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Erro ao decodificar e ler: " + e.getMessage());
            e.printStackTrace();
        }

        scanner.close();
    }
}
