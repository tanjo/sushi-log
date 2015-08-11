package in.tanjo.sushi;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyActivity extends Activity {

  @Bind(R.id.my_activity_textView) TextView mTitleView;
  @Bind(R.id.my_activity_nyuten) Button mNyutenButton;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_my);
    ButterKnife.bind(this);
    setupTypeface();
  }

  private void setupTypeface() {
    Typeface tf = Typeface.createFromAsset(getAssets(), "Ounen-mouhitsu.ttf");
    mTitleView.setTypeface(tf);
    mNyutenButton.setTypeface(tf);
  }

  @OnClick(R.id.my_activity_nyuten)
  void nyuten() {
    Intent intent = new Intent(this, CreateStoreActivity.class);
    startActivity(intent);
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.my, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();
    if (id == R.id.action_settings) {
      return true;
    }
    return super.onOptionsItemSelected(item);
  }
}
