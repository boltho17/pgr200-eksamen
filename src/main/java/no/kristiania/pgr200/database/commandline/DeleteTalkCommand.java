package no.kristiania.pgr200.database.commandline;

public class DeleteTalkCommand implements ConferenceClientCommand {

    private String id;
    private String title;
    private String description;

    public DeleteTalkCommand() {
    }

    public DeleteTalkCommand(String id) {
        this.id = id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {this.id = id;}

    public String getDescription() {
        return description;
    }


    @Override
    public void readArguments(String[] args) {
        setId(getArgument(args, "-id"));
    }

}
