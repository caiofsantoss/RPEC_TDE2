package Jogo;

import java.util.Random;
import java.util.Scanner;

// A classe "Pilha" representa uma pilha de números inteiros, com funcionalidades como:
// inserção, remoção, impressão, ordenação, movimentação de valores entre pilhas, etc.
public class Pilha {
    private Node topo;
    private int tamanho;
    private int tamanhoMaximo;
    private boolean ordemCrescente;

    // Construtor da classe Pilha
    public Pilha(int tamanhoMaximo, boolean ordemCrescente) {
        this.topo = null;
        this.tamanho = 0;
        this.tamanhoMaximo = tamanhoMaximo;
        this.ordemCrescente = ordemCrescente;
    }
    
    // Método para inserir um valor na pilha
    public void Insere(int dado) {
            // Cria um novo nó com o dado e insere no topo da pilha
            Node no = new Node(dado);
            no.proximo = topo;
            topo = no;
            tamanho++;
        }
    
    // Método para remover um valor do topo da pilha
    public void Remover() {
        if (Vazia()) {
            System.out.println("A sua pilha está vazia, favor adicione um valor antes de tentar remover.");
        } else {
            topo = topo.getProximo();
            tamanho--;
        }
    }
    
    // Método para imprimir os valores da pilha
    public void Imprimir(int id) {
            Node atual = topo;
            System.out.println("Pilha " + id + "\n");

            while (atual != null) {
                System.out.println(atual.dado);
                atual = atual.getProximo();
            }

            System.out.println("\n");
        }
    
    // Função para remover um elemento do topo da pilhaOrigem
    public void pop(Pilha pilhaOrigem) {
        if (pilhaOrigem.Vazia()) {
            System.out.println("A pilha está vazia. Não é possível remover o valor.");
        } else {
            pilhaOrigem.Remover();
        }
    }

    // Função para inserir um elemento na pilhaDestino
    public static void push(Node numero, Pilha pilhaDestino) {
        if (pilhaDestino.cheia()) {
            System.out.println("A pilha está cheia. Não é possível inserir o valor.");
        } else {
            pilhaDestino.Insere(numero.getDado());
        }
    }
    
    // Verifica se a pilha está cheia
    public boolean cheia() {
        return tamanho == tamanhoMaximo;
    }
    
    // Verifica se a pilha está vazia
    public boolean Vazia() {
        return topo == null;
    }
    
    // Verifica se é possível mover um elemento da pilhaOrigem para a pilhaDestino
    public static boolean verificar(Pilha pilhaOrigem, Pilha pilhaDestino) {
            if (pilhaOrigem.Vazia()) {
                return false;
            }
            if (pilhaDestino.Vazia() || (pilhaOrigem.ordemCrescente && pilhaOrigem.topo.getDado() < pilhaDestino.topo.getDado()) || (!pilhaOrigem.ordemCrescente && pilhaOrigem.topo.getDado() > pilhaDestino.topo.getDado())) {
                return true;
            }
            return false;
        }
    
    // Função para ordenar a pilha
    public void Ordenar() {
        if (Vazia() || tamanho == 1) {
            return;
        }

        Pilha auxiliar = new Pilha(tamanhoMaximo, ordemCrescente);

        while (!Vazia()) {
            int valor = topo.dado;
            Remover();

            while (!auxiliar.Vazia() && (ordemCrescente ? auxiliar.topo.dado > valor : auxiliar.topo.dado < valor)) {
                Insere(auxiliar.topo.dado);
                auxiliar.Remover();
            }
            auxiliar.Insere(valor);
        }
        while (!auxiliar.Vazia()) {
            Insere(auxiliar.topo.dado);
            auxiliar.Remover();
        }
    }

    // Função para resolver o jogo
    public void resolverJogo(int n, Pilha origem, Pilha destino, Pilha auxiliar) {
        if (n == 1) {
            moverValor(origem, destino);
        } else {
            resolverJogo(n - 1, origem, auxiliar, destino);
            moverValor(origem, destino);
            resolverJogo(n - 1, auxiliar, destino, origem);
        }
    }

    // Função para verificar se o jogo foi vencido
    public static boolean verificarVitoria(int tamanho, Pilha pilhaDestino, Pilha pilhaOrigem, Pilha pilhaAuxiliar) {
        // Verifica se a pilhaDestino está cheia
        if (pilhaDestino.tamanho != tamanho) {
            return false;
        }

        // Verifica se todos os valores estão na pilhaDestino
        while (!pilhaOrigem.Vazia()) {
            if ((pilhaOrigem.ordemCrescente && pilhaOrigem.topo.getDado() < pilhaDestino.topo.getDado()) || (!pilhaOrigem.ordemCrescente && pilhaOrigem.topo.getDado() > pilhaDestino.topo.getDado())) {
                pilhaDestino.Insere(pilhaOrigem.topo.getDado());
                pilhaOrigem.Remover();
            } else {
                return false;
            }
        }

        while (!pilhaAuxiliar.Vazia()) {
            if ((pilhaAuxiliar.ordemCrescente && pilhaAuxiliar.topo.getDado() < pilhaDestino.topo.getDado()) || (!pilhaAuxiliar.ordemCrescente && pilhaAuxiliar.topo.getDado() > pilhaDestino.topo.getDado())) {
                pilhaDestino.Insere(pilhaAuxiliar.topo.getDado());
                pilhaAuxiliar.Remover();
            } else {
                return false;
            }
        }

        // Verifica se a pilhaDestino agora está cheia com os valores
        return pilhaDestino.tamanho == tamanho;
    }

    // Método para movimentar um valor de uma pilha para outra
    public void moverValor(Pilha origem, Pilha destino) {
        if (origem.Vazia()) {
            System.out.println("Pilha de origem vazia. Não é possível mover o valor.");
            return;
        }
    
        if (destino.Vazia()) {
            Node valor = origem.topo;
            origem.pop(origem);
            destino.push(valor, destino);
            return;
        }
    
        if (origem.ordemCrescente && origem.topo.getDado() < destino.topo.getDado()) {
            Node valor = origem.topo;
            origem.pop(origem);
            destino.push(valor, destino);
        } else if (!origem.ordemCrescente && origem.topo.getDado() > destino.topo.getDado()) {
            Node valor = origem.topo;
            origem.pop(origem);
            destino.push(valor, destino);
        } else {
            System.out.println("Movimento inválido. Não é possível mover o valor.");
        }
    }
}
