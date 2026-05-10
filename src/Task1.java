import javax.swing.*;
import java.awt.*;
import java.lang.reflect.*;
import java.util.Scanner;

public class Task1 {

    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);

        System.out.println("1 - консольний режим");
        System.out.println("2 - графічний режим");
        System.out.print("Ваш вибір: ");
        int choice = sc.nextInt();
        sc.nextLine();

        if (choice == 1) {
            System.out.print("Введіть повне ім'я класу: ");
            String name = sc.nextLine();

            Class<?> clazz = getClassByName(name);
            System.out.println(getInfo(clazz));
        } else {
            createGui();
        }
    }

    public static Class<?> getClassByName(String name) throws ClassNotFoundException {
        switch (name) {
            case "int": return int.class;
            case "double": return double.class;
            case "float": return float.class;
            case "long": return long.class;
            case "short": return short.class;
            case "byte": return byte.class;
            case "boolean": return boolean.class;
            case "char": return char.class;
            default: return Class.forName(name);
        }
    }

    public static String getInfo(Class<?> clazz) {
        StringBuilder sb = new StringBuilder();

        sb.append("Пакет: ");
        if (clazz.getPackage() != null) {
            sb.append(clazz.getPackage().getName());
        } else {
            sb.append("немає");
        }

        sb.append("\n");

        if (clazz.isPrimitive()) {
            sb.append("Тип: примітивний тип\n");
        } else if (clazz.isArray()) {
            sb.append("Тип: масив\n");
        } else if (clazz.isInterface()) {
            sb.append("Тип: інтерфейс\n");
        } else {
            sb.append("Тип: клас\n");
        }

        sb.append("Модифікатори: ").append(Modifier.toString(clazz.getModifiers())).append("\n");
        sb.append("Назва: ").append(clazz.getName()).append("\n");

        sb.append("Базовий клас: ");
        if (clazz.getSuperclass() != null) {
            sb.append(clazz.getSuperclass().getName());
        } else {
            sb.append("немає");
        }

        sb.append("\n\nІнтерфейси:\n");
        Class<?>[] interfaces = clazz.getInterfaces();

        if (interfaces.length == 0) {
            sb.append("немає\n");
        } else {
            for (Class<?> i : interfaces) {
                sb.append(i.getName()).append("\n");
            }
        }

        sb.append("\nПоля:\n");
        Field[] fields = clazz.getDeclaredFields();

        if (fields.length == 0) {
            sb.append("немає\n");
        } else {
            for (Field f : fields) {
                sb.append(Modifier.toString(f.getModifiers())).append(" ").append(f.getType().getSimpleName()).append(" ").append(f.getName()).append("\n");
            }
        }

        sb.append("\nКонструктори:\n");
        Constructor<?>[] constructors = clazz.getDeclaredConstructors();

        if (constructors.length == 0) {
            sb.append("немає\n");
        } else {
            for (Constructor<?> c : constructors) {
                sb.append(c).append("\n");
            }
        }

        sb.append("\nМетоди:\n");
        Method[] methods = clazz.getDeclaredMethods();

        if (methods.length == 0) {
            sb.append("немає\n");
        } else {
            for (Method m : methods) {
                sb.append(m).append("\n");
            }
        }

        return sb.toString();
    }

    public static void createGui() {
        JFrame frame = new JFrame("Reflection API");
        frame.setSize(700, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JTextField input = new JTextField("java.lang.String");
        JButton button = new JButton("Аналізувати");
        JTextArea area = new JTextArea();

        area.setEditable(false);

        button.addActionListener(e -> {
            try {
                Class<?> clazz = getClassByName(input.getText());
                area.setText(getInfo(clazz));
            } catch (Exception ex) {
                area.setText("Помилка: " + ex.getMessage());
            }
        });

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(input, BorderLayout.CENTER);
        panel.add(button, BorderLayout.EAST);

        frame.add(panel, BorderLayout.NORTH);
        frame.add(new JScrollPane(area), BorderLayout.CENTER);

        frame.setVisible(true);
    }
}