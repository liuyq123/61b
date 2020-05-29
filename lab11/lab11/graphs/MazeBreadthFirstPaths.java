package lab11.graphs;

import java.util.ArrayDeque;
import java.util.Deque;
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
        maze = m;
        s = maze.xyTo1D(sourceX, sourceY);
        t = maze.xyTo1D(targetX, targetY);
        distTo[s] = 0;
        edgeTo[s] = s;
    }

    /** Conducts a breadth first search of the maze starting at the source. */
    private void bfs(int v) {
        marked[v] = true;
        announce();

        if (v == t) {
            targetFound = true;
        }

        if (targetFound) {
            return;
        }

        Queue<Integer> visited = new ArrayDeque<>();
        for (int w : maze.adj(v)) {
            visited.add(w);
            if (!marked[v]) {
                edgeTo[w] = v;
                announce();
                distTo[w] = distTo[v] + 1;
                if (targetFound) {
                    return;
                }
            }
            while (!visited.isEmpty()) {
                bfs(visited.remove());
            }
        }
    }


    @Override
    public void solve() {
        bfs(s);
    }
}

