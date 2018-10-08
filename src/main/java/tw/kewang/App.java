package tw.kewang;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import java.util.Scanner;
import java.util.concurrent.ExecutionException;

public class App {
    public static void main(String[] args) {
        LoadingCache<Integer, String> cache = CacheBuilder.newBuilder().recordStats().maximumSize(1000).build(new CacheLoader<Integer, String>() {
            @Override
            public String load(Integer integer) throws Exception {
                return "string" + integer;
            }
        });

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("\nPress 0: exit, Press 1: set cache, Press 2: remove cache, Press 3: show stats? ");

            int choice = scanner.nextInt();

            switch (choice) {
                case 0:
                    exit();

                    break;
                case 1:
                    System.out.print("key? ");

                    try {
                        String value = cache.get(scanner.nextInt());

                        System.out.println("cache value: " + value);
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }

                    break;
                case 2:
                    break;
                case 3:
                    showStats(cache);

                    break;
            }
        }
    }

    private static void exit() {
        System.out.println("Close program");

        System.exit(0);
    }

    private static void showStats(LoadingCache<Integer, String> cache) {
        System.out.println(cache.stats().toString());
    }
}