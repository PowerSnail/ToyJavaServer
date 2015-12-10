public class MyProtocol
{
    private static final int WAITING    = 0;
    private static final int LOGIN      = 2;
    private static final int COMMANDS   = 3;

    private int state = MyProtocol.WAITING;
    private MyAuthenticator auth;
    private DummyLambda fetchEvent = (args) -> {
        if (args.length == 3)
        {
            int userID = Integer.parseInt(args[1]);
            int time = Integer.parseInt(args[2]);

            return pullFromDatabase(userID, time);
        } else
        {
            return "Event Fetching Failed! ";
        }
    };
    private DummyLambda likeEvent = (args) -> {
        if (args.length == 2)
        {
            int time = Integer.parseInt(args[1]);
            if (deleteEventFromQueue(time))
            {
                return "Successfully deleted!";
            } else
            {
                return "Deletion failed!";
            }
        } else
        {
            return "Argument Error";
        }
    };
    private DummyLambda dummyAction = (args) -> {
        return "dummyAction Taken";
    };
    private DummyLambda[] commands = new DummyLambda[]{
            dummyAction,
            fetchEvent,
            likeEvent
    };

    private static String pullFromDatabase(int userID, int timeBlock)
    {
        return "Dymmy Event Description";
    }

    private static boolean deleteEventFromQueue(int queueID)
    {
        return true;
    }

    private int decode(String code)
    {
        return (Integer.parseInt(code));
    }

    public String processInput(String theInput)
    {


        if (state == WAITING)
        {
            state = LOGIN;
            return "1001";
        } else if (state == LOGIN)
        {
            if (auth.authenticate(theInput))
            {
                state = COMMANDS;
                return "1000";
            } else
            {
                state = WAITING;
                return "1010";
            }
        } else if (state == COMMANDS)
        {
            if (theInput.equals("SIGNOUT"))
            {
                state = WAITING;
                return "1002";
            } else
            {
                String[] command = theInput.split(" ");
                DummyLambda action = commands[decode(command[0])];
                return action.operation(command);
            }
        }

        return "";
    }

    private interface DummyLambda
    {
        String operation(String[] args);
    }
}