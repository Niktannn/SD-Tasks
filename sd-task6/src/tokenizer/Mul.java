package tokenizer;

public class Mul extends Operation {
    public static final int priority = 1;

    @Override
    public String toString() {
        return "*";
    }

    @Override
    public int getPriority() {
        return priority;
    }

    @Override
    public int calculate(int a, int b) {
        return a * b;
    }
}
