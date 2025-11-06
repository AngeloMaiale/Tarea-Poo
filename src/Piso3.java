import java.util.*;

public class Piso3 {

    private Player p;
    private Scanner in;
    private Set<String> restedRooms = new HashSet<>();
    public Piso3(Player p, Scanner in) {
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
        System.out.println("\nPiso 3 - Sala 1: Sala del Eco");
        System.out.println("Tus pasos resuenan; voces antiguas te llaman por tu nombre.");
        System.out.println("Una voz susurra: 'Rinde lo que no te pertenece y habrá paz.'");
        System.out.println("Opciones: 1. Ignorar | 2. Responder | 3. Buscar eco | 4. Retroceder");

        String o = in.nextLine().trim();
        if ("2".equals(o)) {
            System.out.println("Respondes y una sombra te reconoce. Registras una nota: 'Existe alguien que desea la Reliquia.'");
            p.addItem("Nota eco");
            p.addXp(8);
        } else if ("3".equals(o)) {
            boolean ok = p.skillCheck(6);
            if (ok) {
                System.out.println("Encuentras un eco que menciona a un Traidor Encapuchado.");
                p.addItem("Pista traidor");
                p.addXp(10);
            } else {
                System.out.println("El eco se apaga. Sigues adelante.");
            }
        } else if ("4".equals(o)) {
            System.out.println("Retrocedes unos pasos y una piedra cae; nada grave, sigues.");
        }
    }
    private void room2() {
        System.out.println("\nPiso 3 - Sala 2: Sala del Tesoro Sellado");
        System.out.println("Puerta con cerradura de runas; el aire huele a antigüedad.");
        System.out.println("Opciones: 1. Usar llave | 2. Resolver puzzle | 3. Forzar la puerta | 4. Volver");

        String o = in.nextLine().trim();
        if ("1".equals(o)) {
            if (p.hasItem("Llave Escarchada")) {
                System.out.println("Usas la Llave Escarchada. La cerradura cede.");
                System.out.println("Dentro encuentras: Reliquia menor, Poción mayor, o Antorcha Sagrada.");
                p.addItem("Antorcha Sagrada");
                p.addItem("Poción mayor");
                p.addXp(18);
            } else {
                System.out.println("No tienes la Llave Escarchada.");
            }
        } else if ("2".equals(o)) {
            boolean ok = p.skillCheck(7);
            if (ok) {
                System.out.println("Resuelves las runas y desbloqueas un cofre: Antorcha Sagrada.");
                p.addItem("Antorcha Sagrada");
                p.addXp(16);
            } else {
                System.out.println("Fallas el puzzle y activas una trampa leve: -4 HP.");
                p.damage(4);
            }
        } else if ("3".equals(o)) {
            System.out.println("Intentas forzar: el sello es fuerte. Pierdes tiempo y te expones a frío, -3 HP.");
            p.damage(3);
        } else {
            System.out.println("Decides no arriesgar y sigues.");
        }
    }
    private void room3() {
        System.out.println("\nPiso 3 - Sala 3: Sala de Traición");
        System.out.println("Una figura encapuchada te ofrece alianza por la Reliquia.");
        System.out.println("Traidor: 'Dame la Reliquia y te ahorraré la lucha.'");
        System.out.println("Opciones: 1. Aceptar | 2. Rechazar | 3. Interrogar | 4. Engañar");

        String o = in.nextLine().trim();
        if ("1".equals(o)) {
            System.out.println("Aceptas entregar la Reliquia si la obtienes. El traidor te promete seguridad.");
            p.betrayed = true;
            p.addXp(5);
            System.out.println("Sigue tu camino; la sombra se desvanece.");
            return;
        } else if ("2".equals(o)) {
            System.out.println("Rechazas. El encapuchado se enfurece y ataca.");
            Enemy t = new Enemy("Traidor", 20, 3, 6, 75, "Te arrepentirás");
            boolean won = Combat.fight(p, Arrays.asList(t), in);
            if (!won) return;
            p.addXp(25);
        } else if ("3".equals(o)) {
            boolean ok = p.skillCheck(7);
            if (ok) {
                System.out.println("Interrogas con éxito: el traidor busca la Reliquia por motivos personales; puede traicionarte luego.");
                p.addItem("Información traidor");
                p.addXp(12);
            } else {
                System.out.println("El traidor no dice nada y se aleja con una sonrisa.");
            }
        } else if ("4".equals(o)) {
            boolean ok = p.skillCheck(8);
            if (ok) {
                System.out.println("Engañas al traidor y le quitas un objeto menor. Ganas ventaja potencial para el jefe.");
                p.addItem("Ventaja engaño");
                p.addXp(12);
            } else {
                System.out.println("El engaño falla y el traidor se burla antes de irse.");
            }
        } else {
            System.out.println("No interactúas con el encapuchado y prosigues.");
        }
    }
    private void room4() {
        System.out.println("\nPiso 3 - Sala 4: Sala de la Reliquia");
        System.out.println("Un pedestal vacío envuelto en runas; una sombra se materializa.");
        System.out.println("Señor de la Reliquia: 'Tu linaje no reclama lo que no puede sostener.'");
        System.out.println("Opciones: 1. Combatir | 2. Usar antorcha/objeto | 3. Intentar diálogo | 4. Huir (no)");
        String o = in.nextLine().trim();
        if ("3".equals(o)) {
            boolean ok = p.skillCheck(8);
            if (ok) {
                System.out.println("El diálogo surte efecto: reduces la fase enrabietada del jefe temporalmente.");
                p.addXp(10);
            } else {
                System.out.println("El intento de diálogo falla y el jefe se abalanza con furia.");
            }
        }
        Enemy boss = new Enemy("Señor de la Reliquia", 60, 6, 10, 85, "Tu linaje será puesto a prueba.");
        boolean playerTurn = true;
        boolean phaseRage = false;
        while (p.isAlive() && boss.isAlive()) {
            if (playerTurn) {
                System.out.println("\n Tu turno. Opciones: 1. Atacar | 2. Usar antorcha/Antorcha Sagrada | 3. Habilidad | 4. Inventario");
                String act = in.nextLine().trim();
                if ("2".equals(act)) {
                    if (p.hasItem("Antorcha Sagrada")) {
                        System.out.println("La luz sagrada quema la sombra. Haces 15 daño.");
                        if (p.cheatOneHitKill) { boss.hp = 0; System.out.println("Cheat activo: enemigo vencido de un golpe."); }
                        else boss.hp -= 15;
                    } else if (p.hasTorch) {
                        System.out.println("La luz quema la sombra. Haces 8 daño.");
                        if (p.cheatOneHitKill) { boss.hp = 0; System.out.println("Cheat activo: enemigo vencido de un golpe."); }
                        else boss.hp -= 8;
                    } else {
                        System.out.println("No tienes objeto de luz.");
                    }
                } else if ("3".equals(act) && p.en > 0) {
                    p.en--;
                    System.out.println("Usas habilidad poderosa: 15 daño");
                    if (p.cheatOneHitKill) { boss.hp = 0; System.out.println("Cheat activo: enemigo vencido de un golpe."); }
                    else boss.hp -= 15;
                } else if ("4".equals(act)) {
                    p.showInventory();
                    System.out.println("Nombre del objeto para usar o ENTER para cancelar:");
                    String item = in.nextLine().trim();
                    if (!item.isEmpty()) p.useItem(item);
                } else {
                    if (p.cheatOneHitKill) { boss.hp = 0; System.out.println("Atacas y (cheat) eliminas al jefe de un golpe."); }
                    else {
                        int dmg = p.getBaseAttackDamage();
                        System.out.println("Atacas por " + dmg + " daño");
                        boss.hp -= dmg;
                    }
                }
            } else {
                if (!phaseRage && boss.hp <= 30) {
                    phaseRage = true;
                    System.out.println("El Señor de la Reliquia entra en furia: ataques adicionales.");
                }

                if (phaseRage) {
                    boss.attack(p);
                    if (p.isAlive()) boss.attack(p);
                } else {
                    boss.attack(p);
                }
            }
            playerTurn = !playerTurn;
            p.tickEffects();
        }
        if (!p.isAlive()) return;
        if (!boss.isAlive()) {
            System.out.println("Has vencido al Señor de la Reliquia.");
            System.out.println("En el pedestal brilla la Reliquia del Norte. La tomas con cuidado.");
            p.hasRelic = true;
            p.addGold(Utils.rnd(40, 60));
            p.addXp(200);
            if (p.hasItem("Ventaja engaño")) {
                System.out.println("Tu astucia con el traidor te da ventaja: puedes escapar con la Reliquia sin perderla ante la traición.");
                p.betrayed = false;
            }
        }
    }
    private void room5() {
        if (!p.hasRelic) {
            System.out.println("\nSala de Escape: No llevas la Reliquia. La cripta comienza a temblar y todo se derrumba.");
            System.out.println("Escapas apresuradamente pero sin el objeto. El final será incompleto.");
            return;
        }
        System.out.println("\nPiso 3 - Sala de Escape");
        System.out.println("La cripta tiembla; corredores colapsan. Tienes 5 turnos para salir con la Reliquia.");
        int turns = 5;
        while (turns > 0 && p.isAlive()) {
            System.out.println("\nTurnos restantes: " + turns);
            p.showStatus();
            System.out.println("Opciones: 1. Correr directo 2. Usar atajo 3. Ayudar a aliados 4. Abandonar Reliquia 5. Inventario");
            String o = in.nextLine().trim();
            if ("5".equals(o)) {
                p.showInventory();
                System.out.println("Nombre del objeto para usar o ENTER para cancelar:");
                String item = in.nextLine().trim();
                if (!item.isEmpty()) p.useItem(item);
                continue;
            }
            if ("1".equals(o)) {
                boolean ok = p.skillCheck(5);
                if (ok) {
                    System.out.println("Corres con éxito. Avanzas más rápido.");
                    turns -= 1;
                } else {
                    System.out.println("Caes con un escombro: -3 HP y pierdes tiempo.");
                    p.damage(3);
                    turns -= 2;
                }
            } else if ("2".equals(o)) {
                boolean ok = p.skillCheck(7);
                if (ok) {
                    System.out.println("Atajo exitoso: avanzas en silencio y ganas 2 turnos extra de progreso.");
                    turns -= 1;
                } else {
                    System.out.println("Atajo bloqueado: pierdes 2 turnos y recibes 2 de daño.");
                    p.damage(2);
                    turns -= 3;
                }
            } else if ("3".equals(o)) {
                System.out.println("Ayudas a un aliado y reduces el tiempo total a cambio de 1 EN.");
                p.en = Math.max(0, p.en - 1);
                turns -= 1;
            } else if ("4".equals(o)) {
                System.out.println("Abandonas la Reliquia. La dejas en el pedestal y escapas con vida.");
                p.hasRelic = false;
                return;
            } else {
                System.out.println("Decisión inválida. Pierdes tiempo.");
                turns -= 1;
            }
        }
        if (!p.isAlive()) return;
        if (turns > 0) {
            System.out.println("Logras escapar con la Reliquia dentro del tiempo. El exterior te recibe con viento cortante pero vida.");
            p.addXp(60);
        } else {
            System.out.println("Se acaba el tiempo en el último corredor: un escombro te atrapa parcialmente, pero consigues salir con esfuerzo.");
            p.damage(3);
        }
        if (p.betrayed) {
            System.out.println("El Traidor aparece ante la salida y reclama la Reliquia como acordado.");
            if (p.hasItem("Ventaja engaño")) {
                System.out.println("Aun así, tu astucia permite escapar con la Reliquia.");
            } else {
                System.out.println("Cumples el trato a regañadientes: entregas la Reliquia.");
                p.hasRelic = false;
            }
        }
    }
}