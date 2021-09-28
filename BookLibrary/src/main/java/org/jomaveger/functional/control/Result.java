package org.jomaveger.functional.control;

import java.io.Serializable;
import java.util.concurrent.Callable;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import org.jomaveger.functional.tuples.Nothing;

public abstract class Result<T> implements Serializable {
	
	private static Result<?> empty = new Empty<>();

	private Result() {
	}
	
	public static <T> Result<T> of(final Callable<T> callable) {
		return of(callable, "Null value");
	}

	public static <T> Result<T> of(final Callable<T> callable, final String message) {
		try {
			T value = callable.call();
			return value == null ? Result.failure(message) : Result.success(value);
		} catch (Exception e) {
			return Result.failure(e.getMessage(), e);
		}
	}

	public static <T> Result<T> of(final Function<T, Boolean> predicate, 
									final T value, final String message) {
		try {
			return predicate.apply(value) ? Result.success(value) 
					: Result.failure(String.format(message, value));
		} catch (Exception e) {
			String errMessage = String.format("Exception while evaluating predicate: %s",
					String.format(message, value));
			return Result.failure(errMessage, e);
		}
	}

	public static <T> Result<T> of(final T value) {
		return value != null ? success(value) : Result.failure("Null value");
	}

	public static <T> Result<T> of(final T value, final String message) {
		return value != null ? Result.success(value) : Result.failure(message);
	}
	
	public static <T, U> Result<T> failure(Failure<U> failure) {
		return new Failure<>(failure.exception);
	}

	public static <T> Result<T> failure(String message) {
		return new Failure<>(message);
	}

	public static <T> Result<T> failure(String message, Exception e) {
		return new Failure<>(new IllegalStateException(message, e));
	}

	public static <V> Result<V> failure(Exception e) {
		return new Failure<>(e);
	}

	public static <T> Result<T> success(T value) {
		return new Success<>(value);
	}
	
	public static <T> Result<T> empty() {
		return (Result<T>) empty;
	}
	
	public Result<T> orElse(Supplier<Result<T>> defaultValue) {
		return map(x -> this).getOrElse(defaultValue);
	}

	public abstract Boolean isSuccess();

	public abstract Boolean isFailure();

	public abstract Boolean isEmpty();

	public abstract T getOrElse(final T defaultValue);

	public abstract T getOrElse(final Supplier<T> defaultValue);

	public abstract T successValue();

	public abstract Exception failureValue();

	public abstract <U> Result<U> map(Function<T, U> f);
	
	public abstract Result<T> mapFailure(String s);
	
	public abstract Result<T> mapFailure(String s, Exception e);
	  
	public abstract Result<T> mapFailure(Exception e);
	
	public abstract Result<Nothing> mapEmpty();

	public abstract <U> Result<U> flatMap(Function<T, Result<U>> f);
	
	public abstract Result<T> filter(Function<T, Boolean> f);
	
	public abstract Result<T> filter(Function<T, Boolean> p, String message);
	
	public abstract Boolean exists(Function<T, Boolean> f);
	
	public abstract void forEach(Consumer<T> statement);
	
	public abstract void forEachOrThrow(Consumer<T> c);
	
	public abstract Result<RuntimeException> forEachOrException(Consumer<T> e);
	
	public abstract Result<String> forEachOrFail(Consumer<T> e);
	
	public static <A, B> Function<Result<A>, Result<B>> lift(Function<A, B> f) {
		return x -> {
			try {
				return x.map(f);
			} catch (Exception e) {
				return failure(e);
			}
		};
	}
	
	public static <A, B> Function<A, Result<B>> hlift(Function<A, B> f) {
		return x -> {
			try {
				return Result.of(x).map(f);
			} catch (Exception e) {
				return failure(e);
			}
		};
	}
	
	public static <A, B, C> Function<Result<A>, Function<Result<B>, Result<C>>> lift2
				(final Function<A, Function<B, C>> f) {
		return a -> b -> a.map(f).flatMap(b::map);
	}

	public static <A, B, C, D> Function<Result<A>, Function<Result<B>, Function<Result<C>, Result<D>>>> lift3
					(final Function<A, Function<B, Function<C, D>>> f) {
		return a -> b -> c -> a.map(f).flatMap(b::map).flatMap(c::map);
	}

	private static class Empty<T> extends Result<T> {

		public Empty() {
			super();
		}

		@Override
		public Boolean isSuccess() {
			return false;
		}

		@Override
		public Boolean isFailure() {
			return false;
		}

		@Override
		public Boolean isEmpty() {
			return true;
		}

		@Override
		public T getOrElse(final T defaultValue) {
			return defaultValue;
		}
		
		@Override
		public T getOrElse(Supplier<T> defaultValue) {
			return defaultValue.get();
		}

		@Override
		public T successValue() {
			throw new IllegalStateException("Method successValue() called on a Empty instance");
		}

		@Override
		public RuntimeException failureValue() {
			throw new IllegalStateException("Method failureMessage() called on a Empty instance");
		}

		@Override
		public <U> Result<U> map(Function<T, U> f) {
			return empty();
		}
		
		@Override
	    public Result<T> mapFailure(String s) {
	      return this;
	    }
		
		@Override
	    public Result<T> mapFailure(String f, Exception e) {
	      return this;
	    }

	    @Override
	    public Result<T> mapFailure(Exception e) {
	      return this;
	    }
	    
	    @Override
	    public Result<Nothing> mapEmpty() {
	      return success(Nothing.instance);
	    }

		@Override
		public <U> Result<U> flatMap(Function<T, Result<U>> f) {
			return empty();
		}

		@Override
		public String toString() {
			return "Empty()";
		}

		@Override
		public Result<T> filter(Function<T, Boolean> f) {
			return empty();
		}

		@Override
		public Result<T> filter(Function<T, Boolean> p, String message) {
			return empty();
		}

		@Override
		public Boolean exists(Function<T, Boolean> f) {
			return false;
		}
		
		@Override
        public void forEach(Consumer<T> statement) {
        }

		@Override
		public void forEachOrThrow(Consumer<T> c) {
		}

		@Override
		public Result<RuntimeException> forEachOrException(Consumer<T> e) {
			return empty();
		}

		@Override
		public Result<String> forEachOrFail(Consumer<T> e) {
			return empty();
		}
	}

	private static class Failure<T> extends Empty<T> {

		private final RuntimeException exception;

		private Failure(String message) {
			super();
			this.exception = new IllegalStateException(message);
		}

		private Failure(RuntimeException e) {
			super();
			this.exception = e;
		}

		private Failure(Exception e) {
			super();
			this.exception = new IllegalStateException(e);
		}

		@Override
		public Boolean isSuccess() {
			return false;
		}

		@Override
		public Boolean isFailure() {
			return true;
		}

		@Override
		public T successValue() {
			throw new IllegalStateException("Method successValue() called on a Failure instance");
		}

		@Override
		public RuntimeException failureValue() {
			return this.exception;
		}

		@Override
		public <U> Result<U> map(Function<T, U> f) {
			return failure(this);
		}
		
		@Override
	    public Result<T> mapFailure(String s) {
	      return failure(s, exception);
	    }
		
		@Override
	    public Result<T> mapFailure(String s, Exception e) {
	      return failure(s, e);
	    }

	    @Override
	    public Result<T> mapFailure(Exception e) {
	      return failure(e.getMessage(), e);
	    }
	    
	    @Override
	    public Result<Nothing> mapEmpty() {
	      return failure(this);
	    }

		@Override
		public <U> Result<U> flatMap(Function<T, Result<U>> f) {
			return failure(exception.getMessage(), exception);
		}

		@Override
		public String toString() {
			return String.format("Failure(%s)", failureValue());
		}
		
		@Override
		public Result<T> filter(Function<T, Boolean> f) {
			return failure(this);
		}

		@Override
		public Result<T> filter(Function<T, Boolean> p, String message) {
			return failure(this);
		}

		@Override
		public Boolean exists(Function<T, Boolean> f) {
			return false;
		}
		
		@Override
        public void forEach(Consumer<T> statement) {
        }

		@Override
		public void forEachOrThrow(Consumer<T> c) {
			throw exception;
		}

		@Override
		public Result<RuntimeException> forEachOrException(Consumer<T> e) {
			return success(exception);
		}

		@Override
		public Result<String> forEachOrFail(Consumer<T> e) {
			return success(exception.getMessage());
		}
	}

	private static class Success<T> extends Result<T> {

		private final T value;

		public Success(T value) {
			super();
			this.value = value;
		}

		@Override
		public Boolean isSuccess() {
			return true;
		}

		@Override
		public Boolean isFailure() {
			return false;
		}

		@Override
		public Boolean isEmpty() {
			return false;
		}

		@Override
		public T getOrElse(final T defaultValue) {
			return successValue();
		}
		
		@Override
		public T getOrElse(Supplier<T> defaultValue) {
			return successValue();
		}

		@Override
		public T successValue() {
			return this.value;
		}

		@Override
		public RuntimeException failureValue() {
			throw new IllegalStateException("Method failureValue() called on a Success instance");
		}

		@Override
		public <U> Result<U> map(Function<T, U> f) {
			try {
				return success(f.apply(successValue()));
			} catch (Exception e) {
				return failure(e.getMessage(), e);
			}
		}

		@Override
	    public Result<T> mapFailure(String s) {
	      return this;
	    }
		
		@Override
	    public Result<T> mapFailure(String f, Exception e) {
	      return this;
	    }

	    @Override
	    public Result<T> mapFailure(Exception e) {
	      return this;
	    }
	    
	    @Override
	    public Result<Nothing> mapEmpty() {
	      return failure("Not empty");
	    }
	    
		@Override
		public <U> Result<U> flatMap(Function<T, Result<U>> f) {
			try {
				return f.apply(successValue());
			} catch (Exception e) {
				return failure(e.getMessage(), e);
			}
		}

		@Override
		public String toString() {
			return String.format("Success(%s)", successValue().toString());
		}

		@Override
		public Result<T> filter(Function<T, Boolean> f) {
			return filter(f, "Unmatched predicate with no error message provided.");
		}

		@Override
		public Result<T> filter(Function<T, Boolean> p, String message) {
			try {
				return p.apply(successValue()) ? this : failure(message);
			} catch (Exception e) {
				return failure(e.getMessage(), e);
			}
		}

		@Override
		public Boolean exists(Function<T, Boolean> f) {
			return f.apply(successValue());
		}
		
		@Override
        public void forEach(Consumer<T> e) {
			e.accept(value);
        }

		@Override
		public void forEachOrThrow(Consumer<T> e) {
			e.accept(value);
		}

		@Override
		public Result<RuntimeException> forEachOrException(Consumer<T> e) {
			e.accept(value);
			return empty();
		}

		@Override
		public Result<String> forEachOrFail(Consumer<T> e) {
			e.accept(this.value);
		    return empty();
		}
	}
}