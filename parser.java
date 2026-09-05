import java.util.List;

final class Parser {
    private final List<Lexer.Token> tokens;
    private int position;

    Parser(List<Lexer.Token> tokens) { this.tokens = tokens; }
    private Lexer.Token current() { return tokens.get(position); }
    private Lexer.Token take(Lexer.Type type) {
        Lexer.Token token = current();
        if (token.type != type) throw new IllegalArgumentException("Line " + token.line + ": expected " + type + ".");
        position++;
        return token;
    }

    Syntax.Program parse() {
        Syntax.Program program = new Syntax.Program();
        while (current().type != Lexer.Type.EOF) {
            while (current().type == Lexer.Type.NEWLINE) position++;
            if (current().type == Lexer.Type.EOF) break;
            take(Lexer.Type.WORD);
            Lexer.Token eventKind = tokens.get(position - 1);
            if (!eventKind.value.equalsIgnoreCase("on")) throw new IllegalArgumentException("Line " + eventKind.line + ": expected 'on'.");
            Lexer.Token name = take(Lexer.Type.WORD);
            if (!name.value.equalsIgnoreCase("start")) throw new IllegalArgumentException("Line " + name.line + ": expected 'start'.");
            take(Lexer.Type.NEWLINE);
            take(Lexer.Type.INDENT);
            Syntax.Event event = new Syntax.Event("start");
            while (current().type != Lexer.Type.DEDENT && current().type != Lexer.Type.EOF) {
                Lexer.Token command = take(Lexer.Type.WORD);
                if (!command.value.equalsIgnoreCase("show")) throw new IllegalArgumentException("Line " + command.line + ": unsupported block.");
                event.body.add(new Syntax.Show(take(Lexer.Type.STRING).value));
                take(Lexer.Type.NEWLINE);
            }
            take(Lexer.Type.DEDENT);
            program.events.add(event);
        }
        return program;
    }
}
