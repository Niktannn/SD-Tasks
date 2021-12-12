package tokenizer;

import visitor.TokenVisitor;

public interface Token {
    void accept(TokenVisitor visitor);
}
