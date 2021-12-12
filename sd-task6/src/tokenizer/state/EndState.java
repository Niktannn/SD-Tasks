package tokenizer.state;

import tokenizer.Tokenizer;

public class EndState implements State {
    @Override
    public State makeStep(Tokenizer tokenizer) {
        return this;
    }
}
