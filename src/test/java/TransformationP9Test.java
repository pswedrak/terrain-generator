import model.*;
import org.junit.Test;
import transformation.Transformation;
import transformation.TransformationP9;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TransformationP9Test extends AbstractTransformationTest{
    private Transformation transformation = new TransformationP9();

    @Test
    public void conditionPassesForCorrectGraph(){
        ModelGraph graph = createCorrectGraph();
        assertTrue(transformation.isConditionCompleted(graph, graph.getInterior("i1").orElseThrow(AssertionError::new)));
    }

    @Test
    public void conditionFailsForEmptyGraph(){
        ModelGraph graph = createEmptyGraph();
        assertFalse(transformation.isConditionCompleted(graph, null));
    }

    @Test
    public void conditionFailsForNull(){
        assertFalse(transformation.isConditionCompleted(null, null));
    }

    @Test
    public void conditionFailsForGraphWithNoHangingNodes(){
        ModelGraph graph = createGraphWithNoHangingNodes();
        assertFalse(transformation.isConditionCompleted(graph, graph.getInterior("i1").orElseThrow(AssertionError::new)));
    }

    private ModelGraph createCorrectGraph(){
        ModelGraph graph = new ModelGraph("testGraph");
        Vertex v1 = graph.insertVertex("v1", VertexType.SIMPLE_NODE, new Point3d(0.0, 0.0,0.0));
        Vertex h2 = graph.insertVertex("h2", VertexType.HANGING_NODE, new Point3d(1.0, 0.0, 0.0));
        Vertex v3 = graph.insertVertex("v3", VertexType.SIMPLE_NODE, new Point3d(2.0, 0.0, 0.0));
        Vertex h4 = graph.insertVertex("h4", VertexType.HANGING_NODE, new Point3d(1.5, 0.5, 0.0));
        Vertex v5 = graph.insertVertex("v5", VertexType.SIMPLE_NODE, new Point3d(1.0, 1.0, 0.0));
        Vertex h6 = graph.insertVertex("h6", VertexType.HANGING_NODE, new Point3d(0.5, 0.5, 0.0));

        GraphEdge v1_h2 = graph.insertEdge("e1", v1, h2, true);
        GraphEdge h2_v3 = graph.insertEdge("e2", h2, v3, true);
        GraphEdge v3_h4 = graph.insertEdge("e3", v3, h4, true);
        GraphEdge h4_v5 = graph.insertEdge("e4", h4, v5, true);
        GraphEdge v5_h6 = graph.insertEdge("e5", v5, h6, true);
        GraphEdge h6_v1 = graph.insertEdge("e6", h6, v1, true);

        InteriorNode in1 = graph.insertInterior("i1", v1, v3, v5);
        return graph;
    }

    private ModelGraph createGraphWithNoHangingNodes(){
        ModelGraph graph = new ModelGraph("testGraph");
        Vertex v1 = graph.insertVertex("v1", VertexType.SIMPLE_NODE, new Point3d(0.0, 0.0,0.0));
        Vertex v3 = graph.insertVertex("v3", VertexType.SIMPLE_NODE, new Point3d(2.0, 0.0, 0.0));
        Vertex v5 = graph.insertVertex("v5", VertexType.SIMPLE_NODE, new Point3d(1.0, 1.0, 0.0));

        GraphEdge v1_v3 = graph.insertEdge("e1", v1, v3, true);
        GraphEdge v3_v5 = graph.insertEdge("e2", v3, v5, true);
        GraphEdge v1_v5 = graph.insertEdge("e3", v1, v5, true);

        InteriorNode in1 = graph.insertInterior("i1", v1, v3, v5);
        return graph;
    }
}
