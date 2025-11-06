import java.util.*;

public class Combat {

    public static boolean fight(Player p, List<Enemy> enemies, Scanner in) {
        System.out.println("Comienza el combate!");
        for (Enemy e : enemies) {
            System.out.println(e.name + ": \"" + e.dialog + "\"  -- " + e.attackPreview());
        }

        while (p.isAlive() && enemies.stream().anyMatch(Enemy::isAlive)) {
            System.out.println();
            p.showStatus();
            System.out.println("Tu turno. Opciones: 1. Atacar | 2. Usar habilidad (consume 1 EN) | 3. Inventario | 4. Huir (riesgoso)");
            String opt = in.nextLine().trim();

            if ("2".equals(opt)) {
                if (p.en > 0) {
                    p.en--;
                    System.out.println("Usas una habilidad y haces 6 daño al primer enemigo vivo");
                    Enemy target = enemies.stream().filter(Enemy::isAlive).findFirst().get();
                    if (p.cheatOneHitKill) {
                        target.hp = 0;
                        System.out.println("Cheat activo: enemigo vencido de un golpe.");
                    } else {
                        target.hp -= 6;
                    }
                } else System.out.println("No tienes EN.");
            } else if ("3".equals(opt)) {
                p.showInventory();
                System.out.println("Escribe el nombre exacto del objeto para usarlo, o ENTER para cancelar:");
                String item = in.nextLine().trim();
                if (!item.isEmpty()) {
                    boolean used = p.useItem(item);
                    if (!used) System.out.println("No se usó ningún objeto.");
                }
            } else if ("4".equals(opt)) {
                boolean ok = p.skillCheck(8);
                if (ok) {
                    System.out.println("Huyes con éxito del encuentro.");
                    return false;
                } else {
                    System.out.println("Intentas huir y fallas: recibes un golpe mientras retrocedes.");
                    p.damage(2);
                }
            } else {
                Enemy target = enemies.stream().filter(Enemy::isAlive).findFirst().get();
                if (p.cheatOneHitKill) {
                    target.hp = 0;
                    System.out.println("Atacas a " + target.name + " y (cheat) lo eliminas de un golpe.");
                } else {
                    int dmg = p.getBaseAttackDamage();
                    System.out.println("Atacas a " + target.name + " por " + dmg + " daño");
                    target.hp -= dmg;
                }
            }

            for (Enemy e : enemies) {
                if (e.isAlive()) {
                    e.attack(p);
                    if (!p.isAlive()) break;
                }
            }

            p.tickEffects();
        }

        boolean won = p.isAlive() && enemies.stream().noneMatch(Enemy::isAlive);
        if (won) {
            System.out.println("Has vencido el encuentro");
            int totalXp = 0;
            for (Enemy e : enemies) {
                if (!e.isAlive()) {
                    int xpGain = 15;
                    String n = e.name.toLowerCase();
                    if (n.contains("señor") || n.contains("guardia") || n.contains("traidor")) xpGain = 50;
                    else if (n.contains("lobo")) xpGain = 20;
                    totalXp += xpGain;
                }
            }
            if (totalXp > 0) p.addXp(totalXp);
        } else if (!p.isAlive()) {
            System.out.println("Derrota en combate");
        } else {
            System.out.println("Escapaste del combate (sin victoria)");
        }

        return won;
    }
}