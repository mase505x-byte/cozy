import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

final class KeywordList {
    static final Set<String> KEYWORDS = new HashSet<>(Arrays.asList(
        "on", "start", "button", "pressed", "set", "to", "change", "by",
        "show", "if", "else", "repeat", "times", "wait", "milliseconds",
        "and", "or", "not"
    ));

    private KeywordList() { }
}
// pretty simple huh