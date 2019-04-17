package np.com.naxa.factsnepal.userprofile;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserDetails implements Parcelable {

    @SerializedName("name")
    @Expose
    private String user_name;
    @SerializedName("email")
    @Expose
    private String user_email;
    @SerializedName("photo_url")
    @Expose
    private String photo_url;
    @SerializedName("ward")
    @Expose
    private String ward;
    @SerializedName("district")
    @Expose
    private String district;
    @SerializedName("province")
    @Expose
    private String province;
    @SerializedName("municipality")
    @Expose
    private String municipality;
    @SerializedName("street")
    @Expose
    private String street;
    @SerializedName("birth_year")
    @Expose
    private String birth_year;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("type")
    @Expose
    private String provoder;
    @SerializedName("token")
    @Expose
    private String token;

    @SerializedName("lat")
    @Expose
    private String latitude;
    @SerializedName("longitude")
    @Expose
    private String longitude;



    public UserDetails(String user_name, String user_email, String photo_url, String ward, String district, String province, String municipality,
                       String street, String birth_year, String gender, String provoder, String token, String latitude, String longitude) {
        this.user_name = user_name;
        this.user_email = user_email;
        this.photo_url = photo_url;
        this.ward = ward;
        this.district = district;
        this.province = province;
        this.municipality = municipality;
        this.street = street;
        this.birth_year = birth_year;
        this.gender = gender;
        this.provoder = provoder;
        this.token = token;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public UserDetails(String fbProfileImage, String toString, String toString1, String toString2, String toString3, String toString4, String toString5, String gender, String provider, String facebookToken, String s, String s1) {

    }

    public String getWard() {
        return ward;
    }

    public void setWard(String ward) {
        this.ward = ward;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getMunicipality() {
        return municipality;
    }

    public void setMunicipality(String municipality) {
        this.municipality = municipality;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getBirth_year() {
        return birth_year;
    }

    public void setBirth_year(String birth_year) {
        this.birth_year = birth_year;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getProvoder() {
        return provoder;
    }

    public void setProvoder(String provoder) {
        this.provoder = provoder;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getPhoto_url() {
        return photo_url;
    }

    public void setPhoto_url(String photo_url) {
        this.photo_url = photo_url;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.user_name);
        dest.writeString(this.user_email);
        dest.writeString(this.photo_url);
        dest.writeString(this.ward);
        dest.writeString(this.district);
        dest.writeString(this.province);
        dest.writeString(this.municipality);
        dest.writeString(this.street);
        dest.writeString(this.birth_year);
        dest.writeString(this.gender);
        dest.writeString(this.provoder);
        dest.writeString(this.token);
        dest.writeString(this.latitude);
        dest.writeString(this.longitude);
    }

    protected UserDetails(Parcel in) {
        this.user_name = in.readString();
        this.user_email = in.readString();
        this.photo_url = in.readString();
        this.ward = in.readString();
        this.district = in.readString();
        this.province = in.readString();
        this.municipality = in.readString();
        this.street = in.readString();
        this.birth_year = in.readString();
        this.gender = in.readString();
        this.provoder = in.readString();
        this.token = in.readString();
        this.latitude = in.readString();
        this.longitude = in.readString();
    }

    public static final Parcelable.Creator<UserDetails> CREATOR = new Parcelable.Creator<UserDetails>() {
        @Override
        public UserDetails createFromParcel(Parcel source) {
            return new UserDetails(source);
        }

        @Override
        public UserDetails[] newArray(int size) {
            return new UserDetails[size];
        }
    };

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    public static class Builder {
        @SerializedName("name")
        @Expose
        private String user_name;
        @SerializedName("email")
        @Expose
        private String user_email;
        @SerializedName("photo_url")
        @Expose
        private String photo_url;
        @SerializedName("ward")
        @Expose
        private String ward;
        @SerializedName("district")
        @Expose
        private String district;
        @SerializedName("province")
        @Expose
        private String province;
        @SerializedName("municipality")
        @Expose
        private String municipality;
        @SerializedName("street")
        @Expose
        private String street;
        @SerializedName("birth_year")
        @Expose
        private String birth_year;
        @SerializedName("gender")
        @Expose
        private String gender;
        @SerializedName("type")
        @Expose
        private String provoder;
        @SerializedName("token")
        @Expose
        private String token;

        @SerializedName("lat")
        @Expose
        private String latitude;
        @SerializedName("longitude")
        @Expose
        private String longitude;

        public Builder() {
        }

        public Builder setUser_name(String user_name) {
            this.user_name = user_name;
            return this;
        }

        public Builder setUser_email(String user_email) {
            this.user_email = user_email;
            return this;
        }

        public Builder setPhoto_url(String photo_url) {
            this.photo_url = photo_url;
            return this;
        }

        public Builder setWard(String ward) {
            this.ward = ward;
            return this;
        }

        public Builder setDistrict(String district) {
            this.district = district;
            return this;
        }

        public Builder setProvince(String province) {
            this.province = province;
            return this;
        }

        public Builder setMunicipality(String municipality) {
            this.municipality = municipality;
            return this;
        }

        public Builder setStreet(String street) {
            this.street = street;
            return this;
        }

        public Builder setBirth_year(String birth_year) {
            this.birth_year = birth_year;
            return this;
        }

        public Builder setGender(String gender) {
            this.gender = gender;
            return this;
        }

        public Builder setProvoder(String provoder) {
            this.provoder = provoder;
            return this;
        }

        public Builder setToken(String token) {
            this.token = token;
            return this;
        }

        public Builder setLatitude(String latitude) {
            this.latitude = latitude;
            return this;
        }

        public Builder setLongitude(String longitude) {
            this.longitude = longitude;
            return this;
        }


        public UserDetails build() {
            return new UserDetails(user_name, user_email, photo_url, ward, district, province, municipality, street, birth_year, gender, provoder, token, latitude, longitude);
        }
    }
}
