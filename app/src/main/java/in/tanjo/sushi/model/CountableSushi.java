package in.tanjo.sushi.model;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

public class CountableSushi extends Sushi {

    @SerializedName("count")
    int count;

    public CountableSushi(String name, int price, int count) {
        super(name, price);
        this.count = count;
    }

    public CountableSushi(Sushi sushi) {
        super(sushi.getName(), sushi.getPrice());
        count = 0;
    }

    public static String toJson(CountableSushi model) {
        return new Gson().toJson(model, CountableSushi.class);
    }

    public static CountableSushi fromJson(String json) {
        return new Gson().fromJson(json, CountableSushi.class);
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getCountText() {
        if (count > 0) {
            return String.valueOf(count) + "貫";
        } else {
            return "";
        }
    }
}
