package no.kristiania.pgr200.database.commandline;

import org.junit.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;


public class ConferenceCliClientTest {

    private ConferenceCommandLineClient client = new ConferenceCommandLineClient();

    @Test
    public void shouldDecodeAddCommand() throws IOException {
        String title = SampleData.sampleText(5);
        String description = SampleData.sampleText(10);
        ConferenceClientCommand command = client.decodeCommand(new String[] {
                "add",
                "-title", title,
                "-description", description
        });

        //TEST
        System.out.println(command.getTitle());
        System.out.println(command.getDescription()+"!?!?!?!?!");

        AddTalkCommand expectedCommand = new AddTalkCommand();
        expectedCommand.setTitle(title);
        expectedCommand.setDescription(description);
        assertThat(command).isInstanceOf(AddTalkCommand.class)
            .isEqualToComparingFieldByField(expectedCommand);
    }

    @Test
    public void shouldDecodeAddCommandWithTopic() throws IOException {
        String title = SampleData.sampleText(5);
        String description = SampleData.sampleText(10);
        String topic = SampleData.sampleTopic();
        ConferenceClientCommand command = client.decodeCommand(new String[] {
                "add",
                "-title", title,
                "-topic", topic,
                "-description", description
        });
        AddTalkCommand expectedCommand = new AddTalkCommand();
        expectedCommand.setTitle(title);
        expectedCommand.setTopic(topic);
        expectedCommand.setDescription(description);
        assertThat(command).isInstanceOf(AddTalkCommand.class)
            .isEqualToComparingFieldByField(expectedCommand);
    }

    @Test
    public void shouldDecodeListCommandWithTopic() throws IOException {
        String title = SampleData.sampleText(5);
        String description = SampleData.sampleText(10);
        String topic = SampleData.sampleTopic();
        ConferenceClientCommand command = client.decodeCommand(new String[] {
                "list",
                "-topic", topic,
        });
        ListTalksCommand expectedCommand = new ListTalksCommand();
        expectedCommand.setTopic(topic);
        assertThat(command).isInstanceOf(ListTalksCommand.class)
            .isEqualToComparingFieldByField(expectedCommand);
    }
}
