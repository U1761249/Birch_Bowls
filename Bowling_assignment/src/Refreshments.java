abstract class Refreshments {

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
