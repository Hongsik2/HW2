import java.util.*;

public class ShortestPath {

    static int n;           //노드의 개수
    static int s;           //시작 노드의 번호
    static int[][] G;        //가중치 그래프
    static int[] D;           //출발점 s로부터 (n-1)개의 점까지 각각 최단 거리를 저장한 배열 D


    public static void input(int i, int j, int k) {
        G[i-1][j-1] = k;        //i번 노드와 j번 노드 연결하는 선분의 가중치 = k
        G[j-1][i-1] = k;        //j번 노드와 i번 노드 연결하는 선분의 가중치 = k
    }

    public static void initiateArray(int n) {

        D = new int [n];
        G = new int [n][n];

        for(int i=0; i<n; i++){
            D[i] = Integer.MAX_VALUE;       //D를 ∞로 초기화시킨다.

        }


    }

    public static void Dijkstra(int s) {          //s = 시작 노드의 번호

        boolean[] check = new boolean[n];   //check는 점을 방문했는지 나타내는 변수

        D[s] = 0;                           //D[s]=0으로 초기화한다
        check[s] = true;

        for(int i=0; i<n; i++){
            if(!check[i] && G[s][i] != 0){
                D[i] = G[s][i];
            }
        }

        for(int i = 0; i<n; i++){
            int min=Integer.MAX_VALUE;
            int min_index = 0;

            for(int j=0; j<n; j++){
                if(!check[j] && D[j]!=Integer.MAX_VALUE){ //j노드가 방문하지 않은 노드이고 이전 노드와 연결된 점이라면
                    if(D[j]<min){                           //가장 거리가 짧은 노드를 선택
                        min = D[j];
                        min_index = j;
                    }
                }
            }
            check[min_index] = true;        //j점을 방문한 노드로 저장

            for(int k = 0; k<n; k++){
                if(!check[k] && G[min_index][k]!=0){        //k점이 방문한 노드가 아니고 j,k노드가 연결되어 있다면
                    if(D[k] > D[min_index] + G[min_index][k]){  //k점의 최소 거리가 j점 + j,k사이 거리보다 크다면
                        D[k] = D[min_index] + G[min_index][k];  //k점의 최소 거리를 j점 + j,k사이 거리로 저장
                    }
                }
            }
        }

        for(int i=0; i<n; i++){
            if(D[i] == Integer.MAX_VALUE){
                System.out.printf("inf ");
            }
            else {
                System.out.printf("%d ", D[i]);
            }
        }




    }

    public static void main(String[] args) {




        Scanner sc = new Scanner(System.in);

        System.out.printf("노드 개수 입력 : ");
        n = sc.nextInt();           // 가중치 그래프의 노드 개수
        System.out.printf("시작 노드 입력 : ");
        s = sc.nextInt() -1;        // 시작 노드 번호

        initiateArray(n);           // 배열 D를 ∞로 초기화시킨다. 단, D[s]=0으로 초기화한다.


        System.out.println("노드 번호 1, 노드 번호 2, 가중치 입력");
        System.out.println("입력이 끝났다면 0을 입력해 주세요.");

        int count = 1;

        while(true){


            int a = sc.nextInt();
            if(a == 0){
                break;
            }
            int b = sc.nextInt();
            int c = sc.nextInt();


            input(a, b, c);     //배열 G에 값 입력
        }


        long startTime = System.nanoTime();


        Dijkstra(s);            //다익스트라 알고리즘 실행


        long estimatedTime = System.nanoTime() - startTime;

        System.out.println();
        System.out.printf("걸린 시간 : %d 나노초",estimatedTime);
    }
}
