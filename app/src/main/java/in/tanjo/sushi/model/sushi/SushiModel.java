package in.tanjo.sushi.model.sushi;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

public class SushiModel {

  @SerializedName("name") String mName;                                  // 名前
  @SerializedName("count") int mCount;                                   // 個数
  @SerializedName("per_price") int mPerPrice;                            // 1個あたりの価格
  @SerializedName("per_consumption_tax") double mPerConsumptionTaxRatio; // 1個あたりの消費税率
  @SerializedName("taste") String mTaste;                                // 味

  public String getName() {
    return mName;
  }

  public void setName(String name) {
    mName = name;
  }

  public int getCount() {
    return mCount;
  }

  public void setCount(int count) {
    mCount = count;
  }

  public int getPerPrice() {
    return mPerPrice;
  }

  public void setPerPrice(int perPrice) {
    mPerPrice = perPrice;
  }

  public double getPerConsumptionTaxRatio() {
    return mPerConsumptionTaxRatio;
  }

  public void setPerConsumptionTaxRatio(double perConsumptionTaxRatio) {
    mPerConsumptionTaxRatio = perConsumptionTaxRatio;
  }

  public String getTaste() {
    return mTaste;
  }

  public void setTaste(String taste) {
    mTaste = taste;
  }

  public int getAllPrice() {
    return (int)Math.floor(mCount * mPerPrice * (1.0 + 0.01 * mPerConsumptionTaxRatio));
  }

  public String toJson() {
    return new Gson().toJson(this, SushiModel.class);
  }

  public static SushiModel fromJson(String json) {
    return new Gson().fromJson(json, SushiModel.class);
  }

  public static String toJson(SushiModel model) {
    return new Gson().toJson(model, SushiModel.class);
  }
  
}
