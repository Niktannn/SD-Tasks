package tokenizer.state;

import tokenizer.*;

public class StartState implements State {
    @Override
    public State makeStep(Tokenizer tokenizer) {
        int curSymbol = tokenizer.getCurSymbol();
        switch (curSymbol) {
            case '(' -> tokenizer.addToken(new LeftBrace());
            case ')' -> tokenizer.addToken(new RightBrace());
            case '+' -> tokenizer.addToken(new Plus());
            case '-' -> tokenizer.addToken(new Minus());
            case '*' -> tokenizer.addToken(new Mul());
            case '/' -> tokenizer.addToken(new Div());
            default -> {
                if (tokenizer.isCurDigit()) {
                    return new NumberState();
                }
                if (curSymbol == Tokenizer.END_SYMBOL) {
                    return new EndState();
                }
                return new ErrorState("Unexpected symbol " + (char) curSymbol + " in Start state");
            }
        }
        tokenizer.nextSymbol();
        return this;
    }
}
