package augusto.company;

import java.util.*;

// Classe para implementar o grafo, usando HashMap
class Graph {

    // Em cada no, a chave sera o vertice, e o valor deste vertice eh uma lista de adjacencia
    Map<Vertice, List<Vertice>> adjVertices;

    // Constructor da classe
    Graph() {
        this.adjVertices = new HashMap<Vertice, List<Vertice>>();
    }

    // Metodo para adicionar vertice, checa se ele ja existe, e caso nao, ele eh criado
    void addVertice(String name) {
        adjVertices.putIfAbsent(new Vertice(name), new ArrayList<>());
    }

    // Adiciona uma aresta dado duas vertices
    void addEdge(String origin, String destiny) {
        Vertice o = new Vertice(origin);
        Vertice d = new Vertice(destiny);
        adjVertices.get(o).add(d);
        adjVertices.get(d).add(o);
    }

    // Retorna a lista de adjacencia de um vertice passado como parametro
    List<Vertice> getAdjVertices(String name) {
        return adjVertices.get(new Vertice(name));
    }

    // Vertice eh uma subclasse de Graph com as propriedades de uma vertice
    class Vertice {
        String name;

        // Constructor da classe, inicializa a String name, com o nome do vertice
        Vertice(String name) {
            this.name = name;
        }

        // Os metodos a seguir pertecem ao Java Collections e tiveram que ser sobrescritos para que
        // tudo funcionasse, caso não fossem sobrescritos, ao procurar por uma chave em nosso HashMap,
        // o no nao seria encontrado. Irei explicar melhor no video.

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + getClassType().hashCode();
            result = prime * result + ((name == null) ? 0 : name.hashCode());
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            Vertice other = (Vertice) obj;
            if (!getClassType().equals(other.getClassType()))
                return false;
            if (name == null) {
                return other.name == null;
            } else return name.equals(other.name);
        }

        @Override
        public String toString() {
            return name;
        }

        private Graph getClassType() {
            return Graph.this;
        }


    }

}

// Classe BFS eh onde eh implementado a busca em largura, metodo que usei para encontrar o menor caminho
// entre dois pontos.
class BFS {
    int shortestPath(Graph graph, String origin, String destiny) {

        // Uso um HashSet para guardar os vertices que ja foram visitados
        Set<String> visited = new LinkedHashSet<String>();
        // Um HashSet para guardar o parametro destino, para depois comparar e conferir se chegamos onde pretendiamos
        Set<String> destino = new LinkedHashSet<String>();
        // Fila para implementar o bfs
        Queue<String> queue = new LinkedList<String>();

        // O vertice de origem ja vai estar tanto nos visitados, quanto na fila
        queue.add(origin);
        visited.add(origin);
        destino.add(destiny);

        // Na variavel i é guardado o numero de camadas que já foi iterada
        int i = 0;

        //Enquanto a fila não estivar vazia, o loop continua
        while (!queue.isEmpty()) {

            // Guardo o tamanho atual da fila
            int size = queue.size();

            // Enquanto o size for maior do que 0
            while (size > 0) {
                // Removo o primeiro da fila e guardo na String Vertice
                String vertice = queue.poll();

                // Pego a lista de adjacencia desse vertice, e para cada vertice v da lista, checo se o valor
                // eh o nosso destino, e caso seja, eh retornado o valor de camadas iteradas somado em um
                for (Graph.Vertice v : graph.getAdjVertices(vertice)) {

                    if (destino.contains(v.name)) {
                        return ++i;
                    }

                    // Caso contrario, checa se a vertice ja foi vistada, e caso nao, adiciona ela no Set de visitados
                    // e tambem na fila
                    if (!visited.contains(v.name)) {
                        visited.add(v.name);
                        queue.add(v.name);
                    }

                }
                // Reduz em 1 o size
                size--;
            }

            // Soma 1 no numero de camadas iteradas
            i++;

        }

        // Caso o destino nunca seja encontrado, -1 eh retornado
        return -1;
    }
}

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Leio os dois primeiros inteiros da entrada e armazeno nas variaveis seguintes
        // O int nodes nunca será usado, pois na implementacao com HashMap o tamanho do grafo eh dinamico, e nao
        // requer um numero fixo de vertices
        int nodes = sc.nextInt();
        int edges = sc.nextInt();

        // Crio um novo grafo
        Graph graph = new Graph();

        String line;

        sc.nextLine();

        // Obtenho o resto do input, linha por linha
        for (int i = 0; i < edges; i++) {


            line = sc.nextLine();

            // Para cada linha, eh divido em dois, usando o espaco como criterio (regex: "\s")
            // Em seguida esses dois elementos sao colocados no array words
            String[] words = line.split("\\s");

            // Entao eu adiciono esses dois vertices da entrada, usando o metodo addVertice
            graph.addVertice(words[0]);
            graph.addVertice(words[1]);

            // E em seguida crio uma aresta entre eles, usando o addEdge
            graph.addEdge(words[0], words[1]);
        }

        // O proximo passo eh encontrar a menor distancia entre a entrada e o queijo e depois para a saida

        // Crio a variavel x como objeto da classe BFS
        BFS x = new BFS();

        // E entao uso o metodo shortestPath para encontrar o menor caminho da entrada ate o queijo
        int ateQueijo = x.shortestPath(graph, "Entrada", "*");
        // E em seguida do queijo ate a saida
        int ateSaida = x.shortestPath(graph, "*", "Saida");

        // Entao guardo a soma desses dois inteiros na variavel resultado
        int resultado = ateSaida + ateQueijo;

        // Printo o resultado
        System.out.println(resultado);

        sc.close();
    }
}
