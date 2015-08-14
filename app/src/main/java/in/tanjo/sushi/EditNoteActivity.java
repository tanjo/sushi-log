package in.tanjo.sushi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import butterknife.Bind;
import butterknife.ButterKnife;
import in.tanjo.sushi.model.NoteModel;
import in.tanjo.sushi.model.StoreModel;

public class EditNoteActivity extends AppCompatActivity {
  public static final int REQUESTCODE_NOTE_OBJECT = 22002;
  public static final String INTENT_NOTE_OBJECT = "key_intent_note_object";
  public static final String BUNDLEKEY_NOTE_OBJECT = "key_note_object";

  @Bind(R.id.edit_note_activity_memo_title_edit) EditText mTitleEditText;
  @Bind(R.id.edit_note_activity_store_name_edit) EditText mStoreEditText;
  @Bind(R.id.edit_note_activity_description_edit) EditText mDescriptionEditText;
  @Bind(R.id.edit_note_activity_description_textinputlayout) TextInputLayout mDescriptionTextInputLayout;
  @Bind(R.id.edit_note_activity_memo_title_textinputlayout) TextInputLayout mTitleTextInputLayout;
  @Bind(R.id.edit_note_activity_store_name_textinputlayout) TextInputLayout mStoreTextInputLayout;

  private NoteModel mNoteModel;

  public static void startActivityWithNoteObjectAndRequestCode(Activity activity, NoteModel noteModel) {
    Intent intent = new Intent(activity, EditNoteActivity.class);
    intent.putExtra(INTENT_NOTE_OBJECT, noteModel);
    activity.startActivityForResult(intent, REQUESTCODE_NOTE_OBJECT);
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_edit_note);
    ButterKnife.bind(this);

    catchNoteModel();
  }

  private void catchNoteModel() {
    Intent intent = getIntent();
    if (intent != null) {
      mNoteModel = (NoteModel)intent.getSerializableExtra(INTENT_NOTE_OBJECT);
      if (mNoteModel != null) {
        // Title
        mTitleEditText.setText(mNoteModel.getTitle());
        // Description
        mDescriptionEditText.setText(mNoteModel.getDescription());
        // Store
        StoreModel storeModel = mNoteModel.getStoreModel();
        if (storeModel != null) {
          mStoreEditText.setText(storeModel.getName());
        } else {
          mNoteModel.setStoreModel(new StoreModel());
        }
      } else {
        mNoteModel = new NoteModel();
      }
    }
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu_edit_note, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    if (item.getItemId() == R.id.action_note_input_complete) {
      complete();
      return true;
    }

    return super.onOptionsItemSelected(item);
  }

  private void setNoteResult(NoteModel noteModel) {
    if (noteModel != null) {
      Intent data = new Intent();
      Bundle bundle = new Bundle();
      bundle.putSerializable(BUNDLEKEY_NOTE_OBJECT, noteModel);
      data.putExtras(bundle);
      setResult(RESULT_OK, data);
    }
  }

  private void complete() {
    mNoteModel.getStoreModel().setName(mStoreEditText.getText().toString());
    mNoteModel.setTitle(mTitleEditText.getText().toString());
    mNoteModel.setDescription(mDescriptionEditText.getText().toString());
    setNoteResult(mNoteModel);
    finish();
  }
}
