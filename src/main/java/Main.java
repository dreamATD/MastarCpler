import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

import java.io.*;

public class Main {
    public static void main (String[] args) throws IOException, SyntaxError {
        InputStream is = new FileInputStream ("example/helloworld.txt");
        ANTLRInputStream input = new ANTLRInputStream (is);
        MxStarLexer lexer = new MxStarLexer (input);
        CommonTokenStream tokens = new CommonTokenStream (lexer);
        MxStarParser parser = new MxStarParser (tokens);
        ParseTree tree = parser.code();

        System.out.println("LISP:");
        System.out.println(tree.toStringTree(parser));
        System.out.println();

        // build an AST
        System.out.println("Visitor:");
        BuildAstVisitor buildByVisitor = new BuildAstVisitor();
        Node root = buildByVisitor.visit(tree);

        // print the AST
        TempTestAst tester = new TempTestAst();
        tester.dfs(root, "");

        // build the scopes
        ScopeBuilder scopeBuilder = new ScopeBuilder();
        scopeBuilder.codeResolver(root);
        System.out.println();

        // print the scopes
		Scope.dfs(scopeBuilder.genScope, "");
        // Check Semantic Error;
        SemanticChecker checker = new SemanticChecker(scopeBuilder.genScope);
        try {
            checker.visit(root);
        } catch (SyntaxError e) {
            System.out.println(e.toString() + " on Line: " + e.loc.line + ",  Column: " + e.loc.column);
            throw e;
        }
//        System.out.println("Listener:");
//        ParseTreeWalker walker = new ParseTreeWalker();
//        Evaluator evalByListener = new Evaluator();
//        walker.walk(evalByListener, tree);
    }
}