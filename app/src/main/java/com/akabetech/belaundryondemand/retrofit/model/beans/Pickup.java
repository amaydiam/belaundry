package com.akabetech.belaundryondemand.retrofit.model.beans;

import com.google.gson.annotations.SerializedName;

/**
 * Created by akbar.pambudi on 8/19/2016.
 */
public class Pickup extends BaseBean {
    @SerializedName("id")
    private int id;
    @SerializedName("idTrans")
    private int transId;
    @SerializedName("id_user_beasli")
    private int userId;
    @SerializedName("storeCode")
    private String storeCode;
    @SerializedName("phone_number")
    private String phone;
    @SerializedName("alamat_pickup")
    private String address;
    @SerializedName("tgl_pickup")
    private String pickupDate;
    @SerializedName("time_pickup")
    private String timePickup;
    @SerializedName("note_pickup")
    private String notePickup;
    @SerializedName("status_pickup")
    private String pickupStatus ="0";
@SerializedName("long")
    private double longitude;
    @SerializedName("lat")
    private double latitude;

    public String getTimePickup() {
        return timePickup;
    }

    public void setTimePickup(String timePickup) {
        this.timePickup = timePickup;
    }

    public String getNotePickup() {
        return notePickup;
    }

    public void setNotePickup(String notePickup) {
        this.notePickup = notePickup;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTransId() {
        return transId;
    }

    public void setTransId(int transId) {
        this.transId = transId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }


    public String getPickupDate() {
        return pickupDate;
    }

    public void setPickupDate(String pickupDate) {
        this.pickupDate = pickupDate;
    }

    public String getStoreCode() {
        return storeCode;
    }

    public void setStoreCode(String storeCode) {
        this.storeCode = storeCode;
    }


    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPickupStatus() {
        return pickupStatus;
    }

    public void setPickupStatus(String pickupStatus) {
        this.pickupStatus = pickupStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Pickup)) return false;

        Pickup pickup = (Pickup) o;

        if (getId() != pickup.getId()) return false;
        if (getTransId() != pickup.getTransId()) return false;
        if (getUserId() != pickup.getUserId()) return false;
        if (getStoreCode() != null ? !getStoreCode().equals(pickup.getStoreCode()) : pickup.getStoreCode() != null)
            return false;
        if (getPhone() != null ? !getPhone().equals(pickup.getPhone()) : pickup.getPhone() != null)
            return false;
        if (getAddress() != null ? !getAddress().equals(pickup.getAddress()) : pickup.getAddress() != null)
            return false;
        if (getPickupDate() != null ? !getPickupDate().equals(pickup.getPickupDate()) : pickup.getPickupDate() != null)
            return false;
        return getPickupStatus() != null ? getPickupStatus().equals(pickup.getPickupStatus()) : pickup.getPickupStatus() == null;

    }

    @Override
    public int hashCode() {
        int result = getId();
        result = 31 * result + getTransId();
        result = 31 * result + getUserId();
        result = 31 * result + (getStoreCode() != null ? getStoreCode().hashCode() : 0);
        result = 31 * result + (getPhone() != null ? getPhone().hashCode() : 0);
        result = 31 * result + (getAddress() != null ? getAddress().hashCode() : 0);
        result = 31 * result + (getPickupDate() != null ? getPickupDate().hashCode() : 0);
        result = 31 * result + (getPickupStatus() != null ? getPickupStatus().hashCode() : 0);
        return result;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
}
