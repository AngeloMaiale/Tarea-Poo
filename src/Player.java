import java.util.*;

public class Player {
    public String name;
    public int hp = 30;
    public int maxHp = 30;
    public int en = 5;
    public int maxEn = 5;
    public int gold = 0;
    public int xp = 0;
    public int level = 1;
    private int xpToNext = 50;
    public Map<String, Integer> inventory = new LinkedHashMap<>();
    public boolean hasTorch = false;
    public boolean hasRelic = false;
    public boolean betrayed = false;
    public int tempAtkBonus = 0;
    public int tempAtkTurns = 0;
    public int bonusAtkPermanent = 0;
    // Cheats (persisten toda la partida)
    public boolean cheatInfiniteHealth = false;
    public boolean cheatOneHitKill = false;
    public boolean cheatSkipRooms = false;
    public Player(String name) {
        this.name = name;
    }
    public boolean isAlive() {
        return hp > 0;
    }
    public void addGold(int amount) {
        gold += amount;
        System.out.println("Obtienes " + amount + " oro (Total: " + gold + ")");
    }
    public void addItem(String item) {
        inventory.put(item, inventory.getOrDefault(item, 0) + 1);
        if ("Antorcha".equals(item) || "Antorcha Sagrada".equals(item)) hasTorch = true;
        System.out.println("Obtienes: " + item + " (x" + inventory.get(item) + ")");
    }
    public boolean removeItem(String item) {
        Integer c = inventory.get(item);
        if (c == null || c <= 0) return false;
        if (c == 1) inventory.remove(item);
        else inventory.put(item, c - 1);
        if ("Antorcha".equals(item) || "Antorcha Sagrada".equals(item)) {
            hasTorch = inventory.containsKey("Antorcha") || inventory.containsKey("Antorcha Sagrada");
        }
        return true;
    }
    public boolean hasItem(String item) {
        return inventory.containsKey(item) && inventory.get(item) > 0;
    }
    public void damage(int d) {
        if (cheatInfiniteHealth) {
            System.out.println("Recibes " + d + " daño. (Cheat activo: vida infinita — no pierdes HP)");
            System.out.println("HP actual: " + hp + "/" + maxHp);
            return;
        }
        hp -= d;
        System.out.println("Recibes " + d + " daño. HP restante: " + hp + "/" + maxHp);
        if (hp <= 0) System.out.println("Has caído...");
    }
    public boolean skillCheck(int difficulty) {
        int roll = Utils.rnd(1, 10);
        System.out.println("Tirada de habilidad: " + roll + " vs dificultad " + difficulty);
        return roll >= difficulty;
    }
    public void showStatus() {
        System.out.println("Nivel: " + level + " | XP: " + xp + "/" + xpToNext + " | HP: " + hp + "/" + maxHp + " | EN: " + en + "/" + maxEn + " | Oro: " + gold);
        if (tempAtkTurns > 0) System.out.println("Bonus de daño temporal: +" + tempAtkBonus + " por " + tempAtkTurns + " turnos");
        if (bonusAtkPermanent > 0) System.out.println("Bonus de ataque permanente: +" + bonusAtkPermanent);
        // mostrar cheats activos
        List<String> active = new ArrayList<>();
        if (cheatInfiniteHealth) active.add("Vida infinita");
        if (cheatOneHitKill) active.add("OneHitKill");
        if (cheatSkipRooms) active.add("Saltar salas");
        if (!active.isEmpty()) System.out.println("Cheats activos: " + String.join(", ", active));
    }
    public void showInventory() {
        if (inventory.isEmpty()) {
            System.out.println("Inventario vacío.");
            return;
        }
        System.out.print("Inventario: ");
        List<String> parts = new ArrayList<>();
        for (Map.Entry<String, Integer> e : inventory.entrySet()) {
            parts.add(e.getKey() + " x" + e.getValue());
        }
        System.out.println(String.join(", ", parts));
    }
    public boolean useItem(String item) {
        if (!hasItem(item)) {
            System.out.println("No tienes " + item + ".");
            return false;
        }

        switch (item) {
            case "Poción mayor":
                hp = Math.min(maxHp, hp + 12);
                removeItem(item);
                System.out.println("Usas Poción mayor. +12 HP (HP ahora: " + hp + "/" + maxHp + ")");
                return true;
            case "Poción menor":
                hp = Math.min(maxHp, hp + 6);
                removeItem(item);
                System.out.println("Usas Poción menor. +6 HP (HP ahora: " + hp + "/" + maxHp + ")");
                return true;
            case "Vendaje":
                hp = Math.min(maxHp, hp + 4);
                removeItem(item);
                System.out.println("Usas Vendaje. +4 HP (HP ahora: " + hp + "/" + maxHp + ")");
                return true;
            case "Gema curativa":
                hp = Math.min(maxHp, hp + 20);
                removeItem(item);
                System.out.println("Usas Gema curativa. +20 HP (HP ahora: " + hp + "/" + maxHp + ")");
                return true;
            case "Piedra luminosa":
                en = Math.min(maxEn, en + 2);
                removeItem(item);
                System.out.println("Usas Piedra luminosa. +2 EN (EN ahora: " + en + "/" + maxEn + ")");
                return true;
            case "Cápsula de vigor":
                en = Math.min(maxEn, en + 3);
                tempAtkBonus += 2;
                tempAtkTurns = Math.max(tempAtkTurns, 2);
                removeItem(item);
                System.out.println("Usas Cápsula de vigor. +3 EN y +2 daño por 2 turnos (EN ahora: " + en + "/" + maxEn + ")");
                return true;
            default:
                System.out.println("No puedes usar " + item + " aquí.");
                return false;
        }
    }
    public void tickEffects() {
        if (tempAtkTurns > 0) {
            tempAtkTurns--;
            if (tempAtkTurns == 0) {
                tempAtkBonus = 0;
                System.out.println("El efecto temporal de ataque ha terminado.");
            }
        }
    }
    public int getBaseAttackDamage() {
        int base = 4;
        return base + tempAtkBonus + bonusAtkPermanent;
    }
    public void addXp(int amount) {
        if (amount <= 0) return;
        xp += amount;
        System.out.println("Ganas " + amount + " XP (Total XP: " + xp + "/" + xpToNext + ")");
        checkLevelUp();
    }
    private void checkLevelUp() {
        while (xp >= xpToNext) {
            xp -= xpToNext;
            levelUp();
            xpToNext = (int)Math.round(xpToNext * 1.5);
        }
    }
    private void levelUp() {
        level++;
        int hpGain = 6 + Utils.rnd(0, 4);
        int enGain = 1 + Utils.rnd(0, 1);
        maxHp += hpGain;
        maxEn += enGain;
        hp = maxHp;
        en = Math.min(maxEn, en + enGain);
        if (level % 3 == 0) {
            bonusAtkPermanent += 1;
            System.out.println("¡Subes de nivel a " + level + "! +1 de ataque permanente.");
        } else {
            System.out.println("¡Subes de nivel a " + level + "!");
        }
        System.out.println("Máx HP +" + hpGain + " -> " + maxHp + " | Máx EN +" + enGain + " -> " + maxEn);
    }
}