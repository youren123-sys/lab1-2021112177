import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.*;

public class GraphBuilderTest {
    private GraphBuilder graphBuilder;

    @Before
    public void init() {
        graphBuilder = new GraphBuilder();
        graphBuilder.buildGraph("biden  Trump Obama Bush  Clinton Walker Reagan Carter Ford Nixon Biden Clinton ");
        Map<String, Map<String, Integer>> graph = getGraph(graphBuilder);
    }

    @Test
    public void findBridgeWords() {
        init();
        String[] expected = {""};
        String actual = graphBuilder.findBridgeWords("biden", "").toString();
        assertEquals(expected, actual);
    }


    private Map<String, Map<String, Integer>> getGraph(GraphBuilder graphBuilder) {
        try {
            java.lang.reflect.Field field = GraphBuilder.class.getDeclaredField("graph");
            field.setAccessible(true);
            return (Map<String, Map<String, Integer>>) field.get(graphBuilder);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }


}
