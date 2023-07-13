package interfaces.code;

import java.util.ArrayList;
import java.util.Arrays;
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

    private void addToList(List<Integer> list) {

        for (int i = 0; i < length; i++) {
            list.add(i);
        }

    }

    private void incrementListValues(List<Integer> list) {

        for (int i = 0; i < length; i++) {

            // Retrieve the value.
            int value = list.get(i);

            // Now set it back into the list.
            list.set(i, value + 1);

        }

    }

    private boolean[] searchList(List<Integer> list) {

        boolean[] results = new boolean[4];

        results[0] = list.contains(1);
        results[1] = list.contains(5);
        results[2] = list.contains(length);
        results[3] = list.contains(length + 1);

        return results;

    }

    private void popFrontOfList(List<Integer> list) {

        for (int i = 0; i < length; i++) {
            list.remove(0);
        }

    }

    /**
     * Prints out the time it takes for a list to perform a given set of operations.
     */
    public void timeList(List<Integer> list, String name) {

        System.out.printf("Timing %s:\n", name);

        // First, we fill the list with integers.
        long startTime = System.currentTimeMillis();

        addToList(list);

        long endTime = System.currentTimeMillis();
        System.out.printf("\tFilling List: %dms\n", endTime - startTime);

        // Now, we will iterate the list and increment every integer.
        startTime = System.currentTimeMillis();

        incrementListValues(list);

        endTime = System.currentTimeMillis();
        System.out.printf("\tIncrementing List: %dms\n", endTime - startTime);

        // Then, we will check that this list contains certain elements.
        startTime = System.currentTimeMillis();

        boolean[] searchResults = searchList(list);

        endTime = System.currentTimeMillis();
        System.out.printf("\tSearching List: %dms\n", endTime - startTime);
        System.out.printf(
            "\t\tSearch results: %s\n",
            Arrays.toString(searchResults)
        );

        // Lastly, we will remove the first item until our list is empty.
        startTime = System.currentTimeMillis();

        popFrontOfList(list);

        endTime = System.currentTimeMillis();
        System.out.printf("\tPop Front of List: %dms\n\n", endTime - startTime);

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
