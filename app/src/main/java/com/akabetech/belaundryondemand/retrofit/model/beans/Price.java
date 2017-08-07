package com.akabetech.belaundryondemand.retrofit.model.beans;

/**
 * Created by akbar.pambudi on 8/21/2016.
 */
public class Price extends BaseBean {
    private String packageCode;
    private String packageName;
    private String storeCode;

    public Price() {
    }

    public Price(String packageCode, String packageName, String storeCode) {
        this.packageCode = packageCode;
        this.packageName = packageName;
        this.storeCode = storeCode;
    }

    public String getPackageCode() {
        return packageCode;
    }

    public void setPackageCode(String packageCode) {
        this.packageCode = packageCode;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getStoreCode() {
        return storeCode;
    }

    public void setStoreCode(String storeCode) {
        this.storeCode = storeCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Price)) return false;

        Price price = (Price) o;

        if (getPackageCode() != null ? !getPackageCode().equals(price.getPackageCode()) : price.getPackageCode() != null)
            return false;
        if (getPackageName() != null ? !getPackageName().equals(price.getPackageName()) : price.getPackageName() != null)
            return false;
        return getStoreCode() != null ? getStoreCode().equals(price.getStoreCode()) : price.getStoreCode() == null;

    }

    @Override
    public int hashCode() {
        int result = getPackageCode() != null ? getPackageCode().hashCode() : 0;
        result = 31 * result + (getPackageName() != null ? getPackageName().hashCode() : 0);
        result = 31 * result + (getStoreCode() != null ? getStoreCode().hashCode() : 0);
        return result;
    }
}
