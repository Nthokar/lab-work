package org.example.app;

import org.example.Configurations;

import java.util.Scanner;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class Input implements Runnable {
    public Consumer<String> f;
    String previous;
    @Override
    public void run() {
        System.out.printf("\nCurrent value: %s", previous);
        System.out.println("\nplease enter new value");
        System.out.println("\nyou can leave current value by type \"quit\"");
        var scanner = new Scanner(System.in);
        var message = scanner.next();

        if (message.equalsIgnoreCase("quit")) return;
        previous = message;
        f.accept(message);
    }

    public Input(Consumer<String> f, String previous) {
       this.f = f;
       this.previous = previous;
    }
}
