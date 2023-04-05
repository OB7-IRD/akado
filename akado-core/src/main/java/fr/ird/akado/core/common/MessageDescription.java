package fr.ird.akado.core.common;

import fr.ird.common.message.Message;

import java.util.StringJoiner;

/**
 * To describe a message in inspectors.
 * <p>
 * Created on 04/04/2023.
 *
 * @author Tony Chemit - dev@tchemit.fr
 * @since 1.0.0
 */
public interface MessageDescription {

    class MessageDescriptionImpl implements MessageDescription {
        private final String messageType;
        private final String messageCode;
        private final String messageLabel;
        private final boolean inconsistent;

        public MessageDescriptionImpl(String messageType, String messageCode, String messageLabel, boolean inconsistent) {
            this.messageType = messageType;
            this.messageCode = messageCode;
            this.messageLabel = messageLabel;
            this.inconsistent = inconsistent;
        }

        @Override
        public String getMessageType() {
            return messageType;
        }

        @Override
        public String getMessageCode() {
            return messageCode;
        }

        @Override
        public String getMessageLabel() {
            return messageLabel;
        }

        @Override
        public boolean isInconsistent() {
            return inconsistent;
        }

        @Override
        public String toString() {
            return new StringJoiner(", ", MessageDescription.class.getSimpleName() + "[", "]")
                    .add("messageType='" + messageType + "'")
                    .add("messageCode='" + messageCode + "'")
                    .add("messageLabel='" + messageLabel + "'")
                    .add("inconsistent=" + inconsistent)
                    .toString();
        }
    }

    static MessageDescription info(String messageCode, String messageLabel, boolean inconsistent) {
        return new MessageDescriptionImpl(Message.INFO, messageCode, messageLabel, inconsistent);
    }

    static MessageDescription error(String messageCode, String messageLabel, boolean inconsistent) {
        return new MessageDescriptionImpl(Message.ERROR, messageCode, messageLabel, inconsistent);
    }

    static MessageDescription warning(String messageCode, String messageLabel, boolean inconsistent) {
        return new MessageDescriptionImpl(Message.WARNING, messageCode, messageLabel, inconsistent);
    }

    String getMessageType();

    String getMessageCode();

    String getMessageLabel();

    boolean isInconsistent();

}
