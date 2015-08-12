package in.tanjo.sushi.model.store;

import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class StoreManager {
  public static final String STORE_SAVE_FILE_NAME = "store_info.txt";
  public static final String STORE_SAVE_FILE_FORMAT = "UTF-8";

  private Context mContext;
  private StoresModel mStoresModel;

  public StoreManager(Context context) {
    mContext = context;
    mStoresModel = new StoresModel();
  }

  public void restore() {
    String json = "";

    try {
      InputStream in = mContext.openFileInput(STORE_SAVE_FILE_NAME);
      BufferedReader reader = new BufferedReader(new InputStreamReader(in, STORE_SAVE_FILE_FORMAT));
      String tmp;
      while ((tmp = reader.readLine()) != null) {
        json += tmp;
      }
      reader.close();
    } catch (IOException e) {
      e.printStackTrace();
      json = "{\"items\":[]}";
    }
    mStoresModel = StoresModel.fromJson(json);
  }

  public void save() {
    String json = mStoresModel.toJson();

    try {
      OutputStream out = mContext.openFileOutput(STORE_SAVE_FILE_NAME, Context.MODE_PRIVATE);
      PrintWriter writer = new PrintWriter(new OutputStreamWriter(out, STORE_SAVE_FILE_FORMAT));
      writer.append(json);
      writer.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void delete() {
    mContext.deleteFile(STORE_SAVE_FILE_NAME);
    mStoresModel = StoresModel.fromJson("{\"items\":[]}");
  }

  public StoresModel getStoresModel() {
    return mStoresModel;
  }
}