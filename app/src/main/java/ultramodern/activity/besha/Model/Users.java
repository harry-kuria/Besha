package ultramodern.activity.besha.Model;

public class Users {
    private String username;
    private String imageURL;
    private String Id;


    public Users(String username, String imageURL, String Id) {
        this.username = username;
        this.imageURL = imageURL;
        this.Id = Id;
    }

    public Users() {
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }
}
