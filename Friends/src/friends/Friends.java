package friends;

import java.util.ArrayList;

import structures.Queue;
import structures.Stack;

public class Friends {

	/**
	 * Finds the shortest chain of people from p1 to p2.
	 * Chain is returned as a sequence of names starting with p1,
	 * and ending with p2. Each pair (n1,n2) of consecutive names in
	 * the returned chain is an edge in the graph.
	 * 
	 * @param g Graph for which shortest chain is to be found.
	 * @param p1 Person with whom the chain originates
	 * @param p2 Person at whom the chain terminates
	 * @return The shortest chain from p1 to p2. Null or empty array list if there is no
	 *         path from p1 to p2
	 */
	public static ArrayList<String> shortestChain(Graph g, String p1, String p2) {
		
		if (g == null) {
			return null;
		}
		if(p1 == null){
			return null;
		}
		if(p2 == null) {
			return null;
		}
		ArrayList<String> shortchain = new ArrayList<String>();
		boolean[] visit = new boolean[g.members.length];
		Queue<Person> personque = new Queue<Person>();
		Person[] alreadyvisit = new Person[g.members.length];
		int index = g.map.get(p1);
		personque.enqueue(g.members[index]);
		visit[index] = true;
		
		while (personque.isEmpty() == false) {
			
			Person person = personque.dequeue();
			int personnum = g.map.get(person.name);
			visit[personnum] = true;
			
			Friend neighbor = person.first;
			
			if (neighbor == null) {
				return null;
			}
			
			while (neighbor != null) {
				
				if (visit[neighbor.fnum] == false) {
					visit[neighbor.fnum] = true;
					alreadyvisit[neighbor.fnum] = person; 
					personque.enqueue(g.members[neighbor.fnum]);
					
					
					if (g.members[neighbor.fnum].name.equals(p2)) {
						person = g.members[neighbor.fnum];
						
						
						while (person.name.equals(p1) == false) {
						
							shortchain.add(0, person.name);
							person = alreadyvisit[g.map.get(person.name)];
						
						}
						
						shortchain.add(0, p1);
						return shortchain;
					
					}
				}
				neighbor = neighbor.next;
			}
		}
		return null;
	}
	
	/**
	 * Finds all cliques of students in a given school.
	 * 
	 * Returns an array list of array lists - each constituent array list contains
	 * the names of all students in a clique.
	 * 
	 * @param g Graph for which cliques are to be found.
	 * @param school Name of school
	 * @return Array list of clique array lists. Null or empty array list if there is no student in the
	 *         given school
	 */
	public static ArrayList<ArrayList<String>> cliques(Graph g, String school) {
		
		boolean[] visited = new boolean[g.members.length];
		ArrayList<ArrayList<String>> coolkids = new ArrayList<ArrayList<String>>();
		
		for (int p = 0; p < g.members.length; p++) 
		{
			Person pp = g.members[p];
			
			if (visited[p] == true || pp.student == false) 
			{
				continue;
			}
			
			ArrayList<String> newbie = new ArrayList<String>();
			assistantsearch(visited, g, newbie, p, school);
			
			if (newbie != null && newbie.size() > 0) 
			{
				coolkids.add(newbie);
			}
		}
		
		return coolkids;
		
	}
	
	private static void assistantsearch(boolean[] visited, Graph g, ArrayList<String> newbie, int p, String school) {
		Person anotherperson = g.members[p];
		
		if ((visited[p] == false) && (anotherperson.student == true) && (anotherperson.school.equals(school))) 
		{
			newbie.add(anotherperson.name);
		}
		
		visited[g.map.get(anotherperson.name)] = true;
		
		Friend pointer = g.members[p].first;
		while (pointer != null) 
		{
			int number = pointer.fnum;
			Person friend = g.members[number];
		
			if (visited[number] == false && friend.student && friend.school.equals(school)) 
			{
				assistantsearch(visited, g, newbie, number, school);
			}
			
			pointer = pointer.next;
		}
	}
	
	/**
	 * Finds and returns all connectors in the graph.
	 * 
	 * @param g Graph for which connectors needs to be found.
	 * @return Names of all connectors. Null or empty array list if there are no connectors.
	 */
	public static ArrayList<String> connectors(Graph g) {
		
		/** COMPLETE THIS METHOD **/
		
		String[] names = new String[g.members.length];
		for (int i = 0; i < names.length; i++) {
			names[i] = g.members[i].name;
		}
		
		System.out.println(names);
		
	
		ArrayList<String> connectors = new ArrayList<String>();
		int[] dfsnum = new int[g.members.length], prev = new int[g.members.length];
		boolean[] visited = new boolean[g.members.length];
		int tempnum = 0;
		for (int i =0; i<visited.length;i++) {
			boolean b = visited[i];

			if (b == false) {
				Person currentmember = g.members[i];

				DepthFirstSearch(g, currentmember, dfsnum, visited, prev, tempnum, connectors);

			}
		}

		for (int i = 0; i < connectors.size(); i++) {
			Person currentperson = PersonsName(g, connectors.get(i));
			
			int edges = ListofBuddies(g, currentperson).size();
			if (edges == 1) {
				connectors.remove(i);
			}

		}
		return connectors;
	}
	
private static void DepthFirstSearch(Graph g, Person currentmember, int[] dfsnum, boolean[] visited, int[] prev, int tempnum, ArrayList<String> connectors) {
	int currentIndex = g.map.get(currentmember.name); 
	prev[currentIndex] = tempnum;
	dfsnum[currentIndex] = tempnum;
	visited[currentIndex] = true;
	tempnum++;
	
	ArrayList<Person> friends = ListofBuddies(g, currentmember);

	
	for (int i = 0; i < friends.size(); i++) {
		int indexOfFriend = g.map.get(friends.get(i).name);
		if (visited[indexOfFriend]) {
			prev[currentIndex] = Math.min(prev[currentIndex], dfsnum[indexOfFriend]);
		}
		else {
			DepthFirstSearch(g, friends.get(i), dfsnum, visited, prev, tempnum, connectors);
			
			if (dfsnum[currentIndex] > prev[indexOfFriend]) {
				prev[currentIndex] = Math.min(prev[currentIndex], prev[indexOfFriend]);
			}
			else {
				System.out.println(currentmember.name);
				String temporarystring = currentmember.name;
				
				if (dfsnum[currentIndex] == 0) {

					if (i == friends.size()-1) {

						if (!connectors.contains(temporarystring))
							connectors.add(temporarystring);
					}
	
				}


				else {

					if (!connectors.contains(temporarystring))
						connectors.add(temporarystring);
				}
			}
		}
	}

	return;
}

private static Person PersonsName(Graph g, String name) {
	return g.members[g.map.get(name)];
}


private static ArrayList<Person> ListofBuddies(Graph g, Person currentperson) {

	ArrayList<Person> friends = new ArrayList<Person>();
	Friend anotherone = currentperson.first;
	while (anotherone != null) {
		Person e = g.members[anotherone.fnum];			
		friends.add(e);
		anotherone = anotherone.next;
	}

	return friends;

	}
}

