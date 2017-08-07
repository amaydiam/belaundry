package com.akabetech.belaundryondemand.retrofit.model.beans;

/**
 * Created by akbar.pambudi on 8/21/2016.
 */
public class PerItemPrice extends BaseBean{
    private String unitClothesCode;
    private String unitClothesName;
    private Double unitClothesPrice;
    private String storeCode;

    public PerItemPrice() {
    }

    public PerItemPrice(String unitClothesCode, String unitClothesName, Double unitClothesPrice, String storeCode) {
        this.unitClothesCode = unitClothesCode;
        this.unitClothesName = unitClothesName;
        this.unitClothesPrice = unitClothesPrice;
        this.storeCode = storeCode;
    }

    public String getUnitClothesCode() {
        return unitClothesCode;
    }

    public void setUnitClothesCode(String unitClothesCode) {
        this.unitClothesCode = unitClothesCode;
    }

    public String getUnitClothesName() {
        return unitClothesName;
    }

    public void setUnitClothesName(String unitClothesName) {
        this.unitClothesName = unitClothesName;
    }

    public Double getUnitClothesPrice() {
        return unitClothesPrice;
    }

    public void setUnitClothesPrice(Double unitClothesPrice) {
        this.unitClothesPrice = unitClothesPrice;
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
        if (!(o instanceof PerItemPrice)) return false;

        PerItemPrice that = (PerItemPrice) o;

        if (getUnitClothesCode() != null ? !getUnitClothesCode().equals(that.getUnitClothesCode()) : that.getUnitClothesCode() != null)
            return false;
        if (getUnitClothesName() != null ? !getUnitClothesName().equals(that.getUnitClothesName()) : that.getUnitClothesName() != null)
            return false;
        if (getUnitClothesPrice() != null ? !getUnitClothesPrice().equals(that.getUnitClothesPrice()) : that.getUnitClothesPrice() != null)
            return false;
        return getStoreCode() != null ? getStoreCode().equals(that.getStoreCode()) : that.getStoreCode() == null;

    }

    @Override
    public int hashCode() {
        int result = getUnitClothesCode() != null ? getUnitClothesCode().hashCode() : 0;
        result = 31 * result + (getUnitClothesName() != null ? getUnitClothesName().hashCode() : 0);
        result = 31 * result + (getUnitClothesPrice() != null ? getUnitClothesPrice().hashCode() : 0);
        result = 31 * result + (getStoreCode() != null ? getStoreCode().hashCode() : 0);
        return result;
    }
}
