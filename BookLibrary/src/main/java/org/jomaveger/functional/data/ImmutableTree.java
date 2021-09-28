package org.jomaveger.functional.data;

import java.util.function.Function;

import org.jomaveger.functional.control.Result;
import org.jomaveger.functional.control.TailCall;
import org.jomaveger.functional.tuples.Tuple2;

public abstract class ImmutableTree<A extends Comparable<A>> {
	
	private static final ImmutableTree<?> empty = new EmptyImmutableTree<>();
	
	private ImmutableTree() {
	}
	
	public static <A extends Comparable<A>> ImmutableTree<A> empty() {
		
		return (ImmutableTree<A>) empty;
	}
	
	public static <A extends Comparable<A>> ImmutableTree<A> tree(ImmutableList<A> list) {
		return list.foldLeft(empty(), (t, a) -> t.insert(a));
	}

	public static <A extends Comparable<A>> ImmutableTree<A> tree(A... as) {
		return tree(ImmutableList.of(as));
	}
	
	public static <A extends Comparable<A>> ImmutableTree<A> tree(ImmutableTree<A> t1, A a, ImmutableTree<A> t2) {
		return ordered(t1, a, t2) ? new NonEmptyImmutableTree<>(t1, a, t2)
				: ordered(t2, a, t1) ? new NonEmptyImmutableTree<>(t2, a, t1) 
						: ImmutableTree.<A>empty().insert(a).merge(t1).merge(t2);
	}
	
	public static <A extends Comparable<A>> boolean lt(A first, A second) {
		return first.compareTo(second) < 0;
	}

	public static <A extends Comparable<A>> boolean lt(A first, A second, A third) {
		return lt(first, second) && lt(second, third);
	}
	
	public static <A extends Comparable<A>> boolean ordered(ImmutableTree<A> left, A a, ImmutableTree<A> right) {
		return left.max().flatMap(lMax -> right.min().map(rMin -> lt(lMax, a, rMin)))
				.getOrElse(left.isEmpty() && right.isEmpty())
				|| left.min().mapEmpty().flatMap(ignore -> right.min().map(rMin -> lt(a, rMin))).getOrElse(false)
				|| right.min().mapEmpty().flatMap(ignore -> left.max().map(lMax -> lt(lMax, a))).getOrElse(false);
	}
	
	public static <A extends Comparable<A>> ImmutableTree<A> balance(ImmutableTree<A> tree) {
		return balance_(tree.toListInOrderRight()
				.foldLeft(ImmutableTree.<A>empty(), (t, a) -> new NonEmptyImmutableTree<>(empty(), a, t)));
	}
	
	private static <A extends Comparable<A>> ImmutableTree<A> balance_(ImmutableTree<A> tree) {
		return !tree.isEmpty() && tree.height() > log2nlz(tree.size())
				? Math.abs(tree.left().height() - tree.right().height()) > 1 ? balance_(balanceFirstLevel(tree))
						: new NonEmptyImmutableTree<>(balance_(tree.left()), tree.value(), balance_(tree.right()))
				: tree;
	}
	
	private static <A extends Comparable<A>> ImmutableTree<A> balanceFirstLevel(ImmutableTree<A> tree) {
		return unfold(tree,
				t -> isUnBalanced(t) ? tree.right().height() > tree.left().height() ? Result.success(t.rotateLeft())
						: Result.success(t.rotateRight()) : Result.empty());
	}

	private static <A extends Comparable<A>> boolean isUnBalanced(ImmutableTree<A> tree) {
		// Difference must be 0 if total size of branches is even and 1 if size is odd
		return Math.abs(tree.left().height() - tree.right().height()) > (tree.size() - 1) % 2;
	}

	public static <A> A unfold(A a, Function<A, Result<A>> f) {
		Result<A> ra = Result.success(a);
		return unfold(new Tuple2<>(ra, ra), f).eval()._2.getOrElse(a);
	}

	public static <A> TailCall<Tuple2<Result<A>, Result<A>>> 
				unfold(Tuple2<Result<A>, Result<A>> a, Function<A, Result<A>> f) {
		Result<A> x = a._2.flatMap(f::apply);
		return x.isSuccess() ? TailCall.sus(() -> unfold(new Tuple2<>(a._2, x), f)) : TailCall.ret(a);
	}
	
	public static int log2nlz(int n) {
		return n == 0 ? 0 : 31 - Integer.numberOfLeadingZeros(n);
	}
	
	public abstract A value();

	public abstract ImmutableTree<A> left();

	public abstract ImmutableTree<A> right();
	
	public abstract ImmutableTree<A> insert(A a);
	
	public abstract boolean member(A a);
	
	public abstract Result<A> get(A elt);
	
	public abstract int size();

	public abstract int height();
	
	public abstract Result<A> max();

	public abstract Result<A> min();
	
	public abstract ImmutableTree<A> remove(A a);
	
	public abstract boolean isEmpty();
	
	protected abstract ImmutableTree<A> removeMerge(ImmutableTree<A> ta);
	
	public abstract ImmutableTree<A> merge(ImmutableTree<A> a);
	
	public abstract <B> B foldInOrder(B identity, Function<B, Function<A, Function<B, B>>> f);

	public abstract <B> B foldPreOrder(B identity, Function<A, Function<B, Function<B, B>>> f);

	public abstract <B> B foldPostOrder(B identity, Function<B, Function<B, Function<A, B>>> f);
	
	public abstract <B extends Comparable<B>> ImmutableTree<B> map(Function<A, B> f);
	
	protected abstract ImmutableTree<A> rotateLeft();

	protected abstract ImmutableTree<A> rotateRight();
	
	public abstract ImmutableList<A> toListInOrderRight();
	
	public abstract ImmutableList<A> toListInOrder();
	
	protected abstract ImmutableTree<A> ins(A a);
	
	protected abstract ImmutableTree<A> rem(A a);
	
	protected abstract ImmutableTree<A> mrg(ImmutableTree<A> a);
	
	
	private static class EmptyImmutableTree<A extends Comparable<A>>
				extends ImmutableTree<A> {

		@Override
		public A value() {
			throw new IllegalStateException("value() called on empty");
		}

		@Override
		public ImmutableTree<A> left() {
			throw new IllegalStateException("left() called on empty");
		}

		@Override
		public ImmutableTree<A> right() {
			throw new IllegalStateException("right() called on empty");
		}
		
		@Override
		public String toString() {
			return "E";
		}
		
		@Override
		public boolean equals(Object o) {			
	        
			if (o == null) return false;
			if (!(o.getClass() == this.getClass()))
				return false;
			return this == o;
		}
		
		@Override
		public int hashCode() {
			
			return "E".hashCode();
		}

		@Override
		public ImmutableTree<A> insert(A value) {
			return new NonEmptyImmutableTree<>(empty(), value, empty());
		}

		@Override
		public boolean member(A a) {
			return false;
		}
		
		@Override
		public Result<A> get(A value) {
			return Result.empty();
		}

		@Override
		public int size() {		
			return 0;
		}

		@Override
		public int height() {
			return -1;
		}

		@Override
		public Result<A> max() {
			return Result.empty();
		}

		@Override
		public Result<A> min() {
			return Result.empty();
		}

		@Override
		public ImmutableTree<A> remove(A a) {
			return this;
		}

		@Override
		public boolean isEmpty() {
			return true;
		}

		@Override
		protected ImmutableTree<A> removeMerge(ImmutableTree<A> ta) {
			return ta;
		}

		@Override
		public ImmutableTree<A> merge(ImmutableTree<A> a) {
			return a;
		}

		@Override
		public <B> B foldInOrder(B identity, Function<B, Function<A, Function<B, B>>> f) {
			return identity;
		}

		@Override
		public <B> B foldPreOrder(B identity, Function<A, Function<B, Function<B, B>>> f) {
			return identity;
		}

		@Override
		public <B> B foldPostOrder(B identity, Function<B, Function<B, Function<A, B>>> f) {
			return identity;
		}

		@Override
		public <B extends Comparable<B>> ImmutableTree<B> map(Function<A, B> f) {
			return empty();
		}

		@Override
		protected ImmutableTree<A> rotateLeft() {
			return this;
		}

		@Override
		protected ImmutableTree<A> rotateRight() {
			return this;
		}

		@Override
		public ImmutableList<A> toListInOrderRight() {
			return ImmutableList.empty();
		}
		
		@Override
		public ImmutableList<A> toListInOrder() {
			return ImmutableList.empty();
		}

		@Override
		protected ImmutableTree<A> ins(A a) {
			return insert(a);
		}

		@Override
		protected ImmutableTree<A> rem(A a) {
			return this;
		}

		@Override
		protected ImmutableTree<A> mrg(ImmutableTree<A> a) {
			return a;
		}
	}
	
	
	private static class NonEmptyImmutableTree<A extends Comparable<A>>
				extends ImmutableTree<A> {
		
		private final ImmutableTree<A> left;
	    private final ImmutableTree<A> right;
	    private final A value;

		private NonEmptyImmutableTree(ImmutableTree<A> left, A value, ImmutableTree<A> right) {
			this.left = left;
			this.right = right;
			this.value = value;
		}
		
		@Override
		public A value() {
			return this.value;
		}

		@Override
		public ImmutableTree<A> left() {
			return this.left;
		}

		@Override
		public ImmutableTree<A> right() {
			return this.right;
		}
		
		@Override
		public String toString() {
			return String.format("(T %s %s %s)", left, value, right);
		}
		
		@Override
		public boolean equals(Object o) {
			
			if (this == o) {
				return true;
			}
			if (!(o instanceof NonEmptyImmutableTree)) {
				return false;
			}

			ImmutableTree<A> l = this;
			ImmutableTree<A> r = (ImmutableTree<A>) o;

			if (l.size() != r.size()) {
				return false;
			}

			ImmutableList<A> il = l.toListInOrder();
			ImmutableList<A> ir = r.toListInOrder();
			
			return il.equals(ir);
		}
		
		@Override
		public int hashCode() {
			
			return this.toListInOrder().hashCode();
		}

		@Override
		public ImmutableTree<A> insert(A value) {
			ImmutableTree<A> t = ins(value);
			return t.height() > log2nlz(t.size()) * 20 ? balance(t) : t;
		}
		
		@Override
		protected ImmutableTree<A> ins(A value) {
			return value.compareTo(this.value) < 0 ? new NonEmptyImmutableTree<>(left.ins(value), this.value, right)
				: value.compareTo(this.value) > 0 ? new NonEmptyImmutableTree<>(left, this.value, right.ins(value))
					: new NonEmptyImmutableTree<>(this.left, value, this.right);
		}

		@Override
		public boolean member(A value) {
			return value.compareTo(this.value) < 0
				? left.member(value)
				: value.compareTo(this.value) > 0 
					? right.member(value)
					: true;
		}
		
		@Override
		public Result<A> get(A value) {
			return value.compareTo(this.value) < 0 ? left.get(value)
					: value.compareTo(this.value) > 0 ? right.get(value)
					: Result.success(this.value);
		}

		@Override
		public int size() {
			return 1 + left.size() + right.size();
		}

		@Override
		public int height() {
			return 1 + Math.max(left.height(), right.height());
		}

		@Override
		public Result<A> max() {
			return right.max().orElse(() -> Result.success(value));
		}

		@Override
		public Result<A> min() {
			return left.min().orElse(() -> Result.success(value));
		}

		@Override
		public ImmutableTree<A> remove(A a) {
			ImmutableTree<A> t = rem(a);
		    return t.height() > log2nlz(t.size()) * 20 ? balance(t) : t;
		}
		
		@Override
		protected ImmutableTree<A> rem(A a) {
			if (a.compareTo(this.value) < 0) {
				return new NonEmptyImmutableTree<>(left.rem(a), value, right);
			} else if (a.compareTo(this.value) > 0) {
				return new NonEmptyImmutableTree<>(left, value, right.rem(a));
			} else {
				return left.removeMerge(right);
			}
		}

		@Override
		public boolean isEmpty() {
			return false;
		}

		@Override
		protected ImmutableTree<A> removeMerge(ImmutableTree<A> ta) {
			if (ta.isEmpty()) {
				return this;
			}
			if (ta.value().compareTo(value) < 0) {
				return new NonEmptyImmutableTree<>(left.removeMerge(ta), value, right);
			} else if (ta.value().compareTo(value) > 0) {
				return new NonEmptyImmutableTree<>(left, value, right.removeMerge(ta));
			}
			throw new IllegalStateException("Shouldn't be merging two subtrees with the same value");
		}

		@Override
		public ImmutableTree<A> merge(ImmutableTree<A> a) {
			ImmutableTree<A> t = mrg(a);
		    return t.height() > log2nlz(t.size()) * 20 ? balance(t) : t;
		}
		
		@Override
		protected ImmutableTree<A> mrg(ImmutableTree<A> a) {
			if (a.isEmpty()) {
				return this;
			}
			if (a.value().compareTo(this.value) > 0) {
				return new NonEmptyImmutableTree<>(left, value, right.mrg(
					new NonEmptyImmutableTree<>(empty(), a.value(), a.right()))).mrg(a.left());
			}
			if (a.value().compareTo(this.value) < 0) {
				return new NonEmptyImmutableTree<>(left.mrg(
					new NonEmptyImmutableTree<>(a.left(), a.value(), empty())), value, right).mrg(a.right());
			}
			return new NonEmptyImmutableTree<>(left.mrg(a.left()), value, right.mrg(a.right()));
		}

		@Override
		public <B> B foldInOrder(B identity, Function<B, Function<A, Function<B, B>>> f) {
			return f.apply(left.foldInOrder(identity, f)).apply(value).apply(right.foldInOrder(identity, f));
		}

		@Override
		public <B> B foldPreOrder(B identity, Function<A, Function<B, Function<B, B>>> f) {
			return f.apply(value).apply(left.foldPreOrder(identity, f)).apply(right.foldPreOrder(identity, f));
		}

		@Override
		public <B> B foldPostOrder(B identity, Function<B, Function<B, Function<A, B>>> f) {
			return f.apply(left.foldPostOrder(identity, f)).apply(right.foldPostOrder(identity, f)).apply(value);
		}

		@Override
		public <B extends Comparable<B>> ImmutableTree<B> map(Function<A, B> f) {
			return foldInOrder(ImmutableTree.<B>empty(), t1 -> i -> t2 -> ImmutableTree.tree(t1, f.apply(i), t2));
		}

		@Override
		protected ImmutableTree<A> rotateLeft() {
			return right.isEmpty()
					? this
					: new NonEmptyImmutableTree<>(new NonEmptyImmutableTree<>(left, value, right.left()), right.value(), right.right());
		}

		@Override
		protected ImmutableTree<A> rotateRight() {
			return left.isEmpty()
					? this
			        : new NonEmptyImmutableTree<>(left.left(), left.value(), new NonEmptyImmutableTree<>(left.right(), value, right));
		}

		@Override
		public ImmutableList<A> toListInOrderRight() {
			return unBalanceRight(ImmutableList.empty(), this).eval();
		}
		
		private <A extends Comparable<A>> TailCall<ImmutableList<A>> unBalanceRight(ImmutableList<A> acc, ImmutableTree<A> tree) {
			return tree.isEmpty() ? TailCall.ret(acc)
					: tree.left().isEmpty() 
					? TailCall.sus(() -> unBalanceRight(acc.cons(tree.value()), tree.right()))
					: TailCall.sus(() -> unBalanceRight(acc, tree.rotateRight()));
		}
		
		@Override
		public ImmutableList<A> toListInOrder() {
			return unBalanceLeft(ImmutableList.empty(), this).eval();
		}
		
		private <A extends Comparable<A>> TailCall<ImmutableList<A>> unBalanceLeft(ImmutableList<A> acc, ImmutableTree<A> tree) {
			return tree.isEmpty() ? TailCall.ret(acc)
					: tree.right().isEmpty() 
					? TailCall.sus(() -> unBalanceLeft(acc.cons(tree.value()), tree.left()))
					: TailCall.sus(() -> unBalanceLeft(acc, tree.rotateLeft()));
		}
	}
}
