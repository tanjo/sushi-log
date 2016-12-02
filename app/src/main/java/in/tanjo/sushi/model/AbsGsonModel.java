package in.tanjo.sushi.model;

import com.google.gson.Gson;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

class AbsGsonModel implements Serializable {

    String optString(String string) {
        if (string == null) {
            return "";
        }
        return string;
    }

    protected <E> List<E> optList(List<E> list) {
        if (list == null) {
            return new ArrayList<>();
        }
        return list;
    }

    String toJson() {
        return new Gson().toJson(this, this.getClass());
    }
}
