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
    private String name;
    private double price;

    public Test2(int id, String name, double price) {
        this.id = id;
        this.name = name;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}