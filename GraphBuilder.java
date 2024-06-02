import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class GraphBuilder {
    private Map<String, Map<String, Integer>> graph;
    private Map<String, Map<String, Integer>> dist;
    private Map<String, Map<String, String>> next;
    private Random random;

    public GraphBuilder() {
        graph = new HashMap<>();
        random = new Random();
    }

    public void buildGraph(String content) {
        StringTokenizer tokenizer = new StringTokenizer(content);
        String prevWord = null;

        while (tokenizer.hasMoreTokens()) {
            String currentWord = tokenizer.nextToken().toLowerCase();

            if (prevWord != null) {
                graph.putIfAbsent(prevWord, new HashMap<>());
                Map<String, Integer> edges = graph.get(prevWord);
                edges.put(currentWord, edges.getOrDefault(currentWord, 0) + 1);
            }

            prevWord = currentWord;
        }
        computeFloydWarshall();
    }

    private void computeFloydWarshall() {
        dist = new HashMap<>();
        next = new HashMap<>();

        for (String u : graph.keySet()) {
            dist.putIfAbsent(u, new HashMap<>());
            next.putIfAbsent(u, new HashMap<>());
            for (String v : graph.keySet()) {
                if (u.equals(v)) {
                    dist.get(u).put(v, 0);
                } else {
                    dist.get(u).put(v, Integer.MAX_VALUE / 2); // Use large value to prevent overflow
                }
                next.get(u).put(v, null);
            }
        }

        for (String u : graph.keySet()) {
            for (Map.Entry<String, Integer> entry : graph.get(u).entrySet()) {
                String v = entry.getKey();
                int weight = entry.getValue();
                dist.get(u).put(v, weight);
                next.get(u).put(v, v);
            }
        }

        for (String k : graph.keySet()) {
            for (String i : graph.keySet()) {
                for (String j : graph.keySet()) {
                    if (dist.get(i).get(k) + dist.get(k).get(j) < dist.get(i).get(j)) {
                        dist.get(i).put(j, dist.get(i).get(k) + dist.get(k).get(j));
                        next.get(i).put(j, next.get(i).get(k));
                    }
                }
            }
        }
    }

    public void printGraph() {
        for (Map.Entry<String, Map<String, Integer>> entry : graph.entrySet()) {
            String word = entry.getKey();
            Map<String, Integer> edges = entry.getValue();
            for (Map.Entry<String, Integer> edge : edges.entrySet()) {
                System.out.println(word + " -> " + edge.getKey() + " : " + edge.getValue());
            }
        }
    }

    public Set<String> findBridgeWords(String word1, String word2) {
        Set<String> bridgeWords = new HashSet<>();

        if (graph.containsKey(word1)) {
            Map<String, Integer> neighbors = graph.get(word1);
            for (String neighbor : neighbors.keySet()) {
                if (graph.containsKey(neighbor) && graph.get(neighbor).containsKey(word2)) {
                    bridgeWords.add(neighbor);
                }
            }
        }

        return bridgeWords;
    }

    public String generateNewText(String inputText) {
        StringTokenizer tokenizer = new StringTokenizer(inputText);
        ArrayList<String> words = new ArrayList<>();
        while (tokenizer.hasMoreTokens()) {
            words.add(tokenizer.nextToken().toLowerCase());
        }

        StringBuilder newText = new StringBuilder();
        for (int i = 0; i < words.size(); i++) {
            newText.append(words.get(i));
            if (i < words.size() - 1) {
                Set<String> bridgeWords = findBridgeWords(words.get(i), words.get(i + 1));
                if (!bridgeWords.isEmpty()) {
                    int index = new Random().nextInt(bridgeWords.size());
                    String bridgeWord = new ArrayList<>(bridgeWords).get(index);
                    newText.append(" ").append(bridgeWord);
                }
            }
            if (i < words.size() -            1) {
                newText.append(" ");
            }
        }

        return newText.toString();
    }

    public List<String> shortestPath(String start, String end) {
        if (!next.containsKey(start) || !next.containsKey(end)) {
            return Collections.emptyList();
        }

        List<String> path = new ArrayList<>();
        String at = start;
        while (at != null && !at.equals(end)) {
            path.add(at);
            at = next.get(at).get(end);
        }
        if (at == null) {
            return Collections.emptyList();
        }
        path.add(end);
        return path;
    }

    public int getPathWeight(String start, String end) {
        return dist.getOrDefault(start, Collections.emptyMap()).getOrDefault(end, Integer.MAX_VALUE / 2);
    }

    public void randomWalk(String startNode) {
        Set<String> visitedEdges = new HashSet<>();
        List<String> path = new ArrayList<>();
        String currentNode = startNode;

        while (true) {
            path.add(currentNode);
            Map<String, Integer> edges = graph.get(currentNode);
            if (edges == null || edges.isEmpty()) {
                break;
            }

            List<String> neighbors = new ArrayList<>(edges.keySet());
            String nextNode = neighbors.get(random.nextInt(neighbors.size()));
            String edge = currentNode + "->" + nextNode;

            if (visitedEdges.contains(edge)) {
                break;
            }

            visitedEdges.add(edge);
            currentNode = nextNode;

            System.out.println("Current Path: " + String.join(" -> ", path));
            System.out.print("Do you want to stop? (yes/no): ");
            Scanner scanner = new Scanner(System.in);
            String input = scanner.nextLine().toLowerCase();
            if (input.equals("yes")) {
                break;
            }
        }

        savePathToFile(path);
    }

    private void savePathToFile(List<String> path) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("random_walk_path.txt"))) {
            for (String node : path) {
                writer.write(node + "\n");
            }
            System.out.println("Path saved to random_walk_path.txt");
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }
}

