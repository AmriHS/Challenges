import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

interface Item{
    void accept(Visitor visitor);
}

class Apple implements Item {
    public void accept(Visitor visitor){
        visitor.addToCart(this);
    }
    public final BigDecimal getPrice(){
        return new BigDecimal(0.6);
    }
}

class Orange implements Item{
    public void accept(Visitor visitor){
        visitor.addToCart(this);
    }
    public final BigDecimal getPrice(){
        return new BigDecimal(0.25);
    }
}

interface Visitor{
    void addToCart(Orange o);
    void addToCart(Apple a);
    BigDecimal checkout();
    BigDecimal checkoutWithDiscount();
}
class ItemVisitor implements Visitor {
    List<Orange> oranges;
    List<Apple> apples;

    public ItemVisitor(){
        oranges = new ArrayList();
        apples = new ArrayList();
    }

    public void addToCart(Apple a){
        apples.add(a);
    }

    public void addToCart(Orange o){
        oranges.add(o);
    }

    public BigDecimal checkout(){
        BigDecimal cost = new BigDecimal(0);
        for(Apple a : apples)
            cost = cost.add(a.getPrice());
        for(Orange o : oranges)
            cost = cost.add(o.getPrice());
        cost = cost.setScale(2, RoundingMode.CEILING);
        return cost;
    }

    public BigDecimal checkoutWithDiscount(){
        BigDecimal cost = new BigDecimal(0);
        for(int i=0; i<apples.size(); i+=2)
            cost = cost.add(apples.get(i).getPrice());

        for(int i=0; i<oranges.size(); i++) {
            if ((i + 1) % 3 == 0) continue;
            cost = cost.add(oranges.get(i).getPrice());
        }

        cost = cost.setScale(2, RoundingMode.CEILING);
        return cost;
    }
}

public class Exercise {
    public static void main (String [] args){
        int orangeCount = 0;
        int appleCount = 0;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter number of orange: ");
        orangeCount = scanner.nextInt();
        System.out.println("Enter number of apples: ");
        appleCount = scanner.nextInt();
        Visitor visitor = new ItemVisitor();
        for (int i=0; i<orangeCount; i++){
            Item item = new Orange();
            item.accept(visitor);
        }
        for (int i=0; i<appleCount; i++){
            Item item = new Apple();
            item.accept(visitor);
        }
        System.out.println("Total cost of your basket: £"+visitor.checkout());
        System.out.println("Total cost of your basket with discount: £"+visitor.checkoutWithDiscount());

    }
}
