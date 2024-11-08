import java.util.Scanner;
import java.io.File;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Dictionary {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите имя файла для подсчёта или путь к файлу: ");
        File file = new File(scanner.nextLine());

        if (!file.exists() || !file.isFile()) {
            System.out.println("Файл не существует или не подходит для проверки");
            return;
        }

        // Будем записывать сюда частоту появления символов
        int[] dictionary = new int[52];
        int symbol;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            while ((symbol = reader.read()) != -1) {
                char chars = (char) symbol;
                // Проверка на регистр символа
                if (chars >= 'A' && chars <= 'Z') {
                    dictionary[chars - 'A'] += 1;
                } else if (chars >= 'a' && chars <= 'z') {
                    dictionary[chars - 'a' + 26] += 1;
                }
            }
        } catch (IOException e) {
            System.out.println("Ошибка при чтении: " + e.getMessage());
            return;
        }

        System.out.print("Введите имя файла для записи результатов \n(если файл не существует, то он создастся, если же выбран существующий, то он будем очищен перед записью): ");
        String out = scanner.nextLine();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(out))) {
            for (int i = 0; i < dictionary.length; i++) {
                char chars;
                if (i < 26) {
                    chars = (char) ('A' + i);
                } else {
                    chars = (char) ('a' + (i - 26));
                }
                if (dictionary[i] != 0) {
                    writer.write(chars + ": " + dictionary[i]);
                    writer.newLine();
                }
            }
            System.out.println("\nРезультат успешно записан в файл " + out);
        } catch (IOException e) {
            System.out.println("Ошибка записи: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }
}