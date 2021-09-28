package org.jomaveger.functional.control;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public abstract class Option<A> {

	private static Option<?> none = new None<>();

	private Option() {
	}

	private static <A> Option<A> some(A a) {
		return new Some<>(a);
	}

	private static <A> Option<A> none() {
		return (Option<A>) none;
	}
	
	public static <A> Option<A> instance(A value) {
		if (value == null) {
			return none();
		} else {
			return some(value);
		}
	}
	
	public static <A> Option<A> of(Optional<A> value) {
		if (value.isPresent()) {
			return some(value.get());
		} else {
			return none();
		}
	}
	
	public static <A, B> Function<Option<A>, Option<B>> lift(Function<A, B> f) {
		return x -> {
			try {
				return x.map(f);
			} catch (Exception e) {
				return Option.none();
			}
		};
	}
	
	public static <A, B> Function<A, Option<B>> hlift(Function<A, B> f) {
		return x -> {
			try {
				return Option.instance(x).map(f);
			} catch (Exception e) {
				return Option.none();
			}
		};
	}
	
	public abstract void forEach(Consumer<A> statement);
	
	public abstract Boolean isSome();
	
	public abstract A get();
	
	public abstract A getOrElse(Supplier<A> defaultValue);

	public abstract A getOrElse(A defaultValue);
	
	public Option<A> filter(Function<A, Boolean> f) {
		return flatMap(x -> f.apply(x) ? some(x) : none());
	}
	
	public abstract <B> Option<B> map(Function<A, B> f);
	
	public abstract <B> Option<B> flatMap(Function<A, Option<B>> f);
	
	public static class None<A> extends Option<A> {

		private None() {
	    }

        @Override
        public A get() {
        	throw new IllegalStateException("None has no value");
        }
        
        @Override
        public A getOrElse(Supplier<A> defaultValue) {
        	return defaultValue.get();
        }
        
        @Override
        public A getOrElse(A defaultValue) {
        	return defaultValue;
        }

        @Override
        public <B> Option<B> map(Function<A, B> f) {
            return none();
        }
        
        @Override
        public <B> Option<B> flatMap(Function<A, Option<B>> f) {
        	return none();
        }

        @Override
        public void forEach(Consumer<A> statement) {
        }
        
        @Override
        public String toString() {
          return "None";
        }
        
        @Override
        public boolean equals(Object o) {
          return this == o || o instanceof None;
        }

        @Override
        public int hashCode() {
            return 0;
        }
        
        @Override
        public Boolean isSome() {
          return false;
        }
    }
	
	public static class Some<A> extends Option<A> {
        
		private final A value;
        
        private Some(A value) {
            this.value = value;
        }
        
        public A get() {
            return this.value;
        }
        
        @Override
        public A getOrElse(Supplier<A> defaultValue) {
        	return this.value;
        }
        
        @Override
        public A getOrElse(A defaultValue) {
        	return this.value;
        }
        
        public <B> Option<B> map(Function<A, B> f) {
            return Option.instance(f.apply(this.value));
        }
        
        public <B> Option<B> flatMap(Function<A, Option<B>> f) {
            return f.apply(this.value);
        }
        
        public void forEach(Consumer<A> statement) {
            statement.accept(value);
        }
        
        @Override
        public String toString() {
          return String.format("Some(%s)", this.value);
        }
        
        @Override
        public boolean equals(Object o) {
          return (this == o || o instanceof Some) && this.value.equals(((Some<?>) o).value);
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(value);
        }
        
        @Override
        public Boolean isSome() {
        	return true;
        }
    }
}
