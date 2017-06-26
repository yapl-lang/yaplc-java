package yapl.yaplc.token;

import yapl.yaplc.tokenizer.TokenReader;

public abstract class Token {
	private int startLine, startColumn;
	private int endLine, endColumn;
	private boolean hasStart = false, hasEnd = false;

	public final void position(int line, int column) {
		this.startLine = this.endLine = line;
		this.startColumn = this.endColumn = column;
		this.hasStart = this.hasEnd = true;
	}

	public final void start(int line, int column) {
		this.startLine = line;
		this.startColumn = column;
		this.hasStart = true;
	}

	public final void end(int line, int column) {
		this.endLine = line;
		this.endColumn = column;
		this.hasEnd = true;
	}

	public final boolean hasPosition() {
		return hasStart && hasEnd;
	}


	public int getStartLine() {
		return startLine;
	}

	public int getStartColumn() {
		return startColumn;
	}

	public int getEndLine() {
		return endLine;
	}

	public int getEndColumn() {
		return endColumn;
	}


	public abstract void read(TokenReader reader);
	public abstract String toString();

	public void reuse() {
		TokenFactory.reuse(this);
	}
}
