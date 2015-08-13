package in.tanjo.sushi.model;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

public class CountableSushiModel extends SushiModel {
  @SerializedName("count") int mCount;

  public CountableSushiModel() {
  }

  public CountableSushiModel(String name, int price, int count) {
    super(name, price);
    mCount = count;
  }

  public CountableSushiModel(SushiModel sushiModel) {
    super(sushiModel.getName(), sushiModel.getPrice());
    mCount = 0;
  }

  public static String toJson(CountableSushiModel model) {
    return new Gson().toJson(model, CountableSushiModel.class);
  }

  public static CountableSushiModel fromJson(String json) {
    return new Gson().fromJson(json, CountableSushiModel.class);
  }
}
