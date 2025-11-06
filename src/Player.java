import java.util.*;

public class Player{
    public String name;
    public int hp= 30;
    public int en= 5;              // energía/recursos para habilidades
    public int gold= 0;
    public List<String> inventory= new ArrayList<>();
    public boolean hasTorch = false;

    public Player(String name) {
        this.name = name;
    }

    public boolean isAlive() {
        return hp > 0;
    }

    public void addGold(int amount){
        gold += amount;
        System.out.println("Obtienes "+ amount + "oro (Total:"+ gold +")");
    }

    public void addItem(String item){
        inventory.add(item);
        if ("Antorcha".equals(item)) hasTorch =true;
        System.out.println("Obtienes: " + item);
    }

    public void damage(int d){
        hp -= d;
        System.out.println("Recibes "+ d + "daño. HP restante: " + hp);
        if (hp <= 0) System.out.println("Has caído...");
    }

    public boolean skillCheck(int difficulty){
        int roll = Utils.rnd(1, 10);
        System.out.println("Tirada de habilidad: " + roll + " vs dificultad " + difficulty);
        return roll >= difficulty;
    }
}