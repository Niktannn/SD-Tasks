package tokenizer;

import visitor.TokenVisitor;

public abstract class Operation implements Token {
    @Override
    public void accept(TokenVisitor visitor) {
        visitor.visit(this);
    }

    public abstract int getPriority();
    public abstract int calculate(final int a, final int b);
}
