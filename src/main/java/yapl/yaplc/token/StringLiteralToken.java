package yapl.yaplc.token;

import yapl.yaplc.tokenizer.Middleware;
import yapl.yaplc.tokenizer.TokenReader;

import java.util.Collection;

public final class StringLiteralToken extends Token {
	private String type;
	private String value;

	public StringLiteralToken() {
		type = "";
		value = "";
	}

	public StringLiteralToken(String type, String value) {
		this.type = type;
		this.value = value;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public void read(TokenReader reader) {
		type = reader.skip("\"", "\'");

		switch (type) {
		case "\"":
		case "\'":
			reader.middleware(new Middleware() {
				private final StringBuilder sb = new StringBuilder();

				@Override
				public boolean process(TokenReader reader, Collection<Token> tokens) {
					boolean slash = false;
					while (!reader.readedFully()) {
						if (!slash && reader.is(type)) {
							return false;
						}

						char c = reader.getChar();
						if (slash) {
							slash = false;
							sb.append(c);
						} else {
							switch (c) {
							case '\\':
								slash = true;
								break;
							default:
								sb.append(c);
								break;
							}
						}
					}

					if (slash) {
						sb.append("\n");
						return true;
					}

					tokens.add(new ErrorToken("End of string expected."));
					return false;
				}

				@Override
				public void end(Collection<Token> tokens) {
					value = sb.toString();
					sb.setLength(0);
				}
			});
			break;
		}
	}

	@Override
	public String toString() {
		return "@StringLiteral(value = '" + value + "')";
	}
}
