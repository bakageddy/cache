package dev.bakageddy.cache.util;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.UnaryOperator;
import java.util.function.Consumer;

public class Result<T, E> {
	ResultTag tag;

	T data;
	E err;

	public static<E> Result<T, E> ok(T data) {
		this.tag = ResultTag.Ok;
		this.data = data;
		this.err = null;
	}

	public static<T> Result<T, E> err(E err) {
		this.tag = ResultTag.Err;
		this.data = null;
		this.err = err;
	}

	public bool is_ok() {
		return this.tag == ResultTag.Ok;
	}

	public bool is_err() {
		return this.tag == ResultTag.Err;
	}

	public T unwrap() {
		if (this.is_ok()) {
			return this.data;
		} else {
			throw new ResultUnwrapException("Result.data is null");
		}
	}

	public T unwrap_or(T default) {
		return this.is_ok() ? this.data : default;
	}

	public E unwrap_err() {
		if (this.is_err()) {
			return this.err;
		} else {
			throw new ResultUnwrapException("Result.err is null");
		}
	}


	public T expect(String msg) {
		if (this.is_ok()) {
			return this.data;
		} else {
			throw new ResultUnwrapException("Result.data is null: " + msg);
		}
	}

	public E expect_err(String msg) {
		if (this.is_err()) {
			return this.data;
		} else {
			throw new ResultUnwrapException("Result.err is null: " + msg);
		}
	}

	// NOTE: Result.err -> Optional<E> is not needed
	public Optional<T> as_option() {
		return this.is_ok() ? Optional.of(this.data) : Optional.empty();
	}

	public <U> Result<U, E> map(Function<T, U> f) {
		if (this.is_ok()) {
			U result = f.apply(this.data);
			return Result.ok(result);
		} else {
			return Result.err(this.err);
		}
	} 

	public <F> Result<T, F> map_err(Function<E, F> f) {
		if (this.is_err()) {
			F result = f.apply(this.err);
			return Result.err(result);
		} else {
			return Result.ok(this.data);
		}
	}

	public Result<T, E> and_then(UnaryOperator<T> f) {
		if (this.is_ok()) {
			T result = f.apply(this.data);
			return Result.ok(result);
		} else {
			return this;
		}
	}

	public Result<T, E> or_else(UnaryOperator<E> f) {
		if (this.is_err()) {
			E result = f.apply(this.err);
			return Result.err(result);
		} else {
			return this;
		}
	}

	public Result<Void, E> consume(Consumer<T> f) {
		if (this.is_ok()) {
			f.accept(this.data);
			return Result.ok(null);
		} else {
			return this;
		}
	}

	public Result<T, Void> consume_err(Consume<E> f) {
		if (this.is_err()) {
			f.accept(this.err);
			return Result.err(null);
		} else {
			return this;
		}
	}
}
