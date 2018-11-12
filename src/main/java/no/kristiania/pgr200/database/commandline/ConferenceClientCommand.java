package no.kristiania.pgr200.database.commandline;

public interface ConferenceClientCommand {

    public default String getArgument(String[] args, String string) {
        for (int i = 0; i < args.length; i++) {
            if (args[i].equals(string)) {
                return args[i + 1];
            }
        }
        return null;
    }

    void readArguments(String[] args);

    String getTitle();
    String getDescription();
}
