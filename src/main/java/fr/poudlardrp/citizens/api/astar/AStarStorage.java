package fr.poudlardrp.citizens.api.astar;

public interface AStarStorage {
    void close(AStarNode node);

    AStarNode getBestNode();

    void open(AStarNode node);

    AStarNode removeBestNode();

    boolean shouldExamine(AStarNode neighbour);
}
