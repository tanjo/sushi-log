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
  private NoteModel mActiveNote;

  public NoteManager(Context context) {
    mContext = context;
    restoreNoteList();
    restoreActiveNoteId();
  }

  /**
   * アクティブなノートを取得する
   */
  public NoteModel getActiveNote() {
    return mActiveNote;
  }

  /**
   * アクティブなノートをセットする
   */
  public void setActiveNote(NoteModel activeNote) {
    mActiveNote = activeNote;
  }

  /**
   * アクティブなノートの ID を読み込み
   */
  public void restoreActiveNoteId() {
    SharedPreferences sp = mContext.getSharedPreferences(NOTE_MODEL_SHARED_PREFERENCES, Context.MODE_PRIVATE);
    mActiveNote = restoreNote(mContext, sp.getString(ACTIVE_NOTE_MODEL_KEY, ""));
  }

  /**
   * アクティブなノートの ID を保存
   */
  public void saveActiveNoteId() {
    SharedPreferences sp = mContext.getSharedPreferences(NOTE_MODEL_SHARED_PREFERENCES, Context.MODE_PRIVATE);
    if (mActiveNote != null && mActiveNote.getId() != null) {
      sp.edit().putString(ACTIVE_NOTE_MODEL_KEY, mActiveNote.getId()).apply();
      saveNote(mContext, mActiveNote);
    }
  }

  public NotesModel getNotesModel() {
    return mNotesModel;
  }

  /**
   * Note ID を読み込み
   */
  public void restoreNoteList() {
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
      mNotesModel = NotesModel.fromJson("{\"notes\":[]}");
    }
  }

  /**
   * Note ID を保存
   */
  public void saveNoteList() {
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
   * Note の追加
   */
  public void add(NoteModel noteModel) {
    mNotesModel.getNotes().add(noteModel);
    saveNoteList();
  }

  /**
   * Note の置換
   */
  public void replace(NoteModel noteModel) {
    if (noteModel != null) {
      List<AbsNoteModel> list = mNotesModel.getNotes();
      for (AbsNoteModel absNoteModel : list) {
        if (noteModel.getId().equals(absNoteModel.getId())) {
          mNotesModel.getNotes().set(list.indexOf(absNoteModel), noteModel);
          return;
        }
      }
    }
  }

  /**
   * Note の削除
   */
  public void remove(NoteModel noteModel) {
    remove(noteModel);
  }

  public boolean contains(NoteModel noteModel) {
    return contains((AbsNoteModel) noteModel);
  }

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
    saveNoteList();
  }

  /**
   * Note の取得
   */
  public NoteModel getNote(String noteId) {
    return restoreNote(mContext, noteId);
  }

  /**
   * Note の上書き
   */
  public void setNote(NoteModel noteModel) {
    saveNote(mContext, noteModel);
  }

  /**
   * Note の ID から NoteModel を取得
   */
  public static NoteModel restoreNote(Context context, String noteId) {
    if (noteId == null || noteId.length() == 0) {
      return new NoteModel();
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
      return NoteModel.fromJson(json);
    } else {
      return new NoteModel();
    }
  }

  /**
   * NoteModel を保存
   */
  public static void saveNote(Context context, NoteModel noteModel) {
    try {
      OutputStream out = context.openFileOutput(noteModel.getId(), Context.MODE_PRIVATE);
      PrintWriter writer = new PrintWriter(new OutputStreamWriter(out, NOTE_IDS_SAVE_CHARSET_NAME));
      writer.append(noteModel.toJson());
      writer.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Note を削除
   */
  public static void deleteNote(Context context, String noteId) {
    context.deleteFile(noteId);
  }
}
