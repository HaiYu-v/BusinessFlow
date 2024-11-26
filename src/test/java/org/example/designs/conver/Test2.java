package org.example.designs.conver;

/**
 * TODO
 *
 * <p>
 *     TODO
 * </p>
 *
 * @author HaiYu
 * @version 1.0.0
 * @date 2024-11-22
 */
public class Test2 {
    private int id;
    private String name1;
    private double price;

    public Test2(int id, String name1, double price) {
        this.id = id;
        this.name1 = name1;
        this.price = price;
    }

    public Test2( ) {
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName1() {
        return name1;
    }

    public void setName1(String name1) {
        this.name1 = name1;
    }


}
