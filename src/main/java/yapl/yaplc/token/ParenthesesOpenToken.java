package yapl.yaplc.token;

import yapl.yaplc.tokenizer.TokenReader;

public final class ParenthesesOpenToken extends Token {
	public ParenthesesOpenToken() {
	}

	@Override
	public void read(TokenReader reader) {
		reader.skip("(");
	}

	@Override
	public String toString() {
		return "@ParenthesesOpen";
	}
}
