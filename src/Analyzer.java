import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;
import org.antlr.v4.gui.TreeViewer;

public class Analyzer {
    public static void main(String[] args) throws IOException, InterruptedException {

        // The parser work
        String fileName;
        String[] arguments;
        Scanner in = new Scanner(System.in);
        System.out.print("File name: ");
        fileName = in.nextLine();
        System.out.print("Arguments: ");
        arguments = in.nextLine().split(" ");
        CharStream cs = CharStreams.fromPath(Paths.get("../test-cases/" + fileName + ".c"));
        String[] baseCode = cs.toString().split(System.getProperty("line.separator"));
        StringBuilder code = new StringBuilder(cs.toString());
        CLexer lexer = new CLexer(cs);
        TokenStream tokenStream = new CommonTokenStream(lexer);
        CParser parser = new CParser(tokenStream);
        ParseTree tree = parser.compilationUnit();
        InitListener initListener = new InitListener();
        MyListener listener = new MyListener(code, fileName);
        ParseTreeWalker.DEFAULT.walk(initListener, tree);
        ArrayList<Range> ranges = initListener.getRanges();
        ParseTreeWalker.DEFAULT.walk(listener, tree);
//        TreeViewer treeViewer = new TreeViewer(Arrays.asList(parser.getRuleNames()), tree);
//        treeViewer.open();

        // Generating the alternated code
        BufferedWriter writer = new BufferedWriter(new FileWriter("../test-cases/temp/" + fileName + "-modified.c"));
        writer.write(code.toString());
        writer.close();

        // Running the code (to verify logic has not changed)

        ArrayList<String> allArgs = new ArrayList<String>();
        allArgs.add("../test-cases/temp/" + fileName);
        Collections.addAll(allArgs, arguments);
        ProcessBuilder pb = new ProcessBuilder();
        pb.command("gcc", "../test-cases/temp/" + fileName + "-modified.c", "-o", "../test-cases/temp/" + fileName).start().waitFor();
        Process p = pb.command(allArgs).start();

        BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
        StringBuilder builder = new StringBuilder();
        String line = null;
        while ((line = reader.readLine()) != null) {
            builder.append(line);
            builder.append("\n");
        }

        System.out.println(builder.toString());

        // Processing the output
        CharStream cs2 = CharStreams.fromPath(Paths.get("../test-cases/temp/"  + fileName + ".txt"));
        int[] executed = Stream.of(cs2.toString().split("\r\n")).mapToInt(Integer::parseInt).toArray();
        int linesCount = baseCode.length;
        boolean[] lines = new boolean[linesCount];
        Arrays.fill(lines, false);
        for (int i=0; i<listener.getExecutionCounter(); i++) {
            Arrays.fill(lines, ranges.get(i).getStart()-1, ranges.get(i).getStop(), Arrays.stream(executed).boxed().collect(Collectors.toList()).contains(i));
        }

        String pre = CharStreams.fromPath(Paths.get("../view-template/0.txt")).toString();
        String mid1 = CharStreams.fromPath(Paths.get("../view-template/1.txt")).toString();
        String mid2 = CharStreams.fromPath(Paths.get("../view-template/2.txt")).toString();
        String mid3 = CharStreams.fromPath(Paths.get("../view-template/3.txt")).toString();
        String post = CharStreams.fromPath(Paths.get("../view-template/4.txt")).toString();

        StringBuilder markup = new StringBuilder(pre);
        markup.append(fileName + ".c");
        markup.append(mid1);
        markup.append(Arrays.toString(arguments));
        markup.append(mid2);
        for (int i=0; i<linesCount; i++) {
            if (!lines[i])
                markup.append(baseCode[i].replaceAll("<", "&lt;").replaceAll(">", "&gt;")).append("\n");
            else
                markup.append("<code>").append(baseCode[i].replaceAll("<", "&lt;").replaceAll(">", "&gt;")).append("</code>").append("\n");
        }
        markup.append(mid3);
        markup.append(builder.toString().substring(0, builder.length()-1));
        markup.append(post);

        BufferedWriter writer2 = new BufferedWriter(new FileWriter("../test-cases/" + fileName + ".html"));
        writer2.write(markup.toString());
        writer2.close();

    }
}