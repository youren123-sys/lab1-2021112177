mport org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.*;
import java.util.Set;

@Test
public class testcomputeFloydWarshall {
        private GraphBuilder graphBuilder;
        graphBuilder = new GraphBuilder();
        graphBuilder.buildGraph("biden  Trump Obama Bush  Clinton Walker Reagan Carter Ford Nixon Biden Clinton ");
        Map<String, Map<String, Integer>> graph = getGraph(graphBuilder);

    String[] expected ={"biden","trump","obama"};

    String[] actual =graph.shortestPath("biden", "obama").toString();
    assertEquals(expected, actual);
    private Map<String, Map<String, Integer>> getGraph(GraphBuilder graphBuilder) {
        try {
            java.lang.reflect.Field field = GraphBuilder.class.getDeclaredField("graph");
            field.setAccessible(true);
            return (Map<String, Map<String, Integer>>) field.get(graphBuilder);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }

}

