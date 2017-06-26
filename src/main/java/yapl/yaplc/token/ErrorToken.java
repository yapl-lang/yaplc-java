package yapl.yaplc.token;

import yapl.yaplc.tokenizer.TokenReader;

public final class ErrorToken extends Token {
	private final String message;

	public ErrorToken(String message) {
		this.message = message;
	}

	@Override
	public void read(TokenReader reader) {
		throw new IllegalStateException("The method is not implemented.");
	}

	@Override
	public String toString() {
		return "@Error(" + message + ")";
	}
}
