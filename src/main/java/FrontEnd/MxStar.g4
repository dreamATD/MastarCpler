grammar MxStar;

/*
	general structure
*/

code
:
    (
        classDefinition
        | functionDefinition
        | varDefStatement
    )*

;

classDefinition
:
	Class classId classBody
;

functionDefinition
:
	typeId functionId parameterList functionBody
;

statement
:
	compoundStatement       #compStat
	| expressionStatement   #exprStat
	| conditionalStatement  #condStat
	| iterativeStatement    #iterStat
	| jumpStatement         #jumpStat
	| nullStatement         #nullStat
	| varDefStatement       #vdefStat
;

/*
	for class
*/
classId
:
    Identifier
;

classBody
:

	'{'
	(
		(varDefStatementWithoutInit | functionDefinition)* constructionFunction? (varDefStatementWithoutInit | functionDefinition)*
	)
	'}'
;

constructionFunction
:
	classId emptyParameterList functionBody
;

/*
	for function
*/

functionId
:
    Identifier
;

parameterList
:
	'('
	(
		')'
		| parameter (',' parameter)* ')'
	)
;

emptyParameterList
:
    '(' ')'
;

parameter
:
    typeId variableId
;

functionBody
:
    compoundStatement
;

/*
	statement specifier
*/

compoundStatement
:
	'{'
	(
		statement*
	)
	'}'
;

expressionStatement
:
	expression ';'
;

conditionalStatement
:
	If '(' expression ')' statement
	(Else If '(' expression ')' statement)*
	(Else statement)?
;

iterativeStatement
:
	whileStatement #iterWhileStatement
	| forStatement #iterForStatement
;

whileStatement
:
	While '(' expression ')' statement
;

forStatement
:
	For '(' (initExpr = expression)? ';' (condExpr = expression)? ';' (loopExpr = expression)? ')' statement
;

jumpStatement
:
	Return (expression)? ';' #retJumpStatement
	| Break ';' # brkJumpStatement
	| Continue ';' #ctnJumpStatement
;

nullStatement
:
	';'
;

varDefStatement
:
    variableDefinition ';'
;

varDefStatementWithoutInit
:
    variableDefinitionWithoutInit ';'
;

variableDefinition
:
	typeId varDef (',' varDef)*
;

variableDefinitionWithoutInit
:
    typeId Identifier (',' Identifier)*
;

varDef
:
	Identifier #varWithoutInit
	| Identifier '=' expression #varWithInit
;

/*
	expression
*/

expression
:
	assignmentExpr
;

literal
:
	IntLiteral #arithmeticIntLiteral
	| logicalLiteral #arithmeticLogicalLiteral
	| StringLiteral #unarithmeticStringLiteral
	| NullLiteral #unarithmeticNullLiteral
;

elementExpr
:
	literal #literalElemExpr
	| variableId # varElemExpr
	| '(' expression ')' #sonElemExpr
;

primaryExpr
:
	elementExpr #elemPrimExpr
	| primaryExpr '.' primaryExpr #objAccPrimExpr
	| functionId '(' argumentList? ')' #funcPrimExpr
	| primaryExpr '['expression']' #arrPrimExpr
;

argumentList
:
	expression (',' expression)*
;


rightUnaryExpr
:
	primaryExpr #primRUExpr
	| rightUnaryExpr (op = ('++' | '--')) #pmRUExpr
;

unaryExpr
:
	rightUnaryExpr #rUExpr
	| (op = '++') unaryExpr #lUExpr
	| (op = '--') unaryExpr #lUExpr
	| (op = '~') unaryExpr #lUExpr
	| (op = '!') unaryExpr #lUExpr
	| (op = '-') unaryExpr #lUExpr
	| (op = '+') unaryExpr #lUExpr
	| New newCreator #newUExpr
;

newCreator
:
    simpleTypeId (('['']')+ | ('['expression']')+ ('['']')*)
    | classId (('['']')* | ('['expression']')+ ('['']')*)
    | classId '(' ')'
;

multiplicativeExpr
:
	unaryExpr #unaryMulExpr
	| multiplicativeExpr (op = '*') unaryExpr #mulExpr
	| multiplicativeExpr (op = '/') unaryExpr #mulExpr
	| multiplicativeExpr (op = '%') unaryExpr #mulExpr
;

additiveExpr
:
	multiplicativeExpr #mulAddExpr
	| additiveExpr (op = '+') multiplicativeExpr #addExpr
	| additiveExpr (op = '-') multiplicativeExpr #addExpr
;

shiftExpr
:
	additiveExpr #addShiftExpr
	| shiftExpr (op = '<<') additiveExpr #shftExpr
	| shiftExpr (op = '>>') additiveExpr #shftExpr
;

relationExpr
:
	shiftExpr #shiftRelExpr
	| relationExpr (op = '<') shiftExpr #relExpr
	| relationExpr (op = '>') shiftExpr #relExpr
	| relationExpr (op = '<=') shiftExpr #relExpr
	| relationExpr (op = '>=') shiftExpr #relExpr
;

equalityExpr
:
	relationExpr #relEqualExpr
	| equalityExpr (op = '==') relationExpr #equalExpr
	| equalityExpr (op = '!=') relationExpr #equalExpr
;

andExpr
:
	equalityExpr #equAndExpr
	| andExpr (op = '&') equalityExpr #aExpr
;

xorExpr
:
	andExpr #andXorExpr
	| xorExpr (op = '^') andExpr #xExpr
;

orExpr
:
	xorExpr #xorOrExpr
	| orExpr (op = '|') xorExpr #oExpr
;

logicalAndExpr
:
	orExpr #orLogicalAndExpr
	| logicalAndExpr (op = '&&') orExpr #logicalAExpr
;

logicalOrExpr
:
	logicalAndExpr #andLogicalOrExpr
	| logicalOrExpr (op = '||') logicalAndExpr #logicalOExpr
;

assignmentExpr
:
	logicalOrExpr # orAsgmExpr
	| logicalOrExpr (op = '=') assignmentExpr #asgmExpr
;


/*
	type specifier
*/


typeId
:
	(classId | simpleTypeId) ('['']')*
;


variableId
:
    Identifier | This
;

simpleTypeId
:
	Bool
	| Int
	| String
	| Void
;

/*
	commentary
*/

LineCommentary
:
	'//' ~[\r\n]* -> skip
;

BlockComment
:
	'/*' .*? '*/' -> skip
;

/*
	literal identifier
*/

logicalLiteral
:
    True | False
;

StringLiteral
:
	'"' (ESC | .)*? '"'
;

True
:
	'true'
;

False
:
	'false'
;

IntLiteral
:
	(DIGIT | NONZERODIGIT DIGIT+)
;

NullLiteral
:
	'null'
;

/*
	reserved keywords
*/

Bool
:
	'bool'
;

Int
:
	'int'
;

String
:
	'string'
;

Void
:
	'void'
;

If
:
	'if'
;

Else
:
    'else'
;

For
:
	'for'
;

While
:
	'while'
;

Break
:
	'break'
;

Continue
:
	'continue'
;

Return
:
	'return'
;

New
:
	'new'
;

Class
:
	'class'
;

This
:
	'this'
;


/*
	identifier
*/

Identifier
:
	ALPHABET (NONDIGIT | DIGIT)*
;

/*
	white character
*/

WhiteSpace
:
	[ \t]+ -> skip
;
/*
	start a new line
*/

NewLine
:
	(
		'\r' '\n'?
		| '\n'
	) -> skip
;

/*
	fragment definition
*/


fragment
DIGIT
:
	[0-9]
;

fragment
NONZERODIGIT
:
	[1-9]
;

fragment
NONDIGIT
:
	[a-zA-Z_]
;

fragment
ALPHABET
:
	[a-zA-Z]
;

fragment
ESC
:
    '\\' [btnr"\\]
;