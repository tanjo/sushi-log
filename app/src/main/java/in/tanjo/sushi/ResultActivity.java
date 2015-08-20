package in.tanjo.sushi;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import in.tanjo.sushi.model.NoteModel;
import in.tanjo.sushi.model.StoreModel;
import in.tanjo.sushi.task.ResultCalcAsyncTask;

public class ResultActivity extends AppCompatActivity {
  public static final String INTENT_NOTE_OBJECT = "key_intent_note_object";

  @Bind(R.id.result_activity_toolbar) Toolbar mToolbar;
  @Bind(R.id.result_activity_sum_view) TextView mSumResultView;

  private NoteModel mNoteModel;
  private ResultCalcAsyncTask mResultCalcAsyncTask;

  public static void startActivityWithNoteObject(Activity activity, NoteModel noteModel) {
    Intent intent = new Intent(activity, ResultActivity.class);
    intent.putExtra(INTENT_NOTE_OBJECT, noteModel);
    activity.startActivity(intent);
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_result);
    ButterKnife.bind(this);
    setSupportActionBar(mToolbar);
    catchNoteModel();

    mResultCalcAsyncTask = new ResultCalcAsyncTask(mSumResultView, mNoteModel);
    mResultCalcAsyncTask.execute();
  }

  private void catchNoteModel() {
    Intent intent = getIntent();
    if (intent != null) {
      mNoteModel = (NoteModel)intent.getSerializableExtra(INTENT_NOTE_OBJECT);
    }
  }


  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.add_sushi_menu, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    if (item.getItemId() == R.id.action_sushi_input_complete) {
      mResultCalcAsyncTask.cancel(true);
      finish();
      return true;
    }
    return super.onOptionsItemSelected(item);
  }
}
