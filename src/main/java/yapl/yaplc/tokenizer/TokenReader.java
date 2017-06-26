package yapl.yaplc.tokenizer;

import java.util.Collection;
import java.util.Iterator;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import yapl.yaplc.token.ErrorToken;
import yapl.yaplc.token.Token;
import yapl.yaplc.token.TokenFactory;
import yapl.yaplc.token.LineToken;

public final class TokenReader {
	public static final Pattern PATTERN_WORD = Pattern.compile("^[A-Za-z_$][A-Za-z_$0-9]*");
	public static final Pattern PATTERN_WHITESPACE = Pattern.compile("^[ \t]+");
	public static final Pattern PATTERN_OPERATOR = Pattern.compile("^[=+\\-*/%@\\^!&?:.]+");

	private final Collection<Token> tokens;

	private String string;
	private int number = 0;
	private int offset;

	private final Stack<Integer> saves = new Stack<>();
	private final Stack<Middleware> middlewares = new Stack<>();


	public TokenReader(Collection<Token> tokens) {
		this.tokens = tokens;

		middlewares.add(new Middleware() {
			public boolean process(TokenReader reader, Collection<Token> tokens) {
				Token token;
				while ((token = readToken()) != null) {
					tokens.add(token);
					if (readedFully()) {
						break;
					}
				}

				return true;
			}

			public void end(Collection<Token> token) {
			}
		});
	}


	public void save() {
		saves.push(offset);
	}

	public void restore() {
		offset = saves.pop();
	}

	public void skipRestore() {
		saves.pop();
	}

	public boolean readedFully() {
		return offset >= string.length();
	}


	public void middleware(Middleware middleware) {
		if (middleware.process(this, tokens)) {
			middlewares.push(middleware);
		} else {
			middleware.end(tokens);
		}
	}

	public void line(String string) {
		this.string = string;
		offset = 0;
		tokens.add(new LineToken(number, string));

		Middleware middleware = middlewares.peek();
		do {
			if (middleware.process(this, tokens)) {
				break;
			}

			middleware.end(tokens);
			middlewares.pop();
			middleware = middlewares.peek();
		} while (true);

		if (!readedFully()) {
			tokens.add(new ErrorToken("Token expected."));
		}

		++number;
	}

	public void end() {
		while (!middlewares.isEmpty()) {
			Middleware middleware = middlewares.pop();
			middleware.end(tokens);
		}
	}


	public void fail() throws TokenReadFailedException {
		throw new TokenReadFailedException();
	}

	public String get() {
		return string.substring(offset, offset = string.length());
	}

	public char getChar() {
		if (offset >= string.length()) {
			fail();
		}

		return string.charAt(offset++);
	}

	public String get(String pattern) {
		return get(Pattern.compile(pattern));
	}

	public String get(Pattern pattern) {
		Matcher matcher = pattern.matcher(string.subSequence(offset, string.length()));
		if (!matcher.find()) {
			fail();
		}

		offset += matcher.end();
		return matcher.group(0);
	}

	public String getWord() {
		return get(PATTERN_WORD);
	}

	public String getIndent() {
		if (offset != 0) {
			fail();
		}

		return getWhitespace();
	}

	public String getWhitespace() {
		return get(PATTERN_WHITESPACE);
	}

	public String getOperator() {
		return get(PATTERN_OPERATOR);
	}


	public String skip(String... strings) {
		for (String prefix : strings) {
			if (string.startsWith(prefix, offset)) {
				offset += prefix.length();
				return prefix;
			}
		}

		fail();
		return "";
	}

	public boolean is(String... strings) {
		for (String prefix : strings) {
			if (string.startsWith(prefix, offset)) {
				offset += prefix.length();
				return true;
			}
		}

		return false;
	}


	public Token readToken() {
		for (Class<? extends Token> clazz : TokenFactory.readableTokens()) {
			Token token = TokenFactory.get(clazz);

			if (read(token)) {
				return token;
			}

			token.reuse();
		}

		return null;
	}

	public boolean read(Token token) {
		save();
		try {
			token.read(this);
			skipRestore();
		} catch (TokenReadFailedException | IndexOutOfBoundsException e) {
			restore();
			return false;
		}

		return true;
	}

	private static class TokenReadFailedException extends RuntimeException {
		public TokenReadFailedException() {
			super();
		}

		@Override
		public Throwable fillInStackTrace() {
			return this;
		}
	}
}

