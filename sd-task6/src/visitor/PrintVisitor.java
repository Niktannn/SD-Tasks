package visitor;

import tokenizer.Brace;
import tokenizer.NumberToken;
import tokenizer.Operation;
import tokenizer.Token;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class PrintVisitor implements TokenVisitor {
    private final OutputStream outputStream;

    public PrintVisitor(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    public void print(Token token) {
        try {
            outputStream.write(token.toString().getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    @Override
    public void visit(NumberToken token) {
        print(token);
    }

    @Override
    public void visit(Brace token) {
        print(token);
    }

    @Override
    public void visit(Operation token) {
        print(token);
    }

    public void print(List<Token> tokens) {
        for (Token token : tokens) {
            token.accept(this);
            try {
                outputStream.write(' ');
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }
        }
    }
}
