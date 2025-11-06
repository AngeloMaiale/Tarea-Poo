public class Enemy {
    public String name;
    public int hp;
    public int atk;
    public String dialog;

    public Enemy(String name, int hp, int atk, String dialog){
        this.name= name;
        this.hp= hp;
        this.atk= atk;
        this.dialog= dialog;
    }

    public boolean isAlive() {
        return hp > 0;
    }
}

