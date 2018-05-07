import FrontEnd.*;
import GeneralDataStructure.LinearIR;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

import java.io.*;

public class Main {
    public static void main (String[] args) throws IOException, Exception {
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

        // Check Semantic Error;
        SemanticChecker checker = new SemanticChecker(scopeBuilder.genScope);
        try {
            root.accept(checker);
        } catch (SyntaxError e) {
            System.out.println(e.toString() + " on Line: " + e.loc.line + ",  Column: " + e.loc.column);
            throw e;
        }

        // print the scopes
        scopeBuilder.genScope.dfs("");
        System.out.println();

        IRBuilder irBuilder = new IRBuilder();
        LinearIR irCode = irBuilder.generateIR(root);
        irCode.print();
    }
}