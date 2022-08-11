package ultramodern.activity.besha.Model;

public class SingleCardModel {
    private String category;
    private String amount;
    private String Id;
    private String time;
    private String date;

    public SingleCardModel(String category, String amount, String id, String time, String date) {
        this.category = category;
        this.amount = amount;
        Id = id;
        this.time = time;
        this.date = date;
    }

    public SingleCardModel() {
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
