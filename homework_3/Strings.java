import java.util.Scanner;
import java.util.regex.*;
import java.time.LocalDate;

public class Strings {

    // Метод для получения инициалов
    public static String get_initials(String name) {
        try {
            String[] initials = name.split(" "); // Массив из фамилии, имени и отчества
            if (initials.length != 3) {
                throw new IllegalArgumentException("некорректный ввод ФИО");
            } else {
                // Возвращаем фамилию + инициалы
                return initials[0] + " " + initials[1].charAt(0) + "." + initials[2].charAt(0) + ".";
                }
        } catch (IllegalArgumentException e) {
            System.out.println("Ошибка в получении инициалов: " + e.getMessage());
            return "";
        }
    }

    // Метод для получения пола
    public static String get_gender(String name) {
        // Регулярные выражения для мужских и женских отчеств
        String m = ".*(ович|евич|ич)$";
        String f = ".*(овна|евна|ична|илична)$";
        if (Pattern.matches(m, name)) {
            return "М";
        }
        else if (Pattern.matches(f, name)) {
            return "Ж";
        }
        else {
            return "ОПРЕДЕЛИТЬ_НЕ_УДАЛОСЬ";
        }
    }

    // Метод для получения возраста
    public static int get_age(String data) {
        try {
            String[] times = data.split("\\.");

            if (times.length != 3) {
                throw new IllegalArgumentException("некорректный формат даты");
            }

            int day = Integer.parseInt(times[0]);
            int month = Integer.parseInt(times[1]);
            int year = Integer.parseInt(times[2]);
            LocalDate date_now = LocalDate.now();

            if (month < 1 || month > 12 || day < 1 || day > LocalDate.of(year, month, 1).lengthOfMonth() || LocalDate.of(year, month, day).isAfter(date_now)) {
                throw new IllegalArgumentException("неккоректный ввод даты");
            }

            LocalDate birthday = LocalDate.of(date_now.getYear(), month, day);
            int age = date_now.getYear() - year;

            // Уменьшаем на 1, если дня рождения еще не было
            if (birthday.isAfter(date_now)) {
                age--;
            }
            return age;
        } catch (IllegalArgumentException e) {
            System.out.println("Ошибка в получении возраста: " + e.getMessage());
            return -1;
        }
    }

    // Метод для получения окончания возраста
    public static String get_end(int age) {
        try {
            if (age < 0) {
                throw new IllegalArgumentException("некорректный возраст");
            }
            if (age % 10 == 1 && age % 100 != 11) {
                return age + " год";
            } else if (age % 10 >= 2 && age % 10 <= 4 && !(age % 100 >= 12 && age % 100 <= 14)) {
                return age + " года";
            } else {
                return age + " лет";
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Ошибка в получении окончания возраста: " + e.getMessage());
            return "Неопределён";
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        try {
            System.out.print("Введите ФИО: ");
            String in_name = scanner.nextLine();

            String initials = get_initials(in_name);
            String[] name = in_name.split(" ");
            String gender = get_gender(name[2]);

            System.out.print("Введите дату рождения (в формате число.месяц.год): ");
            String birthday = scanner.nextLine();

            int age = get_age(birthday);
            String age_end = get_end(age);

            System.out.println("Инициалы: " + initials);
            System.out.println("Пол: " + gender);
            System.out.println("Возраст: " + age_end);
            scanner.close();
        } catch (Exception e) {
            System.out.println(" ");
        }
    }
}
