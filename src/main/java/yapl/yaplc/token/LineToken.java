package yapl.yaplc.token;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import yapl.yaplc.tokenizer.TokenReader;

public final class LineToken extends Token {
	private int number;
	private String line;

	public LineToken(int number, String line) {
		this.number = number;
		this.line = line;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public String getLine() {
		return line;
	}

	public void setLine(String line) {
		this.line = line;
	}

	@Override
	public void read(TokenReader reader) {
		throw new IllegalStateException("The method is not implemented.");
	}

	@Override
	public String toString() {
		return new StringBuilder()
			.append("@Line(number = ").append(number)
			.append(", line = '").append(line).append("')")
			.toString();
	}
}
