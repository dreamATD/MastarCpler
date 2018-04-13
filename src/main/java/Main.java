import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

import java.io.*;

public class Main {
    public static void main (String[] args) throws IOException {
        InputStream is = new FileInputStream ("example/test2.cpp");
        ANTLRInputStream input = new ANTLRInputStream (is);
        MxStarLexer lexer = new MxStarLexer (input);
        CommonTokenStream tokens = new CommonTokenStream (lexer);
        MxStarParser parser = new MxStarParser (tokens);
        ParseTree tree = parser.code();

        System.out.println(System.getProperty("user.id"));

        System.out.println("LISP:");
        System.out.println(tree.toStringTree(parser));
        System.out.println();

        System.out.println("Visitor:");
        BuildAstVisitor buildByVisitor = new BuildAstVisitor();
        Node root = buildByVisitor.visit(tree);
        TempTestAst tester = new TempTestAst();
        tester.dfs(root, "");
        System.out.println();

//        System.out.println("Listener:");
//        ParseTreeWalker walker = new ParseTreeWalker();
//        Evaluator evalByListener = new Evaluator();
//        walker.walk(evalByListener, tree);
    }
}