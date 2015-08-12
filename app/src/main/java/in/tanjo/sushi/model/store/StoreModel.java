package in.tanjo.sushi.model.store;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import in.tanjo.sushi.model.EventModel;
import in.tanjo.sushi.model.sushi.SushiModel;

public class StoreModel {

  @SerializedName("name") String mName;                      // 店名
  @SerializedName("date") Date mDate;                        // 来店日
  @SerializedName("sushi_list") List<SushiModel> mSushiList; // 寿司リスト
  @SerializedName("events") List<EventModel> mEvents;        // イベントリスト

  public StoreModel(String name) {
    mSushiList = new ArrayList<SushiModel>();
    mDate = new Date();
    mName = name;
  }

  public String getName() {
    return mName;
  }

  public void setName(String name) {
    mName = name;
  }

  public Date getDate() {
    return mDate;
  }

  public void setDate(Date date) {
    mDate = date;
  }

  public List<SushiModel> getSushiList() {
    return mSushiList;
  }

  public void setSushiList(List<SushiModel> sushiList) {
    mSushiList = sushiList;
  }

  public String toJson() {
    return new Gson().toJson(this, StoreModel.class);
  }

  public static StoreModel fromJson(String json) {
    return new Gson().fromJson(json, StoreModel.class);
  }

  public static String toJson(StoreModel model) {
    return new Gson().toJson(model, StoreModel.class);
  }
}
