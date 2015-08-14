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
    restoreNoteIds();
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
    }
  }

  /**
   * Note ID を読み込み
   */
  public void restoreNoteIds() {
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
      mNotesModel = NotesModel.fromJson("{\"ids\":[]}");
    }
  }

  /**
   * Note ID を保存
   */
  public void saveNoteIds() {
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
    mNotesModel.getIds().add(noteModel.getId());
    saveNote(mContext, noteModel);
    saveNoteIds();
  }

  /**
   * Note の削除
   */
  public void remove(NoteModel noteModel) {
    remove(noteModel.getId());
  }

  /**
   * Note の削除
   */
  public void remove(String noteID) {
    mNotesModel.getIds().remove(noteID);
    deleteNote(mContext, noteID);
    saveNoteIds();
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
