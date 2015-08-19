package in.tanjo.sushi.model;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class NoteModel extends AbsNoteModel {

  @SerializedName("description") String mDescription;
  @SerializedName("store") StoreModel mStoreModel;
  @SerializedName("sushi_list") List<CountableSushiModel> mSushiModelList;

  public NoteModel() {
    super();
    init();
  }

  private void init() {
    mStoreModel = new StoreModel();
    mSushiModelList = new ArrayList<>();
  }

  public static String toJson(NoteModel model) {
    return new Gson().toJson(model, NoteModel.class);
  }

  public static NoteModel fromJson(String json) {
    return new Gson().fromJson(json, NoteModel.class);
  }

  public StoreModel getStoreModel() {
    return mStoreModel;
  }

  public void setStoreModel(StoreModel storeModel) {
    mStoreModel = storeModel;
  }

  public List<CountableSushiModel> getSushiModelList() {
    return mSushiModelList;
  }

  public void setSushiModelList(List<CountableSushiModel> sushiModelList) {
    mSushiModelList = sushiModelList;
  }

  public String getDescription() {
    return optString(mDescription);
  }

  public void setDescription(String description) {
    mDescription = description;
  }
}
