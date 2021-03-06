import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.Iterator;

import org.junit.Test;
import yapl.yaplc.token.Token;
import yapl.yaplc.tokenizer.Tokenizer;

public final class Main {
	@Test
	public void test() throws Throwable {
		Tokenizer tokenizer = new Tokenizer();
		BufferedReader reader = new BufferedReader(new InputStreamReader(Main.class.getResourceAsStream("/test.ypl")));
		Collection<Token> tokens = tokenizer.tokenize(reader);

		for (Token token : tokens) {
			System.out.println(token);
		}
	}
}

