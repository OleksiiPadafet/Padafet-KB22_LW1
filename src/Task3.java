import java.lang.reflect.*;
import java.util.Arrays;

class FunctionNotFoundException extends Exception {
    public FunctionNotFoundException(String message) {
        super(message);
    }
}

class TestClass {

    private double a = 1.0;

    public double f(double x) {
        return Math.exp(-Math.abs(a) * x) * Math.sin(x);
    }

    public double f(double x, int n) {
        return Math.exp(-Math.abs(a) * x) * Math.sin(x) * n;
    }

    public String hello(String name) {
        return "Hello, " + name;
    }

    @Override
    public String toString() {
        return "TestClass [a=" + a + "]";
    }
}

public class Task3 {
    public static void main(String[] args) {
        TestClass obj = new TestClass();

        try {
            Object result1 = callMethod(obj, "f", 1.0);
            System.out.println("Результат f(1.0): " + result1);

            Object result2 = callMethod(obj, "f", 1.0, 2);
            System.out.println("Результат f(1.0, 2): " + result2);

            Object result3 = callMethod(obj, "hello", "Oleksandr");
            System.out.println("Результат hello(): " + result3);

        } catch (Exception e) {
            System.out.println("Помилка: " + e.getMessage());
        }
    }

    public static Object callMethod(Object obj, String methodName, Object... params)
            throws Exception {
        Class<?> clazz = obj.getClass();
        Method[] methods = clazz.getMethods();

        for (Method method : methods) {
            if (!method.getName().equals(methodName)) {
                continue;
            }

            Class<?>[] parameterTypes = method.getParameterTypes();

            if (parameterTypes.length != params.length) {
                continue;
            }

            if (isParametersSuitable(parameterTypes, params)) {
                return method.invoke(obj, params);
            }
        }
        throw new FunctionNotFoundException("Метод " + methodName + " з параметрами " + Arrays.toString(params) + " не знайдено");
    }

    public static boolean isParametersSuitable(Class<?>[] types, Object[] params) {
        for (int i = 0; i < types.length; i++) {
            Class<?> type = types[i];
            Object param = params[i];

            if (param == null) {
                continue;
            }

            Class<?> paramClass = param.getClass();

            if (type.isPrimitive()) {
                if (!primitiveMatches(type, paramClass)) {
                    return false;
                }
            } else {
                if (!type.isAssignableFrom(paramClass)) {
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean primitiveMatches(Class<?> primitive, Class<?> wrapper) {

        if (primitive == int.class && wrapper == Integer.class) return true;
        if (primitive == double.class && wrapper == Double.class) return true;
        if (primitive == float.class && wrapper == Float.class) return true;
        if (primitive == long.class && wrapper == Long.class) return true;
        if (primitive == short.class && wrapper == Short.class) return true;
        if (primitive == byte.class && wrapper == Byte.class) return true;
        if (primitive == boolean.class && wrapper == Boolean.class) return true;
        if (primitive == char.class && wrapper == Character.class) return true;

        return false;
    }
}