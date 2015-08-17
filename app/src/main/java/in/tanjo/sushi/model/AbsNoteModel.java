package in.tanjo.sushi.model;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.util.UUID;

public class AbsNoteModel extends AbsGsonModel {

  @SerializedName("id") String mId;
  @SerializedName("title") String mTitle;

  public AbsNoteModel() {
    mId = UUID.randomUUID().toString();
  }

  public static String toJson(AbsNoteModel model) {
    return new Gson().toJson(model, AbsNoteModel.class);
  }

  public static AbsNoteModel fromJson(String json) {
    return new Gson().fromJson(json, AbsNoteModel.class);
  }

  public String getId() {
    return optString(mId);
  }

  public void setId(String id) {
    mId = id;
  }

  public String getTitle() {
    return optString(mTitle);
  }

  public void setTitle(String title) {
    mTitle = title;
  }
}
