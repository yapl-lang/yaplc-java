package yapl.yaplc.tokenizer;

import yapl.yaplc.token.Token;

import java.util.ArrayList;

public final class TokenCollection extends ArrayList<Token> {
	private final TokenReader reader;

	public TokenCollection(TokenReader reader) {
		this.reader = reader;
	}

	@Override
	public boolean add(Token token) {
		if (!token.hasPosition()) {
			token.position(reader.line(), reader.column());
		}

		return super.add(token);
	}
}
