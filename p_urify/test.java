import java.net.*;
import java.nio.file.*;
import java.util.regex.*;


public class test {
    public static void main(String[] args) throws URISyntaxException {
        switch (args.length) {
        case 1:
            System.out.println(urify(args[0]));
            break;
        case 2:
            System.out.println(urify(args[0], args[1]));
            break;
        default:
            throw new IllegalArgumentException("one or two arguments must be given");
        }
    }
    
    protected static final String urify(String p, String base) throws URISyntaxException {
        Path path = Paths.get("");
        Path basePath = pathify(base);
        boolean unaltered = false;
        if (p.matches("^(?i)file:.+")) {
            path = Paths.get(sanitize_uri(p)).normalize();
        } else if (p.matches("^(?i)[a-z]{2,}:.+")) {
            unaltered = true;
        } else {
            path = Paths.get(p).normalize();
        }
        if (unaltered) {
            return p;
        } else {
            return basePath.resolve(path).toUri().toString();
        }
    }
    protected static final String urify(String p) throws URISyntaxException {
        return urify(p, System.getProperty("user.dir"));
    }

    protected static final Path pathify(String p) throws URISyntaxException {
        if (p.matches("^(?i)file:/.+")) {
            return Paths.get(sanitize_uri(p)).normalize();
        } else {
            return Paths.get(p);
        }
    }

    protected static final URI sanitize_uri(String u) throws URISyntaxException {
        return new URI(u.replace(" ", "%20"));
    }
}
