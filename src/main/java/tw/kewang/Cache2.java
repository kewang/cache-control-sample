package tw.kewang;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import java.security.SecureRandom;
import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

public class Cache2 {
    public static void main(String[] args) {
        Cache<Integer, String> cache = CacheBuilder.newBuilder().recordStats().maximumSize(1000).build();

        SecureRandom secureRandom = new SecureRandom();

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("\n0: exit, 1: set cache, 2: invalidate some cache, 3: invalidate all caches, 4: random fill, 5: show stats? ");

            int choice = scanner.nextInt();

            switch (choice) {
                case 0:
                    exit();

                    break;
                case 1:
                    setCache(cache, scanner);

                    break;
                case 2:
                    invalidateSomeCache(cache, scanner);

                    break;
                case 3:
                    invalidateAllCaches(cache);

                    break;
                case 4:
                    randomFill(cache, secureRandom);

                    break;
                case 5:
                    showStats(cache);

                    break;
            }
        }
    }

    private static void exit() {
        System.out.println("Close program");

        System.exit(0);
    }

    private static void setCache(Cache<Integer, String> cache, Scanner scanner) {
        System.out.print("key? ");

        try {
            int key = scanner.nextInt();

            String value = cache.get(key, new Callable<String>() {
                @Override
                public String call() {
                    return fromExpensiveData();
                }

                private String fromExpensiveData() {
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    System.out.println("from expensive data");

                    return "item" + key;
                }
            });

            System.out.println("cache item: " + value);
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    private static void invalidateSomeCache(Cache<Integer, String> cache, Scanner scanner) {
        System.out.print("key? ");

        cache.invalidate(scanner.nextInt());
    }

    private static void invalidateAllCaches(Cache<Integer, String> cache) {
        cache.invalidateAll();
    }

    private static void randomFill(Cache<Integer, String> cache, SecureRandom secureRandom) {
        for (int i = 0; i < 100; i++) {
            try {
                int key = secureRandom.nextInt(100);

                cache.get(key, new Callable<String>() {
                    @Override
                    public String call() {
                        return fromExpensiveData();
                    }

                    private String fromExpensiveData() {
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        System.out.println("from expensive data");

                        return "item" + key;
                    }
                });
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
    }

    private static void showStats(Cache<Integer, String> cache) {
        System.out.println(cache.stats().toString());
    }
}