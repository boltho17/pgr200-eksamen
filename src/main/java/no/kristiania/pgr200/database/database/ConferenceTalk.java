package no.kristiania.pgr200.database.database;

public class ConferenceTalk {

    private int id;
    private String title;
    private String description;
    private String topic;

    public ConferenceTalk(String title, String description, String topic) {
        this.title = title;
        this.description = description;
        this.topic = topic;
    }

    public ConferenceTalk () {

    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String toString() {
        return title + " " + description + " " + topic;
    }
}
