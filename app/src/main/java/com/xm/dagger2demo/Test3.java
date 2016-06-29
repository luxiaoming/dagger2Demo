package com.xm.dagger2demo;

import java.util.Set;

import javax.inject.Inject;


public class Test3 {
    private Set<String> strings;
    @Inject
    Test3(Set<String> strings) {
        assert strings.contains("ABC");
        assert strings.contains("DEF");
        assert strings.contains("GHI");
        this.strings=strings;

    }
}
