package interfaces.code;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

/**
 * This is a list implementation that likes to chat with you!
 */
class TalkativeList extends ArrayList<Integer> {

    // Since our super class (ArrayList) already implemented this method, we must override it!
    @Override
    public boolean contains(Object o) {

        // We just print some information and then pass this request onto our super class!
        System.out.printf("Looking for %d...\n", o);
        // Question: Why do we know that o will be an integer here? Isn't it any Object?

        return super.contains(o);

    }

}

class Profiler {

    /**
     * Prints out the time it takes for a list to perform a given set of operations.
     */
    public static void time_list(List<Integer> list, String name, int length) {

        System.out.printf("Timing %s:\n", name);

        // First, we fill the list with integers.
        long start_time = System.nanoTime();

        for (int i = 0; i < length; i++) {
            list.add(i);
        }

        long end_time = System.nanoTime();
        System.out.printf("\tFilling List: %dns\n", end_time - start_time);

        // Now, we will iterate the list and increment every integer.
        start_time = System.nanoTime();

        for (int i = 0; i < length; i++) {

            // Retrieve the value.
            int value = list.get(i);

            // Now set it back into the list.
            list.set(i, value + 1);

        }

        end_time = System.nanoTime();
        System.out.printf("\tIncrementing List: %dns\n", end_time - start_time);

        // lastly, we will check that this list contains certain elements.
        start_time = System.nanoTime();

        boolean contains_1 = list.contains(1);
        boolean contains_5 = list.contains(5);
        boolean contains_length = list.contains(length);
        boolean contains_length_add_1 = list.contains(length + 1);

        end_time = System.nanoTime();
        System.out.printf("\tSearching List: %dns\n", end_time - start_time);
        System.out.printf(
            "\t\tFound: 1=%b, 5=%b, %d=%b, %d=%b\n",
            contains_1,
            contains_5,
            length,
            contains_length,
            length + 1,
            contains_length_add_1
        );

    }

    /**
     *
     * This will run a simple profiler that times different implementations of the list interface.
     *
     */
    public static void main(String[] args) {

        int list_length = 10000;

        time_list(new ArrayList<>(), "ArrayList", list_length);
        time_list(new LinkedList<>(), "LinkedList", list_length);
        time_list(new Vector<>(), "Vector", list_length);
        time_list(new TalkativeList(), "TalkativeList", list_length);

        // Now create and add your own lists too!
        // Try expirementing with the length of the list, or any other List operations.

    }

}