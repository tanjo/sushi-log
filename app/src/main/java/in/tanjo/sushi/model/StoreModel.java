package in.tanjo.sushi.model;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.util.UUID;

public class StoreModel extends AbsGsonModel {

  @SerializedName("id") String mId;
  @SerializedName("name") String mName;

  public StoreModel() {
    mId = UUID.randomUUID().toString();
  }

  public static String toJson(StoreModel model) {
    return new Gson().toJson(model, StoreModel.class);
  }

  public static StoreModel fromJson(String json) {
    return new Gson().fromJson(json, StoreModel.class);
  }

  public String getId() {
    return mId;
  }

  public void setId(String id) {
    mId = id;
  }

  public StoreModel(String name) {
    mName = name;
  }

  public String getName() {
    return mName;
  }

  public void setName(String name) {
    mName = name;
  }
}
