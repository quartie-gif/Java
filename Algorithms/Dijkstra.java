package Algorithms;

public class Dijkstra{


    public static void printGraph(int[] dist, int index){
        System.out.println("Distance form source to destination is: ");
        for (int i = 0; i < dist.length; i++) {
            System.out.println(i + "-------" + dist[i]);
        }
    }

    public static int minDistance(int[] dist, boolean[] visited){
        int min = Integer.MAX_VALUE, index = -1;
        for (int i = 0; i < dist.length; i++) {
            if(!visited[i] && dist[i] <= min)
            {
                min = dist[i];
                index = i;
            }
        }
        return index;
    }

    private static void dijkstra(int[][] graph, int src)
    {
        int dist[] = new int[graph[0].length];
        boolean visited[] = new boolean[graph[0].length];

        for (int i = 0; i < graph[0].length; i++) {
            dist[i] = Integer.MAX_VALUE;
            visited[i] = false;
        }

        dist[0]=0;
        for (int i = 0; i < graph[0].length; i++) {
            int minIndex = minDistance(dist, visited);
            visited[minIndex] = true;
            for (int j = 0; j < graph[0].length; j++) {
                if(!visited[j] && graph[minIndex][j]!=0 && dist[minIndex] != Integer.MAX_VALUE && dist[minIndex] + graph[minIndex][j] < dist[j])
                {
                    dist[j] = dist[minIndex] + graph[minIndex][j];
                }
            }
            printGraph(dist, graph[0].length);
        }
    }


    public static void exec(){
        int graph[][] = new int[][]{
                {0,4,0,0,7},
                {4,0,1,2,0},
                {0,1,0,6,0},
                {0,2,6,0,0},
                {7,0,0,0,0}
        };
        dijkstra(graph, 0);
    }
}