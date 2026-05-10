import java.lang.reflect.*;
import java.util.Arrays;

interface Evaluatable {
    double evalf(double x);
}

class ExpFunction implements Evaluatable {
    private double a;

    public ExpFunction(double a) {
        this.a = a;
    }

    @Override
    public double evalf(double x) {
        return Math.exp(-Math.abs(a) * x) * Math.sin(x);
    }

    @Override
    public String toString() {
        return "Exp(-|" + a + "| * x) * sin(x)";
    }
}

class SquareFunction implements Evaluatable {

    @Override
    public double evalf(double x) {
        return x * x;
    }

    @Override
    public String toString() {
        return "x * x";
    }
}

class ProfilingHandler implements InvocationHandler {

    private Object target;

    public ProfilingHandler(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args)
            throws Throwable {
        long start = System.nanoTime();
        Object result = method.invoke(target, args);

        long finish = System.nanoTime();
        System.out.println("[" + target + "]." + method.getName() + " took " + (finish - start) + " ns");

        return result;
    }
}

class TracingHandler implements InvocationHandler {

    private Object target;

    public TracingHandler(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args)
            throws Throwable {
        Object result = method.invoke(target, args);

        System.out.println("[" + target + "]." + method.getName() + "(" + Arrays.toString(args) + ") = " + result);

        return result;
    }
}

public class Task5 {

    public static void main(String[] args) {

        Evaluatable f1 = new ExpFunction(2.5);
        Evaluatable f2 = new SquareFunction();

        System.out.println("Звичайний виклик:");
        System.out.println("F1: " + f1.evalf(1.0));
        System.out.println("F2: " + f2.evalf(1.0));

        Evaluatable profiledF1 = createProfilingProxy(f1);
        Evaluatable profiledF2 = createProfilingProxy(f2);

        System.out.println("\nПрофілювання:");
        System.out.println("F1: " + profiledF1.evalf(1.0));
        System.out.println("F2: " + profiledF2.evalf(1.0));

        Evaluatable tracedF1 = createTracingProxy(f1);
        Evaluatable tracedF2 = createTracingProxy(f2);

        System.out.println("\nТрасування:");
        System.out.println("F1: " + tracedF1.evalf(1.0));
        System.out.println("F2: " + tracedF2.evalf(1.0));
    }

    public static Evaluatable createProfilingProxy(Evaluatable obj) {

        return (Evaluatable) Proxy.newProxyInstance(obj.getClass().getClassLoader(), obj.getClass().getInterfaces(), new ProfilingHandler(obj));
    }

    public static Evaluatable createTracingProxy(Evaluatable obj) {
        return (Evaluatable) Proxy.newProxyInstance(obj.getClass().getClassLoader(), obj.getClass().getInterfaces(), new TracingHandler(obj));
    }
}