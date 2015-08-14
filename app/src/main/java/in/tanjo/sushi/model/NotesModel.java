package in.tanjo.sushi.model;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NotesModel extends AbsGsonModel {

  @SerializedName("ids") List<String> mIds;

  public NotesModel() {
  }

  public static String toJson(NotesModel model) {
    return new Gson().toJson(model, NotesModel.class);
  }

  public static NotesModel fromJson(String json) {
    return new Gson().fromJson(json, NotesModel.class);
  }

  public List<String> getIds() {
    return mIds;
  }

  public void setIds(List<String> ids) {
    mIds = ids;
  }
}
