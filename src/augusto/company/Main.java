package augusto.company;

import java.util.*;

class Graph{

    Map<Vertice, List<Vertice>> adjVertices;

    Graph() {
        this.adjVertices = new HashMap<Vertice, List<Vertice>>();
    }

    void addVertice(String name){
        adjVertices.putIfAbsent(new Vertice(name), new ArrayList<>());
    }

    void addEdge(String origin, String destiny) {
        Vertice o = new Vertice(origin);
        Vertice d = new Vertice(destiny);
        adjVertices.get(o).add(d);
        adjVertices.get(d).add(o);
    }

    List<Vertice> getAdjVertices(String name) {
        return adjVertices.get(new Vertice(name));
    }

    class Vertice {
        String name;

        Vertice(String name){
            this.name = name;
        }

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
                if (other.name != null)
                    return false;
            } else if (!name.equals(other.name))
                return false;
            return true;
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
class BFS {
     int breadthFirstTraversal(Graph graph, String root, String destiny) {
        Set<String> visited = new LinkedHashSet<String>();
        Set<String> destino = new LinkedHashSet<String>();
        Queue<String> queue = new LinkedList<String>();


        queue.add(root);
        visited.add(root);
        destino.add(destiny);
        int i = 0;
        while (!queue.isEmpty()) {

            int size = queue.size();

            while (size > 0){
                String vertice = queue.poll();

                for (Graph.Vertice v : graph.getAdjVertices(vertice)) {

                    if (destino.contains(v.name)){
                        return ++i;
                    }
                    if (!visited.contains(v.name)) {
                        visited.add(v.name);
                        queue.add(v.name);
                    }

                }
                size--;
            }

              i++;

        }
        return -1;
    }
}

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int nodes = sc.nextInt();
        int edges = sc.nextInt();

        Graph graph = new Graph();

        String line;

        sc.nextLine();

        for (int i = 0; i < edges; i++){


            line = sc.nextLine();

            String[] words = line.split("\\s");

            graph.addVertice(words[0]);
            graph.addVertice(words[1]);

            graph.addEdge(words[0], words[1]);


//            System.out.print(words[0] + " " + words[1] + "\n");


        }

        BFS x = new BFS();
        int ateQueijo = x.breadthFirstTraversal(graph, "Entrada", "*");
        int ateSaida = x.breadthFirstTraversal(graph, "*", "Saida");



        int resultado = ateSaida + ateQueijo;

        System.out.println(resultado);

        sc.close();
    }
}
