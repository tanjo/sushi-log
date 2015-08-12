package in.tanjo.sushi;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import in.tanjo.sushi.model.EventModel;
import in.tanjo.sushi.model.store.StoreModel;

public class CreateEventActivity extends Activity {

  @Bind(R.id.create_event_activity_eventmei_view) TextView mEventMeiView;
  @Bind(R.id.create_event_activity_eventmei_edit) EditText mEventMeiEdit;
  @Bind(R.id.create_event_activity_eventday_view) TextView mEventDayView;
  @Bind(R.id.create_event_activity_datepicker) DatePicker mDatePicker;
  @Bind(R.id.create_event_activity_sakusei_button) Button mSakuseiButton;
  @Bind(R.id.create_event_activity_old_list_title_view) TextView mOldListTitleView;
  @Bind(R.id.create_event_activity_old_event_list) ListView mOldEventList;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_create_event);
    ButterKnife.bind(this);

    setupTypeface();

    StoreModel storeModel = (StoreModel)getIntent().getSerializableExtra(StoreModel.INTENT_PUT_EXTRA_KEY);
    if (storeModel != null) {
      setTitle(storeModel.getName());
    }
  }

  @OnClick(R.id.create_event_activity_sakusei_button)
  void sakusei() {
    next(new StoreModel(), new EventModel());
  }

  private void next(StoreModel storeModel, EventModel eventModel) {
    Intent intent = new Intent(this, MemoActivity.class);
    intent.putExtra(StoreModel.INTENT_PUT_EXTRA_KEY, storeModel);
    intent.putExtra(EventModel.INTENT_PUT_EXTRA_KEY, eventModel);
    startActivity(intent);
  }

  private void setupTypeface() {
    Typeface tf = Typeface.createFromAsset(getAssets(), "Ounen-mouhitsu.ttf");
    mEventMeiView.setTypeface(tf);
    mEventDayView.setTypeface(tf);
    mSakuseiButton.setTypeface(tf);
    mOldListTitleView.setTypeface(tf);
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.menu_create_event, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();

    //noinspection SimplifiableIfStatement
    if (id == R.id.action_settings) {
      return true;
    }

    return super.onOptionsItemSelected(item);
  }
}
