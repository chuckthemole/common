package com.rumpus.common.User;

import com.rumpus.common.User.Requests.CreateUserRequest;

public interface IUserFactory<USER extends AbstractCommonUser<USER, META>,
        META extends AbstractCommonUserMetaData<META>> {

    /**
     * Create an empty user.
     *
     * @return a new user instance
     */
    USER createEmpty();

    /**
     * Create a new initialized user from a request.
     *
     * @param request
     *            create user request
     *
     * @return initialized user
     */
    USER createUser(CreateUserRequest request);

    /**
     * Create empty metadata for a user.
     *
     * @return empty metadata
     */
    META createMetaData();
}
