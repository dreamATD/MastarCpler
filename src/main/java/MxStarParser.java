// Generated from /home/cloudroof/IdeaProjects/MastarCpler/src/main/java/MxStar.g4 by ANTLR 4.7
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class MxStarParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.7", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		T__9=10, T__10=11, T__11=12, T__12=13, T__13=14, T__14=15, T__15=16, T__16=17, 
		T__17=18, T__18=19, T__19=20, T__20=21, T__21=22, T__22=23, T__23=24, 
		T__24=25, T__25=26, T__26=27, T__27=28, T__28=29, T__29=30, T__30=31, 
		T__31=32, LineCommentary=33, StringLiteral=34, True=35, False=36, IntLiteral=37, 
		NullLiteral=38, Bool=39, Int=40, String=41, Void=42, If=43, Else=44, For=45, 
		While=46, Break=47, Continue=48, Return=49, New=50, Class=51, This=52, 
		Identifier=53, WhiteSpace=54, NewLine=55;
	public static final int
		RULE_code = 0, RULE_classDefinition = 1, RULE_functionDefinition = 2, 
		RULE_statement = 3, RULE_classId = 4, RULE_classBody = 5, RULE_constructionFunction = 6, 
		RULE_functionId = 7, RULE_parameterList = 8, RULE_emptyParameterList = 9, 
		RULE_parameter = 10, RULE_functionBody = 11, RULE_compoundStatement = 12, 
		RULE_expressionStatement = 13, RULE_conditionalStatement = 14, RULE_iterativeStatement = 15, 
		RULE_whileStatement = 16, RULE_forStatement = 17, RULE_jumpStatement = 18, 
		RULE_nullStatement = 19, RULE_varDefStatement = 20, RULE_varDefStatementWithoutInit = 21, 
		RULE_variableDefinition = 22, RULE_variableDefinitionWithoutInit = 23, 
		RULE_varDef = 24, RULE_expression = 25, RULE_literal = 26, RULE_elementExpr = 27, 
		RULE_primaryExpr = 28, RULE_argumentList = 29, RULE_rightUnaryExpr = 30, 
		RULE_unaryExpr = 31, RULE_newCreator = 32, RULE_multiplicativeExpr = 33, 
		RULE_additiveExpr = 34, RULE_shiftExpr = 35, RULE_relationExpr = 36, RULE_equalityExpr = 37, 
		RULE_andExpr = 38, RULE_xorExpr = 39, RULE_orExpr = 40, RULE_logicalAndExpr = 41, 
		RULE_logicalOrExpr = 42, RULE_assignmentExpr = 43, RULE_typeId = 44, RULE_variableId = 45, 
		RULE_simpleTypeId = 46, RULE_logicalLiteral = 47;
	public static final String[] ruleNames = {
		"code", "classDefinition", "functionDefinition", "statement", "classId", 
		"classBody", "constructionFunction", "functionId", "parameterList", "emptyParameterList", 
		"parameter", "functionBody", "compoundStatement", "expressionStatement", 
		"conditionalStatement", "iterativeStatement", "whileStatement", "forStatement", 
		"jumpStatement", "nullStatement", "varDefStatement", "varDefStatementWithoutInit", 
		"variableDefinition", "variableDefinitionWithoutInit", "varDef", "expression", 
		"literal", "elementExpr", "primaryExpr", "argumentList", "rightUnaryExpr", 
		"unaryExpr", "newCreator", "multiplicativeExpr", "additiveExpr", "shiftExpr", 
		"relationExpr", "equalityExpr", "andExpr", "xorExpr", "orExpr", "logicalAndExpr", 
		"logicalOrExpr", "assignmentExpr", "typeId", "variableId", "simpleTypeId", 
		"logicalLiteral"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "'{'", "'}'", "'('", "')'", "','", "';'", "'='", "'['", "']'", "'.'", 
		"'++'", "'--'", "'~'", "'!'", "'-'", "'+'", "'*'", "'/'", "'%'", "'<<'", 
		"'>>'", "'<'", "'>'", "'<='", "'>='", "'=='", "'!='", "'&'", "'^'", "'|'", 
		"'&&'", "'||'", null, null, "'true'", "'false'", null, "'null'", "'bool'", 
		"'int'", "'string'", "'void'", "'if'", "'else'", "'for'", "'while'", "'break'", 
		"'continue'", "'return'", "'new'", "'class'", "'this'"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, null, null, null, "LineCommentary", 
		"StringLiteral", "True", "False", "IntLiteral", "NullLiteral", "Bool", 
		"Int", "String", "Void", "If", "Else", "For", "While", "Break", "Continue", 
		"Return", "New", "Class", "This", "Identifier", "WhiteSpace", "NewLine"
	};
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "MxStar.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public MxStarParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class CodeContext extends ParserRuleContext {
		public List<ClassDefinitionContext> classDefinition() {
			return getRuleContexts(ClassDefinitionContext.class);
		}
		public ClassDefinitionContext classDefinition(int i) {
			return getRuleContext(ClassDefinitionContext.class,i);
		}
		public List<FunctionDefinitionContext> functionDefinition() {
			return getRuleContexts(FunctionDefinitionContext.class);
		}
		public FunctionDefinitionContext functionDefinition(int i) {
			return getRuleContext(FunctionDefinitionContext.class,i);
		}
		public List<VarDefStatementContext> varDefStatement() {
			return getRuleContexts(VarDefStatementContext.class);
		}
		public VarDefStatementContext varDefStatement(int i) {
			return getRuleContext(VarDefStatementContext.class,i);
		}
		public CodeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_code; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxStarListener ) ((MxStarListener)listener).enterCode(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxStarListener ) ((MxStarListener)listener).exitCode(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxStarVisitor ) return ((MxStarVisitor<? extends T>)visitor).visitCode(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CodeContext code() throws RecognitionException {
		CodeContext _localctx = new CodeContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_code);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(101);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << Bool) | (1L << Int) | (1L << String) | (1L << Void) | (1L << Class) | (1L << Identifier))) != 0)) {
				{
				setState(99);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,0,_ctx) ) {
				case 1:
					{
					setState(96);
					classDefinition();
					}
					break;
				case 2:
					{
					setState(97);
					functionDefinition();
					}
					break;
				case 3:
					{
					setState(98);
					varDefStatement();
					}
					break;
				}
				}
				setState(103);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ClassDefinitionContext extends ParserRuleContext {
		public TerminalNode Class() { return getToken(MxStarParser.Class, 0); }
		public ClassIdContext classId() {
			return getRuleContext(ClassIdContext.class,0);
		}
		public ClassBodyContext classBody() {
			return getRuleContext(ClassBodyContext.class,0);
		}
		public ClassDefinitionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_classDefinition; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxStarListener ) ((MxStarListener)listener).enterClassDefinition(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxStarListener ) ((MxStarListener)listener).exitClassDefinition(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxStarVisitor ) return ((MxStarVisitor<? extends T>)visitor).visitClassDefinition(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ClassDefinitionContext classDefinition() throws RecognitionException {
		ClassDefinitionContext _localctx = new ClassDefinitionContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_classDefinition);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(104);
			match(Class);
			setState(105);
			classId();
			setState(106);
			classBody();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class FunctionDefinitionContext extends ParserRuleContext {
		public TypeIdContext typeId() {
			return getRuleContext(TypeIdContext.class,0);
		}
		public FunctionIdContext functionId() {
			return getRuleContext(FunctionIdContext.class,0);
		}
		public ParameterListContext parameterList() {
			return getRuleContext(ParameterListContext.class,0);
		}
		public FunctionBodyContext functionBody() {
			return getRuleContext(FunctionBodyContext.class,0);
		}
		public FunctionDefinitionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_functionDefinition; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxStarListener ) ((MxStarListener)listener).enterFunctionDefinition(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxStarListener ) ((MxStarListener)listener).exitFunctionDefinition(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxStarVisitor ) return ((MxStarVisitor<? extends T>)visitor).visitFunctionDefinition(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FunctionDefinitionContext functionDefinition() throws RecognitionException {
		FunctionDefinitionContext _localctx = new FunctionDefinitionContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_functionDefinition);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(108);
			typeId();
			setState(109);
			functionId();
			setState(110);
			parameterList();
			setState(111);
			functionBody();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class StatementContext extends ParserRuleContext {
		public StatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_statement; }
	 
		public StatementContext() { }
		public void copyFrom(StatementContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class CondStatContext extends StatementContext {
		public ConditionalStatementContext conditionalStatement() {
			return getRuleContext(ConditionalStatementContext.class,0);
		}
		public CondStatContext(StatementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxStarListener ) ((MxStarListener)listener).enterCondStat(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxStarListener ) ((MxStarListener)listener).exitCondStat(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxStarVisitor ) return ((MxStarVisitor<? extends T>)visitor).visitCondStat(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class NullStatContext extends StatementContext {
		public NullStatementContext nullStatement() {
			return getRuleContext(NullStatementContext.class,0);
		}
		public NullStatContext(StatementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxStarListener ) ((MxStarListener)listener).enterNullStat(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxStarListener ) ((MxStarListener)listener).exitNullStat(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxStarVisitor ) return ((MxStarVisitor<? extends T>)visitor).visitNullStat(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class JumpStatContext extends StatementContext {
		public JumpStatementContext jumpStatement() {
			return getRuleContext(JumpStatementContext.class,0);
		}
		public JumpStatContext(StatementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxStarListener ) ((MxStarListener)listener).enterJumpStat(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxStarListener ) ((MxStarListener)listener).exitJumpStat(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxStarVisitor ) return ((MxStarVisitor<? extends T>)visitor).visitJumpStat(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ExprStatContext extends StatementContext {
		public ExpressionStatementContext expressionStatement() {
			return getRuleContext(ExpressionStatementContext.class,0);
		}
		public ExprStatContext(StatementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxStarListener ) ((MxStarListener)listener).enterExprStat(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxStarListener ) ((MxStarListener)listener).exitExprStat(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxStarVisitor ) return ((MxStarVisitor<? extends T>)visitor).visitExprStat(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class CompStatContext extends StatementContext {
		public CompoundStatementContext compoundStatement() {
			return getRuleContext(CompoundStatementContext.class,0);
		}
		public CompStatContext(StatementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxStarListener ) ((MxStarListener)listener).enterCompStat(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxStarListener ) ((MxStarListener)listener).exitCompStat(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxStarVisitor ) return ((MxStarVisitor<? extends T>)visitor).visitCompStat(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class IterStatContext extends StatementContext {
		public IterativeStatementContext iterativeStatement() {
			return getRuleContext(IterativeStatementContext.class,0);
		}
		public IterStatContext(StatementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxStarListener ) ((MxStarListener)listener).enterIterStat(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxStarListener ) ((MxStarListener)listener).exitIterStat(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxStarVisitor ) return ((MxStarVisitor<? extends T>)visitor).visitIterStat(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class VdefStatContext extends StatementContext {
		public VarDefStatementContext varDefStatement() {
			return getRuleContext(VarDefStatementContext.class,0);
		}
		public VdefStatContext(StatementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxStarListener ) ((MxStarListener)listener).enterVdefStat(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxStarListener ) ((MxStarListener)listener).exitVdefStat(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxStarVisitor ) return ((MxStarVisitor<? extends T>)visitor).visitVdefStat(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StatementContext statement() throws RecognitionException {
		StatementContext _localctx = new StatementContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_statement);
		try {
			setState(120);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,2,_ctx) ) {
			case 1:
				_localctx = new CompStatContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(113);
				compoundStatement();
				}
				break;
			case 2:
				_localctx = new ExprStatContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(114);
				expressionStatement();
				}
				break;
			case 3:
				_localctx = new CondStatContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(115);
				conditionalStatement();
				}
				break;
			case 4:
				_localctx = new IterStatContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(116);
				iterativeStatement();
				}
				break;
			case 5:
				_localctx = new JumpStatContext(_localctx);
				enterOuterAlt(_localctx, 5);
				{
				setState(117);
				jumpStatement();
				}
				break;
			case 6:
				_localctx = new NullStatContext(_localctx);
				enterOuterAlt(_localctx, 6);
				{
				setState(118);
				nullStatement();
				}
				break;
			case 7:
				_localctx = new VdefStatContext(_localctx);
				enterOuterAlt(_localctx, 7);
				{
				setState(119);
				varDefStatement();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ClassIdContext extends ParserRuleContext {
		public TerminalNode Identifier() { return getToken(MxStarParser.Identifier, 0); }
		public ClassIdContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_classId; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxStarListener ) ((MxStarListener)listener).enterClassId(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxStarListener ) ((MxStarListener)listener).exitClassId(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxStarVisitor ) return ((MxStarVisitor<? extends T>)visitor).visitClassId(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ClassIdContext classId() throws RecognitionException {
		ClassIdContext _localctx = new ClassIdContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_classId);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(122);
			match(Identifier);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ClassBodyContext extends ParserRuleContext {
		public List<VarDefStatementWithoutInitContext> varDefStatementWithoutInit() {
			return getRuleContexts(VarDefStatementWithoutInitContext.class);
		}
		public VarDefStatementWithoutInitContext varDefStatementWithoutInit(int i) {
			return getRuleContext(VarDefStatementWithoutInitContext.class,i);
		}
		public List<FunctionDefinitionContext> functionDefinition() {
			return getRuleContexts(FunctionDefinitionContext.class);
		}
		public FunctionDefinitionContext functionDefinition(int i) {
			return getRuleContext(FunctionDefinitionContext.class,i);
		}
		public ConstructionFunctionContext constructionFunction() {
			return getRuleContext(ConstructionFunctionContext.class,0);
		}
		public ClassBodyContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_classBody; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxStarListener ) ((MxStarListener)listener).enterClassBody(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxStarListener ) ((MxStarListener)listener).exitClassBody(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxStarVisitor ) return ((MxStarVisitor<? extends T>)visitor).visitClassBody(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ClassBodyContext classBody() throws RecognitionException {
		ClassBodyContext _localctx = new ClassBodyContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_classBody);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(124);
			match(T__0);
			{
			setState(129);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,4,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					setState(127);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,3,_ctx) ) {
					case 1:
						{
						setState(125);
						varDefStatementWithoutInit();
						}
						break;
					case 2:
						{
						setState(126);
						functionDefinition();
						}
						break;
					}
					} 
				}
				setState(131);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,4,_ctx);
			}
			setState(133);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,5,_ctx) ) {
			case 1:
				{
				setState(132);
				constructionFunction();
				}
				break;
			}
			setState(139);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << Bool) | (1L << Int) | (1L << String) | (1L << Void) | (1L << Identifier))) != 0)) {
				{
				setState(137);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,6,_ctx) ) {
				case 1:
					{
					setState(135);
					varDefStatementWithoutInit();
					}
					break;
				case 2:
					{
					setState(136);
					functionDefinition();
					}
					break;
				}
				}
				setState(141);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
			setState(142);
			match(T__1);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ConstructionFunctionContext extends ParserRuleContext {
		public ClassIdContext classId() {
			return getRuleContext(ClassIdContext.class,0);
		}
		public EmptyParameterListContext emptyParameterList() {
			return getRuleContext(EmptyParameterListContext.class,0);
		}
		public FunctionBodyContext functionBody() {
			return getRuleContext(FunctionBodyContext.class,0);
		}
		public ConstructionFunctionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_constructionFunction; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxStarListener ) ((MxStarListener)listener).enterConstructionFunction(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxStarListener ) ((MxStarListener)listener).exitConstructionFunction(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxStarVisitor ) return ((MxStarVisitor<? extends T>)visitor).visitConstructionFunction(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ConstructionFunctionContext constructionFunction() throws RecognitionException {
		ConstructionFunctionContext _localctx = new ConstructionFunctionContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_constructionFunction);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(144);
			classId();
			setState(145);
			emptyParameterList();
			setState(146);
			functionBody();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class FunctionIdContext extends ParserRuleContext {
		public TerminalNode Identifier() { return getToken(MxStarParser.Identifier, 0); }
		public FunctionIdContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_functionId; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxStarListener ) ((MxStarListener)listener).enterFunctionId(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxStarListener ) ((MxStarListener)listener).exitFunctionId(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxStarVisitor ) return ((MxStarVisitor<? extends T>)visitor).visitFunctionId(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FunctionIdContext functionId() throws RecognitionException {
		FunctionIdContext _localctx = new FunctionIdContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_functionId);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(148);
			match(Identifier);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ParameterListContext extends ParserRuleContext {
		public List<ParameterContext> parameter() {
			return getRuleContexts(ParameterContext.class);
		}
		public ParameterContext parameter(int i) {
			return getRuleContext(ParameterContext.class,i);
		}
		public ParameterListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_parameterList; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxStarListener ) ((MxStarListener)listener).enterParameterList(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxStarListener ) ((MxStarListener)listener).exitParameterList(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxStarVisitor ) return ((MxStarVisitor<? extends T>)visitor).visitParameterList(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ParameterListContext parameterList() throws RecognitionException {
		ParameterListContext _localctx = new ParameterListContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_parameterList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(150);
			match(T__2);
			setState(162);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__3:
				{
				setState(151);
				match(T__3);
				}
				break;
			case Bool:
			case Int:
			case String:
			case Void:
			case Identifier:
				{
				setState(152);
				parameter();
				setState(157);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__4) {
					{
					{
					setState(153);
					match(T__4);
					setState(154);
					parameter();
					}
					}
					setState(159);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(160);
				match(T__3);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class EmptyParameterListContext extends ParserRuleContext {
		public EmptyParameterListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_emptyParameterList; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxStarListener ) ((MxStarListener)listener).enterEmptyParameterList(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxStarListener ) ((MxStarListener)listener).exitEmptyParameterList(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxStarVisitor ) return ((MxStarVisitor<? extends T>)visitor).visitEmptyParameterList(this);
			else return visitor.visitChildren(this);
		}
	}

	public final EmptyParameterListContext emptyParameterList() throws RecognitionException {
		EmptyParameterListContext _localctx = new EmptyParameterListContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_emptyParameterList);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(164);
			match(T__2);
			setState(165);
			match(T__3);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ParameterContext extends ParserRuleContext {
		public TypeIdContext typeId() {
			return getRuleContext(TypeIdContext.class,0);
		}
		public VariableIdContext variableId() {
			return getRuleContext(VariableIdContext.class,0);
		}
		public ParameterContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_parameter; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxStarListener ) ((MxStarListener)listener).enterParameter(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxStarListener ) ((MxStarListener)listener).exitParameter(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxStarVisitor ) return ((MxStarVisitor<? extends T>)visitor).visitParameter(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ParameterContext parameter() throws RecognitionException {
		ParameterContext _localctx = new ParameterContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_parameter);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(167);
			typeId();
			setState(168);
			variableId();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class FunctionBodyContext extends ParserRuleContext {
		public CompoundStatementContext compoundStatement() {
			return getRuleContext(CompoundStatementContext.class,0);
		}
		public FunctionBodyContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_functionBody; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxStarListener ) ((MxStarListener)listener).enterFunctionBody(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxStarListener ) ((MxStarListener)listener).exitFunctionBody(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxStarVisitor ) return ((MxStarVisitor<? extends T>)visitor).visitFunctionBody(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FunctionBodyContext functionBody() throws RecognitionException {
		FunctionBodyContext _localctx = new FunctionBodyContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_functionBody);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(170);
			compoundStatement();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class CompoundStatementContext extends ParserRuleContext {
		public List<StatementContext> statement() {
			return getRuleContexts(StatementContext.class);
		}
		public StatementContext statement(int i) {
			return getRuleContext(StatementContext.class,i);
		}
		public CompoundStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_compoundStatement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxStarListener ) ((MxStarListener)listener).enterCompoundStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxStarListener ) ((MxStarListener)listener).exitCompoundStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxStarVisitor ) return ((MxStarVisitor<? extends T>)visitor).visitCompoundStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CompoundStatementContext compoundStatement() throws RecognitionException {
		CompoundStatementContext _localctx = new CompoundStatementContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_compoundStatement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(172);
			match(T__0);
			{
			setState(176);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__0) | (1L << T__2) | (1L << T__5) | (1L << T__10) | (1L << T__11) | (1L << T__12) | (1L << T__13) | (1L << T__14) | (1L << T__15) | (1L << StringLiteral) | (1L << True) | (1L << False) | (1L << IntLiteral) | (1L << NullLiteral) | (1L << Bool) | (1L << Int) | (1L << String) | (1L << Void) | (1L << If) | (1L << For) | (1L << While) | (1L << Break) | (1L << Continue) | (1L << Return) | (1L << New) | (1L << Identifier))) != 0)) {
				{
				{
				setState(173);
				statement();
				}
				}
				setState(178);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
			setState(179);
			match(T__1);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ExpressionStatementContext extends ParserRuleContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public ExpressionStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expressionStatement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxStarListener ) ((MxStarListener)listener).enterExpressionStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxStarListener ) ((MxStarListener)listener).exitExpressionStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxStarVisitor ) return ((MxStarVisitor<? extends T>)visitor).visitExpressionStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExpressionStatementContext expressionStatement() throws RecognitionException {
		ExpressionStatementContext _localctx = new ExpressionStatementContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_expressionStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(181);
			expression();
			setState(182);
			match(T__5);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ConditionalStatementContext extends ParserRuleContext {
		public List<TerminalNode> If() { return getTokens(MxStarParser.If); }
		public TerminalNode If(int i) {
			return getToken(MxStarParser.If, i);
		}
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public List<StatementContext> statement() {
			return getRuleContexts(StatementContext.class);
		}
		public StatementContext statement(int i) {
			return getRuleContext(StatementContext.class,i);
		}
		public List<TerminalNode> Else() { return getTokens(MxStarParser.Else); }
		public TerminalNode Else(int i) {
			return getToken(MxStarParser.Else, i);
		}
		public ConditionalStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_conditionalStatement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxStarListener ) ((MxStarListener)listener).enterConditionalStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxStarListener ) ((MxStarListener)listener).exitConditionalStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxStarVisitor ) return ((MxStarVisitor<? extends T>)visitor).visitConditionalStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ConditionalStatementContext conditionalStatement() throws RecognitionException {
		ConditionalStatementContext _localctx = new ConditionalStatementContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_conditionalStatement);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(184);
			match(If);
			setState(185);
			match(T__2);
			setState(186);
			expression();
			setState(187);
			match(T__3);
			setState(188);
			statement();
			setState(198);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,11,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(189);
					match(Else);
					setState(190);
					match(If);
					setState(191);
					match(T__2);
					setState(192);
					expression();
					setState(193);
					match(T__3);
					setState(194);
					statement();
					}
					} 
				}
				setState(200);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,11,_ctx);
			}
			setState(203);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,12,_ctx) ) {
			case 1:
				{
				setState(201);
				match(Else);
				setState(202);
				statement();
				}
				break;
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class IterativeStatementContext extends ParserRuleContext {
		public IterativeStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_iterativeStatement; }
	 
		public IterativeStatementContext() { }
		public void copyFrom(IterativeStatementContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class IterWhileStatementContext extends IterativeStatementContext {
		public WhileStatementContext whileStatement() {
			return getRuleContext(WhileStatementContext.class,0);
		}
		public IterWhileStatementContext(IterativeStatementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxStarListener ) ((MxStarListener)listener).enterIterWhileStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxStarListener ) ((MxStarListener)listener).exitIterWhileStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxStarVisitor ) return ((MxStarVisitor<? extends T>)visitor).visitIterWhileStatement(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class IterForStatementContext extends IterativeStatementContext {
		public ForStatementContext forStatement() {
			return getRuleContext(ForStatementContext.class,0);
		}
		public IterForStatementContext(IterativeStatementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxStarListener ) ((MxStarListener)listener).enterIterForStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxStarListener ) ((MxStarListener)listener).exitIterForStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxStarVisitor ) return ((MxStarVisitor<? extends T>)visitor).visitIterForStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final IterativeStatementContext iterativeStatement() throws RecognitionException {
		IterativeStatementContext _localctx = new IterativeStatementContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_iterativeStatement);
		try {
			setState(207);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case While:
				_localctx = new IterWhileStatementContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(205);
				whileStatement();
				}
				break;
			case For:
				_localctx = new IterForStatementContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(206);
				forStatement();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class WhileStatementContext extends ParserRuleContext {
		public TerminalNode While() { return getToken(MxStarParser.While, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public StatementContext statement() {
			return getRuleContext(StatementContext.class,0);
		}
		public WhileStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_whileStatement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxStarListener ) ((MxStarListener)listener).enterWhileStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxStarListener ) ((MxStarListener)listener).exitWhileStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxStarVisitor ) return ((MxStarVisitor<? extends T>)visitor).visitWhileStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final WhileStatementContext whileStatement() throws RecognitionException {
		WhileStatementContext _localctx = new WhileStatementContext(_ctx, getState());
		enterRule(_localctx, 32, RULE_whileStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(209);
			match(While);
			setState(210);
			match(T__2);
			setState(211);
			expression();
			setState(212);
			match(T__3);
			setState(213);
			statement();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ForStatementContext extends ParserRuleContext {
		public ExpressionContext initExpr;
		public ExpressionContext condExpr;
		public ExpressionContext loopExpr;
		public TerminalNode For() { return getToken(MxStarParser.For, 0); }
		public StatementContext statement() {
			return getRuleContext(StatementContext.class,0);
		}
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public ForStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_forStatement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxStarListener ) ((MxStarListener)listener).enterForStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxStarListener ) ((MxStarListener)listener).exitForStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxStarVisitor ) return ((MxStarVisitor<? extends T>)visitor).visitForStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ForStatementContext forStatement() throws RecognitionException {
		ForStatementContext _localctx = new ForStatementContext(_ctx, getState());
		enterRule(_localctx, 34, RULE_forStatement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(215);
			match(For);
			setState(216);
			match(T__2);
			setState(218);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__2) | (1L << T__10) | (1L << T__11) | (1L << T__12) | (1L << T__13) | (1L << T__14) | (1L << T__15) | (1L << StringLiteral) | (1L << True) | (1L << False) | (1L << IntLiteral) | (1L << NullLiteral) | (1L << New) | (1L << Identifier))) != 0)) {
				{
				setState(217);
				((ForStatementContext)_localctx).initExpr = expression();
				}
			}

			setState(220);
			match(T__5);
			setState(222);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__2) | (1L << T__10) | (1L << T__11) | (1L << T__12) | (1L << T__13) | (1L << T__14) | (1L << T__15) | (1L << StringLiteral) | (1L << True) | (1L << False) | (1L << IntLiteral) | (1L << NullLiteral) | (1L << New) | (1L << Identifier))) != 0)) {
				{
				setState(221);
				((ForStatementContext)_localctx).condExpr = expression();
				}
			}

			setState(224);
			match(T__5);
			setState(226);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__2) | (1L << T__10) | (1L << T__11) | (1L << T__12) | (1L << T__13) | (1L << T__14) | (1L << T__15) | (1L << StringLiteral) | (1L << True) | (1L << False) | (1L << IntLiteral) | (1L << NullLiteral) | (1L << New) | (1L << Identifier))) != 0)) {
				{
				setState(225);
				((ForStatementContext)_localctx).loopExpr = expression();
				}
			}

			setState(228);
			match(T__3);
			setState(229);
			statement();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class JumpStatementContext extends ParserRuleContext {
		public JumpStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_jumpStatement; }
	 
		public JumpStatementContext() { }
		public void copyFrom(JumpStatementContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class RetJumpStatementContext extends JumpStatementContext {
		public TerminalNode Return() { return getToken(MxStarParser.Return, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public RetJumpStatementContext(JumpStatementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxStarListener ) ((MxStarListener)listener).enterRetJumpStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxStarListener ) ((MxStarListener)listener).exitRetJumpStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxStarVisitor ) return ((MxStarVisitor<? extends T>)visitor).visitRetJumpStatement(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class CtnJumpStatementContext extends JumpStatementContext {
		public TerminalNode Continue() { return getToken(MxStarParser.Continue, 0); }
		public CtnJumpStatementContext(JumpStatementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxStarListener ) ((MxStarListener)listener).enterCtnJumpStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxStarListener ) ((MxStarListener)listener).exitCtnJumpStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxStarVisitor ) return ((MxStarVisitor<? extends T>)visitor).visitCtnJumpStatement(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class BrkJumpStatementContext extends JumpStatementContext {
		public TerminalNode Break() { return getToken(MxStarParser.Break, 0); }
		public BrkJumpStatementContext(JumpStatementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxStarListener ) ((MxStarListener)listener).enterBrkJumpStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxStarListener ) ((MxStarListener)listener).exitBrkJumpStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxStarVisitor ) return ((MxStarVisitor<? extends T>)visitor).visitBrkJumpStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final JumpStatementContext jumpStatement() throws RecognitionException {
		JumpStatementContext _localctx = new JumpStatementContext(_ctx, getState());
		enterRule(_localctx, 36, RULE_jumpStatement);
		int _la;
		try {
			setState(240);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case Return:
				_localctx = new RetJumpStatementContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(231);
				match(Return);
				setState(233);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__2) | (1L << T__10) | (1L << T__11) | (1L << T__12) | (1L << T__13) | (1L << T__14) | (1L << T__15) | (1L << StringLiteral) | (1L << True) | (1L << False) | (1L << IntLiteral) | (1L << NullLiteral) | (1L << New) | (1L << Identifier))) != 0)) {
					{
					setState(232);
					expression();
					}
				}

				setState(235);
				match(T__5);
				}
				break;
			case Break:
				_localctx = new BrkJumpStatementContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(236);
				match(Break);
				setState(237);
				match(T__5);
				}
				break;
			case Continue:
				_localctx = new CtnJumpStatementContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(238);
				match(Continue);
				setState(239);
				match(T__5);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class NullStatementContext extends ParserRuleContext {
		public NullStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_nullStatement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxStarListener ) ((MxStarListener)listener).enterNullStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxStarListener ) ((MxStarListener)listener).exitNullStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxStarVisitor ) return ((MxStarVisitor<? extends T>)visitor).visitNullStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final NullStatementContext nullStatement() throws RecognitionException {
		NullStatementContext _localctx = new NullStatementContext(_ctx, getState());
		enterRule(_localctx, 38, RULE_nullStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(242);
			match(T__5);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class VarDefStatementContext extends ParserRuleContext {
		public VariableDefinitionContext variableDefinition() {
			return getRuleContext(VariableDefinitionContext.class,0);
		}
		public VarDefStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_varDefStatement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxStarListener ) ((MxStarListener)listener).enterVarDefStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxStarListener ) ((MxStarListener)listener).exitVarDefStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxStarVisitor ) return ((MxStarVisitor<? extends T>)visitor).visitVarDefStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final VarDefStatementContext varDefStatement() throws RecognitionException {
		VarDefStatementContext _localctx = new VarDefStatementContext(_ctx, getState());
		enterRule(_localctx, 40, RULE_varDefStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(244);
			variableDefinition();
			setState(245);
			match(T__5);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class VarDefStatementWithoutInitContext extends ParserRuleContext {
		public VariableDefinitionWithoutInitContext variableDefinitionWithoutInit() {
			return getRuleContext(VariableDefinitionWithoutInitContext.class,0);
		}
		public VarDefStatementWithoutInitContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_varDefStatementWithoutInit; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxStarListener ) ((MxStarListener)listener).enterVarDefStatementWithoutInit(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxStarListener ) ((MxStarListener)listener).exitVarDefStatementWithoutInit(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxStarVisitor ) return ((MxStarVisitor<? extends T>)visitor).visitVarDefStatementWithoutInit(this);
			else return visitor.visitChildren(this);
		}
	}

	public final VarDefStatementWithoutInitContext varDefStatementWithoutInit() throws RecognitionException {
		VarDefStatementWithoutInitContext _localctx = new VarDefStatementWithoutInitContext(_ctx, getState());
		enterRule(_localctx, 42, RULE_varDefStatementWithoutInit);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(247);
			variableDefinitionWithoutInit();
			setState(248);
			match(T__5);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class VariableDefinitionContext extends ParserRuleContext {
		public TypeIdContext typeId() {
			return getRuleContext(TypeIdContext.class,0);
		}
		public List<VarDefContext> varDef() {
			return getRuleContexts(VarDefContext.class);
		}
		public VarDefContext varDef(int i) {
			return getRuleContext(VarDefContext.class,i);
		}
		public VariableDefinitionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_variableDefinition; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxStarListener ) ((MxStarListener)listener).enterVariableDefinition(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxStarListener ) ((MxStarListener)listener).exitVariableDefinition(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxStarVisitor ) return ((MxStarVisitor<? extends T>)visitor).visitVariableDefinition(this);
			else return visitor.visitChildren(this);
		}
	}

	public final VariableDefinitionContext variableDefinition() throws RecognitionException {
		VariableDefinitionContext _localctx = new VariableDefinitionContext(_ctx, getState());
		enterRule(_localctx, 44, RULE_variableDefinition);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(250);
			typeId();
			setState(251);
			varDef();
			setState(256);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__4) {
				{
				{
				setState(252);
				match(T__4);
				setState(253);
				varDef();
				}
				}
				setState(258);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class VariableDefinitionWithoutInitContext extends ParserRuleContext {
		public TypeIdContext typeId() {
			return getRuleContext(TypeIdContext.class,0);
		}
		public List<VariableIdContext> variableId() {
			return getRuleContexts(VariableIdContext.class);
		}
		public VariableIdContext variableId(int i) {
			return getRuleContext(VariableIdContext.class,i);
		}
		public VariableDefinitionWithoutInitContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_variableDefinitionWithoutInit; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxStarListener ) ((MxStarListener)listener).enterVariableDefinitionWithoutInit(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxStarListener ) ((MxStarListener)listener).exitVariableDefinitionWithoutInit(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxStarVisitor ) return ((MxStarVisitor<? extends T>)visitor).visitVariableDefinitionWithoutInit(this);
			else return visitor.visitChildren(this);
		}
	}

	public final VariableDefinitionWithoutInitContext variableDefinitionWithoutInit() throws RecognitionException {
		VariableDefinitionWithoutInitContext _localctx = new VariableDefinitionWithoutInitContext(_ctx, getState());
		enterRule(_localctx, 46, RULE_variableDefinitionWithoutInit);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(259);
			typeId();
			setState(260);
			variableId();
			setState(265);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__4) {
				{
				{
				setState(261);
				match(T__4);
				setState(262);
				variableId();
				}
				}
				setState(267);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class VarDefContext extends ParserRuleContext {
		public VarDefContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_varDef; }
	 
		public VarDefContext() { }
		public void copyFrom(VarDefContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class VarWithoutInitContext extends VarDefContext {
		public VariableIdContext variableId() {
			return getRuleContext(VariableIdContext.class,0);
		}
		public VarWithoutInitContext(VarDefContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxStarListener ) ((MxStarListener)listener).enterVarWithoutInit(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxStarListener ) ((MxStarListener)listener).exitVarWithoutInit(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxStarVisitor ) return ((MxStarVisitor<? extends T>)visitor).visitVarWithoutInit(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class VarWithInitContext extends VarDefContext {
		public VariableIdContext variableId() {
			return getRuleContext(VariableIdContext.class,0);
		}
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public VarWithInitContext(VarDefContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxStarListener ) ((MxStarListener)listener).enterVarWithInit(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxStarListener ) ((MxStarListener)listener).exitVarWithInit(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxStarVisitor ) return ((MxStarVisitor<? extends T>)visitor).visitVarWithInit(this);
			else return visitor.visitChildren(this);
		}
	}

	public final VarDefContext varDef() throws RecognitionException {
		VarDefContext _localctx = new VarDefContext(_ctx, getState());
		enterRule(_localctx, 48, RULE_varDef);
		try {
			setState(273);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,21,_ctx) ) {
			case 1:
				_localctx = new VarWithoutInitContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(268);
				variableId();
				}
				break;
			case 2:
				_localctx = new VarWithInitContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(269);
				variableId();
				setState(270);
				match(T__6);
				setState(271);
				expression();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ExpressionContext extends ParserRuleContext {
		public AssignmentExprContext assignmentExpr() {
			return getRuleContext(AssignmentExprContext.class,0);
		}
		public ExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxStarListener ) ((MxStarListener)listener).enterExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxStarListener ) ((MxStarListener)listener).exitExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxStarVisitor ) return ((MxStarVisitor<? extends T>)visitor).visitExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExpressionContext expression() throws RecognitionException {
		ExpressionContext _localctx = new ExpressionContext(_ctx, getState());
		enterRule(_localctx, 50, RULE_expression);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(275);
			assignmentExpr();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class LiteralContext extends ParserRuleContext {
		public LiteralContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_literal; }
	 
		public LiteralContext() { }
		public void copyFrom(LiteralContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class ArithmeticLogicalLiteralContext extends LiteralContext {
		public LogicalLiteralContext logicalLiteral() {
			return getRuleContext(LogicalLiteralContext.class,0);
		}
		public ArithmeticLogicalLiteralContext(LiteralContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxStarListener ) ((MxStarListener)listener).enterArithmeticLogicalLiteral(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxStarListener ) ((MxStarListener)listener).exitArithmeticLogicalLiteral(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxStarVisitor ) return ((MxStarVisitor<? extends T>)visitor).visitArithmeticLogicalLiteral(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class UnarithmeticStringLiteralContext extends LiteralContext {
		public TerminalNode StringLiteral() { return getToken(MxStarParser.StringLiteral, 0); }
		public UnarithmeticStringLiteralContext(LiteralContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxStarListener ) ((MxStarListener)listener).enterUnarithmeticStringLiteral(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxStarListener ) ((MxStarListener)listener).exitUnarithmeticStringLiteral(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxStarVisitor ) return ((MxStarVisitor<? extends T>)visitor).visitUnarithmeticStringLiteral(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class UnarithmeticNullLiteralContext extends LiteralContext {
		public TerminalNode NullLiteral() { return getToken(MxStarParser.NullLiteral, 0); }
		public UnarithmeticNullLiteralContext(LiteralContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxStarListener ) ((MxStarListener)listener).enterUnarithmeticNullLiteral(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxStarListener ) ((MxStarListener)listener).exitUnarithmeticNullLiteral(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxStarVisitor ) return ((MxStarVisitor<? extends T>)visitor).visitUnarithmeticNullLiteral(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ArithmeticIntLiteralContext extends LiteralContext {
		public TerminalNode IntLiteral() { return getToken(MxStarParser.IntLiteral, 0); }
		public ArithmeticIntLiteralContext(LiteralContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxStarListener ) ((MxStarListener)listener).enterArithmeticIntLiteral(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxStarListener ) ((MxStarListener)listener).exitArithmeticIntLiteral(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxStarVisitor ) return ((MxStarVisitor<? extends T>)visitor).visitArithmeticIntLiteral(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LiteralContext literal() throws RecognitionException {
		LiteralContext _localctx = new LiteralContext(_ctx, getState());
		enterRule(_localctx, 52, RULE_literal);
		try {
			setState(281);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case IntLiteral:
				_localctx = new ArithmeticIntLiteralContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(277);
				match(IntLiteral);
				}
				break;
			case True:
			case False:
				_localctx = new ArithmeticLogicalLiteralContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(278);
				logicalLiteral();
				}
				break;
			case StringLiteral:
				_localctx = new UnarithmeticStringLiteralContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(279);
				match(StringLiteral);
				}
				break;
			case NullLiteral:
				_localctx = new UnarithmeticNullLiteralContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(280);
				match(NullLiteral);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ElementExprContext extends ParserRuleContext {
		public ElementExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_elementExpr; }
	 
		public ElementExprContext() { }
		public void copyFrom(ElementExprContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class LiteralElemExprContext extends ElementExprContext {
		public LiteralContext literal() {
			return getRuleContext(LiteralContext.class,0);
		}
		public LiteralElemExprContext(ElementExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxStarListener ) ((MxStarListener)listener).enterLiteralElemExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxStarListener ) ((MxStarListener)listener).exitLiteralElemExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxStarVisitor ) return ((MxStarVisitor<? extends T>)visitor).visitLiteralElemExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class SonElemExprContext extends ElementExprContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public SonElemExprContext(ElementExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxStarListener ) ((MxStarListener)listener).enterSonElemExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxStarListener ) ((MxStarListener)listener).exitSonElemExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxStarVisitor ) return ((MxStarVisitor<? extends T>)visitor).visitSonElemExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class VarElemExprContext extends ElementExprContext {
		public VariableIdContext variableId() {
			return getRuleContext(VariableIdContext.class,0);
		}
		public VarElemExprContext(ElementExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxStarListener ) ((MxStarListener)listener).enterVarElemExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxStarListener ) ((MxStarListener)listener).exitVarElemExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxStarVisitor ) return ((MxStarVisitor<? extends T>)visitor).visitVarElemExpr(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ElementExprContext elementExpr() throws RecognitionException {
		ElementExprContext _localctx = new ElementExprContext(_ctx, getState());
		enterRule(_localctx, 54, RULE_elementExpr);
		try {
			setState(289);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case StringLiteral:
			case True:
			case False:
			case IntLiteral:
			case NullLiteral:
				_localctx = new LiteralElemExprContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(283);
				literal();
				}
				break;
			case Identifier:
				_localctx = new VarElemExprContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(284);
				variableId();
				}
				break;
			case T__2:
				_localctx = new SonElemExprContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(285);
				match(T__2);
				setState(286);
				expression();
				setState(287);
				match(T__3);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class PrimaryExprContext extends ParserRuleContext {
		public PrimaryExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_primaryExpr; }
	 
		public PrimaryExprContext() { }
		public void copyFrom(PrimaryExprContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class ObjAccPrimExprContext extends PrimaryExprContext {
		public List<PrimaryExprContext> primaryExpr() {
			return getRuleContexts(PrimaryExprContext.class);
		}
		public PrimaryExprContext primaryExpr(int i) {
			return getRuleContext(PrimaryExprContext.class,i);
		}
		public ObjAccPrimExprContext(PrimaryExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxStarListener ) ((MxStarListener)listener).enterObjAccPrimExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxStarListener ) ((MxStarListener)listener).exitObjAccPrimExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxStarVisitor ) return ((MxStarVisitor<? extends T>)visitor).visitObjAccPrimExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ElemPrimExprContext extends PrimaryExprContext {
		public ElementExprContext elementExpr() {
			return getRuleContext(ElementExprContext.class,0);
		}
		public ElemPrimExprContext(PrimaryExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxStarListener ) ((MxStarListener)listener).enterElemPrimExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxStarListener ) ((MxStarListener)listener).exitElemPrimExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxStarVisitor ) return ((MxStarVisitor<? extends T>)visitor).visitElemPrimExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class FuncPrimExprContext extends PrimaryExprContext {
		public FunctionIdContext functionId() {
			return getRuleContext(FunctionIdContext.class,0);
		}
		public ArgumentListContext argumentList() {
			return getRuleContext(ArgumentListContext.class,0);
		}
		public FuncPrimExprContext(PrimaryExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxStarListener ) ((MxStarListener)listener).enterFuncPrimExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxStarListener ) ((MxStarListener)listener).exitFuncPrimExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxStarVisitor ) return ((MxStarVisitor<? extends T>)visitor).visitFuncPrimExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ArrPrimExprContext extends PrimaryExprContext {
		public PrimaryExprContext primaryExpr() {
			return getRuleContext(PrimaryExprContext.class,0);
		}
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public ArrPrimExprContext(PrimaryExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxStarListener ) ((MxStarListener)listener).enterArrPrimExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxStarListener ) ((MxStarListener)listener).exitArrPrimExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxStarVisitor ) return ((MxStarVisitor<? extends T>)visitor).visitArrPrimExpr(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PrimaryExprContext primaryExpr() throws RecognitionException {
		return primaryExpr(0);
	}

	private PrimaryExprContext primaryExpr(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		PrimaryExprContext _localctx = new PrimaryExprContext(_ctx, _parentState);
		PrimaryExprContext _prevctx = _localctx;
		int _startState = 56;
		enterRecursionRule(_localctx, 56, RULE_primaryExpr, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(300);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,25,_ctx) ) {
			case 1:
				{
				_localctx = new ElemPrimExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;

				setState(292);
				elementExpr();
				}
				break;
			case 2:
				{
				_localctx = new FuncPrimExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(293);
				functionId();
				setState(294);
				match(T__2);
				setState(296);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__2) | (1L << T__10) | (1L << T__11) | (1L << T__12) | (1L << T__13) | (1L << T__14) | (1L << T__15) | (1L << StringLiteral) | (1L << True) | (1L << False) | (1L << IntLiteral) | (1L << NullLiteral) | (1L << New) | (1L << Identifier))) != 0)) {
					{
					setState(295);
					argumentList();
					}
				}

				setState(298);
				match(T__3);
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(312);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,27,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(310);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,26,_ctx) ) {
					case 1:
						{
						_localctx = new ObjAccPrimExprContext(new PrimaryExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_primaryExpr);
						setState(302);
						if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
						setState(303);
						match(T__9);
						setState(304);
						primaryExpr(3);
						}
						break;
					case 2:
						{
						_localctx = new ArrPrimExprContext(new PrimaryExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_primaryExpr);
						setState(305);
						if (!(precpred(_ctx, 3))) throw new FailedPredicateException(this, "precpred(_ctx, 3)");
						setState(306);
						match(T__7);
						setState(307);
						expression();
						setState(308);
						match(T__8);
						}
						break;
					}
					} 
				}
				setState(314);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,27,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class ArgumentListContext extends ParserRuleContext {
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public ArgumentListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_argumentList; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxStarListener ) ((MxStarListener)listener).enterArgumentList(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxStarListener ) ((MxStarListener)listener).exitArgumentList(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxStarVisitor ) return ((MxStarVisitor<? extends T>)visitor).visitArgumentList(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ArgumentListContext argumentList() throws RecognitionException {
		ArgumentListContext _localctx = new ArgumentListContext(_ctx, getState());
		enterRule(_localctx, 58, RULE_argumentList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(315);
			expression();
			setState(320);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__4) {
				{
				{
				setState(316);
				match(T__4);
				setState(317);
				expression();
				}
				}
				setState(322);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class RightUnaryExprContext extends ParserRuleContext {
		public RightUnaryExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_rightUnaryExpr; }
	 
		public RightUnaryExprContext() { }
		public void copyFrom(RightUnaryExprContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class PmRUExprContext extends RightUnaryExprContext {
		public Token op;
		public RightUnaryExprContext rightUnaryExpr() {
			return getRuleContext(RightUnaryExprContext.class,0);
		}
		public PmRUExprContext(RightUnaryExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxStarListener ) ((MxStarListener)listener).enterPmRUExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxStarListener ) ((MxStarListener)listener).exitPmRUExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxStarVisitor ) return ((MxStarVisitor<? extends T>)visitor).visitPmRUExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class PrimRUExprContext extends RightUnaryExprContext {
		public PrimaryExprContext primaryExpr() {
			return getRuleContext(PrimaryExprContext.class,0);
		}
		public PrimRUExprContext(RightUnaryExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxStarListener ) ((MxStarListener)listener).enterPrimRUExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxStarListener ) ((MxStarListener)listener).exitPrimRUExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxStarVisitor ) return ((MxStarVisitor<? extends T>)visitor).visitPrimRUExpr(this);
			else return visitor.visitChildren(this);
		}
	}

	public final RightUnaryExprContext rightUnaryExpr() throws RecognitionException {
		return rightUnaryExpr(0);
	}

	private RightUnaryExprContext rightUnaryExpr(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		RightUnaryExprContext _localctx = new RightUnaryExprContext(_ctx, _parentState);
		RightUnaryExprContext _prevctx = _localctx;
		int _startState = 60;
		enterRecursionRule(_localctx, 60, RULE_rightUnaryExpr, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			{
			_localctx = new PrimRUExprContext(_localctx);
			_ctx = _localctx;
			_prevctx = _localctx;

			setState(324);
			primaryExpr(0);
			}
			_ctx.stop = _input.LT(-1);
			setState(330);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,29,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new PmRUExprContext(new RightUnaryExprContext(_parentctx, _parentState));
					pushNewRecursionContext(_localctx, _startState, RULE_rightUnaryExpr);
					setState(326);
					if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
					{
					setState(327);
					((PmRUExprContext)_localctx).op = _input.LT(1);
					_la = _input.LA(1);
					if ( !(_la==T__10 || _la==T__11) ) {
						((PmRUExprContext)_localctx).op = (Token)_errHandler.recoverInline(this);
					}
					else {
						if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
						_errHandler.reportMatch(this);
						consume();
					}
					}
					}
					} 
				}
				setState(332);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,29,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class UnaryExprContext extends ParserRuleContext {
		public UnaryExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_unaryExpr; }
	 
		public UnaryExprContext() { }
		public void copyFrom(UnaryExprContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class LUExprContext extends UnaryExprContext {
		public Token op;
		public UnaryExprContext unaryExpr() {
			return getRuleContext(UnaryExprContext.class,0);
		}
		public LUExprContext(UnaryExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxStarListener ) ((MxStarListener)listener).enterLUExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxStarListener ) ((MxStarListener)listener).exitLUExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxStarVisitor ) return ((MxStarVisitor<? extends T>)visitor).visitLUExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class NewUExprContext extends UnaryExprContext {
		public TerminalNode New() { return getToken(MxStarParser.New, 0); }
		public NewCreatorContext newCreator() {
			return getRuleContext(NewCreatorContext.class,0);
		}
		public NewUExprContext(UnaryExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxStarListener ) ((MxStarListener)listener).enterNewUExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxStarListener ) ((MxStarListener)listener).exitNewUExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxStarVisitor ) return ((MxStarVisitor<? extends T>)visitor).visitNewUExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class RUExprContext extends UnaryExprContext {
		public RightUnaryExprContext rightUnaryExpr() {
			return getRuleContext(RightUnaryExprContext.class,0);
		}
		public RUExprContext(UnaryExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxStarListener ) ((MxStarListener)listener).enterRUExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxStarListener ) ((MxStarListener)listener).exitRUExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxStarVisitor ) return ((MxStarVisitor<? extends T>)visitor).visitRUExpr(this);
			else return visitor.visitChildren(this);
		}
	}

	public final UnaryExprContext unaryExpr() throws RecognitionException {
		UnaryExprContext _localctx = new UnaryExprContext(_ctx, getState());
		enterRule(_localctx, 62, RULE_unaryExpr);
		try {
			setState(348);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__2:
			case StringLiteral:
			case True:
			case False:
			case IntLiteral:
			case NullLiteral:
			case Identifier:
				_localctx = new RUExprContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(333);
				rightUnaryExpr(0);
				}
				break;
			case T__10:
				_localctx = new LUExprContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				{
				setState(334);
				((LUExprContext)_localctx).op = match(T__10);
				}
				setState(335);
				unaryExpr();
				}
				break;
			case T__11:
				_localctx = new LUExprContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				{
				setState(336);
				((LUExprContext)_localctx).op = match(T__11);
				}
				setState(337);
				unaryExpr();
				}
				break;
			case T__12:
				_localctx = new LUExprContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				{
				setState(338);
				((LUExprContext)_localctx).op = match(T__12);
				}
				setState(339);
				unaryExpr();
				}
				break;
			case T__13:
				_localctx = new LUExprContext(_localctx);
				enterOuterAlt(_localctx, 5);
				{
				{
				setState(340);
				((LUExprContext)_localctx).op = match(T__13);
				}
				setState(341);
				unaryExpr();
				}
				break;
			case T__14:
				_localctx = new LUExprContext(_localctx);
				enterOuterAlt(_localctx, 6);
				{
				{
				setState(342);
				((LUExprContext)_localctx).op = match(T__14);
				}
				setState(343);
				unaryExpr();
				}
				break;
			case T__15:
				_localctx = new LUExprContext(_localctx);
				enterOuterAlt(_localctx, 7);
				{
				{
				setState(344);
				((LUExprContext)_localctx).op = match(T__15);
				}
				setState(345);
				unaryExpr();
				}
				break;
			case New:
				_localctx = new NewUExprContext(_localctx);
				enterOuterAlt(_localctx, 8);
				{
				setState(346);
				match(New);
				setState(347);
				newCreator();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class NewCreatorContext extends ParserRuleContext {
		public ClassIdContext classId() {
			return getRuleContext(ClassIdContext.class,0);
		}
		public SimpleTypeIdContext simpleTypeId() {
			return getRuleContext(SimpleTypeIdContext.class,0);
		}
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public NewCreatorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_newCreator; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxStarListener ) ((MxStarListener)listener).enterNewCreator(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxStarListener ) ((MxStarListener)listener).exitNewCreator(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxStarVisitor ) return ((MxStarVisitor<? extends T>)visitor).visitNewCreator(this);
			else return visitor.visitChildren(this);
		}
	}

	public final NewCreatorContext newCreator() throws RecognitionException {
		NewCreatorContext _localctx = new NewCreatorContext(_ctx, getState());
		enterRule(_localctx, 64, RULE_newCreator);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(352);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case Identifier:
				{
				setState(350);
				classId();
				}
				break;
			case Bool:
			case Int:
			case String:
			case Void:
				{
				setState(351);
				simpleTypeId();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			setState(376);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,35,_ctx) ) {
			case 1:
				{
				setState(358);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,32,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(354);
						match(T__7);
						setState(355);
						match(T__8);
						}
						} 
					}
					setState(360);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,32,_ctx);
				}
				}
				break;
			case 2:
				{
				setState(365); 
				_errHandler.sync(this);
				_alt = 1;
				do {
					switch (_alt) {
					case 1:
						{
						{
						setState(361);
						match(T__7);
						setState(362);
						expression();
						setState(363);
						match(T__8);
						}
						}
						break;
					default:
						throw new NoViableAltException(this);
					}
					setState(367); 
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,33,_ctx);
				} while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER );
				setState(373);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,34,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(369);
						match(T__7);
						setState(370);
						match(T__8);
						}
						} 
					}
					setState(375);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,34,_ctx);
				}
				}
				break;
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class MultiplicativeExprContext extends ParserRuleContext {
		public MultiplicativeExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_multiplicativeExpr; }
	 
		public MultiplicativeExprContext() { }
		public void copyFrom(MultiplicativeExprContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class UnaryMulExprContext extends MultiplicativeExprContext {
		public UnaryExprContext unaryExpr() {
			return getRuleContext(UnaryExprContext.class,0);
		}
		public UnaryMulExprContext(MultiplicativeExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxStarListener ) ((MxStarListener)listener).enterUnaryMulExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxStarListener ) ((MxStarListener)listener).exitUnaryMulExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxStarVisitor ) return ((MxStarVisitor<? extends T>)visitor).visitUnaryMulExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class MulExprContext extends MultiplicativeExprContext {
		public Token op;
		public MultiplicativeExprContext multiplicativeExpr() {
			return getRuleContext(MultiplicativeExprContext.class,0);
		}
		public UnaryExprContext unaryExpr() {
			return getRuleContext(UnaryExprContext.class,0);
		}
		public MulExprContext(MultiplicativeExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxStarListener ) ((MxStarListener)listener).enterMulExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxStarListener ) ((MxStarListener)listener).exitMulExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxStarVisitor ) return ((MxStarVisitor<? extends T>)visitor).visitMulExpr(this);
			else return visitor.visitChildren(this);
		}
	}

	public final MultiplicativeExprContext multiplicativeExpr() throws RecognitionException {
		return multiplicativeExpr(0);
	}

	private MultiplicativeExprContext multiplicativeExpr(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		MultiplicativeExprContext _localctx = new MultiplicativeExprContext(_ctx, _parentState);
		MultiplicativeExprContext _prevctx = _localctx;
		int _startState = 66;
		enterRecursionRule(_localctx, 66, RULE_multiplicativeExpr, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			{
			_localctx = new UnaryMulExprContext(_localctx);
			_ctx = _localctx;
			_prevctx = _localctx;

			setState(379);
			unaryExpr();
			}
			_ctx.stop = _input.LT(-1);
			setState(392);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,37,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(390);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,36,_ctx) ) {
					case 1:
						{
						_localctx = new MulExprContext(new MultiplicativeExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_multiplicativeExpr);
						setState(381);
						if (!(precpred(_ctx, 3))) throw new FailedPredicateException(this, "precpred(_ctx, 3)");
						{
						setState(382);
						((MulExprContext)_localctx).op = match(T__16);
						}
						setState(383);
						unaryExpr();
						}
						break;
					case 2:
						{
						_localctx = new MulExprContext(new MultiplicativeExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_multiplicativeExpr);
						setState(384);
						if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
						{
						setState(385);
						((MulExprContext)_localctx).op = match(T__17);
						}
						setState(386);
						unaryExpr();
						}
						break;
					case 3:
						{
						_localctx = new MulExprContext(new MultiplicativeExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_multiplicativeExpr);
						setState(387);
						if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
						{
						setState(388);
						((MulExprContext)_localctx).op = match(T__18);
						}
						setState(389);
						unaryExpr();
						}
						break;
					}
					} 
				}
				setState(394);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,37,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class AdditiveExprContext extends ParserRuleContext {
		public AdditiveExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_additiveExpr; }
	 
		public AdditiveExprContext() { }
		public void copyFrom(AdditiveExprContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class MulAddExprContext extends AdditiveExprContext {
		public MultiplicativeExprContext multiplicativeExpr() {
			return getRuleContext(MultiplicativeExprContext.class,0);
		}
		public MulAddExprContext(AdditiveExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxStarListener ) ((MxStarListener)listener).enterMulAddExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxStarListener ) ((MxStarListener)listener).exitMulAddExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxStarVisitor ) return ((MxStarVisitor<? extends T>)visitor).visitMulAddExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class AddExprContext extends AdditiveExprContext {
		public Token op;
		public AdditiveExprContext additiveExpr() {
			return getRuleContext(AdditiveExprContext.class,0);
		}
		public MultiplicativeExprContext multiplicativeExpr() {
			return getRuleContext(MultiplicativeExprContext.class,0);
		}
		public AddExprContext(AdditiveExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxStarListener ) ((MxStarListener)listener).enterAddExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxStarListener ) ((MxStarListener)listener).exitAddExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxStarVisitor ) return ((MxStarVisitor<? extends T>)visitor).visitAddExpr(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AdditiveExprContext additiveExpr() throws RecognitionException {
		return additiveExpr(0);
	}

	private AdditiveExprContext additiveExpr(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		AdditiveExprContext _localctx = new AdditiveExprContext(_ctx, _parentState);
		AdditiveExprContext _prevctx = _localctx;
		int _startState = 68;
		enterRecursionRule(_localctx, 68, RULE_additiveExpr, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			{
			_localctx = new MulAddExprContext(_localctx);
			_ctx = _localctx;
			_prevctx = _localctx;

			setState(396);
			multiplicativeExpr(0);
			}
			_ctx.stop = _input.LT(-1);
			setState(406);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,39,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(404);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,38,_ctx) ) {
					case 1:
						{
						_localctx = new AddExprContext(new AdditiveExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_additiveExpr);
						setState(398);
						if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
						{
						setState(399);
						((AddExprContext)_localctx).op = match(T__15);
						}
						setState(400);
						multiplicativeExpr(0);
						}
						break;
					case 2:
						{
						_localctx = new AddExprContext(new AdditiveExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_additiveExpr);
						setState(401);
						if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
						{
						setState(402);
						((AddExprContext)_localctx).op = match(T__14);
						}
						setState(403);
						multiplicativeExpr(0);
						}
						break;
					}
					} 
				}
				setState(408);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,39,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class ShiftExprContext extends ParserRuleContext {
		public ShiftExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_shiftExpr; }
	 
		public ShiftExprContext() { }
		public void copyFrom(ShiftExprContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class AddShiftExprContext extends ShiftExprContext {
		public AdditiveExprContext additiveExpr() {
			return getRuleContext(AdditiveExprContext.class,0);
		}
		public AddShiftExprContext(ShiftExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxStarListener ) ((MxStarListener)listener).enterAddShiftExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxStarListener ) ((MxStarListener)listener).exitAddShiftExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxStarVisitor ) return ((MxStarVisitor<? extends T>)visitor).visitAddShiftExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ShftExprContext extends ShiftExprContext {
		public Token op;
		public ShiftExprContext shiftExpr() {
			return getRuleContext(ShiftExprContext.class,0);
		}
		public AdditiveExprContext additiveExpr() {
			return getRuleContext(AdditiveExprContext.class,0);
		}
		public ShftExprContext(ShiftExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxStarListener ) ((MxStarListener)listener).enterShftExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxStarListener ) ((MxStarListener)listener).exitShftExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxStarVisitor ) return ((MxStarVisitor<? extends T>)visitor).visitShftExpr(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ShiftExprContext shiftExpr() throws RecognitionException {
		return shiftExpr(0);
	}

	private ShiftExprContext shiftExpr(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		ShiftExprContext _localctx = new ShiftExprContext(_ctx, _parentState);
		ShiftExprContext _prevctx = _localctx;
		int _startState = 70;
		enterRecursionRule(_localctx, 70, RULE_shiftExpr, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			{
			_localctx = new AddShiftExprContext(_localctx);
			_ctx = _localctx;
			_prevctx = _localctx;

			setState(410);
			additiveExpr(0);
			}
			_ctx.stop = _input.LT(-1);
			setState(420);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,41,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(418);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,40,_ctx) ) {
					case 1:
						{
						_localctx = new ShftExprContext(new ShiftExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_shiftExpr);
						setState(412);
						if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
						{
						setState(413);
						((ShftExprContext)_localctx).op = match(T__19);
						}
						setState(414);
						additiveExpr(0);
						}
						break;
					case 2:
						{
						_localctx = new ShftExprContext(new ShiftExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_shiftExpr);
						setState(415);
						if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
						{
						setState(416);
						((ShftExprContext)_localctx).op = match(T__20);
						}
						setState(417);
						additiveExpr(0);
						}
						break;
					}
					} 
				}
				setState(422);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,41,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class RelationExprContext extends ParserRuleContext {
		public RelationExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_relationExpr; }
	 
		public RelationExprContext() { }
		public void copyFrom(RelationExprContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class ShiftRelExprContext extends RelationExprContext {
		public ShiftExprContext shiftExpr() {
			return getRuleContext(ShiftExprContext.class,0);
		}
		public ShiftRelExprContext(RelationExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxStarListener ) ((MxStarListener)listener).enterShiftRelExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxStarListener ) ((MxStarListener)listener).exitShiftRelExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxStarVisitor ) return ((MxStarVisitor<? extends T>)visitor).visitShiftRelExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class RelExprContext extends RelationExprContext {
		public Token op;
		public RelationExprContext relationExpr() {
			return getRuleContext(RelationExprContext.class,0);
		}
		public ShiftExprContext shiftExpr() {
			return getRuleContext(ShiftExprContext.class,0);
		}
		public RelExprContext(RelationExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxStarListener ) ((MxStarListener)listener).enterRelExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxStarListener ) ((MxStarListener)listener).exitRelExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxStarVisitor ) return ((MxStarVisitor<? extends T>)visitor).visitRelExpr(this);
			else return visitor.visitChildren(this);
		}
	}

	public final RelationExprContext relationExpr() throws RecognitionException {
		return relationExpr(0);
	}

	private RelationExprContext relationExpr(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		RelationExprContext _localctx = new RelationExprContext(_ctx, _parentState);
		RelationExprContext _prevctx = _localctx;
		int _startState = 72;
		enterRecursionRule(_localctx, 72, RULE_relationExpr, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			{
			_localctx = new ShiftRelExprContext(_localctx);
			_ctx = _localctx;
			_prevctx = _localctx;

			setState(424);
			shiftExpr(0);
			}
			_ctx.stop = _input.LT(-1);
			setState(440);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,43,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(438);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,42,_ctx) ) {
					case 1:
						{
						_localctx = new RelExprContext(new RelationExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_relationExpr);
						setState(426);
						if (!(precpred(_ctx, 4))) throw new FailedPredicateException(this, "precpred(_ctx, 4)");
						{
						setState(427);
						((RelExprContext)_localctx).op = match(T__21);
						}
						setState(428);
						shiftExpr(0);
						}
						break;
					case 2:
						{
						_localctx = new RelExprContext(new RelationExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_relationExpr);
						setState(429);
						if (!(precpred(_ctx, 3))) throw new FailedPredicateException(this, "precpred(_ctx, 3)");
						{
						setState(430);
						((RelExprContext)_localctx).op = match(T__22);
						}
						setState(431);
						shiftExpr(0);
						}
						break;
					case 3:
						{
						_localctx = new RelExprContext(new RelationExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_relationExpr);
						setState(432);
						if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
						{
						setState(433);
						((RelExprContext)_localctx).op = match(T__23);
						}
						setState(434);
						shiftExpr(0);
						}
						break;
					case 4:
						{
						_localctx = new RelExprContext(new RelationExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_relationExpr);
						setState(435);
						if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
						{
						setState(436);
						((RelExprContext)_localctx).op = match(T__24);
						}
						setState(437);
						shiftExpr(0);
						}
						break;
					}
					} 
				}
				setState(442);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,43,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class EqualityExprContext extends ParserRuleContext {
		public EqualityExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_equalityExpr; }
	 
		public EqualityExprContext() { }
		public void copyFrom(EqualityExprContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class RelEqualExprContext extends EqualityExprContext {
		public RelationExprContext relationExpr() {
			return getRuleContext(RelationExprContext.class,0);
		}
		public RelEqualExprContext(EqualityExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxStarListener ) ((MxStarListener)listener).enterRelEqualExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxStarListener ) ((MxStarListener)listener).exitRelEqualExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxStarVisitor ) return ((MxStarVisitor<? extends T>)visitor).visitRelEqualExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class EqualExprContext extends EqualityExprContext {
		public Token op;
		public EqualityExprContext equalityExpr() {
			return getRuleContext(EqualityExprContext.class,0);
		}
		public RelationExprContext relationExpr() {
			return getRuleContext(RelationExprContext.class,0);
		}
		public EqualExprContext(EqualityExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxStarListener ) ((MxStarListener)listener).enterEqualExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxStarListener ) ((MxStarListener)listener).exitEqualExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxStarVisitor ) return ((MxStarVisitor<? extends T>)visitor).visitEqualExpr(this);
			else return visitor.visitChildren(this);
		}
	}

	public final EqualityExprContext equalityExpr() throws RecognitionException {
		return equalityExpr(0);
	}

	private EqualityExprContext equalityExpr(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		EqualityExprContext _localctx = new EqualityExprContext(_ctx, _parentState);
		EqualityExprContext _prevctx = _localctx;
		int _startState = 74;
		enterRecursionRule(_localctx, 74, RULE_equalityExpr, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			{
			_localctx = new RelEqualExprContext(_localctx);
			_ctx = _localctx;
			_prevctx = _localctx;

			setState(444);
			relationExpr(0);
			}
			_ctx.stop = _input.LT(-1);
			setState(454);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,45,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(452);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,44,_ctx) ) {
					case 1:
						{
						_localctx = new EqualExprContext(new EqualityExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_equalityExpr);
						setState(446);
						if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
						{
						setState(447);
						((EqualExprContext)_localctx).op = match(T__25);
						}
						setState(448);
						relationExpr(0);
						}
						break;
					case 2:
						{
						_localctx = new EqualExprContext(new EqualityExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_equalityExpr);
						setState(449);
						if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
						{
						setState(450);
						((EqualExprContext)_localctx).op = match(T__26);
						}
						setState(451);
						relationExpr(0);
						}
						break;
					}
					} 
				}
				setState(456);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,45,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class AndExprContext extends ParserRuleContext {
		public AndExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_andExpr; }
	 
		public AndExprContext() { }
		public void copyFrom(AndExprContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class EquAndExprContext extends AndExprContext {
		public EqualityExprContext equalityExpr() {
			return getRuleContext(EqualityExprContext.class,0);
		}
		public EquAndExprContext(AndExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxStarListener ) ((MxStarListener)listener).enterEquAndExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxStarListener ) ((MxStarListener)listener).exitEquAndExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxStarVisitor ) return ((MxStarVisitor<? extends T>)visitor).visitEquAndExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class AExprContext extends AndExprContext {
		public Token op;
		public AndExprContext andExpr() {
			return getRuleContext(AndExprContext.class,0);
		}
		public EqualityExprContext equalityExpr() {
			return getRuleContext(EqualityExprContext.class,0);
		}
		public AExprContext(AndExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxStarListener ) ((MxStarListener)listener).enterAExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxStarListener ) ((MxStarListener)listener).exitAExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxStarVisitor ) return ((MxStarVisitor<? extends T>)visitor).visitAExpr(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AndExprContext andExpr() throws RecognitionException {
		return andExpr(0);
	}

	private AndExprContext andExpr(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		AndExprContext _localctx = new AndExprContext(_ctx, _parentState);
		AndExprContext _prevctx = _localctx;
		int _startState = 76;
		enterRecursionRule(_localctx, 76, RULE_andExpr, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			{
			_localctx = new EquAndExprContext(_localctx);
			_ctx = _localctx;
			_prevctx = _localctx;

			setState(458);
			equalityExpr(0);
			}
			_ctx.stop = _input.LT(-1);
			setState(465);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,46,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new AExprContext(new AndExprContext(_parentctx, _parentState));
					pushNewRecursionContext(_localctx, _startState, RULE_andExpr);
					setState(460);
					if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
					{
					setState(461);
					((AExprContext)_localctx).op = match(T__27);
					}
					setState(462);
					equalityExpr(0);
					}
					} 
				}
				setState(467);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,46,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class XorExprContext extends ParserRuleContext {
		public XorExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_xorExpr; }
	 
		public XorExprContext() { }
		public void copyFrom(XorExprContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class XExprContext extends XorExprContext {
		public Token op;
		public XorExprContext xorExpr() {
			return getRuleContext(XorExprContext.class,0);
		}
		public AndExprContext andExpr() {
			return getRuleContext(AndExprContext.class,0);
		}
		public XExprContext(XorExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxStarListener ) ((MxStarListener)listener).enterXExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxStarListener ) ((MxStarListener)listener).exitXExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxStarVisitor ) return ((MxStarVisitor<? extends T>)visitor).visitXExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class AndXorExprContext extends XorExprContext {
		public AndExprContext andExpr() {
			return getRuleContext(AndExprContext.class,0);
		}
		public AndXorExprContext(XorExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxStarListener ) ((MxStarListener)listener).enterAndXorExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxStarListener ) ((MxStarListener)listener).exitAndXorExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxStarVisitor ) return ((MxStarVisitor<? extends T>)visitor).visitAndXorExpr(this);
			else return visitor.visitChildren(this);
		}
	}

	public final XorExprContext xorExpr() throws RecognitionException {
		return xorExpr(0);
	}

	private XorExprContext xorExpr(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		XorExprContext _localctx = new XorExprContext(_ctx, _parentState);
		XorExprContext _prevctx = _localctx;
		int _startState = 78;
		enterRecursionRule(_localctx, 78, RULE_xorExpr, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			{
			_localctx = new AndXorExprContext(_localctx);
			_ctx = _localctx;
			_prevctx = _localctx;

			setState(469);
			andExpr(0);
			}
			_ctx.stop = _input.LT(-1);
			setState(476);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,47,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new XExprContext(new XorExprContext(_parentctx, _parentState));
					pushNewRecursionContext(_localctx, _startState, RULE_xorExpr);
					setState(471);
					if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
					{
					setState(472);
					((XExprContext)_localctx).op = match(T__28);
					}
					setState(473);
					andExpr(0);
					}
					} 
				}
				setState(478);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,47,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class OrExprContext extends ParserRuleContext {
		public OrExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_orExpr; }
	 
		public OrExprContext() { }
		public void copyFrom(OrExprContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class XorOrExprContext extends OrExprContext {
		public XorExprContext xorExpr() {
			return getRuleContext(XorExprContext.class,0);
		}
		public XorOrExprContext(OrExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxStarListener ) ((MxStarListener)listener).enterXorOrExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxStarListener ) ((MxStarListener)listener).exitXorOrExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxStarVisitor ) return ((MxStarVisitor<? extends T>)visitor).visitXorOrExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class OExprContext extends OrExprContext {
		public Token op;
		public OrExprContext orExpr() {
			return getRuleContext(OrExprContext.class,0);
		}
		public XorExprContext xorExpr() {
			return getRuleContext(XorExprContext.class,0);
		}
		public OExprContext(OrExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxStarListener ) ((MxStarListener)listener).enterOExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxStarListener ) ((MxStarListener)listener).exitOExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxStarVisitor ) return ((MxStarVisitor<? extends T>)visitor).visitOExpr(this);
			else return visitor.visitChildren(this);
		}
	}

	public final OrExprContext orExpr() throws RecognitionException {
		return orExpr(0);
	}

	private OrExprContext orExpr(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		OrExprContext _localctx = new OrExprContext(_ctx, _parentState);
		OrExprContext _prevctx = _localctx;
		int _startState = 80;
		enterRecursionRule(_localctx, 80, RULE_orExpr, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			{
			_localctx = new XorOrExprContext(_localctx);
			_ctx = _localctx;
			_prevctx = _localctx;

			setState(480);
			xorExpr(0);
			}
			_ctx.stop = _input.LT(-1);
			setState(487);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,48,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new OExprContext(new OrExprContext(_parentctx, _parentState));
					pushNewRecursionContext(_localctx, _startState, RULE_orExpr);
					setState(482);
					if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
					{
					setState(483);
					((OExprContext)_localctx).op = match(T__29);
					}
					setState(484);
					xorExpr(0);
					}
					} 
				}
				setState(489);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,48,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class LogicalAndExprContext extends ParserRuleContext {
		public LogicalAndExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_logicalAndExpr; }
	 
		public LogicalAndExprContext() { }
		public void copyFrom(LogicalAndExprContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class OrLogicalAndExprContext extends LogicalAndExprContext {
		public OrExprContext orExpr() {
			return getRuleContext(OrExprContext.class,0);
		}
		public OrLogicalAndExprContext(LogicalAndExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxStarListener ) ((MxStarListener)listener).enterOrLogicalAndExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxStarListener ) ((MxStarListener)listener).exitOrLogicalAndExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxStarVisitor ) return ((MxStarVisitor<? extends T>)visitor).visitOrLogicalAndExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class LogicalAExprContext extends LogicalAndExprContext {
		public Token op;
		public LogicalAndExprContext logicalAndExpr() {
			return getRuleContext(LogicalAndExprContext.class,0);
		}
		public OrExprContext orExpr() {
			return getRuleContext(OrExprContext.class,0);
		}
		public LogicalAExprContext(LogicalAndExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxStarListener ) ((MxStarListener)listener).enterLogicalAExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxStarListener ) ((MxStarListener)listener).exitLogicalAExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxStarVisitor ) return ((MxStarVisitor<? extends T>)visitor).visitLogicalAExpr(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LogicalAndExprContext logicalAndExpr() throws RecognitionException {
		return logicalAndExpr(0);
	}

	private LogicalAndExprContext logicalAndExpr(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		LogicalAndExprContext _localctx = new LogicalAndExprContext(_ctx, _parentState);
		LogicalAndExprContext _prevctx = _localctx;
		int _startState = 82;
		enterRecursionRule(_localctx, 82, RULE_logicalAndExpr, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			{
			_localctx = new OrLogicalAndExprContext(_localctx);
			_ctx = _localctx;
			_prevctx = _localctx;

			setState(491);
			orExpr(0);
			}
			_ctx.stop = _input.LT(-1);
			setState(498);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,49,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new LogicalAExprContext(new LogicalAndExprContext(_parentctx, _parentState));
					pushNewRecursionContext(_localctx, _startState, RULE_logicalAndExpr);
					setState(493);
					if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
					{
					setState(494);
					((LogicalAExprContext)_localctx).op = match(T__30);
					}
					setState(495);
					orExpr(0);
					}
					} 
				}
				setState(500);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,49,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class LogicalOrExprContext extends ParserRuleContext {
		public LogicalOrExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_logicalOrExpr; }
	 
		public LogicalOrExprContext() { }
		public void copyFrom(LogicalOrExprContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class LogicalOExprContext extends LogicalOrExprContext {
		public Token op;
		public LogicalOrExprContext logicalOrExpr() {
			return getRuleContext(LogicalOrExprContext.class,0);
		}
		public LogicalAndExprContext logicalAndExpr() {
			return getRuleContext(LogicalAndExprContext.class,0);
		}
		public LogicalOExprContext(LogicalOrExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxStarListener ) ((MxStarListener)listener).enterLogicalOExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxStarListener ) ((MxStarListener)listener).exitLogicalOExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxStarVisitor ) return ((MxStarVisitor<? extends T>)visitor).visitLogicalOExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class AndLogicalOrExprContext extends LogicalOrExprContext {
		public LogicalAndExprContext logicalAndExpr() {
			return getRuleContext(LogicalAndExprContext.class,0);
		}
		public AndLogicalOrExprContext(LogicalOrExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxStarListener ) ((MxStarListener)listener).enterAndLogicalOrExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxStarListener ) ((MxStarListener)listener).exitAndLogicalOrExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxStarVisitor ) return ((MxStarVisitor<? extends T>)visitor).visitAndLogicalOrExpr(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LogicalOrExprContext logicalOrExpr() throws RecognitionException {
		return logicalOrExpr(0);
	}

	private LogicalOrExprContext logicalOrExpr(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		LogicalOrExprContext _localctx = new LogicalOrExprContext(_ctx, _parentState);
		LogicalOrExprContext _prevctx = _localctx;
		int _startState = 84;
		enterRecursionRule(_localctx, 84, RULE_logicalOrExpr, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			{
			_localctx = new AndLogicalOrExprContext(_localctx);
			_ctx = _localctx;
			_prevctx = _localctx;

			setState(502);
			logicalAndExpr(0);
			}
			_ctx.stop = _input.LT(-1);
			setState(509);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,50,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new LogicalOExprContext(new LogicalOrExprContext(_parentctx, _parentState));
					pushNewRecursionContext(_localctx, _startState, RULE_logicalOrExpr);
					setState(504);
					if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
					{
					setState(505);
					((LogicalOExprContext)_localctx).op = match(T__31);
					}
					setState(506);
					logicalAndExpr(0);
					}
					} 
				}
				setState(511);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,50,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class AssignmentExprContext extends ParserRuleContext {
		public AssignmentExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_assignmentExpr; }
	 
		public AssignmentExprContext() { }
		public void copyFrom(AssignmentExprContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class OrAsgmExprContext extends AssignmentExprContext {
		public LogicalOrExprContext logicalOrExpr() {
			return getRuleContext(LogicalOrExprContext.class,0);
		}
		public OrAsgmExprContext(AssignmentExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxStarListener ) ((MxStarListener)listener).enterOrAsgmExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxStarListener ) ((MxStarListener)listener).exitOrAsgmExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxStarVisitor ) return ((MxStarVisitor<? extends T>)visitor).visitOrAsgmExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class AsgmExprContext extends AssignmentExprContext {
		public Token op;
		public LogicalOrExprContext logicalOrExpr() {
			return getRuleContext(LogicalOrExprContext.class,0);
		}
		public AssignmentExprContext assignmentExpr() {
			return getRuleContext(AssignmentExprContext.class,0);
		}
		public AsgmExprContext(AssignmentExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxStarListener ) ((MxStarListener)listener).enterAsgmExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxStarListener ) ((MxStarListener)listener).exitAsgmExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxStarVisitor ) return ((MxStarVisitor<? extends T>)visitor).visitAsgmExpr(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AssignmentExprContext assignmentExpr() throws RecognitionException {
		AssignmentExprContext _localctx = new AssignmentExprContext(_ctx, getState());
		enterRule(_localctx, 86, RULE_assignmentExpr);
		try {
			setState(517);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,51,_ctx) ) {
			case 1:
				_localctx = new OrAsgmExprContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(512);
				logicalOrExpr(0);
				}
				break;
			case 2:
				_localctx = new AsgmExprContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(513);
				logicalOrExpr(0);
				{
				setState(514);
				((AsgmExprContext)_localctx).op = match(T__6);
				}
				setState(515);
				assignmentExpr();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TypeIdContext extends ParserRuleContext {
		public ClassIdContext classId() {
			return getRuleContext(ClassIdContext.class,0);
		}
		public SimpleTypeIdContext simpleTypeId() {
			return getRuleContext(SimpleTypeIdContext.class,0);
		}
		public TypeIdContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_typeId; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxStarListener ) ((MxStarListener)listener).enterTypeId(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxStarListener ) ((MxStarListener)listener).exitTypeId(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxStarVisitor ) return ((MxStarVisitor<? extends T>)visitor).visitTypeId(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TypeIdContext typeId() throws RecognitionException {
		TypeIdContext _localctx = new TypeIdContext(_ctx, getState());
		enterRule(_localctx, 88, RULE_typeId);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(521);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case Identifier:
				{
				setState(519);
				classId();
				}
				break;
			case Bool:
			case Int:
			case String:
			case Void:
				{
				setState(520);
				simpleTypeId();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			setState(527);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__7) {
				{
				{
				setState(523);
				match(T__7);
				setState(524);
				match(T__8);
				}
				}
				setState(529);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class VariableIdContext extends ParserRuleContext {
		public TerminalNode Identifier() { return getToken(MxStarParser.Identifier, 0); }
		public VariableIdContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_variableId; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxStarListener ) ((MxStarListener)listener).enterVariableId(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxStarListener ) ((MxStarListener)listener).exitVariableId(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxStarVisitor ) return ((MxStarVisitor<? extends T>)visitor).visitVariableId(this);
			else return visitor.visitChildren(this);
		}
	}

	public final VariableIdContext variableId() throws RecognitionException {
		VariableIdContext _localctx = new VariableIdContext(_ctx, getState());
		enterRule(_localctx, 90, RULE_variableId);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(530);
			match(Identifier);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class SimpleTypeIdContext extends ParserRuleContext {
		public TerminalNode Bool() { return getToken(MxStarParser.Bool, 0); }
		public TerminalNode Int() { return getToken(MxStarParser.Int, 0); }
		public TerminalNode String() { return getToken(MxStarParser.String, 0); }
		public TerminalNode Void() { return getToken(MxStarParser.Void, 0); }
		public SimpleTypeIdContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_simpleTypeId; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxStarListener ) ((MxStarListener)listener).enterSimpleTypeId(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxStarListener ) ((MxStarListener)listener).exitSimpleTypeId(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxStarVisitor ) return ((MxStarVisitor<? extends T>)visitor).visitSimpleTypeId(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SimpleTypeIdContext simpleTypeId() throws RecognitionException {
		SimpleTypeIdContext _localctx = new SimpleTypeIdContext(_ctx, getState());
		enterRule(_localctx, 92, RULE_simpleTypeId);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(532);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << Bool) | (1L << Int) | (1L << String) | (1L << Void))) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class LogicalLiteralContext extends ParserRuleContext {
		public TerminalNode True() { return getToken(MxStarParser.True, 0); }
		public TerminalNode False() { return getToken(MxStarParser.False, 0); }
		public LogicalLiteralContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_logicalLiteral; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxStarListener ) ((MxStarListener)listener).enterLogicalLiteral(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxStarListener ) ((MxStarListener)listener).exitLogicalLiteral(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxStarVisitor ) return ((MxStarVisitor<? extends T>)visitor).visitLogicalLiteral(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LogicalLiteralContext logicalLiteral() throws RecognitionException {
		LogicalLiteralContext _localctx = new LogicalLiteralContext(_ctx, getState());
		enterRule(_localctx, 94, RULE_logicalLiteral);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(534);
			_la = _input.LA(1);
			if ( !(_la==True || _la==False) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
		switch (ruleIndex) {
		case 28:
			return primaryExpr_sempred((PrimaryExprContext)_localctx, predIndex);
		case 30:
			return rightUnaryExpr_sempred((RightUnaryExprContext)_localctx, predIndex);
		case 33:
			return multiplicativeExpr_sempred((MultiplicativeExprContext)_localctx, predIndex);
		case 34:
			return additiveExpr_sempred((AdditiveExprContext)_localctx, predIndex);
		case 35:
			return shiftExpr_sempred((ShiftExprContext)_localctx, predIndex);
		case 36:
			return relationExpr_sempred((RelationExprContext)_localctx, predIndex);
		case 37:
			return equalityExpr_sempred((EqualityExprContext)_localctx, predIndex);
		case 38:
			return andExpr_sempred((AndExprContext)_localctx, predIndex);
		case 39:
			return xorExpr_sempred((XorExprContext)_localctx, predIndex);
		case 40:
			return orExpr_sempred((OrExprContext)_localctx, predIndex);
		case 41:
			return logicalAndExpr_sempred((LogicalAndExprContext)_localctx, predIndex);
		case 42:
			return logicalOrExpr_sempred((LogicalOrExprContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean primaryExpr_sempred(PrimaryExprContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0:
			return precpred(_ctx, 2);
		case 1:
			return precpred(_ctx, 3);
		}
		return true;
	}
	private boolean rightUnaryExpr_sempred(RightUnaryExprContext _localctx, int predIndex) {
		switch (predIndex) {
		case 2:
			return precpred(_ctx, 1);
		}
		return true;
	}
	private boolean multiplicativeExpr_sempred(MultiplicativeExprContext _localctx, int predIndex) {
		switch (predIndex) {
		case 3:
			return precpred(_ctx, 3);
		case 4:
			return precpred(_ctx, 2);
		case 5:
			return precpred(_ctx, 1);
		}
		return true;
	}
	private boolean additiveExpr_sempred(AdditiveExprContext _localctx, int predIndex) {
		switch (predIndex) {
		case 6:
			return precpred(_ctx, 2);
		case 7:
			return precpred(_ctx, 1);
		}
		return true;
	}
	private boolean shiftExpr_sempred(ShiftExprContext _localctx, int predIndex) {
		switch (predIndex) {
		case 8:
			return precpred(_ctx, 2);
		case 9:
			return precpred(_ctx, 1);
		}
		return true;
	}
	private boolean relationExpr_sempred(RelationExprContext _localctx, int predIndex) {
		switch (predIndex) {
		case 10:
			return precpred(_ctx, 4);
		case 11:
			return precpred(_ctx, 3);
		case 12:
			return precpred(_ctx, 2);
		case 13:
			return precpred(_ctx, 1);
		}
		return true;
	}
	private boolean equalityExpr_sempred(EqualityExprContext _localctx, int predIndex) {
		switch (predIndex) {
		case 14:
			return precpred(_ctx, 2);
		case 15:
			return precpred(_ctx, 1);
		}
		return true;
	}
	private boolean andExpr_sempred(AndExprContext _localctx, int predIndex) {
		switch (predIndex) {
		case 16:
			return precpred(_ctx, 1);
		}
		return true;
	}
	private boolean xorExpr_sempred(XorExprContext _localctx, int predIndex) {
		switch (predIndex) {
		case 17:
			return precpred(_ctx, 1);
		}
		return true;
	}
	private boolean orExpr_sempred(OrExprContext _localctx, int predIndex) {
		switch (predIndex) {
		case 18:
			return precpred(_ctx, 1);
		}
		return true;
	}
	private boolean logicalAndExpr_sempred(LogicalAndExprContext _localctx, int predIndex) {
		switch (predIndex) {
		case 19:
			return precpred(_ctx, 1);
		}
		return true;
	}
	private boolean logicalOrExpr_sempred(LogicalOrExprContext _localctx, int predIndex) {
		switch (predIndex) {
		case 20:
			return precpred(_ctx, 1);
		}
		return true;
	}

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\39\u021b\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!"+
		"\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\4(\t(\4)\t)\4*\t*\4+\t+\4"+
		",\t,\4-\t-\4.\t.\4/\t/\4\60\t\60\4\61\t\61\3\2\3\2\3\2\7\2f\n\2\f\2\16"+
		"\2i\13\2\3\3\3\3\3\3\3\3\3\4\3\4\3\4\3\4\3\4\3\5\3\5\3\5\3\5\3\5\3\5\3"+
		"\5\5\5{\n\5\3\6\3\6\3\7\3\7\3\7\7\7\u0082\n\7\f\7\16\7\u0085\13\7\3\7"+
		"\5\7\u0088\n\7\3\7\3\7\7\7\u008c\n\7\f\7\16\7\u008f\13\7\3\7\3\7\3\b\3"+
		"\b\3\b\3\b\3\t\3\t\3\n\3\n\3\n\3\n\3\n\7\n\u009e\n\n\f\n\16\n\u00a1\13"+
		"\n\3\n\3\n\5\n\u00a5\n\n\3\13\3\13\3\13\3\f\3\f\3\f\3\r\3\r\3\16\3\16"+
		"\7\16\u00b1\n\16\f\16\16\16\u00b4\13\16\3\16\3\16\3\17\3\17\3\17\3\20"+
		"\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\7\20\u00c7\n\20"+
		"\f\20\16\20\u00ca\13\20\3\20\3\20\5\20\u00ce\n\20\3\21\3\21\5\21\u00d2"+
		"\n\21\3\22\3\22\3\22\3\22\3\22\3\22\3\23\3\23\3\23\5\23\u00dd\n\23\3\23"+
		"\3\23\5\23\u00e1\n\23\3\23\3\23\5\23\u00e5\n\23\3\23\3\23\3\23\3\24\3"+
		"\24\5\24\u00ec\n\24\3\24\3\24\3\24\3\24\3\24\5\24\u00f3\n\24\3\25\3\25"+
		"\3\26\3\26\3\26\3\27\3\27\3\27\3\30\3\30\3\30\3\30\7\30\u0101\n\30\f\30"+
		"\16\30\u0104\13\30\3\31\3\31\3\31\3\31\7\31\u010a\n\31\f\31\16\31\u010d"+
		"\13\31\3\32\3\32\3\32\3\32\3\32\5\32\u0114\n\32\3\33\3\33\3\34\3\34\3"+
		"\34\3\34\5\34\u011c\n\34\3\35\3\35\3\35\3\35\3\35\3\35\5\35\u0124\n\35"+
		"\3\36\3\36\3\36\3\36\3\36\5\36\u012b\n\36\3\36\3\36\5\36\u012f\n\36\3"+
		"\36\3\36\3\36\3\36\3\36\3\36\3\36\3\36\7\36\u0139\n\36\f\36\16\36\u013c"+
		"\13\36\3\37\3\37\3\37\7\37\u0141\n\37\f\37\16\37\u0144\13\37\3 \3 \3 "+
		"\3 \3 \7 \u014b\n \f \16 \u014e\13 \3!\3!\3!\3!\3!\3!\3!\3!\3!\3!\3!\3"+
		"!\3!\3!\3!\5!\u015f\n!\3\"\3\"\5\"\u0163\n\"\3\"\3\"\7\"\u0167\n\"\f\""+
		"\16\"\u016a\13\"\3\"\3\"\3\"\3\"\6\"\u0170\n\"\r\"\16\"\u0171\3\"\3\""+
		"\7\"\u0176\n\"\f\"\16\"\u0179\13\"\5\"\u017b\n\"\3#\3#\3#\3#\3#\3#\3#"+
		"\3#\3#\3#\3#\3#\7#\u0189\n#\f#\16#\u018c\13#\3$\3$\3$\3$\3$\3$\3$\3$\3"+
		"$\7$\u0197\n$\f$\16$\u019a\13$\3%\3%\3%\3%\3%\3%\3%\3%\3%\7%\u01a5\n%"+
		"\f%\16%\u01a8\13%\3&\3&\3&\3&\3&\3&\3&\3&\3&\3&\3&\3&\3&\3&\3&\7&\u01b9"+
		"\n&\f&\16&\u01bc\13&\3\'\3\'\3\'\3\'\3\'\3\'\3\'\3\'\3\'\7\'\u01c7\n\'"+
		"\f\'\16\'\u01ca\13\'\3(\3(\3(\3(\3(\3(\7(\u01d2\n(\f(\16(\u01d5\13(\3"+
		")\3)\3)\3)\3)\3)\7)\u01dd\n)\f)\16)\u01e0\13)\3*\3*\3*\3*\3*\3*\7*\u01e8"+
		"\n*\f*\16*\u01eb\13*\3+\3+\3+\3+\3+\3+\7+\u01f3\n+\f+\16+\u01f6\13+\3"+
		",\3,\3,\3,\3,\3,\7,\u01fe\n,\f,\16,\u0201\13,\3-\3-\3-\3-\3-\5-\u0208"+
		"\n-\3.\3.\5.\u020c\n.\3.\3.\7.\u0210\n.\f.\16.\u0213\13.\3/\3/\3\60\3"+
		"\60\3\61\3\61\3\61\2\16:>DFHJLNPRTV\62\2\4\6\b\n\f\16\20\22\24\26\30\32"+
		"\34\36 \"$&(*,.\60\62\64\668:<>@BDFHJLNPRTVXZ\\^`\2\5\3\2\r\16\3\2),\3"+
		"\2%&\2\u0233\2g\3\2\2\2\4j\3\2\2\2\6n\3\2\2\2\bz\3\2\2\2\n|\3\2\2\2\f"+
		"~\3\2\2\2\16\u0092\3\2\2\2\20\u0096\3\2\2\2\22\u0098\3\2\2\2\24\u00a6"+
		"\3\2\2\2\26\u00a9\3\2\2\2\30\u00ac\3\2\2\2\32\u00ae\3\2\2\2\34\u00b7\3"+
		"\2\2\2\36\u00ba\3\2\2\2 \u00d1\3\2\2\2\"\u00d3\3\2\2\2$\u00d9\3\2\2\2"+
		"&\u00f2\3\2\2\2(\u00f4\3\2\2\2*\u00f6\3\2\2\2,\u00f9\3\2\2\2.\u00fc\3"+
		"\2\2\2\60\u0105\3\2\2\2\62\u0113\3\2\2\2\64\u0115\3\2\2\2\66\u011b\3\2"+
		"\2\28\u0123\3\2\2\2:\u012e\3\2\2\2<\u013d\3\2\2\2>\u0145\3\2\2\2@\u015e"+
		"\3\2\2\2B\u0162\3\2\2\2D\u017c\3\2\2\2F\u018d\3\2\2\2H\u019b\3\2\2\2J"+
		"\u01a9\3\2\2\2L\u01bd\3\2\2\2N\u01cb\3\2\2\2P\u01d6\3\2\2\2R\u01e1\3\2"+
		"\2\2T\u01ec\3\2\2\2V\u01f7\3\2\2\2X\u0207\3\2\2\2Z\u020b\3\2\2\2\\\u0214"+
		"\3\2\2\2^\u0216\3\2\2\2`\u0218\3\2\2\2bf\5\4\3\2cf\5\6\4\2df\5*\26\2e"+
		"b\3\2\2\2ec\3\2\2\2ed\3\2\2\2fi\3\2\2\2ge\3\2\2\2gh\3\2\2\2h\3\3\2\2\2"+
		"ig\3\2\2\2jk\7\65\2\2kl\5\n\6\2lm\5\f\7\2m\5\3\2\2\2no\5Z.\2op\5\20\t"+
		"\2pq\5\22\n\2qr\5\30\r\2r\7\3\2\2\2s{\5\32\16\2t{\5\34\17\2u{\5\36\20"+
		"\2v{\5 \21\2w{\5&\24\2x{\5(\25\2y{\5*\26\2zs\3\2\2\2zt\3\2\2\2zu\3\2\2"+
		"\2zv\3\2\2\2zw\3\2\2\2zx\3\2\2\2zy\3\2\2\2{\t\3\2\2\2|}\7\67\2\2}\13\3"+
		"\2\2\2~\u0083\7\3\2\2\177\u0082\5,\27\2\u0080\u0082\5\6\4\2\u0081\177"+
		"\3\2\2\2\u0081\u0080\3\2\2\2\u0082\u0085\3\2\2\2\u0083\u0081\3\2\2\2\u0083"+
		"\u0084\3\2\2\2\u0084\u0087\3\2\2\2\u0085\u0083\3\2\2\2\u0086\u0088\5\16"+
		"\b\2\u0087\u0086\3\2\2\2\u0087\u0088\3\2\2\2\u0088\u008d\3\2\2\2\u0089"+
		"\u008c\5,\27\2\u008a\u008c\5\6\4\2\u008b\u0089\3\2\2\2\u008b\u008a\3\2"+
		"\2\2\u008c\u008f\3\2\2\2\u008d\u008b\3\2\2\2\u008d\u008e\3\2\2\2\u008e"+
		"\u0090\3\2\2\2\u008f\u008d\3\2\2\2\u0090\u0091\7\4\2\2\u0091\r\3\2\2\2"+
		"\u0092\u0093\5\n\6\2\u0093\u0094\5\24\13\2\u0094\u0095\5\30\r\2\u0095"+
		"\17\3\2\2\2\u0096\u0097\7\67\2\2\u0097\21\3\2\2\2\u0098\u00a4\7\5\2\2"+
		"\u0099\u00a5\7\6\2\2\u009a\u009f\5\26\f\2\u009b\u009c\7\7\2\2\u009c\u009e"+
		"\5\26\f\2\u009d\u009b\3\2\2\2\u009e\u00a1\3\2\2\2\u009f\u009d\3\2\2\2"+
		"\u009f\u00a0\3\2\2\2\u00a0\u00a2\3\2\2\2\u00a1\u009f\3\2\2\2\u00a2\u00a3"+
		"\7\6\2\2\u00a3\u00a5\3\2\2\2\u00a4\u0099\3\2\2\2\u00a4\u009a\3\2\2\2\u00a5"+
		"\23\3\2\2\2\u00a6\u00a7\7\5\2\2\u00a7\u00a8\7\6\2\2\u00a8\25\3\2\2\2\u00a9"+
		"\u00aa\5Z.\2\u00aa\u00ab\5\\/\2\u00ab\27\3\2\2\2\u00ac\u00ad\5\32\16\2"+
		"\u00ad\31\3\2\2\2\u00ae\u00b2\7\3\2\2\u00af\u00b1\5\b\5\2\u00b0\u00af"+
		"\3\2\2\2\u00b1\u00b4\3\2\2\2\u00b2\u00b0\3\2\2\2\u00b2\u00b3\3\2\2\2\u00b3"+
		"\u00b5\3\2\2\2\u00b4\u00b2\3\2\2\2\u00b5\u00b6\7\4\2\2\u00b6\33\3\2\2"+
		"\2\u00b7\u00b8\5\64\33\2\u00b8\u00b9\7\b\2\2\u00b9\35\3\2\2\2\u00ba\u00bb"+
		"\7-\2\2\u00bb\u00bc\7\5\2\2\u00bc\u00bd\5\64\33\2\u00bd\u00be\7\6\2\2"+
		"\u00be\u00c8\5\b\5\2\u00bf\u00c0\7.\2\2\u00c0\u00c1\7-\2\2\u00c1\u00c2"+
		"\7\5\2\2\u00c2\u00c3\5\64\33\2\u00c3\u00c4\7\6\2\2\u00c4\u00c5\5\b\5\2"+
		"\u00c5\u00c7\3\2\2\2\u00c6\u00bf\3\2\2\2\u00c7\u00ca\3\2\2\2\u00c8\u00c6"+
		"\3\2\2\2\u00c8\u00c9\3\2\2\2\u00c9\u00cd\3\2\2\2\u00ca\u00c8\3\2\2\2\u00cb"+
		"\u00cc\7.\2\2\u00cc\u00ce\5\b\5\2\u00cd\u00cb\3\2\2\2\u00cd\u00ce\3\2"+
		"\2\2\u00ce\37\3\2\2\2\u00cf\u00d2\5\"\22\2\u00d0\u00d2\5$\23\2\u00d1\u00cf"+
		"\3\2\2\2\u00d1\u00d0\3\2\2\2\u00d2!\3\2\2\2\u00d3\u00d4\7\60\2\2\u00d4"+
		"\u00d5\7\5\2\2\u00d5\u00d6\5\64\33\2\u00d6\u00d7\7\6\2\2\u00d7\u00d8\5"+
		"\b\5\2\u00d8#\3\2\2\2\u00d9\u00da\7/\2\2\u00da\u00dc\7\5\2\2\u00db\u00dd"+
		"\5\64\33\2\u00dc\u00db\3\2\2\2\u00dc\u00dd\3\2\2\2\u00dd\u00de\3\2\2\2"+
		"\u00de\u00e0\7\b\2\2\u00df\u00e1\5\64\33\2\u00e0\u00df\3\2\2\2\u00e0\u00e1"+
		"\3\2\2\2\u00e1\u00e2\3\2\2\2\u00e2\u00e4\7\b\2\2\u00e3\u00e5\5\64\33\2"+
		"\u00e4\u00e3\3\2\2\2\u00e4\u00e5\3\2\2\2\u00e5\u00e6\3\2\2\2\u00e6\u00e7"+
		"\7\6\2\2\u00e7\u00e8\5\b\5\2\u00e8%\3\2\2\2\u00e9\u00eb\7\63\2\2\u00ea"+
		"\u00ec\5\64\33\2\u00eb\u00ea\3\2\2\2\u00eb\u00ec\3\2\2\2\u00ec\u00ed\3"+
		"\2\2\2\u00ed\u00f3\7\b\2\2\u00ee\u00ef\7\61\2\2\u00ef\u00f3\7\b\2\2\u00f0"+
		"\u00f1\7\62\2\2\u00f1\u00f3\7\b\2\2\u00f2\u00e9\3\2\2\2\u00f2\u00ee\3"+
		"\2\2\2\u00f2\u00f0\3\2\2\2\u00f3\'\3\2\2\2\u00f4\u00f5\7\b\2\2\u00f5)"+
		"\3\2\2\2\u00f6\u00f7\5.\30\2\u00f7\u00f8\7\b\2\2\u00f8+\3\2\2\2\u00f9"+
		"\u00fa\5\60\31\2\u00fa\u00fb\7\b\2\2\u00fb-\3\2\2\2\u00fc\u00fd\5Z.\2"+
		"\u00fd\u0102\5\62\32\2\u00fe\u00ff\7\7\2\2\u00ff\u0101\5\62\32\2\u0100"+
		"\u00fe\3\2\2\2\u0101\u0104\3\2\2\2\u0102\u0100\3\2\2\2\u0102\u0103\3\2"+
		"\2\2\u0103/\3\2\2\2\u0104\u0102\3\2\2\2\u0105\u0106\5Z.\2\u0106\u010b"+
		"\5\\/\2\u0107\u0108\7\7\2\2\u0108\u010a\5\\/\2\u0109\u0107\3\2\2\2\u010a"+
		"\u010d\3\2\2\2\u010b\u0109\3\2\2\2\u010b\u010c\3\2\2\2\u010c\61\3\2\2"+
		"\2\u010d\u010b\3\2\2\2\u010e\u0114\5\\/\2\u010f\u0110\5\\/\2\u0110\u0111"+
		"\7\t\2\2\u0111\u0112\5\64\33\2\u0112\u0114\3\2\2\2\u0113\u010e\3\2\2\2"+
		"\u0113\u010f\3\2\2\2\u0114\63\3\2\2\2\u0115\u0116\5X-\2\u0116\65\3\2\2"+
		"\2\u0117\u011c\7\'\2\2\u0118\u011c\5`\61\2\u0119\u011c\7$\2\2\u011a\u011c"+
		"\7(\2\2\u011b\u0117\3\2\2\2\u011b\u0118\3\2\2\2\u011b\u0119\3\2\2\2\u011b"+
		"\u011a\3\2\2\2\u011c\67\3\2\2\2\u011d\u0124\5\66\34\2\u011e\u0124\5\\"+
		"/\2\u011f\u0120\7\5\2\2\u0120\u0121\5\64\33\2\u0121\u0122\7\6\2\2\u0122"+
		"\u0124\3\2\2\2\u0123\u011d\3\2\2\2\u0123\u011e\3\2\2\2\u0123\u011f\3\2"+
		"\2\2\u01249\3\2\2\2\u0125\u0126\b\36\1\2\u0126\u012f\58\35\2\u0127\u0128"+
		"\5\20\t\2\u0128\u012a\7\5\2\2\u0129\u012b\5<\37\2\u012a\u0129\3\2\2\2"+
		"\u012a\u012b\3\2\2\2\u012b\u012c\3\2\2\2\u012c\u012d\7\6\2\2\u012d\u012f"+
		"\3\2\2\2\u012e\u0125\3\2\2\2\u012e\u0127\3\2\2\2\u012f\u013a\3\2\2\2\u0130"+
		"\u0131\f\4\2\2\u0131\u0132\7\f\2\2\u0132\u0139\5:\36\5\u0133\u0134\f\5"+
		"\2\2\u0134\u0135\7\n\2\2\u0135\u0136\5\64\33\2\u0136\u0137\7\13\2\2\u0137"+
		"\u0139\3\2\2\2\u0138\u0130\3\2\2\2\u0138\u0133\3\2\2\2\u0139\u013c\3\2"+
		"\2\2\u013a\u0138\3\2\2\2\u013a\u013b\3\2\2\2\u013b;\3\2\2\2\u013c\u013a"+
		"\3\2\2\2\u013d\u0142\5\64\33\2\u013e\u013f\7\7\2\2\u013f\u0141\5\64\33"+
		"\2\u0140\u013e\3\2\2\2\u0141\u0144\3\2\2\2\u0142\u0140\3\2\2\2\u0142\u0143"+
		"\3\2\2\2\u0143=\3\2\2\2\u0144\u0142\3\2\2\2\u0145\u0146\b \1\2\u0146\u0147"+
		"\5:\36\2\u0147\u014c\3\2\2\2\u0148\u0149\f\3\2\2\u0149\u014b\t\2\2\2\u014a"+
		"\u0148\3\2\2\2\u014b\u014e\3\2\2\2\u014c\u014a\3\2\2\2\u014c\u014d\3\2"+
		"\2\2\u014d?\3\2\2\2\u014e\u014c\3\2\2\2\u014f\u015f\5> \2\u0150\u0151"+
		"\7\r\2\2\u0151\u015f\5@!\2\u0152\u0153\7\16\2\2\u0153\u015f\5@!\2\u0154"+
		"\u0155\7\17\2\2\u0155\u015f\5@!\2\u0156\u0157\7\20\2\2\u0157\u015f\5@"+
		"!\2\u0158\u0159\7\21\2\2\u0159\u015f\5@!\2\u015a\u015b\7\22\2\2\u015b"+
		"\u015f\5@!\2\u015c\u015d\7\64\2\2\u015d\u015f\5B\"\2\u015e\u014f\3\2\2"+
		"\2\u015e\u0150\3\2\2\2\u015e\u0152\3\2\2\2\u015e\u0154\3\2\2\2\u015e\u0156"+
		"\3\2\2\2\u015e\u0158\3\2\2\2\u015e\u015a\3\2\2\2\u015e\u015c\3\2\2\2\u015f"+
		"A\3\2\2\2\u0160\u0163\5\n\6\2\u0161\u0163\5^\60\2\u0162\u0160\3\2\2\2"+
		"\u0162\u0161\3\2\2\2\u0163\u017a\3\2\2\2\u0164\u0165\7\n\2\2\u0165\u0167"+
		"\7\13\2\2\u0166\u0164\3\2\2\2\u0167\u016a\3\2\2\2\u0168\u0166\3\2\2\2"+
		"\u0168\u0169\3\2\2\2\u0169\u017b\3\2\2\2\u016a\u0168\3\2\2\2\u016b\u016c"+
		"\7\n\2\2\u016c\u016d\5\64\33\2\u016d\u016e\7\13\2\2\u016e\u0170\3\2\2"+
		"\2\u016f\u016b\3\2\2\2\u0170\u0171\3\2\2\2\u0171\u016f\3\2\2\2\u0171\u0172"+
		"\3\2\2\2\u0172\u0177\3\2\2\2\u0173\u0174\7\n\2\2\u0174\u0176\7\13\2\2"+
		"\u0175\u0173\3\2\2\2\u0176\u0179\3\2\2\2\u0177\u0175\3\2\2\2\u0177\u0178"+
		"\3\2\2\2\u0178\u017b\3\2\2\2\u0179\u0177\3\2\2\2\u017a\u0168\3\2\2\2\u017a"+
		"\u016f\3\2\2\2\u017bC\3\2\2\2\u017c\u017d\b#\1\2\u017d\u017e\5@!\2\u017e"+
		"\u018a\3\2\2\2\u017f\u0180\f\5\2\2\u0180\u0181\7\23\2\2\u0181\u0189\5"+
		"@!\2\u0182\u0183\f\4\2\2\u0183\u0184\7\24\2\2\u0184\u0189\5@!\2\u0185"+
		"\u0186\f\3\2\2\u0186\u0187\7\25\2\2\u0187\u0189\5@!\2\u0188\u017f\3\2"+
		"\2\2\u0188\u0182\3\2\2\2\u0188\u0185\3\2\2\2\u0189\u018c\3\2\2\2\u018a"+
		"\u0188\3\2\2\2\u018a\u018b\3\2\2\2\u018bE\3\2\2\2\u018c\u018a\3\2\2\2"+
		"\u018d\u018e\b$\1\2\u018e\u018f\5D#\2\u018f\u0198\3\2\2\2\u0190\u0191"+
		"\f\4\2\2\u0191\u0192\7\22\2\2\u0192\u0197\5D#\2\u0193\u0194\f\3\2\2\u0194"+
		"\u0195\7\21\2\2\u0195\u0197\5D#\2\u0196\u0190\3\2\2\2\u0196\u0193\3\2"+
		"\2\2\u0197\u019a\3\2\2\2\u0198\u0196\3\2\2\2\u0198\u0199\3\2\2\2\u0199"+
		"G\3\2\2\2\u019a\u0198\3\2\2\2\u019b\u019c\b%\1\2\u019c\u019d\5F$\2\u019d"+
		"\u01a6\3\2\2\2\u019e\u019f\f\4\2\2\u019f\u01a0\7\26\2\2\u01a0\u01a5\5"+
		"F$\2\u01a1\u01a2\f\3\2\2\u01a2\u01a3\7\27\2\2\u01a3\u01a5\5F$\2\u01a4"+
		"\u019e\3\2\2\2\u01a4\u01a1\3\2\2\2\u01a5\u01a8\3\2\2\2\u01a6\u01a4\3\2"+
		"\2\2\u01a6\u01a7\3\2\2\2\u01a7I\3\2\2\2\u01a8\u01a6\3\2\2\2\u01a9\u01aa"+
		"\b&\1\2\u01aa\u01ab\5H%\2\u01ab\u01ba\3\2\2\2\u01ac\u01ad\f\6\2\2\u01ad"+
		"\u01ae\7\30\2\2\u01ae\u01b9\5H%\2\u01af\u01b0\f\5\2\2\u01b0\u01b1\7\31"+
		"\2\2\u01b1\u01b9\5H%\2\u01b2\u01b3\f\4\2\2\u01b3\u01b4\7\32\2\2\u01b4"+
		"\u01b9\5H%\2\u01b5\u01b6\f\3\2\2\u01b6\u01b7\7\33\2\2\u01b7\u01b9\5H%"+
		"\2\u01b8\u01ac\3\2\2\2\u01b8\u01af\3\2\2\2\u01b8\u01b2\3\2\2\2\u01b8\u01b5"+
		"\3\2\2\2\u01b9\u01bc\3\2\2\2\u01ba\u01b8\3\2\2\2\u01ba\u01bb\3\2\2\2\u01bb"+
		"K\3\2\2\2\u01bc\u01ba\3\2\2\2\u01bd\u01be\b\'\1\2\u01be\u01bf\5J&\2\u01bf"+
		"\u01c8\3\2\2\2\u01c0\u01c1\f\4\2\2\u01c1\u01c2\7\34\2\2\u01c2\u01c7\5"+
		"J&\2\u01c3\u01c4\f\3\2\2\u01c4\u01c5\7\35\2\2\u01c5\u01c7\5J&\2\u01c6"+
		"\u01c0\3\2\2\2\u01c6\u01c3\3\2\2\2\u01c7\u01ca\3\2\2\2\u01c8\u01c6\3\2"+
		"\2\2\u01c8\u01c9\3\2\2\2\u01c9M\3\2\2\2\u01ca\u01c8\3\2\2\2\u01cb\u01cc"+
		"\b(\1\2\u01cc\u01cd\5L\'\2\u01cd\u01d3\3\2\2\2\u01ce\u01cf\f\3\2\2\u01cf"+
		"\u01d0\7\36\2\2\u01d0\u01d2\5L\'\2\u01d1\u01ce\3\2\2\2\u01d2\u01d5\3\2"+
		"\2\2\u01d3\u01d1\3\2\2\2\u01d3\u01d4\3\2\2\2\u01d4O\3\2\2\2\u01d5\u01d3"+
		"\3\2\2\2\u01d6\u01d7\b)\1\2\u01d7\u01d8\5N(\2\u01d8\u01de\3\2\2\2\u01d9"+
		"\u01da\f\3\2\2\u01da\u01db\7\37\2\2\u01db\u01dd\5N(\2\u01dc\u01d9\3\2"+
		"\2\2\u01dd\u01e0\3\2\2\2\u01de\u01dc\3\2\2\2\u01de\u01df\3\2\2\2\u01df"+
		"Q\3\2\2\2\u01e0\u01de\3\2\2\2\u01e1\u01e2\b*\1\2\u01e2\u01e3\5P)\2\u01e3"+
		"\u01e9\3\2\2\2\u01e4\u01e5\f\3\2\2\u01e5\u01e6\7 \2\2\u01e6\u01e8\5P)"+
		"\2\u01e7\u01e4\3\2\2\2\u01e8\u01eb\3\2\2\2\u01e9\u01e7\3\2\2\2\u01e9\u01ea"+
		"\3\2\2\2\u01eaS\3\2\2\2\u01eb\u01e9\3\2\2\2\u01ec\u01ed\b+\1\2\u01ed\u01ee"+
		"\5R*\2\u01ee\u01f4\3\2\2\2\u01ef\u01f0\f\3\2\2\u01f0\u01f1\7!\2\2\u01f1"+
		"\u01f3\5R*\2\u01f2\u01ef\3\2\2\2\u01f3\u01f6\3\2\2\2\u01f4\u01f2\3\2\2"+
		"\2\u01f4\u01f5\3\2\2\2\u01f5U\3\2\2\2\u01f6\u01f4\3\2\2\2\u01f7\u01f8"+
		"\b,\1\2\u01f8\u01f9\5T+\2\u01f9\u01ff\3\2\2\2\u01fa\u01fb\f\3\2\2\u01fb"+
		"\u01fc\7\"\2\2\u01fc\u01fe\5T+\2\u01fd\u01fa\3\2\2\2\u01fe\u0201\3\2\2"+
		"\2\u01ff\u01fd\3\2\2\2\u01ff\u0200\3\2\2\2\u0200W\3\2\2\2\u0201\u01ff"+
		"\3\2\2\2\u0202\u0208\5V,\2\u0203\u0204\5V,\2\u0204\u0205\7\t\2\2\u0205"+
		"\u0206\5X-\2\u0206\u0208\3\2\2\2\u0207\u0202\3\2\2\2\u0207\u0203\3\2\2"+
		"\2\u0208Y\3\2\2\2\u0209\u020c\5\n\6\2\u020a\u020c\5^\60\2\u020b\u0209"+
		"\3\2\2\2\u020b\u020a\3\2\2\2\u020c\u0211\3\2\2\2\u020d\u020e\7\n\2\2\u020e"+
		"\u0210\7\13\2\2\u020f\u020d\3\2\2\2\u0210\u0213\3\2\2\2\u0211\u020f\3"+
		"\2\2\2\u0211\u0212\3\2\2\2\u0212[\3\2\2\2\u0213\u0211\3\2\2\2\u0214\u0215"+
		"\7\67\2\2\u0215]\3\2\2\2\u0216\u0217\t\3\2\2\u0217_\3\2\2\2\u0218\u0219"+
		"\t\4\2\2\u0219a\3\2\2\28egz\u0081\u0083\u0087\u008b\u008d\u009f\u00a4"+
		"\u00b2\u00c8\u00cd\u00d1\u00dc\u00e0\u00e4\u00eb\u00f2\u0102\u010b\u0113"+
		"\u011b\u0123\u012a\u012e\u0138\u013a\u0142\u014c\u015e\u0162\u0168\u0171"+
		"\u0177\u017a\u0188\u018a\u0196\u0198\u01a4\u01a6\u01b8\u01ba\u01c6\u01c8"+
		"\u01d3\u01de\u01e9\u01f4\u01ff\u0207\u020b\u0211";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}