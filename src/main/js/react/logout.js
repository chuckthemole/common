import React, { useState } from 'react';
import { isCurrentUserAuthenticated } from './common_requests';

export default function Logout() {
    const logout = <form method="post" action="/logout"><button className="logoutBtn button is-danger" type="submit" value="Sign Out">Sign Out</button></form>;
    const is_user_authenticated = isCurrentUserAuthenticated();
    return is_user_authenticated.isAuthenticated ? logout : <></>;
}