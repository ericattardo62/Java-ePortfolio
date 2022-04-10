package ePortfolio;

/* */
public class Investment{

    private String symbol;
    private String name;
    private int quantity;
    private double price;
    private double bookValue;

    /* */
    public Investment(String investSymbol, String investName, int investQuantity, double investPrice, double investBookValue) {
        setSymbol(investSymbol);
        setName(investName);
        setQuantity(investQuantity);
        setPrice(investPrice);
        setBookValue(investBookValue);
    }
    /* */
    public void setSymbol(String newSymbol){
        symbol = newSymbol;
    }
    /* */
    public String getSymbol(){
        return symbol;
    }
    /* */
    public void setName(String newName){
        name = newName;
    }
    /* */
    public String getName(){
        return name;
    }
    /* */
    public void setQuantity(int newQuantity){
        quantity = newQuantity;
    }
    /* */
    public int getQuantity(){
        return quantity;
    }
    /* */
    public void setPrice(double newPrice){
        price = newPrice;
    }
    /* */
    public double getPrice(){
        return price;
    }
    /* */
    public void setBookValue(double newBookValue){
        bookValue = newBookValue;
    }
    /* */
    public double getBookValue(){
        return bookValue;
    }

}