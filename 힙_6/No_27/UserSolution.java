package 힙_6.No_27;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

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

