package in.tanjo.sushi.model;

import com.google.common.base.Strings;
import com.google.gson.annotations.SerializedName;

import com.squareup.phrase.Phrase;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class License extends AbsGsonModel {

    @Nullable
    @SerializedName("name")
    private String name;

    @Nullable
    @SerializedName("copyright_holder")
    private String copyrightHodler;

    @NonNull
    @SerializedName("type")
    private LicenseType type;

    @Nullable
    @SerializedName("year")
    private String year;

    @Nullable
    @SerializedName("url")
    private String url;

    @Nullable
    @SerializedName("notice")
    private String notice;

    public License() {
        this(LicenseType.UNKNOWN, null, null, null, null, null);
    }

    public License(
            @NonNull LicenseType type,
            @Nullable String name,
            @Nullable String copyrightHodler,
            @Nullable String year,
            @Nullable String url,
            @Nullable String notice) {
        this.type = type;
        this.year = year;
        this.name = name;
        this.copyrightHodler = copyrightHodler;
        this.url = url;
        this.notice = notice;
    }

    public License(
            @NonNull LicenseType type,
            @Nullable String name,
            @Nullable String copyrightHodler,
            @Nullable String year,
            @Nullable String url) {
        this.type = type;
        this.year = year;
        this.name = name;
        this.copyrightHodler = copyrightHodler;
        this.url = url;
        this.notice = null;
    }

    public License(
            @NonNull LicenseType type,
            @Nullable String name,
            @Nullable String copyrightHodler,
            int year,
            @Nullable String url,
            @Nullable String notice) {
        this.type = type;
        this.year = String.valueOf(year);
        this.name = name;
        this.copyrightHodler = copyrightHodler;
        this.url = url;
        this.notice = notice;
    }

    public License(
            @NonNull LicenseType type,
            @Nullable String name,
            @Nullable String copyrightHodler,
            int year,
            @Nullable String url) {
        this.type = type;
        this.year = String.valueOf(year);
        this.name = name;
        this.copyrightHodler = copyrightHodler;
        this.url = url;
        this.notice = null;
    }

    @Nullable
    public String getName() {
        return optString(name);
    }

    public void setName(@Nullable String name) {
        this.name = name;
    }

    @Nullable
    public String getCopyrightHodler() {
        return optString(copyrightHodler);
    }

    public void setCopyrightHodler(@Nullable String copyrightHodler) {
        this.copyrightHodler = copyrightHodler;
    }

    @NonNull
    public LicenseType getType() {
        return type;
    }

    public void setType(@NonNull LicenseType type) {
        this.type = type;
    }

    @Nullable
    public String getUrl() {
        return optString(url);
    }

    public void setUrl(@Nullable String url) {
        this.url = url;
    }

    @Nullable
    public String getNotice() {
        return optString(notice);
    }

    public void setNotice(@Nullable String notice) {
        this.notice = notice;
    }

    @Nullable
    public String getYear() {
        return year;
    }

    public void setYear(@Nullable String year) {
        this.year = year;
    }

    @Nullable
    public String toCopyright() {
        if (Strings.isNullOrEmpty(year) && Strings.isNullOrEmpty(copyrightHodler)) {
            return "";
        }
        return Phrase.from("Copyright {year} {copyright_holder}")
                .put("year", Strings.nullToEmpty(year))
                .put("copyright_holder", Strings.nullToEmpty(copyrightHodler))
                .format()
                .toString();
    }

    @NonNull
    public String toLicense() {
        if (type == LicenseType.APACHE2) {
            return "Licensed under the Apache License, Version 2.0 (the \"License\");\n"
                    + "you may not use this file except in compliance with the License.\n"
                    + "You may obtain a copy of the License at\n"
                    + "\n"
                    + "   http://www.apache.org/licenses/LICENSE-2.0\n"
                    + "\n"
                    + "Unless required by applicable law or agreed to in writing, software\n"
                    + "distributed under the License is distributed on an \"AS IS\" BASIS,\n"
                    + "WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.\n"
                    + "See the License for the specific language governing permissions and\n"
                    + "limitations under the License.";
        }
        return "";
    }
}