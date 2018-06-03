import BackEnd.CodeGen;
import FrontEnd.*;
import GeneralDataStructure.LinearIR;
import GeneralDataStructure.MyListClass.MyList;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

import java.io.*;
import java.util.ArrayList;

public class Main {
    public static void main (String[] args) throws IOException, Exception {
        String fileName = "test";
        InputStream is = new FileInputStream ("example/" + fileName + ".txt");
        ANTLRInputStream input = new ANTLRInputStream (is);
        MxStarLexer lexer = new MxStarLexer (input);
        CommonTokenStream tokens = new CommonTokenStream (lexer);
        MxStarParser parser = new MxStarParser (tokens);
        parser.setErrorHandler( new BailErrorStrategy());
        ParseTree tree = parser.code();

//        System.out.println("LISP:");
//        System.out.println(tree.toStringTree(parser));
//        System.out.println();

        // build an AST
//        System.out.println("Visitor:");
        BuildAstVisitor buildByVisitor = new BuildAstVisitor();
        Node root = buildByVisitor.visit(tree);

        // print the AST
//        TempTestAst tester = new TempTestAst();
//        tester.dfs(root, "");

        // build the scopes
        ScopeBuilder scopeBuilder = new ScopeBuilder();
        scopeBuilder.codeResolver(root);
//        System.out.println();

        // Check Semantic Error;
        SemanticChecker checker = new SemanticChecker(scopeBuilder.genScope);
        try {
            root.accept(checker);
        } catch (SyntaxError e) {
            System.out.println(e.toString() + " on Line: " + e.loc.line + ",  Column: " + e.loc.column);
            throw e;
        }

//         print the scopes
//        scopeBuilder.genScope.dfs("");
//        System.out.println();
        IRBuilder irBuilder = new IRBuilder();
        LinearIR irCode = irBuilder.generateIR(root);
//        irCode.print();

        CodeGen codeGenerator = new CodeGen(irCode);
        ArrayList<String> codes = codeGenerator.generateCode();

//        System.out.println();
        String content = "";
        for (int i = 0; i < codes.size(); ++i) {
            content += codes.get(i) + '\n';
            System.out.println(codes.get(i));
        }
//        System.out.print(content);

        File file = new File("output/" + fileName + ".asm");
        OutputStream out = new FileOutputStream(file);
        if (!file.exists()) file.createNewFile();
        out.write(content.getBytes());
        out.flush();
        out.close();
    }
}