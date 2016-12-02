package in.tanjo.sushi;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import in.tanjo.sushi.adapter.MainAdapter;
import in.tanjo.sushi.adapter.NavigationAdapter;
import in.tanjo.sushi.listener.OnRecyclerViewAdapterItemClickListener;
import in.tanjo.sushi.model.AbsNoteModel;
import in.tanjo.sushi.model.CountableSushi;
import in.tanjo.sushi.model.NoteManager;
import in.tanjo.sushi.model.Note;
import in.tanjo.sushi.model.Sushi;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.main_coordinatorlayout)
    CoordinatorLayout coordinatorLayout;

    @BindView(R.id.main_drawerlayout)
    DrawerLayout mDrawerLayout;

    @BindView(R.id.main_recycler_view)
    RecyclerView mMainRecyclerView;

    @BindView(R.id.main_toolbar)
    Toolbar mToolbar;

    @BindView(R.id.main_floating_action_button)
    FloatingActionButton mFloatingActionButton;

    @BindView(R.id.navigation_recycler_view)
    RecyclerView mNavigationRecyclerView;

    private NoteManager mNoteManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        init();
    }

    /**
     * イニシャライザー
     */
    private void init() {
        mNoteManager = new NoteManager(this);

        setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(R.drawable.ic_menu_black_24dp);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawerLayout.openDrawer(mNavigationRecyclerView);
            }
        });

        mMainRecyclerView.setHasFixedSize(true);
        mMainRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        updateMainAdapter();

        initNavigaitonRecyclerView();
    }

    private void updateMainAdapter() {
        MainAdapter mainAdapter = new MainAdapter(mNoteManager.getActiveNote().getSushis());
        mainAdapter
                .setOnRecyclerViewAdapterItemClickListener(new OnRecyclerViewAdapterItemClickListener<CountableSushi>() {
                    @Override
                    public void onItemClick(View v, RecyclerView.Adapter adapter, int position, CountableSushi model) {
                        model.setCount(model.getCount() + 1);
                        changeItem(position, model);
                    }

                    @Override
                    public void onItemLongClick(View v, RecyclerView.Adapter adapter, final int position,
                            final CountableSushi model) {
                        PopupMenu popupMenu = new PopupMenu(MainActivity.this, v);
                        popupMenu.getMenuInflater().inflate(R.menu.popup_countable_sushi_model_menu, popupMenu.getMenu());
                        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                            @Override
                            public boolean onMenuItemClick(MenuItem item) {
                                switch (item.getItemId()) {
                                    case R.id.popup_menu_sushi_delete:
                                        removeItem(position, model);
                                        snackbar(model.getName() + "を削除しました");
                                        break;
                                    case R.id.popup_menu_sushi_plus1:
                                        model.setCount(model.getCount() + 1);
                                        changeItem(position, model);
                                        break;
                                    case R.id.popup_menu_sushi_minus1:
                                        if (model.getCount() - 1 >= 0) {
                                            model.setCount(model.getCount() - 1);
                                        }
                                        changeItem(position, model);
                                        break;
                                }
                                return true;
                            }
                        });
                        popupMenu.show();
                    }
                });
        mMainRecyclerView.setAdapter(mainAdapter);
    }

    /**
     * ナビゲーションのリサイクルViewを初期化
     */
    private void initNavigaitonRecyclerView() {
        mNavigationRecyclerView.setHasFixedSize(true);
        mNavigationRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        updateNavigationAdapter();
    }

    private void changeItem(int position, CountableSushi sushiModel) {
        mNoteManager.getActiveNote().getSushis().set(position, sushiModel);
        mMainRecyclerView.getAdapter().notifyItemChanged(position);
        mNoteManager.saveActiveNote();
    }

    private void removeItem(int position, CountableSushi sushiModel) {
        mNoteManager.getActiveNote().getSushis().remove(sushiModel);
        mMainRecyclerView.getAdapter().notifyItemRemoved(position);
        mNoteManager.saveActiveNote();
        // スクロールができなくなるとなにもできなくなるので強制的に表示してあげる.
        mFloatingActionButton.show();
    }

    /**
     * Snackbar を表示させる.
     */
    private void snackbar(String text) {
        final Snackbar snackbar = Snackbar.make(coordinatorLayout, text, Snackbar.LENGTH_LONG);
        snackbar.setAction("OK", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                snackbar.dismiss();
            }
        });
        snackbar.show();
    }

    private void updateNavigationAdapter() {
        NavigationAdapter navigationAdapter = new NavigationAdapter(mNoteManager.getNotesModel().getNotes());
        navigationAdapter
                .setOnRecyclerViewAdapterItemClickListener(new OnRecyclerViewAdapterItemClickListener<AbsNoteModel>() {
                    @Override
                    public void onItemClick(View v, RecyclerView.Adapter adapter, int position, AbsNoteModel model) {
                        if (!mNoteManager.contains(mNoteManager.getActiveNote())) {
                            mNoteManager.add(mNoteManager.getActiveNote());
                            mNoteManager.saveNotesModel();
                        }
                        mNoteManager.setActiveNote(mNoteManager.getNote(model.getId()));
                        mNoteManager.saveActiveNote();
                        updateMainAdapter();
                        mNavigationRecyclerView.getAdapter().notifyDataSetChanged();
                        mDrawerLayout.closeDrawers();
                    }

                    @Override
                    public void onItemLongClick(View v, RecyclerView.Adapter adapter, int position, final AbsNoteModel model) {
                        if (mNoteManager.getActiveNote().getId().equals(model.getId())) {
                            snackbar("現在使用中のファイルのため削除できません.");
                        } else {
                            new AlertDialog.Builder(MainActivity.this, R.style.AppDialog)
                                    .setTitle("ノートの削除")
                                    .setMessage(model.getTitle() + "を削除しますか？")
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            if (mNoteManager.contains(model)) {
                                                mNoteManager.remove(model);
                                                mNavigationRecyclerView.getAdapter().notifyDataSetChanged();
                                            }
                                        }
                                    })
                                    .setNegativeButton("キャンセル", null)
                                    .show();
                        }
                    }
                });
        mNavigationRecyclerView.setAdapter(navigationAdapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case EditNoteActivity.REQUESTCODE_NOTE_OBJECT: {
                    receiveNote(data);
                    break;
                }
                case AddSushiActivity.REQUESTCODE_SUSHI_OBJECT: {
                    receiveSushi(data);
                    break;
                }
            }
        }
    }

    /**
     * この Activity で NoteModel を受け取ったときに処理する.
     */
    private void receiveNote(Intent data) {
        if (data == null) {
            return;
        }
        Bundle bundle = data.getExtras();
        if (bundle != null) {
            Note note = (Note) bundle.getSerializable(EditNoteActivity.BUNDLEKEY_NOTE_OBJECT);
            if (note != null) {
                mNoteManager.setActiveNote(note);
                updateMainAdapter();
                snackbar("メモを更新しました");
                mNoteManager.saveActiveNote();
                mNoteManager.replace(mNoteManager.getActiveNote());
                mNoteManager.saveNotesModel();
                mNavigationRecyclerView.getAdapter().notifyDataSetChanged();
            }
        }
    }

    /**
     * この Activity で SushiModel を受け取ったときに処理する.
     */
    private void receiveSushi(Intent data) {
        if (data == null) {
            return;
        }
        Bundle bundle = data.getExtras();
        if (bundle != null) {
            Sushi sushi = (Sushi) bundle.getSerializable(AddSushiActivity.BUNDLEKEY_SUSHI_MODEL);
            if (sushi != null) {
                addItem(new CountableSushi(sushi));
                snackbar(sushi.getName() + "を追加しました");
            }
        }
    }

    private void addItem(CountableSushi sushiModel) {
        mNoteManager.getActiveNote().getSushis().add(sushiModel);
        mMainRecyclerView.getAdapter().notifyItemInserted(mNoteManager.getActiveNote().getSushis().size());
        mNoteManager.saveActiveNote();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_create_new_note:
                if (!mNoteManager.contains(mNoteManager.getActiveNote())) {
                    mNoteManager.add(mNoteManager.getActiveNote());
                    mNoteManager.saveNotesModel();
                }
                mNoteManager.setActiveNote(new Note());
                mNoteManager.saveActiveNote();
                updateMainAdapter();
                mNavigationRecyclerView.getAdapter().notifyDataSetChanged();
                break;
            case R.id.action_settings:
                snackbar("設定は開発中です");
                break;
            case R.id.action_note_edit:
                EditNoteActivity.startActivityWithNoteObjectAndRequestCode(this, mNoteManager.getActiveNote());
                break;
            case R.id.action_oaiso:
                ResultActivity.startActivityWithNoteObject(this, mNoteManager.getActiveNote());
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.main_floating_action_button)
    void add() {
        AddSushiActivity.startActivityWithSushiRequestCode(this);
    }
}