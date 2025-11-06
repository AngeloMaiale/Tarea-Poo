import java.util.*;

public class Piso2 {

    private Player p;
    private Scanner in;
    private Set<String> restedRooms = new HashSet<>();

    public Piso2(Player p, Scanner in) {
        this.p = p;
        this.in = in;
    }
    public void play() {
        if (!preRoom("Sala 1")) room1();
        if (!p.isAlive()) return;

        if (!preRoom("Sala 2")) room2();
        if (!p.isAlive()) return;

        if (!preRoom("Sala 3")) room3();
        if (!p.isAlive()) return;

        if (!preRoom("Sala 4")) room4();
        if (!p.isAlive()) return;

        if (!preRoom("Sala 5")) room5();
    }
    private boolean preRoom(String roomName) {
        while (true) {
            System.out.println("\n— Zona de preparación: " + roomName + " —");
            System.out.println("Opciones: 1. Descansar (recuperar HP/EN una vez) | 2. Inventario | 3. Ver estado | 4. Continuar | 5. Cheats");
            System.out.println("Nota: solo puedes descansar 1 vez por sala.");
            String o = in.nextLine().trim();
            if ("1".equals(o)) {
                if (restedRooms.contains(roomName)) {
                    System.out.println("Ya descansaste antes de esta sala.");
                } else {
                    int heal = 6;
                    int restoreEn = 2;
                    p.hp = Math.min(p.maxHp, p.hp + heal);
                    p.en = Math.min(p.maxEn, p.en + restoreEn);
                    restedRooms.add(roomName);
                    System.out.println("Descansas brevemente. Recuperas " + heal + " HP y " + restoreEn + " EN.");
                    p.showStatus();
                }
            } else if ("2".equals(o)) {
                p.showInventory();
                System.out.println("Escribe el nombre exacto del objeto para usarlo, o ENTER para volver:");
                String item = in.nextLine().trim();
                if (!item.isEmpty()) p.useItem(item);
            } else if ("3".equals(o)) {
                p.showStatus();
            } else if ("5".equals(o)) {
                cheatsMenu();
            } else {
                if (p.cheatSkipRooms) {
                    System.out.println("Cheat 'Saltar salas' activo. ¿Deseas saltar esta sala? (S/N)");
                    String s = in.nextLine().trim().toUpperCase();
                    if ("S".equals(s) || "Y".equals(s)) {
                        System.out.println("Sala " + roomName + " saltada por cheat.");
                        return true;
                    }
                }
                return false;
            }
        }
    }
    private void cheatsMenu() {
        while (true) {
            System.out.println("\n--- Menú Cheats ---");
            System.out.println("Cheat 1: Vida infinita (actual: " + (p.cheatInfiniteHealth ? "ON" : "OFF") + ")");
            System.out.println("Cheat 2: Matar de un golpe (OneHitKill) (actual: " + (p.cheatOneHitKill ? "ON" : "OFF") + ")");
            System.out.println("Cheat 3: Saltar salas (permite 'saltar sala' en la preRoom) (actual: " + (p.cheatSkipRooms ? "ON" : "OFF") + ")");
            System.out.println("Opciones: A. Alternar 1 | B. Alternar 2 | C. Alternar 3 | X. Volver");
            String c = in.nextLine().trim().toUpperCase();
            if ("A".equals(c)) { p.cheatInfiniteHealth = !p.cheatInfiniteHealth; System.out.println("Vida infinita -> " + p.cheatInfiniteHealth); }
            else if ("B".equals(c)) { p.cheatOneHitKill = !p.cheatOneHitKill; System.out.println("OneHitKill -> " + p.cheatOneHitKill); }
            else if ("C".equals(c)) { p.cheatSkipRooms = !p.cheatSkipRooms; System.out.println("Saltar salas -> " + p.cheatSkipRooms); }
            else if ("X".equals(c)) break;
            else System.out.println("Opción inválida.");
        }
    }
    private void room1() {
        System.out.println("\nPiso 2 - Sala 1: Sala de Escarcha");
        System.out.println("Suelo resbaladizo y estalactitas de hielo colgantes.");
        System.out.println("Al cruzar, el suelo tiembla ligeramente.");
        System.out.println("Opciones: 1. Avanzar con cuidado | 2. Rodar/atajar | 3. Buscar alternativa | 4. Volver");

        String o = in.nextLine().trim();
        if ("1".equals(o)) {
            boolean ok = p.skillCheck(6);
            if (ok) {
                System.out.println("Avanzas con cuidado y evitas el peligro. Encuentras 5 monedas.");
                p.addGold(5);
                p.addXp(8);
                if (Utils.rnd(1, 10) <= 3) p.addItem("Piedra luminosa");
            } else {
                System.out.println("Fallaste la maniobra y caes: recibes 7 de daño.");
                p.damage(7);
            }
        } else if ("2".equals(o)) {
            boolean ok = p.skillCheck(5);
            if (ok) {
                System.out.println("Consigues un atajo y te adelantas: encuentras 3 monedas.");
                p.addGold(3);
                p.addXp(6);
            } else {
                System.out.println("El atajo falla y sufres daño por caída: 6 HP.");
                p.damage(6);
            }
        } else if ("3".equals(o)) {
            System.out.println("Buscas alternativa y encuentras paso seguro y una pista: 'El comercio mueve destinos.'");
        } else {
            System.out.println("Regresas pacíficamente a la sala anterior.");
        }
    }
    private void room2() {
        System.out.println("\nPiso 2 - Sala 2: Sala del Comerciante");
        System.out.println("Una figura envuelta en pieles junto a una carreta diminuta.");
        System.out.println("Comerciante: 'No todo se gana con acero, chica.'");

        while (true) {
            System.out.println("Opciones: 1. Ver inventario | 2. Vender | 3. Intercambiar fragmento por llave | 4. Rechazar");
            String o = in.nextLine().trim();
            if ("1".equals(o)) {
                System.out.println("Comerciante ofrece: Antorcha (10 oro); Poción mayor (25 oro); Llave escarchada (si tienes Fragmento)");
                System.out.println("Escribe 'comprar Antorcha' o 'comprar Poción mayor' o ENTER para volver:");
                String buy = in.nextLine().trim();
                if (buy.startsWith("comprar")) {
                    String item = buy.replaceFirst("comprar", "").trim();
                    if ("Antorcha".equalsIgnoreCase(item) && p.gold >= 10) {
                        p.addGold(-10);
                        p.addItem("Antorcha");
                    } else if ("Poción mayor".equalsIgnoreCase(item) && p.gold >= 25) {
                        p.addGold(-25);
                        p.addItem("Poción mayor");
                    } else System.out.println("No puedes comprar eso ahora.");
                }
            } else if ("2".equals(o)) {
                if (p.gold > 0) {
                    System.out.println("Vendes objetos menores por 5 oro.");
                    p.addGold(5);
                } else {
                    System.out.println("No tienes oro para comerciar.");
                }
            } else if ("3".equals(o)) {
                if (p.hasItem("Fragmento de Historia")) {
                    System.out.println("Intercambias Fragmento de Historia por una Llave Escarchada.");
                    p.removeItem("Fragmento de Historia");
                    p.addItem("Llave Escarchada");
                    p.addXp(10);
                } else {
                    System.out.println("No tienes Fragmento de Historia.");
                }
            } else {
                System.out.println("El comerciante te mira y asiente. Sigues tu camino.");
                break;
            }
        }
    }

    private void room3() {
        System.out.println("\nPiso 2 - Sala 3: Sala de Emboscada");
        System.out.println("Sombras entre columnas: se oyen gruñidos.");
        System.out.println("Tres Lobos de Torre emergen.");
        System.out.println("Opciones: 1. Luchar | 2. Usar habilidad de área | 3. Defender | 4. Huir");

        String o = in.nextLine().trim();
        if ("4".equals(o)) {
            boolean ok = p.skillCheck(8);
            if (ok) {
                System.out.println("Huyes con éxito, pero pierdes 2 monedas en la prisa.");
                p.addGold(-2);
                return;
            } else {
                System.out.println("Intentas huir y fallas: los lobos atacan.");
            }
        }

        Enemy l1 = new Enemy("Lobo de Torre", 8, 2, 4, 80, "Grrr");
        Enemy l2 = new Enemy("Lobo de Torre", 8, 2, 4, 80, "Grrr");
        Enemy l3 = new Enemy("Lobo de Torre", 8, 2, 4, 80, "Grrr");

        boolean won = Combat.fight(p, Arrays.asList(l1, l2, l3), in);
        if (won) {
            System.out.println("Tras el encuentro recoges cuero y oro.");
            p.addGold(Utils.rnd(15, 20));
            p.addItem("Cuero");
            p.addXp(10);
            if (Utils.rnd(1, 10) <= 2) p.addItem("Cápsula de vigor");
        }
    }

    private void room4() {
        System.out.println("\nPiso 2 - Sala 4: Sala del Enigma");
        System.out.println("Un mural con escenas fragmentadas de la historia de la Reliquia.");
        System.out.println("Tres escenas; una falta la última pieza.");
        System.out.println("Opciones: 1. Ordenar escenas 2. Usar pista 3. Inspeccionar piezas 4. Salir");

        String o = in.nextLine().trim();
        if ("1".equals(o)) {
            System.out.println("Introduce la secuencia (ej: 1-2-3):");
            String seq = in.nextLine().trim();
            if ("2-1-3".equals(seq) || "2,1,3".equals(seq)) {
                System.out.println("Orden correcto. Obtienes una pista crítica sobre el jefe final.");
                p.addItem("Pista jefe");
                p.addXp(18);
            } else {
                System.out.println("Secuencia incorrecta. Pierdes 2 EN por fatiga mental.");
                p.en = Math.max(0, p.en - 2);
            }
        } else if ("2".equals(o)) {
            System.out.println("Usas una pista: recuerdas imágenes de luz y fuego que dañan la oscuridad.");
        } else if ("3".equals(o)) {
            System.out.println("Inspeccionas las piezas y encuentras una fichita metálica: '3'.");
            p.addItem("Fichita 3");
        } else {
            System.out.println("Sales del enigma sin cambios.");
        }
    }

    private void room5() {
        System.out.println("\nPiso 2 - Sala 5: Sala del Guardián");
        System.out.println("Un guerrero espectral con ojos de hielo se yergue.");
        System.out.println("Guardián: 'Quien busque la Reliquia debe superar la prueba.'");
        System.out.println("Opciones: 1. Combate | 2. Usar antorcha | 3. Intentar diálogo | 4. Huir (imposible)");

        String o = in.nextLine().trim();
        if ("3".equals(o)) {
            boolean ok = p.skillCheck(6);
            if (ok) {
                System.out.println("El diálogo calma parcialmente al guardián. Te permite avanzar pero no te brinda la llave.");
                p.addGold(5);
                p.addXp(10);
                return;
            } else {
                System.out.println("El guardián no cede. Comienza el combate.");
            }
        } else if ("2".equals(o)) {
            if (p.hasTorch) {
                System.out.println("Enciendes la antorcha: reduces regeneración del guardián durante el combate.");
            } else {
                System.out.println("No tienes antorcha.");
            }
        }

        Enemy boss = new Enemy("Guardián Espectral", 40, 4, 7, 80, "No te llevarás lo que no es tuyo.");
        boolean playerTurn = true;

        while (p.isAlive() && boss.isAlive()) {
            if (playerTurn) {
                System.out.println("\nTu turno. Opciones: 1. Atacar 2. Usar antorcha 3. Habilidad 4. Inventario");
                String act = in.nextLine().trim();
                if ("2".equals(act) && p.hasTorch) {
                    System.out.println("La luz frena la regeneración del guardián. Haces 8 daño.");
                    if (p.cheatOneHitKill) { boss.hp = 0; System.out.println("Cheat activo: enemigo vencido de un golpe."); }
                    else boss.hp -= 8;
                } else if ("3".equals(act) && p.en > 0) {
                    p.en--;
                    System.out.println("Usas habilidad: 12 daño.");
                    if (p.cheatOneHitKill) { boss.hp = 0; System.out.println("Cheat activo: enemigo vencido de un golpe."); }
                    else boss.hp -= 12;
                } else if ("4".equals(act)) {
                    p.showInventory();
                    System.out.println("Nombre del objeto para usar o ENTER para cancelar:");
                    String item = in.nextLine().trim();
                    if (!item.isEmpty()) p.useItem(item);
                } else {
                    if (p.cheatOneHitKill) { boss.hp = 0; System.out.println("Atacas y (cheat) eliminas al guardián de un golpe."); }
                    else {
                        int dmg = p.getBaseAttackDamage();
                        System.out.println("Atacas por " + dmg + " daño");
                        boss.hp -= dmg;
                    }
                }
            } else {
                if (Utils.rnd(1, 100) <= 30 && !p.hasTorch) {
                    System.out.println("El guardián intenta regenerar en lugar de atacar.");
                    boss.hp += 5;
                } else {
                    boss.attack(p);
                }
            }
            playerTurn = !playerTurn;
            p.tickEffects();
        }

        if (!p.isAlive()) return;
        if (!boss.isAlive()) {
            System.out.println("Has vencido al Guardián intermedio.");
            p.addGold(Utils.rnd(30, 40));
            if (!p.hasItem("Llave Escarchada")) p.addItem("Llave Escarchada");
            p.addXp(80);
            return;
        }
    }
}
