package no.kristiania.pgr200.database.commandline;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ConferenceCommandLineClient {

    /**
     * Decodes input from user and turns it into a command-object
     * @return the commandType with values from arguments
     * @throws IOException if command is invalid
     */
    public ConferenceClientCommand decodeCommand(String[] args) throws IOException {

        String input = args[0];

        switch (input) {
            case "add":
                return createAddCommand(args);
            case "delete":
                return createDeleteCommand(args);
            case "list":
                return createListCommand(args);
            default:
                throw new IOException();

        }
    }

    /**
     * Builds an AddCommand-instance
     * Default title: "unknown"
     * Default description: "unknown"
     * @param strings
     * @return
     */
    private AddTalkCommand createAddCommand(String[] strings) {

        String title = getArgument("-title", strings, "unknown");
        String description = getArgument("-description", strings, "unknown");
        String topic = getArgument("-topic", strings, null);

        return new AddTalkCommand(title, description, topic);
    }

    /**
     * Builds a RemoveCommand-instance
     * Default id: null
     * @param strings
     * @return
     */
    private DeleteTalkCommand createDeleteCommand(String[] strings) {

        String id = getArgument("-id", strings, null);

        return new DeleteTalkCommand(id);
    }


    /**
     * Builds an EditTalkCommand-instance
     * Defaut id: null
     * Default fieldToBeUpdated: null
     * Default newField : null
     * @param strings
     * @return
     */
    public ListTalksCommand createListCommand(String[] strings) {
        // input like: -id 42 -field name -new Something_Awesome -> name of talk 42 changed to "Something_Awesome"
        String id = getArgument("-id", strings, null);
        String title = getArgument("-title", strings, "unknown");
        String topic = getArgument("-topic", strings, "unknown");

        return new ListTalksCommand(id, title, topic);
    }

    /**
     * Returns value of given argument identifier
     * @param identifier of argument (i.e. "-title")
     * @return value if found or default value if not
     */
    private String getArgument(String identifier, String[] strings, String defaultValue) {
        for (int i = 0; i < strings.length - 1; i++) {
            Pattern pattern = Pattern.compile(strings[i]);
            Matcher matcher = pattern.matcher(identifier);
            if(matcher.matches()) {
                String value = strings[i + 1];
                i++;
                return value;
            }
        }
        return defaultValue;
    }


}