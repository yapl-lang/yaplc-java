package yapl.yaplc.token;

import yapl.yaplc.tokenizer.TokenReader;

public final class IndentToken extends Token {
	private String indent;


	public IndentToken() {
		this.indent = "";
	}

	public IndentToken(String indent) {
		this.indent = indent;
	}

	public String getIndent() {
		return indent;
	}

	public void setIndent(String indent) {
		this.indent = indent;
	}

	@Override
	public void read(TokenReader reader) {
		setIndent(reader.getIndent());
	}

	@Override
	public String toString() {
		return "@Indent(indent = '" + indent + "')";
	}
}
