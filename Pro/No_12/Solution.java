package Pro.No_12;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

class Solution
{

    private final static int CMD_INIT		= 0;
    private final static int CMD_ADD		= 1;
    private final static int CMD_REMOVE		= 2;
    private final static int CMD_REQUEST	= 3;
    private final static int CMD_CHECK		= 4;

    private static UserSolution usersolution = new UserSolution();

    public static final class Result
    {
        int finish;
        int param;

        Result()
        {
            finish = 0;
            param = 0;
        }
    }

    private static boolean run (BufferedReader br) throws Exception
    {
        Result res;
        int ans;
        int cmd, time, id1, id2, ret, param1, param2;
        int Q = 0;
        boolean okay = false;

        StringTokenizer st = new StringTokenizer(br.readLine(), " ");
        Q = Integer.parseInt(st.nextToken());
        for (int i = 0; i < Q; ++i)
        {
            st = new StringTokenizer(br.readLine(), " ");
            cmd = Integer.parseInt(st.nextToken());
            switch (cmd)
            {
                case CMD_INIT:
                    param1 = Integer.parseInt(st.nextToken());
                    usersolution.init(param1);
                    okay = true;
                    break;

                case CMD_ADD:
                    time = Integer.parseInt(st.nextToken());
                    id1 = Integer.parseInt(st.nextToken());
                    id2 = Integer.parseInt(st.nextToken());
                    usersolution.addHub(time, id1, id2);
                    break;

                case CMD_REMOVE:
                    time = Integer.parseInt(st.nextToken());
                    id1 = Integer.parseInt(st.nextToken());
                    ans = Integer.parseInt(st.nextToken());
                    ret = usersolution.removeHub(time, id1);
                    if (ans != ret)
                        okay = false;
                    break;

                case CMD_REQUEST:
                    time = Integer.parseInt(st.nextToken());
                    id1 = Integer.parseInt(st.nextToken());
                    id2 = Integer.parseInt(st.nextToken());
                    param1 = Integer.parseInt(st.nextToken());
                    usersolution.requestDL(time, id1, id2, param1);
                    break;

                case CMD_CHECK:
                    time = Integer.parseInt(st.nextToken());
                    id1 = Integer.parseInt(st.nextToken());
                    param1 = Integer.parseInt(st.nextToken());
                    param2 = Integer.parseInt(st.nextToken());
                    res = usersolution.checkPC(time, id1);
                    if (res.finish != param1 || res.param != param2)
                        okay = false;
                    break;

                default:
                    okay = false;
                    break;
            }
        }

        return okay;
    }

    public static void main(String[] args) throws Exception
    {

        //System.setIn(new java.io.FileInputStream("res/sample_input.txt"));

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer line = new StringTokenizer(br.readLine(), " ");

        int TC = Integer.parseInt(line.nextToken());
        int MARK = Integer.parseInt(line.nextToken());

        for (int testcase = 1; testcase <= TC; ++testcase)
        {
            int score = run(br) ? MARK : 0;
            System.out.println("#" + testcase + " " + score);
        }

        br.close();
    }
}