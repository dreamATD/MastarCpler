package FrontEnd;
import GeneralDataStructure.TypeSystem.*;

import org.antlr.v4.runtime.tree.TerminalNode;

public class BuildAstVisitor extends MxStarBaseVisitor <Node> {
	@Override public CodeNode visitCode(MxStarParser.CodeContext ctx) {
		CodeNode res = new CodeNode();
		for (int i = 0; i < ctx.getChildCount(); ++i) {
			res.sons.add(visit(ctx.getChild(i)));
		}
		return res;
	}
	@Override public ClassDefNode visitClassDefinition(MxStarParser.ClassDefinitionContext ctx) {
		ClassDefNode res = new ClassDefNode();
		res.sons.addAll(visit(ctx.classBody()).sons);
		String className = ctx.classId().getText();
		for (int i = 0; i < res.sons.size(); ++i) {
			Node son = res.sons.get(i);
			if (son instanceof ConsFuncDefNode && son.id == null) son.id = className;
		}
		res.id = className;
		res.loc = new Location(ctx.classId().start);
		return res;
	}

	@Override public FuncDefNode visitFunctionDefinition(MxStarParser.FunctionDefinitionContext ctx) {
		FuncDefNode res = new FuncDefNode();
		res.type = TypeRef.buildTypeRef(ctx.typeId().getText());
		res.id = ctx.functionId().getText();
		res.sons.addAll(visit(ctx.parameterList()).sons);
		res.sons.add(visit(ctx.functionBody()));
		res.loc = new Location(ctx.start);
		return res;
	}
	@Override public Node visitCompStat(MxStarParser.CompStatContext ctx) {
		return visit(ctx.compoundStatement());
	}

	@Override public Node visitExprStat(MxStarParser.ExprStatContext ctx) {
		return visit(ctx.expressionStatement());
	}
	@Override public Node visitIterStat(MxStarParser.IterStatContext ctx) {
		return visit(ctx.iterativeStatement());
	}
	@Override public Node visitJumpStat(MxStarParser.JumpStatContext ctx) {
		return visit(ctx.jumpStatement());
	}
	@Override public Node visitNullStat(MxStarParser.NullStatContext ctx) {
		return visit(ctx.nullStatement());
	}
	@Override public Node visitVdefStat(MxStarParser.VdefStatContext ctx) {
		return visit(ctx.varDefStatement());
	}
	@Override public ClassDefNode visitClassBody(MxStarParser.ClassBodyContext ctx) {
		ClassDefNode res = new ClassDefNode();
		for (MxStarParser.VarDefStatementWithoutInitContext data : ctx.varDefStatementWithoutInit()) {
			res.sons.add(visit(data));
		}
		for (MxStarParser.FunctionDefinitionContext data : ctx.functionDefinition()) {
			res.sons.add(visit(data));
		}
		if (ctx.constructionFunction() != null) res.sons.add(visit(ctx.constructionFunction()));
		else {
			ConsFuncDefNode son = new ConsFuncDefNode();
			son.type = TypeRef.buildTypeRef("void");
			son.id = null;
			son.sons.add(new NullStatNode());
			res.sons.add(son);
		}
		res.loc = new Location(ctx.start);
		return res;
	}
	@Override public ConsFuncDefNode visitConstructionFunction(MxStarParser.ConstructionFunctionContext ctx) {
		ConsFuncDefNode res = new ConsFuncDefNode();
		res.id = ctx.classId().getText();
		res.type = new VoidTypeRef();
		res.sons.add(visit(ctx.functionBody()));
		res.loc = new Location(ctx.start);
		return res;
	}
	@Override public FuncDefNode visitParameterList(MxStarParser.ParameterListContext ctx) {
		FuncDefNode res = new FuncDefNode();
		for (MxStarParser.ParameterContext data: ctx.parameter()) {
			res.sons.add(visit(data));
		}
		res.loc = new Location(ctx.start);
		return res;
	}
	@Override public VarDefNode visitParameter(MxStarParser.ParameterContext ctx) {
		VarDefNode res = new VarDefNode();
		res.id = ctx.variableId().getText();
		res.type = TypeRef.buildTypeRef(ctx.typeId().getText());
		res.loc = new Location(ctx.start);
		return res;
	}
	@Override public Node visitFunctionBody(MxStarParser.FunctionBodyContext ctx) {
		return visit(ctx.compoundStatement());
	}
	@Override public CompStatNode visitCompoundStatement(MxStarParser.CompoundStatementContext ctx) {
		CompStatNode res = new CompStatNode();
		for (int i = 0; i < ctx.statement().size(); ++i) {
			res.sons.add(visit(ctx.statement(i)));
		}
		res.loc = new Location(ctx.start);
		return res;
	}
	@Override public ExprStatNode visitExpressionStatement(MxStarParser.ExpressionStatementContext ctx) {
		ExprStatNode res = new ExprStatNode();
		res.sons.add(visit(ctx.expression()));
		res.loc = new Location(ctx.start);
		return res;
	}
	@Override public CondStatNode visitConditionalStatement(MxStarParser.ConditionalStatementContext ctx) {
		if (ctx.Else().size() == 0) {
			IfStatNode res = new IfStatNode();
			res.sons.add(visit(ctx.expression(0)));
			res.sons.add(visit(ctx.statement(0)));
			res.loc = new Location(ctx.start);
			res.markForIf = false;
			return res;
		} else {
			IfElseStatNode res = new IfElseStatNode();
			for (int i = 0; i < ctx.If().size(); ++i) {
				IfStatNode tmp = new IfStatNode();
				tmp.sons.add(visit(ctx.expression(i)));
				tmp.sons.add(visit(ctx.statement(i)));
				tmp.markForIf = true;
				res.sons.add(tmp);
			}
			res.sons.add(visit(ctx.statement(ctx.statement().size()-1)));
			res.loc = new Location(ctx.start);
			return res;
		}
	}
	@Override public Node visitIterWhileStatement(MxStarParser.IterWhileStatementContext ctx) {
		return visit(ctx.whileStatement());
	}
	@Override public Node visitIterForStatement(MxStarParser.IterForStatementContext ctx) {
		return visit(ctx.forStatement());
	}
	@Override public WhileStatNode visitWhileStatement(MxStarParser.WhileStatementContext ctx) {
		WhileStatNode res = new WhileStatNode();
		res.sons.add(visit(ctx.expression()));
		res.sons.add(visit(ctx.statement()));
		res.loc = new Location(ctx.start);
		return res;
	}
	@Override public ForStatNode visitForStatement(MxStarParser.ForStatementContext ctx) {
		ForStatNode res = new ForStatNode();
		if (ctx.initExpr == null) res.sons.add(new EmptyExprNode());
		else res.sons.add(visit(ctx.initExpr));
		if (ctx.condExpr == null) res.sons.add(new EmptyExprNode());
		else res.sons.add(visit(ctx.condExpr));
		if (ctx.loopExpr == null) res.sons.add(new EmptyExprNode());
		else res.sons.add(visit(ctx.loopExpr));
		res.sons.add(visit(ctx.statement()));
		res.loc = new Location(ctx.start);
		return res;
	}
	@Override public RetStatNode visitRetJumpStatement(MxStarParser.RetJumpStatementContext ctx) {
		RetStatNode res = new RetStatNode();
		if (ctx.expression() != null) res.sons.add(visit(ctx.expression()));
		res.loc = new Location(ctx.start);
		return res;
	}
	@Override public BrkStatNode visitBrkJumpStatement(MxStarParser.BrkJumpStatementContext ctx) {
		BrkStatNode res = new BrkStatNode();
		res.loc = new Location(ctx.start);
		return res;
	}
	@Override public CtnStatNode visitCtnJumpStatement(MxStarParser.CtnJumpStatementContext ctx) {
		CtnStatNode res = new CtnStatNode();
		res.loc = new Location(ctx.start);
		return res;
	}
	@Override public NullStatNode visitNullStatement(MxStarParser.NullStatementContext ctx) {
		NullStatNode res = new NullStatNode();
		res.loc = new Location(ctx.start);
		return res;
	}
	@Override public Node visitVarDefStatement(MxStarParser.VarDefStatementContext ctx) {
		return visit(ctx.variableDefinition());
	}
	@Override public Node visitVarDefStatementWithoutInit(MxStarParser.VarDefStatementWithoutInitContext ctx) {
		return visit(ctx.variableDefinitionWithoutInit());
	}
	@Override public VarDefStatNode visitVariableDefinition(MxStarParser.VariableDefinitionContext ctx) {
		TypeRef type = TypeRef.buildTypeRef(ctx.typeId().getText());
		VarDefStatNode res = new VarDefStatNode();
		for (int i = 0; i < ctx.varDef().size(); ++i) {
			Node tmp = visit(ctx.varDef(i));
			tmp.type = type;
			res.sons.add(tmp);
		}
		res.type = type;
		res.loc = new Location(ctx.start);
		return res;
	}
	@Override public VarDefStatNode visitVariableDefinitionWithoutInit(MxStarParser.VariableDefinitionWithoutInitContext ctx) {
		TypeRef type = TypeRef.buildTypeRef(ctx.typeId().getText());
		VarDefStatNode res = new VarDefStatNode();
		for (int i = 0; i < ctx.Identifier().size(); ++i) {
			VarDefNode tmp = new VarDefNode();
			TerminalNode var = ctx.Identifier(i);
			tmp.id = var.getText();
			tmp.type = type;
			tmp.loc = new Location(var.getSymbol());
			res.sons.add(tmp);
		}
		res.type = type;
		res.loc = new Location(ctx.start);
		return res;
	}
	@Override public VarDefNode visitVarWithoutInit(MxStarParser.VarWithoutInitContext ctx) {
		VarDefNode res = new VarDefNode();
		res.id = ctx.Identifier().getText();
		res.loc = new Location(ctx.start);
		return res;
	}
	@Override public VarDefNode visitVarWithInit (MxStarParser.VarWithInitContext ctx) {
		VarDefNode res = new VarDefNode();
		res.id = ctx.Identifier().getText();
		res.sons.add(visit(ctx.expression()));
		res.loc = new Location(ctx.start);
		return res;
	}
	@Override public Node visitExpression(MxStarParser.ExpressionContext ctx) {
		return visit(ctx.assignmentExpr());
	}
	@Override public Node visitLiteralElemExpr(MxStarParser.LiteralElemExprContext ctx) {
		return visit(ctx.literal());
	}
	@Override public VarExprNode visitVarElemExpr(MxStarParser.VarElemExprContext ctx) {
		VarExprNode res = new VarExprNode();
		res.id = ctx.variableId().getText();
		res.loc = new Location(ctx.start);
		return res;
	}
	@Override public Node visitSonElemExpr(MxStarParser.SonElemExprContext ctx) {
		return visit(ctx.expression());
	}
	@Override public ObjAccExprNode visitObjAccPrimExpr(MxStarParser.ObjAccPrimExprContext ctx) {
		ObjAccExprNode res = new ObjAccExprNode();
		if (ctx.primaryExpr(0).getText().equals("this")) {
			VarExprNode son = new VarExprNode();
			son.loc = new Location(ctx.primaryExpr(0).start);
			son.id = "this";
			res.sons.add(son);
			res.sons.add(visit(ctx.primaryExpr(1)));
			res.loc = new Location(ctx.start);
		} else {
			res.sons.add(visit(ctx.primaryExpr(0)));
			res.sons.add(visit(ctx.primaryExpr(1)));
			res.loc = new Location(ctx.start);
		}
		return res;
	}
	@Override public Node visitElemPrimExpr(MxStarParser.ElemPrimExprContext ctx) {
		return visit(ctx.elementExpr());
	}
	@Override public FuncExprNode visitFuncPrimExpr(MxStarParser.FuncPrimExprContext ctx) {
		FuncExprNode res = new FuncExprNode();
		res.id = ctx.functionId().getText();
		if (ctx.argumentList() != null){
			res.sons.addAll(visit(ctx.argumentList()).sons);
		}
		res.loc = new Location(ctx.start);
		return res;
	}
	@Override public ArrExprNode visitArrPrimExpr(MxStarParser.ArrPrimExprContext ctx) {
		ArrExprNode res = new ArrExprNode();
		Node tmp = visit(ctx.primaryExpr());
		res.sons.add(tmp);
		res.id = tmp.id;
		res.loc = new Location(ctx.start);
		res.sons.add(visit(ctx.expression()));
		return res;
	}
	@Override public FuncExprNode visitArgumentList(MxStarParser.ArgumentListContext ctx) {
		FuncExprNode res = new FuncExprNode();
		for (int i = 0; i < ctx.expression().size(); ++i) {
			res.sons.add(visit(ctx.expression(i)));
		}
		res.loc = new Location(ctx.start);
		return res;
	}
	@Override public RightUnaryExprNode visitPmRUExpr(MxStarParser.PmRUExprContext ctx) {
		RightUnaryExprNode res = new RightUnaryExprNode();
		res.id = ctx.op.getText();
		res.sons.add(visit(ctx.rightUnaryExpr()));
		res.loc = new Location(ctx.start);
		return res;
	}
	@Override public Node visitPrimRUExpr(MxStarParser.PrimRUExprContext ctx) {
		return visit(ctx.primaryExpr());
	}
	@Override public Node visitRUExpr(MxStarParser.RUExprContext ctx) {
		return visit(ctx.rightUnaryExpr());
	}
	@Override public LeftUnaryExprNode visitLUExpr(MxStarParser.LUExprContext ctx) {
		LeftUnaryExprNode res = new LeftUnaryExprNode();
		res.id = ctx.op.getText();
		res.sons.add(visit(ctx.unaryExpr()));
		res.loc = new Location(ctx.start);
		return res;
	}
	@Override public Node visitNewUExpr(MxStarParser.NewUExprContext ctx) {
		NewExprNode res = new NewExprNode();
		res.sons.add(visit(ctx.newCreator()));
		res.loc = new Location(ctx.start);
		return res;
	}
	@Override public TypeExprNode visitNewCreator(MxStarParser.NewCreatorContext ctx) {
		int dimension = 0;
		String text = ctx.getText();
		for (int i = 0; i < text.length(); ++i) {
			if (text.charAt(i) == '[') ++dimension;
		}
		int i = -1;
		int size = ctx.expression().size();
		TypeExprNode res = new TypeExprNode(), tmp = res;
		for (int dim = 0; dim < dimension; ++dim) {
			if (dim < size) tmp.sons.add(visit(ctx.expression(dim)));
			else tmp.sons.add(new EmptyExprNode());
			TypeExprNode son = new TypeExprNode();
			tmp.sons.add(son);
			tmp = son;
			son.loc = new Location(ctx.start);
		}
		if (ctx.classId() != null) tmp.type = TypeRef.buildTypeRef(ctx.classId().getText());
		else tmp.type = TypeRef.buildTypeRef(ctx.simpleTypeId().getText());
		res.loc = new Location(ctx.start);
		return res;
	}
	@Override public Node visitUnaryMulExpr(MxStarParser.UnaryMulExprContext ctx) {
		return visit(ctx.unaryExpr());
	}
	@Override public BinaryExprNode visitMulExpr(MxStarParser.MulExprContext ctx) {
		BinaryExprNode res = new BinaryExprNode();
		res.id = ctx.op.getText();
		res.sons.add(visit(ctx.multiplicativeExpr()));
		res.sons.add(visit(ctx.unaryExpr()));
		res.loc = new Location(ctx.start);
		return res;
	}
	@Override public Node visitMulAddExpr(MxStarParser.MulAddExprContext ctx) {
		return visit(ctx.multiplicativeExpr());
	}
	@Override public BinaryExprNode visitAddExpr(MxStarParser.AddExprContext ctx) {
		BinaryExprNode res = new BinaryExprNode();
		res.id = ctx.op.getText();
		res.sons.add(visit(ctx.additiveExpr()));
		res.sons.add(visit(ctx.multiplicativeExpr()));
		res.loc = new Location(ctx.start);
		return res;
	}
	@Override public Node visitAddShiftExpr(MxStarParser.AddShiftExprContext ctx) {
		return visit(ctx.additiveExpr());
	}
	@Override public BinaryExprNode visitShftExpr(MxStarParser.ShftExprContext ctx) {
		BinaryExprNode res = new BinaryExprNode();
		res.id = ctx.op.getText();
		res.sons.add(visit(ctx.shiftExpr()));
		res.sons.add(visit(ctx.additiveExpr()));
		res.loc = new Location(ctx.start);
		return res;
	}
	@Override public Node visitShiftRelExpr(MxStarParser.ShiftRelExprContext ctx) {
		return visit(ctx.shiftExpr());
	}
	@Override public BinaryExprNode visitRelExpr(MxStarParser.RelExprContext ctx) {
		BinaryExprNode res = new BinaryExprNode();
		res.id = ctx.op.getText();
		res.sons.add(visit(ctx.relationExpr()));
		res.sons.add(visit(ctx.shiftExpr()));
		res.loc = new Location(ctx.start);
		return res;
	}
	@Override public Node visitRelEqualExpr(MxStarParser.RelEqualExprContext ctx) {
		return visit(ctx.relationExpr());
	}
	@Override public BinaryExprNode visitEqualExpr(MxStarParser.EqualExprContext ctx) {
		BinaryExprNode res = new BinaryExprNode();
		res.id = ctx.op.getText();
		res.sons.add(visit(ctx.equalityExpr()));
		res.sons.add(visit(ctx.relationExpr()));
		res.loc = new Location(ctx.start);
		return res;
	}
	@Override public Node visitEquAndExpr(MxStarParser.EquAndExprContext ctx) {
		return visit(ctx.equalityExpr());
	}
	@Override public BinaryExprNode visitAExpr(MxStarParser.AExprContext ctx) {
		BinaryExprNode res = new BinaryExprNode();
		res.id = ctx.op.getText();
		res.sons.add(visit(ctx.andExpr()));
		res.sons.add(visit(ctx.equalityExpr()));
		res.loc = new Location(ctx.start);
		return res;
	}
	@Override public BinaryExprNode visitXExpr(MxStarParser.XExprContext ctx) {
		BinaryExprNode res = new BinaryExprNode();
		res.id = ctx.op.getText();
		res.sons.add(visit(ctx.xorExpr()));
		res.sons.add(visit(ctx.andExpr()));
		res.loc = new Location(ctx.start);
		return res;
	}
	@Override public Node visitAndXorExpr(MxStarParser.AndXorExprContext ctx) {
		return visit(ctx.andExpr());
	}
	@Override public Node visitXorOrExpr(MxStarParser.XorOrExprContext ctx) {
		return visit(ctx.xorExpr());
	}
	@Override public BinaryExprNode visitOExpr(MxStarParser.OExprContext ctx) {
		BinaryExprNode res = new BinaryExprNode();
		res.id = ctx.op.getText();
		res.sons.add(visit(ctx.orExpr()));
		res.sons.add(visit(ctx.xorExpr()));
		res.loc = new Location(ctx.start);
		return res;
	}
	@Override public Node visitOrLogicalAndExpr(MxStarParser.OrLogicalAndExprContext ctx) {
		return visit(ctx.orExpr());
	}
	@Override public BinaryExprNode visitLogicalAExpr(MxStarParser.LogicalAExprContext ctx) {
		BinaryExprNode res = new BinaryExprNode();
		res.id = ctx.op.getText();
		res.sons.add(visit(ctx.logicalAndExpr()));
		res.sons.add(visit(ctx.orExpr()));
		res.loc = new Location(ctx.start);
		return res;
	}
	@Override public BinaryExprNode visitLogicalOExpr(MxStarParser.LogicalOExprContext ctx) {
		BinaryExprNode res = new BinaryExprNode();
		res.id = ctx.op.getText();
		res.sons.add(visit(ctx.logicalOrExpr()));
		res.sons.add(visit(ctx.logicalAndExpr()));
		res.loc = new Location(ctx.start);
		return res;
	}
	@Override public Node visitAndLogicalOrExpr(MxStarParser.AndLogicalOrExprContext ctx) {
		return visit(ctx.logicalAndExpr());
	}
	@Override public Node visitOrAsgmExpr(MxStarParser.OrAsgmExprContext ctx) {
		return visit(ctx.logicalOrExpr());
	}
	@Override public BinaryExprNode visitAsgmExpr(MxStarParser.AsgmExprContext ctx) {
		BinaryExprNode res = new BinaryExprNode();
		res.id = ctx.op.getText();
		res.sons.add(visit(ctx.logicalOrExpr()));
		res.sons.add(visit(ctx.assignmentExpr()));
		res.loc = new Location(ctx.start);
		return res;
	}
	@Override public IntLiteralNode visitArithmeticIntLiteral(MxStarParser.ArithmeticIntLiteralContext ctx) {
		IntLiteralNode res = new IntLiteralNode();
		res.id = ctx.IntLiteral().getText();
		res.type = new IntTypeRef();
		res.loc = new Location(ctx.start);
		return res;
	}
	@Override public LogicalLiteralNode visitArithmeticLogicalLiteral(MxStarParser.ArithmeticLogicalLiteralContext ctx) {
		LogicalLiteralNode res = new LogicalLiteralNode();
		res.id = ctx.logicalLiteral().getText();
		res.type = new BoolTypeRef();
		res.loc = new Location(ctx.start);
		return res;
	}
	@Override public StringLiteralNode visitUnarithmeticStringLiteral(MxStarParser.UnarithmeticStringLiteralContext ctx) {
		StringLiteralNode res = new StringLiteralNode();
		String tmp = ctx.StringLiteral().getText();
		res.id = "";
		for (int i = 0; i < tmp.length(); ++i) {
			if (tmp.charAt(i) != '\\') res.id = res.id + tmp.charAt(i);
			else {
				switch (tmp.charAt(i + 1)) {
					case '\\': res.id += '\\'; break;
					case 'n' : res.id += '\n'; break;
					case 't' : res.id += '\t'; break;
					case '"' : res.id += '\"'; break;
				}
				++i;
			}
		}
		System.err.print(res.id.length());
		res.type = new StringTypeRef();
		res.loc = new Location(ctx.start);
		return res;
	}
	@Override public NullLiteralNode visitUnarithmeticNullLiteral(MxStarParser.UnarithmeticNullLiteralContext ctx) {
		NullLiteralNode res = new NullLiteralNode();
		res.id = ctx.NullLiteral().getText();
		res.type = new NullTypeRef();
		res.loc = new Location(ctx.start);
		return res;
	}
}

