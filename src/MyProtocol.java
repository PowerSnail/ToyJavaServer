import java.util.HashMap;
public class MyProtocol {
	 private static final int WAITING = 0;
	 private static final int GREETED = 1;
	 private static final int PROMPTEDFORPASS = 2;
	 private static final int LOGGEDIN = 3;
	 
	 private MyAuthenticator auth;
	 
    private interface DummyLambda {
        String operation(String[] args);
    }

    private DummyLambda fetchEvent = (args) -> {
        if (args.length == 3) {
            int userID  = Integer.parseInt(args[1]);
            int time    = Integer.parseInt(args[2]);

            return pullFromDatabase(userID, time);
        } else {
            return "Event Fetching Failed! ";
        }
    };

    private DummyLambda likeEvent = (args) -> {
        if (args.length == 2) {
            int time = Integer.parseInt(args[1]);
            if (deleteEventFromQueue(time)) {
                return "Successfully deleted!";
            } else {
                return "Deletion failed!";
            }
        } else {
            return "Argument Error";
        }
    };
	
	private DummyLambda dummyAction = (args) -> {
		return "dummyAction Taken";
	};
    
    private DummyLambda[] commands = new DummyLambda[] {
			dummyAction,
            fetchEvent,
            likeEvent
    };


    private static String pullFromDatabase(int userID, int timeBlock) {
        return "Dymmy Event Description";
    }

    private static boolean deleteEventFromQueue(int queueID) {
        return true;
    }
	
	private int decode(String code) {
		return (Integer.parseInt(code));
	}
	 
	 public String processInput(String theInput) {

		 
		 if (state == WAITING) 
		 {
			 state = GREETED;
			 return "Welcome! Please enter your username: \n> ";
		 } 
		 else if (state == GREETED) 
		 {
			 auth = new MyAuthenticator(theInput);
			state = PROMPTEDFORPASS;
			 return "Please enter your password: ";
		 } 
		 else if (state == PROMPTEDFORPASS) 
		 {
			 if (auth.authenticate(theInput)) 
			 {
				 state = LOGGEDIN;
				 return "Successfully logged in! \nCommands shall be entered now!";
			 } 
			 else 
			 {
				 state = WAITING;
				 return "ERROR! Please re-enter your username and passwords! \n Please Enter your username: \n> ";
			 }
		 } 
		 else if (state == LOGGEDIN) 
		 {
			if (theInput.equals("SIGNOUT")) {
				state = WAITING;
				return "Logged out";
			} else {
			 String[] command = theInput.split(" ");
			 DummyLambda action = commands.get(decode(command[0]));
			 return action.operation(command);
			}
		 }
	 }
}