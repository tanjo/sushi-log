package in.tanjo.sushi.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MemosModel extends AbsGsonModel {

  @SerializedName("memos") List<String> mMemoModelList;

  public MemosModel add(String key) {
    return null; // TODO:
  }

  public MemosModel remove(String key) {
    return null; // TODO:
  }

}
