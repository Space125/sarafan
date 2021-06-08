package com.example.sarafan.domain;

/**
 * @author Ivan Kurilov on 18.05.2021
 */

public final class Views {
    public interface Id {
    }

    public interface IdText extends Id {
    }

    public interface FullComment extends IdText {
    }

    public interface FullMessage extends IdText {
    }
}
