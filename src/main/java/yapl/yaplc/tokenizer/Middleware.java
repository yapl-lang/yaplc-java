package yapl.yaplc.tokenizer;

import java.util.Collection;
import yapl.yaplc.token.Token;

public abstract class Middleware {
	/**
	 * Process the middleware for the next line.
	 * @param reader The reader to process
	 * @param tokens Output collection for tokens
	 * @return Whether middleware will be called for thenext line
	 */
	public abstract boolean process(TokenReader reader, Collection<Token> tokens);

	/**
	 * End the middleware. Called after process returned false or middleware was attached and code is ended.
	 * @param tokens Output collection for tokens
	 */
	public abstract void end(Collection<Token> tokens);
}

