package in.tanjo.sushi.model;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Note extends AbsNoteModel {

    @SerializedName("description")
    private String description;

    @SerializedName("store")
    private StoreModel storeModel;

    @SerializedName("sushi_list")
    private List<CountableSushi> sushis;

    public Note() {
        super();
        init();
    }

    private void init() {
        storeModel = new StoreModel();
        sushis = new ArrayList<>();
    }

    public static String toJson(Note model) {
        return new Gson().toJson(model, Note.class);
    }

    public static Note fromJson(String json) {
        return new Gson().fromJson(json, Note.class);
    }

    public StoreModel getStoreModel() {
        return storeModel;
    }

    public void setStoreModel(StoreModel storeModel) {
        this.storeModel = storeModel;
    }

    public List<CountableSushi> getSushis() {
        return sushis;
    }

    public void setSushis(List<CountableSushi> sushis) {
        this.sushis = sushis;
    }

    public String getDescription() {
        return optString(description);
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
