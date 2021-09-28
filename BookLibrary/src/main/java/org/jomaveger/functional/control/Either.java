package org.jomaveger.functional.control;

import java.util.function.Function;

public abstract class Either<E, A> {

	public abstract <B> Either<E, B> map(Function<A, B> f);

	public abstract <B> Either<E, B> flatMap(Function<A, Either<E, B>> f);

	public abstract boolean isLeft();

	public abstract boolean isRight();

	public abstract E left();

	public abstract A right();
	
	public static <E, A> Either<E, A> left(E value) {
		return new Left<>(value);
	}

	public static <E, A> Either<E, A> right(A value) {
		return new Right<>(value);
	}

	private static class Left<E, A> extends Either<E, A> {

		private final E value;

		private Left(E value) {
			this.value = value;
		}

		public <B> Either<E, B> map(Function<A, B> f) {
			return new Left<>(value);
		}

		public <B> Either<E, B> flatMap(Function<A, Either<E, B>> f) {
			return new Left<>(value);
		}

		@Override
		public String toString() {
			return String.format("Left(%s)", value);
		}

		@Override
		public boolean isLeft() {
			return true;
		}

		@Override
		public boolean isRight() {
			return false;
		}

		@Override
		public E left() {
			return this.value;
		}

		@Override
		public A right() {
			throw new IllegalStateException("getRight called on Left");
		}
	}

	private static class Right<E, A> extends Either<E, A> {

		private final A value;

		private Right(A value) {
			this.value = value;
		}

		public <B> Either<E, B> map(Function<A, B> f) {
			return new Right<>(f.apply(value));
		}

		public <B> Either<E, B> flatMap(Function<A, Either<E, B>> f) {
			return f.apply(value);
		}

		@Override
		public String toString() {
			return String.format("Right(%s)", value);
		}

		@Override
		public boolean isLeft() {
			return false;
		}

		@Override
		public boolean isRight() {
			return true;
		}

		@Override
		public E left() {
			throw new IllegalStateException("getLeft called on Right");
		}

		@Override
		public A right() {
			return this.value;
		}
	}
}