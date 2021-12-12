package tokenizer.state;

import tokenizer.NumberToken;
import tokenizer.Tokenizer;

public class NumberState implements State {
    @Override
    public State makeStep(Tokenizer tokenizer) {
        StringBuilder number = new StringBuilder();
        while (tokenizer.isCurDigit()) {
            number.append((char) tokenizer.getCurSymbol());
            tokenizer.nextSymbol();
        }
        if (!number.isEmpty()) {
            tokenizer.addToken(new NumberToken(number.toString()));
        }
        return new StartState();
    }
}
