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
		res.id = ctx.classId().getText();
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
		else res.sons.add(new ConsFuncDefNode());
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
		if (ctx.Else() == null) {
			IfStatNode res = new IfStatNode();
			res.sons.add(visit(ctx.expression(0)));
			res.sons.add(visit(ctx.statement(0)));
			res.loc = new Location(ctx.start);
			return res;
		} else {
			IfElseStatNode res = new IfElseStatNode();
			for (int i = 0; i < ctx.If().size(); ++i) {
				IfStatNode tmp = new IfStatNode();
				tmp.sons.add(visit(ctx.expression(i)));
				tmp.sons.add(visit(ctx.statement(i)));
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
		res.sons.add(visit(ctx.loopExpr));
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
		res.loc = new Location(ctx.start);
		return res;
	}
	@Override public VarDefStatNode visitVariableDefinitionWithoutInit(MxStarParser.VariableDefinitionWithoutInitContext ctx) {
		TypeRef type = TypeRef.buildTypeRef(ctx.typeId().getText());
		VarDefStatNode res = new VarDefStatNode();
		for (int i = 0; i < ctx.variableId().size(); ++i) {
			VarDefNode tmp = new VarDefNode();
			MxStarParser.VariableIdContext varContext = ctx.variableId(i);
			tmp.id = varContext.getText();
			tmp.type = type;
			tmp.loc = new Location(varContext.start);
			res.sons.add(tmp);
		}
		res.loc = new Location(ctx.start);
		return res;
	}
	@Override public VarDefNode visitVarWithoutInit(MxStarParser.VarWithoutInitContext ctx) {
		VarDefNode res = new VarDefNode();
		res.id = ctx.variableId().getText();
		res.loc = new Location(ctx.start);
		return res;
	}
	@Override public VarDefNode visitVarWithInit (MxStarParser.VarWithInitContext ctx) {
		VarDefNode res = new VarDefNode();
		res.id = ctx.variableId().getText();
		res.sons.add(visit(ctx.expression()));
		res.loc = new Location(ctx.start);
		return res;
	}
	@Override public Node visitExpression(MxStarParser.ExpressionContext ctx) {
		return visit(ctx.assignmentExpr());
	}
	@Override public LiteralNode visitLiteralElemExpr(MxStarParser.LiteralElemExprContext ctx) {
		LiteralNode res = new LiteralNode();
		Node tmp = visit(ctx.literal());
		res.id = ctx.literal().getText();
		res.type = tmp.type;
		res.loc = new Location(ctx.start);
		return res;
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
		res.sons.add(visit(ctx.primaryExpr(0)));
		res.sons.add(visit(ctx.primaryExpr(1)));
		res.loc = new Location(ctx.start);
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
		res.sons.add(visit(ctx.primaryExpr()));
		res.sons.add(visit(ctx.expression()));
		res.loc = new Location(ctx.start);
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
		return visit(ctx.newCreator());
	}
	@Override public NewExprNode visitNewCreator(MxStarParser.NewCreatorContext ctx) {
		NewExprNode res = new NewExprNode();
		int dimension = 0;
		String text = ctx.getText();
		for (int i = 0; i < text.length(); ++i) {
			if (text.charAt(i) == '[') ++dimension;
		}
		if (ctx.simpleTypeId() != null) res.type = new ArrayTypeRef(ctx.simpleTypeId().getText(), dimension);
		else res.type = new ArrayTypeRef(ctx.classId().getText(), dimension);
		for (int i = 0; i < ctx.expression().size(); ++i) {
			res.sons.add(visit(ctx.expression(i)));
		}
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
		res.id = ctx.StringLiteral().getText();
		res.type = new StringTypeRef();
		res.loc = new Location(ctx.start);
		return res;
	}
	@Override public NullLiteralNode visitUnarithmeticNullLiteral(MxStarParser.UnarithmeticNullLiteralContext ctx) {
		NullLiteralNode res = new NullLiteralNode();
		res.id = ctx.NullLiteral().getText();
		res.type = new VoidTypeRef();
		res.loc = new Location(ctx.start);
		return res;
	}
}

