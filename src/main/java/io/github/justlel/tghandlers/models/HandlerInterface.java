package io.github.justlel.tghandlers.models;

import com.pengrad.telegrambot.model.Update;
import io.github.justlel.tghandlers.models.handlers.GenericUpdateHandler;
import io.github.justlel.tghandlers.models.handlers.SpecificUpdateHandler;

/**
 * Interface to be implemented by all update handlers.
 * Its only method, {@link #handleUpdate}, will be called
 * by a {@link SpecificUpdateHandler} or a {@link GenericUpdateHandler},
 * once the update has been dispatched to the correct handler.
 *
 * @author justlel
 * @version 1.0
 */
public interface HandlerInterface {

    /**
     * Once the update is dispatched, it will be passed to this method, which will
     * take care of executing the correct actions specified for the update.
     *
     * @param update The update to be handled.
     */
    void handleUpdate(Update update);
}
