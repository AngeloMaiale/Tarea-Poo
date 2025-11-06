import java.util.*;

public class Combat
{
    // combate simple: jugador ataca primero, daño fijo por simplicidad
    public static boolean fight(Player p, List<Enemy> enemies, Scanner in){
        System.out.println("Comienza el combate!");
        for(Enemy e : enemies){
            System.out.println(e.name + ": \"" + e.dialog + "\"");
        }
        while(p.isAlive() && enemies.stream().anyMatch(Enemy::isAlive)){
            System.out.println("\n  Tu turno. Opciones: 1. Atacar 2. Usar habilidad(consume 1 EN) 3. Inventario");
            String opt = in.nextLine().trim();
            if ("2".equals(opt)) {
                if (p.en > 0) {
                    p.en--;
                    System.out.println("Usas una habilidad y haces 6 daño al primer enemigo vivo");
                    Enemy target = enemies.stream().filter(Enemy::isAlive).findFirst().get();
                    target.hp -= 6;
                } else System.out.println("No tienes EN.");
            } else if ("3".equals(opt)) {
                System.out.println("Inventario: " + p.inventory);
            } else {
                Enemy target = enemies.stream().filter(Enemy::isAlive).findFirst().get();
                System.out.println("Atacas a "+ target.name + "por 4 daño");
                target.hp -= 4;
            }


            // enemigos atacan
            for (Enemy e : enemies) {
                if (e.isAlive()) {
                    System.out.println(e.name + "ataca por "+ e.atk + "daño");
                    p.damage(e.atk);
                    if (!p.isAlive()) break;
                }
            }
        }
        boolean won = p.isAlive();
        if (won) System.out.println("Has vencido el encuentro");
        else System.out.println("Derrota en combate");
        return won;
    }
}

