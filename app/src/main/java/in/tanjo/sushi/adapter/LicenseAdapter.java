package in.tanjo.sushi.adapter;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Arrays;
import java.util.List;

import in.tanjo.sushi.R;
import in.tanjo.sushi.adapter.holder.LicenseViewHolder;
import in.tanjo.sushi.model.License;

import static in.tanjo.sushi.model.LicenseType.APACHE2;


public class LicenseAdapter extends RecyclerView.Adapter<LicenseViewHolder> implements LicenseViewHolder.Listener {

    private static final List<License> licenses = Arrays.asList(
            new License(APACHE2, "Butter Knife", "Jake Wharton", 2013, "http://jakewharton.github.io/butterknife/"),
            new License(APACHE2, "Gson", "Google Inc.", 2008, "https://github.com/google/gson"),
            new License(APACHE2, "Guava", "", null, "https://github.com/google/guava"),
            new License(APACHE2, "Phrase", "Square, Inc.", 2013, "https://github.com/square/phrase"),
            new License(APACHE2, "RxAndroid", "The RxAndroid authors", 2015, "https://github.com/ReactiveX/RxAndroid"),
            new License(APACHE2, "RxBinding", "Jake Wharton", 2015, "https://github.com/JakeWharton/RxBinding"),
            new License(APACHE2, "RxJava", "Netflix, Inc.", 2013, "https://github.com/ReactiveX/RxJava"),
            new License(APACHE2, "RxJavaMath", "Netflix, Inc.", 2012, "https://github.com/ReactiveX/RxJavaMath")
    );

    private Listener listener;

    public LicenseAdapter(Listener listener) {
        this.listener = listener;
    }

    @Override
    public LicenseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.license_view, parent, false);
        return new LicenseViewHolder(view, this);
    }

    @Override
    public void onBindViewHolder(LicenseViewHolder holder, int position) {
        holder.bind(licenses.get(position), position);
    }

    @Override
    public int getItemCount() {
        return licenses.size();
    }

    @Override
    public void onLicenseClick(int position) {
        License license;
        try {
            license = licenses.get(position);
        } catch (IndexOutOfBoundsException ignore) {
            license = null;
        }
        if (listener != null) {
            listener.onLicenseClick(license);
        }
    }

    public interface Listener {

        void onLicenseClick(@Nullable License license);
    }
}
