package fr.ird.akado.observe;

import fr.ird.akado.core.common.MessageDescription;
import org.junit.Assert;
import org.junit.Test;

import java.util.Set;
import java.util.TreeSet;

/**
 * Created on 05/04/2023.
 *
 * @author Tony Chemit - dev@tchemit.fr
 * @since 1.0.0
 */
public class MessageDescriptionsTest {

    @Test
    public void testCodeNotDuplicated() {
        Set<String> codes = new TreeSet<>();
        for (MessageDescription messageDescriptions : MessageDescriptions.values()) {
            System.out.println(messageDescriptions);
            String messageCode = messageDescriptions.getMessageCode();
            Assert.assertTrue(String.format("Code: %s is duplicated", messageCode), codes.add(messageCode));
        }
    }
}