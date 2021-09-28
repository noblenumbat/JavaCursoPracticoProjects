package org.jomaveger.structures;

import java.util.Arrays;

import org.jomaveger.lang.dbc.Contract;

public class GraphAlgorithms<T> extends Graph<T> {
	
	public GraphAlgorithms() {
		super();
	}
	
	public GraphAlgorithms(boolean isDirected, boolean isWeighted) {
		super(isDirected, isWeighted);
	}
	
	public IList<Vertex<T>> DFS(Vertex<T> v, int k, ITable<Vertex<T>, Integer> path, IList<Vertex<T>> dfsList) {
		k++;
		path.set(v, k);
		dfsList.addLast(v);
		IList<Vertex<T>> adj = getAdjVertex(v);
		while (!adj.isEmpty()) {
			Vertex<T> w = adj.getFirst();
			adj.removeFirst();
			if (path.get(w) == 0) {
				dfsList = DFS(w, k, path, dfsList);
			}
		}
		return dfsList;
	}
	
	public IList<Vertex<T>> allDFS() {
		int k = 0;
		IList<Vertex<T>> dfsList = new LinkedList<>();
		ITable<Vertex<T>, Integer> path = new LinkedTable<>();
		for (Vertex<T> vertex : vertex) {
			path.set(vertex, 0);
		}
		for (Vertex<T> vertex : vertex) {
			if (path.get(vertex) == 0) {
				dfsList = DFS(vertex, k, path, dfsList);
			}
		}
		return dfsList;
	}
	
	public IList<Vertex<T>> itDFS(Vertex<T> v, ITable<Vertex<T>, Integer> path, IList<Vertex<T>> dfsList) {
		IStack<VertexInfo<T>> stack = new LinkedStack<>();
		VertexInfo<T> p = new VertexInfo<>();
		VertexInfo<T> q, r;
		int k = 1;
		path.set(v, k);
		dfsList.addLast(v);
		p.vertex = v;
		p.adj = getAdjVertex(v);
		stack.push(p);
		
		while (!stack.isEmpty()) {
			p = stack.peek();
			stack.pop();
			if (!p.adj.isEmpty()) {
				Vertex<T> w = p.adj.getFirst();
				p.adj.removeFirst();
				r = new VertexInfo<>();
				r.vertex = p.vertex;
				r.adj = p.adj;
				stack.push(r);
				if (path.get(w) == 0) {
					k++;
					path.set(w, k);
					dfsList.addLast(w);
					q = new VertexInfo<>();
					q.vertex = w;
					q.adj = getAdjVertex(w);
					stack.push(q);
				}
			}
		}
		
		return dfsList;
	}
	
	public IList<Vertex<T>> allItDFS() {
		IList<Vertex<T>> dfsList = new LinkedList<>();
		ITable<Vertex<T>, Integer> path = new LinkedTable<>();
		for (Vertex<T> vertex : vertex) {
			path.set(vertex, 0);
		}
		for (Vertex<T> vertex : vertex) {
			if (path.get(vertex) == 0) {
				dfsList = itDFS(vertex, path, dfsList);
			}
		}
		return dfsList;
	}
	
	public IList<Vertex<T>> BFS(Vertex<T> v, ITable<Vertex<T>, Integer> path, IList<Vertex<T>> bfsList) {
		IQueue<Vertex<T>> q = new LinkedQueue<>();
		Vertex<T> u, w;
		IList<Vertex<T>> l;
		int k = 1;
		path.set(v, k);
		bfsList.addLast(v);
		q.enqueue(v);
		
		while (!q.isEmpty()) {
			u = q.front();
			q.dequeue();
			l = getAdjVertex(u);
			while (!l.isEmpty()) {
				w = l.getFirst();
				l.removeFirst();
				if (path.get(w) == 0) {
					k++;
					path.set(w, k);
					bfsList.addLast(w);
					q.enqueue(w);
				}
			}
			
		}
		
		return bfsList;
	}
	
	public IList<Vertex<T>> allBFS() {
		IList<Vertex<T>> bfsList = new LinkedList<>();
		ITable<Vertex<T>, Integer> path = new LinkedTable<>();
		for (Vertex<T> vertex : vertex) {
			path.set(vertex, 0);
		}
		for (Vertex<T> vertex : vertex) {
			if (path.get(vertex) == 0) {
				bfsList = BFS(vertex, path, bfsList);
			}
		}
		return bfsList;
	}
	
	public boolean isConnected(Vertex<T> v) {
		int k = 0;
		IList<Vertex<T>> dfsList = new LinkedList<>();
		ITable<Vertex<T>, Integer> path = new LinkedTable<>();
		for (Vertex<T> vertex : vertex) {
			path.set(vertex, 0);
		}
		dfsList = DFS(v, k, path, dfsList);
		return (dfsList.size() == vertex.size());
	}
	
	public IList<Vertex<T>> getAdjVertex(Vertex<T> v) {
		IList<Vertex<T>> adj = new LinkedList<>();
		IList<Edge<T>> edges = this.getAdj(v);
		for (Edge<T> edge : edges) {
			adj.addLast(edge.getDest());
		}
		return adj;
	}
	
	public Edge<T> getEdge(Vertex<T> v, Vertex<T> w) {
		int vIndex = this.search(v);
		IList<Edge<T>> eg = edges.get(vIndex);
		Edge<T> result = null;
		for (Edge<T> edge : eg) {
			if (edge.getOrig().equals(v) && edge.getDest().equals(w))
				result = edge;
		}
		return result;
	}
	
	public ITable<Vertex<T>, Integer> stronglyConnectedComponents(Vertex<T> v) {
		int k = 0; int[] c = {0};
		ITable<Vertex<T>, Integer> path = new LinkedTable<>();
		for (Vertex<T> vertex : vertex) {
			path.set(vertex, 0);
		}
		ITable<Vertex<T>, Integer> component = new LinkedTable<>();
		for (Vertex<T> vertex : vertex) {
			component.set(vertex, 0);
		}
		ITable<Vertex<T>, Integer> higher = new LinkedTable<>();
		for (Vertex<T> vertex : vertex) {
			higher.set(vertex, 0);
		}
		IStack<Vertex<T>> stack = new LinkedStack<>();
		for (Vertex<T> vertex : vertex) {
			if (path.get(vertex) == 0) {
				component = stronglyConnectedDFS(vertex, k, c, stack, path, component, higher);
			}
		}
		return component;
	}
	
	private ITable<Vertex<T>, Integer> stronglyConnectedDFS(Vertex<T> v, int k, int[] c, IStack<Vertex<T>> stack,
			ITable<Vertex<T>, Integer> path, ITable<Vertex<T>, Integer> component, ITable<Vertex<T>, Integer> higher) {
		k++;
		path.set(v, k);
		higher.set(v, k);
		stack.push(v);
		IList<Vertex<T>> list = getAdjVertex(v);
		while (!list.isEmpty()) {
			Vertex<T> w = list.getFirst();
			list.removeFirst();
			if (path.get(w) == 0) {
				stronglyConnectedDFS(w, k, c, stack, path, component, higher);
				higher.set(v, Math.min(higher.get(v), higher.get(w)));
			} else {
				if ((path.get(w) < path.get(v)) && component.get(w) == 0) {
					higher.set(v, Math.min(higher.get(v), path.get(w)));	
				}
			}
		}
		if (higher.get(v) == path.get(v)) {
			c[0]++;
			component.set(v, c[0]);
			Vertex<T> w = stack.peek();
			stack.pop();
			while (!w.equals(v)) {
				component.set(w, c[0]);
				w = stack.peek();
				stack.pop();
			}
		}
		return component;
	}
	
	public ITable<Vertex<T>, Integer> getInputDegree() {
		ITable<Vertex<T>, Integer> idegree = new LinkedTable<>();
		
		for (Vertex<T> v : vertex) {
			int input = 0;
			for (IList<Edge<T>> iList : edges) {
				for (Edge<T> edge : iList) {
					if (edge.getDest().equals(v)) {
						input++;
					}
				}
			}
			idegree.set(v, input);
		}
		
		return idegree;
	}
	
	public IList<Vertex<T>> topologicalSort() {
		IList<Vertex<T>> tsort = new LinkedList<>();
		IList<Vertex<T>> adj;
		ITable<Vertex<T>, Integer> idegree = getInputDegree();
		IQueue<Vertex<T>> q = new LinkedQueue<>();
		IList<Vertex<T>> idl = idegree.keyList();
		for (Vertex<T> vertex : idl) {
			if (idegree.get(vertex) == 0) {
				q.enqueue(vertex);
			}
		}
		int posicion = -1;
		while (!q.isEmpty()) {
			Vertex<T> v = q.front();
			q.dequeue();
			posicion++;
			tsort.add(posicion, v);
			adj = getAdjVertex(v);
			
			while (!adj.isEmpty()) {
				Vertex<T> w = adj.getFirst();
				adj.removeFirst();
				idegree.set(w, idegree.get(w) - 1);
				if (idegree.get(w) == 0) {
					q.enqueue(w);
				}
			}
		}
		return tsort;
	}
	
	public CostPath<T> kruskal(Vertex<T> v) {
		
		GraphAlgorithms<T> result = new GraphAlgorithms<T>();
		
		CostPath<T> cp = new CostPath<>();
        cp.cost = 0.0;
        cp.result = result;
		
        double cost = 0.0;
        
        if (numVertex() <= 1)
        	return cp;
        
        IList<Edge<T>> edges = getEdges();
        
        IList<Edge<T>> sort = IBinarySearchTree.sort(edges);
        
        UnionFind<Vertex<T>> unionFind = new UnionFind<>();
        
        for (Vertex<T> u : vertex)
            unionFind.add(u);

        for (Vertex<T> u : vertex)
            result.addVertex(u);

        int numEdges = 0;

        for (Edge<T> edge: sort) {
            
            if (unionFind.find(edge.getOrig()) == unionFind.find(edge.getDest()))
                continue;

            result.addEdge(getEdge(edge.getOrig(), edge.getDest()));
            
            cost += edge.getWeight();

            unionFind.union(edge.getOrig(), edge.getDest());

            if (++numEdges == numVertex()) break;
        }
        
        cp.cost = cost;
		cp.result = result;
        return cp;
	}
	
	public IList<Edge<T>> getEdges() {
		IList<Edge<T>> result = new LinkedList<>();
		
		ISet<Vertex<T>> used = new Set<>();
		
		
		for (int i = 0; i < vertex.size(); i++) {
			
			Vertex<T> v = vertex.get(i);
			
			IList<Edge<T>> edges = getAdj(v);
			
			for (Edge<T> edge : edges) {
				if (used.contains(edge.getDest())) continue;
				
				result.addLast(edge);
			}
			
			used.add(v);
		}
		
		return result;
	}

	public CostPath<T> prim(Vertex<T> v) {
		IPriorityQueue<Vertex<T>> pq = new FibonacciHeap<>();
		
		ITable<Vertex<T>, Entry<Vertex<T>>> entries = new ClosedHashTable<>();
		
		GraphAlgorithms<T> result = new GraphAlgorithms<T>();
		
		CostPath<T> cp = new CostPath<>();
        cp.cost = 0.0;
        cp.result = result;
		
        double cost = 0.0;
        
		if (isEmpty())
            return cp;
		
		result.addVertex(v);
		
		addOutgoingEdges(v, pq, result, entries);

		for (int i = 0; i < numVertex() - 1; ++i) {
			
            Vertex<T> toAdd = pq.dequeueMin().getValue();

            Vertex<T> endpoint = minCostEndpoint(toAdd, result);

            result.addVertex(toAdd);
            Edge<T> e = getEdge(toAdd, endpoint);
            result.addEdge(e);
            
            cost += getWeight(e);
            
            addOutgoingEdges(toAdd, pq, result, entries);
        }
		
		cp.cost = cost;
		cp.result = result;
        return cp;
	}
	
	public ITable<Vertex<T>, Double> dijkstra(Vertex<T> v) {
		IPriorityQueue<Vertex<T>> pq = new FibonacciHeap<>();
		
		ITable<Vertex<T>, Entry<Vertex<T>>> entries = new ClosedHashTable<>();
		
		ITable<Vertex<T>, Double> result = new LinkedTable<>();
		
		for(Vertex<T> u: vertex) {
			entries.set(u, pq.enqueue(u, Double.POSITIVE_INFINITY));
		}
		
		pq.decreaseKey(entries.get(v), 0.0);

		while (!pq.isEmpty()) {
		
			Entry<Vertex<T>> curr = pq.dequeueMin();
			
			result.set(curr.getValue(), curr.getPriority());
			
			IList<Edge<T>> edges = this.getAdj(curr.getValue());
			
			for (Edge<T> edge : edges) {
				
				if (result.contains(edge.getDest())) continue;
				
				double pathCost = curr.getPriority() + edge.getWeight();
				
				Entry<Vertex<T>> dest = entries.get(edge.getDest());
				
				if (Double.compare(pathCost, dest.getPriority()) < 0)
					pq.decreaseKey(dest, pathCost);
			}
		}
		
		return result;
	}
	
	public ITable<Vertex<T>, Double> bellmanFord(Vertex<T> v) {
		ITable<Vertex<T>, Double> result = new LinkedTable<>();
		for (Vertex<T> u: vertex) {
			result.set(u, Double.POSITIVE_INFINITY);
		}
		result.set(v, 0.0);
		
		
		ITable<Vertex<T>, Double> scratch = new LinkedTable<>();
		
		for (int k = 1; k <= numVertex(); ++k) {
			
			putAll(scratch, result);
			
			for (Vertex<T> u : vertex) {
				
				IList<Edge<T>> edges = this.getAdj(u);
				
				for (Edge<T> edge : edges) {
					scratch.set(edge.getDest(),
                        Math.min(scratch.get(edge.getDest()), edge.getWeight() + result.get(u)));
				}
			}
			
			ITable<Vertex<T>, Double> temp = result;
            result = scratch;
            scratch = temp;
		}
		
		return result;
	}
	
	public GraphAlgorithms<T> floydWarshall() {
		ITable<Vertex<T>, Integer> vertices = enumerateNodes();
		
		double[][] values  = new double[vertices.size()][vertices.size()];
		
		for (int i = 0; i < vertices.size(); ++i)
            for (int j = 0; j < vertices.size(); ++j)
                values[i][j] = Double.POSITIVE_INFINITY;
		
		IList<Vertex<T>> vert = vertices.keyList();
		for (Vertex<T> vertex : vert) {
			IList<Edge<T>> edges = this.getAdj(vertex);
			
			for (Edge<T> e : edges) {
				values[vertices.get(vertex)][vertices.get(e.getDest())] = e.getWeight();
			}
		}
		
		for (int i = 0; i < vertices.size(); ++i)
            values[i][i] = 0.0;
		
		double[][] scratch = new double[vertices.size()][vertices.size()];
        for (int k = 0; k < vertices.size(); ++k) {
            
            for (int i = 0; i < vertices.size(); ++i)
                for (int j = 0; j < vertices.size(); ++j)
                    scratch[i][j] = Math.min(values[i][j],
                                             values[i][k] + values[k][j]);

            double[][] temp = values;
            values = scratch;
            scratch = values;
        }

        return distancesToGraph(values, vertices);
	}
	
	private GraphAlgorithms<T> distancesToGraph(double[][] values, ITable<Vertex<T>, Integer> vertices) {
		GraphAlgorithms<T> result = new GraphAlgorithms<T>(true, true);
		
		Vertex<T>[] indexToNode = (Vertex<T>[]) new Vertex[vertices.size()];
		IList<Vertex<T>> vert = vertices.keyList();
		for (Vertex<T> vertex : vert) {
			indexToNode[vertices.get(vertex)] = vertex;
		}
        
		for (Vertex<T> v: indexToNode)
            result.addVertex(v);
		
		for (int i = 0; i < vertices.size(); ++i)
            for (int j = 0; j < vertices.size(); ++j)
                result.addEdge(new Edge<>(indexToNode[i], indexToNode[j], values[i][j]));
		
		return result;
	}

	private ITable<Vertex<T>, Integer> enumerateNodes() {
		ITable<Vertex<T>, Integer> result = new LinkedTable<>();

        for (Vertex<T> vert: vertex)
            result.set(vert, result.size());

        return result;
    }
	
	private void putAll(ITable<Vertex<T>, Double> scratch, ITable<Vertex<T>, Double> result) {
		IList<Vertex<T>> keys = result.keyList();
		for (Vertex<T> vertex : keys) {
			Double value = result.get(vertex);
			scratch.set(vertex, value);
		}		
	}

	private Vertex<T> minCostEndpoint(Vertex<T> toAdd, GraphAlgorithms<T> result) {
		Vertex<T> endpoint = null;
        double leastCost = Double.POSITIVE_INFINITY;

        IList<Edge<T>> edges = this.getAdj(toAdd);
        for (Edge<T> edge : edges) {
        	
        	if (!result.hasVertex(edge.getDest())) continue;
        	
        	if (Double.compare(edge.getWeight(), leastCost) == 1 || Double.compare(edge.getWeight(), leastCost) == 0) continue;
        	
        	endpoint = edge.getDest();
        	leastCost = edge.getWeight();
        }
        
        return endpoint;
	}

	private void addOutgoingEdges(Vertex<T> v, IPriorityQueue<Vertex<T>> pq, GraphAlgorithms<T> result, ITable<Vertex<T>, Entry<Vertex<T>>> entries) {
		IList<Edge<T>> edges = this.getAdj(v);
		for (Edge<T> edge : edges) {
			if (result.hasVertex(edge.getDest())) continue;
			
			if (!entries.contains(edge.getDest())) {
				entries.set(edge.getDest(), pq.enqueue(edge.getDest(), edge.getWeight()));
			}
			else if (Double.compare(entries.get(edge.getDest()).getPriority(), edge.getWeight()) == 1) {
				pq.decreaseKey(entries.get(edge.getDest()), edge.getWeight());
			}
		}
	}

	public int[] coloring(int m) {
		Contract.require(m > 0);
		int n = this.numVertex();
		
		int[] colors = new int[n];
		
		Arrays.fill(colors, 0);
		
		if (!graphColoringUtil(m, colors, 0))
			return null;
		
		return colors;
	}
	
	private boolean graphColoringUtil(int m, int[] colors, int v) {
		if (v == this.numVertex())
			return true;
		
		for (int i = 1; i <= m; i++) {
			
			if (isSafe(colors, v, i)) {
				
				colors[v] = i;
				
				if (graphColoringUtil(m, colors, v + 1))
					return true;
				
				colors[v] = 0;
			}
		}
		
		return false;
	}

	private boolean isSafe(int[] colors, int v, int cr) {
		for (int i = 0; i < this.numVertex(); i++) {
			if (this.hEdge(v, i)  && cr == colors[i])
				return false;
		}
		return true;
	}

	private boolean hEdge(int v, int i) {
		Vertex<T> u = vertex.get(v);
		Vertex<T> w = vertex.get(i);
		return getEdge(u, w) != null;
	}
	
	public int[] hamiltonianCycle() {
		int[] sol = new int[this.numVertex()];
		sol[0] = 0;
		for (int i = 1; i < sol.length; i++) {
			sol[i] = -1;
		}
		
		if (!solveHamiltonianCycle(sol, 1)) {
			return null;
		} else {
			return sol;
		}
	}
	
	private boolean solveHamiltonianCycle(int[] sol, int k) {
		if (k == numVertex()) {
            if (hEdge(sol[k - 1], sol[0]))
                return true;
            else
                return false;
        }
		for (int ver = 1; ver < numVertex(); ver++) {
			
            if (check(ver, sol, k)) {
            	
                sol[k] = ver;
                
                if (solveHamiltonianCycle(sol, k + 1)) return true;

                sol[k] = -1;
            }
        }
		
		return false;
	}

	private boolean check(int ver, int[] sol, int k) {
		
		if (!hEdge(sol[k - 1], ver))
            return false;
		
        for (int i = 0; i < k; i++) {
            if (sol[i] == ver)
                return false;
        }
        
        return true;
	}

	public static class VertexInfo<T> {
		
		public Vertex<T> vertex;
		public IList<Vertex<T>> adj;
	}
	
	public static class CostPath<T> {
		
		public GraphAlgorithms<T> result;
		public Double cost;
	}
}
