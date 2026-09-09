import java.util.*;
 
// 핵심 아이디어
// 주문 ID가 최대 10억이지만 주문은 최대 20,000개뿐이다.
// 내부적으로 번호를 새로 매기고 HashMap(주문 ID, 내부번호)으로 관리한다.
// 내부번호가 곧 접수 순서라서, 오름차순 탐색만으로 가장 먼저 받은 주문이 자동으로 처리된다.(정렬 필요 X)
class UserSolution {
    int N;
    int total;
    int alive;
 
    int[] orderID;
    int[][] remain;
    int[][] supplied;
    int[] remainTotal;
    int[] state;
 
    HashMap<Integer, Integer> orderIdx;
 
    public void init(int N) {
        this.N = N;                         // 음료 종류 개수 (1 ~ N번)
 
        total = 0;                          // 지금까지 받은 주문 개수
        alive = 0;                          // 남아있는 주문 개수
 
        orderID = new int[20000];           // orderID[내부번호] = 주문 ID
        remain = new int[20000][N + 1];     // remain[내부번호][음료] = 아직 남아있는 음료 개수
        supplied = new int[20000][N + 1];   // supplied[내부번호][음료] = 이미 만들어진 음료 개수
        remainTotal = new int [20000];      // remainTotal[내부번호] = 남은 음료 총 개수
        state = new int[20000];             // state[내부번호] = 0 진행중 / 1 완료 / 2 취소
 
        orderIdx = new HashMap<>();           // 주문 ID, 내부번호
    }
 
    // 새 주문을 받는다. 내부번호는 0부터 순서대로 매기고 재사용하지 않는다.
    public int order(int mID, int M, int mBeverages[]) {
        orderID[total] = mID;
        remainTotal[total] = M;
        state[total] = 0;
 
        // 같은 음료가 여러 개 올 수 있으므로 대입이 아니라 증가.
        for (int i = 0; i < M; i++) {
            remain[total][mBeverages[i]]++;
        }
 
        orderIdx.put(mID, total);
         
        alive++;
        total++;
 
        return alive;
    }
 
    // 음료 하나를 그 음료가 필요한 가장 먼저 받은 주문에 배치한다.
    // 내부번호 오름차순이 곧 접수 순서라 처음 찾은 주문이 정답이다.
    public int supply(int mBeverage) {
        for (int i = 0; i < total; i++) {
            // state가 더 잘 걸러지므로 먼저 검사한다.
            if (state[i] != 0) continue;
            if (remain[i][mBeverage] == 0) continue;
 
            remainTotal[i]--;
            remain[i][mBeverage]--;
            supplied[i][mBeverage]++;
 
            // 남은 음료 총 개수가 0이 되면 주문이 완료된 것.
            if (remainTotal[i] == 0) {
                alive--;
                state[i] = 1;
            }
 
            return orderID[i];
        }
 
        return -1;
    }
 
    // 주문을 취소하고, 이미 배치돼 있던 음료들을 다른 주문에 재배치한다.
    public int cancel(int mID) {
        int idx = orderIdx.get(mID);
 
        if (state[idx] == 1) return 0;
        if (state[idx] == 2) return -1;
				
        int count = remainTotal[idx];
 
        // 재배치 전에 상태를 먼저 바꿔야 한다.
        // 그러면 아래 supply가 이 주문을 걸러내므로 자기 자신에게 되돌아오지 않는다.
        alive--;
        state[idx] = 2;
 
        // 우선 바깥쪽 반복문에서 음료 종류의 개수만큼 반복한다.
        // 그리고 안쪽 반복문에서 해당 종류의 음료 개수만큼 반복한다.
        // 재배치 과정은 배치 과정과 동일하므로 supply를 그대로 호출한다.
        for (int i = 1; i <= N; i++) {
            for (int j = 0; j < supplied[idx][i]; j++) {
                supply(i);
            }
        }
 
        return count;
    }
 
    public int getStatus(int mID) {
        int idx = orderIdx.get(mID);
 
        if (state[idx] == 1) return 0;
        if (state[idx] == 2) return -1;
         
        return remainTotal[idx];
    }
 
    // 남은 음료가 많은 순, 같으면 먼저 받은 순으로 최대 5개.
    // 남은 개수가 1~10 범위로 작으므로 정렬하지 않고 큰 값부터 훑는다.
    Solution.RESULT hurry() {
        Solution.RESULT res = new Solution.RESULT();
 
        res.cnt = 0;
 
        // 주문 하나의 음료는 최대 10개(M ≤ 10)라 10부터 내려온다.
        for (int i = 10; i > 0; i--) {
            // 내부번호 오름차순 = 접수 순서라 같은 개수끼리는 먼저 받은 주문이 앞에 온다.
            for (int j = 0; j < total; j++) {
                if (state[j] != 0) continue;
                if (remainTotal[j] != i) continue;
                 
                res.IDs[res.cnt] = orderID[j];
                res.cnt++;
 
                if (res.cnt == 5) return res;
            }
        }
 
        return res;
    }
}
