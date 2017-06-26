package yapl.yaplc.token;

import yapl.yaplc.tokenizer.TokenReader;

public final class ParenthesesCloseToken extends Token {
	public ParenthesesCloseToken() {
	}

	@Override
	public void read(TokenReader reader) {
		reader.skip(")");
	}

	@Override
	public String toString() {
		return "@ParenthesesClose";
	}
}
