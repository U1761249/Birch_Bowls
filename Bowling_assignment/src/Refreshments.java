abstract class Refreshments {

    //Abstract as all refreshments are either of type food or drink.

    private String productName;
    private String ProductPrice;


    public String getProductName() { return productName; }

    public String getProductPrice() { return ProductPrice; }

    public Refreshments(String productName, String productPrice) {
        super();
        this.productName = productName;
        ProductPrice = productPrice;
    }

    public Refreshments() {}
}
