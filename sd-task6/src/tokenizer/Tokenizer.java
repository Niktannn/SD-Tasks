package tokenizer;

import tokenizer.state.EndState;
import tokenizer.state.ErrorState;
import tokenizer.state.StartState;
import tokenizer.state.State;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.util.ArrayList;
import java.util.List;

public class Tokenizer {
    public static final int END_SYMBOL = -1;

    private final InputStream is;

    private List<Token> tokens;
    private State state;
    private int curSymbol;


    public Tokenizer(InputStream is) {
        this.is = is;
        this.state = new StartState();
        nextSymbol();
    }

    public List<Token> tokenize() {
        tokens = new ArrayList<>();
        while (!(state instanceof EndState) && !(state instanceof ErrorState)) {
            skipSpaces();
            state = state.makeStep(this);
        }
        if (state instanceof ErrorState e) {
            throw new RuntimeException(e.getMessage());
        }
        return tokens;
    }

    public void nextSymbol() {
        try {
            curSymbol = is.read();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public int getCurSymbol() {
        return curSymbol;
    }

    public void addToken(Token token) {
        tokens.add(token);
    }

    public boolean isCurDigit() {
        return '0' <= curSymbol && curSymbol <= '9';
    }

    private void skipSpaces() {
        while (Character.isWhitespace(curSymbol)) {
            nextSymbol();
        }
    }
}
