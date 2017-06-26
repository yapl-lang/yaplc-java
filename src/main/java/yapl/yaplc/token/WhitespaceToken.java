package yapl.yaplc.token;

import yapl.yaplc.tokenizer.TokenReader;

public final class WhitespaceToken extends Token {
	private String whitespace;


	public WhitespaceToken() {
		this.whitespace = "";
	}

	public WhitespaceToken(String whitespace) {
		this.whitespace = whitespace;
	}

	public String getWhitespace() {
		return whitespace;
	}

	public void setWhitespace(String whitespace) {
		this.whitespace = whitespace;
	}

	@Override
	public void read(TokenReader reader) {
		setWhitespace(reader.getWhitespace());
	}

	@Override
	public String toString() {
		return "@Whitespace(whitespace = '" + whitespace + "')";
	}
}
