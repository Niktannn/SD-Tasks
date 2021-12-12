package tokenizer.state;

import tokenizer.Tokenizer;

public class ErrorState implements State {
    private final String message;

    public ErrorState(String message) {
        this.message = message;
    }

    @Override
    public State makeStep(Tokenizer tokenizer) {
        return this;
    }

    public String getMessage() {
        return message;
    }
}
