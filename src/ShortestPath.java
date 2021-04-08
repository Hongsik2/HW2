//통합 완료!

import java.util.Scanner;

public class ShortestPath {

    static int V;               //노드의 개수
    static int E;               //간선의 개수
    static int s;               //시작 노드
    static int[][] Graph;       //가중치 그래프
    static int[] D;             //출발점 s로부터 (n-1)개의 점까지 각각 최단 거리를 저장한 배열 D
    static boolean[] check;     //노드를 방문했는지 확인

    static void input(int i, int j, int w) { //정점끼리 간선으로 연결. 가중치 값으로 대입

        Graph[i][j] = w;                   //i번 노드와 j번 노드 연결하는 간선의 가중치 = w
        Graph[j][i] = w;                   //j번 노드와 i번 노드 연결하는 간선의 가중치 = w
    }

    static int findindex() {

        int index = 0;
        for (int i = 1; i < V + 1; i++) {
            int min = Integer.MAX_VALUE;
            index = 0;
            for (int j = 1; j < V + 1; j++) {
                if (!check[j] && D[j] < min && D[j] != Integer.MAX_VALUE) {       //방문하지 않았고, 최소인 거리의 인덱스 리턴
                    min = D[j];
                    index = j;
                }
            }
        }
        return index;
    }

    static void Dijkstra() {

        for (int i = 1; i < V + 1; i++) {                 //방문하지 않았고, 출발점과 이어진 노드의 가중치를 D에 대입
            if (!check[i] && Graph[s][i] != 0) {
                D[i] = Graph[s][i];
            }
        }

        for (int i = 1; i < V + 1; i++) {
            int min_index = findindex();
            check[min_index] = true;     //최소 거리인 노드(A)에 방문 표시

            for (int j = 1; j < V + 1; j++) {
                if (!check[j] && Graph[min_index][j] != 0             //A와 연결된 방문하지 않은 노드들 중 최소 거리 선택
                        && D[min_index] + Graph[min_index][j] < D[j])
                    D[j] = D[min_index] + Graph[min_index][j];
            }
        }
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        System.out.printf("노드 개수 입력 : ");
        V = sc.nextInt();                           // 가중치 그래프의 노드 개수
        System.out.printf("간선 개수 입력 : ");
        E = sc.nextInt();
        System.out.printf("시작 노드 입력 : ");
        s = sc.nextInt();                           // 시작 노드 번호

        Graph = new int[V + 1][V + 1];
        System.out.println("노드 노드 거리 순으로 입력하세요");
        for (int i = 0; i < E; i++) {
            input(sc.nextInt(), sc.nextInt(), sc.nextInt());      //두 개의 정점을 이어주며 가중치를 입력받는다.
        }

        D = new int[V + 1];
        for (int i = 1; i < V + 1; i++) {
            D[i] = Integer.MAX_VALUE;            //배열 D를 ∞로 초기화시킨다.
        }

        D[s] = 0;                             //출발점의 거리를 0으로 설정
        check = new boolean[V + 1];
        check[s] = true;                      //출발점을 방문했으므로 true

        Dijkstra();

        for (int i = 1; i < V + 1; i++) {
            if (D[i] == Integer.MAX_VALUE) {
                System.out.printf("inf ");
            } else {
                System.out.printf("%d ", D[i]);
            }
        }
    }
}
