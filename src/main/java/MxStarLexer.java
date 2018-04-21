// Generated from /home/cloudroof/IdeaProjects/MastarCpler/src/main/java/MxStar.g4 by ANTLR 4.7
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class MxStarLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.7", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		T__9=10, T__10=11, T__11=12, T__12=13, T__13=14, T__14=15, T__15=16, T__16=17, 
		T__17=18, T__18=19, T__19=20, T__20=21, T__21=22, T__22=23, T__23=24, 
		T__24=25, T__25=26, T__26=27, T__27=28, T__28=29, T__29=30, T__30=31, 
		T__31=32, LineCommentary=33, BlockComment=34, StringLiteral=35, True=36, 
		False=37, IntLiteral=38, NullLiteral=39, Bool=40, Int=41, String=42, Void=43, 
		If=44, Else=45, For=46, While=47, Break=48, Continue=49, Return=50, New=51, 
		Class=52, This=53, Identifier=54, WhiteSpace=55, NewLine=56;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] ruleNames = {
		"T__0", "T__1", "T__2", "T__3", "T__4", "T__5", "T__6", "T__7", "T__8", 
		"T__9", "T__10", "T__11", "T__12", "T__13", "T__14", "T__15", "T__16", 
		"T__17", "T__18", "T__19", "T__20", "T__21", "T__22", "T__23", "T__24", 
		"T__25", "T__26", "T__27", "T__28", "T__29", "T__30", "T__31", "LineCommentary", 
		"BlockComment", "StringLiteral", "True", "False", "IntLiteral", "NullLiteral", 
		"Bool", "Int", "String", "Void", "If", "Else", "For", "While", "Break", 
		"Continue", "Return", "New", "Class", "This", "Identifier", "WhiteSpace", 
		"NewLine", "DIGIT", "NONZERODIGIT", "NONDIGIT", "ALPHABET", "ESC"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "'{'", "'}'", "'('", "')'", "','", "';'", "'='", "'.'", "'['", "']'", 
		"'++'", "'--'", "'~'", "'!'", "'-'", "'+'", "'*'", "'/'", "'%'", "'<<'", 
		"'>>'", "'<'", "'>'", "'<='", "'>='", "'=='", "'!='", "'&'", "'^'", "'|'", 
		"'&&'", "'||'", null, null, null, "'true'", "'false'", null, "'null'", 
		"'bool'", "'int'", "'string'", "'void'", "'if'", "'else'", "'for'", "'while'", 
		"'break'", "'continue'", "'return'", "'new'", "'class'", "'this'"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, null, null, null, "LineCommentary", 
		"BlockComment", "StringLiteral", "True", "False", "IntLiteral", "NullLiteral", 
		"Bool", "Int", "String", "Void", "If", "Else", "For", "While", "Break", 
		"Continue", "Return", "New", "Class", "This", "Identifier", "WhiteSpace", 
		"NewLine"
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


	public MxStarLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "MxStar.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getChannelNames() { return channelNames; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2:\u0172\b\1\4\2\t"+
		"\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!"+
		"\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\4(\t(\4)\t)\4*\t*\4+\t+\4"+
		",\t,\4-\t-\4.\t.\4/\t/\4\60\t\60\4\61\t\61\4\62\t\62\4\63\t\63\4\64\t"+
		"\64\4\65\t\65\4\66\t\66\4\67\t\67\48\t8\49\t9\4:\t:\4;\t;\4<\t<\4=\t="+
		"\4>\t>\3\2\3\2\3\3\3\3\3\4\3\4\3\5\3\5\3\6\3\6\3\7\3\7\3\b\3\b\3\t\3\t"+
		"\3\n\3\n\3\13\3\13\3\f\3\f\3\f\3\r\3\r\3\r\3\16\3\16\3\17\3\17\3\20\3"+
		"\20\3\21\3\21\3\22\3\22\3\23\3\23\3\24\3\24\3\25\3\25\3\25\3\26\3\26\3"+
		"\26\3\27\3\27\3\30\3\30\3\31\3\31\3\31\3\32\3\32\3\32\3\33\3\33\3\33\3"+
		"\34\3\34\3\34\3\35\3\35\3\36\3\36\3\37\3\37\3 \3 \3 \3!\3!\3!\3\"\3\""+
		"\3\"\3\"\7\"\u00cc\n\"\f\"\16\"\u00cf\13\"\3\"\3\"\3#\3#\3#\3#\7#\u00d7"+
		"\n#\f#\16#\u00da\13#\3#\3#\3#\3#\3#\3$\3$\3$\7$\u00e4\n$\f$\16$\u00e7"+
		"\13$\3$\3$\3%\3%\3%\3%\3%\3&\3&\3&\3&\3&\3&\3\'\3\'\3\'\6\'\u00f9\n\'"+
		"\r\'\16\'\u00fa\5\'\u00fd\n\'\3(\3(\3(\3(\3(\3)\3)\3)\3)\3)\3*\3*\3*\3"+
		"*\3+\3+\3+\3+\3+\3+\3+\3,\3,\3,\3,\3,\3-\3-\3-\3.\3.\3.\3.\3.\3/\3/\3"+
		"/\3/\3\60\3\60\3\60\3\60\3\60\3\60\3\61\3\61\3\61\3\61\3\61\3\61\3\62"+
		"\3\62\3\62\3\62\3\62\3\62\3\62\3\62\3\62\3\63\3\63\3\63\3\63\3\63\3\63"+
		"\3\63\3\64\3\64\3\64\3\64\3\65\3\65\3\65\3\65\3\65\3\65\3\66\3\66\3\66"+
		"\3\66\3\66\3\67\3\67\3\67\7\67\u0153\n\67\f\67\16\67\u0156\13\67\38\6"+
		"8\u0159\n8\r8\168\u015a\38\38\39\39\59\u0161\n9\39\59\u0164\n9\39\39\3"+
		":\3:\3;\3;\3<\3<\3=\3=\3>\3>\3>\4\u00d8\u00e5\2?\3\3\5\4\7\5\t\6\13\7"+
		"\r\b\17\t\21\n\23\13\25\f\27\r\31\16\33\17\35\20\37\21!\22#\23%\24\'\25"+
		")\26+\27-\30/\31\61\32\63\33\65\34\67\359\36;\37= ?!A\"C#E$G%I&K\'M(O"+
		")Q*S+U,W-Y.[/]\60_\61a\62c\63e\64g\65i\66k\67m8o9q:s\2u\2w\2y\2{\2\3\2"+
		"\t\4\2\f\f\17\17\4\2\13\13\"\"\3\2\62;\3\2\63;\5\2C\\aac|\4\2C\\c|\b\2"+
		"$$^^ddppttvv\2\u0177\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2"+
		"\13\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25\3"+
		"\2\2\2\2\27\3\2\2\2\2\31\3\2\2\2\2\33\3\2\2\2\2\35\3\2\2\2\2\37\3\2\2"+
		"\2\2!\3\2\2\2\2#\3\2\2\2\2%\3\2\2\2\2\'\3\2\2\2\2)\3\2\2\2\2+\3\2\2\2"+
		"\2-\3\2\2\2\2/\3\2\2\2\2\61\3\2\2\2\2\63\3\2\2\2\2\65\3\2\2\2\2\67\3\2"+
		"\2\2\29\3\2\2\2\2;\3\2\2\2\2=\3\2\2\2\2?\3\2\2\2\2A\3\2\2\2\2C\3\2\2\2"+
		"\2E\3\2\2\2\2G\3\2\2\2\2I\3\2\2\2\2K\3\2\2\2\2M\3\2\2\2\2O\3\2\2\2\2Q"+
		"\3\2\2\2\2S\3\2\2\2\2U\3\2\2\2\2W\3\2\2\2\2Y\3\2\2\2\2[\3\2\2\2\2]\3\2"+
		"\2\2\2_\3\2\2\2\2a\3\2\2\2\2c\3\2\2\2\2e\3\2\2\2\2g\3\2\2\2\2i\3\2\2\2"+
		"\2k\3\2\2\2\2m\3\2\2\2\2o\3\2\2\2\2q\3\2\2\2\3}\3\2\2\2\5\177\3\2\2\2"+
		"\7\u0081\3\2\2\2\t\u0083\3\2\2\2\13\u0085\3\2\2\2\r\u0087\3\2\2\2\17\u0089"+
		"\3\2\2\2\21\u008b\3\2\2\2\23\u008d\3\2\2\2\25\u008f\3\2\2\2\27\u0091\3"+
		"\2\2\2\31\u0094\3\2\2\2\33\u0097\3\2\2\2\35\u0099\3\2\2\2\37\u009b\3\2"+
		"\2\2!\u009d\3\2\2\2#\u009f\3\2\2\2%\u00a1\3\2\2\2\'\u00a3\3\2\2\2)\u00a5"+
		"\3\2\2\2+\u00a8\3\2\2\2-\u00ab\3\2\2\2/\u00ad\3\2\2\2\61\u00af\3\2\2\2"+
		"\63\u00b2\3\2\2\2\65\u00b5\3\2\2\2\67\u00b8\3\2\2\29\u00bb\3\2\2\2;\u00bd"+
		"\3\2\2\2=\u00bf\3\2\2\2?\u00c1\3\2\2\2A\u00c4\3\2\2\2C\u00c7\3\2\2\2E"+
		"\u00d2\3\2\2\2G\u00e0\3\2\2\2I\u00ea\3\2\2\2K\u00ef\3\2\2\2M\u00fc\3\2"+
		"\2\2O\u00fe\3\2\2\2Q\u0103\3\2\2\2S\u0108\3\2\2\2U\u010c\3\2\2\2W\u0113"+
		"\3\2\2\2Y\u0118\3\2\2\2[\u011b\3\2\2\2]\u0120\3\2\2\2_\u0124\3\2\2\2a"+
		"\u012a\3\2\2\2c\u0130\3\2\2\2e\u0139\3\2\2\2g\u0140\3\2\2\2i\u0144\3\2"+
		"\2\2k\u014a\3\2\2\2m\u014f\3\2\2\2o\u0158\3\2\2\2q\u0163\3\2\2\2s\u0167"+
		"\3\2\2\2u\u0169\3\2\2\2w\u016b\3\2\2\2y\u016d\3\2\2\2{\u016f\3\2\2\2}"+
		"~\7}\2\2~\4\3\2\2\2\177\u0080\7\177\2\2\u0080\6\3\2\2\2\u0081\u0082\7"+
		"*\2\2\u0082\b\3\2\2\2\u0083\u0084\7+\2\2\u0084\n\3\2\2\2\u0085\u0086\7"+
		".\2\2\u0086\f\3\2\2\2\u0087\u0088\7=\2\2\u0088\16\3\2\2\2\u0089\u008a"+
		"\7?\2\2\u008a\20\3\2\2\2\u008b\u008c\7\60\2\2\u008c\22\3\2\2\2\u008d\u008e"+
		"\7]\2\2\u008e\24\3\2\2\2\u008f\u0090\7_\2\2\u0090\26\3\2\2\2\u0091\u0092"+
		"\7-\2\2\u0092\u0093\7-\2\2\u0093\30\3\2\2\2\u0094\u0095\7/\2\2\u0095\u0096"+
		"\7/\2\2\u0096\32\3\2\2\2\u0097\u0098\7\u0080\2\2\u0098\34\3\2\2\2\u0099"+
		"\u009a\7#\2\2\u009a\36\3\2\2\2\u009b\u009c\7/\2\2\u009c \3\2\2\2\u009d"+
		"\u009e\7-\2\2\u009e\"\3\2\2\2\u009f\u00a0\7,\2\2\u00a0$\3\2\2\2\u00a1"+
		"\u00a2\7\61\2\2\u00a2&\3\2\2\2\u00a3\u00a4\7\'\2\2\u00a4(\3\2\2\2\u00a5"+
		"\u00a6\7>\2\2\u00a6\u00a7\7>\2\2\u00a7*\3\2\2\2\u00a8\u00a9\7@\2\2\u00a9"+
		"\u00aa\7@\2\2\u00aa,\3\2\2\2\u00ab\u00ac\7>\2\2\u00ac.\3\2\2\2\u00ad\u00ae"+
		"\7@\2\2\u00ae\60\3\2\2\2\u00af\u00b0\7>\2\2\u00b0\u00b1\7?\2\2\u00b1\62"+
		"\3\2\2\2\u00b2\u00b3\7@\2\2\u00b3\u00b4\7?\2\2\u00b4\64\3\2\2\2\u00b5"+
		"\u00b6\7?\2\2\u00b6\u00b7\7?\2\2\u00b7\66\3\2\2\2\u00b8\u00b9\7#\2\2\u00b9"+
		"\u00ba\7?\2\2\u00ba8\3\2\2\2\u00bb\u00bc\7(\2\2\u00bc:\3\2\2\2\u00bd\u00be"+
		"\7`\2\2\u00be<\3\2\2\2\u00bf\u00c0\7~\2\2\u00c0>\3\2\2\2\u00c1\u00c2\7"+
		"(\2\2\u00c2\u00c3\7(\2\2\u00c3@\3\2\2\2\u00c4\u00c5\7~\2\2\u00c5\u00c6"+
		"\7~\2\2\u00c6B\3\2\2\2\u00c7\u00c8\7\61\2\2\u00c8\u00c9\7\61\2\2\u00c9"+
		"\u00cd\3\2\2\2\u00ca\u00cc\n\2\2\2\u00cb\u00ca\3\2\2\2\u00cc\u00cf\3\2"+
		"\2\2\u00cd\u00cb\3\2\2\2\u00cd\u00ce\3\2\2\2\u00ce\u00d0\3\2\2\2\u00cf"+
		"\u00cd\3\2\2\2\u00d0\u00d1\b\"\2\2\u00d1D\3\2\2\2\u00d2\u00d3\7\61\2\2"+
		"\u00d3\u00d4\7,\2\2\u00d4\u00d8\3\2\2\2\u00d5\u00d7\13\2\2\2\u00d6\u00d5"+
		"\3\2\2\2\u00d7\u00da\3\2\2\2\u00d8\u00d9\3\2\2\2\u00d8\u00d6\3\2\2\2\u00d9"+
		"\u00db\3\2\2\2\u00da\u00d8\3\2\2\2\u00db\u00dc\7,\2\2\u00dc\u00dd\7\61"+
		"\2\2\u00dd\u00de\3\2\2\2\u00de\u00df\b#\2\2\u00dfF\3\2\2\2\u00e0\u00e5"+
		"\7$\2\2\u00e1\u00e4\5{>\2\u00e2\u00e4\13\2\2\2\u00e3\u00e1\3\2\2\2\u00e3"+
		"\u00e2\3\2\2\2\u00e4\u00e7\3\2\2\2\u00e5\u00e6\3\2\2\2\u00e5\u00e3\3\2"+
		"\2\2\u00e6\u00e8\3\2\2\2\u00e7\u00e5\3\2\2\2\u00e8\u00e9\7$\2\2\u00e9"+
		"H\3\2\2\2\u00ea\u00eb\7v\2\2\u00eb\u00ec\7t\2\2\u00ec\u00ed\7w\2\2\u00ed"+
		"\u00ee\7g\2\2\u00eeJ\3\2\2\2\u00ef\u00f0\7h\2\2\u00f0\u00f1\7c\2\2\u00f1"+
		"\u00f2\7n\2\2\u00f2\u00f3\7u\2\2\u00f3\u00f4\7g\2\2\u00f4L\3\2\2\2\u00f5"+
		"\u00fd\5s:\2\u00f6\u00f8\5u;\2\u00f7\u00f9\5s:\2\u00f8\u00f7\3\2\2\2\u00f9"+
		"\u00fa\3\2\2\2\u00fa\u00f8\3\2\2\2\u00fa\u00fb\3\2\2\2\u00fb\u00fd\3\2"+
		"\2\2\u00fc\u00f5\3\2\2\2\u00fc\u00f6\3\2\2\2\u00fdN\3\2\2\2\u00fe\u00ff"+
		"\7p\2\2\u00ff\u0100\7w\2\2\u0100\u0101\7n\2\2\u0101\u0102\7n\2\2\u0102"+
		"P\3\2\2\2\u0103\u0104\7d\2\2\u0104\u0105\7q\2\2\u0105\u0106\7q\2\2\u0106"+
		"\u0107\7n\2\2\u0107R\3\2\2\2\u0108\u0109\7k\2\2\u0109\u010a\7p\2\2\u010a"+
		"\u010b\7v\2\2\u010bT\3\2\2\2\u010c\u010d\7u\2\2\u010d\u010e\7v\2\2\u010e"+
		"\u010f\7t\2\2\u010f\u0110\7k\2\2\u0110\u0111\7p\2\2\u0111\u0112\7i\2\2"+
		"\u0112V\3\2\2\2\u0113\u0114\7x\2\2\u0114\u0115\7q\2\2\u0115\u0116\7k\2"+
		"\2\u0116\u0117\7f\2\2\u0117X\3\2\2\2\u0118\u0119\7k\2\2\u0119\u011a\7"+
		"h\2\2\u011aZ\3\2\2\2\u011b\u011c\7g\2\2\u011c\u011d\7n\2\2\u011d\u011e"+
		"\7u\2\2\u011e\u011f\7g\2\2\u011f\\\3\2\2\2\u0120\u0121\7h\2\2\u0121\u0122"+
		"\7q\2\2\u0122\u0123\7t\2\2\u0123^\3\2\2\2\u0124\u0125\7y\2\2\u0125\u0126"+
		"\7j\2\2\u0126\u0127\7k\2\2\u0127\u0128\7n\2\2\u0128\u0129\7g\2\2\u0129"+
		"`\3\2\2\2\u012a\u012b\7d\2\2\u012b\u012c\7t\2\2\u012c\u012d\7g\2\2\u012d"+
		"\u012e\7c\2\2\u012e\u012f\7m\2\2\u012fb\3\2\2\2\u0130\u0131\7e\2\2\u0131"+
		"\u0132\7q\2\2\u0132\u0133\7p\2\2\u0133\u0134\7v\2\2\u0134\u0135\7k\2\2"+
		"\u0135\u0136\7p\2\2\u0136\u0137\7w\2\2\u0137\u0138\7g\2\2\u0138d\3\2\2"+
		"\2\u0139\u013a\7t\2\2\u013a\u013b\7g\2\2\u013b\u013c\7v\2\2\u013c\u013d"+
		"\7w\2\2\u013d\u013e\7t\2\2\u013e\u013f\7p\2\2\u013ff\3\2\2\2\u0140\u0141"+
		"\7p\2\2\u0141\u0142\7g\2\2\u0142\u0143\7y\2\2\u0143h\3\2\2\2\u0144\u0145"+
		"\7e\2\2\u0145\u0146\7n\2\2\u0146\u0147\7c\2\2\u0147\u0148\7u\2\2\u0148"+
		"\u0149\7u\2\2\u0149j\3\2\2\2\u014a\u014b\7v\2\2\u014b\u014c\7j\2\2\u014c"+
		"\u014d\7k\2\2\u014d\u014e\7u\2\2\u014el\3\2\2\2\u014f\u0154\5w<\2\u0150"+
		"\u0153\5w<\2\u0151\u0153\5s:\2\u0152\u0150\3\2\2\2\u0152\u0151\3\2\2\2"+
		"\u0153\u0156\3\2\2\2\u0154\u0152\3\2\2\2\u0154\u0155\3\2\2\2\u0155n\3"+
		"\2\2\2\u0156\u0154\3\2\2\2\u0157\u0159\t\3\2\2\u0158\u0157\3\2\2\2\u0159"+
		"\u015a\3\2\2\2\u015a\u0158\3\2\2\2\u015a\u015b\3\2\2\2\u015b\u015c\3\2"+
		"\2\2\u015c\u015d\b8\2\2\u015dp\3\2\2\2\u015e\u0160\7\17\2\2\u015f\u0161"+
		"\7\f\2\2\u0160\u015f\3\2\2\2\u0160\u0161\3\2\2\2\u0161\u0164\3\2\2\2\u0162"+
		"\u0164\7\f\2\2\u0163\u015e\3\2\2\2\u0163\u0162\3\2\2\2\u0164\u0165\3\2"+
		"\2\2\u0165\u0166\b9\2\2\u0166r\3\2\2\2\u0167\u0168\t\4\2\2\u0168t\3\2"+
		"\2\2\u0169\u016a\t\5\2\2\u016av\3\2\2\2\u016b\u016c\t\6\2\2\u016cx\3\2"+
		"\2\2\u016d\u016e\t\7\2\2\u016ez\3\2\2\2\u016f\u0170\7^\2\2\u0170\u0171"+
		"\t\b\2\2\u0171|\3\2\2\2\16\2\u00cd\u00d8\u00e3\u00e5\u00fa\u00fc\u0152"+
		"\u0154\u015a\u0160\u0163\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}