import java.util.*;

public class Piso1 {
    private Player p;
    private Scanner in;

    public Piso1(Player p, Scanner in) {
        this.p = p;
        this.in = in;
    }

    public void play() {
        room1();
        if (!p.isAlive()) return;
        room2();
        if (!p.isAlive()) return;
        room3();
        if (!p.isAlive()) return;
        room4();
        if (!p.isAlive()) return;
        room5();
    }

    private void room1() {
        System.out.println("\n   Sala 1: Sala de Patrulla ");
        System.out.println("Un pasillo helado, antorchas apagadas. Voz ronca: pasos acercándose");
        System.out.println("Texto:\" Dos Vigías Exhaustos patrullan la sala \"");
        System.out.println("Opciones: 1. Atacar / 2. Intentar sigilo / 3. Usar habilidad / 4. Inventario");
        String opt = in.nextLine().trim();
        if ("2".equals(opt)) {
            boolean success = p.skillCheck(6);
            if (success) {
                System.out.println("Sigilo exitoso. Evitas combate y encuentras 3 monedas");
                p.addGold(3);
                return;
            } else {
                System.out.println("Sigilo fallido. Los vigías te detectan");
            }
        }


        // combate contra dos vigías débiles
        Enemy e1 = new Enemy("Vigía Exhausto", 6, 2, "¡Alto ahí!");
        Enemy e2 = new Enemy("Vigía Exhausto", 6, 2, "¡Alto ahí!");
        boolean won = Combat.fight(p, Arrays.asList(e1, e2), in);
        if (won) {
            int oro = Utils.rnd(5, 8);
            p.addGold(oro);
            p.addItem("Parche de tela");
            System.out.println("Pista obtenida:\" Runas arriba guardan puertas \"");
        }
    }

    private void room2(){
        System.out.println("\n Sala 2: Sala del Mensajero");
        System.out.println("Un mensajero sangrando se apoya en la pared, sus ojos vidriosos.");
        System.out.println("Texto:\" El mensajero susurra: 'La luz revela la verdad'\"");
        while (true){
            System.out.println("Opciones: 1 Hablar  2 Revisar bolsa  3 Curar  4 Continuar");
            String o = in.nextLine().trim();
            if ("1".equals(o)) {
                System.out.println("Mensajero:\" No entres sin luz\"");
                System.out.println("Respuestas: A. ¿Por qué? / B.Gracias / C.Volver");
                String r= in.nextLine().trim().toUpperCase();
                if("A".equals(r)){
                    System.out.println("Mensajero:\" Espectros cazan en la oscuridad.\" Te entrega una poción menor");
                    p.addItem("Poción menor");
                    break;
                }else if ("B".equals(r)){
                    System.out.println("El mensajero entrega una pista y cae dormido.");
                    System.out.println("Pista: \"La luz revela runas ocultas.\"");
                    break;
                }else{
                    continue;
                }
            }else if("2".equals(o)){
                System.out.println("Revisas la bolsa y encuentras un vendaje (Se añade al inventario)");
                p.addItem("Vendaje");
            }else if("3".equals(o)){
                if(p.inventory.contains("Poción menor")) {
                    System.out.println("Usas la Poción menor y recuperas 6 HP");
                    p.hp += 6;
                    p.inventory.remove("Poción menor");
                }else System.out.println("No tienes poción");
            }else break;
        }
    }

    private void room3(){
        System.out.println("\n  Sala 3: Sala del Cofre");
        System.out.println("Un cofre oxidado bajo un arco cubierto de runas heladas");
        System.out.println("Texto:\" Hay una inscripción:'Toque con cuidado' \"");
        while(true){
            System.out.println("Opciones: 1. Abrir cofre / 2. Examinar runas / 3. Desactivar trampa / 4. Dejarlo");
            String o= in.nextLine().trim();
            if("1".equals(o)){
                System.out.println("Intentas abrir el cofre sin desactivar nada...");
                boolean trap = Utils.rnd(1, 10) <= 4;                  // 40% trampa
                if(trap){
                    System.out.println("¡Trampa activada!");
                    p.damage(5);
                    System.out.println("Pista: \"Las runas reaccionan a la luz.\"");
                }else{
                    if(!p.hasTorch){
                        p.addItem("Antorcha");
                    }else{
                        p.addGold(Utils.rnd(10, 15));
                    }
                }
                break;
            }else if("2".equals(o)){
                System.out.println("Examinar runas: parecen reaccionar a la luz, hay marcas de desgaste arriba");
            }else if("3".equals(o)){
                boolean success= p.skillCheck(5);
                if(success){
                    System.out.println("Desactivas la trampa con éxito");
                    if(!p.hasTorch) p.addItem("Antorcha");
                    else p.addGold(Utils.rnd(10, 15));
                } else{
                    System.out.println("Fallas al desactivar la trampa.");
                    p.damage(5);
                }
                break;
            }else break;
        }
    }

    private void room4(){
        System.out.println("\n   Sala 4: Sala del Puzzle ");
        System.out.println("Una puerta sellada con tres símbolos; debajo, una frase incompleta");
        System.out.println("Inscripción:'Luz, no sombra, revela el —' ");
        while(true){
            System.out.println("Opciones: 1. Intentar resolver / 2. Usar fragmento de historia / 3. Buscar pistas / 4. Volver");
            String o= in.nextLine().trim();
            if("1".equals(o)){
                System.out.println("Introduce la palabra que completa la inscripción:");
                String palabra= in.nextLine().trim();
                if("VERDAD".equalsIgnoreCase(palabra)){
                    System.out.println("La puerta se abre. Obtienes una Llave menor");
                    p.addItem("Llave menor");
                    break;
                }else{
                    System.out.println("Error. Se activa una pequeña trampa.");
                    p.damage(3);
                    break;
                }
            }else if("2".equals(o)){
                System.out.println("Usas fragmento de historia: recuerdas la frase del mensajero... 'La luz revela la verdad' ");
            }else if("3".equals(o)){
                System.out.println("Buscas pistas y encuentras marcas que apuntan a la palabra 'VERDAD' ");
            }else break;
        }
    }

    private void room5(){
        System.out.println("\n   Sala 5: Sala del Guardián Menor");
        System.out.println("Una figura entera de armadura helada bloquea la salida.");
        System.out.println("Guardia de Hielo: 'Nadie toma lo que yace más arriba...' ");
        while(true){
            System.out.println("Opciones: 1. Combate / 2. Usar antorcha / 3. Intentar negociar / 4. Huir");
            String o= in.nextLine().trim();
            if("2".equals(o)){
                if(p.hasTorch){
                    System.out.println("Enciendes la antorcha. El Guardián parece menos efectivo contra tu luz");

                    // efecto aplicado dentro del combate
                }else System.out.println("No tienes antorcha");
            }else if ("3".equals(o)){
                boolean nego= p.skillCheck(7);
                if(nego){
                    System.out.println("Negociación exitosa: el guardián baja la guardia parcialmente. Obtienes 10 oro");
                    p.addGold(10);
                    return;
                }else{
                    System.out.println("Negociación fallida. Comienza el combate");
                }
            }else if("4".equals(o)) {
                System.out.println("Huyes de la sala y regresas al inicio del piso");
                return;
            }

            // combate especial: Guardián con Ola de Frío
            Enemy guard= new Enemy("Guardia de Hielo", 30, 5, "Nadie toma lo que yace más arriba.");


            // combate personalizado simple: cada turno el guardián puede usar Ola de Frío
            boolean playerTurn= true;
            while(p.isAlive() && guard.isAlive()) {
                if(playerTurn){
                    System.out.println("\n Tu turno. Opciones: 1. Atacar / 2. Usar antorcha / 3. Habilidad");
                    String act= in.nextLine().trim();
                    if("2".equals(act)){
                        if(p.hasTorch){
                            System.out.println("Usas la antorcha para quemar la armadura de hielo. Haces 8 daño y reduces efecto especial");
                            guard.hp -= 8;
                        }else System.out.println("No tienes antorcha");
                    }else if("3".equals(act) && p.en > 0){
                        p.en--;
                        System.out.println("Usas habilidad: 10 daño");
                        guard.hp -= 10;
                    }else{
                        System.out.println("Atacas por 5 daño");
                        guard.hp -= 5;
                    }
                }else{

                    // turno del guardián
                    boolean useWave = Utils.rnd(1, 100) <= 40;             // 40% usar ola
                    if(useWave){
                        int reduce = 1;
                        if(p.hasTorch){
                            System.out.println("El guardián lanza Ola de Frío, pero tu antorcha reduce el efecto en 50%");
                            reduce = 0;            // reducción simbólica (evita penalidad)
                        }else{
                            System.out.println("El guardián lanza Ola de Frío. Tu EN se reduce en 1 este turno");
                            p.en = Math.max(0, p.en - 1);
                        }
                    }else{
                        System.out.println("Guardia ataca por "+ guard.atk + "daño");
                        p.damage(guard.atk);
                    }
                }
                playerTurn = !playerTurn;
            }
            if(!p.isAlive()) return;
            if(!guard.isAlive()){
                System.out.println("Has vencido al Guardián Menor");
                p.addGold(Utils.rnd(20, 30));
                p.addItem("Fragmento de Historia");
                System.out.println("Se abre la puerta al Piso 2");
                return;
            }
            break;
        }
    }
}

