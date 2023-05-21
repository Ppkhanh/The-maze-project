package mazegen;

import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

public class DijkstraSolve {
	private Block current;
	private final Block [][] blocks;
	
	public DijkstraSolve(Block[][] blocks, MazePanel panel) {
		this.blocks = blocks;
		current = blocks[panel.ROWS/2-1][panel.COLS/2-1];
		
		startCalculating(panel);
	}

	private void startCalculating(MazePanel panel) {
		/*
		 * 1.mark distance of beginning node as 0
		 * 2. visit neighbors
		 * 3. update distance of neighbors to current.distance + 1
		 * 4. choose the neighbor, add it to queue
		 * 5. mark the neighbor as visited
		 * 6. search adjacent neighbors of this one
		 * 7. if the neighbors distance is bigger than currentneighbors.distance + 1, then update distance
		 * 8. repeat as long as there are unvisited neighbors/queue is not empty..
		 * 
		 */
		current.setDistance(0);
		//for other starting squares as well
		panel.getBlocks()[panel.ROWS/2-1][panel.COLS/2].setDistance(0);
		panel.getBlocks()[panel.ROWS/2][panel.COLS/2-1].setDistance(0);
		panel.getBlocks()[panel.ROWS/2][panel.COLS/2].setDistance(0);
		
		current.setParent(null);
		current.setVisitedDIJK(true);
		
		Queue<Block> visitedQueue = new PriorityQueue<Block>(new DistanceComparator());
		visitedQueue.offer(current);
		
		int unvisitedCounter = (panel.ROWS-2)*(panel.COLS-2)-1;
		
		while(unvisitedCounter > 0) {
			
			List<Block> adjacentBlocks = current.getValidMoveNeighborsTEST();	//take neighbors from current cell
			
			for(int i = 0; i < adjacentBlocks.size(); i++) {
				
				if(adjacentBlocks.get(i).isVisitedDIJK() == false) {	//we have not visited the cell yet
					
					adjacentBlocks.get(i).setVisitedDIJK(true);			//now we have
					visitedQueue.offer(adjacentBlocks.get(i));			//put in queue, which automatically sorts ascending order of distances (smallest first, then sec. smallest...)
					
					if(adjacentBlocks.get(i).getDistance() > current.getDistance() + 1) {	
						adjacentBlocks.get(i).setDistance(current.getDistance() + 1);
						adjacentBlocks.get(i).setParent(current);	//marking parents so we can get the actual shortest path to each cell :)
					}
				}
			}
			current = visitedQueue.poll();			//gets the head of the queue (smallest distance) and makes it current cell
			--unvisitedCounter;
		}
		
		blocks[1][0].setDistance(blocks[1][1].getDistance() + 1);
		blocks[1][19].setDistance(blocks[1][18].getDistance() + 1);
		blocks[18][0].setDistance(blocks[18][1].getDistance() + 1);
		blocks[18][19].setDistance(blocks[18][18].getDistance() + 1);
		
//		System.out.println("1, 0 Distanz: "+blocks[1][0].getDistance());
//		System.out.println("1, 19 Distanz: "+blocks[1][19].getDistance());
//		System.out.println("18, 0 Distanz: "+blocks[18][0].getDistance());
//		System.out.println("18, 19 Distanz: "+blocks[18][19].getDistance());
		
}

	class DistanceComparator implements Comparator<Block>{

		@Override
		public int compare(Block o1, Block o2) {
			if(o1.getDistance() > o2.getDistance()) {		//ascending order
			return 1;
			}
			else if(o1.getDistance() < o2.getDistance()) {
				return -1;
			}
			return 0;
		}
	
	}
	

	
	
	
}
