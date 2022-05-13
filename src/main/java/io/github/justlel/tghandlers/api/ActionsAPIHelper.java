package io.github.justlel.tghandlers.api;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.request.*;
import com.pengrad.telegrambot.response.BaseResponse;
import com.pengrad.telegrambot.response.SendResponse;
import org.jetbrains.annotations.Nullable;

/**
 * A helper for executing requests to the Telegram API.
 * Each request can be executed only if the telegram bot instance has
 * already been defined via the {@link #setTelegramBotInstance} method.
 * All the methods are executed using the {@link #executeRaw} method, which
 * can also be used to send a non-defined request to the API.
 *
 * @author justlel
 * @version 1.0
 */
public class ActionsAPIHelper {

    /**
     * The instance of the Telegram bot to be used when making requests.
     * It can oly be initialized once via the {@link #setTelegramBotInstance} method,
     * and attempting to make a request while this value is null will result in an {@link IllegalStateException}.
     */
    private static TelegramBot telegramBot;

    /**
     * Returns a standard object representing a SendMessage request.
     * The object is made so that each request has the values "disableWebPagePreview" set to true,
     * and the "parseMode" value set to "HTML".
     *
     * @param text   The text to be sent.
     * @param chatId The chat id to send the message to.
     * @return A standard object representing a SendMessage request.
     */
    private static SendMessage getSendMessageObject(String text, long chatId) {
        return new SendMessage(text, String.valueOf(chatId))
                .disableWebPagePreview(true)
                .parseMode(ParseMode.HTML);
    }

    /**
     * Returns a standard object representing a EditMessageText request.
     * The object is made so that each request has the values "disableWebPagePreview" set to true,
     * and the "parseMode" value set to "HTML".
     *
     * @param text      The text to be edited.
     * @param chatId    The chat id to edit the message in.
     * @param messageId The message id to edit.
     * @return A standard object representing a EditMessageText request.
     */
    private static EditMessageText getEditMessageTextObject(String text, long chatId, int messageId) {
        return new EditMessageText(chatId, messageId, text)
                .disableWebPagePreview(true)
                .parseMode(ParseMode.HTML);
    }

    /**
     * Sends a message to the specified chat id, with the specified text, as a reply to the specified message id and, if
     * given, an InlineKeyboard.
     * The object sent is obtained via the {@link #getSendMessageObject} method.
     *
     * @param text           The text to be sent.
     * @param chatId         The chat id to send the message to.
     * @param replyToMessage The message id to reply to.
     * @param keyboardMarkup The keyboard markup to be sent, null if no keyboard markup is to be sent.
     * @return The {@link BaseResponse} from the Telegram API.
     */
    public static SendResponse replyToMessage(String text, long chatId, Integer replyToMessage, @Nullable InlineKeyboardMarkup keyboardMarkup) {
        SendMessage sendMessage = getSendMessageObject(text, chatId).replyToMessageId(replyToMessage);
        if (keyboardMarkup != null)
            sendMessage.replyMarkup(keyboardMarkup);
        return executeRaw(sendMessage);
    }

    /**
     * Sends a message to the specified chat id, with the specified text, as a reply to the specified message id.
     * The object sent is obtained via the {@link #getSendMessageObject} method.
     *
     * @param text           The text to be sent.
     * @param chatId         The chat id to send the message to.
     * @param replyToMessage The message id to reply to.
     * @return The {@link BaseResponse} from the Telegram API.
     */
    public static BaseResponse replyToMessage(String text, long chatId, Integer replyToMessage) {
        return replyToMessage(text, chatId, replyToMessage, null);
    }

    /**
     * Sends a message to the specified chat id, with the specified text, and eventually the specified keyboard markup
     * The object sent is obtained via the {@link #getSendMessageObject} method.
     *
     * @param text           The text to be sent.
     * @param chatId         The chat id to send the message to.
     * @param keyboardMarkup The keyboard markup to be sent, null if no keyboard markup is to be sent.
     * @return The {@link BaseResponse} from the Telegram API.
     */
    public static BaseResponse sendMessage(String text, long chatId, @Nullable InlineKeyboardMarkup keyboardMarkup) {
        SendMessage sendMessage = getSendMessageObject(text, chatId);
        if (keyboardMarkup != null)
            sendMessage.replyMarkup(keyboardMarkup);
        return executeRaw(sendMessage);
    }

    /**
     * Sends a message to the specified chat id, with the specified text.
     * The object sent is obtained via the {@link #getSendMessageObject} method.
     *
     * @param text   The text to be sent.
     * @param chatId The chat id to send the message to.
     * @return The {@link BaseResponse} from the Telegram API.
     */
    public static BaseResponse sendMessage(String text, long chatId) {
        return sendMessage(text, chatId, null);
    }

    /**
     * Edits the message to the specified text, in the specified chat id, and with the specified message id, and optionally
     * with an InlineKeyboard.
     * The object sent is obtained via the {@link #getEditMessageTextObject} method.
     *
     * @param text           The text to be edited.
     * @param chatId         The chat id to edit the message in.
     * @param messageId      The message id to edit.
     * @param keyboardMarkup The keyboard markup to be sent, null if no keyboard markup is to be sent.
     * @return The {@link BaseResponse} from the Telegram API.
     */
    public static BaseResponse editMessage(String text, long chatId, int messageId, @Nullable InlineKeyboardMarkup keyboardMarkup) {
        EditMessageText editMessageText = getEditMessageTextObject(text, chatId, messageId);
        if (keyboardMarkup != null)
            editMessageText.replyMarkup(keyboardMarkup);
        return executeRaw(editMessageText);
    }

    /**
     * Edits the message to the specified text, in the specified chat id, and with the specified message id.
     * The object sent is obtained via the {@link #getEditMessageTextObject} method.
     *
     * @param text      The text to be edited.
     * @param chatId    The chat id to edit the message in.
     * @param messageId The message id to edit.
     * @return The {@link BaseResponse} from the Telegram API.
     */
    public static BaseResponse editMessage(String text, long chatId, int messageId) {
        return editMessage(text, chatId, messageId, null);
    }

    /**
     * Answers a callback query marked by a specified callback query id, with the specified text.
     * The method automatically sets the "showAlert" parameter to true, displaying a popup in the
     * user's Telegram app.
     *
     * @param callbackQueryId The callback query id to answer.
     * @param text            The text to be sent.
     * @return The {@link BaseResponse} from the Telegram API.
     */
    public static BaseResponse answerCallbackQuery(String callbackQueryId, String text) {
        AnswerCallbackQuery answerCallbackQuery = new AnswerCallbackQuery(callbackQueryId)
                .text(text)
                .showAlert(true);
        return executeRaw(answerCallbackQuery);
    }

    /**
     * Leaves a chat with a specified chat id.
     *
     * @param chatId The chat id to leave.
     * @return The {@link BaseResponse} from the Telegram API.
     */
    public static BaseResponse leaveChat(Long chatId) {
        return executeRaw(new LeaveChat(chatId));
    }

    /**
     * Forwards a message with a specified id from a chat to another.
     *
     * @param toChatId   The chat id to forward the message to.
     * @param fromChatId The chat id to forward the message from.
     * @param messageId  The message id to forward.
     * @return The {@link BaseResponse} from the Telegram API.
     */
    public static BaseResponse forwardMessage(Long toChatId, Long fromChatId, Integer messageId) {
        return executeRaw(new ForwardMessage(toChatId, fromChatId, messageId));
    }

    /**
     * Returns the {@link #telegramBot} instance.
     *
     * @return The {@link #telegramBot} instance.
     */
    private static TelegramBot getTelegramBotInstance() {
        return telegramBot;
    }

    /**
     * Sets the {@link #telegramBot} instance to be used when making requests to the API.
     * If an instance is already set, the method will raise an {@link IllegalStateException}.
     *
     * @param telegramBotInstance The {@link TelegramBot} instance to be used.
     * @throws IllegalAccessException If the {@link #telegramBot} instance is already set.
     */
    public static void setTelegramBotInstance(TelegramBot telegramBotInstance) throws IllegalAccessException {
        if (telegramBot != null)
            throw new IllegalAccessException();
        telegramBot = telegramBotInstance;
    }

    /**
     * Executes a raw request to the Telegram API, returning a {@link BaseResponse} object as result.
     * If the {@link #telegramBot} instance is not initialized, an {@link IllegalStateException} is thrown.
     *
     * @param request The request to be executed.
     * @param <T>     The type of the request object.
     * @param <R>     The type of the response object.
     * @return The {@link BaseResponse} from the Telegram API.
     * @throws IllegalStateException If the {@link #telegramBot} instance is not initialized.
     */
    public static <T extends BaseRequest<T, R>, R extends BaseResponse> R executeRaw(T request) throws IllegalStateException {
        if (getTelegramBotInstance() == null)
            throw new IllegalStateException("Telegram bot instance not set");
        return getTelegramBotInstance().execute(request);
    }
}
