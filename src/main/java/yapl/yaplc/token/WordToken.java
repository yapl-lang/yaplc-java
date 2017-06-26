package yapl.yaplc.token;

import yapl.yaplc.tokenizer.TokenReader;

public final class WordToken extends Token {
	private String word;


	public WordToken() {
		this.word = "";
	}

	public WordToken(String word) {
		this.word = word;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	@Override
	public void read(TokenReader reader) {
		setWord(reader.getWord());
	}

	@Override
	public String toString() {
		return "@Word(word = " + word + ")";
	}
}
