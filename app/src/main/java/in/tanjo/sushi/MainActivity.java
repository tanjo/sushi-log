package in.tanjo.sushi;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.PopupMenu;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import in.tanjo.sushi.model.CountableSushiModel;
import in.tanjo.sushi.model.NoteManager;
import in.tanjo.sushi.model.SushiModel;

public class MainActivity extends AppCompatActivity {
  @Bind(R.id.main_framelayout) FrameLayout mFrameLayout;
  @Bind(R.id.main_recycler_view) RecyclerView mRecyclerView;

  private NoteManager mNoteManager;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    ButterKnife.bind(this);

    mNoteManager = new NoteManager(this);

    mRecyclerView.setHasFixedSize(true);
    mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

    updateMainAdapter();
  }

  private void updateMainAdapter() {
    MainAdapter mainAdapter = new MainAdapter(mNoteManager.getActiveNote().getSushiModelList());
    mainAdapter.setOnMainAdapterItemClickListener(new MainAdapter.OnMainAdapterItemClickListener() {
      @Override
      public void onItemClick(View v, MainAdapter adapter, int position, final CountableSushiModel countableSushiModel) {
        countableSushiModel.setCount(countableSushiModel.getCount() + 1);
        updateMainAdapter();
        mNoteManager.saveActiveNoteId();
        NoteManager.saveNote(MainActivity.this, mNoteManager.getActiveNote());
      }

      @Override
      public void onItemLongClick(View v, MainAdapter adapter, int position, final CountableSushiModel countableSushiModel) {
        PopupMenu popupMenu = new PopupMenu(MainActivity.this, v);
        popupMenu.getMenuInflater().inflate(R.menu.popup_countable_sushi_model_menu, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
          @Override
          public boolean onMenuItemClick(MenuItem item) {
            switch (item.getItemId()) {
              case R.id.popup_menu_sushi_delete:
                mNoteManager.getActiveNote().getSushiModelList().remove(countableSushiModel);
                updateMainAdapter();
                final Snackbar snackbar = Snackbar.make(mFrameLayout, countableSushiModel.getName() + "を削除しました", Snackbar.LENGTH_LONG);
                snackbar.setAction("OK", new View.OnClickListener() {
                  @Override
                  public void onClick(View v) {
                    snackbar.dismiss();
                  }
                });
                snackbar.show();
                mNoteManager.saveActiveNoteId();
                NoteManager.saveNote(MainActivity.this, mNoteManager.getActiveNote());
                break;
              case R.id.popup_menu_sushi_plus1:
                countableSushiModel.setCount(countableSushiModel.getCount() + 1);
                updateMainAdapter();
                mNoteManager.saveActiveNoteId();
                NoteManager.saveNote(MainActivity.this, mNoteManager.getActiveNote());
                break;
              case R.id.popup_menu_sushi_minus1:
                if (countableSushiModel.getCount() - 1 >= 0) {
                  countableSushiModel.setCount(countableSushiModel.getCount() - 1);
                }
                updateMainAdapter();
                mNoteManager.saveActiveNoteId();
                NoteManager.saveNote(MainActivity.this, mNoteManager.getActiveNote());
                break;
            }
            return true;
          }
        });
        popupMenu.show();
      }
    });
    mRecyclerView.setAdapter(mainAdapter);
  }

  @OnClick(R.id.main_floating_action_button)
  void add() {
    AddSushiActivity.startActivityWithSushiRequestCode(this);
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (resultCode == RESULT_OK) {
      switch (requestCode) {
        case AddSushiActivity.REQUESTCODE_SUSHI_OBJECT:
          Bundle bundle = data.getExtras();
          SushiModel sushiModel = (SushiModel) bundle.getSerializable(AddSushiActivity.BUNDLEKEY_SUSHI_MODEL);
          if (sushiModel != null) {
            CountableSushiModel countableSushiModel = new CountableSushiModel(sushiModel);
            mNoteManager.getActiveNote().getSushiModelList().add(countableSushiModel);

            updateMainAdapter();

            final Snackbar snackbar = Snackbar.make(mFrameLayout, sushiModel.getName() + "を追加しました", Snackbar.LENGTH_LONG);
            snackbar.setAction("OK", new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                snackbar.dismiss();
              }
            });
            snackbar.show();

            mNoteManager.saveActiveNoteId();
            NoteManager.saveNote(this, mNoteManager.getActiveNote());
          }
          break;
      }
    }
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.main_menu, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    if (item.getItemId() == R.id.action_settings) {
      Toast.makeText(this, "設定", Toast.LENGTH_SHORT).show();
      return true;
    }
    return super.onOptionsItemSelected(item);
  }

  @Override
  public boolean dispatchKeyEvent(KeyEvent event) {
    return super.dispatchKeyEvent(event);
  }
}

