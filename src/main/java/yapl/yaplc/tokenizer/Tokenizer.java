package yapl.yaplc.tokenizer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import yapl.yaplc.token.ErrorToken;
import yapl.yaplc.token.LineToken;
import yapl.yaplc.token.Token;

public class Tokenizer {
	public Tokenizer() {
	}

	public Collection<Token> tokenize(Reader reader) throws IOException {
		return tokenize(new BufferedReader(reader));
	}

	public Collection<Token> tokenize(BufferedReader reader) throws IOException {
		List<Token> tokens = new ArrayList<>();

		String line;
		TokenReader lineReader = new TokenReader(tokens);
		while ((line = reader.readLine()) != null) {
			lineReader.line(line);
		}

		lineReader.end();

		return Collections.unmodifiableCollection(tokens);
	}
}

