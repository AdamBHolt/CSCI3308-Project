import java.io.*;
import java.util.*;

/**
 * This class is the data manager for a program that manages a social network of Friends
 * It uses a FriendGraph to hold friends and their connections
 * @author Adam Holt
 * @date 4/27/14
 */
public class DataManager implements DataManagerInterface
{
    //The graph to hold the friends and their connections
    private FriendGraph friendGraph;

    //Default constructor
    public DataManager()
    {
	//Instantiate the graph
	friendGraph = new FriendGraph();
    }

    /**
     * Get an ArrayList of friends of the friends of a participant
     * @param name Name of the participant to find friends of friends in the form <First name> <Last name> of <Home town>
     * @return An ArrayList of Strings of each of the friends of friends of the participant
     */
    public ArrayList<String> friendsOfFriends(String name)
    {
	//List of the friends of the participant
	ArrayList<String> friends = listFriends(name);
	//The list to be returned
	ArrayList<String> returnList = new ArrayList<>();
	//A set to hold the names of friends as they are found
	Set<String> friendSet = new TreeSet<>();

	//Check through each of the participant's friends
	for(String currentFriend : friends)
	    //Check each of the friends of the current friend
	    for(String friend : listFriends(currentFriend))
		//If the friend is no the same as the participant or already a friend of the participant
		if(!friend.toString().equals(name) && !friends.contains(friend))
		    //Add the friend to the set
		    friendSet.add(friend);

	//Check through each element of the set and add it to the return list
	for(String s : friendSet)
	    returnList.add(s);

	//Return the return list
	return returnList;
    }

    /**
     * Get an ArrayList of friends of the friends of a participant
     * @param fName First name of the participant
     * @param lName Last name of the participant
     * @param hometown Home town of the participant
     * @return An ArrayList of Strings of each of the friends of friends of the participant
     */
    public ArrayList<String> friendsOfFriends(String fname, String lname, String hometown)
    {
	//Return the results of the friendsOfFriends method based on the complete name
	return friendsOfFriends(fname + " " + lname + " of " + hometown);
    }

    /**
     * Get a list of the friends of a participant
     * @param name Name of the participant to find friends of friends in the form <First name> <Last name> of <Home town>
     * @return A list of the friends of a participant
     */
    public ArrayList<String> listFriends(String name)
    {
	//Return the results of the listFriends method based on a Friend object
	return listFriends(new Friend(name));
    }

    /**
     * Get a list of the friends of a participant
     * @param fName First name of the participant
     * @param lName Last name of the participant
     * @param hometown Home town of the participant
     * @return A list of the friends of a participant
     */
    public ArrayList<String> listFriends(String fname, String lname,String hometown)
    {
	//Return the results of the listFriends method based on a Friend object
	return listFriends(new Friend(fname, lname, hometown));
    }

    /**
     * Get a list of the friends of a participant
     * @param f A Friend object
     * @return A list of the friends of a participant
     */
    public ArrayList<String> listFriends(Friend f)
    {
	//List to be returned
	ArrayList<String> returnList = new ArrayList<>();
	//A set of the keys of the graph
	Set<Edge<Friend, Friend>> friendSet = friendGraph.edgesOf(f);

	//Check each edge in the set of keys
	for(Edge<Friend, Friend> e : friendSet)
	{
	    //If the first vertex equals the Friend add the second vertex to the return list
	    if(e.getV1().equals(f))
		returnList.add(e.getV2().toString());
	    //Otherwise add the first vertex to the return list
	    else
		returnList.add(e.getV1().toString());
	}
	//Return the return list
	return returnList;
    }

    /**
     * Get an array list of each of the elements of a participant's name
     * @param name Participant's name in the form <First name> <Last name> of <Home town>
     * @return ArrayList of Strings representing the individual parts of a participant's name
     */
    public ArrayList<String> getProfile(String name)
    {
	//The list to be returned
	ArrayList<String> returnList = new ArrayList<>();
	//Scanner to get the tokens from the name
	Scanner scan = new Scanner(name);

	//Add each element of the name to the return list
	returnList.add(scan.next());
	returnList.add(scan.next());
	scan.next();
	returnList.add(scan.nextLine().trim());

	//Close the scanner
	scan.close();

	//Return the return list
	return returnList;
    }

    /**
     * Get a Vector of the participants in the graph
     *	@return a Vector of Strings representing each of the participants in the graph
     */
    public Vector<String> vectorOfParticipants()
    {
	//Vector to be returned
	Vector<String> returnVector = new Vector<>();
	//A set of the keys of the graph
	Set<Friend> friendSet = friendGraph.vertexSet();

	//Check through each Friend in the set of keys
	for(Friend f : friendSet)
	    //Add each friend to the return vector
	    returnVector.add(f.toString());

	//Return the return vector
	return returnVector;
    }

    /**
     * Add a participant to the graph
     * @param fName First name of the participant
     * @param lName Last name of the participant
     * @param city Home town of the participant 
     */
    public void addParticipant(String fName, String lName, String city)
    {
	//Create a new Friend object based on the parameters
	Friend f = new Friend(fName, lName, city);
	//If the Friend does not already exist in the graph add it
	if(!friendGraph.containsVertex(f))
	    friendGraph.addVertex(f);
    }

    /**
     * Add a friend edge to the graph
     * @param profile The participant to add the friend to
     * @param newFriend The friend to be added
     */
    public void addFriend(String profile, String newFriend)
    {
	//Friend objects based on the passed Strings
	Friend f1 = new Friend(profile);
	Friend f2 = new Friend(newFriend);

	//If the  friends are not equal to each other and the edge does not already exist
	if(!f1.equals(f2) && !friendGraph.containsEdge(f1, f2))
	{
	    //Add the edge to the graph
	    try{friendGraph.addEdge(f1, f2);}
	    catch(NullPointerException e1){}
	    catch(IllegalArgumentException e2){}
	}
    }

    /**
     * Add a friend edge to the graph
     * @param profileFname First name of the participant to add a friend to
     * @param profileLname Last name of the participant to add a friend to
     * @param profileHometown Home town of the participant to add a friend to
     * @param newFriendFname First name of the friend to be added
     * @param newFriendLName Last name of the friend to be added
     * @param newFriendHometown Home town of the friend to be added
     */
    public void addFriend(String profileFname, String profileLname,
	    String profileHometown, String newFriendFname,
	    String newFriendLName, String newFriendHometown)
    {
	//Add a friend edge based on the complete Strings of the participant's names
	addFriend(profileFname + " " + profileLname + " of " + profileHometown, newFriendFname + " " + newFriendLName + " of " + newFriendHometown);
    }

    /**
     * Populate the graph with participants based on a text file
     * @param participantFile File of participants to be added
     * @throws FileNotFoundException
     */
    public void populateParticipants(File participantsFile)throws FileNotFoundException
    {
	//Scanner to read from the selected file
	Scanner inputFile = null;
	//Tokenizer to get the values
	StringTokenizer tokens = null;

	//Try to create a new Scanner
	try
	{
	    //Create a new scanner based on the file
	    inputFile = new Scanner(participantsFile);
	}
	catch (FileNotFoundException e)
	{
	    e.printStackTrace();
	}

	//Read each line in the text file
	while(inputFile.hasNext())
	{
	    //create a : delimited tokenizer
	    tokens = new StringTokenizer(inputFile.nextLine(), ":");

	    //Continue while there are tokens remaining
	    while(tokens.hasMoreTokens())
	    {
		//Add a participant based on the current tokens
		addParticipant(tokens.nextToken(), tokens.nextToken(), tokens.nextToken());
	    }
	}

	//Close the file
	inputFile.close();
    }

    /**
     * Populate the graph with friend edges based on a text file
     * @param participantFile File of participants to be added
     * @throws FileNotFoundException
     */
    public void populateFriends(File friendsFile) throws FileNotFoundException
    {
	//Scanner to read from the selected file
	Scanner inputFile = null;
	//Tokenizer to get the values
	StringTokenizer tokens = null;

	//Strings to represent the names of the participants and friends
	String fName, lName, homeTown, f, l, h;
	//Number of friends for the participant
	int friends;

	//Try to create a new Scanner
	try
	{
	    //Create a new scanner based on the file
	    inputFile = new Scanner(friendsFile);
	}
	catch (FileNotFoundException e)
	{
	    e.printStackTrace();
	}

	//Read each line in the text file
	while(inputFile.hasNextLine())
	{
	    //create a : delimited tokenizer
	    tokens = new StringTokenizer(inputFile.nextLine(), ":");

	    //The first three tokens are the names and number of friends of the participant
	    fName = tokens.nextToken();
	    lName = tokens.nextToken();
	    //Get the home town of the participant based on the names
	    homeTown = friendGraph.getHomeTown(fName, lName);
	    friends = Integer.parseInt(tokens.nextToken());

	    //Repeat for the number of friends
	    for(int i=0; i<friends; i++)
	    {
		//Set the names and home town of the friend to add
		f = tokens.nextToken();
		l = tokens.nextToken();
		h = friendGraph.getHomeTown(f, l);
		//Add the friend
		addFriend(fName, lName, homeTown, f, l, h);
	    }
	}
	//Close the file
	inputFile.close();
    }
}