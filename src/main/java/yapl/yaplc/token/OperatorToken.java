package yapl.yaplc.token;

import yapl.yaplc.tokenizer.TokenReader;

public final class OperatorToken extends Token {
	private String operator;


	public OperatorToken() {
		this.operator = "";
	}

	public OperatorToken(String operator) {
		this.operator = operator;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	@Override
	public void read(TokenReader reader) {
		setOperator(reader.getOperator());
	}

	@Override
	public String toString() {
		return "@Operator(operator = '" + operator + "')";
	}
}
