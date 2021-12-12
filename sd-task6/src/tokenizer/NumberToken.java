package tokenizer;

import visitor.TokenVisitor;

public class NumberToken implements Token {
    private final String value;

    public NumberToken (String value) {
        this.value = value;
    }

    @Override
    public void accept(TokenVisitor visitor) {
        visitor.visit(this);
    }

    public String getValue() {
        return this.value;
    }

    @Override
    public String toString() {
        return value;
    }
}
