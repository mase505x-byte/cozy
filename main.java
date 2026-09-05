import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) throws IOException {
        Path sourceFile = Paths.get("helloworld.cozy");
        String source;
        if (Files.exists(sourceFile)) {
            source = new String(Files.readAllBytes(sourceFile), StandardCharsets.UTF_8);
        } else {
            source = "on start\n    show \"Hello, World!\"\n";
        }
        Syntax.Program program = new Parser(Lexer.lex(source)).parse();
        System.out.print(Assembler.assemble(program));
    }
}
