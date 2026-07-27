package com.rumpus.common.Config.User;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.rumpus.common.AbstractCommonObject;
import com.rumpus.common.Dao.User.IUserDao;
import com.rumpus.common.Service.User.IUserService;
import com.rumpus.common.Service.User.UserSecurityService;
import com.rumpus.common.User.AbstractCommonUser;
import com.rumpus.common.User.AbstractCommonUserMetaData;
import com.rumpus.common.User.AbstractUserFactory;

/**
 * Base Spring configuration for application-specific user services.
 * <p>
 * This class implements the common wiring required to construct a user service
 * while delegating the concrete service implementation to subclasses.
 *
 * <p>
 * Concrete applications provide their own implementations of
 * {@link #createUserService(IUserDao, UserSecurityService, AbstractUserFactory, PasswordEncoder)}
 * to instantiate the appropriate application-specific {@link IUserService}.
 *
 * <p>
 * This follows the <em>Template Method</em> pattern: the bean lifecycle and
 * dependency injection are handled by the base class, while subclasses define
 * the concrete service implementation.
 *
 * @param <USER>
 *            Concrete user type.
 * @param <USER_META>
 *            Concrete user metadata type.
 * @param <USER_SERVICE>
 *            Concrete user service implementation.
 * @param <USER_DAO>
 *            Concrete user DAO implementation.
 * @param <USER_FACTORY>
 *            Concrete user factory implementation.
 */
public abstract class AbstractCommonUserConfig<USER extends AbstractCommonUser<USER, USER_META>,
        USER_META extends AbstractCommonUserMetaData<USER_META>,
        USER_SERVICE extends IUserService<USER, USER_META>,
        USER_DAO extends IUserDao<USER, USER_META>,
        USER_FACTORY extends AbstractUserFactory<USER, USER_META>>
        extends
            AbstractCommonObject {

    /**
     * Primary application user service bean.
     */
    public static final String BEAN_USER_SERVICE = "userService";

    /**
     * Default constructor.
     */
    protected AbstractCommonUserConfig() {
    }

    /**
     * Creates the application's primary {@link IUserService} bean.
     * <p>
     * All common dependencies are resolved by Spring and passed to the subclass,
     * which is responsible for constructing the concrete service implementation.
     *
     * @param userDao
     *            User persistence implementation.
     * @param userSecurityService
     *            Security integration for user management.
     * @param userFactory
     *            Factory used to create user instances.
     * @param passwordEncoder
     *            Password encoder used for hashing and verification.
     *
     * @return The application-specific user service.
     */
    @Bean(name = BEAN_USER_SERVICE)
    @Primary
    public USER_SERVICE userService(
            final USER_DAO userDao,
            final UserSecurityService userSecurityService,
            final USER_FACTORY userFactory,
            final PasswordEncoder passwordEncoder) {

        return createUserService(
                userDao,
                userSecurityService,
                userFactory,
                passwordEncoder);
    }

    /**
     * Creates the concrete user service implementation.
     * <p>
     * Subclasses implement this method to construct their application-specific
     * {@link IUserService}. This method is intentionally not a Spring bean; it is
     * invoked by
     * {@link #userService(IUserDao, UserSecurityService, AbstractUserFactory, PasswordEncoder)}
     * during bean creation.
     *
     * @param userDao
     *            User persistence implementation.
     * @param userSecurityService
     *            Security integration for user management.
     * @param userFactory
     *            Factory used to create user instances.
     * @param passwordEncoder
     *            Password encoder used for hashing and verification.
     *
     * @return A fully initialized application-specific user service.
     */
    protected abstract USER_SERVICE createUserService(
            USER_DAO userDao,
            UserSecurityService userSecurityService,
            USER_FACTORY userFactory,
            PasswordEncoder passwordEncoder);
}
