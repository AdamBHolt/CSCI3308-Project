import java.util.*;

/**
 * This class is the data structure for a program that manages a social network of Friends
 * @author Adam Holt
 * @date 4/27/14
 */
public class FriendGraph implements GraphInterface<Friend, Edge<Friend, Friend>>
{

    //Tree map that will hold the vertices as Friend objects
    //And the Edges in a TreeSet and act as an adjacency list for the map
    private TreeMap<Friend, TreeSet<Edge<Friend, Friend>>> adjacencyList;

    /**
     * Default constructor
     */
    public FriendGraph()
    {
	//Instantiate the adjacency list
	adjacencyList = new TreeMap<>();
    }

    /**
     * Gets an Edge<Friend, Friend> object form the current graph if the edge exists
     * @param sourceVertex The originating vertex
     * @param targetVertex The target vertex
     * @return An Edge<Friend, Friend> object if the edge exists in the graph, otherwise null
     */
    public Edge<Friend, Friend> getEdge(Friend sourceVertex, Friend targetVertex)
    {
	//If either vertex is null return null
	if(sourceVertex==null || targetVertex==null)
	    return null;

	//Only proceed if the graph contains the source vertex
	//And if the edge between the vertices exists in the graph
	if(containsVertex(sourceVertex) && containsEdge(sourceVertex, targetVertex))
	{
	    //Temporary Edge based on the vertices to test
	    Edge<Friend, Friend> tempEdge = new Edge<>(sourceVertex, targetVertex);

	    //Check each edge in the adjacency list
	    for(Edge<Friend, Friend> e : adjacencyList.get(sourceVertex))
		//If the target edge is found return a reference to the object
		if(e.equals(tempEdge))
		    return e;
	}
	//Return null if the edge was not found
	return null;
    }

    /**
     * Add an edge to the graph
     * @param sourceVertex The originating vertex
     * @param targetVertex The target vertex
     * @return The Edge<Friend, Friend> object if the edge was added, otherwise null
     * @throws NullPointerException
     * @throws IllegalArgumentException
     */
    public Edge<Friend, Friend> addEdge(Friend sourceVertex, Friend targetVertex) throws NullPointerException, IllegalArgumentException
    {
	//If either vertex is null throw a NullPointerException
	if(sourceVertex==null || targetVertex==null)
	    throw new NullPointerException();

	//If either vertex is not in the graph throw an IllegalArgumentException
	if(!containsVertex(sourceVertex) || !containsVertex(targetVertex))
	    throw new IllegalArgumentException();

	//Only proceed if the graph does not already contain the edge
	if(!containsEdge(sourceVertex, targetVertex))
	{
	    //The edge to be added
	    Edge<Friend, Friend> e = new Edge<>(sourceVertex, targetVertex);
	    //Add the edge to the adjacency list for both vertices
	    adjacencyList.get(sourceVertex).add(e);
	    adjacencyList.get(targetVertex).add(e);
	    //Return the edge
	    return e;
	}
	//If the edge was not added, then it was already in the graph, return null
	else
	    return null;
    }

    /**
     * Add a Friend vertex to the graph
     * @param Friend vertex to add to the graph
     * @return True if the vertex was added, otherwise false
     */
    public boolean addVertex(Friend v)
    {
	//If the Friend is already in the graph return false
	if(containsVertex(v))
	    return false;

	//Otherwise add the Friend as a vertex and a new TreeSet
	//And return true
	adjacencyList.put(v, new TreeSet<Edge<Friend, Friend>>());
	return true;
    }

    /**
     * Check if an edge is in the graph
     * @param sourceVertex The originating vertex
     * @param targetVertex The target vertex
     * @return True if the edge is in the graph, otherwise false
     */
    public boolean containsEdge(Friend sourceVertex, Friend targetVertex)
    {
	//Only proceed if the source vertex is in the graph
	if(containsVertex(sourceVertex))
	{
	    //Temporary Edge based on the vertices to test
	    Edge<Friend, Friend> tempEdge = new Edge<>(sourceVertex, targetVertex);

	    //Check each edge in the adjacency list
	    for(Edge<Friend, Friend> e : adjacencyList.get(sourceVertex))
		//If the edge is found return true
		if(e.equals(tempEdge))
		    return true;

	    //Check each edge in the adjacency list
	    for(Edge<Friend, Friend> e : adjacencyList.get(targetVertex))
		//If the edge is found return true
		if(e.equals(tempEdge))
		    return true;
	}
	//If the edge was not found or if the source vertex was not in the graph return false
	return false;
    }

    /**
     * Check if a vertex is in the graph
     * @param v Friend object to be checked
     * @return True if the vertex is in the graph otherwise false
     */
    public boolean containsVertex(Friend v)
    {
	//Return the results of the containsKey method of the map
	return adjacencyList.containsKey(v);
    }

    /**
     * Gets a Set containing all of the edges in the graph
     * @return A Set object containing Edge<Friend, Friend> objects contained in he adjacency list
     */
    public Set<Edge<Friend, Friend>> edgeSet()
    {
	//A set of the keys in the map
	Set<Friend> keys = adjacencyList.keySet();
	//The set to be returned
	Set<Edge<Friend, Friend>> returnSet = new TreeSet<>();

	//Check through each Friend that is a key in the map
	for(Friend f : keys)
	    //Check through each Edge that is mapped to in the map
	    for(Edge<Friend, Friend> e : adjacencyList.get(f))
		//Add each Edge to the return set
		returnSet.add(e);
	//Return the return set
	return returnSet;
    }

    /**
     * Gets the a Set containing all of the edges for a specific vertex
     * @param vertex The vertex to find the edges for
     * @return A Set object containing Edge<Friend, Friend> objects associated with the vertex
     */
    public Set<Edge<Friend, Friend>> edgesOf(Friend vertex)
    {
	//Return the set that is mapped to the vertex
	return adjacencyList.get(vertex);
    }

    /**
     * Remove an edge from the graph
     * @param sourceVertex The originating vertex
     * @param targetVertex The target vertex
     * @return The edge if it was removed, otherwise null
     */
    public Edge<Friend, Friend> removeEdge(Friend sourceVertex, Friend targetVertex)
    {
	//Only proceed if the edge is contained in the graph
	if(containsEdge(sourceVertex, targetVertex))
	{
	    //Temporary Edge based on the vertices to test
	    Edge<Friend, Friend> tempEdge = new Edge<>(sourceVertex, targetVertex);

	    //Check through each edge of the source vertex
	    for(Edge<Friend, Friend> e : adjacencyList.get(sourceVertex))
		//Delete the edge when it is found
		if(e.equals(tempEdge))
		    adjacencyList.get(sourceVertex).remove(e);
 
	    //Check through each edge of the target vertex
	    for(Edge<Friend, Friend> e : adjacencyList.get(targetVertex))
		//Delete the edge when it is found
		if(e.equals(tempEdge))
		{
		    adjacencyList.get(targetVertex).remove(e);
		    return tempEdge;
		}
	    
	    //Return the temporary edge
	    return tempEdge;
	}
	//If the edge was not found return null
	return null;
    }

    /**
     * Remove a vertex from the graph
     * @param v The vertex to be removed
     * @return True if the vertex is removed otherwise false
     */
    public boolean removeVertex(Friend v)
    {
	//Only proceed if the graph contains the vertex
	if(containsVertex(v))
	{
	    //A set of the keys in the map
	    Set<Friend> keys = adjacencyList.keySet();

	    //Check through each Friend that is a key in the map
	    for(Friend f : keys)
	    {
		//Temporary edge to be checked
		Edge<Friend, Friend> tempEdge = new Edge<>(v, f);
		//Check each edge in the adjacency list for the current key
		for(Edge<Friend, Friend> e : adjacencyList.get(f))
		    //If the edge is found remove it
		    if(e.equals(tempEdge))
			adjacencyList.get(f).remove(e);
	    }
	    //Remove the vertex from the map
	    adjacencyList.remove(v);
	    //Return true
	    return true;
	}
	//If the vertex was not removed return false
	return false;
    }

    /**
     * Get a set containing all the vertices in the graph
     * @return A Set object containing Friend objects that are vertices in the graph
     */
    public Set<Friend> vertexSet()
    {
	//Return the keyset of the map
	return adjacencyList.keySet();
    }


    /**
     * Get the hometown of a Friend in the graph based on the first and last names
     * @param fName First name of the Friend
     * @param lName Last name of the Friend
     * @return The homeTown of the Friend
     */
    public String getHomeTown(String fName, String lName)
    {
	//Check through each Friend in the vertex set
	for(Friend f : vertexSet())
	    //when the Friend is found return the hometown
	    if(f.equals(new Friend(fName, lName, "")))
		return f.getHomeTown();
	//If the Friend is not found return null
	return null;
    }
}