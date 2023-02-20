package 힙_6.No_27;

import java.util.ArrayList;

class UserSolution {

    Post[] posts;
    Post[][] u_posts;

    // 각 u_posts 의 length 저장
    int[] lengths;

    ArrayList<Integer>[] follower;

    // priority queue
    Post[] pq;
    int last_idx;

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

        u_posts[uID][lengths[uID]++] = posts[pID];

        // idx 1000을 넘어가지 않게 조정
        lengths[uID] %= 1000;
    }

    public void like(int pID, int timestamp) {

        posts[pID].like++;
    }

    public void getFeed(int uID, int timestamp, int[] pIDList) {

        // 저장할 장소
        pq = new Post[100001];
        last_idx = 0;

        // follow 한 사람들의 계시글을 priority queue 에 추가한다.
        for (int f_idx : follower[uID]) {
            for (int i = 0; i < lengths[f_idx]; i++) {
                insert(u_posts[f_idx][i], timestamp);
            }
        }

        for (int i = 0; i < 10; i++) {

            int idx = pop(timestamp);

            if (idx == -1) break;

            pIDList[i] = idx;
        }
    }

    void insert(Post post, int timestamp) {

        pq[++last_idx] = post;

        int loc = last_idx;

        while (loc / 2 > 0) {

            int parent_t = timestamp - pq[loc / 2].timestamp;
            int curr_t = timestamp - pq[loc].timestamp;

            // parent, curr 둘 다 만들어 진지 1000초가 지난 경우
            if (parent_t >= 1000 && curr_t >= 1000) {

                // timestamp 가 높은 쪽이 우선순위를 가진다.
                if (pq[loc].timestamp > pq[loc / 2].timestamp) {

                    swap(loc / 2, loc);
                    loc /= 2;

                }

                // 아니면 반복문 종료
                else break;
            }

            // parent 만 만들어 진지 1000초가 지난 경우
            else if (parent_t >= 1000 && curr_t < 1000) {

                // 무조건 교환이 일어난다.
                swap(loc / 2, loc);
                loc /= 2;
            }

            // child 만 만들어 진지 1000초가 지난 경우
            else if (parent_t < 1000 && curr_t >= 1000) {

                // parent 우선순위가 크므로 반복문을 종료한다.
                break;
            }

            // 둘 다 1000초가 지나지 않은 경우
            else {

                // parent 보다 like 이 큰 경우
                if (pq[loc].like > pq[loc / 2].like) {
                    swap(loc / 2, loc);
                    loc /= 2;
                }

                // parent 와 like 이 같지만 timestamp 가 높은 경우
                else if (pq[loc].like == pq[loc / 2].like && pq[loc].timestamp > pq[loc / 2].timestamp) {
                    swap(loc, loc / 2);
                    loc /= 2;
                } else break;
            }
        }
    }

    int pop(int timestamp) {

        if (last_idx == 0) return -1;

        int ret = pq[1].pID;
        pq[1] = pq[last_idx--];

        int parent_idx = 1;
        while (parent_idx * 2 <= last_idx) {

            int left_child_idx = parent_idx * 2;
            int right_child_idx = parent_idx * 2 + 1;
            int root_idx = parent_idx;

            if (left_child_idx <= last_idx) {

                int time_root = timestamp - pq[root_idx].timestamp;
                int time_l = timestamp - pq[left_child_idx].timestamp;

                // root, left 둘 다 만들어 진지 1000초가 지난 경우
                if (time_root >= 1000 && time_l >= 1000) {

                    // timestamp 가 높은 쪽이 우선순위를 가진다.
                    if (pq[left_child_idx].timestamp > pq[root_idx].timestamp) {

                        root_idx = left_child_idx;
                    }
                }

                // root 만 만들어 진지 1000초가 지난 경우
                else if (time_root >= 1000 && time_l < 1000) {

                    // 무조건 교환이 일어난다.
                    root_idx = left_child_idx;
                }

                // left 만 만들어 진지 1000초가 지난 경우
                else if (time_root < 1000 && time_l >= 1000) {

                }

                // 둘 다 1000초가 지나지 않은 경우
                else {

                    // parent 보다 like 이 큰 경우
                    if (pq[left_child_idx].like > pq[root_idx].like) {
                        root_idx = left_child_idx;
                    }

                    // parent 와 like 이 같지만 timestamp 가 높은 경우
                    else if (pq[left_child_idx].like == pq[root_idx].like && pq[left_child_idx].timestamp > pq[root_idx].timestamp) {
                        root_idx = left_child_idx;
                    }
                }
            }

            if (right_child_idx <= last_idx) {

                int time_root = timestamp - pq[root_idx].timestamp;
                int time_right = timestamp - pq[right_child_idx].timestamp;

                // root, right 둘 다 만들어 진지 1000초가 지난 경우
                if (time_root >= 1000 && time_right >= 1000) {

                    // timestamp 가 높은 쪽이 우선순위를 가진다.
                    if (pq[right_child_idx].timestamp > pq[root_idx].timestamp) {

                        root_idx = right_child_idx;
                    }
                }

                // root 만 만들어 진지 1000초가 지난 경우
                else if (time_root >= 1000 && time_right < 1000) {

                    // 무조건 교환이 일어난다.
                    root_idx = right_child_idx;
                }

                // right 만 만들어 진지 1000초가 지난 경우
                else if (time_root < 1000 && time_right >= 1000) {

                }

                // 둘 다 1000초가 지나지 않은 경우
                else {

                    // root 보다 like 이 큰 경우
                    if (pq[right_child_idx].like > pq[root_idx].like) {
                        root_idx = right_child_idx;
                    }

                    // root 와 like 이 같지만 timestamp 가 높은 경우
                    else if (pq[right_child_idx].like == pq[root_idx].like && pq[right_child_idx].timestamp > pq[root_idx].timestamp) {
                        root_idx = right_child_idx;
                    }
                }
            }

            if (parent_idx != root_idx) {
                swap(parent_idx, root_idx);
                parent_idx = root_idx;
            } else break;

        }

        return ret;
    }

    void swap(int num1, int num2) {
        Post temp = pq[num1];
        pq[num1] = pq[num2];
        pq[num2] = temp;
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

