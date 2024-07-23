package org.acme;

import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.annotations.QuarkusMain;
import org.jboss.logging.Logger;

@QuarkusMain
public class Main {

    public static void main(String ... args) {
        System.out.println("Running main method");
        Quarkus.run(args);
    }
}


