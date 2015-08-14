package in.tanjo.sushi.model;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class NoteModel extends AbsGsonModel {

  @SerializedName("id") String mId;
  @SerializedName("title") String mTitle;
  @SerializedName("description") String mDescription;
  @SerializedName("store") StoreModel mStoreModel;
  @SerializedName("sushi_list") List<CountableSushiModel> mSushiModelList;

  public NoteModel() {
    init(new StoreModel(), "");
  }

  private void init(StoreModel storeModel, String title) {
    mId = UUID.randomUUID().toString();
    mStoreModel = storeModel;
    mTitle = title;
    mSushiModelList = new ArrayList<>();
  }

  public static String toJson(NoteModel model) {
    return new Gson().toJson(model, NoteModel.class);
  }

  public static NoteModel fromJson(String json) {
    return new Gson().fromJson(json, NoteModel.class);
  }

  public String getId() {
    return mId;
  }

  public void setId(String id) {
    mId = id;
  }

  public StoreModel getStoreModel() {
    return mStoreModel;
  }

  public void setStoreModel(StoreModel storeModel) {
    mStoreModel = storeModel;
  }

  public String getTitle() {
    return mTitle;
  }

  public void setTitle(String title) {
    mTitle = title;
  }

  public List<CountableSushiModel> getSushiModelList() {
    return mSushiModelList;
  }

  public void setSushiModelList(List<CountableSushiModel> sushiModelList) {
    mSushiModelList = sushiModelList;
  }

  public String getDescription() {
    return mDescription;
  }

  public void setDescription(String description) {
    mDescription = description;
  }
}
