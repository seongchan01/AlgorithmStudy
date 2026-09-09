import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.TreeSet;

class UserSolution {

	int N;
	int seq;

	// 주문 ID별 주문 정보
	LinkedHashMap<Integer, Order> orderMap = new LinkedHashMap<>();

	// 음료 ID별 대기 주문 목록
	HashMap<Integer, LinkedHashSet<Integer>> linkedHashSet = new HashMap<>();

	// hurry용
	TreeSet<Order> hurrySet;

	class Order {
		int mID;
		int M;

		int[] beverages;
		boolean[] supplied;

		// 남은 음료 개수
		int remain;

		// 주문 들어온 순서
		int seq;

		Order(int mID, int M, int[] beverages, int seq) {

			this.mID = mID;
			this.M = M;
			this.seq = seq;
//			this.beverages = beverages;

			this.beverages = new int[M];

			for (int i = 0; i < M; i++) {
				this.beverages[i] = beverages[i];
			}

			this.supplied = new boolean[M];

			// 처음에는 모든 음료가 남아있음
			this.remain = M;
		}
	}

	public void init(int N) {

		this.N = N;
		this.seq = 0;

		orderMap = new LinkedHashMap<>();
		linkedHashSet = new HashMap<>();

		hurrySet = new TreeSet<>((o1, o2) -> {

			// remain 내림차순
			if (o1.remain != o2.remain) {
				return o2.remain - o1.remain;
			}

			// 남은 음료가 같으면 먼저 받은 주문이 앞으로
			return o1.seq - o2.seq;
		});
	}

	public int order(int mID, int M, int mBeverages[]) {

		Order order = new Order(mID, M, mBeverages, seq++);

		orderMap.put(mID, order);

		// hurry 목록에 추가
		hurrySet.add(order);

		for (int i = 0; i < M; i++) {

			int beverage = mBeverages[i];

			if (!linkedHashSet.containsKey(beverage)) {
				linkedHashSet.put(beverage, new LinkedHashSet<>());
			}

			linkedHashSet.get(beverage).add(mID);
		}

		return hurrySet.size();
	}

	public int supply(int mBeverage) {

		// 해당 음료를 원하는 주문 자체가 없음
		if (!linkedHashSet.containsKey(mBeverage)) {
			return -1;
		}

		// 해당 음료를 기다리는 주문이 없음
		if (linkedHashSet.get(mBeverage).isEmpty()) {
			return -1;
		}

		// 해당 음료를 가장 먼저 주문한 주문 ID
		int orderID = linkedHashSet.get(mBeverage).iterator().next();

		// 그 ID를 기준으로 객체 생성
		Order o = orderMap.get(orderID);

		// remain이 TreeSet 정렬 기준이기 때문에 remain을 변경하기 전에 먼저 제거
		hurrySet.remove(o);

		// 해당 주문에서 음료 하나 공급
		for (int i = 0; i < o.M; i++) {

			if (o.beverages[i] == mBeverage && !o.supplied[i]) {

				o.supplied[i] = true;

				// 남은 음료 하나 감소
				o.remain--;

				break;
			}
		}

		// 이 주문에서 같은 음료를 아직 더 필요로 하는지 확인

		boolean check = false;

		for (int i = 0; i < o.M; i++) {

			if (o.beverages[i] == mBeverage && !o.supplied[i]) {

				check = true;
				break;
			}
		}

		// 같은 음료를 더 이상 필요로 하지 않으면 해당 음료 대기목록에서 주문 삭제

		if (!check) {
			linkedHashSet.get(mBeverage).remove(orderID);
		}

		// 아직 남은 음료가 있으면 변경된 remain 기준으로 다시 TreeSet에 넣음
		if (o.remain > 0) {
			hurrySet.add(o);
		}

		return orderID;
	}

	public int cancel(int mID) {

		Order o = orderMap.get(mID);

		// 이미 취소된 주문
		if (o == null) {
			return -1;
		}

		// 취소되기 전 남은 음료 개수
		int remain = o.remain;

		// 이미 모든 음료 제조 완료
		if (remain == 0) {
			return 0;
		}

		// 취소되는 주문이므로 hurry 목록에서 제거
		hurrySet.remove(o);

		// 먼저 취소될 주문을 모든 음료 대기목록에서 제거
		for (int i = 0; i < o.M; i++) {

			int beverage = o.beverages[i];

			if (linkedHashSet.containsKey(beverage)) {
				linkedHashSet.get(beverage).remove(mID);
			}
		}

		// orderMap 에서도 제거
		orderMap.remove(mID);

		// 주문에 공급한 음료 재배치
		for (int i = 0; i < o.M; i++) {

			if (o.supplied[i]) {
				supply(o.beverages[i]);
			}
		}

		return remain;
	}

	public int getStatus(int mID) {

		Order o = orderMap.get(mID);

		// 취소된 주문
		if (o == null) {
			return -1;
		}

		// 남은 음료 개수
		return o.remain;
	}

	Solution.RESULT hurry() {

		Solution.RESULT res = new Solution.RESULT();

		res.cnt = 0;

		// 트리셋으로 정렬되어 있는거 가져오기
		for (Order o : hurrySet) {

			res.IDs[res.cnt] = o.mID;
			res.cnt++;

			if (res.cnt == 5) {
				break;
			}
		}

		return res;
	}
}
