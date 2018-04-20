// Generated from /home/cloudroof/IdeaProjects/MastarCpler/src/main/java/MxStar.g4 by ANTLR 4.7
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link MxStarParser}.
 */
public interface MxStarListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link MxStarParser#code}.
	 * @param ctx the parse tree
	 */
	void enterCode(MxStarParser.CodeContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxStarParser#code}.
	 * @param ctx the parse tree
	 */
	void exitCode(MxStarParser.CodeContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxStarParser#classDefinition}.
	 * @param ctx the parse tree
	 */
	void enterClassDefinition(MxStarParser.ClassDefinitionContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxStarParser#classDefinition}.
	 * @param ctx the parse tree
	 */
	void exitClassDefinition(MxStarParser.ClassDefinitionContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxStarParser#functionDefinition}.
	 * @param ctx the parse tree
	 */
	void enterFunctionDefinition(MxStarParser.FunctionDefinitionContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxStarParser#functionDefinition}.
	 * @param ctx the parse tree
	 */
	void exitFunctionDefinition(MxStarParser.FunctionDefinitionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code compStat}
	 * labeled alternative in {@link MxStarParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterCompStat(MxStarParser.CompStatContext ctx);
	/**
	 * Exit a parse tree produced by the {@code compStat}
	 * labeled alternative in {@link MxStarParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitCompStat(MxStarParser.CompStatContext ctx);
	/**
	 * Enter a parse tree produced by the {@code exprStat}
	 * labeled alternative in {@link MxStarParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterExprStat(MxStarParser.ExprStatContext ctx);
	/**
	 * Exit a parse tree produced by the {@code exprStat}
	 * labeled alternative in {@link MxStarParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitExprStat(MxStarParser.ExprStatContext ctx);
	/**
	 * Enter a parse tree produced by the {@code condStat}
	 * labeled alternative in {@link MxStarParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterCondStat(MxStarParser.CondStatContext ctx);
	/**
	 * Exit a parse tree produced by the {@code condStat}
	 * labeled alternative in {@link MxStarParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitCondStat(MxStarParser.CondStatContext ctx);
	/**
	 * Enter a parse tree produced by the {@code iterStat}
	 * labeled alternative in {@link MxStarParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterIterStat(MxStarParser.IterStatContext ctx);
	/**
	 * Exit a parse tree produced by the {@code iterStat}
	 * labeled alternative in {@link MxStarParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitIterStat(MxStarParser.IterStatContext ctx);
	/**
	 * Enter a parse tree produced by the {@code jumpStat}
	 * labeled alternative in {@link MxStarParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterJumpStat(MxStarParser.JumpStatContext ctx);
	/**
	 * Exit a parse tree produced by the {@code jumpStat}
	 * labeled alternative in {@link MxStarParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitJumpStat(MxStarParser.JumpStatContext ctx);
	/**
	 * Enter a parse tree produced by the {@code nullStat}
	 * labeled alternative in {@link MxStarParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterNullStat(MxStarParser.NullStatContext ctx);
	/**
	 * Exit a parse tree produced by the {@code nullStat}
	 * labeled alternative in {@link MxStarParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitNullStat(MxStarParser.NullStatContext ctx);
	/**
	 * Enter a parse tree produced by the {@code vdefStat}
	 * labeled alternative in {@link MxStarParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterVdefStat(MxStarParser.VdefStatContext ctx);
	/**
	 * Exit a parse tree produced by the {@code vdefStat}
	 * labeled alternative in {@link MxStarParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitVdefStat(MxStarParser.VdefStatContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxStarParser#classId}.
	 * @param ctx the parse tree
	 */
	void enterClassId(MxStarParser.ClassIdContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxStarParser#classId}.
	 * @param ctx the parse tree
	 */
	void exitClassId(MxStarParser.ClassIdContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxStarParser#classBody}.
	 * @param ctx the parse tree
	 */
	void enterClassBody(MxStarParser.ClassBodyContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxStarParser#classBody}.
	 * @param ctx the parse tree
	 */
	void exitClassBody(MxStarParser.ClassBodyContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxStarParser#constructionFunction}.
	 * @param ctx the parse tree
	 */
	void enterConstructionFunction(MxStarParser.ConstructionFunctionContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxStarParser#constructionFunction}.
	 * @param ctx the parse tree
	 */
	void exitConstructionFunction(MxStarParser.ConstructionFunctionContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxStarParser#functionId}.
	 * @param ctx the parse tree
	 */
	void enterFunctionId(MxStarParser.FunctionIdContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxStarParser#functionId}.
	 * @param ctx the parse tree
	 */
	void exitFunctionId(MxStarParser.FunctionIdContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxStarParser#parameterList}.
	 * @param ctx the parse tree
	 */
	void enterParameterList(MxStarParser.ParameterListContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxStarParser#parameterList}.
	 * @param ctx the parse tree
	 */
	void exitParameterList(MxStarParser.ParameterListContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxStarParser#emptyParameterList}.
	 * @param ctx the parse tree
	 */
	void enterEmptyParameterList(MxStarParser.EmptyParameterListContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxStarParser#emptyParameterList}.
	 * @param ctx the parse tree
	 */
	void exitEmptyParameterList(MxStarParser.EmptyParameterListContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxStarParser#parameter}.
	 * @param ctx the parse tree
	 */
	void enterParameter(MxStarParser.ParameterContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxStarParser#parameter}.
	 * @param ctx the parse tree
	 */
	void exitParameter(MxStarParser.ParameterContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxStarParser#functionBody}.
	 * @param ctx the parse tree
	 */
	void enterFunctionBody(MxStarParser.FunctionBodyContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxStarParser#functionBody}.
	 * @param ctx the parse tree
	 */
	void exitFunctionBody(MxStarParser.FunctionBodyContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxStarParser#compoundStatement}.
	 * @param ctx the parse tree
	 */
	void enterCompoundStatement(MxStarParser.CompoundStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxStarParser#compoundStatement}.
	 * @param ctx the parse tree
	 */
	void exitCompoundStatement(MxStarParser.CompoundStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxStarParser#expressionStatement}.
	 * @param ctx the parse tree
	 */
	void enterExpressionStatement(MxStarParser.ExpressionStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxStarParser#expressionStatement}.
	 * @param ctx the parse tree
	 */
	void exitExpressionStatement(MxStarParser.ExpressionStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxStarParser#conditionalStatement}.
	 * @param ctx the parse tree
	 */
	void enterConditionalStatement(MxStarParser.ConditionalStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxStarParser#conditionalStatement}.
	 * @param ctx the parse tree
	 */
	void exitConditionalStatement(MxStarParser.ConditionalStatementContext ctx);
	/**
	 * Enter a parse tree produced by the {@code iterWhileStatement}
	 * labeled alternative in {@link MxStarParser#iterativeStatement}.
	 * @param ctx the parse tree
	 */
	void enterIterWhileStatement(MxStarParser.IterWhileStatementContext ctx);
	/**
	 * Exit a parse tree produced by the {@code iterWhileStatement}
	 * labeled alternative in {@link MxStarParser#iterativeStatement}.
	 * @param ctx the parse tree
	 */
	void exitIterWhileStatement(MxStarParser.IterWhileStatementContext ctx);
	/**
	 * Enter a parse tree produced by the {@code iterForStatement}
	 * labeled alternative in {@link MxStarParser#iterativeStatement}.
	 * @param ctx the parse tree
	 */
	void enterIterForStatement(MxStarParser.IterForStatementContext ctx);
	/**
	 * Exit a parse tree produced by the {@code iterForStatement}
	 * labeled alternative in {@link MxStarParser#iterativeStatement}.
	 * @param ctx the parse tree
	 */
	void exitIterForStatement(MxStarParser.IterForStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxStarParser#whileStatement}.
	 * @param ctx the parse tree
	 */
	void enterWhileStatement(MxStarParser.WhileStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxStarParser#whileStatement}.
	 * @param ctx the parse tree
	 */
	void exitWhileStatement(MxStarParser.WhileStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxStarParser#forStatement}.
	 * @param ctx the parse tree
	 */
	void enterForStatement(MxStarParser.ForStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxStarParser#forStatement}.
	 * @param ctx the parse tree
	 */
	void exitForStatement(MxStarParser.ForStatementContext ctx);
	/**
	 * Enter a parse tree produced by the {@code retJumpStatement}
	 * labeled alternative in {@link MxStarParser#jumpStatement}.
	 * @param ctx the parse tree
	 */
	void enterRetJumpStatement(MxStarParser.RetJumpStatementContext ctx);
	/**
	 * Exit a parse tree produced by the {@code retJumpStatement}
	 * labeled alternative in {@link MxStarParser#jumpStatement}.
	 * @param ctx the parse tree
	 */
	void exitRetJumpStatement(MxStarParser.RetJumpStatementContext ctx);
	/**
	 * Enter a parse tree produced by the {@code brkJumpStatement}
	 * labeled alternative in {@link MxStarParser#jumpStatement}.
	 * @param ctx the parse tree
	 */
	void enterBrkJumpStatement(MxStarParser.BrkJumpStatementContext ctx);
	/**
	 * Exit a parse tree produced by the {@code brkJumpStatement}
	 * labeled alternative in {@link MxStarParser#jumpStatement}.
	 * @param ctx the parse tree
	 */
	void exitBrkJumpStatement(MxStarParser.BrkJumpStatementContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ctnJumpStatement}
	 * labeled alternative in {@link MxStarParser#jumpStatement}.
	 * @param ctx the parse tree
	 */
	void enterCtnJumpStatement(MxStarParser.CtnJumpStatementContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ctnJumpStatement}
	 * labeled alternative in {@link MxStarParser#jumpStatement}.
	 * @param ctx the parse tree
	 */
	void exitCtnJumpStatement(MxStarParser.CtnJumpStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxStarParser#nullStatement}.
	 * @param ctx the parse tree
	 */
	void enterNullStatement(MxStarParser.NullStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxStarParser#nullStatement}.
	 * @param ctx the parse tree
	 */
	void exitNullStatement(MxStarParser.NullStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxStarParser#varDefStatement}.
	 * @param ctx the parse tree
	 */
	void enterVarDefStatement(MxStarParser.VarDefStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxStarParser#varDefStatement}.
	 * @param ctx the parse tree
	 */
	void exitVarDefStatement(MxStarParser.VarDefStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxStarParser#varDefStatementWithoutInit}.
	 * @param ctx the parse tree
	 */
	void enterVarDefStatementWithoutInit(MxStarParser.VarDefStatementWithoutInitContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxStarParser#varDefStatementWithoutInit}.
	 * @param ctx the parse tree
	 */
	void exitVarDefStatementWithoutInit(MxStarParser.VarDefStatementWithoutInitContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxStarParser#variableDefinition}.
	 * @param ctx the parse tree
	 */
	void enterVariableDefinition(MxStarParser.VariableDefinitionContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxStarParser#variableDefinition}.
	 * @param ctx the parse tree
	 */
	void exitVariableDefinition(MxStarParser.VariableDefinitionContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxStarParser#variableDefinitionWithoutInit}.
	 * @param ctx the parse tree
	 */
	void enterVariableDefinitionWithoutInit(MxStarParser.VariableDefinitionWithoutInitContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxStarParser#variableDefinitionWithoutInit}.
	 * @param ctx the parse tree
	 */
	void exitVariableDefinitionWithoutInit(MxStarParser.VariableDefinitionWithoutInitContext ctx);
	/**
	 * Enter a parse tree produced by the {@code varWithoutInit}
	 * labeled alternative in {@link MxStarParser#varDef}.
	 * @param ctx the parse tree
	 */
	void enterVarWithoutInit(MxStarParser.VarWithoutInitContext ctx);
	/**
	 * Exit a parse tree produced by the {@code varWithoutInit}
	 * labeled alternative in {@link MxStarParser#varDef}.
	 * @param ctx the parse tree
	 */
	void exitVarWithoutInit(MxStarParser.VarWithoutInitContext ctx);
	/**
	 * Enter a parse tree produced by the {@code varWithInit}
	 * labeled alternative in {@link MxStarParser#varDef}.
	 * @param ctx the parse tree
	 */
	void enterVarWithInit(MxStarParser.VarWithInitContext ctx);
	/**
	 * Exit a parse tree produced by the {@code varWithInit}
	 * labeled alternative in {@link MxStarParser#varDef}.
	 * @param ctx the parse tree
	 */
	void exitVarWithInit(MxStarParser.VarWithInitContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxStarParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExpression(MxStarParser.ExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxStarParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExpression(MxStarParser.ExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code arithmeticIntLiteral}
	 * labeled alternative in {@link MxStarParser#literal}.
	 * @param ctx the parse tree
	 */
	void enterArithmeticIntLiteral(MxStarParser.ArithmeticIntLiteralContext ctx);
	/**
	 * Exit a parse tree produced by the {@code arithmeticIntLiteral}
	 * labeled alternative in {@link MxStarParser#literal}.
	 * @param ctx the parse tree
	 */
	void exitArithmeticIntLiteral(MxStarParser.ArithmeticIntLiteralContext ctx);
	/**
	 * Enter a parse tree produced by the {@code arithmeticLogicalLiteral}
	 * labeled alternative in {@link MxStarParser#literal}.
	 * @param ctx the parse tree
	 */
	void enterArithmeticLogicalLiteral(MxStarParser.ArithmeticLogicalLiteralContext ctx);
	/**
	 * Exit a parse tree produced by the {@code arithmeticLogicalLiteral}
	 * labeled alternative in {@link MxStarParser#literal}.
	 * @param ctx the parse tree
	 */
	void exitArithmeticLogicalLiteral(MxStarParser.ArithmeticLogicalLiteralContext ctx);
	/**
	 * Enter a parse tree produced by the {@code unarithmeticStringLiteral}
	 * labeled alternative in {@link MxStarParser#literal}.
	 * @param ctx the parse tree
	 */
	void enterUnarithmeticStringLiteral(MxStarParser.UnarithmeticStringLiteralContext ctx);
	/**
	 * Exit a parse tree produced by the {@code unarithmeticStringLiteral}
	 * labeled alternative in {@link MxStarParser#literal}.
	 * @param ctx the parse tree
	 */
	void exitUnarithmeticStringLiteral(MxStarParser.UnarithmeticStringLiteralContext ctx);
	/**
	 * Enter a parse tree produced by the {@code unarithmeticNullLiteral}
	 * labeled alternative in {@link MxStarParser#literal}.
	 * @param ctx the parse tree
	 */
	void enterUnarithmeticNullLiteral(MxStarParser.UnarithmeticNullLiteralContext ctx);
	/**
	 * Exit a parse tree produced by the {@code unarithmeticNullLiteral}
	 * labeled alternative in {@link MxStarParser#literal}.
	 * @param ctx the parse tree
	 */
	void exitUnarithmeticNullLiteral(MxStarParser.UnarithmeticNullLiteralContext ctx);
	/**
	 * Enter a parse tree produced by the {@code literalElemExpr}
	 * labeled alternative in {@link MxStarParser#elementExpr}.
	 * @param ctx the parse tree
	 */
	void enterLiteralElemExpr(MxStarParser.LiteralElemExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code literalElemExpr}
	 * labeled alternative in {@link MxStarParser#elementExpr}.
	 * @param ctx the parse tree
	 */
	void exitLiteralElemExpr(MxStarParser.LiteralElemExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code varElemExpr}
	 * labeled alternative in {@link MxStarParser#elementExpr}.
	 * @param ctx the parse tree
	 */
	void enterVarElemExpr(MxStarParser.VarElemExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code varElemExpr}
	 * labeled alternative in {@link MxStarParser#elementExpr}.
	 * @param ctx the parse tree
	 */
	void exitVarElemExpr(MxStarParser.VarElemExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code sonElemExpr}
	 * labeled alternative in {@link MxStarParser#elementExpr}.
	 * @param ctx the parse tree
	 */
	void enterSonElemExpr(MxStarParser.SonElemExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code sonElemExpr}
	 * labeled alternative in {@link MxStarParser#elementExpr}.
	 * @param ctx the parse tree
	 */
	void exitSonElemExpr(MxStarParser.SonElemExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code objAccPrimExpr}
	 * labeled alternative in {@link MxStarParser#primaryExpr}.
	 * @param ctx the parse tree
	 */
	void enterObjAccPrimExpr(MxStarParser.ObjAccPrimExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code objAccPrimExpr}
	 * labeled alternative in {@link MxStarParser#primaryExpr}.
	 * @param ctx the parse tree
	 */
	void exitObjAccPrimExpr(MxStarParser.ObjAccPrimExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code elemPrimExpr}
	 * labeled alternative in {@link MxStarParser#primaryExpr}.
	 * @param ctx the parse tree
	 */
	void enterElemPrimExpr(MxStarParser.ElemPrimExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code elemPrimExpr}
	 * labeled alternative in {@link MxStarParser#primaryExpr}.
	 * @param ctx the parse tree
	 */
	void exitElemPrimExpr(MxStarParser.ElemPrimExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code funcPrimExpr}
	 * labeled alternative in {@link MxStarParser#primaryExpr}.
	 * @param ctx the parse tree
	 */
	void enterFuncPrimExpr(MxStarParser.FuncPrimExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code funcPrimExpr}
	 * labeled alternative in {@link MxStarParser#primaryExpr}.
	 * @param ctx the parse tree
	 */
	void exitFuncPrimExpr(MxStarParser.FuncPrimExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code arrPrimExpr}
	 * labeled alternative in {@link MxStarParser#primaryExpr}.
	 * @param ctx the parse tree
	 */
	void enterArrPrimExpr(MxStarParser.ArrPrimExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code arrPrimExpr}
	 * labeled alternative in {@link MxStarParser#primaryExpr}.
	 * @param ctx the parse tree
	 */
	void exitArrPrimExpr(MxStarParser.ArrPrimExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxStarParser#argumentList}.
	 * @param ctx the parse tree
	 */
	void enterArgumentList(MxStarParser.ArgumentListContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxStarParser#argumentList}.
	 * @param ctx the parse tree
	 */
	void exitArgumentList(MxStarParser.ArgumentListContext ctx);
	/**
	 * Enter a parse tree produced by the {@code pmRUExpr}
	 * labeled alternative in {@link MxStarParser#rightUnaryExpr}.
	 * @param ctx the parse tree
	 */
	void enterPmRUExpr(MxStarParser.PmRUExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code pmRUExpr}
	 * labeled alternative in {@link MxStarParser#rightUnaryExpr}.
	 * @param ctx the parse tree
	 */
	void exitPmRUExpr(MxStarParser.PmRUExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code primRUExpr}
	 * labeled alternative in {@link MxStarParser#rightUnaryExpr}.
	 * @param ctx the parse tree
	 */
	void enterPrimRUExpr(MxStarParser.PrimRUExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code primRUExpr}
	 * labeled alternative in {@link MxStarParser#rightUnaryExpr}.
	 * @param ctx the parse tree
	 */
	void exitPrimRUExpr(MxStarParser.PrimRUExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code rUExpr}
	 * labeled alternative in {@link MxStarParser#unaryExpr}.
	 * @param ctx the parse tree
	 */
	void enterRUExpr(MxStarParser.RUExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code rUExpr}
	 * labeled alternative in {@link MxStarParser#unaryExpr}.
	 * @param ctx the parse tree
	 */
	void exitRUExpr(MxStarParser.RUExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code lUExpr}
	 * labeled alternative in {@link MxStarParser#unaryExpr}.
	 * @param ctx the parse tree
	 */
	void enterLUExpr(MxStarParser.LUExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code lUExpr}
	 * labeled alternative in {@link MxStarParser#unaryExpr}.
	 * @param ctx the parse tree
	 */
	void exitLUExpr(MxStarParser.LUExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code newUExpr}
	 * labeled alternative in {@link MxStarParser#unaryExpr}.
	 * @param ctx the parse tree
	 */
	void enterNewUExpr(MxStarParser.NewUExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code newUExpr}
	 * labeled alternative in {@link MxStarParser#unaryExpr}.
	 * @param ctx the parse tree
	 */
	void exitNewUExpr(MxStarParser.NewUExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxStarParser#newCreator}.
	 * @param ctx the parse tree
	 */
	void enterNewCreator(MxStarParser.NewCreatorContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxStarParser#newCreator}.
	 * @param ctx the parse tree
	 */
	void exitNewCreator(MxStarParser.NewCreatorContext ctx);
	/**
	 * Enter a parse tree produced by the {@code unaryMulExpr}
	 * labeled alternative in {@link MxStarParser#multiplicativeExpr}.
	 * @param ctx the parse tree
	 */
	void enterUnaryMulExpr(MxStarParser.UnaryMulExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code unaryMulExpr}
	 * labeled alternative in {@link MxStarParser#multiplicativeExpr}.
	 * @param ctx the parse tree
	 */
	void exitUnaryMulExpr(MxStarParser.UnaryMulExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code mulExpr}
	 * labeled alternative in {@link MxStarParser#multiplicativeExpr}.
	 * @param ctx the parse tree
	 */
	void enterMulExpr(MxStarParser.MulExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code mulExpr}
	 * labeled alternative in {@link MxStarParser#multiplicativeExpr}.
	 * @param ctx the parse tree
	 */
	void exitMulExpr(MxStarParser.MulExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code mulAddExpr}
	 * labeled alternative in {@link MxStarParser#additiveExpr}.
	 * @param ctx the parse tree
	 */
	void enterMulAddExpr(MxStarParser.MulAddExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code mulAddExpr}
	 * labeled alternative in {@link MxStarParser#additiveExpr}.
	 * @param ctx the parse tree
	 */
	void exitMulAddExpr(MxStarParser.MulAddExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code addExpr}
	 * labeled alternative in {@link MxStarParser#additiveExpr}.
	 * @param ctx the parse tree
	 */
	void enterAddExpr(MxStarParser.AddExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code addExpr}
	 * labeled alternative in {@link MxStarParser#additiveExpr}.
	 * @param ctx the parse tree
	 */
	void exitAddExpr(MxStarParser.AddExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code addShiftExpr}
	 * labeled alternative in {@link MxStarParser#shiftExpr}.
	 * @param ctx the parse tree
	 */
	void enterAddShiftExpr(MxStarParser.AddShiftExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code addShiftExpr}
	 * labeled alternative in {@link MxStarParser#shiftExpr}.
	 * @param ctx the parse tree
	 */
	void exitAddShiftExpr(MxStarParser.AddShiftExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code shftExpr}
	 * labeled alternative in {@link MxStarParser#shiftExpr}.
	 * @param ctx the parse tree
	 */
	void enterShftExpr(MxStarParser.ShftExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code shftExpr}
	 * labeled alternative in {@link MxStarParser#shiftExpr}.
	 * @param ctx the parse tree
	 */
	void exitShftExpr(MxStarParser.ShftExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code shiftRelExpr}
	 * labeled alternative in {@link MxStarParser#relationExpr}.
	 * @param ctx the parse tree
	 */
	void enterShiftRelExpr(MxStarParser.ShiftRelExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code shiftRelExpr}
	 * labeled alternative in {@link MxStarParser#relationExpr}.
	 * @param ctx the parse tree
	 */
	void exitShiftRelExpr(MxStarParser.ShiftRelExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code relExpr}
	 * labeled alternative in {@link MxStarParser#relationExpr}.
	 * @param ctx the parse tree
	 */
	void enterRelExpr(MxStarParser.RelExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code relExpr}
	 * labeled alternative in {@link MxStarParser#relationExpr}.
	 * @param ctx the parse tree
	 */
	void exitRelExpr(MxStarParser.RelExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code relEqualExpr}
	 * labeled alternative in {@link MxStarParser#equalityExpr}.
	 * @param ctx the parse tree
	 */
	void enterRelEqualExpr(MxStarParser.RelEqualExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code relEqualExpr}
	 * labeled alternative in {@link MxStarParser#equalityExpr}.
	 * @param ctx the parse tree
	 */
	void exitRelEqualExpr(MxStarParser.RelEqualExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code equalExpr}
	 * labeled alternative in {@link MxStarParser#equalityExpr}.
	 * @param ctx the parse tree
	 */
	void enterEqualExpr(MxStarParser.EqualExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code equalExpr}
	 * labeled alternative in {@link MxStarParser#equalityExpr}.
	 * @param ctx the parse tree
	 */
	void exitEqualExpr(MxStarParser.EqualExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code equAndExpr}
	 * labeled alternative in {@link MxStarParser#andExpr}.
	 * @param ctx the parse tree
	 */
	void enterEquAndExpr(MxStarParser.EquAndExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code equAndExpr}
	 * labeled alternative in {@link MxStarParser#andExpr}.
	 * @param ctx the parse tree
	 */
	void exitEquAndExpr(MxStarParser.EquAndExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code aExpr}
	 * labeled alternative in {@link MxStarParser#andExpr}.
	 * @param ctx the parse tree
	 */
	void enterAExpr(MxStarParser.AExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code aExpr}
	 * labeled alternative in {@link MxStarParser#andExpr}.
	 * @param ctx the parse tree
	 */
	void exitAExpr(MxStarParser.AExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code xExpr}
	 * labeled alternative in {@link MxStarParser#xorExpr}.
	 * @param ctx the parse tree
	 */
	void enterXExpr(MxStarParser.XExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code xExpr}
	 * labeled alternative in {@link MxStarParser#xorExpr}.
	 * @param ctx the parse tree
	 */
	void exitXExpr(MxStarParser.XExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code andXorExpr}
	 * labeled alternative in {@link MxStarParser#xorExpr}.
	 * @param ctx the parse tree
	 */
	void enterAndXorExpr(MxStarParser.AndXorExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code andXorExpr}
	 * labeled alternative in {@link MxStarParser#xorExpr}.
	 * @param ctx the parse tree
	 */
	void exitAndXorExpr(MxStarParser.AndXorExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code xorOrExpr}
	 * labeled alternative in {@link MxStarParser#orExpr}.
	 * @param ctx the parse tree
	 */
	void enterXorOrExpr(MxStarParser.XorOrExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code xorOrExpr}
	 * labeled alternative in {@link MxStarParser#orExpr}.
	 * @param ctx the parse tree
	 */
	void exitXorOrExpr(MxStarParser.XorOrExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code oExpr}
	 * labeled alternative in {@link MxStarParser#orExpr}.
	 * @param ctx the parse tree
	 */
	void enterOExpr(MxStarParser.OExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code oExpr}
	 * labeled alternative in {@link MxStarParser#orExpr}.
	 * @param ctx the parse tree
	 */
	void exitOExpr(MxStarParser.OExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code orLogicalAndExpr}
	 * labeled alternative in {@link MxStarParser#logicalAndExpr}.
	 * @param ctx the parse tree
	 */
	void enterOrLogicalAndExpr(MxStarParser.OrLogicalAndExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code orLogicalAndExpr}
	 * labeled alternative in {@link MxStarParser#logicalAndExpr}.
	 * @param ctx the parse tree
	 */
	void exitOrLogicalAndExpr(MxStarParser.OrLogicalAndExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code logicalAExpr}
	 * labeled alternative in {@link MxStarParser#logicalAndExpr}.
	 * @param ctx the parse tree
	 */
	void enterLogicalAExpr(MxStarParser.LogicalAExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code logicalAExpr}
	 * labeled alternative in {@link MxStarParser#logicalAndExpr}.
	 * @param ctx the parse tree
	 */
	void exitLogicalAExpr(MxStarParser.LogicalAExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code logicalOExpr}
	 * labeled alternative in {@link MxStarParser#logicalOrExpr}.
	 * @param ctx the parse tree
	 */
	void enterLogicalOExpr(MxStarParser.LogicalOExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code logicalOExpr}
	 * labeled alternative in {@link MxStarParser#logicalOrExpr}.
	 * @param ctx the parse tree
	 */
	void exitLogicalOExpr(MxStarParser.LogicalOExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code andLogicalOrExpr}
	 * labeled alternative in {@link MxStarParser#logicalOrExpr}.
	 * @param ctx the parse tree
	 */
	void enterAndLogicalOrExpr(MxStarParser.AndLogicalOrExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code andLogicalOrExpr}
	 * labeled alternative in {@link MxStarParser#logicalOrExpr}.
	 * @param ctx the parse tree
	 */
	void exitAndLogicalOrExpr(MxStarParser.AndLogicalOrExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code orAsgmExpr}
	 * labeled alternative in {@link MxStarParser#assignmentExpr}.
	 * @param ctx the parse tree
	 */
	void enterOrAsgmExpr(MxStarParser.OrAsgmExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code orAsgmExpr}
	 * labeled alternative in {@link MxStarParser#assignmentExpr}.
	 * @param ctx the parse tree
	 */
	void exitOrAsgmExpr(MxStarParser.OrAsgmExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code asgmExpr}
	 * labeled alternative in {@link MxStarParser#assignmentExpr}.
	 * @param ctx the parse tree
	 */
	void enterAsgmExpr(MxStarParser.AsgmExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code asgmExpr}
	 * labeled alternative in {@link MxStarParser#assignmentExpr}.
	 * @param ctx the parse tree
	 */
	void exitAsgmExpr(MxStarParser.AsgmExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxStarParser#typeId}.
	 * @param ctx the parse tree
	 */
	void enterTypeId(MxStarParser.TypeIdContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxStarParser#typeId}.
	 * @param ctx the parse tree
	 */
	void exitTypeId(MxStarParser.TypeIdContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxStarParser#variableId}.
	 * @param ctx the parse tree
	 */
	void enterVariableId(MxStarParser.VariableIdContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxStarParser#variableId}.
	 * @param ctx the parse tree
	 */
	void exitVariableId(MxStarParser.VariableIdContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxStarParser#simpleTypeId}.
	 * @param ctx the parse tree
	 */
	void enterSimpleTypeId(MxStarParser.SimpleTypeIdContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxStarParser#simpleTypeId}.
	 * @param ctx the parse tree
	 */
	void exitSimpleTypeId(MxStarParser.SimpleTypeIdContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxStarParser#logicalLiteral}.
	 * @param ctx the parse tree
	 */
	void enterLogicalLiteral(MxStarParser.LogicalLiteralContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxStarParser#logicalLiteral}.
	 * @param ctx the parse tree
	 */
	void exitLogicalLiteral(MxStarParser.LogicalLiteralContext ctx);
}