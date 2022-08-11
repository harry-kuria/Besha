package ultramodern.activity.besha;

public class SendMessage {
    private String sender;
    private String reciever;
    private String message;

    public SendMessage(String sender, String reciever, String message) {
        this.sender = sender;
        this.reciever = reciever;
        this.message = message;
    }
    private SendMessage(){}
}
