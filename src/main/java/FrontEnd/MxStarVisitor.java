// Generated from /home/cloudroof/IdeaProjects/MastarCpler/src/main/java/FrontEnd/MxStar.g4 by ANTLR 4.7
package FrontEnd;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link MxStarParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface MxStarVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link MxStarParser#code}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCode(MxStarParser.CodeContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxStarParser#classDefinition}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitClassDefinition(MxStarParser.ClassDefinitionContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxStarParser#functionDefinition}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunctionDefinition(MxStarParser.FunctionDefinitionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code compStat}
	 * labeled alternative in {@link MxStarParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCompStat(MxStarParser.CompStatContext ctx);
	/**
	 * Visit a parse tree produced by the {@code exprStat}
	 * labeled alternative in {@link MxStarParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExprStat(MxStarParser.ExprStatContext ctx);
	/**
	 * Visit a parse tree produced by the {@code condStat}
	 * labeled alternative in {@link MxStarParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCondStat(MxStarParser.CondStatContext ctx);
	/**
	 * Visit a parse tree produced by the {@code iterStat}
	 * labeled alternative in {@link MxStarParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIterStat(MxStarParser.IterStatContext ctx);
	/**
	 * Visit a parse tree produced by the {@code jumpStat}
	 * labeled alternative in {@link MxStarParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitJumpStat(MxStarParser.JumpStatContext ctx);
	/**
	 * Visit a parse tree produced by the {@code nullStat}
	 * labeled alternative in {@link MxStarParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNullStat(MxStarParser.NullStatContext ctx);
	/**
	 * Visit a parse tree produced by the {@code vdefStat}
	 * labeled alternative in {@link MxStarParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVdefStat(MxStarParser.VdefStatContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxStarParser#classId}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitClassId(MxStarParser.ClassIdContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxStarParser#classBody}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitClassBody(MxStarParser.ClassBodyContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxStarParser#constructionFunction}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConstructionFunction(MxStarParser.ConstructionFunctionContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxStarParser#functionId}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunctionId(MxStarParser.FunctionIdContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxStarParser#parameterList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParameterList(MxStarParser.ParameterListContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxStarParser#emptyParameterList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEmptyParameterList(MxStarParser.EmptyParameterListContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxStarParser#parameter}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParameter(MxStarParser.ParameterContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxStarParser#functionBody}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunctionBody(MxStarParser.FunctionBodyContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxStarParser#compoundStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCompoundStatement(MxStarParser.CompoundStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxStarParser#expressionStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpressionStatement(MxStarParser.ExpressionStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxStarParser#conditionalStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConditionalStatement(MxStarParser.ConditionalStatementContext ctx);
	/**
	 * Visit a parse tree produced by the {@code iterWhileStatement}
	 * labeled alternative in {@link MxStarParser#iterativeStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIterWhileStatement(MxStarParser.IterWhileStatementContext ctx);
	/**
	 * Visit a parse tree produced by the {@code iterForStatement}
	 * labeled alternative in {@link MxStarParser#iterativeStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIterForStatement(MxStarParser.IterForStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxStarParser#whileStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWhileStatement(MxStarParser.WhileStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxStarParser#forStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitForStatement(MxStarParser.ForStatementContext ctx);
	/**
	 * Visit a parse tree produced by the {@code retJumpStatement}
	 * labeled alternative in {@link MxStarParser#jumpStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRetJumpStatement(MxStarParser.RetJumpStatementContext ctx);
	/**
	 * Visit a parse tree produced by the {@code brkJumpStatement}
	 * labeled alternative in {@link MxStarParser#jumpStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBrkJumpStatement(MxStarParser.BrkJumpStatementContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ctnJumpStatement}
	 * labeled alternative in {@link MxStarParser#jumpStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCtnJumpStatement(MxStarParser.CtnJumpStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxStarParser#nullStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNullStatement(MxStarParser.NullStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxStarParser#varDefStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVarDefStatement(MxStarParser.VarDefStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxStarParser#varDefStatementWithoutInit}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVarDefStatementWithoutInit(MxStarParser.VarDefStatementWithoutInitContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxStarParser#variableDefinition}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVariableDefinition(MxStarParser.VariableDefinitionContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxStarParser#variableDefinitionWithoutInit}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVariableDefinitionWithoutInit(MxStarParser.VariableDefinitionWithoutInitContext ctx);
	/**
	 * Visit a parse tree produced by the {@code varWithoutInit}
	 * labeled alternative in {@link MxStarParser#varDef}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVarWithoutInit(MxStarParser.VarWithoutInitContext ctx);
	/**
	 * Visit a parse tree produced by the {@code varWithInit}
	 * labeled alternative in {@link MxStarParser#varDef}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVarWithInit(MxStarParser.VarWithInitContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxStarParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpression(MxStarParser.ExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code arithmeticIntLiteral}
	 * labeled alternative in {@link MxStarParser#literal}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArithmeticIntLiteral(MxStarParser.ArithmeticIntLiteralContext ctx);
	/**
	 * Visit a parse tree produced by the {@code arithmeticLogicalLiteral}
	 * labeled alternative in {@link MxStarParser#literal}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArithmeticLogicalLiteral(MxStarParser.ArithmeticLogicalLiteralContext ctx);
	/**
	 * Visit a parse tree produced by the {@code unarithmeticStringLiteral}
	 * labeled alternative in {@link MxStarParser#literal}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUnarithmeticStringLiteral(MxStarParser.UnarithmeticStringLiteralContext ctx);
	/**
	 * Visit a parse tree produced by the {@code unarithmeticNullLiteral}
	 * labeled alternative in {@link MxStarParser#literal}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUnarithmeticNullLiteral(MxStarParser.UnarithmeticNullLiteralContext ctx);
	/**
	 * Visit a parse tree produced by the {@code literalElemExpr}
	 * labeled alternative in {@link MxStarParser#elementExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLiteralElemExpr(MxStarParser.LiteralElemExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code varElemExpr}
	 * labeled alternative in {@link MxStarParser#elementExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVarElemExpr(MxStarParser.VarElemExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code sonElemExpr}
	 * labeled alternative in {@link MxStarParser#elementExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSonElemExpr(MxStarParser.SonElemExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code objAccPrimExpr}
	 * labeled alternative in {@link MxStarParser#primaryExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitObjAccPrimExpr(MxStarParser.ObjAccPrimExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code elemPrimExpr}
	 * labeled alternative in {@link MxStarParser#primaryExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitElemPrimExpr(MxStarParser.ElemPrimExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code funcPrimExpr}
	 * labeled alternative in {@link MxStarParser#primaryExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFuncPrimExpr(MxStarParser.FuncPrimExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code arrPrimExpr}
	 * labeled alternative in {@link MxStarParser#primaryExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArrPrimExpr(MxStarParser.ArrPrimExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxStarParser#argumentList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArgumentList(MxStarParser.ArgumentListContext ctx);
	/**
	 * Visit a parse tree produced by the {@code pmRUExpr}
	 * labeled alternative in {@link MxStarParser#rightUnaryExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPmRUExpr(MxStarParser.PmRUExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code primRUExpr}
	 * labeled alternative in {@link MxStarParser#rightUnaryExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrimRUExpr(MxStarParser.PrimRUExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code rUExpr}
	 * labeled alternative in {@link MxStarParser#unaryExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRUExpr(MxStarParser.RUExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code lUExpr}
	 * labeled alternative in {@link MxStarParser#unaryExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLUExpr(MxStarParser.LUExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code newUExpr}
	 * labeled alternative in {@link MxStarParser#unaryExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNewUExpr(MxStarParser.NewUExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxStarParser#newCreator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNewCreator(MxStarParser.NewCreatorContext ctx);
	/**
	 * Visit a parse tree produced by the {@code unaryMulExpr}
	 * labeled alternative in {@link MxStarParser#multiplicativeExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUnaryMulExpr(MxStarParser.UnaryMulExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code mulExpr}
	 * labeled alternative in {@link MxStarParser#multiplicativeExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMulExpr(MxStarParser.MulExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code mulAddExpr}
	 * labeled alternative in {@link MxStarParser#additiveExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMulAddExpr(MxStarParser.MulAddExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code addExpr}
	 * labeled alternative in {@link MxStarParser#additiveExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAddExpr(MxStarParser.AddExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code addShiftExpr}
	 * labeled alternative in {@link MxStarParser#shiftExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAddShiftExpr(MxStarParser.AddShiftExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code shftExpr}
	 * labeled alternative in {@link MxStarParser#shiftExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitShftExpr(MxStarParser.ShftExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code shiftRelExpr}
	 * labeled alternative in {@link MxStarParser#relationExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitShiftRelExpr(MxStarParser.ShiftRelExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code relExpr}
	 * labeled alternative in {@link MxStarParser#relationExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRelExpr(MxStarParser.RelExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code relEqualExpr}
	 * labeled alternative in {@link MxStarParser#equalityExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRelEqualExpr(MxStarParser.RelEqualExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code equalExpr}
	 * labeled alternative in {@link MxStarParser#equalityExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEqualExpr(MxStarParser.EqualExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code equAndExpr}
	 * labeled alternative in {@link MxStarParser#andExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEquAndExpr(MxStarParser.EquAndExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code aExpr}
	 * labeled alternative in {@link MxStarParser#andExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAExpr(MxStarParser.AExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code xExpr}
	 * labeled alternative in {@link MxStarParser#xorExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitXExpr(MxStarParser.XExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code andXorExpr}
	 * labeled alternative in {@link MxStarParser#xorExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAndXorExpr(MxStarParser.AndXorExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code xorOrExpr}
	 * labeled alternative in {@link MxStarParser#orExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitXorOrExpr(MxStarParser.XorOrExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code oExpr}
	 * labeled alternative in {@link MxStarParser#orExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOExpr(MxStarParser.OExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code orLogicalAndExpr}
	 * labeled alternative in {@link MxStarParser#logicalAndExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOrLogicalAndExpr(MxStarParser.OrLogicalAndExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code logicalAExpr}
	 * labeled alternative in {@link MxStarParser#logicalAndExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLogicalAExpr(MxStarParser.LogicalAExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code logicalOExpr}
	 * labeled alternative in {@link MxStarParser#logicalOrExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLogicalOExpr(MxStarParser.LogicalOExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code andLogicalOrExpr}
	 * labeled alternative in {@link MxStarParser#logicalOrExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAndLogicalOrExpr(MxStarParser.AndLogicalOrExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code orAsgmExpr}
	 * labeled alternative in {@link MxStarParser#assignmentExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOrAsgmExpr(MxStarParser.OrAsgmExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code asgmExpr}
	 * labeled alternative in {@link MxStarParser#assignmentExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAsgmExpr(MxStarParser.AsgmExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxStarParser#typeId}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTypeId(MxStarParser.TypeIdContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxStarParser#variableId}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVariableId(MxStarParser.VariableIdContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxStarParser#simpleTypeId}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSimpleTypeId(MxStarParser.SimpleTypeIdContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxStarParser#logicalLiteral}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLogicalLiteral(MxStarParser.LogicalLiteralContext ctx);
}