package yapl.yaplc.token;

import yapl.yaplc.tokenizer.TokenReader;
import yapl.yaplc.tokenizer.Middleware;
import java.util.Collection;

public final class LineCommentToken extends Token {
	private String type, comment;


	public LineCommentToken() {
		this.type = "";
		this.comment = "";
	}

	public LineCommentToken(String type, String comment) {
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
		type = reader.skip("//", "#");

		reader.middleware(new Middleware() {
			private final StringBuilder commentBuilder = new StringBuilder();

			@Override
			public boolean process(TokenReader reader, Collection<Token> tokens) {
				commentBuilder.append(reader.get());

				int i = commentBuilder.length(), count = 0;
				while (i > 0 && commentBuilder.charAt(--i) == '\\') {
					++count;
				}

				if (count % 2 == 0) {
					commentBuilder.setLength(commentBuilder.length() - count / 2);
					return false;
				}

				commentBuilder.setLength(commentBuilder.length() - (count + 1) / 2);
				commentBuilder.append("\n");
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
		return "@LineComment(type = " + type + ", comment = '" + comment + "')";
	}
}
