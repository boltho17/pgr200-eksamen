package no.kristiania.pgr200.database.commandline;

public class DeleteTalkCommand implements ConferenceClientCommand {

    private String id;

    public DeleteTalkCommand() {
    }

    public DeleteTalkCommand(String id) {
        this.id = id;
    }

    public void setId(String id) {
        this.id = id;
    }


    @Override
    public void readArguments(String[] args) {
        setId(getArgument(args, "-id"));
    }

}
