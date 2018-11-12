package no.kristiania.pgr200.database.commandline;

public class AddTalkCommand implements ConferenceClientCommand {

    private String title;
    private String description;
    private String topic;

    public AddTalkCommand() {
    }

    public AddTalkCommand(String title, String description, String topic) {
        this.title = title;
        this.description = description;
        this.topic = topic;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    @Override
    public void readArguments(String[] args) {
        setTitle(getArgument(args, "-title"));
        setTopic(getArgument(args, "-topic"));
        setDescription(getArgument(args, "-description"));
    }

}
