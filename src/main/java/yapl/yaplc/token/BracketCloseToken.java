package yapl.yaplc.token;

import yapl.yaplc.tokenizer.TokenReader;

public final class BracketCloseToken extends Token {
	public BracketCloseToken() {
	}

	@Override
	public void read(TokenReader reader) {
		reader.skip("]");
	}

	@Override
	public String toString() {
		return "@BracketClose";
	}
}
