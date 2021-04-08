# 최단 경로(Shoest Path) 찾기

- 주어진 가중치 그래프에서 어느 한 출발점에서 또 다른 도착점까지의 최단 경로를 찾는 문제

---

### 다익스트라(Dijkstra) 최단 경로 알고리즘 이용

- 주어진 출발점에서 시작
- 출발점으로부터 최단 거리가 확정되지 않은 점들 중 가장 가까운 점들을 추가 후, 그 점과의 최단 거리를 확정

---

### ShortestPath(G, s)

입력 : 가중치 그래프 G = (V, E), |V| = n, |E|= m

출력 : 출발점 s로부터 (n-1)개의 점까지 각각 최단 거리를 저장한 배열 D

1. 배열 D를 ∞로 초기화시킨다. 단, D[s]=0으로 초기화한다.

​                 // 배열 D[v]에는 출발점 s로부터 점 v까지의 거리가 저장된다.

2. while (s로부터의 최단 거리가 확정되지 않은 점이 있으면) {

3. 현재까지 s로부터 최단 거리가 확정되지 않은 각 점 v에 대해서 최소의 D[v]의 값을 가진 점 vmin을 선택하고, 출발점 s로부터 점 vmin까지의 최단 거리 D[vmin]을 확정한다.

4. s로부터 현재보다 짧은 거리로 점 vmin을 통해 우회 가능한 각 점 w에 대해서 D[w]를 갱신한다. }

5. return D

---

### Source Code 설명

#### 변수명

```java
static int V;               
static int E;              
static int s;              
static int[][] Graph;       
static int[] D;            
static boolean[] check;    
```

V : 노드(정점)의 개수

E : 간선의 개수

s : 시작 노드(출발점)

Graph : 가중치 그래프

D : 출발점 s로부터 (n-1)개의 점까지 각각 최단 거리(Distance)

check : 노드를 방문했는지 확인



#### Input

```java
Scanner sc = new Scanner(System.in);
System.out.printf("노드 개수 입력 : ");
V = sc.nextInt();                           
System.out.printf("간선 개수 입력 : ");
E = sc.nextInt();
System.out.printf("시작 노드 입력 : ");
s = sc.nextInt();                         

Graph = new int[V + 1][V + 1];
System.out.println("노드 노드 가중치 순으로 입력하세요");
for (int i = 0; i < E; i++) {
    input(sc.nextInt(), sc.nextInt(), sc.nextInt());   
}
```

- 총 정점의 개수, 총 간선의 개수, 출발점을 차례로 입력한다.
- 그래프의 정점을 연결해주며 가중치(거리)를 입력해준다. *첫 정점의 시작은 1부터이다.*

```java
static void input(int i, int j, int w) { 
    Graph[i][j] = w;                  
    Graph[j][i] = w;                  
}
```

- 무방향 그래프이므로, 두 개의 정점을 연결해주며 동일한 가중치를 대입하여 준다.



#### 배열 D의 초기화, 출발점 설정

```java
D = new int[V + 1];
for (int i = 1; i < V + 1; i++) {
    D[i] = Integer.MAX_VALUE;         
}
```

- 배열 D를 ∞ 으로 초기화시킨다.

```java
D[s] = 0;                            
check = new boolean[V + 1];
check[s] = true;                     
```

- s는 출발점으로, 거리를 0으로 설정
- 출발점을 방문했으므로 해당 점에 방문 표시를 true로 설정



#### 최단 거리인 정점, 인덱스 값 찾기 - findindex

```java
static int findindex() {

    int index = 0;
    for (int i = 1; i < V + 1; i++) {
        int min = Integer.MAX_VALUE;
        index = 0;
        for (int j = 1; j < V + 1; j++) {
            if (!check[j] && D[j] < min && D[j] != Integer.MAX_VALUE) { //방문하지 않았고, 최소 거리의 인덱스 리턴
                min = D[j];
                index = j;
            }
        }
    }
    return index;
}
```

> while-루프는 (n-1)회 수행된다.
>
> 현재까지 s로부터 최단 거리가 확정된 점들의 집합을 T라고 놓으면, V-T는 현재까지 s로부터 최단 거리가 확정되지 않은 점들의 집합이다.
>
> 따라서 V-T에 속한 각 점 v에 대해서 D[v]가 최소인 점 v(min)을 선택하고, v(min)의 최단 거리를 확정시킨다. 즉, D[v(min)] ≤ D[v], v∈V-T이다. ‘확정한다는 것’은 2가지의 의미를 갖는다.
>
> •D[vmin]이 확정된 후에는 다시 변하지 않는다.
>
> •점 vmin이 T에 포함된다.

- 방문하지 않은 점들 중, 최소 거리를 가진 정점의 인덱스 값을 리턴한다.



#### 최단 거리 찾기

```java
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
            if (!check[j] && Graph[min_index][j] != 0          //A와 연결된 방문하지 않은 노드들 중 최소 거리 선택
                    && D[min_index] + Graph[min_index][j] < D[j])
                D[j] = D[min_index] + Graph[min_index][j];
        }
    }
}
```

> V-T에 속한 점들 중 v(min)을 거쳐 감 (경유함)으로서 s로부터의 거리가 현재보다 더 짧아지는 점 w가 있으면, 그 점의 D[w]를 갱신한다.
>
> 다음 그림은 v(min)이 T에 포함된 상태를 보이고 있는데, v(min)에 인접한 점 w1, w2, w3 각각에 대해서 만일 (D[v(min)]+선분 (v,w(i))의 가중치) < D[w(i)]이면, D[w(i)] = (D[v(min)]+선분(v(min),w(i))의 가중치)로 갱신한다.

- 출발점과 연결되어 있고, 방문하지 않은 점들과의 거리들을 D로 넣어주며 초기화해준다.
- findindex로부터 리턴받은 최소 거리의 인덱스값으로 해당 정점을 방문 표시를 true로 해준다.
- 그 노드와 연결되어 있고, 방문하지 않은 점들 중 최소 거리를 선택한다.

---

### 입력 & 출력 확인해보기

정점 번호

1. 서울
2. 천안
3. 논산
4. 대전
5. 광주
6. 부산
7. 대구
8. 포항
9. 강릉
10. 원주

![image-20210408120808690](C:\Users\82106\AppData\Roaming\Typora\typora-user-images\image-20210408120808690.png)

##### 입력

![image-20210408121958682](C:\Users\82106\AppData\Roaming\Typora\typora-user-images\image-20210408121958682.png)

##### 출력

![image-20210408121353148](C:\Users\82106\AppData\Roaming\Typora\typora-user-images\image-20210408121353148.png)

