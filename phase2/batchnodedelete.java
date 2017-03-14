package phase2;

import diskmgr.*;
import global.Descriptor;
import global.EID;
import global.GlobalConst;
import global.NID;
import heap.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by joelmascarenhas on 3/11/17.
 */
public class batchnodedelete implements GlobalConst{
    static boolean nodedelete(String filename,String dbname)
            throws Exception {
        int[] res = new int[]{0,0,0,0,0};
        GraphDB phase2 = new GraphDB();
        phase2.openDB(dbname,1024);
        int counter = 0;
        List<String> content = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String lineread;
            while ((lineread = br.readLine()) != null) {
                content.add(lineread);
            }
        }

        for (int i=0;i<content.size();i++) {
            NScan nodescan = phase2.getNhf().openScan();
            NID start_nid = new NID();
            NID delnid = new NID();
            Node current_node;
            boolean delnode = false;

            String temp = content.get(i);

            while (!delnode) {
                current_node = nodescan.getNext(start_nid);
                if (current_node.getLabel().equals(temp)) {
                    delnode = true;
                    delnid = start_nid;
                }
            }

            EScan edgescan = phase2.getEhf().openScan();
            EID start_eid = new EID();
            List<EID> edgesToBeDeleted = new ArrayList<>();
            Edge current_edge;
            current_edge = edgescan.getNext(start_eid);
            NID src;
            NID dest;
            while(current_edge != null)
            {
                src = current_edge.getSource();
                dest = current_edge.getDestination();
                if(delnid.equals(src) || delnid.equals(dest))
                    edgesToBeDeleted.add(start_eid);
            }
            boolean edgedelstatus;
            for(EID a:edgesToBeDeleted)
                edgedelstatus = phase2.getEhf().deleteEdge(a);

            boolean stat = phase2.getNhf().deleteNode(delnid);
            counter++;

        }
        // get the node count
        res[0] = phase2.getNodeCnt();

        // get the edge count
        res[1] = phase2.getEdgeCnt();

        // get the pages read count
        res[2] = phase2.getNoOfReads();
        // PCounter.getRcounter();

        //get the pages write count
        res[3] = phase2.getNoOfWrites();

        System.out.println("Node count = " + res[0]);
        System.out.println("Edge count = " + res[1]);
        System.out.println("Disk pages read =" + res[2]);
        System.out.println("Disk pages written =" + res[3]);

        if(counter == content.size())
            return true;
        else return false;
    }
}
