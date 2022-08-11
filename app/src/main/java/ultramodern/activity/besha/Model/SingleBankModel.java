package ultramodern.activity.besha.Model;

public class SingleBankModel {
    private String amount;
    private String date;
    private String time;
    private String imageURL;
    private String category;

    public SingleBankModel() {
    }

    public SingleBankModel( String amount, String date, String time, String imageURL, String category) {
        this.amount = amount;
        this.date = date;
        this.time = time;
        this.imageURL = imageURL;
        this.category = category;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
