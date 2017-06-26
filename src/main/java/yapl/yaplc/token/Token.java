package yapl.yaplc.token;

import yapl.yaplc.tokenizer.TokenReader;

public abstract class Token {
	public abstract void read(TokenReader reader);
	public abstract String toString();

	public void reuse() {
		TokenFactory.reuse(this);
	}
}
