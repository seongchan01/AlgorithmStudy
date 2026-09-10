// 핵심 아이디어
// 알고리즘이 아니라 규칙을 정확히 옮기는 문제.
// 설치 조건 네 개(연속 세 열 / 결합판 일치 / 높이 초과 금지 / 옆면 접촉)를 그대로 검사한다.
// check와 add는 판정 로직이 같고, 세는지 설치하는지만 다르다.
// pourIn은 층별 빈칸을 아래부터 누적해 물이 부족해지는 지점을 찾는다.
class UserSolution {
    int N, mWidth, mHeight;
    int[] ID;
    int[][] length;
    int[][] shape;
    int[] order;

    public void init(int N, int mWidth, int mHeight, int mIDs[], int mLengths[][], int mUpShapes[][]) {
        this.N = N;
        this.mWidth = mWidth;
        this.mHeight = mHeight;

        ID = new int[N];
        length = new int[N][mWidth];
        shape = new int[N][mWidth];
        order = new int[N];

        for (int i = 0; i < N; i++) {
            ID[i] = mIDs[i];
            for (int j = 0; j < mWidth; j++) {
                length[i][j] = mLengths[i][j];
                shape[i][j] = mUpShapes[i][j];
            }
        }

        // ID 배열은 순서가 뒤죽박죽이므로 방문 순서만 따로 만든다.
        // ID를 직접 정렬하면 length, shape와 짝이 깨진다.
        for (int i = 0; i < N; i++) order[i] = i;

        // 선택 정렬
        for (int i = 0; i < N - 1; i++) {
            for (int j = i + 1; j < N; j++) {
                if (ID[order[i]] > ID[order[j]]) {
                    int tmp = order[i];
                    order[i] = order[j];
                    order[j] = tmp;
                }
            }
        }
    }

    // 설치 가능한 위치의 총 개수를 센다. 어항 상태는 건드리지 않는다.
    // 우선순위를 따지지 않으므로 어항 순서는 무관하다.
    public int checkStructures(int mLengths[], int mUpShapes[], int mDownShapes[]) {
        int count = 0;

        for (int i = 0; i < N; i++) {
            // j, j+1, j+2 세 열을 쓰므로 j는 mWidth-3 까지
            for (int j = 0; j <= mWidth - 3; j++) {
                boolean ok = true;

                // k = 세 구조물 중 몇 번째, j+k = 실제 열 번호
                // continue로는 실패를 바깥으로 전달할 수 없어 ok 플래그를 쓴다.
                // 결합판은 종류가 4개라 잘 걸러지므로 높이보다 먼저 검사한다.
                for (int k = 0; k < 3; k++) {
                    if (shape[i][j + k] != mDownShapes[k]) {
                        ok = false;
                        break;
                    }
                    if (length[i][j + k] + mLengths[k] > mHeight) {
                        ok = false;
                        break;
                    }
                }

                // 새 구조물끼리 옆면이 최소 한 칸 맞닿아야 한다.
                // lo = 시작 층, hi = 끝 층. 한쪽이 완전히 위에 떠 있으면 안 닿는 것.
                if (ok) {
                    int lo0 = length[i][j] + 1, hi0 = length[i][j] + mLengths[0];
                    int lo1 = length[i][j + 1] + 1, hi1 = length[i][j + 1] + mLengths[1];
                    int lo2 = length[i][j + 2] + 1, hi2 = length[i][j + 2] + mLengths[2];

                    if (lo0 > hi1 || lo1 > hi0) ok = false;
                    if (lo1 > hi2 || lo2 > hi1) ok = false;
                }

                if (ok) count++;
            }
        }

        return count;
    }

    // 우선순위 1위 한 곳에만 설치한다.
    // order(ID 오름차순)로 돌고 열은 왼쪽부터 보므로, 처음 찾은 곳이 곧 1위다.
    public int addStructures(int mLengths[], int mUpShapes[], int mDownShapes[]) {
        for (int idx = 0; idx < N; idx++) {
            int i = order[idx];

            for (int j = 0; j <= mWidth - 3; j++) {
                boolean ok = true;

                for (int k = 0; k < 3; k++) {
                    if (shape[i][j + k] != mDownShapes[k]) {
                        ok = false;
                        break;
                    }
                    if (length[i][j + k] + mLengths[k] > mHeight) {
                        ok = false;
                        break;
                    }
                }

                if (ok) {
                    int lo0 = length[i][j] + 1, hi0 = length[i][j] + mLengths[0];
                    int lo1 = length[i][j + 1] + 1, hi1 = length[i][j + 1] + mLengths[1];
                    int lo2 = length[i][j + 2] + 1, hi2 = length[i][j + 2] + mLengths[2];

                    if (lo0 > hi1 || lo1 > hi0) ok = false;
                    if (lo1 > hi2 || lo2 > hi1) ok = false;
                }

                // 설치 = 높이를 늘리고 맨 위 결합판을 새 구조물 것으로 바꾸는 것.
                if (ok) {
                    for (int k = 0; k < 3; k++) {
                        length[i][j + k] += mLengths[k];
                        shape[i][j + k] = mUpShapes[k];
                    }
                    // 열 위치는 0-based 인덱스가 아니라 1부터 세므로 +1
                    return ID[i] * 1000 + (j + 1);
                }
            }
        }

        return 0;
    }

    // 물을 가장 높이 채울 수 있는 어항 하나를 찾는다. 실제로 채우지는 않는다.
    // 어항마다 결과가 달라 비교가 필요하므로 중간에 끝내지 않고 끝까지 돈다.
    public Solution.Result pourIn(int mWater) {
        Solution.Result ret = new Solution.Result();
        ret.ID = ret.height = ret.used = 0;

        for (int idx = 0; idx < N; idx++) {
            int i = order[idx];
            int sum = 0;
            int h = 0;

            // 물이 든 층은 빈칸 없이 꽉 차야 하므로 1층부터 누적해 올라간다.
            for (int l = 1; l <= mHeight; l++) {
                int count = 0;
                for (int j = 0; j < mWidth; j++) {
                    if (length[i][j] < l) count++;    // 그 열이 이 층까지 안 닿으면 빈칸
                }

                // 이 층까지 채울 물이 부족하면 위층은 더 부족하므로 멈춘다.
                if (sum + count > mWater) break;

                // 물을 실제로 쓴 층만 인정한다.
                // 아래층이 구조물로 꽉 차 있으면 sum이 0이라 높이로 안 세어진다.
                sum += count;
                if (sum > 0) h = l;
            }

            // 높이가 크면 갱신, 같으면 물을 많이 쓴 쪽.
            // 둘 다 같으면 갱신하지 않아 먼저 나온 쪽(ID 작은 쪽)이 남는다.
            if (h > ret.height || (h == ret.height && sum > ret.used)) {
                ret.ID = ID[i];
                ret.height = h;
                ret.used = sum;
            }
        }

        return ret;
    }
}
