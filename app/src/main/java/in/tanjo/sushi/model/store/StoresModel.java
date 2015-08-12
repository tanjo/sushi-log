package in.tanjo.sushi.model.store;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class StoresModel {
  @SerializedName("items") List<StoreModel> mItems;

  public StoresModel() {
    mItems = new ArrayList<StoreModel>();
  }

  public List<StoreModel> getItems() {
    return mItems;
  }

  public void setStoreModels(List<StoreModel> storeModels) {
    mItems = storeModels;
  }

  public String toJson() {
    return new Gson().toJson(this, StoresModel.class);
  }

  public static StoresModel fromJson(String json) {
    return new Gson().fromJson(json, StoresModel.class);
  }

  public static String toJson(StoresModel model) {
    return new Gson().toJson(model, StoresModel.class);
  }

}
