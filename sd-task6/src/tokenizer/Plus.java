package tokenizer;

public class Plus extends Operation {
    public static final int priority = 0;

    @Override
    public String toString() {
        return "+";
    }

    @Override
    public int getPriority() {
        return priority;
    }

    @Override
    public int calculate(int a, int b) {
        return a + b;
    }
}
