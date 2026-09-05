final class Assembler {
    static String assemble(Syntax.Program program) {
        StringBuilder output = new StringBuilder();
        for (Syntax.Event event : program.events) {
            output.append("EVENT ").append(event.name).append('\n');
            for (Syntax.Statement statement : event.body) {
                if (statement instanceof Syntax.Show) {
                    output.append("SHOW ").append(((Syntax.Show) statement).text).append('\n');
                }
            }
            output.append("END_EVENT\n");
        }
        return output.toString();
    }
}
