import java.util.*;

public class Game {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        boolean keepPlaying = true;

        while (keepPlaying) {
            Player player = new Player("Protagonista");

            System.out.println("Eira Valen. La nieve cruje bajo tus botas. Las banderas de tu casa ya no ondean; sólo cenizas y rumores");
            System.out.println("En la base de una torre olvidada, la entrada a la Cripta de Hielo aguarda bajo un arco tallado con runas antiguas");
            System.out.println("Recupera la Reliquia del Norte y el nombre de tu casa podrá volver a resonar \n");

            Piso1 floor1 = new Piso1(player, in);
            floor1.play();
            if (!player.isAlive()) {
                endingC(player);
                keepPlaying = postGameMenu(in);
                continue;
            }

            System.out.println("\nFin del Piso 1. Se abre la puerta al Piso 2...\n");

            Piso2 floor2 = new Piso2(player, in);
            floor2.play();
            if (!player.isAlive()) {
                endingC(player);
                keepPlaying = postGameMenu(in);
                continue;
            }

            System.out.println("\nFin del Piso 2. Se abre la puerta al Piso 3...\n");

            Piso3 floor3 = new Piso3(player, in);
            floor3.play();
            if (!player.isAlive()) {
                endingC(player);
                keepPlaying = postGameMenu(in);
                continue;
            }

            // Determinar final según estado acumulado
            if (player.hasRelic) {
                if (player.betrayed) endingB(player);
                else endingA(player);
            } else {
                endingC(player);
            }

            keepPlaying = postGameMenu(in);
        }

        System.out.println("Gracias por jugar. Hasta la próxima.");
        in.close();
    }

    private static void endingA(Player p) {
        System.out.println("\nFinal A (victoria legítima):");
        System.out.println("Eira emerge con la Reliquia. Las campanas del norte suenan y comienzan las conversaciones. Tu casa tiene una oportunidad.");
        if (p.cheatInfiniteHealth || p.cheatOneHitKill || p.cheatSkipRooms) {
            System.out.println("\nNota importante: Activaste cheats durante la partida. Por ello, ninguno de los acontecimientos mostrados ocurrió realmente.");
            System.out.println("El protagonista aún tiene una aventura por hacer si desea ganarla sin trampas.");
        }
    }

    private static void endingB(Player p) {
        System.out.println("\nFinal B (victoria a costo):");
        System.out.println("Tienes la Reliquia pero la traición pesará; el poder regresa, pero a un precio personal alto.");
        if (p.cheatInfiniteHealth || p.cheatOneHitKill || p.cheatSkipRooms) {
            System.out.println("\nNota importante: Activaste cheats durante la partida. Por ello, ninguno de los acontecimientos mostrados ocurrió realmente.");
            System.out.println("El protagonista aún tiene una aventura por hacer si desea ganarla sin trampas.");
        }
    }

    private static void endingC(Player p) {
        System.out.println("\nFinal C (fallo):");
        System.out.println("La cripta reclama lo que intentaste tomar; solo el viento conoce tu nombre ahora.");
        if (p.cheatInfiniteHealth || p.cheatOneHitKill || p.cheatSkipRooms) {
            System.out.println("\nNota: Activaste cheats durante la partida. Por ello, la experiencia está alterada y el resultado no refleja una victoria auténtica.");
            System.out.println("Si quieres un final 'real', reinicia sin activar cheats.");
        }
    }

    private static boolean postGameMenu(Scanner in) {
        while (true) {
            System.out.println("\nOpciones finales: 1. Reiniciar partida  2. Salir");
            String o = in.nextLine().trim();
            if ("1".equals(o)) return true;
            if ("2".equals(o)) return false;
            System.out.println("Opción inválida.");
        }
    }
}
