package dev.zykov.service;

import dev.zykov.socket.MainClientSocket;
import io.micronaut.context.event.ApplicationEventListener;
import io.micronaut.runtime.event.ApplicationShutdownEvent;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;

@Singleton
@RequiredArgsConstructor
public class MainSocketRunApplicationShutdownListener implements ApplicationEventListener<ApplicationShutdownEvent> {

    private final MainClientSocket mainClientSocket;

    @Override
    public void onApplicationEvent(ApplicationShutdownEvent event) {
        mainClientSocket.closeAllSockets();
    }
}
