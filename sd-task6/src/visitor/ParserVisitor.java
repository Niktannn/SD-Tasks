package visitor;

import tokenizer.*;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class ParserVisitor implements TokenVisitor {
    private List<Token> rpn;
    private Deque<Token> stack;

    @Override
    public void visit(NumberToken token) {
        rpn.add(token);
    }

    @Override
    public void visit(Brace token) {
        if (token instanceof LeftBrace) {
            stack.push(token);
        } else {
            Token t;
            while (!((t = stack.pop()) instanceof LeftBrace)) {
                rpn.add(t);
            }
        }
    }

    @Override
    public void visit(Operation token) {
        Token t;
        while (!stack.isEmpty() && ((t = stack.peek()) instanceof Operation)
                && ((Operation) t).getPriority() >= token.getPriority()) {
            rpn.add(stack.pop());
        }
        stack.push(token);
    }

    public List<Token> parse(List<Token> tokens) {
        rpn = new ArrayList<>();
        stack = new ArrayDeque<>();
        for (Token t : tokens) {
            t.accept(this);
        }
        while (!stack.isEmpty()) {
            rpn.add(stack.pop());
        }
        return rpn;
    }
}
