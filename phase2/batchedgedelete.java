package phase2;


import diskmgr.*;
import global.Descriptor;
import global.EID;
import global.GlobalConst;
import heap.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by joelmascarenhas on 3/11/17.
 */
public class batchedgedelete implements GlobalConst{
    public batchedgedelete() { }

    static boolean edgedelete(String filename,String dbname)
            throws Exception {
        int[] res = new int[]{0, 0, 0, 0};
        boolean status = false;
        GraphDB phase2 = new GraphDB(0);
        phase2.openDB(dbname, 1000);
        EID edgeId = new EID();
        EdgeHeapfile ehf = phase2.getEhf();
        Edge scanned_edge;
        int delCount = 0;

        //List<String> content = Files.readAllLines(Paths.get(filename));
        List<String> content = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String lineread;
            while ((lineread = br.readLine()) != null) {
                content.add(lineread);
            }
        }

        for (int i = 0; i < content.size(); i++) {
            String[] temp = content.get(i).split(" ");
            String edgeLabel = temp[2];
            EScan edgeScan = ehf.openScan();
            EID eid = new EID();
            EID eidToDelete = new EID();

            //scanned_edge = edgeScan.getNext(eid);
            boolean edgeFound = false;
            while (edgeFound == false) {
                scanned_edge = edgeScan.getNext(eid);

                if (scanned_edge.getLabel().equals(edgeLabel)) {
                    //delete the edge
                    edgeFound = true;
                    boolean delStatus = ehf.deleteEdge(eid);
                    delCount++;
                }


            }

        }

        res[0] = phase2.getNodeCnt();
        res[1] = phase2.getEdgeCnt();
        res[2] = phase2.getNoOfReads();
        res[3] = phase2.getNoOfWrites();
        System.out.println("Node count = " + res[0]);
        System.out.println("Edge count = " + res[1]);
        System.out.println("Disk pages read =" + res[2]);
        System.out.println("Disk pages written =" + res[3]);

        if (content.size() == delCount) {
            return true;
        }
        return false;

    }
}
