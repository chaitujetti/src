package heap;

/**
 * Created by vamsikrishnag on 3/7/17.
 */

import java.io.*;
import java.lang.*;
import global.*;

public class Edge extends Tuple {
    private String label;
    private NID source;
    private NID destination;
    private int weight;

    public Edge() {
        super();
        super.setFieldCount((short) 4);
    }

    public Edge(byte[] anode, int offset)
    {
        super(anode, offset, anode.length);
    }
    public Edge(byte[] anode, int offset, int length)
    {
        super(anode, offset, length);
    }



    public Edge(Edge e)
    {
        super(e);
        this.source=e.source;
        this.label=e.label;
        this.destination=e.destination;
        this.weight=e.weight;
    }

    public Edge(Tuple tuple) throws IOException {
        if (tuple != null) {
            
            this.data = tuple.data;
            this.label = Convert.getStrValue(0, this.data, Edge.MAX_SIZE + 2);
            NID srcId = new NID();
            srcId.pageNo.pid = Convert.getIntValue(Edge.MAX_SIZE + 2, this.data);
            srcId.slotNo = Convert.getIntValue(Edge.MAX_SIZE + 2 + 4, this.data);
            this.source = srcId;
            NID destId = new NID();
            destId.pageNo.pid = Convert.getIntValue(Edge.MAX_SIZE + 2 + 4 + 4, this.data);
            destId.slotNo = Convert.getIntValue(Edge.MAX_SIZE + 2 + 4 + 4 + 4, this.data);
            this.destination = destId;
            this.weight = Convert.getIntValue(Edge.MAX_SIZE + 2 + 4 + 4 + 4 + 4, this.data);

        }
    }

    public String getLabel()
    {
        return label;
    }

    public int getWeight()
    {
        return weight;
    }

    public NID getSource()
    {
        return source;

    }

    public byte[] getEdgeByteArray()
    {
        return getTupleByteArray();
    }

    private int getEdgeLength() {
        return (MAX_SIZE+2) + 4+4 +4+4 +4;

    }

    public NID getDestination()
    {
        return destination;
    }

    public Edge setLabel(String label) throws IOException {
        this.label=label;
        Convert.setStrValue(this.label, 0, data);
        tuple_length = getEdgeLength();
        return this;
    }

    public Edge setWeight(int Weight) throws IOException {
        this.weight=Weight;
        
        Convert.setIntValue(weight, MAX_SIZE+2+4+4+4+4, data);
        tuple_length = getEdgeLength();
        return this;
    }
    public Edge setSource(NID source) throws IOException {
        this.source=source;
        source.pageNo.writeToByteArray(data, MAX_SIZE+2);
        Convert.setIntValue(source.slotNo, MAX_SIZE+2+4, data);
        tuple_length = getEdgeLength();
        return this;
    }

    public Edge setDestination(NID dest) throws IOException {
        destination=dest;
        destination.pageNo.writeToByteArray(data, MAX_SIZE+2+4+4);
        Convert.setIntValue(destination.slotNo, MAX_SIZE+2+4+4+4, data);
        return this;
    }


    public void print(AttrType type[]) throws IOException
    {
        System.out.print("[");
        System.out.print("edge label : "+this.label);
        System.out.print("source : slotNo : "+this.source.slotNo +", pageNo :"+this.source.pageNo);
        System.out.print("Destination : slotNo : "+this.destination.slotNo +", pageNo :"+this.destination.pageNo);
        System.out.print("weight : "+this.weight);
        System.out.println("]");
    }
    public void edgeCopy(Edge edge)
    {
        byte [] temp = edge.getEdgeByteArray();
        System.arraycopy(temp, 0, data, tuple_offset, tuple_length);

    }

    public void edgeInit(byte[] aedge, int offset)
    {
        tupleInit(aedge, offset, aedge.length);

    }
    public void edgeSet(byte[] fromedge, int offset)
    {
        tupleSet(fromedge, offset, fromedge.length);

    }

}
