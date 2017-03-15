package heap;

/**
 * Created by vamsikrishnag on 3/7/17.
 */

import java.io.*;
import java.lang.*;
import global.*;

public class Node extends Tuple
{
    private String label;
    private Descriptor attrDesc;
    public Node()
    {
        super();
    }

    public Node(Tuple tuple) throws IOException {
        this.data = tuple.data;
        this.attrDesc = Convert.getDescValue(10,tuple.data);
        this.label = Convert.getStrValue(0,tuple.data,10);
    }

    public Node(Node fromNode)
    {
        super(fromNode);
        this.attrDesc = fromNode.attrDesc;
        this.label = fromNode.label;
    }

    public Node(byte [] atuple, int offset){super(atuple,offset,atuple.length);}
    public Node(byte [] atuple, int offset, int length)
    {
        super(atuple,offset,length);
    }


    public byte [] getNodeByteArray()
    {
        return getTupleByteArray();
    }

    public short[] copyFldOffset() {

        return super.copyFldOffset();
    }

    public String getLabel() throws IOException, FieldNumberOutOfBoundException
    {
        //String sval = Convert.getStrValue(fldOffset[0], data,fldOffset[1] - fldOffset[0]);
        //return sval;
        return this.label;
    }

    public Descriptor getDesc() throws IOException, FieldNumberOutOfBoundException
    {
        //Descriptor desc=Convert.getDescValue(fldOffset[1],data)
        //return desc;
        return this.attrDesc;
    }

    public Node setLabel(String label) throws IOException, FieldNumberOutOfBoundException
    {
        //Convert.setStrValue (label, fldOffset[0], data);
        this.label = label;
        Convert.setStrValue(this.label,0,data);
        tuple_length = getLength();
        return this;
    }

    public Node setDesc(Descriptor desc) throws IOException, FieldNumberOutOfBoundException
    {
        //Convert.setDescValue(desc,fldOffset[1], data);
        this.attrDesc = desc;
        Convert.setDescValue(this.attrDesc,10,data);
        tuple_length = getLength();
        return this;
    }
    public int getLength() {
        return 20;
    }

    public void print() throws IOException, FieldNumberOutOfBoundException
    {
        System.out.print("[Label: "+ this.label);
        System.out.print(", Descriptors: "+ this.attrDesc+"]\n"); //Make Descriptor get function public
    }

    public short size()
    {
        return super.size();
    }

    public void nodeCopy(Node fromNode)
    {
        byte [] temparray = fromNode.getTupleByteArray();
        System.arraycopy(temparray, 0,data, super.getTupleOffset(),super.getTupleLength());
//       fldCnt = fromTuple.noOfFlds();
//       fldOffset = fromTuple.copyFldOffset();
    }

    public void nodeInit(byte [] anode, int offset)
    {
        super.tupleInit(anode,offset,anode.length);
    }

    public void nodeSet(byte [] fromnode, int offset)
    {
        super.tupleSet(fromnode,offset,fromnode.length);
    }
}
