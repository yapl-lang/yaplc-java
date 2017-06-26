package yapl.yaplc.token;

import yapl.yaplc.tokenizer.TokenReader;

public final class BraceCloseToken extends Token {
	public BraceCloseToken() {
	}

	@Override
	public void read(TokenReader reader) {
		reader.skip("}");
	}

	@Override
	public String toString() {
		return "@BraceClose";
	}
}
