package in.tanjo.sushi.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class StoreModel {

  @SerializedName("name") String mName;                      // 店名
  @SerializedName("date") Date mDate;                        // 来店日
  @SerializedName("sushi_list") List<SushiModel> mSushiList; // 寿司リスト

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
}
