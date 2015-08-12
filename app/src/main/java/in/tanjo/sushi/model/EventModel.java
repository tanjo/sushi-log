package in.tanjo.sushi.model;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

import in.tanjo.sushi.model.store.StoreModel;
import in.tanjo.sushi.model.sushi.SushiModel;

public class EventModel implements Serializable {
  public static final String INTENT_PUT_EXTRA_KEY = "intent_put_extra_event_model_key";

  @SerializedName("name") String mName;
  @SerializedName("ate_sushi") List<SushiModel> mSushiList;
  @SerializedName("store") StoreModel mStore;

  public String getName() {
    return mName;
  }

  public void setName(String name) {
    mName = name;
  }

  public List<SushiModel> getSushiList() {
    return mSushiList;
  }

  public void setSushiList(List<SushiModel> sushiList) {
    mSushiList = sushiList;
  }

  public StoreModel getStore() {
    return mStore;
  }

  public void setStore(StoreModel store) {
    mStore = store;
  }

  public String toJson() {
    return new Gson().toJson(this, EventModel.class);
  }

  public static EventModel fromJson(String json) {
    return new Gson().fromJson(json, EventModel.class);
  }

  public static String toJson(EventModel model) {
    return new Gson().toJson(model, EventModel.class);
  }
}
