package Heap_6.No_27;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;
/*
9416. Social Media

[문제 설명]
Social media 에서 사용되는 몇 가지 기능을 구현해보자.
1. 게시글을 등록한다. 각 게시글들은 등록되는 시점의 timestamp 를 가지고 있다.
2. 다른 사용자를 “follow” 한다. “follow”를 하게 되면, 그 사용자의 게시글을 볼 수 있다.
3. 특정 게시글에 “like”를 추가한다.
4. 특정 사용자를 기준으로 자신이 게시한 글과 자신이 “follow” 한 사용자의 게시글 중 우선 순위가 높은 글부터 내림차순으로 최대 10 개의 게시글을 보여준다.

각 게시글의 우선순위를 계산하는 방법은 다음과 같다.

1. 게시된 지 1,000 초 이내인 게시글이 그렇지 않은 게시글보다 우선순위가 높다.
2. 게시된 지 1,000 초 이내인 게시글들 중에는 “like” 가 많은 게시글의 우선순위가 높다.
3. 게시된 지 1,000 초 이내이면서 “like” 의 개수가 같은 게시글들 중에는 “timestamp” 가 높은 게시글의 우선순위가 높다.
4. 게시된 지 1,000 초를 초과한 게시글의 경우, “timestamp” 가 높은 게시글의 우선순위가 높다.

다음은 구현해야 할 API이다.

void init(int N)
각 testcase 시작 시 초기화를 위해 1번 호출된다.

Parameters
N: 사용자 수 (2 ≤ N ≤ 1,000)

void follow(int uID1, int uID2, int timestamp)
“uID1” 사용자가 “uID2” 사용자를 “follow” 한다.
“uID1” 사용자는 “uID2” 사용자의 모든 게시글을 볼 수 있다.

Parameters
uID1, uID2 : 사용자의 id (1 ≤ uID1, uID2 ≤ N)
timestamp : 현재 시점의 “timestamp” (1 ≤ timestamp ≤ 100,000)

void makePost(int uID, int pID, int timestamp)
“uID” 사용자가 “pID” 게시글을 게시한다.

Parameters
uID : 사용자의 ID (1 ≤ uID ≤ N)
pID : 게시글의 ID ( 1 부터 오름차순으로 주어진다. )
timestamp : 현재 시점의 “timestamp” (1 ≤ timestamp ≤ 100,000)

void like(int pID, int timestamp)
“pID” 게시글에 “like” 를 1 번 추가 한다.
“pID” 는 makePost() 에서 전달되었던 값으로만 주어 진다.

Parameters
pID : “like” 를 추가할 게시글의 pID
timestamp : 현재 시점의 “timestamp” (1 ≤ timestamp ≤ 100,000)

void getFeed(int uID, int timestamp, int pIDList[])
현재 “timestamp” 를 기준으로 “uID” 사용자에게 보여지는 최대 10 개의 게시글의 “pID” 들을 찾아 우선순위의 내림차순으로 “pIDList[]” 배열에 저장하여 반환 한다.

Parameters
uID : 사용자의 id (1 ≤ uID ≤ N)
timestamp : 현재 시점의 timestamp (1 ≤ timestamp ≤ 100,000)
pIDList[] : 보여지는 게시글의 pID 들을 저장하는 배열

[제약사항]
1.     사용자 수 N 은 2 이상 1,000 이하의 정수이다. (2 ≤ N ≤ 1,000)
2.     timestamp 는 1 에서 시작하고 최대 100,000 까지 증가한다.
3.     모든 함수들은 timestamp 오름차순으로 호출된다.
4.     모든 게시물들의 timestamp 는 서로 다르다.
5.     follow(), makePost(), like() 함수의 호출 횟수는 각각 100,000 회 이하이다.
6.     getFeed() 함수의 호출 횟수는 5,000 회 이하이다.
7.     특정 사용자가 한번 “follow” 한 사용자를 다시 “follow” 하는 호출은 발생하지 않는다.

[입출력]
입출력은 제공되는 Main 부분의 코드에서 처리하므로 User Code 부분의 코드에서는 별도로 입출력을 처리하지 않는다.
sample input에 대한 정답 출력 결과는 아래와 같은 형태로 보여진다.

주요 아이디어
1. priorityQueue 를 2개 써야 한다는 것을 제외하면 특별한 것이 없다.
 */
class UserSolution {

    Post[] posts;
    Post[][] u_posts;

    // 각 u_posts 의 length 저장
    int[] lengths;

    ArrayList<Integer>[] follower;

    public void init(int N) {

        // time stamp 의 범위가 1~100000 이기에 이 이상의 post 는 만들어지지 않는다.
        posts = new Post[100001];

        // 1000 번 이상의 post 는 우선순위에 들기 힘들다.
        u_posts = new Post[N + 1][1000];

        lengths = new int[N + 1];

        follower = new ArrayList[N + 1];

        // 모든 user 는 자기 자신을 follow 하는 것과 같다.
        for (int i = 1; i <= N; i++) {
            follower[i] = new ArrayList<>();
            follower[i].add(i);
        }
    }

    public void follow(int uID1, int uID2, int timestamp) {
        follower[uID1].add(uID2);
    }

    public void makePost(int uID, int pID, int timestamp) {

        posts[pID] = new Post(pID, timestamp);

        u_posts[uID][lengths[uID] % 1000] = posts[pID];

        // idx 1000을 넘어가지 않게 조정
        lengths[uID]++;
    }

    public void like(int pID, int timestamp) {

        posts[pID].like++;
    }

    public void getFeed(int uID, int timestamp, int[] pIDList) {

        // timestamp 차이가 1000초 이상
        PriorityQueue<Post> pq_1 = new PriorityQueue<>(new Comparator<Post>() {
            @Override
            public int compare(Post o1, Post o2) {
                return o2.timestamp - o1.timestamp;
            }
        });

        // timestamp 차이가 1000 이하
        PriorityQueue<Post> pq_2 = new PriorityQueue<>(new Comparator<Post>() {
            @Override
            public int compare(Post o1, Post o2) {

                if(o1.like == o2.like) return o2.timestamp - o1.timestamp;
                return o2.like - o1.like;
            }
        });

        // follow 한 사람들의 계시글을 priority queue 에 추가한다.
        for (int f_idx : follower[uID]) {
            for (int i = 0; i < Math.min(lengths[f_idx], 1000); i++) {

                if(timestamp - u_posts[f_idx][i].timestamp > 1000) pq_1.add(u_posts[f_idx][i]);
                else pq_2.add(u_posts[f_idx][i]);
            }
        }

        int i=0;
        while(!pq_2.isEmpty() && i<10) pIDList[i++] = pq_2.poll().pID;
        while(!pq_1.isEmpty() && i<10) pIDList[i++] = pq_1.poll().pID;
    }
}

class Post{

    int pID;
    int timestamp;
    int like;

    public Post(int pID, int timestamp) {
        this.pID = pID;
        this.timestamp = timestamp;
        this.like = 0;
    }
}

