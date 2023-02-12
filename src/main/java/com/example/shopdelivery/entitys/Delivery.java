package com.example.shopdelivery.entitys;

import java.util.ArrayList;

public class Delivery implements Comparable<Delivery>{
     public String id;
   public ArrayList<String> orderList;

    @Override
    public String toString() {
        return "Номер доставки = " + getId() + "\n" +
                "Список заказов = " + getOrderList().toString() + "\n";
    }

    public Delivery(String id, ArrayList<String> orderList) {
       setId(id);
       setOrderList(orderList);
    }

    public String getId() {
        return id;
    }

    public ArrayList<String> getOrderList() {
        return orderList;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setOrderList(ArrayList<String> orderList) {
        this.orderList = orderList;
    }

    @Override
    public int compareTo(Delivery o) {
        if (Integer.parseInt(this.id.substring(1,this.id.length()-1)) >=
                Integer.parseInt(o.id.substring(1,o.id.length()-1))) {
            return 1;
        }
        return 0;
    }
}
