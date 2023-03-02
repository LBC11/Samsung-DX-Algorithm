package 해시_8.No_31;

import java.util.ArrayList;

/*
9465. 메일서버

[문제 설명]
메일을 주고받는 기능을 제공하는 메일서버 프로그램을 작성하려고 한다.
메일서버 프로그램에는 메일 전송, 받은 메일 삭제, 받은 메일 검색 등의 기능이 있다.

메일서버에는 각각의 유저에게 받은 메일을 저장할 수 있는 받은 메일함이 주어진다.
받은 메일함은 일정한 개수의 메일만 저장된다.

void init(int N, int K)
각 테스트 케이스의 처음에 호출된다.
N 명의 유저가 메일서버를 이용한다.
유저 한 명당 받은 메일함에 저장할 수 있는 메일의 개수는 K 개이다.

각각의 인자 값의 범위는 아래와 같다.
 3 ≤ N ≤ 1,000
 3 ≤ K ≤ 300

Parameters
   N : 메일 서버 이용자의 수
   K : 받은 메일함에 저장할 수 있는 메일의 개수

void sendMail(char subject[], int uID, int cnt, int rIDs[])
uID 유저가 subject[] 제목의 메일 전송을 메일 서버에 요청한다.
메일 서버는 subject[] 제목의 메일을 rIDs[] 수신인들의 받은 메일함에 저장한다.
수신인들의 받은 메일함에 있는 메일의 개수가 K 개일 경우, 가장 오래된 메일이 삭제되고 subject[] 제목의 메일이 저장된다.
subject[] 는 1개 이상 10개 이하의 단어로 구성되며, 단어가 2개 이상일 경우 각 단어의 사이는 빈 칸(‘ ‘) 하나로 이루어져 있다.
subject[] 에 포함되는 각 단어는 영어 소문자로 구성되며, 길이는 3 이상 10 이하이다.
subject[] 는 ‘\0’ 으로 끝나는 문자열이다.

rIDs[] 는 모두 서로 다른 사용자의 id 이다.
각각의 인자 값의 범위는 아래와 같다.
 1 ≤ subject[] 에 있는 단어의 개수 ≤ 10
 0 ≤ uID ≤ ( N - 1 )
 1 ≤ cnt ≤ 50
 0 ≤ rIDs[] ≤ ( N - 1 )

Parameters
   subject[] : 메일 제목
   uID : 메일을 보내는 유저의 id
   cnt : 메일을 받는 사람들의 수
   rIDs[] : 메일을 받는 유저들의 id

int getCount(int uID)
uID 유저의 받은 메일함에 있는 메일의 수를 반환한다.
반환되는 메일의 개수에 삭제된 메일은 포함되지 않는다.
각각의 인자 값의 범위는 아래와 같다.
 0 ≤ uID ≤ ( N - 1 )

Parameters
   uID : 받은 메일함을 확인하는 유저의 id

Returns
   uID 유저의 받은 메일함에 있는 메일 개수

int deleteMail(int uID, char subject[])
uID 유저의 받은 메일함에서 subject[] 와 일치하는 제목을 가진 메일을 삭제하고, 삭제한 메일의 개수를 리턴한다.
subject[] 는 영어 소문자와 빈칸으로 구성되며, ‘\0’ 으로 끝나고 ‘\0’ 을 포함한 전체 길이는 200 이하이다.
각각의 인자 값의 범위는 아래와 같다.

 0 ≤ uID ≤ ( N - 1 )

Parameters
   uID : 메일을 삭제하려는 유저의 id
   subject[] : 삭제할 메일의 제목

Returns
   삭제한 메일의 개수

int searchMail(int uID, char text[])
uID 유저의 받은 메일함에서 메일 제목에 text[] 단어가 포함되어 있는 메일을 찾고, 찾은 메일의 개수를 리턴한다.
메일 제목에 포함되어 있는 단어 중 하나와 text[] 단어가 일치해야만 검색이 되며, 일부분만 같을 경우 검색이 되지 않아 찾은 메일 개수에 포함되지 않는다.
예를 들어, 메일 제목이 “aaaa bbbb cccc” 이고, text[] = “aaa” 일 경우 해당 메일은 찾은 메일 개수에 포함되지 않는다.
text[] 는 영어 소문자로 구성되며, ‘\0’ 으로 끝나고 ‘\0’ 을 포함한 전체 길이는 11 이하이다.
각각의 인자 값의 범위는 아래와 같다.

 0 ≤ uID ≤ ( N - 1 )

Parameters
   uID : 받은 메일함에서 단어를 검색하려는 유저의 id
   text[] : 검색할 단어

Returns
   검색된 메일의 개수

[제약사항]
1. 각 테스트 케이스 시작 시 init() 함수가 호출된다.
2. sendMail() 함수에서 수신인들의 받은 메일함에 있는 메일의 개수가 K 개일 경우, 가장 오래된 메일이 삭제되고 subject[] 제목의 메일이 저장된다.
3. 각 테스트 케이스에서 메일서버를 이용하는 유저의 수는 최대 1,000 이다.
4. 각 테스트 케이스에서 사용되는 서로 다른 단어의 종류는 최대 10,000 개이다.
5. 각 테스트 케이스에서 sendMail() 함수의 호출 횟수는 최대 10,000 이다.
6. 각 테스트 케이스에서 모든 함수의 호출 횟수 총합은 최대 30,000 이다.

[입출력]
입출력은 제공되는 Main 부분의 코드에서 처리하므로 User Code 부분의 코드에서는 별도로 입출력을 처리하지 않는다.
Sample input 에 대한 정답 출력 결과는 아래와 같은 형태로 보여진다.

#1 100
#2 100
#3 100
#4 100
#5 100

주요 아이디어
1. hash 을 이용해 단어를 int 값으로 변환

+ 충돌 발생으로 인해 testcase 1개가 통과가 안되고 있었다...
  초기값을 5381, shift 정도를 5로 바꿨더니 통과한다.

 */
public class UserSolution {

    final long Hash_size = 1 << 30;
    final long DIV = Hash_size - 1;

    User[] users;

    int max_mail;

    void init(int N, int K) {

        // users init
        users = new User[N];

        // 각 user init
        for (int i = 0; i < N; i++) {
            users[i] = new User();
        }

        // 메일 최대 개수 설정
        max_mail = K;
    }

    void sendMail(char[] subject, int uID, int cnt, int[] rlDs) {

        ArrayList<Integer> m = new ArrayList<>();

        int start = 0;
        for (int i = 0; i < subject.length; i++) {

            if (subject[i] == ' ' || subject[i] == '\0') {
                m.add(getHash(subject, start, i));

                // 다음 단어 hashing 을 위한 갱신
                start = i + 1;

                if (subject[i] == '\0') break;
            }
        }

        for (int i = 0; i < cnt; i++) {
            addMail(users[rlDs[i]], m);
        }

    }

    int getCount(int uID) {

        return users[uID].mail_box.size();
    }

    int deleteMail(int uID, char[] subject) {

        ArrayList<Integer> m = new ArrayList<>();

        int start = 0;
        for (int i = 0; i < subject.length; i++) {

            if (subject[i] == ' ' || subject[i] == '\0') {
                m.add(getHash(subject, start, i));

                // 다음 단어 hashing 을 위한 갱신
                start = i + 1;

                if (subject[i] == '\0') break;
            }
        }

        ArrayList<ArrayList<Integer>> delete_mail = new ArrayList<>();

        for (ArrayList<Integer> mail : users[uID].mail_box) {

            // 둘의 제목이 같으면 사이즈가 같아야 한다. -> 계산 빠르게 하기 위해
            if (mail.size() == m.size()) {

                boolean flag = true;

                for (int i = 0; i < m.size(); i++) {

                    // 둘의 원소가 다르면 해당 메일이 아니다.
                    if (!mail.get(i).equals(m.get(i))) {
                        flag = false;
                        break;
                    }
                }

                // 삭제한 mail 을 찾으면 지운다
                if (flag) delete_mail.add(mail);
            }
        }

        for (ArrayList<Integer> m1 : delete_mail) {
            users[uID].mail_box.remove(m1);
        }

        return delete_mail.size();
    }

    int searchMail(int uID, char[] text) {

        int ans = 0;

        // 맨 마지막의 \0 을 제외한 나머지 부분만 hash 를 통해 int 값으로 변형
        int word = getHash_word(text);

        for (ArrayList<Integer> mail : users[uID].mail_box) {
            for (int i : mail) {

                // word 와 같은 단어가 있다면
                if (word == i) {
                    ans++;
                    break;
                }
            }
        }

        return ans;
    }

    int getHash(char[] arr, int start, int end) {

        long hash = 5381;
        for (int i = start; i < end; i++) {

            hash = (hash << 5) + hash + arr[i];
        }

        return (int) (hash & DIV);
    }

    int getHash_word(char[] arr) {

        long hash = 5381;
        for (int i = 0; i < arr.length; i++) {

            // '\0' 이 나오면 단어가 끝난 거다.
            if (arr[i] == '\0') break;

            hash = (hash << 5) + hash + arr[i];
        }

        return (int) (hash & DIV);
    }

    void addMail(User user, ArrayList<Integer> m) {

        // 이미 mail 함이 포화상태라면
        if (user.mail_box.size() == max_mail) {

            // 가장 오래된 mail 삭제
            user.mail_box.remove(0);
        }

        // 새로운 매일 추가
        user.mail_box.add(m);
    }
}

class User {

    ArrayList<ArrayList<Integer>> mail_box;

    public User() {
        this.mail_box = new ArrayList<>();
    }
}