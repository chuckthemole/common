package com.rumpus.common.Controller;

import com.rumpus.common.AbstractCommonObject;

/**
 * Root base class for all controllers.
 *
 * <p>
 * This class intentionally contains only functionality that is shared by every
 * controller, regardless of whether it serves REST endpoints, MVC views,
 * GraphQL, or another transport mechanism.
 * </p>
 *
 * <p>
 * Specialized controller behavior should be implemented in more focused
 * abstract subclasses such as {@code AbstractCommonRestController} or
 * {@code AbstractCommonUserController}.
 * </p>
 */
public abstract class AbstractCommonController
        extends
            AbstractCommonObject
        implements
            ICommonController {
}
