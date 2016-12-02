package in.tanjo.sushi.model;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

public class Sushi extends AbsGsonModel {

    @SerializedName("id")
    private String id;

    @SerializedName("name")
    private String name;

    @SerializedName("price")
    private int price;

    public Sushi() {
    }

    public Sushi(String name, int price) {
        this.name = name;
        this.price = price;
    }

    public static String toJson(Sushi model) {
        return new Gson().toJson(model, Sushi.class);
    }

    public static Sushi fromJson(String json) {
        return new Gson().fromJson(json, Sushi.class);
    }

    public String getId() {
        return optString(id);
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return optString(name);
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getPriceText() {
        if (price > 0) {
            return String.valueOf(price) + "円";
        } else {
            return "";
        }
    }
}
