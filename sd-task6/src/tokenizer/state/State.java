package tokenizer.state;

import tokenizer.Tokenizer;

public interface State {
    State makeStep(Tokenizer tokenizer);
}
