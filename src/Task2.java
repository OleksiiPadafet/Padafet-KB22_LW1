import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.Scanner;

class Check {

    private double x;
    private double y;

    public Check(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double dist() {
        return Math.sqrt(x * x + y * y);
    }

    public String info() {
        return "x = " + x + ", y = " + y;
    }

    public void clear() {
        x = 0;
        y = 0;
    }

    private void secret() {
        System.out.println("Private method");
    }
}

public class Task2 {

    public static void main(String[] args) throws Exception {

        Check obj = new Check(3, 4);

        Class<?> clazz = obj.getClass();

        System.out.println("Реальний тип об'єкта: " + clazz.getName());

        System.out.println("\nСтан об'єкта:");
        Field[] fields = clazz.getDeclaredFields();

        for (Field field : fields) {
            field.setAccessible(true);
            System.out.println(field.getType().getSimpleName() + " " + field.getName() + " = " + field.get(obj));
        }

        System.out.println("\nВідкриті методи без параметрів:");

        Method[] allMethods = clazz.getMethods();
        ArrayList<Method> methods = new ArrayList<>();

        for (Method method : allMethods) {
            if (method.getParameterCount() == 0 && method.getDeclaringClass() != Object.class) {
                methods.add(method);
                System.out.println(methods.size() + ") " + method.getReturnType().getSimpleName() + " " + method.getName() + "()");
            }
        }

        Scanner sc = new Scanner(System.in);

        System.out.print("\nВиберіть номер методу: ");
        int number = sc.nextInt();

        if (number < 1 || number > methods.size()) {
            System.out.println("Неправильний номер методу");
            return;
        }
        Method selectedMethod = methods.get(number - 1);
        Object result = selectedMethod.invoke(obj);

        System.out.println("\nРезультат виклику: " + result);

        System.out.println("\nСтан об'єкта після виклику:");
        for (Field field : fields) {
            field.setAccessible(true);

            System.out.println(field.getType().getSimpleName() + " " + field.getName() + " = " + field.get(obj));
        }
    }
}