package visitor;

import tokenizer.Brace;
import tokenizer.NumberToken;
import tokenizer.Operation;

public interface TokenVisitor {
    void visit(NumberToken token);
    void visit(Brace token);
    void visit(Operation token);
}
