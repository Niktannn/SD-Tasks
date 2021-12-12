package visitor;

import tokenizer.Brace;
import tokenizer.NumberToken;
import tokenizer.Operation;
import tokenizer.Token;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

public class CalcVisitor implements TokenVisitor {
    private Deque<Integer> stack;

    @Override
    public void visit(NumberToken token) {
        stack.push(Integer.parseInt(token.getValue()));
    }

    @Override
    public void visit(Brace token) {
        throw new IllegalArgumentException("No braces allowed in reversed polish notation");
    }

    @Override
    public void visit(Operation token) {
        if (stack.size() < 2) {
            throw new IllegalArgumentException("Wrong polish notation");
        }
        int b = stack.pop();
        int a = stack.pop();
        stack.push(token.calculate(a, b));
    }

    public int calculate(List<Token> tokens) {
        stack = new ArrayDeque<>();
        for (Token token : tokens) {
            token.accept(this);
        }
        if (stack.size() != 1) {
            throw new IllegalArgumentException("Wrong polish notation");
        }
        return stack.pop();
    }
}
