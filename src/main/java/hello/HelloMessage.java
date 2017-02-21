package hello;

public class HelloMessage {

    private String name;
    private String myId;

    public HelloMessage() {
    }

    public HelloMessage(String name, String myId) {
        this.name = name;
        this.myId = myId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMyId() {
        return myId;
    }

    public void setMyId(String myId) {
        this.myId = myId;
    }
}
