package yapl.yaplc.token;

import yapl.yaplc.tokenizer.TokenReader;

public final class BraceOpenToken extends Token {
	public BraceOpenToken() {
	}

	@Override
	public void read(TokenReader reader) {
		reader.skip("{");
	}

	@Override
	public String toString() {
		return "@BraceOpen";
	}
}
