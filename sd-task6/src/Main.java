import tokenizer.Token;
import tokenizer.Tokenizer;
import visitor.CalcVisitor;
import visitor.ParserVisitor;
import visitor.PrintVisitor;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        Tokenizer tokenizer = new Tokenizer(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)));
        List<Token> tokens = tokenizer.tokenize();

        if (tokens.size() == 0) {
            throw new IllegalArgumentException("Empty input");
        }

        ParserVisitor parserVisitor = new ParserVisitor();
        PrintVisitor printVisitor = new PrintVisitor(System.out);
        CalcVisitor calcVisitor = new CalcVisitor();

        List<Token> rpnTokens = parserVisitor.parse(tokens);

        System.out.print("Reverse Polish Notation:");
        printVisitor.print(rpnTokens);
        System.out.println();

        int result = calcVisitor.calculate(rpnTokens);
        System.out.println("Calculated: " + result);
    }
}
