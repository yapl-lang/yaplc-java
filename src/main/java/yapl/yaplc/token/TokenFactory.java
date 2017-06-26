package yapl.yaplc.token;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;

public final class TokenFactory {
	private TokenFactory() {}
	private static final HashMap<Class<? extends Token>, ArrayList<Token>> tokens = new HashMap<>();
	private static Collection<Class<? extends Token>> readableTokens = new ArrayList<>();

	private static <T extends Token> void registerToken(Class<T> clazz) {
		registerToken(clazz, true);
	}

	private static <T extends Token> void registerToken(Class<T> clazz, boolean readable) {
		try {
			ArrayList<Token> list = new ArrayList<>();
			list.add(clazz.newInstance());
			tokens.put(clazz, list);

			if (readable) {
				readableTokens.add(clazz);
			}
		} catch (IllegalAccessException | InstantiationException e) {
			e.printStackTrace();
		}
	}

	static {
		registerToken(WordToken.class);

		registerToken(IndentToken.class);
		registerToken(WhitespaceToken.class);

		registerToken(LineCommentToken.class);
		registerToken(MultiLineCommentToken.class);

		registerToken(OperatorToken.class);

		registerToken(StringLiteralToken.class);

		registerToken(BraceOpenToken.class);
		registerToken(BraceCloseToken.class);
		registerToken(BracketOpenToken.class);
		registerToken(BracketCloseToken.class);
		registerToken(ParenthesesOpenToken.class);
		registerToken(ParenthesesCloseToken.class);

		readableTokens = Collections.unmodifiableCollection(readableTokens);
	}

	public static <T extends Token> T get(Class<T> clazz) {
		ArrayList<Token> tokenList = tokens.get(clazz);

		if (tokenList == null) {
			return null;
		}

		synchronized (tokenList) {
			if (tokenList.isEmpty()) {
				try {
					return clazz.newInstance();
				} catch (InstantiationException | IllegalAccessException e) {
					e.printStackTrace();
					return null;
				}
			}

			return (T)tokenList.remove(0);
		}
	}

	public static <T extends Token> void reuse(T token) {
		if (token == null) {
			throw new NullPointerException("token");
		}

		ArrayList<Token> tokenList = tokens.get(token.getClass());
		if (tokenList == null) {
			return;
		}

		synchronized (tokenList) {
			tokenList.add(token);
		}
	}

	public static Collection<Class<? extends Token>> readableTokens() {
		return readableTokens;
	}
}
