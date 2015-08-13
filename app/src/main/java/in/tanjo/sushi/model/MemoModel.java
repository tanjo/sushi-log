package in.tanjo.sushi.model;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;

public class MemoModel extends AbsGsonModel {

  @SerializedName("id") String mId;
  @SerializedName("title") String mTitle;
  @SerializedName("sushi") List<CountableSushiModel> mSushiList;
  @SerializedName("created_at") String mCreatedAt;
  @SerializedName("updated_at") String mUpdatedAt;

  public MemoModel(String title) {
    mTitle = title;
    mCreatedAt = new Date().toString();
  }

  public static String toJson(MemoModel model) {
    return new Gson().toJson(model, MemoModel.class);
  }

  public static MemoModel fromJson(String json) {
    return new Gson().fromJson(json, MemoModel.class);
  }

  public String getTitle() {
    return mTitle;
  }

  public void setTitle(String title) {
    mTitle = title;
  }

  public List<CountableSushiModel> getSushiList() {
    return mSushiList;
  }

  public void setSushiList(List<CountableSushiModel> sushiList) {
    mSushiList = sushiList;
  }

  public String getCreatedAt() {
    return mCreatedAt;
  }

  public void setCreatedAt(String createdAt) {
    mCreatedAt = createdAt;
  }

  public String getUpdatedAt() {
    return mUpdatedAt;
  }

  public void setUpdatedAt(String updatedAt) {
    mUpdatedAt = updatedAt;
  }

  public static MemoModel restore(String key, Context context) {
    String json = "";

    try {
      InputStream in = context.openFileInput(key);
      BufferedReader reader = new BufferedReader(new InputStreamReader(in, key));
      String tmp;
      while ((tmp = reader.readLine()) != null) {
        json += tmp;
      }
      reader.close();
    } catch (IOException e) {
      e.printStackTrace();
      json = null;
    }
    if (json != null) {
      return MemoModel.fromJson(json);
    } else {
      return null;
    }
  }

  public static boolean save(String key, Context context, MemoModel memoModel) {
    String json = memoModel.toJson();

    boolean isSuccess = true;
    try {
      OutputStream out = context.openFileOutput(key, Context.MODE_PRIVATE);
      PrintWriter writer = new PrintWriter(new OutputStreamWriter(out, key));
      writer.append(json);
      writer.close();
    } catch (IOException e) {
      e.printStackTrace();
      isSuccess = false;
    }
    return isSuccess;
  }

  public static void delete(String key, Context context) {
    context.deleteFile(key);
  }
}
