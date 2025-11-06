import java.util.*;

public class Game {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.println("Eira Valen. La nieve cruje bajo tus botas. Las banderas de tu casa ya no ondean; sólo cenizas y rumores");
        System.out.println("En la base de una torre olvidada, la entrada a la Cripta de Hielo aguarda bajo un arco tallado con runas antiguas");
        System.out.println("Recupera la Reliquia del Norte y el nombre de tu casa podrá volver a resonar \n");
        Player player = new Player("Protagonista");
        Piso1 floor1 = new Piso1(player, in);
        floor1.play();
        System.out.println("Fin del Piso 1. Se abre la puerta al Piso 2...");
        in.close();
    }
}

