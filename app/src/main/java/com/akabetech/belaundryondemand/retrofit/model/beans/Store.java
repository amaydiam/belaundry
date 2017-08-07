package com.akabetech.belaundryondemand.retrofit.model.beans;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by akbar.pambudi on 8/15/2016.
 */
public class Store extends BaseBean implements Parcelable {
    @SerializedName("storeCode")
    private String code;
   @SerializedName("storeOwnerCode")
    private String storeOwnerCode;
    @SerializedName("storeName")
    private String name;
    @SerializedName("storeAddress")
    private String address;
    @SerializedName("storeLat")
    private Double latitude;
    @SerializedName("storeLong")
    private Double longitude;
    @SerializedName("about_us")
    private String aboutUs;
    private String postalCode;
    @SerializedName("pick_up_terms")
    private String termAndCondition;
    @SerializedName("is_pick_up")
    private int isPicked;
    private String logo;

    private Bitmap logoBitmap;

    public Store(Parcel in) {
        code = in.readString();
        storeOwnerCode = in.readString();
        name = in.readString();
        address = in.readString();
        aboutUs = in.readString();
        postalCode = in.readString();
        termAndCondition = in.readString();
        isPicked = in.readInt();
        logo = in.readString();
        logoBitmap = in.readParcelable(Bitmap.class.getClassLoader());
    }

    public static final Creator<Store> CREATOR = new Creator<Store>() {
        @Override
        public Store createFromParcel(Parcel in) {
            return new Store(in);
        }

        @Override
        public Store[] newArray(int size) {
            return new Store[size];
        }
    };

    public Store() {

    }

    public Bitmap getLogoBitmap() {
        return logoBitmap;
    }

    public void setLogoBitmap(Bitmap logoBitmap) {
        this.logoBitmap = logoBitmap;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getStoreOwnerCode() {
        return storeOwnerCode;
    }

    public void setStoreOwnerCode(String storeOwnerCode) {
        this.storeOwnerCode = storeOwnerCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getAboutUs() {
        return aboutUs;
    }

    public void setAboutUs(String aboutUs) {
        this.aboutUs = aboutUs;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getTermAndCondition() {
        return termAndCondition;
    }

    public void setTermAndCondition(String termAndCondition) {
        this.termAndCondition = termAndCondition;
    }

    public int getIsPicked() {
        return isPicked;
    }

    public void setIsPicked(int isPicked) {
        this.isPicked = isPicked;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (!(o instanceof Store)) return false;

        Store store = (Store) o;

        if (getIsPicked() != store.getIsPicked()) return false;
        if (getCode() != null ? !getCode().equals(store.getCode()) : store.getCode() != null)
            return false;
        if (getStoreOwnerCode() != null ? !getStoreOwnerCode().equals(store.getStoreOwnerCode()) : store.getStoreOwnerCode() != null)
            return false;
        if (getName() != null ? !getName().equals(store.getName()) : store.getName() != null)
            return false;
        if (getAddress() != null ? !getAddress().equals(store.getAddress()) : store.getAddress() != null)
            return false;
        if (getLatitude() != null ? !getLatitude().equals(store.getLatitude()) : store.getLatitude() != null)
            return false;
        if (getLongitude() != null ? !getLongitude().equals(store.getLongitude()) : store.getLongitude() != null)
            return false;
        if (getAboutUs() != null ? !getAboutUs().equals(store.getAboutUs()) : store.getAboutUs() != null)
            return false;
        if (getPostalCode() != null ? !getPostalCode().equals(store.getPostalCode()) : store.getPostalCode() != null)
            return false;
        return getTermAndCondition() != null ? getTermAndCondition().equals(store.getTermAndCondition()) : store.getTermAndCondition() == null;

    }

    @Override
    public int hashCode() {
        int result = getCode() != null ? getCode().hashCode() : 0;
        result = 31 * result + (getStoreOwnerCode() != null ? getStoreOwnerCode().hashCode() : 0);
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        result = 31 * result + (getAddress() != null ? getAddress().hashCode() : 0);
        result = 31 * result + (getLatitude() != null ? getLatitude().hashCode() : 0);
        result = 31 * result + (getLongitude() != null ? getLongitude().hashCode() : 0);
        result = 31 * result + (getAboutUs() != null ? getAboutUs().hashCode() : 0);
        result = 31 * result + (getPostalCode() != null ? getPostalCode().hashCode() : 0);
        result = 31 * result + (getTermAndCondition() != null ? getTermAndCondition().hashCode() : 0);
        result = 31 * result + getIsPicked();
        return result;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(code);
        parcel.writeString(storeOwnerCode);
        parcel.writeString(name);
        parcel.writeString(address);
        parcel.writeString(aboutUs);
        parcel.writeString(postalCode);
        parcel.writeString(termAndCondition);
        parcel.writeInt(isPicked);
        parcel.writeString(logo);
        parcel.writeParcelable(logoBitmap, i);
    }
}
