package com.example.josem.shoplistbesta;

/**
 * Created by josem on 17/07/2016.
 */
public class ShopListItem {

    public static String DEFAULT_QUANTITY_UNITS = "uds";
    private float quantity  = 1;
    private String quantityUnits = "ud";
    private String name = "";
    private String family = "";

    public ShopListItem() {

    }

    public void set(float quantity,String quantityUnits,String name){
        this.quantity = (Float.isNaN(quantity) || (quantity == 0)) ? 1 : quantity ;
        this.quantityUnits = quantityUnits;
        this.name = name;
    }

    public float getQuantity() {
        return quantity;
    }

    public void setQuantity(float quantity) {
        if(quantity == 0) this.quantity = 1;
        else this.quantity = quantity;
    }

    public void setQuantity(int quantity) {
        setQuantity((float)quantity);
    }

    public void setQuantity(String q){
        if (q.length()==0) this.quantity = 1;
        else this.quantity = Float.parseFloat(q);
    }

    public String getQuantityUnits() {
        return quantityUnits;
    }

    public void setQuantityUnits(String quantityUnits) {
        if(quantityUnits.length()==0) this.quantityUnits = DEFAULT_QUANTITY_UNITS;
        this.quantityUnits = quantityUnits;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFamily() {
        return family;
    }

    public void setFamily(String family) {
        this.family = family;
    }

    public ShopListItem(String name) {

    }

    public ShopListItem(String name, float quantity) {
        this.name = name;
        this.quantity = quantity;
    }


    public boolean stringReady(){
        return this.name.length() > 0;
    }

    public String toString(){
        return this.quantityFormatted() + " " + this.quantityUnitsFormatted() + " " + name;
    }

    public String quantityFormatted()
    {
        if(this.quantity == (long) this.quantity)
            return String.format("%d",(long)this.quantity);
            return String.format("%s",this.quantityFormatted());
    }

    public String quantityUnitsFormatted(){
        if(this.quantityUnits.length() == 0) return "uds";
        return this.quantityUnits;
    }

}
