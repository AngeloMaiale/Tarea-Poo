import java.util.*;

public class Piso1 {
    private Player p;
    private Scanner in;
    private Set<String> restedRooms = new HashSet<>();
    public Piso1(Player p, Scanner in) {
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
            System.out.println("Opciones: 1. Descansar (recuperar HP/EN una vez) 2. Inventario 3. Ver estado 4. Continuar 5. Cheats");
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
            System.out.println("Opciones: A. Alternar 1  B. Alternar 2  C. Alternar 3  X. Volver");
            String c = in.nextLine().trim().toUpperCase();
            if ("A".equals(c)) { p.cheatInfiniteHealth = !p.cheatInfiniteHealth; System.out.println("Vida infinita -> " + p.cheatInfiniteHealth); }
            else if ("B".equals(c)) { p.cheatOneHitKill = !p.cheatOneHitKill; System.out.println("OneHitKill -> " + p.cheatOneHitKill); }
            else if ("C".equals(c)) { p.cheatSkipRooms = !p.cheatSkipRooms; System.out.println("Saltar salas -> " + p.cheatSkipRooms); }
            else if ("X".equals(c)) break;
            else System.out.println("Opción inválida.");
        }
    }

    private void room1() {
        System.out.println("\n Sala 1: Sala de Patrulla ");
        System.out.println("Un pasillo helado, antorchas apagadas. Voz ronca: pasos acercándose");
        System.out.println("Texto:\" Dos Vigías Exhaustos patrullan la sala \"");
        System.out.println("Opciones: 1. Atacar / 2. Intentar sigilo / 3. Usar habilidad / 4. Inventario");

        String opt = in.nextLine().trim();
        if ("2".equals(opt)) {
            boolean success = p.skillCheck(6);
            if (success) {
                System.out.println("Sigilo exitoso. Evitas combate y encuentras 3 monedas");
                p.addGold(3);
                p.addXp(10);
                return;
            } else {
                System.out.println("Sigilo fallido. Los vigías te detectan");
            }
        }

        Enemy e1 = new Enemy("Vigía Exhausto", 6, 1, 3, 85, "¡Alto ahí!");
        Enemy e2 = new Enemy("Vigía Exhausto", 6, 1, 3, 85, "¡Alto ahí!");

        boolean won = Combat.fight(p, Arrays.asList(e1, e2), in);
        if (won) {
            int oro = Utils.rnd(5, 8);
            p.addGold(oro);
            p.addItem("Parche de tela");
            p.addXp(12);
            System.out.println("Pista obtenida:\" Runas arriba guardan puertas \"");
            if (Utils.rnd(1, 10) <= 3) p.addItem("Vendaje");
        }
    }
    private void room2() {
        System.out.println("\n Sala 2: Sala del Mensajero");
        System.out.println("Un mensajero sangrando se apoya en la pared, sus ojos vidriosos.");
        System.out.println("Texto:\" El mensajero susurra: 'La luz revela la verdad'\"");

        while (true) {
            System.out.println("Opciones: 1 Hablar 2 Revisar bolsa 3 Curar 4 Continuar");
            String o = in.nextLine().trim();
            if ("1".equals(o)) {
                System.out.println("Mensajero:\" No entres sin luz\"");
                System.out.println("Respuestas: A. ¿Por qué? / B.Gracias / C.Volver");
                String r = in.nextLine().trim().toUpperCase();
                if ("A".equals(r)) {
                    System.out.println("Mensajero:\" Espectros cazan en la oscuridad.\" Te entrega una poción menor");
                    p.addItem("Poción menor");
                    p.addXp(8);
                    break;
                } else if ("B".equals(r)) {
                    System.out.println("El mensajero entrega una pista y cae dormido.");
                    System.out.println("Pista: \"La luz revela runas ocultas.\"");
                    p.addXp(6);
                    break;
                } else {
                    continue;
                }
            } else if ("2".equals(o)) {
                System.out.println("Revisas la bolsa y encuentras un vendaje (Se añade al inventario)");
                p.addItem("Vendaje");
            } else if ("3".equals(o)) {
                if (p.hasItem("Poción menor")) {
                    System.out.println("Usas la Poción menor y recuperas 6 HP");
                    p.useItem("Poción menor");
                } else System.out.println("No tienes poción");
            } else break;
        }
    }

    private void room3() {
        System.out.println("\n Sala 3: Sala del Cofre");
        System.out.println("Un cofre oxidado bajo un arco cubierto de runas heladas");
        System.out.println("Texto:\" Hay una inscripción:'Toque con cuidado' \"");

        while (true) {
            System.out.println("Opciones: 1. Abrir cofre | 2. Examinar runas | 3. Desactivar trampa | 4. Dejarlo");
            String o = in.nextLine().trim();
            if ("1".equals(o)) {
                System.out.println("Intentas abrir el cofre sin desactivar nada...");
                boolean trap = Utils.rnd(1, 10) <= 4;
                if (trap) {
                    System.out.println("¡Trampa activada!");
                    p.damage(5);
                    System.out.println("Pista: \"Las runas reaccionan a la luz.\"");
                } else {
                    if (!p.hasTorch) {
                        p.addItem("Antorcha");
                        p.addXp(8);
                    } else {
                        p.addGold(Utils.rnd(10, 15));
                        p.addXp(6);
                    }
                }
                break;
            } else if ("2".equals(o)) {
                System.out.println("Examinar runas: parecen reaccionar a la luz, hay marcas de desgaste arriba");
            } else if ("3".equals(o)) {
                boolean success = p.skillCheck(5);
                if (success) {
                    System.out.println("Desactivas la trampa con éxito");
                    if (!p.hasTorch) p.addItem("Antorcha");
                    else p.addGold(Utils.rnd(10, 15));
                    p.addXp(12);
                } else {
                    System.out.println("Fallas al desactivar la trampa.");
                    p.damage(5);
                }
                break;
            } else break;
        }
    }

    private void room4() {
        System.out.println("\n Sala 4: Sala del Puzzle ");
        System.out.println("Una puerta sellada con tres símbolos; debajo, una frase incompleta");
        System.out.println("Inscripción:'Luz, no sombra, revela la —' ");

        while (true) {
            System.out.println("Opciones: 1. Intentar resolver | 2. Usar fragmento de historia | 3. Buscar pistas | 4. Volver");
            String o = in.nextLine().trim();
            if ("1".equals(o)) {
                System.out.println("Introduce la palabra que completa la inscripción:");
                String palabra = in.nextLine().trim();
                if ("VERDAD".equalsIgnoreCase(palabra)) {
                    System.out.println("La puerta se abre. Obtienes una Llave menor");
                    p.addItem("Llave menor");
                    p.addXp(20);
                    break;
                } else {
                    System.out.println("Error. Se activa una pequeña trampa.");
                    p.damage(3);
                    break;
                }
            } else if ("2".equals(o)) {
                System.out.println("Usas fragmento de historia: recuerdas la frase del mensajero... 'La luz revela la verdad' ");
            } else if ("3".equals(o)) {
                System.out.println("Buscas pistas y encuentras marcas que apuntan a la palabra 'VERDAD' ");
            } else break;
        }
    }

    private void room5() {
        System.out.println("\n Sala 5: Sala del Guardián Menor");
        System.out.println("Una figura entera de armadura helada bloquea la salida.");
        System.out.println("Guardia de Hielo: 'Nadie toma lo que yace más arriba...' ");

        while (true) {
            System.out.println("Opciones: 1. Combate | 2. Usar antorcha | 3. Intentar negociar | 4. Huir");
            String o = in.nextLine().trim();
            if ("2".equals(o)) {
                if (p.hasTorch) {
                    System.out.println("Enciendes la antorcha. El Guardián parece menos efectivo contra tu luz");
                } else System.out.println("No tienes antorcha");
            } else if ("3".equals(o)) {
                boolean nego = p.skillCheck(7);
                if (nego) {
                    System.out.println("Negociación exitosa: el guardián baja la guardia parcialmente. Obtienes 10 oro");
                    p.addGold(10);
                    p.addXp(10);
                    return;
                } else {
                    System.out.println("Negociación fallida. Comienza el combate");
                }
            } else if ("4".equals(o)) {
                System.out.println("Huyes de la sala y regresas al inicio del piso");
                return;
            }

            Enemy guard = new Enemy("Guardia de Hielo", 30, 3, 6, 75, "Nadie toma lo que yace más arriba.");

            boolean playerTurn = true;
            while (p.isAlive() && guard.isAlive()) {
                if (playerTurn) {
                    System.out.println("\n Tu turno. Opciones: 1. Atacar | 2. Usar antorcha | 3. Habilidad | 4. Inventario");
                    String act = in.nextLine().trim();
                    if ("2".equals(act)) {
                        if (p.hasTorch) {
                            System.out.println("Usas la antorcha para quemar la armadura de hielo. Haces 8 daño y reduces efecto especial");
                            if (p.cheatOneHitKill) {
                                guard.hp = 0;
                                System.out.println("Cheat activo: enemigo vencido de un golpe.");
                            } else guard.hp -= 8;
                        } else System.out.println("No tienes antorcha");
                    } else if ("3".equals(act) && p.en > 0) {
                        p.en--;
                        System.out.println("Usas habilidad: 10 daño");
                        if (p.cheatOneHitKill) {
                            guard.hp = 0;
                            System.out.println("Cheat activo: enemigo vencido de un golpe.");
                        } else guard.hp -= 10;
                    } else if ("4".equals(act)) {
                        p.showInventory();
                        System.out.println("Nombre del objeto para usar o ENTER para cancelar:");
                        String item = in.nextLine().trim();
                        if (!item.isEmpty()) p.useItem(item);
                    } else {
                        if (p.cheatOneHitKill) {
                            guard.hp = 0;
                            System.out.println("Atacas y (cheat) eliminas al guardián de un golpe.");
                        } else {
                            int dmg = p.getBaseAttackDamage();
                            System.out.println("Atacas por " + dmg + " daño");
                            guard.hp -= dmg;
                        }
                    }
                } else {
                    guard.attack(p);
                }
                playerTurn = !playerTurn;
                p.tickEffects();
            }

            if (!p.isAlive()) return;
            if (!guard.isAlive()) {
                System.out.println("Has vencido al Guardián Menor");
                p.addGold(Utils.rnd(20, 30));
                p.addItem("Fragmento de Historia");
                p.addXp(40);
                System.out.println("Se abre la puerta al Piso 2");
                return;
            }
            break;
        }
    }
}
