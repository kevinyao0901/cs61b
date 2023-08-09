package bearmaps.hw4;

import bearmaps.proj2ab.ArrayHeapMinPQ;
import edu.princeton.cs.algs4.Stopwatch;

import java.util.*;

public class AStarSolver<Vertex> implements ShortestPathsSolver<Vertex>{

    private SolverOutcome outcome;
    private LinkedList<Vertex> solution=new LinkedList<>();
    private double solutionWeight;
    private int numStatesExplored;
    private double explorationTime;
    private final double INF=Double.POSITIVE_INFINITY;

    public AStarSolver(AStarGraph<Vertex> input, Vertex start, Vertex end, double timeout)
    {
        ArrayHeapMinPQ<Vertex> PQ=new ArrayHeapMinPQ<>();
        Map<Vertex,Double> distToStart=new HashMap<>();
        Map<Vertex,Double> distToEnd=new HashMap<>();
        Map<Vertex,Vertex> edgeTo=new HashMap<>();

        Stopwatch sw=new Stopwatch();
        distToStart.put(start,0.0);
        PQ.add(start,distToStart.get(start));

        while(PQ.size()!=0)
        {
            if(PQ.getSmallest().equals(end))
            {
                outcome=SolverOutcome.SOLVED;
                solutionWeight=distToStart.get(end);

                Vertex curVertex=PQ.getSmallest();
                solution.addFirst(curVertex);
                while(!curVertex.equals(start))
                {
                    solution.addFirst(edgeTo.get(curVertex));
                    curVertex=edgeTo.get(curVertex);
                }

                explorationTime=sw.elapsedTime();
                return;
            }

            List<WeightedEdge<Vertex>> neighborEdges=input.neighbors(PQ.removeSmallest());
            numStatesExplored+=1;

            explorationTime=sw.elapsedTime();
            if(explorationTime>timeout)
            {
                outcome=SolverOutcome.TIMEOUT;
                solution=new LinkedList<>();
                solutionWeight=0;
                return;
            }

            for(WeightedEdge<Vertex> edge:neighborEdges)
            {
                Vertex source=edge.from();
                Vertex dest=edge.to();
                double weight=edge.weight();

                if(!distToStart.containsKey(dest))
                {
                    distToStart.put(dest,INF);
                }

                if(!distToEnd.containsKey(dest))
                {
                    distToEnd.put(dest,input.estimatedDistanceToGoal(dest,end));
                }

                if(distToStart.get(source)+weight<distToStart.get(dest))
                {
                    distToStart.put(dest,distToStart.get(source)+weight);

                    edgeTo.put(dest,source);
                    if(PQ.contains(dest))
                    {
                        PQ.changePriority(dest,distToStart.get(dest)+distToEnd.get(dest));
                    }
                    else
                    {
                        PQ.add(dest,distToStart.get(dest)+distToEnd.get(dest));
                    }
                }
            }
        }

        outcome=SolverOutcome.UNSOLVABLE;
        solution=new LinkedList<>();
        solutionWeight=0;
        explorationTime=sw.elapsedTime();
    }

    @Override
    public SolverOutcome outcome() {
        return outcome;
    }

    @Override
    public List<Vertex> solution() {
        return solution;
    }

    @Override
    public double solutionWeight() {
        return solutionWeight;
    }

    @Override
    public int numStatesExplored() {
        return numStatesExplored;
    }

    @Override
    public double explorationTime() {
        return explorationTime;
    }
}
