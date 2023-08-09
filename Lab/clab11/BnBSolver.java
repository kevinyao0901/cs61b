import java.util.ArrayList;
import java.util.List;

/**
 * BnBSolver for the Bears and Beds problem. Each Bear can only be compared to Bed objects and each Bed
 * can only be compared to Bear objects. There is a one-to-one mapping between Bears and Beds, i.e.
 * each Bear has a unique size and has exactly one corresponding Bed with the same size.
 * Given a list of Bears and a list of Beds, create lists of the same Bears and Beds where the ith Bear is the same
 * size as the ith Bed.
 */
public class BnBSolver {

    private List<Bear> sortedBears=new ArrayList<>();
    private List<Bed> sortedBeds=new ArrayList<>();


    public BnBSolver(List<Bear> bears, List<Bed> beds) {
        // TODO: Fix me.
        quickSort(bears,beds);
    }

    /**
     * Returns List of Bears such that the ith Bear is the same size as the ith Bed of solvedBeds().
     */
    public List<Bear> solvedBears() {
        // TODO: Fix me.
        return sortedBears;
    }

    /**
     * Returns List of Beds such that the ith Bear is the same size as the ith Bear of solvedBears().
     */
    public List<Bed> solvedBeds() {
        // TODO: Fix me.
        return sortedBeds;
    }

    private List<Bear> catenateBear(List<Bear> b1, List<Bear> b2) {
        List<Bear> catenated = new ArrayList<>();
        for (Bear bear : b1) {
            catenated.add(bear);
        }
        for (Bear bear : b2) {
            catenated.add(bear);
        }
        return catenated;
    }

    private List<Bed> catenateBed(List<Bed> b1, List<Bed> b2) {
        List<Bed> catenated = new ArrayList<>();
        for (Bed bed : b1) {
            catenated.add(bed);
        }
        for (Bed bed : b2) {
            catenated.add(bed);
        }
        return catenated;
    }

    private void partitionBear(List<Bear> unsorted,Bed pivot,List<Bear> equal,List<Bear> less,List<Bear> greater)
    {
        for (Bear bear:unsorted)
        {
            if(bear.compareTo(pivot)<0)
            {
                less.add(bear);
            }
            else if (bear.compareTo(pivot)>0)
            {
                greater.add(bear);
            }
            else
            {
                equal.add(bear);
            }
        }
    }

    private void partitionBed(List<Bed> unsorted,Bear pivot,List<Bed> equal,List<Bed> less,List<Bed> greater)
    {
        for (Bed bed:unsorted)
        {
            if(bed.compareTo(pivot)<0)
            {
                less.add(bed);
            }
            else if (bed.compareTo(pivot)>0)
            {
                greater.add(bed);
            }
            else
            {
                equal.add(bed);
            }
        }
    }

    private Pair<List<Bear>,List<Bed>> quickSort(List<Bear> bears,List<Bed> beds)
    {
        if(bears.size()<=1 || beds.size()<=1)
        {
            return new Pair<>(bears,beds);
        }

        Bear pivotBear;
        List<Bear> lessBear=new ArrayList<>();
        List<Bear> equalBear=new ArrayList<>();
        List<Bear> greaterBear=new ArrayList<>();

        Bed pivotBed=beds.get(0);
        List<Bed> lessBed=new ArrayList<>();
        List<Bed> equalBed=new ArrayList<>();
        List<Bed> greaterBed=new ArrayList<>();

        partitionBear(bears,pivotBed,equalBear,lessBear,greaterBear);
        pivotBear=equalBear.get(0);
        partitionBed(beds,pivotBear,equalBed,lessBed,greaterBed);

        Pair<List<Bear>,List<Bed>> less=quickSort(lessBear,lessBed);
        lessBear=less.first();
        lessBed=less.second();

        Pair<List<Bear>,List<Bed>> greater=quickSort(greaterBear,greaterBed);
        greaterBear=greater.first();
        greaterBed=greater.second();

        sortedBears=catenateBear(catenateBear(lessBear,equalBear),greaterBear);
        sortedBeds=catenateBed(catenateBed(lessBed,equalBed),greaterBed);

        return new Pair<>(sortedBears,sortedBeds);
    }
}
