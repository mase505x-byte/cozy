import java.util.ArrayList;
import java.util.List;

final class Syntax {
    static final class Program {
        final List<Event> events = new ArrayList<>();
    }

    static final class Event {
        final String name;
        final List<Statement> body = new ArrayList<>();
        Event(String name) { this.name = name; }
    }

    interface Statement { }

    static final class Show implements Statement {
        final String text;
        Show(String text) { this.text = text; }
    }

    private Syntax() { }
}
