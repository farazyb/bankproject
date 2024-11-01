package com.example.concurrent_banking_system.controller.states;

public interface EntryState<T> {
    void execute(EntryContext<T> entryContext);

    void previous(AbstractEntryState<T> state);
}
