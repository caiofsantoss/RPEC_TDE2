import static Jogo.Pilha.verificarVitoria;
import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
    // Cria um objeto Scanner para receber entrada do usuário
    Scanner in = new Scanner(System.in);

    // Solicita ao usuário o tamanho das pilhas
    System.out.println("Por favor, digite o tamanho das pilhas:");
    int tamanho = in.nextInt();

    // Solicita ao usuário a ordem dos valores nas pilhas
    System.out.println("Escolha a ordem dos valores:");
    System.out.println("1 - Ordem crescente");
    System.out.println("2 - Ordem decrescente");
    int escolhaOrdem = in.nextInt();
    
    // Determina se a ordem é crescente com base na escolha do usuário
    boolean ordemCrescente = (escolhaOrdem == 1);

    // Cria quatro pilhas com o mesmo tamanho e ordem
    Pilha pilha1 = new Pilha(tamanho, ordemCrescente);
    Pilha pilha2 = new Pilha(tamanho, ordemCrescente);
    Pilha pilha3 = new Pilha(tamanho, ordemCrescente);
    Pilha global = new Pilha(tamanho, ordemCrescente);

    // Preenche a pilha1 com valores aleatórios e a ordena
    for (int i = 0; i < tamanho; i++) {
        Random rand = new Random();
        int dado = rand.nextInt(100);
        pilha1.Insere(dado);
    }
    pilha1.Ordenar();

    // Imprime o estado inicial das pilhas
    pilha1.Imprimir(1);
    pilha2.Imprimir(2);
    pilha3.Imprimir(3);

    int contador = 0;
    boolean vitoria = false;

    // Loop principal do jogo
    while (!vitoria) {
        // Exibe as opções de ação para o jogador
        System.out.println("\n0- Sair do Jogo\n1- Movimentar\n2- Solução Automática");
        int opcao = in.nextInt();
        switch (opcao) {
            case 0:
                // O jogador escolheu sair do jogo
                System.out.println("Você saiu do jogo.");
                return;
            case 1:
                // O jogador escolheu realizar uma jogada manual
                System.out.println("Qual é a pilha de origem?");
                int movimento = in.nextInt();
                switch (movimento) {
                    case 1:
                        System.out.println("Qual é a pilha de destino (2 ou 3)?");
                        int resultado = in.nextInt();
                        if (resultado == 2) {
                            pilha2.moverValor(pilha1, pilha2);
                        } else if (resultado == 3) {
                            pilha3.moverValor(pilha1, pilha3);
                        }
                        contador++; // Incrementa o contador de jogadas
                        break;
                    case 2:
                        System.out.println("Qual é a pilha de destino (1 ou 3)?");
                        resultado = in.nextInt();
                        if (resultado == 1) {
                            pilha1.moverValor(pilha2, pilha1);
                        } else if (resultado == 3) {
                            pilha3.moverValor(pilha2, pilha3);
                        }
                        contador++;
                        break;
                    case 3:
                        System.out.println("Qual é a pilha de destino (1 ou 2)?");
                        resultado = in.nextInt();
                        if (resultado == 1) {
                            pilha1.moverValor(pilha3, pilha1);
                        } else if (resultado == 2) {
                            pilha2.moverValor(pilha3, pilha2);
                        }
                        contador++;
                        break;
                }
                break;
            case 2:
                // O jogador escolheu a solução automática do jogo
                System.out.println("Resolvendo o jogo de forma Crescente...");
                Pilha auxiliar = new Pilha(tamanho, ordemCrescente);
                pilha1.resolverJogo(tamanho, pilha1, pilha3, auxiliar);
                System.out.println("Jogo resolvido!");
                contador += (int) (Math.pow(2, tamanho) - 1); // Incrementa o contador com o número de jogadas automáticas
                break;
        }

        // Imprime o estado atual das pilhas após cada jogada
        pilha1.Imprimir(1);
        pilha2.Imprimir(2);
        pilha3.Imprimir(3);

        // Verifica se o jogador venceu o jogo
        vitoria = verificarVitoria(tamanho, pilha3, pilha1, pilha2);
    }

    // Exibe a mensagem de vitória e o número total de jogadas
    System.out.printf("Você precisou de %d jogadas para ganhar!\n", contador);
}
}
