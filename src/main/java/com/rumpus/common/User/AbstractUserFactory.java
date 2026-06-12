package com.rumpus.common.User;

import java.util.UUID;

import com.rumpus.common.User.Requests.CreateUserRequest;

abstract public class AbstractUserFactory<USER extends AbstractCommonUser<USER, META>,
        META extends AbstractCommonUserMetaData<META>>
        implements
            IUserFactory<USER, META> {

    @Override
    public USER createUser(CreateUserRequest request) {
        USER user = this.createEmpty();

        final String username = request.getUsername();
        user.setId(UUID.randomUUID()); // TODO: Should we make sure it's available?
        user.setUsername(username);
        user.setEmail(request.getEmail());

        user.setMetaData(this.createMetaData());

        user.setUserDetails(
                CommonUserDetails.createFromUsernamePassword(
                        username,
                        request.getPassword(),
                        true));

        return user;
    }
}
