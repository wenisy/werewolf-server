package hello;

public class Greeting {

    private String Id;

    private String content;

    public Greeting() {
    }

    public Greeting(String Id, String content) {
        this.Id = Id;
        this.content = content;
    }

    public String getId() {
        return Id;
    }

    public String getContent() {
        return content;
    }

}
