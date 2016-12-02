package in.tanjo.sushi.model;

import android.content.Context;
import android.content.SharedPreferences;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.List;

public class NoteManager {

    private static final String NOTE_IDS_SAVE_FILE_NAME = "note_ids.txt";

    private static final String NOTE_IDS_SAVE_CHARSET_NAME = "UTF-8";

    private static final String NOTE_MODEL_SHARED_PREFERENCES = "note_model_shared_preferences";

    private static final String ACTIVE_NOTE_MODEL_KEY = "active_note_model_key";

    private Context mContext;

    private NotesModel mNotesModel;

    private Note mActiveNote;

    public NoteManager(Context context) {
        mContext = context;
        restoreNotesModel();
        restoreActiveNote();
    }

    /**
     * NotesModel を読み込み
     */
    private void restoreNotesModel() {
        String json = "";
        try {
            InputStream in = mContext.openFileInput(NOTE_IDS_SAVE_FILE_NAME);
            BufferedReader reader = new BufferedReader(new InputStreamReader(in, NOTE_IDS_SAVE_CHARSET_NAME));
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
            mNotesModel = NotesModel.fromJson(json);
        } else {
            mNotesModel = new NotesModel();
        }
    }

    /**
     * アクティブなノートを読み込み
     */
    private void restoreActiveNote() {
        SharedPreferences sp = mContext.getSharedPreferences(NOTE_MODEL_SHARED_PREFERENCES, Context.MODE_PRIVATE);
        mActiveNote = restoreNote(mContext, sp.getString(ACTIVE_NOTE_MODEL_KEY, ""));
    }

    /**
     * Note の ID から NoteModel を取得
     */
    private static Note restoreNote(Context context, String noteId) {
        if (noteId == null || noteId.length() == 0) {
            return new Note();
        }
        String json = "";
        try {
            InputStream in = context.openFileInput(noteId);
            BufferedReader reader = new BufferedReader(new InputStreamReader(in, NOTE_IDS_SAVE_CHARSET_NAME));
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
            return Note.fromJson(json);
        } else {
            return new Note();
        }
    }

    /**
     * アクティブなノートを取得する
     */
    public Note getActiveNote() {
        return mActiveNote;
    }

    /**
     * アクティブなノートをセットする
     */
    public void setActiveNote(Note activeNote) {
        mActiveNote = activeNote;
    }

    /**
     * アクティブなノートを保存
     */
    public void saveActiveNote() {
        SharedPreferences sp = mContext.getSharedPreferences(NOTE_MODEL_SHARED_PREFERENCES, Context.MODE_PRIVATE);
        if (mActiveNote != null && mActiveNote.getId() != null) {
            sp.edit().putString(ACTIVE_NOTE_MODEL_KEY, mActiveNote.getId()).apply();
            saveNote(mContext, mActiveNote);
        }
    }

    /**
     * NoteModel を保存
     */
    private static void saveNote(Context context, Note note) {
        try {
            OutputStream out = context.openFileOutput(note.getId(), Context.MODE_PRIVATE);
            PrintWriter writer = new PrintWriter(new OutputStreamWriter(out, NOTE_IDS_SAVE_CHARSET_NAME));
            writer.append(note.toJson());
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * NotesModel を取得する
     */
    public NotesModel getNotesModel() {
        return mNotesModel;
    }

    /**
     * Note の追加
     */
    public void add(Note note) {
        mNotesModel.getNotes().add(note);
        saveNotesModel();
    }

    /**
     * NotesModel を保存
     */
    public void saveNotesModel() {
        try {
            OutputStream out = mContext.openFileOutput(NOTE_IDS_SAVE_FILE_NAME, Context.MODE_PRIVATE);
            PrintWriter writer = new PrintWriter(new OutputStreamWriter(out, NOTE_IDS_SAVE_CHARSET_NAME));
            writer.append(mNotesModel.toJson());
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Note の置換
     */
    public void replace(Note note) {
        if (note != null) {
            List<AbsNoteModel> list = mNotesModel.getNotes();
            for (AbsNoteModel absNoteModel : list) {
                if (note.getId().equals(absNoteModel.getId())) {
                    mNotesModel.getNotes().set(list.indexOf(absNoteModel), note);
                    return;
                }
            }
        }
    }

    /**
     * Note の削除
     */
    public void remove(Note note) {
        remove(note);
    }

    /**
     * Note の存在を確認
     */
    public boolean contains(Note note) {
        return contains((AbsNoteModel) note);
    }

    /**
     * Note の存在を確認
     */
    public boolean contains(AbsNoteModel noteModel) {
        List<AbsNoteModel> list = mNotesModel.getNotes();
        for (AbsNoteModel absNoteModel : list) {
            if (noteModel.getId().equals(absNoteModel.getId())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Note の削除
     */
    public void remove(AbsNoteModel noteModel) {
        mNotesModel.getNotes().remove(noteModel);
        deleteNote(mContext, noteModel.getId());
        saveNotesModel();
    }

    /**
     * Note を削除
     */
    private static void deleteNote(Context context, String noteId) {
        context.deleteFile(noteId);
    }

    /**
     * Note の取得
     */
    public Note getNote(String noteId) {
        return restoreNote(mContext, noteId);
    }

    /**
     * Note の上書き
     */
    public void setNote(Note note) {
        saveNote(mContext, note);
    }
}
