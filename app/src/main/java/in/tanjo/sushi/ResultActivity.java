package in.tanjo.sushi;

import com.squareup.phrase.Phrase;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import in.tanjo.sushi.model.CountableSushi;
import in.tanjo.sushi.model.Note;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.observables.MathObservable;
import rx.schedulers.Schedulers;

public class ResultActivity extends AbsActivity {

    private static final String INTENT_NOTE_OBJECT = "key_intent_note_object";

    @BindView(R.id.result_activity_sum_view)
    TextView resultTextView;

    private Note note;

    static void startActivityWithNoteObject(Activity activity, Note note) {
        Intent intent = new Intent(activity, ResultActivity.class);
        intent.putExtra(INTENT_NOTE_OBJECT, note);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        catchNoteModel();
        initRx();
    }

    @Override
    public int getContentViewLayout() {
        return R.layout.activity_result;
    }

    @Override
    public int getToolbarId() {
        return R.id.result_activity_toolbar;
    }

    private void catchNoteModel() {
        Intent intent = getIntent();
        if (intent != null) {
            note = (Note) intent.getSerializableExtra(INTENT_NOTE_OBJECT);
        }
    }

    /**
     * Rx なものを初期化
     */
    private void initRx() {
        addSubscription(sum(note)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        resultTextView.setText(Phrase.from("{result}円").put("result", integer).format());
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        resultTextView.setText("");
                    }
                }));
    }

    /**
     * 合計を計算
     */
    private Observable<Integer> sum(Note note) {
        List<CountableSushi> sushi = note.getSushis();
        if (sushi == null || sushi.size() == 0) {
            return Observable.just(0);
        }
        return MathObservable
                .sumInteger(Observable.from(sushi).map(new Func1<CountableSushi, Integer>() {
                    @Override
                    public Integer call(CountableSushi countableSushiModel) {
                        return countableSushiModel.getPrice() * countableSushiModel.getCount();
                    }
                }))
                .subscribeOn(Schedulers.computation());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_sushi_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_sushi_input_complete) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
