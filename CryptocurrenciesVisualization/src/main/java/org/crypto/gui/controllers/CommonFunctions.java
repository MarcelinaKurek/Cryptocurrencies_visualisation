package org.crypto.gui.controllers;

import java.util.Map;
import java.util.Optional;

public class CommonFunctions {
    public static String findIdByName(Map<String, String> allCoins, String name) {
        Optional<String> id = allCoins
                .entrySet()
                .stream()
                .filter(entry -> name.equals(entry.getValue()))
                .map(Map.Entry::getKey)
                .findFirst();

        return id.orElse("");
    }
}
