public class Enemy {

    public String name;
    public int hp;
    public int minAtk;
    public int maxAtk;
    public int accuracy; // 0-100
    public String dialog;

    public Enemy(String name, int hp, int minAtk, int maxAtk, int accuracy, String dialog) {
        this.name = name;
        this.hp = hp;
        this.minAtk = minAtk;
        this.maxAtk = maxAtk;
        this.accuracy = Math.max(0, Math.min(100, accuracy));
        this.dialog = dialog;
    }

    public boolean isAlive() {
        return hp > 0;
    }

    public boolean attack(Player p) {
        int roll = Utils.rnd(1, 100);
        if (roll > accuracy) {
            System.out.println(name + " intenta atacar pero falla.");
            return false;
        }
        int dmg = Utils.rnd(minAtk, maxAtk);
        System.out.println(name + " ataca y hace " + dmg + " daño");
        p.damage(dmg);
        return true;
    }

    public String attackPreview() {
        return name + " puede hacer entre " + minAtk + " y " + maxAtk + " daño (AC: " + accuracy + "%)";
    }
}
