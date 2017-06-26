package yapl.yaplc.token;

import yapl.yaplc.tokenizer.Middleware;
import yapl.yaplc.tokenizer.TokenReader;

import java.util.Collection;

public final class MultiLineCommentToken extends Token {
	private String type, comment;


	public MultiLineCommentToken() {
		this.type = "";
		this.comment = "";
	}

	public MultiLineCommentToken(String type, String comment) {
		this.type = type;
		this.comment = comment;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	@Override
	public void read(TokenReader reader) {
		type = reader.skip("/*");

		reader.middleware(new Middleware() {
			private final StringBuilder commentBuilder = new StringBuilder();

			@Override
			public boolean process(TokenReader reader, Collection<Token> tokens) {
				while (!reader.readedFully()) {
					char c = reader.getChar();
					if (c == '*' && !reader.readedFully()) {
						reader.save();
						if (reader.getChar() == '/') {
							reader.skipRestore();
							return false;
						}
						reader.restore();
					}

					commentBuilder.append(c);
				}

				commentBuilder.append('\n');

				return true;
			}

			@Override
			public void end(Collection<Token> tokens) {
				comment = commentBuilder.toString();
				commentBuilder.setLength(0);
			}
		});
	}

	@Override
	public String toString() {
		return "@MultiLineComment(type = " + type + ", comment = '" + comment + "')";
	}
}
