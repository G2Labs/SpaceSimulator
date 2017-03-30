package spaceSim;

import java.util.List;

public class Message {
	private List<Dot> listOfDots;
	public Message(List<Dot> list){
		listOfDots=list;
	}
	public List<Dot> getData(){
		return listOfDots;
	}
}
