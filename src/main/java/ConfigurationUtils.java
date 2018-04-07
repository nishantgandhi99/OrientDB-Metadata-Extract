import org.apache.commons.cli.*;

public class ConfigurationUtils {

    private static Options OPTIONS;

    static {
        OPTIONS = new Options();
        OPTIONS.addOption("hl", "help", false, "show help");
        OPTIONS.addOption("h", "host", true, "Hostname");
        OPTIONS.addOption("u", "user", true, "Username for Database");
        OPTIONS.addOption("p", "pass", true, "Password for Database");
        OPTIONS.addOption("d", "database", true, "Database Name");
        OPTIONS.addOption("e", "export", true, "Export Path with Filename");

    }

    public static CommandLine parseArgs(final String[] args) throws ParseException {

        if (args.length == 0) {
            throw new IllegalArgumentException("No arguments were provided (try -hl)");
        }
        CommandLineParser parser = new BasicParser();
        CommandLine cmd = parser.parse(OPTIONS, args);


        if (cmd.hasOption("hl")) {
            printHelp();
            return null;
        }

        performSanityCheck(cmd);

        return cmd;
    }

    private static void printHelp() {
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp(ConfigurationUtils.class.getName(), OPTIONS, true);
    }

    private static void performSanityCheck(CommandLine cmd) {
        if (!cmd.hasOption("h")) {
            throw new IllegalArgumentException("Must provide Server Host (-h)");
        }
        if (!cmd.hasOption("u")) {
            throw new IllegalArgumentException("Must provide Username (-u)");
        }
        if (!cmd.hasOption("p")) {
            throw new IllegalArgumentException("Must provide Password (-p)");
        }
        if (!cmd.hasOption("d")) {
            throw new IllegalArgumentException("Must provide Database Name (-d)");
        }

    }
}
