import java.util.ArrayList;
import java.util.List;

final class Lexer {
    enum Type { WORD, STRING, NEWLINE, INDENT, DEDENT, EOF }

    static final class Token {
        final Type type;
        final String value;
        final int line;
        Token(Type type, String value, int line) {
            this.type = type;
            this.value = value;
            this.line = line;
        }
    }

    static List<Token> lex(String source) {
        List<Token> tokens = new ArrayList<>();
        int previousIndent = 0;
        String[] lines = source.split("\\R", -1);

        for (int i = 0; i < lines.length; i++) {
            String line = lines[i];
            if (line.trim().isEmpty() || line.trim().startsWith("#")) continue;
            int indent = 0;
            while (indent < line.length() && line.charAt(indent) == ' ') indent++;
            if (indent > previousIndent) {
                if (indent - previousIndent != 4) {
                    throw new IllegalArgumentException("Line " + (i + 1) + ": indentation must increase by 4 spaces.");
                }
                tokens.add(new Token(Type.INDENT, "", i + 1));
                previousIndent = indent;
            }
            while (indent < previousIndent) {
                tokens.add(new Token(Type.DEDENT, "", i + 1));
                previousIndent -= 4;
            }
            if (indent != previousIndent) throw new IllegalArgumentException("Line " + (i + 1) + ": invalid indentation.");

            String text = line.substring(indent).trim();
            if (text.startsWith("show ")) {
                tokens.add(new Token(Type.WORD, "show", i + 1));
                tokens.add(new Token(Type.STRING, text.substring(5).trim(), i + 1));
            } else {
                for (String word : text.split("\\s+")) tokens.add(new Token(Type.WORD, word, i + 1));
            }
            tokens.add(new Token(Type.NEWLINE, "", i + 1));
        }
        while (previousIndent > 0) {
            tokens.add(new Token(Type.DEDENT, "", lines.length));
            previousIndent -= 4;
        }
        tokens.add(new Token(Type.EOF, "", lines.length));
        return tokens;
    }
}
