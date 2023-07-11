package interfaces.code;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

/**
 * This is a list implementation that likes to tell you what it's doing!
 */
class TalkativeList<E> extends ArrayList<E> {

    // Since our super class (ArrayList) already implemented this method, we must override it!
    @Override
    public boolean contains(Object o) {

        // We just print some information and then pass this request onto our super class!
        System.out.printf("Looking for %s...\n", o.toString());

        boolean contains = super.contains(o);

        if (contains) {
            System.out.println("I found it!");
        } else {
            System.out.println("No where to be seen.");
        }

        return contains;

    }

}

/**
 * Performs a profiling test on list interfaces.
 */
class Profiler {

    // The length of the lists we will profile.
    int length;

    public Profiler(int length) {

        this.length = length;

    }

    /**
     * Prints out the time it takes for a list to perform a given set of operations.
     */
    public void timeList(List<Integer> list, String name) {

        System.out.printf("Timing %s:\n", name);

        // First, we fill the list with integers.
        long startTime = System.currentTimeMillis();

        for (int i = 0; i < length; i++) {
            list.add(i);
        }

        long endTime = System.currentTimeMillis();
        System.out.printf("\tFilling List: %dms\n", endTime - startTime);

        // Now, we will iterate the list and increment every integer.
        startTime = System.currentTimeMillis();

        for (int i = 0; i < length; i++) {

            // Retrieve the value.
            int value = list.get(i);

            // Now set it back into the list.
            list.set(i, value + 1);

        }

        endTime = System.currentTimeMillis();
        System.out.printf("\tIncrementing List: %dms\n", endTime - startTime);

        // Then, we will check that this list contains certain elements.
        startTime = System.currentTimeMillis();

        boolean containsOne = list.contains(1);
        boolean containsFive = list.contains(5);
        boolean containsLength = list.contains(length);
        boolean containsLengthAddOne = list.contains(length + 1);

        endTime = System.currentTimeMillis();
        System.out.printf("\tSearching List: %dms\n", endTime - startTime);
        System.out.printf(
            "\t\tFound: 1=%b, 5=%b, %d=%b, %d=%b\n",
            containsOne,
            containsFive,
            length,
            containsLength,
            length + 1,
            containsLengthAddOne
        );

        // Lastly, we will remove the first item until our list is empty.
        startTime = System.currentTimeMillis();

        for (int i = 0; i < length; i++) {

            list.remove(0);

        }

        endTime = System.currentTimeMillis();
        System.out.printf("\tPop Front of List: %dms\n", endTime - startTime);

    }

    public static void main(String[] args) {

        int listLength = 100000;
        Profiler profiler = new Profiler(listLength);

        profiler.timeList(new ArrayList<>(), "ArrayList");
        profiler.timeList(new LinkedList<>(), "LinkedList");
        profiler.timeList(new Vector<>(), "Vector");
        profiler.timeList(new TalkativeList<>(), "TalkativeList");

        // Now create and add your own lists too!
        // Try expirementing with the length of the list, or any other List operations.

    }

}
