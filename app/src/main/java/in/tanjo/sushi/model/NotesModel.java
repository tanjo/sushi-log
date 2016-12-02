package in.tanjo.sushi.model;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class NotesModel extends AbsGsonModel {

    @SerializedName("notes")
    private List<AbsNoteModel> mNotes;

    public NotesModel() {
        mNotes = new ArrayList<AbsNoteModel>();
    }

    public static String toJson(NotesModel model) {
        return new Gson().toJson(model, NotesModel.class);
    }

    public static NotesModel fromJson(String json) {
        return new Gson().fromJson(json, NotesModel.class);
    }

    public List<AbsNoteModel> getNotes() {
        return mNotes;
    }

    public void setNotes(List<AbsNoteModel> notes) {
        mNotes = notes;
    }
}
