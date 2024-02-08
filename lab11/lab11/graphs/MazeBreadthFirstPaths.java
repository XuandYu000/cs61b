package lab11.graphs;

import edu.princeton.cs.algs4.LinkedQueue;

import java.util.LinkedList;
import java.util.Queue;

/**
 *  @author Josh Hug
 */
public class MazeBreadthFirstPaths extends MazeExplorer {
    /* Inherits public fields:
    public int[] distTo;
    public int[] edgeTo;
    public boolean[] marked;
    */
    private int s;
    private int t;
    private boolean targetFound = false;
    private Maze maze;

    public MazeBreadthFirstPaths(Maze m, int sourceX, int sourceY, int targetX, int targetY) {
        super(m);
        // Add more variables here!
        maze = m;
        s = maze.xyTo1D(sourceX, sourceY);
        t = maze.xyTo1D(targetX, targetY);
        distTo[s] = 0;
        edgeTo[s] = s;
    }

    /** Conducts a breadth first search of the maze starting at the source. */
    private void bfs() {
        Queue<Integer> que = new LinkedList<>();
        marked[s] = true;
        announce();
        que.add(s);
        while(!que.isEmpty()) {
            int now = que.remove();
            if (now == t) {
                targetFound = true;
                break;
            }
            for (int nextP : maze.adj(now)) {
                if (!marked[nextP]) {
                   edgeTo[nextP] = now;
                   marked[nextP] = true;
                   announce();
                   distTo[nextP] = distTo[now] + 1;
                   que.add(nextP);
                }
            }
        }
        if (targetFound) {
            return;
        }
    }


    @Override
    public void solve() {
         bfs();
    }
}

