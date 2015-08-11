package in.tanjo.sushi.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class EventModel {

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
}
