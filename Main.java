import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        System.out.println("commit1");
        System.out.println("commit2");
        try {
            // 从控制台读取文件路径
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            System.out.print("Enter file path: ");
            String filePath = reader.readLine();

            // 读取文件内容
            String content = FileReader.readFile(filePath);

            // 处理文本内容
            String processedContent = TextProcessor.processText(content);

            // 构建有向图
            GraphBuilder graphBuilder = new GraphBuilder();
            graphBuilder.buildGraph(processedContent);

            // 显示菜单
            while (true) {
                System.out.println("\nChoose an option:");
                System.out.println("1. Display the directed graph");
                System.out.println("2. Find bridge words");
                System.out.println("3. Generate new text with bridge words");
                System.out.println("4. Calculate shortest path");
                System.out.println("5. Perform a random walk");
                System.out.println("6. Exit");

                // 读取用户选择
                System.out.print("Enter your choice: ");
                String choice = reader.readLine();

                switch (choice) {
                    case "1":
                        // 显示有向图
                        System.out.println("\nDirected Graph:");
                        graphBuilder.printGraph();
                        break;
                    case "2":
                        // 查询桥接词
                        System.out.print("Enter the first word: ");
                        String word1 = reader.readLine().toLowerCase();
                        System.out.print("Enter the second word: ");
                        String word2 = reader.readLine().toLowerCase();
                        Set<String> bridgeWords = graphBuilder.findBridgeWords(word1, word2);
                        System.out.println("Bridge words between \"" + word1 + "\" and \"" + word2 + "\": " + bridgeWords);
                        break;
                    case "3":
                        // 生成新文本
                        System.out.print("Enter the new text: ");
                        String newText = reader.readLine();
                        String generatedText = graphBuilder.generateNewText(newText);
                        System.out.println("Generated text with bridge words: " + generatedText);
                        break;
                    case "4":
                        // 计算最短路径
                        System.out.print("Enter the start word: ");
                        String startWord = reader.readLine().toLowerCase();
                        System.out.print("Enter the end word: ");
                        String endWord = reader.readLine().toLowerCase();
                        List<String> shortestPath = graphBuilder.shortestPath(startWord, endWord);
                        if (shortestPath.isEmpty()) {
                            System.out.println("No path found between \"" + startWord + "\" and \"" + endWord + "\".");
                        } else {
                            System.out.println("Shortest path between \"" + startWord + "\" and \"" + endWord + "\": " + shortestPath);
                            System.out.println("Length of the shortest path: " + graphBuilder.getPathWeight(startWord, endWord));
                        }
                        break;
                    case "5":
                        // 随机游走
                        System.out.print("Enter the starting word for random walk: ");
                        String startNode = reader.readLine().toLowerCase();
                        graphBuilder.randomWalk(startNode);
                        break;
                    case "6":
                        // 退出程序
                        System.out.println("Exiting program...");
                        return;
                    default:
                        System.out.println("Invalid choice. Please enter a valid option.");
                }
            }

        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
    }
}
