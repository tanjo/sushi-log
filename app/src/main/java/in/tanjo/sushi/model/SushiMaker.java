package in.tanjo.sushi.model;

public class SushiMaker extends Sushi {

    private boolean isAlreadyChangedPrice = false;

    public boolean canMake() {
        return getName().length() > 0 && isAlreadyChangedPrice;
    }

    @Override
    public void setPrice(int price) {
        super.setPrice(price);
        isAlreadyChangedPrice = true;
    }
}
