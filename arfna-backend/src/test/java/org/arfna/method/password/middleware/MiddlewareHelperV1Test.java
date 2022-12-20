package org.arfna.method.password.middleware;

import org.arfna.database.entity.Subscriber;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MiddlewareHelperV1Test {

    @Test
    public void testNonexistentSubscriberUnauthorized() {
        IMiddlewareHelper helper = new MiddlewareHelperV1();
        assertFalse(helper.isSubscriberAuthorized(Optional.empty(), ESubscriberRole.WRITER_ROLE));
        assertFalse(helper.isSubscriberAuthorized(Optional.empty(), ESubscriberRole.MAINT_ROLE));
        assertFalse(helper.isSubscriberAuthorized(Optional.empty(), ESubscriberRole.ADMIN_ROLE));
    }

    @Test
    public void testNoneUserIsLessThanAll() {
        IMiddlewareHelper helper = new MiddlewareHelperV1();
        Subscriber sub = new Subscriber();
        sub.setRole(ESubscriberRole.NONE_ROLE.getRoleName());
        assertFalse(helper.isSubscriberAuthorized(Optional.of(sub), ESubscriberRole.WRITER_ROLE));
        assertFalse(helper.isSubscriberAuthorized(Optional.of(sub), ESubscriberRole.MAINT_ROLE));
        assertFalse(helper.isSubscriberAuthorized(Optional.of(sub), ESubscriberRole.ADMIN_ROLE));
    }

    @Test
    public void testWriterIsAtLeastWriter() {
        IMiddlewareHelper helper = new MiddlewareHelperV1();
        Subscriber sub = new Subscriber();
        sub.setRole(ESubscriberRole.WRITER_ROLE.getRoleName());
        assertTrue(helper.isSubscriberAuthorized(Optional.of(sub), ESubscriberRole.WRITER_ROLE));
        assertFalse(helper.isSubscriberAuthorized(Optional.of(sub), ESubscriberRole.MAINT_ROLE));
        assertFalse(helper.isSubscriberAuthorized(Optional.of(sub), ESubscriberRole.ADMIN_ROLE));
    }

    @Test
    public void testMaintIsAtLeastWriter() {
        IMiddlewareHelper helper = new MiddlewareHelperV1();
        Subscriber sub = new Subscriber();
        sub.setRole(ESubscriberRole.MAINT_ROLE.getRoleName());
        assertTrue(helper.isSubscriberAuthorized(Optional.of(sub), ESubscriberRole.WRITER_ROLE));
        assertTrue(helper.isSubscriberAuthorized(Optional.of(sub), ESubscriberRole.MAINT_ROLE));
        assertFalse(helper.isSubscriberAuthorized(Optional.of(sub), ESubscriberRole.ADMIN_ROLE));
    }

    @Test
    public void testAdminIsAlwaysAllowed() {
        IMiddlewareHelper helper = new MiddlewareHelperV1();
        Subscriber sub = new Subscriber();
        sub.setRole(ESubscriberRole.ADMIN_ROLE.getRoleName());
        assertTrue(helper.isSubscriberAuthorized(Optional.of(sub), ESubscriberRole.WRITER_ROLE));
        assertTrue(helper.isSubscriberAuthorized(Optional.of(sub), ESubscriberRole.MAINT_ROLE));
        assertTrue(helper.isSubscriberAuthorized(Optional.of(sub), ESubscriberRole.ADMIN_ROLE));
    }

}
