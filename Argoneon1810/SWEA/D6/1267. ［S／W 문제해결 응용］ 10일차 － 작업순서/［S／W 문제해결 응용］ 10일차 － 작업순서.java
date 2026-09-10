import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
import java.util.Collections;
import java.util.Deque;
import java.util.ArrayDeque;
import java.util.List;
import java.util.ArrayList;

class Solution
{
	static final boolean RECOMPUTE_RANK = false;
	
	// 단일 테스트 케이스의 상태를 관리하기 위한 인스턴스 변수들이다.
	// 이전 케이스와의 상태 오염을 방지하기 위해, 매 테스트 케이스마다 객체를 새로 생성하여 사용해야 한다.
	int V, E;
	List<List<Integer>> outgoingEdges, incomingEdges;
	int[] rank;

	// 연쇄적인 간선 재연결을 위해 전달받는 다음 유력 후보 노드의 정보이다.
	// nextType이 1이면 자녀 노드(하향 이동)를, 2면 부모 노드(상향 이동)를 우선 검사한다.
	// nextNode가 0이면 지정된 대기 후보가 없음을 의미한다.
	int nextNode = 0;
	int nextType = 0;

	// 랭크 부분 갱신 시, 재계산이 필요한 노드들을 추적하기 위한 큐와 방문 표시 배열이다.
	// 간선이 변경되어 랭크 변화의 여지가 있는 노드들만 이 큐에 담아 국소적으로 연산한다.
	Deque<Integer> dirtyQueue = new ArrayDeque<>();
	boolean[] dirtyFlag;

	// ============== GRAPH HELPERS ====================

	// 두 노드 사이의 순서를 정하는 함수
	// 재연결이 만드는 간선의 방향은 이 순서로 결정된다.

	// 고정 랭크 방법용. 랭크가 같으면 노드 번호로 상하위 판정을 수행한다.
	boolean precedesByRankThenIndex(int a, int b) {
		return rank[a] != rank[b] ? rank[a] < rank[b]
								  : a < b;
	}
	// 재계산 랭크 방법용. 랭크만 본다. 랭크가 같으면 앞서지 않는 것으로 취급한다.
	boolean precedesByRankOnly(int a, int b) {
		return rank[a] < rank[b];
	}
	// 지금 켜진 방법이 쓰는 비교
	boolean precedes(int a, int b) {
		return RECOMPUTE_RANK ? precedesByRankOnly(a, b)
							  : precedesByRankThenIndex(a, b);
	}

	// 간선 재연결 함수들
	// 박싱 언박싱 비용 절감용 배열
	Integer[] boxed;
	void attach(int from, int to) {
		// 조기 탈출 조건 (그림에 있는 '흡수' 조건)
		if (outgoingEdges.get(from).contains(boxed[to])) return;
		outgoingEdges.get(from).add(boxed[to]);
		incomingEdges.get(to).add(boxed[from]);
	}

	void detach(int from, int to) {
		outgoingEdges.get(from).remove(boxed[to]);
		incomingEdges.get(to).remove(boxed[from]);
	}

	// 고정 랭크에서는 부분 갱신을 하지 않으므로 표시 자체를 남기지 않는다
	void markDirty(int v) {
		if (!RECOMPUTE_RANK) return;
		if (dirtyFlag[v]) return;
		dirtyFlag[v] = true;
		dirtyQueue.add(v);
	}

	// ============== GRAPH HELPERS ENDS ====================
	

	// ============== 유효성 조건 ================= 
	
	boolean isValid(int[] edgeFrom, int[] edgeTo, List<Integer> order) {
		try {
			// 새 그래프가 주어진 그래프보다 많은 V를 가지고 있어서는 안됨
		    if (order.size() != V)
		        throw new IllegalStateException(
		        		"Output Count Mismatch: " +
        				"Expected " + V + ", but " + 
        				order.size() + " was given."
				);
		    // 새 그래프 안에 어떤 노드가 한번 이상 등장해서는 안됨
		    int[] position = new int[V + 1];
		    java.util.Arrays.fill(position, -1);
		    for (int i=0; i<V; ++i) {
		        int v = order.get(i);
		        if (position[v] >= 0)
		        	throw new IllegalStateException(
		        			"Duplicate: Vertex " + v +
		        			" occured more than once."
        			);
		        position[v] = i;
		    }
		    // 원본 간선 from-to가 새로 형성된 edgeFrom-edgeTo 사이에서
		    // 여전히 순서대로 보존되어 있다면 유효한 그래프
		    for (int e=0; e<E; ++e)
		        if (position[edgeFrom[e]] >= position[edgeTo[e]])
		            throw new IllegalStateException(
		            		"Inverse: Edge " + 
            				edgeFrom[e] + "->" + edgeTo[e] + 
            				" is not in order."
            		);
			return true;
		}
		catch (IllegalStateException e) {
			return false;
		}
	}
	
	// ============== 유효성 조건 끝 ================= 

	// 재연결 작업이 모두 끝나 선형화가 완료된 그래프를 순회하여 최종 결과를 추출한다.
	// 1. 진입 차수가 0인 노드들을 모두 찾는다.
	// 2. 이 시작점들을 랭크 우선, 노드 번호 차선으로 오름차순 정렬하여 순서를 확정한다.
	// 3. 정렬된 각 시작점부터 출발하여, 다음 간선을 따라가며 순서대로 결과 리스트에 담는다.
	// (재연결이 완전히 끝난 상태이므로 각 노드의 자녀는 최대 1개뿐이며, 일직선(Chain) 형태를 띤다.)
	List<Integer> readChains() {
		List<Integer> heads = new ArrayList<>();
		for (int v=1; v<=V; ++v)
			if (incomingEdges.get(v).isEmpty())
				heads.add(v);
		final int[] rankSnapshot = rank;
		Collections.sort(
				heads, 
				(x, y) -> {
					return rankSnapshot[x] != rankSnapshot[y]
							? rankSnapshot[x] - rankSnapshot[y]
							: x - y;
				}
		);
		List<Integer> order = new ArrayList<>(V);
		for (int head : heads) {
			int current = head;
			while (current >= 0) {
				order.add(current);
				List<Integer> next = outgoingEdges.get(current);
				current = next.isEmpty() ? -1 : next.get(0);
			}
		}
		return order;
	}

	// 한 번 계산된 랭크 값을 기억하여 중복 연산을 방지하는 메모이제이션 기반 랭킹 함수
	// 특정 노드 v의 랭크를 재귀적으로 계산하여 반환한다.
	// 노드의 랭크는 부모 노드들의 랭크 중 최댓값에 1을 더한 값으로 정의된다.
	// 부모의 랭크가 아직 계산되지 않았다면, 상위 노드로 거슬러 올라가며 먼저 값을 구한다.
	// 한 번 계산이 완료된 노드는 rank에 값을 저장하고 resolved로 표시하여, 
	// 이후 다시 조회할 때는 연산 없이 기록된 값을 즉시 반환한다.
	boolean[] resolved;
	int rankOf(int v) {
		if (resolved[v]) return rank[v];
		resolved[v] = true;
		int highest = 0;
		for (int p : incomingEdges.get(v)) {
			int parentRank = rankOf(p) + 1;
			if (parentRank > highest) highest = parentRank;
		}
		rank[v] = highest;
		return highest;
	}

	// 모든 정점(1~V)에 대해 랭크 계산을 수행한다.
	// (메모이제이션 적용으로 전체 시간 복잡도는 O(V + E) 보장)
	void computeRank() {
		rank = new int[V + 1];
		resolved = new boolean[V + 1];
		for (int v = 1; v <= V; ++v)
			rankOf(v);
	}

	// naming convention: NodeFrom|head|---->|tail|NodeTo

	// reconnection type 1: head downwarding
	// found rule:
	// 같은 부모를 공유하는 두 서로 다른 간선 사이에서
	// 한쪽 간선의 head를 다른 간선의 tail에 붙여도
	// 기존 그래프의 실행 순서가 위반되지 않는다.
	boolean headDownwardingAt(int a) {
		List<Integer> children = outgoingEdges.get(a);
		if (children.size() < 2) return false;
		int first = -1, second = -1;
		// 랭크 제약:
		// 재연결로 생기는 간선은 상위 랭크를 가리키면 안된다.
		// 후보 중 순서가 먼저인 두 대상을 각각 first, second로 하고
		// 새 간선을 언제나 first에서 second로 만들도록 구현
		// 구조적으로 규칙을 위반할 수 없도록 되어 있다
		for (int c : children) {
			if (first < 0 || precedes(c, first)) {
				second = first;
				first = c; 
			}
			else if (second < 0 || precedes(c, second)) 
				second = c;
		}
		detach(a, second);
		attach(first, second);
		// first가 자녀를 하나 더 가졌으므로 다음 후보가 된다
		nextNode = first;
		nextType = 1;
		markDirty(second);
		return true;
	}

	// reconnection type 2: tail upwarding
	// found rule:
	// 같은 자녀를 공유하는 두 서로 다른 간선 사이에서
	// 한쪽 간선의 tail을 다른 간선의 head에 붙여도
	// 기존 그래프의 실행 순서가 위반되지 않는다.
	boolean tailUpwardingAt(int e) {
		List<Integer> parents = incomingEdges.get(e);
		if (parents.size() < 2) return false;
		int first = -1, second = -1;
		// 랭크 제약
		for (int d : parents) {
			if (first < 0 || precedes(d, first)) {
				second = first;
				first = d;
			}
			else if (second < 0 || precedes(d, second)) 
				second = d;
		}
		detach(first, e);
		attach(first, second);
		// second가 부모를 하나 더 가졌으므로 다음 후보가 된다
		nextNode = second;
		nextType = 2;
		markDirty(e);
		markDirty(second);
		return true;
	}

	// 직전 재연결 작업이 발생한 위치에서 연이어 재연결을 시도한다.
	// Type 1 재연결 시 자녀를 추가로 얻은 first 노드, 
	// Type 2 재연결 시 부모를 추가로 얻은 second 노드가 다음 재연결의 유력한 후보가 된다.
	// 이 후보를 우선 검사하므로 매번 전체 정점을 처음부터 다시 스캔할 필요가 없다.
	// 후보 노드에서 더 이상 재연결이 불가능해지면 false를 반환하여 전체 스캔으로 제어권을 넘긴다.
	boolean reconnectAtCandidate() {
		int node = nextNode;
		if (node == 0) return false;
		boolean done = nextType == 1 ? headDownwardingAt(node)
									 : tailUpwardingAt(node);
		if (!done) nextNode = 0;
		return done;
	}

	// 저장된 유력 후보가 없거나, 후보 노드에서 재연결에 실패했을 때 정점 전체를 스캔한다.
	// Type 1(head downwarding)을 모든 정점에 대해 먼저 시도하며,
	// Type 1으로 갱신할 수 있는 경우가 모두 소진되었을 때만 Type 2(tail upwarding)로 넘어간다.
	// (어느 쪽을 먼저 처리하든 알고리즘의 유효성은 동일하게 보장되며, 이는 결과의 최종 형태를 결정하는 구현상의 선택이다.)
	boolean reconnectByFullScan() {
		for (int a=1; a<=V; ++a)
			if (headDownwardingAt(a)) return true;
		for (int e=1; e<=V; ++e)
			if (tailUpwardingAt(e)) return true;
		return false;
	}

	// 단락 평가를 활용하여 1회의 재연결을 수행한다.
	// 앞의 reconnectAtCandidate()가 성공(true)하면, 
	// 뒤의 reconnectByFullScan()은 호출되지 않고 즉시 반환된다.
	// 이를 통해 유력한 후보가 있는 동안에는 값비싼 전체 탐색을 수행하지 않도록 방어한다.
	boolean reconnectOnce() {
		return reconnectAtCandidate() || reconnectByFullScan();
	}

	// 간선 재연결 시 한 번에 하나의 간선만 변경되므로 랭크 변화도 국소적으로만 발생한다.
	// 따라서 전체를 다시 계산하지 않고,
	// 부모가 바뀐 노드만 dirtyQueue에 담아 부분 갱신한다.
	//
	// 간선 추가 및 삭제에 따라 도착 노드의 랭크는 오르거나 내릴 수 있다.
	// 간선이 추가될 때는 (새 부모 랭크 + 1)이 기존 랭크보다 클 때만 랭크가 오르며,
	// 간선이 제거될 때는 기존 최대 랭크를 제공하던 부모가 사라졌을 수 있으므로
	// 간선이 더해지거나 빼지는 것 만으로는 새 랭크를 바로 알 수 없다.
	// 
	// 따라서 본 코드는 간선 추가와 제거 상황을 굳이 구분하지 않고,
	// 큐에서 노드를 꺼낼 때마다 해당 노드의 '모든 부모'를 다시 확인하여 랭크를 일괄 재계산한다.
	// 
	// 재계산 결과 랭크가 실제로 변경된 노드의 자식들만 큐에 추가하므로,
	// 변화가 멈추는 지점에서 연산 전파도 자연스럽게 종료된다.
	void patchRank() {
		while (!dirtyQueue.isEmpty()) {
			int v = dirtyQueue.poll();
			dirtyFlag[v] = false;
			int updated = 0;
			for (int p : incomingEdges.get(v))
				if (rank[p] + 1 > updated) updated = rank[p] + 1;
			if (updated == rank[v]) continue;
			rank[v] = updated;
			for (int c : outgoingEdges.get(v))
				markDirty(c);
		}
	}

	List<Integer> linearize() {
		// rank는 처음 한번은 꼭 계산 해야한다.
		computeRank();
		dirtyFlag = new boolean[V + 1];
		while (reconnectOnce()) {
			// 완전히 정렬될 때 까지 reconnection
			// 
			// 랭크 재계산을 수행하면 다음 Pro와 Con을 가진다
			// Pro
			// - 동렬간 재연결 시도에서 예외를 두지 않아도 된다.
			// - 계산 과정이 직관적이다.
			// Con
			// - 재계산 비용이 발생한다. 전체를 다시 계산하면 O(V+E)이지만
			//   부모가 바뀐 노드에서 시작해 값이 실제로 변하는 범위까지만 퍼뜨리면
			//   대부분의 재연결에서 몇 개의 노드만 손보고 끝난다.
			//
			// 예외만 두면 전체적인 꼴은 동일하므로 일단 둘 다 지원하게 둔다
			if (RECOMPUTE_RANK)
				patchRank();
		}
		return readChains();
	}

	String solve(BufferedReader br) throws IOException {
		StringTokenizer st = new StringTokenizer(br.readLine());
		V = Integer.parseInt(st.nextToken());
		E = Integer.parseInt(st.nextToken());
		// 입력 수령 끝
		// 박싱 언박싱 비용 절감용 배열 먼저 채우기
		boxed = new Integer[V + 1];
		for (int v=0; v<=V; ++v) boxed[v] = v;
		// 출발-도착 꼴로 주어지는데
		// 그래프 위에서 노드 사이를 오가려면
		// V로부터 뻗는 V+1 뿐만 아니라
		// V로 향하는 모든 V-1들도 알아야 하므로
		// incoming과 outgoing을 둘 다 기록
		outgoingEdges = new ArrayList<>(V + 1);
		incomingEdges = new ArrayList<>(V + 1);
		for (int v=0; v<=V; ++v) {
			outgoingEdges.add(new ArrayList<Integer>());
			incomingEdges.add(new ArrayList<Integer>());
		}
//		int[] edgeFrom = new int[E], edgeTo = new int[E]; // only for validity check
		st = new StringTokenizer(br.readLine());
		for (int e=0; e<E; ++e) {
			int from, to;
			from = Integer.parseInt(st.nextToken());
			to = Integer.parseInt(st.nextToken());
//		    edgeFrom[e] = from;	// only for validity check
//		    edgeTo[e] = to;		// only for validity check
			outgoingEdges.get(from).add(to);
			incomingEdges.get(to).add(from);
		}
		// 계산
		List<Integer> result = linearize();
//		// 검증 (필요하면 쓰기)
//		boolean valid = isValid(edgeFrom, edgeTo, result);
//		if (!valid) 
//			return "INVALID";
		// 출력
		StringBuilder sb = new StringBuilder();
		for (int i=0; i<result.size(); ++i) {
			if (i > 0) sb.append(' ');
			sb.append(result.get(i));
		}
		return sb.toString();
	}

	static void solve() throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int T;
//		T = Integer.parseInt(br.readLine().trim());
		T = 10;
		StringBuilder sb = new StringBuilder();
		// 케이스마다 객체를 새로 만든다. 이전 케이스의 그래프와 랭크가 남으면 안된다.
		for(int test_case = 1; test_case <= T; test_case++)
			sb.append("#").append(test_case)
			  .append(' ').append(new Solution().solve(br))
			  .append('\n');
		System.out.print(sb.toString());
	}

	public static void main(String args[]) throws Exception
	{
		solve();
	}
}