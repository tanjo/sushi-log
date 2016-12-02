package in.tanjo.sushi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.tanjo.sushi.model.Note;
import in.tanjo.sushi.model.StoreModel;

public class EditNoteActivity extends AppCompatActivity {

    static final int REQUESTCODE_NOTE_OBJECT = 22002;

    static final String BUNDLEKEY_NOTE_OBJECT = "key_note_object";

    private static final String INTENT_NOTE_OBJECT = "key_intent_note_object";

    @BindView(R.id.edit_note_activity_memo_title_edit)
    EditText titleEditText;

    @BindView(R.id.edit_note_activity_store_name_edit)
    EditText storeEditText;

    @BindView(R.id.edit_note_activity_description_edit)
    EditText descriptionEditText;

    @BindView(R.id.edit_note_toolbar)
    Toolbar toolbar;

    private Note note;

    static void startActivityWithNoteObjectAndRequestCode(Activity activity, Note note) {
        Intent intent = new Intent(activity, EditNoteActivity.class);
        intent.putExtra(INTENT_NOTE_OBJECT, note);
        activity.startActivityForResult(intent, REQUESTCODE_NOTE_OBJECT);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        catchNoteModel();
    }

    private void catchNoteModel() {
        Intent intent = getIntent();
        if (intent != null) {
            note = (Note) intent.getSerializableExtra(INTENT_NOTE_OBJECT);
            if (note != null) {
                // Title
                titleEditText.setText(note.getTitle());
                // Description
                descriptionEditText.setText(note.getDescription());
                // Store
                StoreModel storeModel = note.getStoreModel();
                if (storeModel != null) {
                    storeEditText.setText(storeModel.getName());
                } else {
                    note.setStoreModel(new StoreModel());
                }
            } else {
                note = new Note();
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

    private void complete() {
        note.getStoreModel().setName(storeEditText.getText().toString());
        note.setTitle(titleEditText.getText().toString());
        note.setDescription(descriptionEditText.getText().toString());
        setNoteResult(note);
        finish();
    }

    private void setNoteResult(Note note) {
        if (note != null) {
            Intent data = new Intent();
            Bundle bundle = new Bundle();
            bundle.putSerializable(BUNDLEKEY_NOTE_OBJECT, note);
            data.putExtras(bundle);
            setResult(RESULT_OK, data);
        }
    }
}
