package org.jomaveger.structures;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;

public class GraphTest {
	
	private IGraph<Integer> g;
	private Vertex<Integer> a = new Vertex<>("A", 10);
	private Vertex<Integer> b = new Vertex<>("A", 20);
	private Vertex<Integer> c = new Vertex<>("A", 30);
	private Vertex<Integer> d = new Vertex<>("A", 40);
	private Vertex<Integer> e = new Vertex<>("A", 50);
	private Edge<Integer> ab = new Edge<>(a, b);
	private Edge<Integer> ad = new Edge<>(a, d);
	private Edge<Integer> bc = new Edge<>(b, c);
	private Edge<Integer> be = new Edge<>(b, e); 
	private Edge<Integer> dc = new Edge<>(d, c);
	private Edge<Integer> ed = new Edge<>(e, d);
	
	@BeforeEach
    public void setUp() {
		g = new Graph<>(true, false);
    }
    
    @AfterEach
    public void tearDown() {
        g = null;
    }

    @Test
    public void testConstructorEnsuresGraphIsEmpty() {
        assertTrue(g.isEmpty());
        assertTrue(g.isDirected());
        assertFalse(g.isWeighted());
    }
    
    @Test
    public void testAddVertexEnsuresGraphHasVertex() {
        g.addVertex(a);
        g.addVertex(b);
        g.addVertex(c);
        g.addVertex(d);
        g.addVertex(e);
        assertTrue(g.hasVertex(a));
        assertTrue(g.hasVertex(b));
        assertTrue(g.hasVertex(c));
        assertTrue(g.hasVertex(d));
        assertTrue(g.hasVertex(e));
    }
    
    @Test
    public void testAddEdgeEnsuresGraphHasEdge() {
    	g.addVertex(a);
        g.addVertex(b);
        g.addVertex(c);
        g.addVertex(d);
        g.addVertex(e);
        g.addEdge(ab);
        g.addEdge(ad);
        g.addEdge(bc);
        g.addEdge(be);
        g.addEdge(dc);
        g.addEdge(ed);
        assertTrue(g.hasEdge(ab));
        assertTrue(g.hasEdge(ad));
        assertTrue(g.hasEdge(bc));
        assertTrue(g.hasEdge(be));
        assertTrue(g.hasEdge(dc));
        assertTrue(g.hasEdge(ed));
    }
    
    @Test
    public void testSeveralQuerys() {
    	g.addVertex(a);
        g.addVertex(b);
        g.addVertex(c);
        g.addVertex(d);
        g.addVertex(e);
        g.addEdge(ab);
        g.addEdge(ad);
        g.addEdge(bc);
        g.addEdge(be);
        g.addEdge(dc);
        g.addEdge(ed);
        assertEquals(5, g.numVertex());
        assertEquals(6, g.numEdges());
        IList<Edge<Integer>> list = g.getAdj(b);
        assertEquals((Integer)2, list.size());
        assertTrue(list.get(0).getDest().equals(c));
        assertTrue(list.get(1).getDest().equals(e));
    }
    
    @Test
    public void testRemove() {
    	g.addVertex(a);
        g.addVertex(b);
        g.addVertex(c);
        g.addVertex(d);
        g.addVertex(e);
        g.addEdge(ab);
        g.addEdge(ad);
        g.addEdge(bc);
        g.addEdge(be);
        g.addEdge(dc);
        g.addEdge(ed);
        assertEquals(5, g.numVertex());
        assertEquals(6, g.numEdges());
        g.removeVertex(c);
        assertEquals(4, g.numVertex());
        assertEquals(4, g.numEdges());
        g.removeEdge(be);
        assertEquals(4, g.numVertex());
        assertEquals(3, g.numEdges());
    }
}
