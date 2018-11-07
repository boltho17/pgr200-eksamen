package no.kristiania.pgr200.database.commandline;

public class ListTalksCommand implements ConferenceClientCommand {

    private String id, title, topic;

    public ListTalksCommand() {}

    public ListTalksCommand(String id, String title, String topic) {
        this.id = id;
        this.title = id;
        this.topic = topic;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    @Override
    public void readArguments(String[] args) {
        setTopic(getArgument(args, "-topic"));
    }

}
