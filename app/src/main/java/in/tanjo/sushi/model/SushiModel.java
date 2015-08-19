package in.tanjo.sushi.model;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

public class SushiModel extends AbsGsonModel {

  @SerializedName("id") String mId;
  @SerializedName("name") String mName;
  @SerializedName("price") int mPrice;

  public SushiModel() {
  }

  public SushiModel(String name, int price) {
    mName = name;
    mPrice = price;
  }

  public static String toJson(SushiModel model) {
    return new Gson().toJson(model, SushiModel.class);
  }

  public static SushiModel fromJson(String json) {
    return new Gson().fromJson(json, SushiModel.class);
  }

  public String getId() {
    return optString(mId);
  }

  public void setId(String id) {
    mId = id;
  }

  public String getName() {
    return optString(mName);
  }

  public void setName(String name) {
    mName = name;
  }

  public int getPrice() {
    return mPrice;
  }

  public void setPrice(int price) {
    mPrice = price;
  }

  public String getPriceText() {
    if (mPrice > 0) {
      return String.valueOf(mPrice) + "円";
    } else {
      return "";
    }
  }
}
