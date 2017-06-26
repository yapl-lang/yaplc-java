package yapl.yaplc.token;

import yapl.yaplc.tokenizer.TokenReader;

public final class BracketOpenToken extends Token {
	public BracketOpenToken() {
	}

	@Override
	public void read(TokenReader reader) {
		reader.skip("[");
	}

	@Override
	public String toString() {
		return "@BracketOpen";
	}
}
