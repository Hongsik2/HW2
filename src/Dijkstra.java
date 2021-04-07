//04.05 배열을 이용한 Dijkstra 알고리즘 구현(간선과 가중치 입력시, 첫 번째 정점은 0으로 시작함)
//04.07 배열 크기 + 1 과 인덱스 시작을 1로 해줘서 첫 번째 정점을 1로 시작하도록 함.

import java.util.Scanner;

public class Dijkstra {

    static void point(int[][] Graph, int i, int j, int w) { //정점끼리 간선으로 연결. 가중치 값으로 대입
        Graph[i][j] = w;
        Graph[j][i] = w;
    }

    static void Shortest(int[][] Graph, int[] D, boolean[] visited, int V, int s) {
        for (int i = 1; i < V + 1; i++) {
            int index = findindex(D, visited, V);
            visited[index] = true;     //최소 거리인 노드(A)에 방문 표시

            for (int j = 1; j < V + 1; j++) {
                if (!visited[j] && Graph[index][j] != 0 && D[index] + Graph[index][j] < D[j]) //A와 연결된 방문하지 않은 노드들 중 최소 거리 선택
                    D[j] = D[index] + Graph[index][j];
            }
        }
    }

    static int findindex(int[] D, boolean[] visited, int V) {
        int index = 0;
        for (int i = 1; i < V + 1; i++) {
            int min = Integer.MAX_VALUE;
            index = 0;
            for (int j = 1; j < V + 1; j++) {
                if (!visited[j] && D[j] < min && D[j] != Integer.MAX_VALUE) { //방문하지 않았고, 최소인 거리의 인덱스 리턴
                    min = D[j];
                    index = j;
                }
            }
        }
        return index;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int V = scanner.nextInt(); //정점의 개수, 간선의 개수, 출발점 s을 입력 받는다.
        int E = scanner.nextInt();
        int s = scanner.nextInt();
        int[][] Graph = new int[V + 1][V + 1];
        for (int i = 0; i < E; i++) {
            point(Graph, scanner.nextInt(), scanner.nextInt(), scanner.nextInt()); //두 개의 정점을 이어주며 가중치를 입력받는다.
        }

        boolean[] visited = new boolean[V + 1]; //방문했는지 확인 (내부에 모두 false가 자동으로 초기화된다)
        int[] D = new int[V + 1];               //최단 경로 거리
        for (int i = 1; i < V + 1; i++) {
            D[i] = Integer.MAX_VALUE;           //배열 D를 무한대로 초기화시킨다.
        }
        D[s] = 0;
        visited[s] = true;      //출발점의 거리를 0으로 설정, 방문했으므로 true로 설정


       for (int i = 1; i < V + 1; i++) {
            if (!visited[i] && Graph[s][i] != 0) //방문하지 않았고, 출발점과 이어진 노드의 가중치를 D에 대입
               D[i] = Graph[s][i];
        }

        Shortest(Graph, D, visited, V, s);

        for (int i = 1; i < V + 1; i++) {
            System.out.print(D[i] + " ");
        }
        scanner.close();
    }

}